package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.VoteFragment
import com.example.myapplication.VotedUsers
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_question.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    val manager = supportFragmentManager
    val rooms = ArrayList<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference

        setSpinner(spinnerRooms)

        val fragment  = ListFragment()
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

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


    fun showCreateCategoryDialogForGroupAdd() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_question, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Add group")
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


                database.child("Questions").child("Group").push().setValue(question)
                database.child("GrupsForSpinner").push().setValue(question)
                roomNumberString=question


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

    fun addGroupClicked(view: View) {
        showCreateCategoryDialogForGroupAdd()
    }
    fun setSpinner(spinner: Spinner){

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            rooms
        )
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        database = FirebaseDatabase.getInstance().reference
        database.child("GroupsForSpinner").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                rooms.clear()
                for (ds in dataSnapshot.children) {
                    if (ds.value is String){
                        rooms.add(ds.value.toString())
                        spinner!!.setAdapter(aa)
                    }




                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })



        spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                Toast.makeText( applicationContext,"Selected room : "+ rooms[position],Toast.LENGTH_SHORT).show()
                roomNumberString=rooms[position]
                val fragment  = ListFragment()
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment_holder, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
    }
}

