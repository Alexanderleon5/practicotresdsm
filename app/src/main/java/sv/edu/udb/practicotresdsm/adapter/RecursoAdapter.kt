package sv.edu.udb.practicotresdsm.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sv.edu.udb.practicotresdsm.R
import sv.edu.udb.practicotresdsm.model.Recurso
import sv.edu.udb.practicotresdsm.ui.recurso.EditarRecursoActivity

class RecursoAdapter(private val recursoList: List<Recurso>) :
    RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recurso, parent, false)
        return RecursoViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = recursoList[position]
        holder.bind(recurso)
    }
    override fun getItemCount(): Int = recursoList.size
    inner class RecursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.textTitulo)
        private val descripcion: TextView = itemView.findViewById(R.id.textDescripcion)
        private val tipo: TextView = itemView.findViewById(R.id.textTipo)
        private val imagen: ImageView = itemView.findViewById(R.id.imageRecurso)
        fun bind(recurso: Recurso) {
            titulo.text = recurso.titulo
            descripcion.text = recurso.descripcion
            tipo.text = recurso.tipo
            Glide.with(itemView.context)
                .load(recurso.imagen)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imagen)
            itemView.setOnClickListener {
                val popup = PopupMenu(itemView.context, itemView)
                popup.menuInflater.inflate(R.menu.menu_recurso_opciones, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_ver_enlace -> {
                            val url = recurso.enlace
                            if (!url.isNullOrBlank() && (url.startsWith("http://") || url.startsWith("https://"))) {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    itemView.context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(itemView.context, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(itemView.context, "Enlace inválido o vacío", Toast.LENGTH_SHORT).show()
                            }
                            true
                        }
                        R.id.menu_editar -> {
                            val context = itemView.context
                            val intent = Intent(context, EditarRecursoActivity::class.java).apply {
                                putExtra("recurso_id", recurso.id)
                                putExtra("titulo", recurso.titulo)
                                putExtra("descripcion", recurso.descripcion)
                                putExtra("tipo", recurso.tipo)
                                putExtra("enlace", recurso.enlace)
                                putExtra("imagen", recurso.imagen)
                            }
                            context.startActivity(intent)
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }
        }
    }
}
