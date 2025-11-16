package com.example.ellectorvoraz.data

import android.text.InputType

// Crear un campo del formulario
data class FormField(
    // "key" para identificar qué datos van en el campo (autor, título, etc)
    val key: String,
    // Etiqueta visible del campo
    val label: String,
    // Defaultear el campo a texto, despues se modifica si es necesario
    val inputType: Int = InputType.TYPE_CLASS_TEXT
)

// Armar el formulario completo
data class FormScreen(
    // Titulo del formulario
    val title: String,
    // Campos del formulario
    val fields: List<FormField>
)

// Aca se definen todos los formularios
// Titulo = Titulo de la pantalla
// Campos = FormField(key, label) - key es qué dato y label la hint que muestra el form
// Recordar cambiar el inputType de los campos si no son texto
object FormRepository {
    fun getFormForType(formType: String): FormScreen? {
        return when (formType) {
            "REGISTRO_LIBRO" -> FormScreen(
                title = "REGISTRO DE LIBROS",
                fields = listOf(
                    FormField("autor","Autor"),
                    FormField("titulo","Titulo"),
                    FormField("editorial","Editorial"),
                    FormField("isbn","ISBN"),
                    FormField("genero","Género"),
                    FormField("seccion","Sección"),
                    FormField("precio","Precio", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
                    FormField("stock", "Stock", InputType.TYPE_CLASS_NUMBER)
                )
            )
            "REGISTRO_REVISTA" -> FormScreen(
                title = "REGISTRO DE REVISTAS",
                fields = listOf(
                    FormField("nombre", "Nombre"),
                    FormField("fecha", "Fecha", InputType.TYPE_CLASS_DATETIME),
                    FormField("edicion", "Edición del Año", InputType.TYPE_CLASS_NUMBER),
                    FormField("numero", "Numero", InputType.TYPE_CLASS_NUMBER),
                    FormField("issn", "ISSN", InputType.TYPE_CLASS_NUMBER),
                    FormField("seccion", "Seccion"),
                    FormField("precio", "Precio", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
                    FormField("stock", "Stock", InputType.TYPE_CLASS_NUMBER)
                )
            )
            "REGISTRO_ARTICULO" -> FormScreen(
                title = "REGISTRO DE ARTICULOS",
                fields = listOf(
                    FormField("nombre", "Nombre"),
                    FormField("marca", "Marca"),
                    FormField("descripcion", "Descripcion"),
                    FormField("seccion", "Seccion"),
                    FormField("precio", "Precio", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
                    FormField("stock", "Stock", InputType.TYPE_CLASS_NUMBER)
                )
            )



            else -> null
        }
    }
}