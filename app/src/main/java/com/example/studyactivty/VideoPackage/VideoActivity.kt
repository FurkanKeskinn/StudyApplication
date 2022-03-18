package com.example.studyactivty.VideoPackage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyactivty.databinding.ActivityVideoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VideoActivity : AppCompatActivity() {
    private var _binding: ActivityVideoBinding? = null
    private val binding get() = _binding!!

    lateinit var context: Context

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter: VideoRecyclerAdapter

    var videoList = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()


        getData()

        var layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVideo.layoutManager = layoutManager
        recyclerViewAdapter = VideoRecyclerAdapter(videoList)
        binding.recyclerViewVideo.adapter = recyclerViewAdapter
    }

    fun getData() {

        database.collection("Videos").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{
            snapshot, exception ->

            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{

                if (snapshot != null){
                    if (!snapshot.isEmpty){
                        val documents = snapshot.documents

                        videoList.clear()

                        for (document in documents){

                            val userEmail = document.get("userEmail") as String
                            val videoName = document.get("VideoName") as String
                            val videoUrl = document.get("VideoUrl") as String

                            val downloadVideo = Video(userEmail,videoName,videoUrl)
                            videoList.add(downloadVideo)
                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}