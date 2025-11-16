package com.example.ellectorvoraz.data

import android.content.Context
import android.content.Intent
import com.example.ellectorvoraz.P7_PantallaSistemaGeneral

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

    // Aca se agrega cada menu con sus botones
    private fun getLibreriaMain(context: Context) = MenuScreen(
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

    private fun getLibreriaCatalogos(context: Context) = MenuScreen(
        title = "CATALOGOS",
        buttons = listOf(
            MenuButton(
                text = "Libros",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_CATALOGO_LIBROS") }
            ),
            MenuButton(
                text = "Revistas",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_CATALOGO_REVISTAS") }
            ),
            MenuButton(
                text = "Articulos",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_CATALOGO_ARTICULOS") }
            )
        )
    )

    private fun getLibreriaBusqueda(context: Context) = MenuScreen(
        title = "BUSQUEDA DE LIBROS",
        buttons = listOf(
            MenuButton(
                text = "Título",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA_TITULO") }
            ),
            MenuButton(
                text = "Autor",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA_AUTOR") }
            ),
            MenuButton(
                text = "Editorial",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA_EDITORIAL") }
            ),
            MenuButton(
                text = "ISBN",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA_ISBN") }
            ),
            MenuButton(
                text = "Género",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA_GENERO") }
            )
        )
    )

    // Navegacion
    fun getMenuScreenForType(context: Context, menuType: String): MenuScreen? {
        return when (menuType) {
            "LIBRERIA_MAIN" -> getLibreriaMain(context)
            "LIBRERIA_CATALOGO" -> getLibreriaCatalogos(context)
            "LIBRERIA_BUSQUEDA" -> getLibreriaBusqueda(context)
            else -> null
        }
    }

    private fun createMenuIntent(context: Context, menuType: String): Intent {
        val intent = Intent(context, P7_PantallaSistemaGeneral::class.java)
        intent.putExtra(P7_PantallaSistemaGeneral.EXTRA_MENU_TYPE, menuType)
        return intent

    }
}

