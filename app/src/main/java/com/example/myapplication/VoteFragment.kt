package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("UserVotes").child("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                voters.clear()
                for (ds in dataSnapshot.children) {
                    val votes = ds.getValue(VotedUsers::class.java)
                    val VotedName = votes?.user
                    val VotedNumber = votes?.voted
                    voters.add(VotedUsers(VotedName, VotedNumber))

                    val listAdapter = VoterAdapter(voters);
                    recyclerView.adapter = listAdapter
                    val layoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = layoutManager
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Not success",Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

}
