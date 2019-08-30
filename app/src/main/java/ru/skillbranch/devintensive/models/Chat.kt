package ru.skillbranch.devintensive.models

import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.models.data.User
import java.util.*

class Chat (
    val id:String,
    val title: String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    fun unreadableMessageCount(): Int {
        //TODO implement me
        return messages.count { it.isReaded.not() }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        //TODO implement me
        return Date()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String, String?> = when(val lastMessage = messages.lastOrNull()){
        is TextMessage -> Pair(lastMessage.text?:"", lastMessage.from.firstName)
        is ImageMessage -> Pair(lastMessage.image, lastMessage.from.firstName)
        else -> Pair("",null)
    }

    fun isSingle(): Boolean = members.size == 1

    enum class ChatType{
        SINGLE,
        GROUP,
        ARCHIVE
    }
}