package src.java.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayerGridFrame extends JFrame {
    private List<Player> players;

    public PlayerGridFrame(List<Player> players) {
        this.players = players;
        setTitle("Player Grid");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame, not the entire application
        
        initUI();
    }

    private void initUI() {
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columns
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        for (Player player : players) {
            ImageIcon icon = new ImageIcon(player.getImage());
            JButton playerButton = new JButton(icon);
            playerButton.addActionListener(e -> showPlayerDetails(player));
            gridPanel.add(playerButton);
        }

        add(scrollPane);
    }

    private void showPlayerDetails(Player player) {
        // Display player details in a JOptionPane, JLabel, or any other component
        JOptionPane.showMessageDialog(this, "Name: " + player.getFirstName() + " " + player.getLastName() +
                "\nTeam: " + player.getTeam() +
                "\nPosition: " + player.getPosition(), "Player Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
