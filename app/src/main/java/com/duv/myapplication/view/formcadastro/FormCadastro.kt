package com.duv.myapplication.view.formcadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.duv.myapplication.R
import com.duv.myapplication.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {
    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        binding.btnCadastro.setOnClickListener { view ->
            val emailTxt = binding.editEmail.text.toString()
            val senhaTxt = binding.editSenha.text.toString()

            if (emailTxt.isEmpty() || senhaTxt.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(emailTxt,senhaTxt).addOnCompleteListener() {
                    cadastro ->
                    if (cadastro.isSuccessful) {
                        val snackbar = Snackbar.make(view, "Sucesso ao cadastrar o usuario", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        binding.editSenha.setText("")
                        binding.editEmail.setText("")
                    }
                }.addOnFailureListener {
                    exception ->

                    val mensagemDeErro = when(exception) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Email invalido"
                        is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar usuario"
                    }
                    val snackbar = Snackbar.make(view, mensagemDeErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
    }
}