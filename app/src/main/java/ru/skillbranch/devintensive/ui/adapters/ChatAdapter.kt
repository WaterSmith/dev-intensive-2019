package ru.skillbranch.devintensive.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatAdapter(val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ChatAdapter.SingleViewHolder>() {
    var items : List<ChatItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        Log.d("M_ChatAdapter","onCreateViewHolder")
        return SingleViewHolder(inflator.inflate(R.layout.item_chat_single, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(items[position], listener)
        Log.d("M_ChatAdapter","onBindViewHolder")
    }

    fun updateData(data : List<ChatItem>){
        items = data
        notifyDataSetChanged()
    }

    inner class SingleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item:ChatItem, listener: (ChatItem) -> Unit){
            if (item.avatar==null) {
                iv_avatar_single.setText(item.initials)
            } else {
                //TODO set avatar drawable
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
}