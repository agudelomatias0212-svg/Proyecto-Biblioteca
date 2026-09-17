import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Biblioteca {

        private ArrayList<Libro> libros;
        private HashMap<String, ArrayList<Libro>> librosPorAutor;
        private HashSet<String> codigosRegistrados;

        private String[] generos = {
                "Novela","Ciencia","Historia","Infantil","Tecnico"
        };

}
