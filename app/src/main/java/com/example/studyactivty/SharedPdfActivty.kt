package com.example.studyactivty

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.studyactivty.databinding.ActivitySharedPdfBinding
import com.example.studyactivty.databinding.ActivitySharedVideoActivtyBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class SharedPdfActivty : AppCompatActivity() {

    private var _binding: ActivitySharedPdfBinding? = null
    private val binding get() = _binding!!

    var selectedItem: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySharedPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

    }

    fun Shared(view: View) {

        val uuid = UUID.randomUUID()
        val pdfNames = "${uuid}.pdf"
        val reference = storage.reference
        val pdfReference = reference.child("PDFCollections").child(pdfNames)


        if (selectedItem != null) {
            pdfReference.putFile(selectedItem!!).addOnSuccessListener { taskSnapshot ->
                val uploadPdfReference =
                    FirebaseStorage.getInstance().reference.child("PDFCollections").child(pdfNames)
                uploadPdfReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()

                    val currentuserEmail = auth.currentUser!!.email.toString()
                    val pdfName = binding.pdfName.text.toString()  //kullanıcı pdf ismi alma işlemi
                    val date = Timestamp.now()

                    //veri tabanı işlemleri

                    val pdfHashMap = hashMapOf<String, Any>()
                    pdfHashMap.put("pdfUrl", downloadUrl)
                    pdfHashMap.put("PdfName", pdfName)
                    pdfHashMap.put("userEmail", currentuserEmail)
                    pdfHashMap.put("date", date)


                    database.collection("PDFCollections").add(pdfHashMap)
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
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    fun selectedPdf(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )

        } else {
            //izin zaten varsa
            val storageIntent = Intent(Intent.ACTION_GET_CONTENT)
            storageIntent.type = "application/pdf"
            storageIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(storageIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //izin verilince yapılacaklar
                val storageIntent = Intent(Intent.ACTION_GET_CONTENT)
                storageIntent.type = "application/pdf"
                storageIntent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(storageIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedItem = data.data

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}