package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.toChatItem
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val chats = mutableLiveData((loadChats()))


    fun getChatData() : LiveData<List<ChatItem>> {
        return chats
    }

    fun loadChats(): List<ChatItem> = chatRepository.loadChats().map{
        it.toChatItem()
    }.sortedBy { it.id.toInt() }

    fun addItems() {
        val newItems = DataGenerator.generateChatsWithOffset(chats.value!!.size, 5).map { it.toChatItem() }
        val copy = chats.value!!.toMutableList()
        copy.addAll(newItems)
        chats.value = copy.sortedBy { it.id.toInt() }
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArhived = true))

    }
    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArhived = false))

    }
}