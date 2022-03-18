package com.example.studyactivty.PdfPackage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PDF(var userEmail: String, var pdfName: String, var pdfUrl: String) : Parcelable