package com.leon.wechat

import android.os.Message

class Message {
    var message: String? = null
    var senderId: String? = null


    constructor(){}


    constructor(message: String?,senderId: String?){
        this.message = message
        this.senderId = senderId



    }

}