package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.utils.Utils

fun Chat.toChatItem(): ChatItem {
    return if (isSingle()) {
        val user = members.first()
        ChatItem(
            id,
            user.avatar,
            Utils.toInitials(user.firstName, user.lastName) ?: "??",
            "${user.firstName ?: ""} ${user.lastName ?: ""}",
            lastMessageShort().first,
            unreadableMessageCount(),
            lastMessageDate()?.shortFormat(),
            user.isOnline
        )
    } else {
        ChatItem(
            id,
            null,
            "",
            title,
            lastMessageShort().first,
            unreadableMessageCount(),
            lastMessageDate()?.shortFormat(),
            false,
            Chat.ChatType.GROUP,
            lastMessageShort().second
        )
    }
}