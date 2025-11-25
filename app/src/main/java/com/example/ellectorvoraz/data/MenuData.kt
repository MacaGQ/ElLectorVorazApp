package com.example.ellectorvoraz.data

import android.content.Context
import android.content.Intent
import com.example.ellectorvoraz.P12_PantallaDeRegistroReutilizable
import com.example.ellectorvoraz.P21_PantallaCatalogoReutilizable
import com.example.ellectorvoraz.P7_PantallaMenuOpcionesReutilizable
import com.example.ellectorvoraz.P82_PantallaTrasnsaccionesReutilizable

// Crear boton: texto e intent
data class MenuButton (
    val text: String,
    val createIntentAction: (Context) -> Intent
)

// Crear menu: Titulo y botones
data class MenuScreen(
    val title: String,
    val buttons: List<MenuButton>
)

// Informacion de cada Menu
object MenuRepository {

    // Contexto extra para cargar el menu de acuerdo a de que pantalla se viene y reutilizar el menu particular
    private const val EXTRA_CATALOG_CONTEXT = "EXTRA_CATALOG_CONTEXT"

    // Aca se agrega cada menu con sus botones
    // title = Titulo del Menu (aparece en la actionbar)
    // buttons = cada boton el intent de a qué pantalla/menu tiene que ir
    // Hay que modificar "createIntentAction" de acuerdo a donde tiene que ir cada botón

    // P7 - Sistema General
    private fun getLibreriaGeneral(context: Context) = MenuScreen(
        title = "SECTOR LIBRERIA",
        buttons = listOf(
            MenuButton(
                text = "Catalogos",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_CATALOGO") }
            ),
            MenuButton(
                text = "Gestion",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_GESTION") }
            ),
            MenuButton(
                text = "Eventos",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_EVENTOS") }
            )
        )
    )

    // P9 - Pantalla de Catálogos
    private fun getLibreriaCatalogos(context: Context) = MenuScreen(
        title = "CATALOGOS",
        buttons = listOf(
            MenuButton(
                text = "Libros",
                createIntentAction = { createParticularMenuIntent(context, "LIBROS") }
            ),
            MenuButton(
                text = "Revistas",
                createIntentAction = { createParticularMenuIntent(context, "REVISTAS") }
            ),
            MenuButton(
                text = "Articulos",
                createIntentAction = { createParticularMenuIntent(context, "ARTICULOS") }
            )
        )
    )

    // P10/P76 - Pantallas de Sistema Particular
    private fun getLibreriaParticular(context: Context, contextType: String?) = MenuScreen(
        title = contextType ?: "BIENVENIDOS",
        buttons = listOf(
            MenuButton(
                text = "Listados",
                createIntentAction = {
                    if (contextType != null) {
                        val intent = Intent(context, P21_PantallaCatalogoReutilizable::class.java)
                        intent.putExtra(P21_PantallaCatalogoReutilizable.EXTRA_CATALOG_TYPE, contextType)
                        intent
                    } else {
                        createMenuIntent(context, "LIBRERIA_CATALOGO")
                    }
                }
            ),
            MenuButton(
                text = "Registro",
                createIntentAction = {
                    when (contextType) {
                        "VENTAS" -> {
                            val intent = Intent(context, P82_PantallaTrasnsaccionesReutilizable::class.java)
                            intent.putExtra(P82_PantallaTrasnsaccionesReutilizable.EXTRA_TRANSACTION_TYPE, P82_PantallaTrasnsaccionesReutilizable.TYPE_VENTA)
                            intent
                        }
                        "PEDIDOS" -> {
                            val intent = Intent(context, P82_PantallaTrasnsaccionesReutilizable::class.java)
                            intent.putExtra(P82_PantallaTrasnsaccionesReutilizable.EXTRA_TRANSACTION_TYPE, P82_PantallaTrasnsaccionesReutilizable.TYPE_PEDIDO)
                            intent
                        }
                        else ->
                            if (contextType != null) {
                                val intent = Intent(context, P12_PantallaDeRegistroReutilizable::class.java)
                                intent.putExtra(P12_PantallaDeRegistroReutilizable.EXTRA_FORM_TYPE, contextType)
                                intent
                            } else {
                                Intent()
                            }
                    }
                }
            ),
        )
    )

    // P11 - Pantalla de Registros
    private fun getLibreriaRegistros(context: Context) = MenuScreen(
        title = "REGISTROS",
        buttons = listOf(
            MenuButton(
                text = "Libros",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_REGISTROS_LIBROS") }
            ),
            MenuButton(
                text = "Revistas",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_REGISTROS_REVISTAS") }
            ),
            MenuButton(
                text = "Libreria",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_REGISTROS_LIBRERIA") }
            )
        )
    )

    // P75 - Pantalla de Sistema Particular: Gestión
    private fun getLibreriaGestion(context: Context) = MenuScreen(
        title = "GESTION",
        buttons = listOf(
            MenuButton(
                text = "Pedidos",
                createIntentAction = { createParticularMenuIntent(context, "PEDIDOS") }
            ),
            MenuButton(
                text = "Ventas",
                createIntentAction = { createParticularMenuIntent(context, "VENTAS") }
            ),
            MenuButton(
                text = "Proveedores",
                createIntentAction = { createParticularMenuIntent(context, "PROVEEDORES") }
            ),
        )
    )

    // Navegacion
    fun getMenuScreenForType(context: Context, intent: Intent): MenuScreen? {
        val menuType = intent.getStringExtra(P7_PantallaMenuOpcionesReutilizable.EXTRA_MENU_TYPE)
        val catalogContext = intent.getStringExtra(EXTRA_CATALOG_CONTEXT)
        return when (menuType) {
            "LIBRERIA_GENERAL" -> getLibreriaGeneral(context)
            "LIBRERIA_CATALOGO" -> getLibreriaCatalogos(context)
            "LIBRERIA_PARTICULAR" -> getLibreriaParticular(context, catalogContext)
            "LIBRERIA_REGISTROS" -> getLibreriaRegistros(context)
            "LIBRERIA_GESTION" -> getLibreriaGestion(context)
            else -> null
        }
    }

    private fun createMenuIntent(context: Context, menuType: String): Intent {
        val intent = Intent(context, P7_PantallaMenuOpcionesReutilizable::class.java)
        intent.putExtra(P7_PantallaMenuOpcionesReutilizable.EXTRA_MENU_TYPE, menuType)
        return intent
    }

    private fun createParticularMenuIntent(context: Context, catalogType: String): Intent {
        val intent = Intent(context, P7_PantallaMenuOpcionesReutilizable::class.java)
        intent.putExtra(P7_PantallaMenuOpcionesReutilizable.EXTRA_MENU_TYPE, "LIBRERIA_PARTICULAR")
        intent.putExtra(EXTRA_CATALOG_CONTEXT, catalogType)
        return intent
    }
}

