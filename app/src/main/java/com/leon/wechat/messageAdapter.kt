package com.leon.wechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.leon.wechat.UserAdapter.UserViewHolder

class messageAdapter(val context : Context,val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1;
    val ITEM_SEND = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieve, parent, false)
            return RecieveViewHolder(view)

        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SendViewHolder(view)

        }


    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]


        if(holder.javaClass == SendViewHolder::class.java){


            val viewHolder = holder as SendViewHolder

            holder.sendMessage.text = currentMessage.message



        }else{
            val viewHolder = holder as RecieveViewHolder
            holder.recieveMessage.text = currentMessage.message

        }



    }
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SEND
        }else{
            return ITEM_RECIEVE
        }
    }

    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sendMessage = itemView.findViewById<TextView>(R.id.sent_message)
    }

    class RecieveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recieveMessage = itemView.findViewById<TextView>(R.id.recieve_message)
    }
}