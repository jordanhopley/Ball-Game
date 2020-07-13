import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    private Color backgroundColor;
    private Color textColor;
    private String text;
    private float fontSize;
    private float rolloverSize;

    private Font font;
    private Font rolloverFont;

    public Button(Color backgroundColor, Color textColor, String text, float fontSize, float rolloverSize) {

        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.text = text;
        this.fontSize = fontSize;
        this.rolloverSize = rolloverSize;

        FontHandler fh = new FontHandler();

        font = fh.registerFont(fontSize);
        rolloverFont = fh.registerFont(rolloverSize);

        setBorderPainted(false);
        setFocusPainted(false);
        super.setContentAreaFilled(false);

    }

    @Override
    public void paintComponent(Graphics g) {

        setForeground(textColor);

        if (getModel().isRollover()) {
            setFont(rolloverFont);
        }
        else {
            setFont(font);
        }

        setText(text);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);

    }

}
