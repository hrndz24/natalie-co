package natalie.gui;

import natalie.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Natalie extends JFrame {

    private Color paleGold = new Color(241, 237, 196);
    private Color gold = new Color(191, 174, 48);
    private Color betterGold = new Color(166, 120, 0);
    private Color marsala = new Color(163, 0, 0);
    private ArrayList<Automobile> automobiles;
    private ArrayList<AutoPanel> autoPanels;
    private JLabel organization;
    private JTextField searchField;
    private JButton sortButton, editButton;
    private static JPanel panel;
    private JScrollPane scrollPane;
    private final String KEY = "376572172432";

    public void display() throws IOException {
        setLocation(300, 120);
        setSize(900, 600);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        automobiles = DatabaseReader.readDatabase();
        autoPanels = AutoPanel.makePanels(automobiles);

        organization = new JLabel("Natalie&Co".toUpperCase());
        organization.setFont(new Font("Serif", Font.BOLD, 20));
        organization.setForeground(gold);
        organization.setSize(250, 25);
        organization.setLocation(20, 20);

        searchField = new JTextField("Search...");
        searchField.setLocation(670, 20);
        searchField.setSize(180, 25);
        searchField.setName("Search...");
        Border goldenBorder = BorderFactory.createLineBorder(gold, 2);
        searchField.setBorder(goldenBorder);
        searchField.setFocusable(false);
        searchField.addMouseListener(new MyMouseListener());
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String search = searchField.getText().toUpperCase();
                if (sortButton.isVisible()) {
                    ArrayList<AutoPanel> panelsToShow = new ArrayList<>();
                    for (AutoPanel autoPanel : autoPanels) {
                        String carInfo = autoPanel.getManufacturer() + " " + autoPanel.getModel();
                        if (carInfo.contains(search)) {
                            panelsToShow.add(autoPanel);
                        }
                    }
                    updateScrollPane(panelsToShow);
                } else {
                    ArrayList<Automobile> panelsToShow = new ArrayList<>();
                    for (Automobile automobile : automobiles) {
                        String carInfo = automobile.getManufacturer() + " " + automobile.getModel();
                        if (carInfo.contains(search)) {
                            panelsToShow.add(automobile);
                        }
                    }
                    updateScrollPane(panelsToShow);
                }
            }
        });

        panel = new JPanel();
        scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(40, 70, 800, 400);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        updateScrollPane(autoPanels);

        sortButton = new JButton("Sort".toUpperCase());
        sortButton.setLocation(50, 500);
        sortButton.setSize(200, 30);
        sortButton.setBackground(betterGold);
        sortButton.setForeground(Color.WHITE);
        sortButton.setBorder(null);
        sortButton.setFocusable(false);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                automobiles.sort(new Comparator<Automobile>() {
                    @Override
                    public int compare(Automobile o1, Automobile o2) {
                        return o1.getYear().compareTo(o2.getYear());
                    }
                });
                Collections.reverse(automobiles);
                updateScrollPane(AutoPanel.makePanels(automobiles));
            }
        });

        editButton = new JButton("edit".toUpperCase());
        editButton.setLocation(625, 500);
        editButton.setSize(200, 30);
        editButton.setBackground(marsala);
        editButton.setForeground(Color.WHITE);
        editButton.setBorder(null);
        editButton.setFocusable(false);
        editButton.addActionListener(new EditListener());

        add(scrollPane);
        add(organization);
        add(searchField);
        add(sortButton);
        add(editButton);
    }

    public void updateScrollPane(ArrayList<?> objects) {
        scrollPane.setVisible(false);
        panel.removeAll();
        for (Object object : objects) {
            JPanel jAutoPanel = new JAutoPanel(object, this);
            panel.add(jAutoPanel);
        }
        panel.add(Box.createVerticalGlue());
        scrollPane.setVisible(true);
    }

    private class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame();
            frame.setLocation(520, 320);
            frame.setSize(400, 200);
            frame.getContentPane().setBackground(paleGold);
            frame.setLayout(null);

            JLabel label = new JLabel(("<html>You should have rights to<br/>modify the information</html>").toUpperCase());
            label.setSize(400, 60);
            label.setLocation(100, 20);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setSize(200, 25);
            passwordField.setLocation(100, 100);
            passwordField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Arrays.equals(passwordField.getPassword(), KEY.toCharArray())) {
                        frame.dispose();
                        sortButton.setVisible(false);
                        editButton.setVisible(false);

                        updateScrollPane(automobiles);

                        JButton addButton = new JButton("Add".toUpperCase());
                        addButton.setLocation(575, 500);
                        addButton.setSize(150, 30);
                        addButton.setForeground(Color.WHITE);
                        addButton.setBackground(marsala);
                        addButton.setBorder(null);
                        addButton.setFocusable(false);
                        addButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame frame = new AddAutomobileFrame(Natalie.this);
                                frame.setVisible(true);
                            }
                        });

                        JButton finishButton = new JButton("finish".toUpperCase());
                        finishButton.setLocation(735, 500);
                        finishButton.setSize(100, 30);
                        finishButton.setForeground(Color.WHITE);
                        finishButton.setBackground(betterGold);
                        finishButton.setBorder(null);
                        finishButton.setFocusable(false);
                        finishButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                editButton.setVisible(true);
                                sortButton.setVisible(true);
                                finishButton.setVisible(false);
                                addButton.setVisible(false);
                                try {
                                    automobiles = DatabaseReader.readDatabase();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                autoPanels = AutoPanel.makePanels(automobiles);
                                updateScrollPane(autoPanels);
                            }
                        });
                        add(addButton);
                        add(finishButton);
                    } else {
                        passwordField.setBackground(Color.RED);
                        passwordField.setText("");
                    }
                }
            });
            frame.getContentPane().add(label);
            frame.getContentPane().add(passwordField);
            frame.setVisible(true);
        }
    }
}