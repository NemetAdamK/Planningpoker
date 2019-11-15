package com.example.firebasetest

import com.example.myapplication.VotedUsers

class Question (val question:String?, var activit: Boolean?, val votes:ArrayList<VotedUsers>?, var seconds: Int?){

    constructor() : this("str",false,ArrayList<VotedUsers>(),60)
}