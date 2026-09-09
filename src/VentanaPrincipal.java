import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;


public class VentanaPrincipal extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tablaLibros;

    public VentanaPrincipal() {
        setTitle("Biblioteca municipal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setBackground(Color.PINK);
        panelFormulario.setPreferredSize(new Dimension(800, 120));

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.ORANGE);
        panelBotones.setPreferredSize(new Dimension(800, 60));

        String[] columnas = {"Titulo", "Autor", "ISBN", "Genero","Año", "Copias"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        modeloTabla.addRow(new Object[]{"Cien años de soledad", "Gabriel García Márquez", "9780307474728", "Novela", 1967, 3});
        modeloTabla.addRow(new Object[]{"El coronel no tiene quien le escriba", "Gabriel García Márquez", "9780307475473", "Novela", 1961, 2});
        modeloTabla.addRow(new Object[]{"La vorágine", "José Eustasio Rivera", "9789583001093", "Novela", 1924, 1});

        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

    }
}