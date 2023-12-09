package src.controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class CustomFont {

    private static final String FONT_PATH = "resources\\font\\m29.TTF";

    static {
        registerFont();
    }

    public static Font loadFont(float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH));

            return customFont.deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    private static void registerFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH)));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}

