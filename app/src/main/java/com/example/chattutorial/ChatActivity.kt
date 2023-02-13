package com.example.chattutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chattutorial.databinding.ActivityChatBinding
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding

    private lateinit var mSocket : Socket
    private lateinit var userName: String
    private lateinit var roomNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        init()
    }

    private fun init(): Unit{
        try{
            mSocket = IO.socket("http://10.0.2.2:8000")
            Log.d("Connected", "port : 8000")
        }catch (e: URISyntaxException){
            Log.d("error", e.toString())
        }

        userName = intent.getStringExtra("user_name").toString()
        roomNumber = intent.getStringExtra("room_number").toString()
        
        mSocket.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 소켓해제
        mSocket.disconnect()
    }
}
