package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
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
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatAdapter(val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {
    var items : List<ChatItem> = listOf()

    override fun getItemViewType(position: Int): Int = items[position].chatType.intValue

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        Log.d("M_ChatAdapter","onCreateViewHolder")
        //return SingleViewHolder(inflator.inflate(R.layout.item_chat_single, parent, false))
        return when(viewType){
            Chat.ChatType.SINGLE.intValue -> SingleViewHolder(inflator.inflate(R.layout.item_chat_single, parent, false))
            Chat.ChatType.GROUP.intValue -> GroupViewHolder(inflator.inflate(R.layout.item_chat_group, parent, false))
            else -> SingleViewHolder(inflator.inflate(R.layout.item_chat_single, parent, false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
        Log.d("M_ChatAdapter","onBindViewHolder")
    }

    fun updateData(data : List<ChatItem>){
        Log.d("M_ChatAdapter","update data adapter new data ${data.size} hash ${data.hashCode()}" +
                 " old data ${items.size} hash ${items.hashCode()}")

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

    abstract inner class ChatItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer, ItemTouchViewHolder {
        override fun onItemSelected() = itemView.setBackgroundColor(Color.LTGRAY)

        override fun onItemCleared() = itemView.setBackgroundColor(Color.WHITE)

        abstract fun bind(item:ChatItem, listener: (ChatItem) -> Unit)
    }

    inner class SingleViewHolder(override val containerView: View) : ChatItemViewHolder(containerView){
        override fun bind(item:ChatItem, listener: (ChatItem) -> Unit){
            if (item.avatar==null) {
                Glide.with(itemView)
                    .clear(iv_avatar_single)
                iv_avatar_single.setImageDrawable(containerView.resources.getDrawable(R.color.color_accent, containerView.context.theme))
                iv_avatar_single.setText(item.initials)
            } else {
                iv_avatar_single.setText(null)
                Glide.with(itemView)
                    .load(item.avatar)
                    .into(iv_avatar_single)

            }

            sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE

            with(tv_date_single){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_single){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }

            tv_title_single.text = item.title
            tv_message_single.text = item.shortDescription
            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }

    inner class GroupViewHolder(override val containerView: View) : ChatItemViewHolder(containerView){
        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            iv_avatar_group.setText(item.title[0].toString())
            //sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE

            with(tv_date_group){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_group){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }

            tv_title_group.text = item.title
            tv_message_group.text = item.shortDescription

            with(tv_message_author){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.author
            }
            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }
}