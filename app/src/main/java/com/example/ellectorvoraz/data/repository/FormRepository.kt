package com.example.ellectorvoraz.data.repository

// Definir los tipos de campo posible
enum class FormFieldType {
    TEXT,
    NUMBER,
    NUMBER_DECIMAL,
    DATETIME,
    ENTITY_SELECTOR
}


// Crear un campo del formulario
data class FormField(
    // "key" para identificar qué datos van en el campo (autor, título, etc)
    val key: String,
    // Etiqueta visible del campo
    val label: String,
    // Defaultear el campo a texto, despues se modifica si es necesario
    val type: FormFieldType = FormFieldType.TEXT,
    val entityType: String? = null
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
            "LIBROS" -> getLibroForm()
            "REVISTAS" -> getRevistaForm()
            "ARTICULOS" -> getArticulosForm()
            "PROVEEDORES" -> getProveedorForm()
            else -> null
        }
    }
    // P12 - Registro de Libros
    private fun getLibroForm() = FormScreen (
        title = "REGISTRO DE LIBROS",
        fields = listOf(
            FormField("autor","Autor"),
            FormField("titulo","Titulo"),
            FormField("editorial","Editorial"),
            FormField("isbn","ISBN"),
            FormField("genero","Género"),
            FormField("seccion","Sección"),
            FormField("precio","Precio", FormFieldType.NUMBER_DECIMAL),
            FormField("stock", "Stock", FormFieldType.NUMBER),
            FormField(
                key = "proveedorId",
                label = "Proveedor",
                type = FormFieldType.ENTITY_SELECTOR,
                entityType = "PROVEEDOR"
            )
        )
    )

    // P15 - Registro de Revistas
    private fun getRevistaForm() = FormScreen (
        title = "REGISTRO DE REVISTAS",
        fields = listOf(
            FormField("nombre", "Nombre"),
            FormField("categoria", "Categoria"),
            FormField("edicion", "Edición del Año"),
            FormField("numero", "Numero", FormFieldType.NUMBER),
            FormField("issn", "ISSN"),
            FormField("precio", "Precio", FormFieldType.NUMBER_DECIMAL),
            FormField("stock", "Stock", FormFieldType.NUMBER),
            FormField(
                key = "proveedorId",
                label = "Proveedor",
                type = FormFieldType.ENTITY_SELECTOR,
                entityType = "PROVEEDOR"
            )
        )
    )

    // P18 - Registro de Articulos
    private fun getArticulosForm() = FormScreen (
        title = "REGISTRO DE ARTICULOS",
        fields = listOf(
            FormField("nombre", "Nombre"),
            FormField("marca", "Marca"),
            FormField("precio", "Precio", FormFieldType.NUMBER_DECIMAL),
            FormField("stock", "Stock", FormFieldType.NUMBER),
            FormField("seccion", "Seccion"),
            FormField("codigo", "Código"),
            FormField(
                key = "proveedorId",
                label = "Proveedor",
                type = FormFieldType.ENTITY_SELECTOR,
                entityType = "PROVEEDOR"
            )
        )
    )

    private fun getProveedorForm() = FormScreen (
        title = "REGISTRO DE PROVEEDORES",
        fields = listOf(
            FormField("nombre", "Nombre"),
            FormField("telefono", "Teléfono"),
            FormField("email", "Email"),
            FormField("direccion", "Dirección"),
            FormField("categoria", "Categoria")
        )
    )
}