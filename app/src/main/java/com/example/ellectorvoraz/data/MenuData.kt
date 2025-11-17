package com.example.ellectorvoraz.data

import android.content.Context
import android.content.Intent
import com.example.ellectorvoraz.P21_PantallaCatalogoLibros
import com.example.ellectorvoraz.P7_PantallaMenuOpcionesReutilizable

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
                createIntentAction = {
                    val intent = Intent(context, P21_PantallaCatalogoLibros::class.java)
                    intent.putExtra(P21_PantallaCatalogoLibros.EXTRA_CATALOG_TYPE, "LIBROS")
                    intent
                }
            ),
            MenuButton(
                text = "Revistas",
                createIntentAction = {
                    val intent = Intent(context, P21_PantallaCatalogoLibros::class.java)
                    intent.putExtra(P21_PantallaCatalogoLibros.EXTRA_CATALOG_TYPE, "REVISTAS")
                    intent
                     }
            ),
            MenuButton(
                text = "Articulos",
                createIntentAction = {
                    val intent = Intent(context, P21_PantallaCatalogoLibros::class.java)
                    intent.putExtra(P21_PantallaCatalogoLibros.EXTRA_CATALOG_TYPE, "ARTICULOS")
                    intent
                }
            )
        )
    )

    // P10 - Pantalla de Sistema Particular
    private fun getLibreriaParticular(context: Context) = MenuScreen(
        title = "BIENVENIDOS",
        buttons = listOf(
            MenuButton(
                text = "Búsqueda",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_BUSQUEDA") }
            ),
            MenuButton(
                text = "Listados",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_LISTADOS")}
            ),
            MenuButton(
                text = "Registro",
                createIntentAction = { createMenuIntent(context, "LIBRERIA_REGISTRO")}
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

    // P22 - Busqueda de Libros
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
            "LIBRERIA_GENERAL" -> getLibreriaGeneral(context)
            "LIBRERIA_CATALOGO" -> getLibreriaCatalogos(context)
            "LIBERERIA_PARTICULAR" -> getLibreriaParticular(context)
            "LIBRERIA_REGISTROS" -> getLibreriaRegistros(context)
            "LIBRERIA_BUSQUEDA" -> getLibreriaBusqueda(context)
            else -> null
        }
    }

    private fun createMenuIntent(context: Context, menuType: String): Intent {
        val intent = Intent(context, P7_PantallaMenuOpcionesReutilizable::class.java)
        intent.putExtra(P7_PantallaMenuOpcionesReutilizable.EXTRA_MENU_TYPE, menuType)
        return intent

    }
}

