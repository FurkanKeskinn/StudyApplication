package com.example.studyactivty.VideoPackage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyactivty.R
import com.example.studyactivty.databinding.RecylerVideoRowBinding

class VideoRecyclerAdapter(private val videoList: ArrayList<Video>) : RecyclerView.Adapter<VideoRecyclerAdapter.VideoHolder>(){
    class VideoHolder(val binding : RecylerVideoRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val binding = RecylerVideoRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoRecyclerAdapter.VideoHolder, position: Int) {

        holder.binding.videoViewRow.setImageResource(R.drawable.play)
        holder.binding.textViewVideoRow.text = videoList[position].videoName

        holder.binding.cardViewVideoRow.setOnClickListener {

            holder.itemView.context.startActivity(VideoViewActivity.newIntent(holder.itemView.context,videoList[position]))
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}