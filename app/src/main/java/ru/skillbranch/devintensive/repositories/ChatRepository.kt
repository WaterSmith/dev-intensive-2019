package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats():MutableLiveData<List<Chat>> = chats

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chat.id }

        if (ind == -1) return

        copy[ind] = chat
        chats.value = copy
    }

    fun find(chatId: String): Chat? {
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }
}