package com.example.tmdbfilms.watchlist

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

private const val FILE_NAME = "share_data"
private const val KEY_WATCH_LIST = "watchlist"

class WatchListRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WatchListRepository {

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter: JsonAdapter<List<WatchListItemModel>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, WatchListItemModel::class.java))

    override suspend fun updateWatchList(items: List<WatchListItem>) {
        withContext(ioDispatcher) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            if (sp.contains(KEY_WATCH_LIST).not()) {
                editor.putString(KEY_WATCH_LIST, adapter.toJson(mutableListOf()))
            }
            val models = mutableListOf<WatchListItemModel>()
            items.forEach {
                models.add(WatchListItemModel(it.id, it.title, it.posterPath, it.mediaType))
            }
            editor.putString(KEY_WATCH_LIST, adapter.toJson(models))
            editor.apply()
        }
    }

    override suspend fun addToWatchList(item: WatchListItem) {
        withContext(ioDispatcher) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            if (sp.contains(KEY_WATCH_LIST).not()) {
                editor.putString(KEY_WATCH_LIST, "[]")
            }
            val models =
                adapter.fromJson(sp.getString(KEY_WATCH_LIST, "[]") ?: "[]")?.toMutableList()
            val model =
                WatchListItemModel(item.id, item.title, item.posterPath, item.mediaType.lowercase())
            models?.add(model)
            editor.putString(KEY_WATCH_LIST, adapter.toJson(models))
            editor.apply()
        }
    }

    override suspend fun contains(key: Int): Boolean {
        return withContext(ioDispatcher) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            var models: List<WatchListItemModel>? = mutableListOf()
            if (sp.contains(KEY_WATCH_LIST)) {
                models = adapter.fromJson(sp.getString(KEY_WATCH_LIST, "[]") ?: "[]")?.toMutableList()
            }
            models?.find { it.id == key } != null
        }
    }

    override suspend fun getAllWatchListItems(): List<WatchListItem> {
        return withContext(ioDispatcher) {
            val items = mutableListOf<WatchListItem>()
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            if (sp.contains(KEY_WATCH_LIST)) {
                val models =
                    adapter.fromJson(sp.getString(KEY_WATCH_LIST, "[]") ?: "[]")
                models?.forEach {
                    items.add(WatchListItem(it.id, it.title, it.posterPath, it.mediaType.uppercase()))
                }
            }
            items
        }
    }

    override suspend fun removeFromWatchList(key: Int) {
        return withContext(ioDispatcher) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            val models =
                adapter.fromJson(sp.getString(KEY_WATCH_LIST, "[]") ?: "[]")
            val newItems = models?.filterNot { it.id == key }
            if (newItems != null) {
                val editor = sp.edit()
                editor.putString(KEY_WATCH_LIST, adapter.toJson(newItems))
                editor.commit()
            }
        }
    }

    override suspend fun move(fromPosition: Int, toPosition: Int) {
        withContext(ioDispatcher) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            val models =
                adapter.fromJson(sp.getString(KEY_WATCH_LIST, "[]") ?: "[]")?.toMutableList()
                    ?: return@withContext
            if (fromPosition < toPosition) {
                for (index in fromPosition until toPosition) {
                    Collections.swap(models, index, index + 1)
                }
            } else {
                for (index in fromPosition downTo toPosition + 1) {
                    Collections.swap(models, index, index - 1)
                }
            }
            val editor = sp.edit()
            editor.putString(KEY_WATCH_LIST, adapter.toJson(models))
            editor.apply()
        }
    }


}