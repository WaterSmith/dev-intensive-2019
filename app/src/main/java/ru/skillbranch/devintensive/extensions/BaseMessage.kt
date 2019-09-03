package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

object BaseMessageAbstractFactory {
    var lastId = -1
    fun makeMessage(from: User, chat: Chat, date: Date = Date(), type: String = "text", payload: Any?):BaseMessage {
        return when (type) {
            "image" -> ImageMessage("${++lastId}", from, chat, date = date,  image = payload as String)
            else -> TextMessage("${++lastId}", from, chat, date = date, text = payload as String)
        }
    }
}

fun BaseMessage.formatMessage(): String = when(this){
        is ImageMessage -> "id:$id ${this.from.firstName} " +
                "${if (isIncoming) "получил" else "отправил"} изображение \"$image\" ${date.humanizeDiff()}"
        is TextMessage -> "id:$id ${this.from.firstName} " +
                    "${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
        else -> "id:$id ${this.from.firstName} " +
                "${if (isIncoming) "получил" else "отправил"} не поддерживаемый тип сообщения ${date.humanizeDiff()}"
    }