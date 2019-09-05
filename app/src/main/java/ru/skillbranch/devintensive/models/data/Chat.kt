package ru.skillbranch.devintensive.models.data

import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
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
        return messages.count { !it.isReaded }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        return when(val lastMessage = messages.lastOrNull()){
            null -> null
            else -> lastMessage.date
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String, String?> = when(val lastMessage = messages.lastOrNull()){
        is TextMessage -> Pair(lastMessage.text?:"", lastMessage.from.firstName)
        is ImageMessage -> Pair(lastMessage.image, lastMessage.from.firstName)
        else -> Pair("",null)
    }

    fun isSingle(): Boolean = members.size == 1
}