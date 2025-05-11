package sv.edu.udb.practicotresdsm.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import sv.edu.udb.practicotresdsm.R
import sv.edu.udb.practicotresdsm.firebase.FirebaseService
import sv.edu.udb.practicotresdsm.ui.auth.LoginActivity
import sv.edu.udb.practicotresdsm.ui.recurso.AgregarRecursoActivity
import sv.edu.udb.practicotresdsm.ui.recurso.ListaRecursosActivity

class MainMenuActivity : AppCompatActivity() {
    private lateinit var btnAgregarRecurso: Button
    private lateinit var btnVerRecursos: Button
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        btnAgregarRecurso = findViewById(R.id.btnAgregarRecurso)
        btnVerRecursos = findViewById(R.id.btnVerRecursos)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        btnAgregarRecurso.setOnClickListener {
            startActivity(Intent(this, AgregarRecursoActivity::class.java))
        }
        btnVerRecursos.setOnClickListener {
            startActivity(Intent(this, ListaRecursosActivity::class.java))
        }
        btnCerrarSesion.setOnClickListener {
            FirebaseService.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
