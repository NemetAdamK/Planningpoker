package com.example.firebasetest

import android.os.Parcel
import android.os.Parcelable
import com.example.myapplication.VotedUsers

class Question (val question:String?, var activit: Boolean?, val votes:ArrayList<VotedUsers>?, var seconds: Int?) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        TODO("votes"),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    constructor() : this("str",false,ArrayList<VotedUsers>(),60)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeValue(activit)
        parcel.writeValue(seconds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}