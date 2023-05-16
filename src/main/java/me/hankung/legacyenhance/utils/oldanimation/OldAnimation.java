package me.hankung.legacyenhance.utils.oldanimation;

import me.hankung.legacyenhance.utils.oldanimation.handler.AnimationHandler;
import me.hankung.legacyenhance.utils.oldanimation.handler.SneakHandler;

public class OldAnimation {

    public AnimationHandler animationHandler;
    public SneakHandler sneakHandler;

    public void init() {
        animationHandler = new AnimationHandler();
        sneakHandler = new SneakHandler();
    }

    public void clientTick() {
        animationHandler.onTick();
        sneakHandler.onTick();
    }

}
