package src.java.main;

import javax.imageio.ImageIO;
import javax.swing.*;  
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;


public class PlayerStatsApp extends JFrame {
	

    private PlayerManager playerManager;
    private List<Player> players;
    private final Action action = new SwingAction();
    
    /**
     * @wbp.nonvisual location=230,191
     */
    

    public PlayerStatsApp() {
        playerManager = new PlayerManager();
        players = playerManager.listPlayers();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("FIFA Player Stats");
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        

        initUI();  // Initialize the UI components
    }

    private void initUI() {
        players = loadInitialPlayers();
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel(new ImageIcon("/Users/utsadas6/Downloads/ea-sports-fifa-world-kicks-off-global-open-beta-ukheaderimage_00.jpeg.adapt.crop191x100.628p.jpeg"));
        JButton goButton = new JButton("Go!");
        goButton.setAction(action);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the content pane to the player selection panel
                setContentPane(createPlayerSelectionPanel());
                // Revalidate the frame to reflect the changes
                revalidate();
                // Repaint the frame to ensure proper rendering
                repaint();
            }
        });

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(goButton, BorderLayout.SOUTH);

        setContentPane(welcomePanel);
    }


    private void showPlayerGrid(ActionEvent e) {
        // Assuming PlayerManager is a class that handles player data
        PlayerManager manager = new PlayerManager();
        players = manager.listPlayers(); // This should fetch players from database or JSON
        PlayerGridFrame playerGridFrame = new PlayerGridFrame(players);
        playerGridFrame.setVisible(true);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 5, 5)); // 3 rows, 3 cols
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        for (Player player : players) {
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(new URL(player.getImage()));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                // Provide a default image icon
                icon = new ImageIcon("ea-sports-fifa-world-kicks-off-global-open-beta-ukheaderimage_00.jpeg.adapt.crop191x100.628p.jpeg");
            }

            JButton playerButton = new JButton(icon);
            playerButton.addActionListener(ae -> showPlayerDetails(player));
            gridPanel.add(playerButton);
        }


        setContentPane(scrollPane);
        revalidate();
    }

    private JPanel createPlayerSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 10));
        for (Player player : players) {
            JButton btn = new JButton(player.getFirstName() + " " + player.getLastName());
            btn.addActionListener(e -> showPlayerDetails(player));
            panel.add(btn);
        }
        return panel;
    }

    private void showPlayerDetails(Player player) {
        JPanel detailPanel = new JPanel(new GridLayout(0, 1));
        detailPanel.add(new JLabel("Name: " + player.getFirstName() + " " + player.getLastName()));
        detailPanel.add(new JLabel("Team: " + player.getTeam()));
        detailPanel.add(new JLabel("Position: " + player.getPosition()));
        detailPanel.add(new JLabel(new ImageIcon(player.getImage())));

        setContentPane(detailPanel);
        revalidate();
    }

    // Method to load initial players from JSON into the database
    private List<Player> loadInitialPlayers() {
        try (FileReader reader = new FileReader("/Users/utsadas6/eclipse-workspace/FifaPlayerStats/src/player.json")) {
            Gson gson = new Gson();
            java.lang.reflect.Type listType = new TypeToken<List<Player>>(){}.getType();
            List<Player> playersFromJson = gson.fromJson(reader, listType);
            playersFromJson.forEach(playerManager::addPlayer);
            return playerManager.listPlayers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load player data from JSON: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Goo!");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
