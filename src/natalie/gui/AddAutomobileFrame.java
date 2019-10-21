package natalie.gui;

import natalie.Automobile;
import natalie.DatabaseReader;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class AddAutomobileFrame extends JFrame {

    private JTextField manufacturer, model, color, year, vin;
    private RoundedCornerButton finishButton;

    public AddAutomobileFrame(Natalie natalie) {

        setLocation(380, 320);
        setSize(750, 150);
        getContentPane().setBackground(new Color(241, 237, 196));
        setLayout(null);
        setVisible(true);

        MaskFormatter yearMask = new MaskFormatter(), vinMask = new MaskFormatter();
        try {
            yearMask.setMask("####");
            yearMask.setPlaceholder("Year");
            vinMask.setMask("UUUUU##UAAUU#####");
            vinMask.setPlaceholder("VIN");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        manufacturer = createTextField("Manufacturer",
                new Point(20, 35), new Dimension(120, 25), null);
        model = createTextField("Model",
                new Point(150, 35), new Dimension(170, 25), null);
        color = createTextField("Color",
                new Point(330, 35), new Dimension(150, 25), null);
        year = createTextField("Year",
                new Point(490, 35), new Dimension(50, 25), yearMask);
        vin = createTextField("VIN              ",
                new Point(550, 35), new Dimension(170, 25), vinMask);

        finishButton = new RoundedCornerButton("finish".toUpperCase());
        finishButton.setPressedColor(new Color(194, 195, 186));
        finishButton.setLocation(600, 70);
        finishButton.setSize(80, 25);
        finishButton.setBackground(Color.WHITE);
        finishButton.setForeground(Color.BLACK);
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Automobile automobile = new Automobile(manufacturer.getText().toUpperCase(),
                        model.getText().toUpperCase(), year.getText().toUpperCase(),
                        color.getText().toUpperCase(), vin.getText().toUpperCase(), 0);
                try {
                    DatabaseReader.addToDatabase(automobile);
                    ArrayList<Automobile> automobiles = DatabaseReader.readDatabase();
                    natalie.fillScrollPane(automobiles);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
        add(finishButton);
    }

    private JTextField createTextField(String text, Point p, Dimension d, MaskFormatter mask) {
        JTextField textField = new JFormattedTextField(mask);
        textField.setFocusable(false);
        textField.setText(text);
        textField.setName(text);
        textField.setLocation(p);
        textField.setSize(d);
        textField.addMouseListener(new MyMouseListener());
        getContentPane().add(textField);
        return textField;
    }
}
