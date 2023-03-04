package com.duv.myapplication.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.duv.myapplication.R
import com.duv.myapplication.databinding.ActivityTelaPrincipalBinding
import com.duv.myapplication.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityTelaPrincipalBinding
    private val db  = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }
        binding.btnGravarDadosDb.setOnClickListener {
            val usuarioMap = hashMapOf(
                "nome" to "Marcos",
                "sobremnome" to "Duart",
                "idade" to 28
            )
            db.collection("Usuários").document("Marcos")
                .set(usuarioMap).addOnCompleteListener { task ->
                    Log.d("db", "Sucesso ao salvar dados")
                }.addOnFailureListener {

                }
        }

        binding.btnLerDadosDb.setOnClickListener {
            db.collection("Usuários").document("Eduardo")
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        binding.txtResultado.text = value.getString("sobrenome")
                    }
                }
        }

        binding.btnAtualizarDadosDb.setOnClickListener {
            db.collection("Usuários").document("Eduardo").update("sobrenome", "Vinicius Cabrera")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showResultado( binding.txtResultado)
                    }
                }
        }

        binding.btnDeletarDadosDb.setOnClickListener {
            db.collection("Usuários").document("Marcos").delete().addOnCompleteListener {
                Log.d("db_delete", "Sucesso ao deletar dados")
            }
        }
    }

    private fun showResultado(text:TextView){
        db.collection("Usuários").document("Eduardo")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    text.text = value.getString("sobrenome")
                }
            }
    }
}