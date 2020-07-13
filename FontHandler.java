import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class FontHandler {

    private Font font;

    public FontHandler() {

    }

    public Font registerFont(float fontSize) {

        try {
            URL fonturl = getClass().getResource("Fonts\\ka2.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fonturl.openStream()).deriveFont(fontSize);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (IOException | FontFormatException e) { e.printStackTrace(); }

        return font;

    }

    public Font getFont() {
        return font;
    }

}
