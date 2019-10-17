package natalie.gui;

import natalie.AutoPanel;
import natalie.Automobile;
import natalie.DatabaseReader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class JAutoPanel extends JPanel {

    private JLabel manufacturer, model, color, year, vin;
    private JComboBox<String> colorBox;
    private JButton editButton, deleteButton;
    private Automobile automobile;
    private Natalie natalie;

    public JAutoPanel(Object object, Natalie natalie) {
        this.natalie = natalie;
        setUpRawPanel();
        if(object instanceof Automobile){
            fillAutomobile((Automobile) object);
        } else{
            fillAutoPanel((AutoPanel) object);
        }
    }

    private void fillAutomobile(Automobile automobile){
        this.automobile = automobile;

        manufacturer.setText(automobile.getManufacturer().toUpperCase());
        manufacturer.setSize(110, 25);

        model.setText(automobile.getModel().toUpperCase());
        model.setLocation(160, 35);

        year.setText(automobile.getYear().toUpperCase());
        vin.setText(automobile.getVin().toUpperCase());

        color = new JLabel(automobile.getColor().toUpperCase());
        color.setSize(150, 25);
        color.setLocation(390, 35);

        deleteButton = new JButton("Delete".toUpperCase());
        deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setSize(90, 20);
        deleteButton.setLocation(580, 70);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseReader.deleteFromDatabase(automobile);
                    ArrayList<Automobile> automobiles = DatabaseReader.readDatabase();
                    natalie.fillScrollPane(automobiles);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        editButton = new JButton("Edit".toUpperCase());
        editButton.setBackground(new Color(163, 0, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setSize(80, 20);
        editButton.setLocation(680, 70);
        editButton.setFocusable(false);
        editButton.addActionListener(new EditActionListener());

        add(color);
        add(editButton);
        add(deleteButton);
    }

    private void fillAutoPanel(AutoPanel autoPanel){
        manufacturer.setText(autoPanel.getManufacturer().toUpperCase());
        model.setText(autoPanel.getModel().toUpperCase());
        year.setText(autoPanel.getYear().toUpperCase());
        vin.setText(autoPanel.getVins().get(0).toUpperCase());

        String[] colors = autoPanel.getColors().toArray(new String[0]);
        colorBox = new JComboBox<String>(colors);
        colorBox.setFocusable(false);
        colorBox.setSize(150, 20);
        colorBox.setLocation(350, 35);
        colorBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = colorBox.getSelectedIndex();
                vin.setText(autoPanel.getVins().get(selectedIndex));
            }
        });
        add(colorBox);
    }

    private void setUpRawPanel() {
        setLayout(null);
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(blackLine);
        setPreferredSize(new Dimension(770, 100));
        setBackground(new Color(241, 237, 196));

        manufacturer = new JLabel();
        manufacturer.setLocation(20, 35);
        manufacturer.setSize(110, 25);

        model = new JLabel();
        model.setLocation(150, 35);
        model.setSize(170, 25);

        vin = new JLabel();
        vin.setLocation(620, 35);
        vin.setSize(170, 25);

        year = new JLabel();
        year.setLocation(550, 35);
        year.setSize(50, 25);

        add(manufacturer);
        add(model);
        add(vin);
        add(year);
    }

    private class EditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            manufacturer.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);

            MaskFormatter yearMask = new MaskFormatter(), vinMask = new MaskFormatter();
            try {
                yearMask.setMask("####");
                yearMask.setPlaceholder("Year");
                vinMask.setMask("UUUUU##UAAUU#####");
                vinMask.setPlaceholder("VIN");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            JTextField manufacturerField = createTextField(manufacturer.getText(),
                    new Point(20, 35), new Dimension(100, 25), null);
            JTextField modelField = createTextField(model.getText(),
                    new Point(150, 35), new Dimension(170, 25), null);
            JTextField colorField = createTextField(color.getText(),
                    new Point(390, 35), new Dimension(150, 25), null);
            JTextField yearField = createTextField(year.getText(),
                    new Point(550, 35), new Dimension(50, 25), yearMask);
            JTextField vinField = createTextField(vin.getText(),
                    new Point(620, 35), new Dimension(160, 25), vinMask);

            JButton finishButton = new JButton("Finish".toUpperCase());
            finishButton.setBackground(new Color(166, 120, 0));
            finishButton.setForeground(Color.WHITE);
            finishButton.setSize(80, 20);
            finishButton.setLocation(680, 70);
            finishButton.setFocusable(false);
            finishButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editLabel(manufacturerField, manufacturer, automobile.getManufacturer());
                    editLabel(modelField, model, automobile.getModel());
                    editLabel(colorField, color, automobile.getColor());
                    editLabel(yearField, year, automobile.getYear());
                    editLabel(vinField, vin, automobile.getVin());

                    manufacturer.setVisible(true);
                    deleteButton.setVisible(true);
                    finishButton.setVisible(false);
                    editButton.setVisible(true);

                    Automobile newAutomobile = new Automobile(manufacturer.getText(),
                            model.getText(), year.getText(), color.getText(),
                            vin.getText(), automobile.getSerialNumber());
                    try {
                        DatabaseReader.editInDatabase(newAutomobile);
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            });
            add(finishButton);
        }

        private void editLabel(JTextField field, JLabel label, String oldLabel) {
            if (!field.getText().equals(field.getName())) {
                label.setText(field.getText().toUpperCase());
            } else {
                label.setText(oldLabel.toUpperCase());
            }
            field.setVisible(false);
        }

        private JTextField createTextField(String text, Point p, Dimension d, MaskFormatter mask) {
            JTextField textField = new JFormattedTextField(mask);
            textField.setText(text);
            textField.setName(text);
            textField.setLocation(p);
            textField.setSize(d);
            add(textField);
            return textField;
        }
    }
}
