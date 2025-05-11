    package sv.edu.udb.practicotresdsm.ui.recurso

    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response
    import sv.edu.udb.practicotresdsm.R
    import sv.edu.udb.practicotresdsm.api.RetrofitClient
    import sv.edu.udb.practicotresdsm.model.Recurso

    class EditarRecursoActivity : AppCompatActivity() {
        private lateinit var editTitulo: EditText
        private lateinit var editDescripcion: EditText
        private lateinit var editTipo: EditText
        private lateinit var editEnlace: EditText
        private lateinit var editImagen: EditText
        private lateinit var buttonGuardarCambios: Button
        private lateinit var buttonEliminar: Button
        private var recursoId: String? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_editar_recurso)

            editTitulo = findViewById(R.id.editTextTituloEdit)
            editDescripcion = findViewById(R.id.editTextDescripcionEdit)
            editTipo = findViewById(R.id.editTextTipoEdit)
            editEnlace = findViewById(R.id.editTextEnlaceEdit)
            editImagen = findViewById(R.id.editTextImagenEdit)
            buttonGuardarCambios = findViewById(R.id.buttonGuardarCambios)
            buttonEliminar = findViewById(R.id.buttonEliminar)
            recursoId = intent.getStringExtra("recurso_id")
            editTitulo.setText(intent.getStringExtra("titulo"))
            editDescripcion.setText(intent.getStringExtra("descripcion"))
            editTipo.setText(intent.getStringExtra("tipo"))
            editEnlace.setText(intent.getStringExtra("enlace"))
            editImagen.setText(intent.getStringExtra("imagen"))
            buttonGuardarCambios.setOnClickListener {
                val titulo = editTitulo.text.toString().trim()
                val descripcion = editDescripcion.text.toString().trim()
                val tipo = editTipo.text.toString().trim()
                val enlace = editEnlace.text.toString().trim()
                val imagen = editImagen.text.toString().trim()

                if (titulo.isEmpty() || descripcion.isEmpty() || tipo.isEmpty()) {
                    Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val recurso = Recurso(
                    id = recursoId,
                    titulo = titulo,
                    descripcion = descripcion,
                    tipo = tipo,
                    enlace = enlace,
                    imagen = imagen
                )
                recursoId?.let { id ->
                    RetrofitClient.apiService.updateRecurso(id, recurso).enqueue(object : Callback<Recurso> {
                        override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@EditarRecursoActivity, "Recurso actualizado", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@EditarRecursoActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Recurso>, t: Throwable) {
                            Toast.makeText(this@EditarRecursoActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
            buttonEliminar.setOnClickListener {
                recursoId?.let { id ->
                    RetrofitClient.apiService.deleteRecurso(id).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@EditarRecursoActivity, "Recurso eliminado", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@EditarRecursoActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@EditarRecursoActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
    }
