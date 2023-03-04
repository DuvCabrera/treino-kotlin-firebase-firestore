package com.duv.myapplication.view.formlogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.duv.myapplication.R
import com.duv.myapplication.databinding.ActivityFormLoginBinding
import com.duv.myapplication.view.formcadastro.FormCadastro
import com.duv.myapplication.view.telaprincipal.TelaPrincipal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {
    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java )
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener { view ->
            val email  = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()


            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener { result ->
                    if(result.isSuccessful) {
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {
                    binding.editSenha.setText("")
                    binding.editEmail.setText("")
                    val snackbar = Snackbar.make(view, "Erro ao fazer Login de usuario", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()

                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            navegarTelaPrincipal()
        }
    }
    private fun navegarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java )
        startActivity(intent)
        finish()
    }
}