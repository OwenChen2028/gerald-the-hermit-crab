import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame {
    public Launcher() {
        setTitle("Game Launcher");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Game Title", SwingConstants.CENTER);
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        JButton gameButton = createButton("Start");
        gbc.gridy = 1;
        panel.add(gameButton, gbc);

        JButton exitButton = createButton("Exit");
        gbc.gridy = 2;
        panel.add(exitButton, gbc);

        gameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                launchGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(panel);

        setVisible(true);
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        
        button.setBackground(Color.GRAY);
        button.setForeground(Color.LIGHT_GRAY);
        
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createEmptyBorder());
        
        return button;
    }
    private void launchGame() {
        Game.main(new String[]{});
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Launcher();
            }
        });
    }
}
