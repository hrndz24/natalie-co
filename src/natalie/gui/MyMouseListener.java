package natalie.gui;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // clears the field if there is default text
        JTextComponent component = (JTextComponent) e.getSource();
        component.setFocusable(true);
        if (component.getName().equals(component.getText())) {
            component.setText("");
        }
        component.setBackground(Color.WHITE);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // shows cursor on the field
        JTextComponent component = (JTextComponent) e.getSource();
        component.setFocusable(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JTextComponent component = (JTextComponent) e.getSource();
        if (component.getText().equals("")) {
            component.setText(component.getName());
        }
        component.setFocusable(false);
    }
}