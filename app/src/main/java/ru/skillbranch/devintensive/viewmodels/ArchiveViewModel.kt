package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.toChatItem
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class ArchiveViewModel : ViewModel() {
        private val chatRepository = ChatRepository
        private val query = mutableLiveData("")
        private val chats = Transformations.map(chatRepository.loadChats()){ chats ->
            return@map chats.filter { it.isArchived }
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }
        }

        fun getChatData() : LiveData<List<ChatItem>> {
            val result = MediatorLiveData<List<ChatItem>>()
            val filterF = {
                result.value =  if (query.value!!.isEmpty()) chats.value!!
                else chats.value!!.filter { it.title.contains(query.value!!, true) }
            }
            result.addSource(chats){filterF.invoke()}
            result.addSource(query){filterF.invoke()}

            return result
        }

        private fun setArchiveStatus(chatId: String, arhivedStatus: Boolean ){
            val chat = chatRepository.find(chatId)
            chat ?: return
            chatRepository.update(chat.copy(isArchived = arhivedStatus))
        }

        fun addToArchive(chatId: String) {
            setArchiveStatus(chatId, true)
        }

        fun restoreFromArchive(chatId: String) {
            setArchiveStatus(chatId, false)
        }

        fun handleSearchQuery(queryStr: String?) {
            query.value = queryStr.orEmpty()
        }
    }
