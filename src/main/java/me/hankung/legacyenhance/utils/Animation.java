package me.hankung.legacyenhance.utils;

public class Animation {
    public static float clamp(float number, float min, float max) {
        return number < min ? min : Math.min(number, max);
    }
}
