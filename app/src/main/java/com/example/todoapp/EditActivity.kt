package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        var editT: EditText = findViewById(R.id.edittext)
        var editD: EditText = findViewById(R.id.editdate)
        val ok: Button = findViewById(R.id.ok)
        val neok: Button = findViewById(R.id.neok)

        var args = intent.extras
        if (args != null) {
            editT.setText(args.getString("text"))
            editD.setText(args.getString("date"))
        }

        ok.setOnClickListener{
            val intent = Intent()
            intent.putExtra("text", editT.text.toString())
            intent.putExtra("date", editD.text.toString())
            if (args != null) {
                intent.putExtra("uid", args.getInt("id"))
            }
            setResult(2, intent)
            finish()
        }

        neok.setOnClickListener{
            finish()
        }

    }
}