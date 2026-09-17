import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


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
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.ORANGE);
        panelBotones.setPreferredSize(new Dimension(800, 60));

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

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String isbn = txtIsbn.getText();
        String genero = txtGenero.getText();
        int ano = Integer.parseInt(txtAno.getText());
        int copias = Integer.parseInt(txtCopias.getText());

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

        String autorBuscado = txtBuscarAutor.getText().toLowerCase();

        ArrayList<Object[]> encontrados = new ArrayList<>();

        for (Object[] libro : listaLibros) {
            String autor = libro[1].toString().toLowerCase();
            if (autor.contains(autorBuscado)) {
                encontrados.add(libro);
            }
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
}