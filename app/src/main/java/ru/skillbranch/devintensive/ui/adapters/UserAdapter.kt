package ru.skillbranch.devintensive.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_group.*
import kotlinx.android.synthetic.main.item_chat_single.*
import kotlinx.android.synthetic.main.item_user_list.*
import kotlinx.android.synthetic.main.item_user_list.sv_indicator
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter(val listener: (UserItem) -> Unit):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var items : List<UserItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        return UserViewHolder(inflator.inflate(R.layout.item_user_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun updateData(data : List<UserItem>){
        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = items[oldItemPosition].id == data[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean  = items[oldItemPosition].hashCode() == data[newItemPosition].hashCode()

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(override val containerView: View) :RecyclerView.ViewHolder(containerView), LayoutContainer  {
        fun bind(user:UserItem, listener: (UserItem) -> Unit){
            iv_selected.visibility = if(user.isSelected) View.VISIBLE else View.GONE
            if (user.avatar==null) {
                Glide.with(itemView)
                    .clear(iv_avatar_user)
                iv_avatar_user.setImageDrawable(containerView.resources.getDrawable(R.color.color_accent, containerView.context.theme))
                iv_avatar_user.setText(user.initials)
            } else {
                iv_avatar_user.setText(null)
                Glide.with(itemView)
                    .load(user.avatar)
                    .into(iv_avatar_user)

            }
            sv_indicator.visibility = if (user.isOnline) View.VISIBLE else View.GONE
            tv_user_name.text = user.fullName
            tv_last_activity.text = user.lastActivity
            itemView.setOnClickListener{listener.invoke(user)}
        }
    }
}