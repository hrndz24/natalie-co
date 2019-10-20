package natalie.gui;

import javax.swing.*;
import java.awt.*;

public class RoundedCornerButton extends JButton {

    private  int radius = 10;
    private Color pressedColor = Color.CYAN;

    public RoundedCornerButton(String title) {
        super(title);
        init();

    }
    public RoundedCornerButton() {
        super();
        init();
    }

    private void init() {
        setBorder(null);
        setFocusable(false);
    }

    public void paintComponent(Graphics g) {

        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        Graphics2D g2d = (Graphics2D)g;

        if (getModel().isPressed()) {
            g2d.setColor(pressedColor);
        }else {
            g2d.setColor(getBackground());
        }

        // Anti-aliased lines and text
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        g2d.setColor(getBackground());
        g2d.drawRoundRect(0,0,getWidth(),getHeight(),radius,radius);

        // This is needed on non-Mac so text
        // is repainted correctly!
        super.paintComponent(g);
    }

    public void setPressedColor(Color color){
        this.pressedColor = color;
    }

    public void setForeground(Color color){
        super.setForeground(color);
    }
}
