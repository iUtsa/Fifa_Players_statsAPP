package src.java.main;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlayerStatsApp app = new PlayerStatsApp();
            app.setVisible(true);
            
        });
    }
}
