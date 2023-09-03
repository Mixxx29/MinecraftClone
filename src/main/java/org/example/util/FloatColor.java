package org.example.util;

import java.awt.*;

public class FloatColor {
    private final Color color;

    public FloatColor(Color color) {
        this.color = color;
    }

    public float getRed() {
        return color.getRed() / 255.0f;
    }

    public float getGreen() {
        return color.getGreen() / 255.0f;
    }

    public float getBlue() {
        return color.getBlue() / 255.0f;
    }

    public float getAlpha() {
        return color.getAlpha() / 255.0f;
    }
}
