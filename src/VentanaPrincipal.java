import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.text.Normalizer;
import java.time.Year;


public class VentanaPrincipal extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tablaLibros;

    private JTextField txtTitulo, txtAutor, txtIsbn, txtGenero, txtAno, txtCopias;
    private JButton btnAgregar, btnEliminar;

    private JTextField txtBuscarAutor;
    private JButton btnFiltrar, btnVerTodos;

    private ArrayList<Object[]> listaLibros;

    public VentanaPrincipal() {
        setTitle("Biblioteca municipal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        listaLibros = new ArrayList<>();

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

        panelBotones.add(new JLabel("Buscar por autor:"));
        txtBuscarAutor = new JTextField(15);
        panelBotones.add(txtBuscarAutor);

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

        listaLibros.add(new Object[]{"Cien años de soledad", "Gabriel García Márquez", "9780307474728", "Novela", 1967, 3});
        listaLibros.add(new Object[]{"El coronel no tiene quien le escriba", "Gabriel García Márquez", "9780307475473", "Novela", 1961, 2});
        listaLibros.add(new Object[]{"La vorágine", "José Eustasio Rivera", "9789583001093", "Novela", 1924, 1});

        refrescarTabla(listaLibros);

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

        int anoActual = Year.now().getValue();

        if (ano > anoActual) {
            JOptionPane.showMessageDialog(this, "El año no puede ser mayor a " + anoActual);
            return;
        }

        if (copias < 0) {
            JOptionPane.showMessageDialog(this, "Las copias no pueden ser un numero negativo");
            return;
        }

        listaLibros.add(new Object[]{titulo, autor, isbn, genero, ano, copias});

        refrescarTabla(listaLibros);

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
        String isbn = modeloTabla.getValueAt(fila, 2).toString();

        int respuesta = JOptionPane.showConfirmDialog(this,
                "Seguro que quieres eliminar: " + titulo + "?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            for (int i = 0; i < listaLibros.size(); i++) {
                if (listaLibros.get(i)[2].toString().equals(isbn)) {
                    listaLibros.remove(i);
                    break;
                }
            }

            refrescarTabla(listaLibros);
        }
    }

    private void filtrarPorAutor() {

        String autorBuscado = sinTildes(txtBuscarAutor.getText().toLowerCase().trim());

        if (autorBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un autor para poder filtrar");
            return;
        }

        ArrayList<Object[]> encontrados = new ArrayList<>();

        for (Object[] libro : listaLibros) {
            String autor = sinTildes(libro[1].toString().toLowerCase());
            if (autor.contains(autorBuscado)) {
                encontrados.add(libro);
            }
        }

        if (encontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros de ese autor");
            return;
        }

        refrescarTabla(encontrados);
    }

    private void verTodos() {
        txtBuscarAutor.setText("");
        refrescarTabla(listaLibros);
    }

    private void refrescarTabla(ArrayList<Object[]> lista) {

        modeloTabla.setRowCount(0);

        for (Object[] libro : lista) {
            modeloTabla.addRow(libro);
        }
    }

    private String sinTildes(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalizado.replaceAll("[^\\p{ASCII}]", "");
    }
}