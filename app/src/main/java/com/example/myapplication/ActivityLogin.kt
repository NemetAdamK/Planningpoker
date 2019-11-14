package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.firebasetest.MainActivity
import com.example.firebasetest.roomNumberString
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class ActivityLogin : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Write a message to the database
        val mmdatabase = FirebaseDatabase.getInstance()
        val myRef = mmdatabase.getReference("message")

        myRef.setValue("Hello, World!")

        database = FirebaseDatabase.getInstance().reference
        val nameEdit = findViewById<EditText>(R.id.editTextName)

        val roomEdit = findViewById<EditText>(R.id.editTextRoomId)
        val button = findViewById<Button>(R.id.button)
        database.child("users").setValue(1)
        button.setOnClickListener {

            if (nameEdit.text.isNotEmpty() && roomEdit.text.isNotEmpty()) {
                roomNumberString = roomEdit.text.toString()
                database.child("Questions").child("Group").setValue(roomEdit.text.toString())
               val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Please complete fields",Toast.LENGTH_LONG).show()
            }


        }
    }
}
