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

        public Biblioteca() {
                libros = new ArrayList<>();
                librosPorAutor = new HashMap<>();
                codigosRegistrados = new HashSet<>();
        }

        public boolean agregarlibro(Libro libro) {

                if (libro == null) {
                        return false;
                }
                if (libro.getAutor().isBlank() ||
                        libro.getTitulo().isBlank() ||
                        libro.getGenero().isBlank() ||
                        libro.getCodigo().isBlank()) {
                        return false;
                }

                int anioActual = java.time.Year.now().getValue();

                if (libro.getañopublicado() <= 0 ||
                        libro.getañopublicado() > anioActual) {
                        return false;
                }

                if (libro.getcopiasDisponibles() < 0){
                        return false;
                }

                if (codigosRegistrados.contains(libro.getCodigo())) {
                        return false;
                }

                libros.add(libro);

                codigosRegistrados.add(libro.getCodigo());

                librosPorAutor.computeIfAbsent(libro.getAutor(), k -> new ArrayList<>())
                        .add(libro);
                return true;

        }

        public ArrayList<Libro> obtenerTodos() {
                ArrayList<Libro> copiaLibros = new ArrayList<>();

                for (Libro libro : libros)  {
                        copiaLibros.add(libro);
                }
                return copiaLibros;
        }

        public ArrayList<Libro> filtrarPorAutor(String autor) {
                ArrayList<Libro> librosFiltrados = new ArrayList<>();

                for (Libro libro : librosPorAutor.getOrDefault(autor, new ArrayList<>())) {
                        librosFiltrados.add(libro);
                }

                return librosFiltrados;
        }

        public void eliminarlibro(String codigo) {
                for (int i = 0; i < libros.size(); i++) {

                        Libro libro = libros.get(i);

                        if (libro.getCodigo().equals(codigo)) {

                                libros.remove(i);
                                codigosRegistrados.remove(codigo);

                                ArrayList<Libro> librosAutor = librosPorAutor.get(libro.getAutor());

                                if (librosAutor != null) {
                                        librosAutor.remove(libro);

                                        if (librosAutor.isEmpty()) {
                                                librosPorAutor.remove(libro.getAutor());
                                        }
                                }

                                break;
                        }
                }
        }
}
