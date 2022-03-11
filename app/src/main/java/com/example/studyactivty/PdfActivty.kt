package com.example.studyactivty

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyactivty.databinding.ActivityPdfBinding
import com.example.studyactivty.databinding.ActivtyLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PdfActivty : AppCompatActivity() {

    private var _binding: ActivityPdfBinding? = null
    private val binding get() = _binding!!

    lateinit var context: Context

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter: PdfRecyclerAdapter

    var pdfList = ArrayList<PDF>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        getData()

        var layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = PdfRecyclerAdapter(pdfList)
        binding.recyclerView.adapter = recyclerViewAdapter

    }

    fun getData(){
        database.collection("PDFCollections")
            .orderBy("date", Query.Direction.DESCENDING)

            .addSnapshotListener {
                snapshot,exception->

            if(exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
            else{

                if (snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents

                        pdfList.clear()
                        for (document in documents){

                            //her bir dökümanı bu işlemlerle alıyoruz (pdf altındaki uIdiler)

                            val userEmail = document.get("userEmail") as String
                            val pdfName = document.get("PdfName") as String
                            val pdfUrl = document.get("pdfUrl") as String

                            val downloadPdf = PDF(userEmail,pdfName,pdfUrl)
                            pdfList.add(downloadPdf)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.shared_pdf){
            val intent = Intent(this,SharedPdfActivty::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.sign_out){
            auth.signOut()
            val intent = Intent(this,HomePageActivtiy::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}