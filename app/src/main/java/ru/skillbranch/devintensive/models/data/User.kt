package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User (
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    val isOnline: Boolean = false
){
    fun toUserItem(): UserItem {
        val lastActivity = when {
                lastVisit == null -> "Еще ни разу не заходил"
                isOnline -> "Онлайн"
                else -> "Последний раз был ${lastVisit.humanizeDiff()}"
            }
        return UserItem(
            id,
            "${firstName.orEmpty()} ${lastName.orEmpty()}",
            Utils.toInitials(firstName, lastName).orEmpty(),
            avatar,
            lastActivity,
            false,
            isOnline
        )
    }

    constructor(id: String, firstName: String?, lastName: String?): this (
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null)
    constructor(id: String) : this (id, "John", "Doe")

    companion object Factory{
        private var lastId = -1
        fun makeUser(fullName:String?) : User {
            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(
                "${takeNextId()}",
                firstName,
                lastName
            )
        }

        fun takeNextId() = "${++lastId}"
    }

    class Builder() {
        private var id: String? = null
        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date? = null
        private var isOnline: Boolean = false

        fun id(id: String): Builder {
            this.id = id
            return this
        }

        fun firstName(firstName: String?): Builder {
            this.firstName = firstName
            return this
        }

        fun lastName(lastName: String?): Builder {
            this.lastName = lastName
            return this
        }

        fun avatar(avatar: String?): Builder {
            this.avatar = avatar
            return this
        }

        fun rating(rating: Int): Builder {
            this.rating = rating
            return this
        }

        fun respect(respect: Int): Builder {
            this.respect = respect
            return this
        }

        fun lastVisit(lastVisit: Date?): Builder {
            this.lastVisit = lastVisit
            return this
        }

        fun isOnline(isOnline: Boolean): Builder {
            this.isOnline = isOnline
            return this
        }

        fun build(): User =
            User(
                id = this.id ?: takeNextId(),
                firstName = this.firstName,
                lastName = this.lastName,
                avatar = this.avatar,
                rating = this.rating,
                respect = this.respect,
                lastVisit = this.lastVisit,
                isOnline = this.isOnline
            )
    }
}