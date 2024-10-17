package dev.enjarai.mls.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.enjarai.mls.DrawContextWrapper;
import dev.enjarai.mls.config.ModConfig;
import dev.enjarai.mls.screens.LoadingScreen;
import dev.enjarai.mls.screens.SnowFlakesScreen;
import dev.enjarai.mls.screens.StackingScreen;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraftforge.client.loading.ForgeLoadingOverlay;
import net.minecraftforge.fml.earlydisplay.ColourScheme;
import net.minecraftforge.fml.earlydisplay.DisplayWindow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ForgeLoadingOverlay.class)
public abstract class ForgeLoadingOverlayMixin extends Overlay {
    /*
    @Shadow @Final private Minecraft minecraft;
    @Shadow private long fadeOutStart;
    @Unique
    private LoadingScreen moderateLoadingScreen$loadingScreen;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void moderateLoadingScreen$constructor(Minecraft mc, ReloadInstance reloader, Consumer errorConsumer, DisplayWindow displayWindow, CallbackInfo ci) {
        moderateLoadingScreen$loadingScreen = switch (ModConfig.screenType.get()) {
            case SNOWFLAKES -> new SnowFlakesScreen(this.minecraft);
            case STACKING -> new StackingScreen(this.minecraft);
        };
    }

    // 背景颜色变量填充
    @ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraftforge/fml/earlydisplay/ColourScheme;background()Lnet/minecraftforge/fml/earlydisplay/ColourScheme$Colour;"))
    private ColourScheme.Colour moderateLoadingScreen$changeColorGl(ColourScheme.Colour constant) {
        Color color = Color.getColor("logo", ModConfig.backgroundColor.get());
        return new ColourScheme.Colour(color.getRed(), color.getGreen(), color.getBlue());
    }

    // logo前注入图标
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", ordinal = 1, shift = At.Shift.BEFORE))
    private void moderateLoadingScreen$renderPatches(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        long k = Util.getMillis();
        float f = this.fadeOutStart > -1L ? (float)(k - this.fadeOutStart) / 1000.0F : -1.0F;
        moderateLoadingScreen$loadingScreen.renderPatches(new DrawContextWrapper(graphics), partialTick, f >= 1.0f);
    }

    // 渲染后重置透明度
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;defaultBlendFunc()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void moderateLoadingScreen$resetTransparency(net.minecraft.client.gui.GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci,
                                                         int i, int j, long l, float f, float g, float h) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, h);
    }
    // logo透明度
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0), index = 3)
    private float moderateLoadingScreen$modifyLogoTransparency(float fade) {
        return fade * ModConfig.logoOpacity.get() / 100f;
    }

    // 加载条透明度修改
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0), index = 5)
    private float moderateLoadingScreen$modifyBarTransparency(float original) {
        return original * ModConfig.barOpacity.get() / 100f;
    }
     */
}
