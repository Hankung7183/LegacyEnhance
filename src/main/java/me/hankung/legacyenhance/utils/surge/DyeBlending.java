package me.hankung.legacyenhance.utils.surge;

import net.minecraft.util.DyeColor;

public class DyeBlending {

    private static final DyeColor[][] BLENDABLE_DYE_COLORS = new DyeColor[16][16];

    static {
        BLENDABLE_DYE_COLORS[15][8] = DyeColor.SILVER;
        BLENDABLE_DYE_COLORS[15][4] = DyeColor.LIGHT_BLUE;
        BLENDABLE_DYE_COLORS[15][2] = DyeColor.LIME;
        BLENDABLE_DYE_COLORS[15][1] = DyeColor.PINK;
        BLENDABLE_DYE_COLORS[15][0] = DyeColor.GRAY;
        BLENDABLE_DYE_COLORS[11][1] = DyeColor.ORANGE;
        BLENDABLE_DYE_COLORS[9][5] = DyeColor.MAGENTA;
        BLENDABLE_DYE_COLORS[8][15] = DyeColor.SILVER;
        BLENDABLE_DYE_COLORS[5][9] = DyeColor.MAGENTA;
        BLENDABLE_DYE_COLORS[4][15] = DyeColor.LIGHT_BLUE;
        BLENDABLE_DYE_COLORS[4][2] = DyeColor.CYAN;
        BLENDABLE_DYE_COLORS[4][1] = DyeColor.PURPLE;
        BLENDABLE_DYE_COLORS[2][15] = DyeColor.LIME;
        BLENDABLE_DYE_COLORS[2][4] = DyeColor.CYAN;
        BLENDABLE_DYE_COLORS[1][15] = DyeColor.PINK;
        BLENDABLE_DYE_COLORS[1][11] = DyeColor.ORANGE;
        BLENDABLE_DYE_COLORS[1][4] = DyeColor.PURPLE;
        BLENDABLE_DYE_COLORS[0][15] = DyeColor.GRAY;
    }

    public static DyeColor getBlendedColor(DyeColor first, DyeColor second) {
        return BLENDABLE_DYE_COLORS[first.getSwappedId()][second.getSwappedId()];
    }

}
