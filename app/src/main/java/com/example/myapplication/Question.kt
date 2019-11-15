package com.example.firebasetest

import android.widget.Chronometer
import java.sql.Timestamp

class Question (val question:String?,var activit: Boolean?,val votes:ArrayList<VotedUsers>? ,var seconds: Int?){

    constructor() : this("str",false,ArrayList<VotedUsers>(),60)
}