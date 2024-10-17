package dev.enjarai.mls;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.enjarai.mls.mixin.DrawContextAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

// This exists to provide a unified interface for rendering
public class DrawContextWrapper {
    /*? if >=1.20 {*/
    private final GuiGraphics context;
    public DrawContextWrapper(GuiGraphics context) {
        this.context = context;
    }

    public PoseStack matrices() {
        return context.pose();
    }

    public void drawTexturedQuad(ResourceLocation identifier, int x0, int x1, int y0, int y1) {
        ((DrawContextAccessor) context).loadingScreen$drawTexturedQuad(
                identifier,
                x0, x1, y0, y1, 0,
                0.0f, 1.0f, 0.0f, 1.0f
        );
    }
    /*?} else {*/
    /*
    private final MatrixStack stack;
    public DrawContextWrapper(MatrixStack stack) {
        this.stack = stack;
    }

    public MatrixStack matrices() {
        return stack;
    }
    public void drawTexturedQuad(Identifier identifier, int x0, int x1, int y0, int y1) {
        DrawContextAccessor.loadingScreen$drawTexturedQuad(
                matrices().peek().getPositionMatrix(),
                x0, x1, y0, y1, 0,
                0.0f, 1.0f, 0.0f, 1.0f
        );
    }
    /*?} */
}