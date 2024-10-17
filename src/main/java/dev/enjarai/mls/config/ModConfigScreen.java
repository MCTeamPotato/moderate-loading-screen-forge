package dev.enjarai.mls.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.Util;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModConfigScreen {

    public static final ConfigScreenHandler.ConfigScreenFactory FACTORY =
            new ConfigScreenHandler.ConfigScreenFactory(ModConfigScreen::openConfig);

    public static Screen openConfig(Minecraft mc, Screen screen) {
        Util.getPlatform().openFile(
                FMLPaths.CONFIGDIR.get().resolve("mls-client.toml").toFile()
        );
        return screen;
    }

}
