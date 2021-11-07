package com.example.municipalityevents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username = findViewById<TextView>(R.id.username);
        var password = findViewById<TextView>(R.id.password);
        var loginbtn = findViewById<MaterialButton>(R.id.loginButton);

        //Hardcoded login

        loginbtn.setOnClickListener(View.OnClickListener {
            if (username.text.toString().equals("admin") && password.text.toString().equals("admin")){
                Toast.makeText(this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
            }

        })

    }
}