package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {
    val chats = mutableLiveData(DataGenerator.stabChats)

    fun loadChats():MutableLiveData<List<Chat>> {
        return chats
    }
}