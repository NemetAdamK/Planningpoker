package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.roomNumberString
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VoteFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_voters, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.votersRecyclerView)
        val voters = ArrayList<VotedUsers>()

        val args = arguments
        val questionName = args?.getString("Question Name", "0")
        val groupId = args?.getString("Group number", "null")
        if (!questionName.equals("0")&&(!groupId.equals("null"))) {
            Toast.makeText(context, "Votes for : " + questionName, Toast.LENGTH_SHORT).show()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference.child("Answers").child("Group").child(roomNumberString).orderByKey()
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    voters.clear()
                    for (ds in dataSnapshot.children) {

                        val votes = ds.getValue(VotedUsers::class.java)
                        val VotedName = votes?.name
                        val VotedNumber = votes?.vote
                        val VotedQuestion = votes?.questionString
                        val VotedGroup = votes?.questionGroup
                        if (VotedQuestion.equals(questionName)){
                            voters.add(VotedUsers(VotedName, VotedNumber,VotedQuestion,VotedGroup))

                            val listAdapter = VoterAdapter(voters);
                            recyclerView.adapter = listAdapter
                            val layoutManager = LinearLayoutManager(activity)
                            recyclerView.layoutManager = layoutManager
                        }

                    }

                    if (voters.size==0){
                        Toast.makeText(context, "Incoming votes for : " + questionName, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Not success", Toast.LENGTH_SHORT).show()
                }
            })
        } else
        {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }
        return view
    }

}
