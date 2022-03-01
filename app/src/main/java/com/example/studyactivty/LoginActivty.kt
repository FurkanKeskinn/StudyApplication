package com.example.studyactivty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_activty.*

class LoginActivty : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activty)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this, PdfActivty::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun btnRegister(view: View){
        var email = edt_register_Email.text.toString()
        var password = edt_register_Password.text.toString()

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

        auth.signInWithEmailAndPassword(edt_register_Email.text.toString(),edt_register_Password.text.toString())
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