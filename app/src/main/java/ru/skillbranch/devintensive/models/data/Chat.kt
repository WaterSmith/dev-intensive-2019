package ru.skillbranch.devintensive.models.data

import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.repositories.ChatRepository
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun unreadableMessageCount(): Int {

        return if (id=="-1") ChatRepository.unreadableArchiveMessageCount.value?:0 else messages.count { !it.isReaded }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        return when(val lastMessage = getLastMessage()){
            null -> null
            else -> lastMessage.date
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String, String?> {
        return when (val lastMessage = getLastMessage()) {
            is TextMessage -> Pair(lastMessage.text ?: "", lastMessage.from.firstName)
            is ImageMessage -> Pair("${lastMessage.from.firstName} - отправил фото", lastMessage.from.firstName)
            else -> Pair("", null)
        }
    }

    private fun getLastMessage():BaseMessage? = if (id == "-1") ChatRepository.lastArchiveMessage.value else messages.lastOrNull()

    fun isSingle(): Boolean = members.size == 1
}