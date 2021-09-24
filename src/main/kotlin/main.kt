import java.util.*

fun main() {
    //Permite leer entradas de teclado en la consola
    val scanner = Scanner(System.`in`)
    //Imprime un mensaje en la consola
    println("Hola como te llamas?")
    //Leemos lo ingresado en la consola
    val nombre = scanner.nextLine()
    //Imprimimos lo valores
    println("Hola $nombre")
    val edad = leerEdad()

    val persona = Persona(nombre, edad)
    val carta = Carta()
    carta.agregarItem(Item(1, "Cerveza", 1500))
    carta.agregarItem(Item(2, "Vino", 1800))
    carta.agregarItem(Item(3, "Destilado", 2500))
    val bar = Bar(carta)

    if(persona.isMayorDeEdad) {
        bar.atender(persona)
    } else {
        println("andate pa la casa")
    }
}

fun leerEdad(): Int {
    val scanner = Scanner(System.`in`)
    println("Cual es tu edad?:")
    return try {
        scanner.nextInt()
    } catch (ex: InputMismatchException) {
        println("Error al ingresar la edad, intenta con un numero")
        leerEdad()
    }
}

class Persona(
    val nombre: String,
    val edad: Int
) {
    val isMayorDeEdad :Boolean
        get() = edad >= 18
}

class Bar(
    val carta: Carta
) {
    fun atender(persona: Persona) {
        println("Hola ${persona.nombre}, que le sirvo?:")
        carta.mostrarMenu()
        val seleccion = seleccionCarta()
        if(carta.existeItem(seleccion)) {
            val item = carta.obtenerItem(seleccion)
            println("tome mi washo aqui tiene su ${item.nombre}, son \$${item.precio}")
        }
    }

    private fun seleccionCarta(): Int {
        val scanner = Scanner(System.`in`)
        return try {
            scanner.nextInt()
        } catch (ex: InputMismatchException) {
            println("Error al seleccionar opcion, intenta nuevamente")
            seleccionCarta()
        }
    }
}

data class Item(
    val id: Int,
    val nombre: String,
    val precio: Int
)

class Carta {
    private val items = mutableListOf<Item>()

    fun agregarItem(item: Item) {
        items.add(item)
    }

    fun quitarItem(item: Item) {
        items.remove(item)
    }

    fun obtenerItem(id: Int): Item = items.first { it.id == id }

    fun mostrarMenu() {
        println("""
            ##############################
            #             CARTA          #
            ##############################
        """.trimIndent())
        items.forEach { (id, nombre, precio) ->
            println("$id - $nombre : $precio")
        }
        println("##############################")
    }

    fun existeItem(id: Int) : Boolean = items.find { it.id == id } != null
}