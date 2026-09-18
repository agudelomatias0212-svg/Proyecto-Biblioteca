# Sistema de Gestión de Biblioteca

Aplicación de escritorio hecha en Java con interfaz gráfica en Swing, para
manejar el catálogo de una biblioteca. 

El caso es el de la Biblioteca Municipal San Rafael, que llevaba su catálogo
en cuadernos y hojas de cálculo. Por eso se les repetían libros, quedaban
fichas sin autor o sin año, y para saber qué tenían de un autor tocaba
revisar a mano. Este programa reemplaza ese proceso.

## Integrantes

- Matias Agudelo — interfaz gráfica (`VentanaPrincipal`)
- Santiago Alvarado — lógica del proyecto (`MaterialBibliografico`, `Libro`, `Biblioteca`)

## Qué hace

- Registrar un libro con título, autor, ISBN, género, año y copias
- Ver el catálogo completo en una tabla
- Filtrar los libros por autor
- Eliminar un libro, pidiendo confirmación antes

Valida que no falte ningún campo, que el año y las copias sean números, que
el año no sea mayor al actual, que las copias no sean negativas, y que no se
repita un ISBN.

## Las clases

| Clase | Responsabilidad |
|---|---|
| `MaterialBibliografico` | Clase abstracta con lo común a cualquier material: título y autor |
| `Libro` | Hereda de la anterior y agrega código, género, año y copias |
| `Biblioteca` | Guarda el catálogo y aplica las reglas del negocio |
| `VentanaPrincipal` | La ventana. Muestra datos y recibe las acciones del usuario |
| `Main` | Arranca la aplicación |

## Estructuras de datos y por qué

- **Array de tamaño fijo** para los nombres de las columnas de la tabla, que
  nunca cambian
- **ArrayList** para guardar los libros, porque el catálogo crece y un array
  no se puede agrandar
- **HashMap** de autor a lista de libros, para que filtrar por autor no
  tenga que recorrer todo el catálogo
- **HashSet** con los ISBN ya registrados, para saber al instante si un
  libro está repetido sin revisar la lista entera

## Cómo ejecutarlo

Se necesita el JDK 17 o superior.

**En IntelliJ IDEA:**

1. Clonar el repositorio
2. Abrirlo con **Open** sobre la carpeta del proyecto
3. Verificar que la carpeta `src` esté marcada como Sources Root
4. Abrir `Main.java` y darle al botón de Run

**O solo presionas las teclas:** Ctrl + mayus + F10
## Ramas

| Rama | Contenido |
|---|---|
| `main` | La versión estable |
| `feature/clase-libro` | `MaterialBibliografico` y `Libro` |
| `feature/biblioteca` | `Biblioteca` |
| `feature/Interfaz-Grafica` | La ventana en Swing |

Cada uno trabajó en su propia rama y las integramos a `main`. 
Santiago trabajo en las ramas **feature/clase-libro** y **feature/biblioteca**, y Matías trabajo en la rama **feature/Interfaz-Grafica**.

## Sobre el uso de IA

Usamos IA como apoyo para entender conceptos, para saber que puntos clave que nos pedía el taller nos faltaba en nuestro código y para que nos asesorara sobre como hacer el README y que colocar en el. Los conceptos fueron puntualmente para entender la separación entre `JTable` y
`DefaultTableModel`, cómo un lambda funciona como `ActionListener`, y por
qué borrar de una lista mientras se recorre con un for-each da problemas. Tambien se uso para entender como se usa y para que sirven: HashSet, TreeMap, LinkedList, Queue / PriorityQueue, LinkedHashMap y HashMap.