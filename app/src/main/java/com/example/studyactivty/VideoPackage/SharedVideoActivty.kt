package com.example.studyactivty.VideoPackage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.studyactivty.databinding.ActivitySharedVideoActivtyBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_shared_pdf.*
import kotlinx.android.synthetic.main.activity_shared_video_activty.*
import java.util.*

class SharedVideoActivty : AppCompatActivity() {

    private var _binding: ActivitySharedVideoActivtyBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySharedVideoActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()


    }

    fun SharedVideo(view: View){

        val reference = storage.reference
        val videoReference = reference.child("Videos")


//devamı var
        if(videoUrl != null){

            val downloadUrl = binding.videoUrl.text.toString()
            val currentuserEmail = auth.currentUser!!.email.toString()
            val videoName = binding.videoName.text.toString()  //kullanıcı pdf ismi alma işlemi
            val date = Timestamp.now()

            //veri tabanı işlemleri

            val pdfHashMap = hashMapOf<String, Any>()
            pdfHashMap.put("VideoUrl", downloadUrl)
            pdfHashMap.put("VideoName", videoName)
            pdfHashMap.put("userEmail", currentuserEmail)
            pdfHashMap.put("date", date)


            database.collection("Videos").add(pdfHashMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }
        }
    }
}