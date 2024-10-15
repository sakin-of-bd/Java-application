package typad;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {
    private ImageIcon icon;
    private JLabel label, titleLabel, descriptionLabel;

    About() {
        init();
    }

    public void init() {
        setTitle("About");

        setLayout(new BorderLayout());
        
        titleLabel = new JLabel("Typad", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));

        descriptionLabel = new JLabel("<html>Typad is a Text Editor which is developed by Engineer Md. Abir Rahman.<br><div style='text-align: center;'>Typad came from the word \"Type\" & \"Pad\".</div></html>", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        
        icon = new ImageIcon(getClass().getResource("sakin.jpg"));
        label = new JLabel(icon, JLabel.CENTER);
        
        add(titleLabel, BorderLayout.NORTH);      
        add(descriptionLabel, BorderLayout.CENTER); 
        add(label, BorderLayout.SOUTH);            

        //Frame 
        setBounds(400, 100, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        About frame = new About();
    }
}
