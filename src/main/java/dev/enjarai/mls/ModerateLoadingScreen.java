package dev.enjarai.mls;

import com.mojang.blaze3d.platform.NativeImage;
import dev.enjarai.mls.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.resource.ResourcePackLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.function.Predicate;

import static dev.enjarai.mls.config.ModConfigScreen.FACTORY;

@Mod("mls")
public class ModerateLoadingScreen {
    public static final String MODID = "mls";
    public ModerateLoadingScreen(){
        ModLoadingContext.get().registerExtensionPoint(FACTORY.getClass(), () -> FACTORY);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, ModConfig.SPEC);
    }

    public static ArrayList<ResourceLocation> getIcon(){
        ArrayList<ResourceLocation> result = new ArrayList<>();

        for (IModInfo modInfo : ModList.get().getMods()) {
            String modId = modInfo.getModId();

            if(ModConfig.modIdBlacklist.get().stream().anyMatch(Predicate.isEqual(modId))) continue;

            ModContainer modContainer = ModList.get().getModContainerById(modId).orElse(null);
            String icon = modContainer.getModInfo().getLogoFile().orElse(null);
            if (icon == null) continue;

            TextureManager tm = Minecraft.getInstance().getTextureManager();
            final PathPackResources resourcePack = ResourcePackLoader.getPackFor(modInfo.getModId())
                    .orElse(ResourcePackLoader.getPackFor("forge").
                            orElseThrow(()->new RuntimeException("Can't find forge, WHAT!")));

            try {
                NativeImage logo;
                IoSupplier<InputStream> logoResource = resourcePack.getRootResource(icon);
                if (logoResource != null) {
                    logo = NativeImage.read(logoResource.get());
                    result.add(tm.register("modlogo", new DynamicTexture(logo) {
                        @Override
                        public void upload() {
                            this.bind();
                            NativeImage td = this.getPixels();
                            // Use custom "blur" value which controls texture filtering (nearest-neighbor vs linear)
                            this.getPixels().upload(0, 0, 0, 0, 0, td.getWidth(), td.getHeight(), modInfo.getLogoBlur(), false, false, false);
                        }
                    }));
                }
            } catch (IOException ignored) {}
        }
        return result;
    }

    /*
    private static NativeImageBackedTexture getIconTexture(ModContainer iconSource, String iconPath) {
        try {
            Path path = iconSource.getPath(iconPath);
            try (InputStream inputStream = Files.newInputStream(path)) {
                NativeImage image = NativeImage.read(Objects.requireNonNull(inputStream));
                Validate.validState(image.getHeight() == image.getWidth(), "Must be square icon");
                return new NativeImageBackedTexture(image);
            }

        } catch (Throwable t) {
            return null;
        }
    }
     */


    // https://stackoverflow.com/questions/45321050/java-string-matching-with-wildcards
    private static String createRegexFromGlob(String glob) {
        StringBuilder out = new StringBuilder("^");
        for(int i = 0; i < glob.length(); ++i) {
            final char c = glob.charAt(i);
            switch (c) {
                case '*' -> out.append(".*");
                case '?' -> out.append('.');
                case '.' -> out.append("\\.");
                case '\\' -> out.append("\\\\");
                default -> out.append(c);
            }
        }
        out.append('$');
        return out.toString();
    }

    public static ResourceLocation id(String path) {
        /*? if >=1.21 {*//*
        return Identifier.of(MODID, path);
        *//*?} else {*/
        return new ResourceLocation(MODID, path);
        /*?} */
    }
}
