import javax.swing.*;
import java.awt.*;


public class VentanaPrincipal extends JFrame {

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
        panelTabla.setBackground(Color.CYAN);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.ORANGE);
        panelBotones.setPreferredSize(new Dimension(800, 60));

        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

    }
}