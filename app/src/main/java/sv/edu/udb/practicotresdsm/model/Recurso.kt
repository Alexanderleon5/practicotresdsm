package sv.edu.udb.practicotresdsm.model

data class Recurso(
    val id: String? = null,
    val titulo: String,
    val descripcion: String,
    val tipo: String,
    val enlace: String,
    val imagen: String
)
