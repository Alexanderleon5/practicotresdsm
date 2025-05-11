package sv.edu.udb.practicotresdsm.ui.recurso

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.edu.udb.practicotresdsm.R
import sv.edu.udb.practicotresdsm.api.RetrofitClient
import sv.edu.udb.practicotresdsm.model.Recurso

class AgregarRecursoActivity : AppCompatActivity() {

    private lateinit var titulo: EditText
    private lateinit var descripcion: EditText
    private lateinit var tipo: EditText
    private lateinit var enlace: EditText
    private lateinit var imagen: EditText
    private lateinit var guardarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_recurso)

        titulo = findViewById(R.id.inputTitulo)
        descripcion = findViewById(R.id.inputDescripcion)
        tipo = findViewById(R.id.inputTipo)
        enlace = findViewById(R.id.inputEnlace)
        imagen = findViewById(R.id.inputImagen)
        guardarBtn = findViewById(R.id.btnGuardar)
        guardarBtn.setOnClickListener {
            val tituloText = titulo.text.toString()
            val descripcionText = descripcion.text.toString()
            val tipoText = tipo.text.toString()
            val enlaceText = enlace.text.toString()
            val imagenText = imagen.text.toString()
            if (tituloText.isEmpty() || descripcionText.isEmpty() || tipoText.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val recurso = Recurso(
                titulo = tituloText,
                descripcion = descripcionText,
                tipo = tipoText,
                enlace = enlaceText,
                imagen = imagenText
            )
            RetrofitClient.apiService.addRecurso(recurso).enqueue(object : Callback<Recurso> {
                override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AgregarRecursoActivity, "Recurso guardado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AgregarRecursoActivity, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Recurso>, t: Throwable) {
                    Toast.makeText(this@AgregarRecursoActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
