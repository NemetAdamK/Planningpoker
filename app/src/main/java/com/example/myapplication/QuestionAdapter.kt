package com.example.firebasetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

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
        holder.textViewQuestion.text = question.question
        if (question.activit == true) {
            holder.textViewActive.text = "Active"
        } else {
            holder.textViewActive.text = "Inactive"
        }
        holder.textViewTime.text = question.seconds.toString()
        if (question.votes.toString().equals("[]")) {
            holder.textViewUsers.text = "No votes yet"
        } else {
            holder.textViewUsers.text = question.votes.toString()
        }
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewQuestion = itemView.findViewById<TextView>(R.id.textViewQuestion)
        val textViewActive = itemView.findViewById<TextView>(R.id.textViewActive)
        val textViewUsers = itemView.findViewById<TextView>(R.id.textViewVoted)
        val textViewTime = itemView.findViewById<TextView>(R.id.textViewTime)
    }
}