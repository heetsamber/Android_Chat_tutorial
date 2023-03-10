package com.example.chattutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chattutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi();
    }

    private fun initUi(): Unit{
        binding.connectBtn.setOnClickListener{
            var intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("username", binding.userName.text.toString())
            intent.putExtra("room_number", binding.userName.text.toString())
            startActivity(intent)
        }
    }
}