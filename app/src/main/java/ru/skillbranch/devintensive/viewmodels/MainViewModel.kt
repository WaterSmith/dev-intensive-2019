package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.toChatItem
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    fun getChatData() : LiveData<List<ChatItem>> {
        return mutableLiveData(loadChats())
    }

    private val chatRepository = ChatRepository

    fun loadChats(): List<ChatItem> = chatRepository.loadChats().map{
        it.toChatItem()
    }

}