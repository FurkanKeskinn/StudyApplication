package com.example.studyactivty.VideoPackage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.AsyncTask.execute
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.example.studyactivty.PdfPackage.PDF
import com.example.studyactivty.PdfPackage.PdfViewActivity
import com.example.studyactivty.R
import com.example.studyactivty.databinding.ActivitySharedVideoActivtyBinding
import com.example.studyactivty.databinding.ActivityVideoBinding
import com.example.studyactivty.databinding.ActivityVideoViewBinding
import kotlinx.android.synthetic.main.activity_shared_video_activty.*
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.activity_video_view.*
import kotlinx.android.synthetic.main.recyler_video_row.*
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class VideoViewActivity : AppCompatActivity() {
    private var _binding: ActivityVideoViewBinding? = null
    private val binding get() = _binding!!

    lateinit var videoView : VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoView = binding.video

        (intent?.getParcelableExtra<Parcelable>(VideoViewActivity.BUNDLE_KEY_ITEM2) as Video?)?.let {
            GetVideoFromUrl().execute(it.videoUrl)
        }
    }

    companion object {
        const val BUNDLE_KEY_ITEM2 = "bundle.key.item2"
        fun newIntent(context: Context, video: Video) = Intent(context, VideoViewActivity::class.java).apply {
            putExtra(BUNDLE_KEY_ITEM2,video)
        }
    }

    inner class GetVideoFromUrl : AsyncTask<String?, Void?, InputStream?>() {
        override fun doInBackground(vararg value: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(value.first())
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream?) {
            videoView.setVideoPath(BUNDLE_KEY_ITEM2)
            videoView.start()
        }
    }

}