package com.example.studyactivty.PdfPackage


import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.studyactivty.databinding.ActivityPdfViewBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class PdfViewActivity : AppCompatActivity() {

    private var _binding: ActivityPdfViewBinding? = null
    private val binding get() = _binding!!

    lateinit var pdfView: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfView = binding.pdfView

        (intent?.getParcelableExtra<Parcelable>(BUNDLE_KEY_ITEM) as PDF?)?.let {
            GetPDFFromUrl().execute(it.pdfUrl)
        }
    }

    companion object {
        const val BUNDLE_KEY_ITEM = "bundle.key.item"
        fun newIntent(context: Context, pdf: PDF) = Intent(context, PdfViewActivity::class.java).apply {
            putExtra(BUNDLE_KEY_ITEM,pdf)
        }
    }

    inner class GetPDFFromUrl : AsyncTask<String?, Void?, InputStream?>() {
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
            pdfView.fromStream(inputStream).load()
        }
    }
}