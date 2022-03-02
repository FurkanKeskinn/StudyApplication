package com.example.studyactivty


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pdf_view.view.*
import kotlinx.android.synthetic.main.activity_shared_pdf_activty.*
import java.io.File

class PdfViewActivity : AppCompatActivity() {

    lateinit var pdfView: PDFView
    var pdfUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        pdfView = findViewById(R.id.pdfView)

        var intent: Intent = intent

        if (getIntent() != null) {
            pdfUrl = intent.getStringExtra("pdfUrl")!!
            if (pdfUrl != null) {
                Toast.makeText(this, "çalıştı", Toast.LENGTH_LONG).show()
                pdfView.pdfView.fromAsset(pdfUrl)
                    .enableDoubletap(true)
                    .pages(0)
                    .load()
            }
        }

    }

}