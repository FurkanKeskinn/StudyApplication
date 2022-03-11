package com.example.studyactivty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.studyactivty.databinding.ActivtyLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivty : AppCompatActivity() {
    private var _binding: ActivtyLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_login)
        _binding = ActivtyLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        if(currentUser != null){
            val intent = Intent(this, PdfActivty::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun btnRegister(view: View){
        var email =binding.edtRegisterEmail.text.toString()
        var password = binding.edtRegisterPassword.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task ->

            //asenkron
            if (task.isSuccessful){

                //diğer aktiviteye git
                val intent = Intent(this, PdfActivty::class.java)
                startActivity(intent)
                finish()
            }
        } .addOnFailureListener { exception ->

            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()

        }
    }

    fun btnLogin(view: View){

        auth.signInWithEmailAndPassword(binding.edtRegisterEmail.text.toString(),binding.edtRegisterPassword.text.toString())
            .addOnCompleteListener{
                task ->

                if (task.isSuccessful){

                    val currentuser = auth.currentUser?.email.toString()
                    Toast.makeText(this, "Hoşgeldin: ${currentuser}",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PdfActivty::class.java)
                    startActivity(intent)
                    finish()

                }
            }.addOnFailureListener{ exception ->

                Toast.makeText(this, exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
    }
}