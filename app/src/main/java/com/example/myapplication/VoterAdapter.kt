package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class VoterAdapter(val userlist:ArrayList<VotedUsers>) : RecyclerView.Adapter<VoterAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.voters_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voter: VotedUsers = userlist[position]

        holder.textViewUser.text = voter.user
        holder.textViewVote.text = voter.voted


    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewUser = itemView.findViewById<TextView>(R.id.textViewVoter)
        val textViewVote = itemView.findViewById<TextView>(R.id.textViewVotedNum)

    }
}