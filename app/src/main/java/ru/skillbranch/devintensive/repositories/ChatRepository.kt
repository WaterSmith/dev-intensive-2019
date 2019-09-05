package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()

    var unreadableArchiveMessageCount = mutableLiveData(0)
    var lastArchiveMessage = MutableLiveData<BaseMessage>()

    fun loadChats(): MutableLiveData<List<Chat>> = chats

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chat.id }
        if (ind == -1) return

        val isArhiveChanged = copy[ind].isArchived != chat.isArchived

        copy[ind] = chat

        if (isArhiveChanged) {
            updateArchiveData(copy)
        }

        chats.value = copy
    }

    private fun updateArchiveData(copy : MutableList<Chat>) {
        var hasArhived = false
        var unreadableMessageCount = 0
        var lastMessage:BaseMessage? = null
        var lastMessageTime:Long = 0

        copy.filter { it.isArchived }.forEach {
            hasArhived = true
            it.messages.forEach{
                if (!it.isReaded) unreadableMessageCount++
                if (it.date.time>lastMessageTime) {
                    lastMessage = it
                    lastMessageTime = it.date.time
                }
            }
        }

        val ind = copy.indexOfFirst { it.id == "-1" }

        lastArchiveMessage.value = lastMessage
        unreadableArchiveMessageCount.value = unreadableMessageCount


        if (hasArhived) {
            val archive = Chat("-1", "Архив чатов")
            if (ind == -1) copy.add(0,archive) else copy[ind] = archive
        } else {
            if (ind > -1) {
                copy.removeAt(ind)
            }
        }
    }

    fun find(chatId: String): Chat? {
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }

}