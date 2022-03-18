package com.example.studyactivty.VideoPackage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Video(var userEmail: String, var videoName: String, var videoUrl: String) :Parcelable{
}