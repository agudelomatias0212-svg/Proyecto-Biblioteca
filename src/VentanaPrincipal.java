import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class VentanaPrincipal extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tablaLibros;

    private JTextField txtTitulo, txtAutor, txtIsbn, txtGenero, txtAno, txtCopias;
    private JButton btnAgregar, btnEliminar;

    private JComboBox<String> comboAutores;
    private JButton btnFiltrar, btnVerTodos;

    private Biblioteca biblioteca;

    public VentanaPrincipal() {
        setTitle("Biblioteca municipal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        biblioteca = new Biblioteca();

        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del libro"));

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Catalogo"));

        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        panelFormulario.add(new JLabel("Titulo:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Genero:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        panelFormulario.add(new JLabel("Año:"));
        txtAno = new JTextField();
        panelFormulario.add(txtAno);

        panelFormulario.add(new JLabel("Copias:"));
        txtCopias = new JTextField();
        panelFormulario.add(txtCopias);

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarLibro());
        panelBotones.add(btnAgregar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarLibro());
        panelBotones.add(btnEliminar);

        panelBotones.add(new JLabel("Autor:"));
        comboAutores = new JComboBox<>();
        panelBotones.add(comboAutores);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> filtrarPorAutor());
        panelBotones.add(btnFiltrar);

        btnVerTodos = new JButton("Ver todos");
        btnVerTodos.addActionListener(e -> verTodos());
        panelBotones.add(btnVerTodos);

        String[] columnas = {"Titulo", "Autor", "ISBN", "Genero", "Año", "Copias"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setRowHeight(22);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        biblioteca.agregarlibro(new Libro("Cien años de soledad", "Gabriel García Márquez", "9780307474728", "Novela", 1967, 3));
        biblioteca.agregarlibro(new Libro("El coronel no tiene quien le escriba", "Gabriel García Márquez", "9780307475473", "Novela", 1961, 2));
        biblioteca.agregarlibro(new Libro("La vorágine", "José Eustasio Rivera", "9789583001093", "Novela", 1924, 1));

        refrescarTabla(biblioteca.obtenerTodos());
        actualizarComboAutores();

        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

    }

    private void agregarLibro() {

        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String genero = txtGenero.getText().trim();
        String textoAno = txtAno.getText().trim();
        String textoCopias = txtCopias.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()
                || genero.isEmpty() || textoAno.isEmpty() || textoCopias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes llenar todos los campos");
            return;
        }

        int ano;
        int copias;

        try {
            ano = Integer.parseInt(textoAno);
            copias = Integer.parseInt(textoCopias);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año y las copias deben ser numeros enteros");
            return;
        }

        Libro libro = new Libro(titulo, autor, isbn, genero, ano, copias);

        boolean agregado = biblioteca.agregarlibro(libro);

        if (!agregado) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo agregar el libro.\n"
                            + "Revisa que el año sea valido, que las copias no sean negativas\n"
                            + "y que el ISBN no este registrado ya.");
            return;
        }

        refrescarTabla(biblioteca.obtenerTodos());
        actualizarComboAutores();

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtGenero.setText("");
        txtAno.setText("");
        txtCopias.setText("");

        txtTitulo.requestFocus();
    }

    private void eliminarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un libro de la tabla");
            return;
        }

        String titulo = modeloTabla.getValueAt(fila, 0).toString();
        String codigo = modeloTabla.getValueAt(fila, 2).toString();

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Seguro que quieres eliminar: " + titulo + "?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            biblioteca.eliminarlibro(codigo);

            refrescarTabla(biblioteca.obtenerTodos());
            actualizarComboAutores();
        }
    }

    private void filtrarPorAutor() {

        String autor = (String) comboAutores.getSelectedItem();

        if (autor == null || autor.equals("(Todos)")) {
            refrescarTabla(biblioteca.obtenerTodos());
            return;
        }

        refrescarTabla(biblioteca.filtrarPorAutor(autor));
    }

    private void verTodos() {
        comboAutores.setSelectedIndex(0);
        refrescarTabla(biblioteca.obtenerTodos());
    }

    private void refrescarTabla(ArrayList<Libro> lista) {

        modeloTabla.setRowCount(0);

        for (Libro libro : lista) {
            modeloTabla.addRow(new Object[]{
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getCodigo(),
                    libro.getGenero(),
                    libro.getañopublicado(),
                    libro.getcopiasDisponibles()
            });
        }
    }

    private void actualizarComboAutores() {

        comboAutores.removeAllItems();
        comboAutores.addItem("(Todos)");

        ArrayList<String> autores = new ArrayList<>();

        for (Libro libro : biblioteca.obtenerTodos()) {
            if (!autores.contains(libro.getAutor())) {
                autores.add(libro.getAutor());
            }
        }

        for (String autor : autores) {
            comboAutores.addItem(autor);
        }
    }
}