package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.VoteFragment
import com.example.myapplication.VotedUsers
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialog_add_question.view.*


class MainActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = VotedUsers("k222k", "ss8")
        database = FirebaseDatabase.getInstance().reference
        database.child("UserVotes").child("users").push().setValue(user)



    }


    fun showCreateCategoryDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_question, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Add question")
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog

            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
            val question = mDialogView.dialogQuestion.text.toString()

            if (question.isNotEmpty()){
                database = FirebaseDatabase.getInstance().reference
                database.child("Questions").child("Group").child(roomNumberString).push().setValue(Question(question,false,ArrayList<VotedUsers>(),60)
                )

            }
           else
            {
                Toast.makeText(applicationContext,"Empty field",Toast.LENGTH_SHORT).show()
            }

            mAlertDialog.dismiss()
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }



    }



    fun onAddCliclked(view: View) {
        val fragment = ListFragment()
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        Toast.makeText(applicationContext,"Questions shown",Toast.LENGTH_SHORT).show()
    }

    fun onShowClicked(view: View) {
        val fragment  = VoteFragment()
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onAddQuestionClicked(view: View) {

        showCreateCategoryDialog()

    }
}

