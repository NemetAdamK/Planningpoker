package com.example.firebasetest

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.security.AccessController.getContext
import java.util.concurrent.TimeUnit
import kotlin.time.seconds
import com.google.firebase.FirebaseError
import com.google.firebase.database.*


class QuestionAdapter(val userlist:ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userlist.size
           }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question: Question = userlist[position]
        holder.buttonStart.setOnClickListener {

            timerData(question)

        }
        holder.textViewQuestion.text = question.question
        if (question.activit == true) {
            holder.textViewActive.text = "Active"
        } else {
            holder.textViewActive.text = "Inactive"
        }
        holder.textViewTime.text = question.seconds.toString()


    }

    private fun updateInFirebaseFinishTimer(question: Question) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomNumberString).orderByKey()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val QuestionResult = ds.getValue(Question::class.java)
                    val questionText = QuestionResult?.question
                    if (questionText==question.question){
                        ds.ref.child("seconds").setValue(0)
                        ds.ref.child("activit").setValue(false)


                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }

    private fun updateInFirebaseStartTimer(question: Question) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomNumberString).orderByKey()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val QuestionResult = ds.getValue(Question::class.java)
                    val questionText = QuestionResult?.question
                    if (questionText==question.question){
                        ds.ref.child("activit").setValue(true)
                        ds.ref.child("seconds").setValue(question.seconds)

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }

    private fun timerData(question: Question){
        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                question.seconds = ((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)).toInt())
                updateInFirebaseStartTimer(question)
                notifyDataSetChanged()
            }

            override fun onFinish() {
                updateInFirebaseFinishTimer(question)
                notifyDataSetChanged()

            }
        }.start()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewQuestion = itemView.findViewById<TextView>(R.id.textViewQuestion)
        val textViewActive = itemView.findViewById<TextView>(R.id.textViewActive)
        val textViewTime = itemView.findViewById<TextView>(R.id.textViewTime)
        val buttonStart = itemView.findViewById<Button>(R.id.buttonStartTimer)
    }
}