package com.example.myapplication

class VotedUsers(val name: String?, var vote: String?,var questionGroup: String?,var questionString: String?) {
    constructor() : this("No one","0","no question","no group")
}
