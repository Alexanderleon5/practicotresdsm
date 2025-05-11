package sv.edu.udb.practicotresdsm.ui.recurso

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sv.edu.udb.practicotresdsm.R
import sv.edu.udb.practicotresdsm.adapter.RecursoAdapter
import sv.edu.udb.practicotresdsm.api.RetrofitClient
import sv.edu.udb.practicotresdsm.model.Recurso

class ListaRecursosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recursoAdapter: RecursoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_recursos)
        recyclerView = findViewById(R.id.recyclerViewRecursos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cargarRecursos()
    }
    private fun cargarRecursos() {
        RetrofitClient.apiService.getRecursos().enqueue(object : Callback<List<Recurso>> {
            override fun onResponse(call: Call<List<Recurso>>, response: Response<List<Recurso>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    recursoAdapter = RecursoAdapter(lista)
                    recyclerView.adapter = recursoAdapter
                } else {
                    Toast.makeText(this@ListaRecursosActivity, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Recurso>>, t: Throwable) {
                Toast.makeText(this@ListaRecursosActivity, "Fallo: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
