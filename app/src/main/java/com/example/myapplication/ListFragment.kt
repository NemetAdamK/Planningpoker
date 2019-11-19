package com.example.firebasetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.os.AsyncTask



class ListFragment: Fragment() {

    override fun onStart() {
        super.onStart()

        var recyclerView = view?.findViewById<RecyclerView>(R.id.listRecyclerView)
        val questions = ArrayList<Question>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomNumberString).orderByKey()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                questions.clear()
                for (ds in dataSnapshot.children) {
                    val QuestionResult = ds.getValue(Question::class.java)
                    val questionText = QuestionResult?.question
                    val questionActivity = QuestionResult?.activit
                    val questionTime = QuestionResult?.seconds
                    val questionUsers = QuestionResult?.votes

                    questions.add(Question(questionText, questionActivity,questionUsers, questionTime))

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        val listAdapter = QuestionAdapter(questions);
        recyclerView?.adapter = listAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list,container,false)
        var recyclerView =view.findViewById<RecyclerView>(R.id.listRecyclerView)
        val questions = ArrayList<Question>()


        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Questions").child("Group").child(roomNumberString).orderByKey()

        val listAdapter = QuestionAdapter(questions)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    questions.clear()
                    for (ds in dataSnapshot.children) {
                        val QuestionResult = ds.getValue(Question::class.java)
                        val questionText = QuestionResult?.question
                        val questionActivity = QuestionResult?.activit
                        val questionTime = QuestionResult?.seconds
                        val questionUsers = QuestionResult?.votes

                        questions.add(Question(questionText, questionActivity,questionUsers, questionTime))
                    }
                recyclerView.adapter = listAdapter

                }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })


        return view
    }


}



