package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_archive.*
import kotlinx.android.synthetic.main.item_chat_group.*
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType

class ChatAdapter(val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {
    var items : List<ChatItem> = listOf()


    override fun getItemViewType(position: Int): Int = items[position].chatType.layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when(viewType){
            ChatType.SINGLE.layoutId -> SingleViewHolder(view)
            ChatType.GROUP.layoutId -> GroupViewHolder(view)
            ChatType.ARCHIVE.layoutId -> ArhiveViewHolder(view)
            else -> TODO("Unsupported chat type:${viewType}")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun updateData(data : List<ChatItem>){
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

    abstract inner class ChatItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun bind(item:ChatItem, listener: (ChatItem) -> Unit)
    }

    abstract inner class TouchableChatItemViewHolder(override val containerView: View) : ChatItemViewHolder(containerView), ItemTouchViewHolder {



        override fun onItemSelected() {
            var colorBackground = TypedValue()
            itemView.context.theme.resolveAttribute(R.attr.colorItemSelectedBackground, colorBackground, true)
            itemView.setBackgroundColor(colorBackground.data)
        }
        override fun onItemCleared() {
            var colorBackground = TypedValue()
            itemView.context.theme.resolveAttribute(R.attr.colorItemBackground, colorBackground, true)
            itemView.setBackgroundColor(colorBackground.data)
        }
    }

    inner class SingleViewHolder(override val containerView: View) : TouchableChatItemViewHolder(containerView){
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

    inner class GroupViewHolder(override val containerView: View) : TouchableChatItemViewHolder(containerView){
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
                text = "@"+item.author
            }
            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }
    inner class ArhiveViewHolder(override val containerView: View): ChatItemViewHolder(containerView){
        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            with(tv_date_archive){
                visibility = if (item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_archive){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }

            tv_message_archive.text = item.shortDescription

            with(tv_message_author_archive){
                visibility = if (item.messageCount>0) View.VISIBLE else View.GONE
                text = "@"+item.author
            }
            itemView.setOnClickListener{
                listener.invoke(item)
            }
        }
    }
}