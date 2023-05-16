package me.hankung.legacyenhance.utils.oldanimation.handler;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.mixin.oldanimation.FoodItemAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WoolItem;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AnimationHandler {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    public float prevSwingProgress;
    public float swingProgress;
    private int swingProgressInt;
    private boolean isSwingInProgress;

    public float getSwingProgress(float partialTickTime) {
        float currentProgress = this.swingProgress - this.prevSwingProgress;
        if (!this.isSwingInProgress) {
            return this.mc.player.getHandSwingProgress(partialTickTime);
        } else {
            if (currentProgress < 0.0F) {
                ++currentProgress;
            }

            return this.prevSwingProgress + currentProgress * partialTickTime;
        }
    }

    private int getArmSwingAnimationEnd(ClientPlayerEntity player) {
        return player.hasStatusEffect(StatusEffect.HASTE)
                ? 5 - player.getEffectInstance(StatusEffect.HASTE).getAmplifier()
                : (player.hasStatusEffect(StatusEffect.MINING_FATIGUE)
                        ? 8 + player.getEffectInstance(StatusEffect.MINING_FATIGUE).getAmplifier() * 2
                        : 6);
    }

    private void updateSwingProgress() {
        ClientPlayerEntity player = this.mc.player;
        if (player != null) {
            this.prevSwingProgress = this.swingProgress;
            int max = this.getArmSwingAnimationEnd(player);
            if (LegacyEnhance.CONFIG.oldanimatePunching.get()
                    && this.mc.options.attackKey.isPressed()
                    && this.mc.result != null
                    && this.mc.result.type == Type.BLOCK
                    && (!this.isSwingInProgress || this.swingProgressInt >= max >> 1 || this.swingProgressInt < 0)) {
                this.isSwingInProgress = true;
                this.swingProgressInt = -1;
            }

            if (this.isSwingInProgress) {
                ++this.swingProgressInt;
                if (this.swingProgressInt >= max) {
                    this.swingProgressInt = 0;
                    this.isSwingInProgress = false;
                }
            } else {
                this.swingProgressInt = 0;
            }

            this.swingProgress = (float) this.swingProgressInt / (float) max;
        }
    }

    public void onTick() {
        this.updateSwingProgress();
    }

    public boolean renderItemInFirstPerson(HeldItemRenderer renderer, ItemStack stack, float equipProgress,
            float partialTicks) {
        if (stack == null) {
            return false;
        } else {
            Item item = stack.getItem();
            if (item != Items.FILLED_MAP && !this.mc.getItemRenderer().hasDepth(stack)) {
                UseAction action = stack.getUseAction();
                if ((item != Items.FISHING_ROD || LegacyEnhance.CONFIG.oldanimateOldRod.get())
                        && (action != UseAction.NONE || LegacyEnhance.CONFIG.oldanimateOldModel.get())
                        && (action != UseAction.BLOCK || LegacyEnhance.CONFIG.oldanimateOldSwordBlock.get())
                        && (action != UseAction.BOW || LegacyEnhance.CONFIG.oldanimateOldBow.get())) {
                    ClientPlayerEntity player = this.mc.player;
                    float var4 = player.prevPitch + (player.pitch - player.prevPitch) * partialTicks;
                    GlStateManager.pushMatrix();
                    GlStateManager.rotate(var4, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(
                            player.prevYaw + (player.yaw - player.prevYaw) * partialTicks, 0.0F,
                            1.0F, 0.0F);
                    DiffuseLighting.enableNormally();
                    GlStateManager.popMatrix();
                    float pitch = player.lastRenderPitch + (player.renderPitch - player.lastRenderPitch) * partialTicks;
                    float yaw = player.lastRenderYaw + (player.renderYaw - player.lastRenderYaw) * partialTicks;
                    GlStateManager.rotate((player.pitch - pitch) * 0.1F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate((player.yaw - yaw) * 0.1F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.enableRescaleNormal();
                    if (item instanceof WoolItem) {
                        GlStateManager.enableBlend();
                        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
                    }

                    int i = this.mc.world.getLight(new BlockPos(player.x,
                            player.y + (double) player.getEyeHeight(), player.z), 0);
                    float brightnessX = (float) (i & 65535);
                    float brightnessY = (float) (i >> 16);
                    GLX.gl13MultiTexCoord2f(GLX.lightmapTextureUnit, brightnessX, brightnessY);
                    int rgb = item.getDisplayColor(stack, 0);
                    float red = (float) (rgb >> 16 & 0xFF) / 255.0F;
                    float green = (float) (rgb >> 8 & 0xFF) / 255.0F;
                    float blue = (float) (rgb & 0xFF) / 255.0F;
                    GlStateManager.color(red, green, blue, 1.0F);
                    GlStateManager.pushMatrix();
                    int useCount = player.getItemUseTicks();
                    float swingProgress = this.getSwingProgress(partialTicks);
                    boolean blockHitOverride = false;
                    if (LegacyEnhance.CONFIG.oldanimatePunching.get() && useCount <= 0
                            && this.mc.options.useKey.isPressed()) {
                        boolean block = action == UseAction.BLOCK;
                        boolean consume = false;
                        if (item instanceof FoodItem) {
                            boolean alwaysEdible = ((FoodItemAccessor) item).getAlwaysEdible();
                            if (player.canConsume(alwaysEdible)) {
                                consume = action == UseAction.EAT || action == UseAction.DRINK;
                            }
                        }

                        if (block || consume) {
                            blockHitOverride = true;
                        }
                    }

                    if ((useCount > 0 || blockHitOverride) && action != UseAction.NONE
                            && this.mc.player.isUsingItem()) {
                        switch (action) {
                            case EAT:
                            case DRINK:
                                this.doConsumeAnimation(stack, useCount, partialTicks);
                                this.doEquipAndSwingTransform(equipProgress,
                                        LegacyEnhance.CONFIG.oldanimateOldBlockHit.get() ? swingProgress : 0.0F);
                                break;
                            case BLOCK:
                                this.doEquipAndSwingTransform(equipProgress,
                                        LegacyEnhance.CONFIG.oldanimateOldBlockHit.get() ? swingProgress : 0.0F);
                                this.doSwordBlockAnimation();
                                break;
                            case BOW:
                                this.doEquipAndSwingTransform(equipProgress,
                                        LegacyEnhance.CONFIG.oldanimateOldBlockHit.get() ? swingProgress : 0.0F);
                                this.doBowAnimation(stack, useCount, partialTicks);
                            default:
                                break;
                        }
                    } else {
                        this.doSwingTranslation(swingProgress);
                        this.doEquipAndSwingTransform(equipProgress, swingProgress);
                    }

                    if (item.shouldRotate()) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    }

                    if (this.doFirstPersonTransform(stack)) {
                        renderer.renderItem(player, stack, Mode.FIRST_PERSON);
                    } else {
                        renderer.renderItem(player, stack, Mode.NONE);
                    }

                    GlStateManager.popMatrix();
                    if (item instanceof WoolItem) {
                        GlStateManager.disableBlend();
                    }

                    GlStateManager.disableRescaleNormal();
                    DiffuseLighting.disable();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    public void doSwordBlock3rdPersonTransform() {
        if (LegacyEnhance.CONFIG.oldanimateOldSwordBlock3rd.get()) {
            GlStateManager.translate(-0.15F, -0.2F, 0.0F);
            GlStateManager.rotate(70.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.119F, 0.2F, -0.024F);
        }
    }

    private boolean doFirstPersonTransform(ItemStack stack) {
        switch (stack.getUseAction()) {
            case EAT:
            case DRINK:
                if (!LegacyEnhance.CONFIG.oldanimateOldEating.get()) {
                    return true;
                }
                break;
            case BLOCK:
                if (!LegacyEnhance.CONFIG.oldanimateOldSwordBlock.get()) {
                    return true;
                }
                break;
            case BOW:
                if (!LegacyEnhance.CONFIG.oldanimateOldBow.get()) {
                    return true;
                }
                break;
            case NONE:
                if (!LegacyEnhance.CONFIG.oldanimateOldModel.get()) {
                    return true;
                }
        }

        GlStateManager.translate(0.58800083F, 0.36999986F, -0.77000016F);
        GlStateManager.translate(0.0F, -0.3F, 0.0F);
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
        GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
        GlStateManager.scale(-2.0F, 2.0F, -2.0F);
        if (this.mc.getItemRenderer().hasDepth(stack)) {
            GlStateManager.scale(0.58823526F, 0.58823526F, 0.58823526F);
            GlStateManager.rotate(-25.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.25F, -0.125F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            return true;
        } else {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            return false;
        }
    }

    private void doConsumeAnimation(ItemStack stack, int useCount, float partialTicks) {
        if (LegacyEnhance.CONFIG.oldanimateOldEating.get()) {
            float useAmount = (float) useCount - partialTicks + 1.0F;
            float useAmountNorm = 1.0F - useAmount / (float) stack.getMaxUseTime();
            float useAmountPow = 1.0F - useAmountNorm;
            useAmountPow = useAmountPow * useAmountPow * useAmountPow;
            useAmountPow = useAmountPow * useAmountPow * useAmountPow;
            useAmountPow = useAmountPow * useAmountPow * useAmountPow;
            float useAmountFinal = 1.0F - useAmountPow;
            GlStateManager.translate(
                    0.0F, MathHelper.abs(MathHelper.cos(useAmount / 4.0F * (float) Math.PI) * 0.1F)
                            * (float) ((double) useAmountNorm > 0.2 ? 1 : 0),
                    0.0F);
            GlStateManager.translate(useAmountFinal * 0.6F, -useAmountFinal * 0.5F, 0.0F);
            GlStateManager.rotate(useAmountFinal * 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(useAmountFinal * 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(useAmountFinal * 30.0F, 0.0F, 0.0F, 1.0F);
        } else {
            float f = (float) useCount - partialTicks + 1.0F;
            float f1 = f / (float) stack.getMaxUseTime();
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);
            if (f1 >= 0.8F) {
                f2 = 0.0F;
            }

            GlStateManager.translate(0.0F, f2, 0.0F);
            float f3 = 1.0F - (float) Math.pow((double) f1, 27.0);
            GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
            GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    private void doSwingTranslation(float swingProgress) {
        float swingProgress2 = MathHelper.sin(swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
        GlStateManager.translate(-swingProgress3 * 0.4F,
                MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI * 2.0F) * 0.2F,
                -swingProgress2 * 0.2F);
    }

    private void doEquipAndSwingTransform(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56F, -0.52F - (1.0F - equipProgress) * 0.6F, -0.72F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float swingProgress2 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float swingProgress3 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(-swingProgress2 * 20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-swingProgress3 * 20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-swingProgress3 * 80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void doSwordBlockAnimation() {
        // GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.translate(-0.5F, 0.3F, -0.1F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    private void doBowAnimation(ItemStack stack, int useCount, float partialTicks) {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float totalPullback = (float) stack.getMaxUseTime() - ((float) useCount - partialTicks + 1.0F);
        float pullbackNorm = totalPullback / 20.0F;
        pullbackNorm = (pullbackNorm * pullbackNorm + pullbackNorm * 2.0F) / 3.0F;
        if (pullbackNorm > 1.0F) {
            pullbackNorm = 1.0F;
        }

        if (pullbackNorm > 0.1F) {
            GlStateManager.translate(0.0F,
                    MathHelper.sin((totalPullback - 0.1F) * 1.3F) * 0.01F * (pullbackNorm - 0.1F), 0.0F);
        }

        GlStateManager.translate(0.0F, 0.0F, pullbackNorm * 0.1F);
        if (LegacyEnhance.CONFIG.oldanimateOldBow.get()) {
            GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
        }

        float zScale = 1.0F + pullbackNorm * 0.2F;
        GlStateManager.scale(1.0F, 1.0F, zScale);
        if (LegacyEnhance.CONFIG.oldanimateOldBow.get()) {
            GlStateManager.translate(0.0F, -0.5F, 0.0F);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }
}