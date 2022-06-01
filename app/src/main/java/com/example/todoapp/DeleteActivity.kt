package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        var args = intent.extras
        val yes: Button = findViewById(R.id.yes)
        val no: Button = findViewById(R.id.no)

        yes.setOnClickListener{
            val intent = Intent()
            intent.putExtra("delete", "true")

            if (args != null) {
                intent.putExtra("uid", args.getInt("id"))
                Log.d("DebugTag", "Delete submitted ${args.getInt("id")}")
            }
            setResult(3, intent)
            finish()
        }

        no.setOnClickListener{
            Log.d("DebugTag", "Delete not submitted")
            finish()
        }
    }
}