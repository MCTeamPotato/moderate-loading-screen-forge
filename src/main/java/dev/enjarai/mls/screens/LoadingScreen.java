package dev.enjarai.mls.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.MatrixUtil;
import dev.enjarai.mls.DrawContextWrapper;
import dev.enjarai.mls.ModerateLoadingScreen;
import dev.enjarai.mls.config.ModConfig;
import dev.enjarai.mls.config.Orientation;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Random;

public abstract class LoadingScreen {
    protected final int patchSize = ModConfig.iconSize.get();
    protected final Orientation orientation = ModConfig.orientation.get();
    protected final Minecraft client;
    protected final ArrayList<ResourceLocation> icons;
    protected final Random random = new Random();
    protected final ArrayList<Patch> patches = new ArrayList<>();
    protected double patchTimer = 0f;
    //protected boolean tater = ModerateLoadingScreen.CONFIG.showTater;
    protected boolean modsOnlyOnce = ModConfig.modsOnlyOnce.get();

    public LoadingScreen(Minecraft client) {
        this.client = client;

        icons = ModerateLoadingScreen.getIcon();
    }

    public abstract void createPatch(ResourceLocation texture);

    protected ResourceLocation getNextTexture() {
        // Summon the holy tater if enabled
        /*
        if (tater) {
            tater = false;
            return ModerateLoadingScreen.id("textures/gui/tiny_potato.png");
        }
         */

        return icons.get(random.nextInt(icons.size()));
    }

    public void updatePatches(float delta, boolean ending) {
        processPhysics(delta, ending);

        if (!icons.isEmpty()) {
            patchTimer -= delta;

            if (patchTimer < 0f && !ending) {
                ResourceLocation icon = getNextTexture();

                if (modsOnlyOnce) {
                    icons.remove(icon);
                }
                createPatch(icon);

                patchTimer = getPatchTimer();
            }
        }
    }

    protected double getPatchTimer() {
        return random.nextFloat();
    }

    protected double getOffsetX() {
        return 0;
    }

    protected double getOffsetY() {
        return 0;
    }

    protected int getScreenWidth() {
        return orientation.switchAxes ? client.getWindow().getGuiScaledHeight() : client.getWindow().getGuiScaledWidth();
    }

    protected int getScreenHeight() {
        return orientation.switchAxes ? client.getWindow().getGuiScaledWidth() : client.getWindow().getGuiScaledHeight();
    }

    protected void processPhysics(float delta, boolean ending) {
        for (Patch patch : patches) {
            if (ending)
                patch.fallSpeed *= 1.0 + delta / 3;

            patch.update(delta);
        }
    }

    public void renderPatches(DrawContextWrapper wrapper, float delta, boolean ending) {
        // spike prevention
        if (delta < 2.0f)
            updatePatches(delta, ending);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (Patch patch : patches) {
            RenderSystem.setShaderTexture(0, patch.texture);
            patch.render(wrapper, getOffsetX(), getOffsetY());
        }
    }

    protected class Patch {
        protected double x, y, rot;
        protected final ResourceLocation texture;

        protected final double horizontal, rotSpeed;
        protected final double scale;

        public double fallSpeed;

        protected final int patchSize;

        public Patch(double x, double y, double rot, double horizontal, double fallSpeed, double rotSpeed, double scale, ResourceLocation texture, int patchSize) {
            this.x = x;
            this.y = y;
            this.rot = rot;

            this.horizontal = horizontal;
            this.fallSpeed = fallSpeed;
            this.rotSpeed = rotSpeed;

            this.scale = scale;

            this.texture = texture;

            this.patchSize = patchSize;
        }

        public void update(float delta) {
            x += horizontal * delta;
            y += fallSpeed * delta;

            rot += rotSpeed * delta;
        }

        public void render(DrawContextWrapper wrapper, double offsetX, double offsetY) {
            PoseStack matrices = wrapper.matrices();
            matrices.pushPose();
            if (orientation.switchAxes) {
                matrices.translate(
                        perhapsInvert(y + offsetY, getScreenHeight()),
                        perhapsInvert(x + offsetX, getScreenWidth()),
                        0
                );
            } else {
                matrices.translate(
                        perhapsInvert(x + offsetX, getScreenWidth()),
                        perhapsInvert(y + offsetY, getScreenHeight()),
                        0
                );
            }

            Matrix4f matrix = matrices.last().pose();
            MatrixUtil.mulComponentWise(matrix.rotate((float) rot * 0.017453292F, 0, 0, 1), (float) scale);

            double x1 = -patchSize / 2d;
            double y1 = -patchSize / 2d;
            double x2 = patchSize / 2d;
            double y2 = patchSize / 2d;

            wrapper.drawTexturedQuad(texture, (int) x1, (int) x2, (int) y1, (int) y2);
            matrices.popPose();
        }

        private double perhapsInvert(double value, int fullSize) {
            return orientation.reverseAxes ? fullSize - value : value;
        }
    }
}
