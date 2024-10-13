package dev.enjarai.mls;

import dev.enjarai.mls.config.ModConfigDef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.InputSupplier;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.resource.ResourcePackLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod("mls")
public class ModerateLoadingScreen {
    public static final String MODID = "mls";
    public static final ModConfigDef CONFIG = new ModConfigDef();


    public static ArrayList<Identifier> getIcon(){
        ArrayList<Identifier> result = new ArrayList<>();

        for (IModInfo modInfo : ModList.get().getMods()) {
            String modId = modInfo.getModId();

            List<String> list = CONFIG.modIdBlacklist;
            if(CONFIG.modIdBlacklist.stream().anyMatch(Predicate.isEqual(modId))) continue;

            ModContainer modContainer = ModList.get().getModContainerById(modId).orElse(null);
            String icon = modContainer.getModInfo().getLogoFile().orElse(null);
            if (icon == null) continue;

            TextureManager tm = MinecraftClient.getInstance().getTextureManager();
            final PathPackResources resourcePack = ResourcePackLoader.getPackFor(modInfo.getModId())
                    .orElse(ResourcePackLoader.getPackFor("forge").
                            orElseThrow(()->new RuntimeException("Can't find forge, WHAT!")));

            try {
                NativeImage logo;
                InputSupplier<InputStream> logoResource = resourcePack.openRoot(icon);
                if (logoResource != null) {
                    logo = NativeImage.read(logoResource.get());
                    result.add(tm.registerDynamicTexture("modlogo", new NativeImageBackedTexture(logo) {
                        @Override
                        public void upload() {
                            this.bindTexture();
                            NativeImage td = this.getImage();
                            // Use custom "blur" value which controls texture filtering (nearest-neighbor vs linear)
                            this.getImage().upload(0, 0, 0, 0, 0, td.getWidth(), td.getHeight(), modInfo.getLogoBlur(), false, false, false);
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

    public static Identifier id(String path) {
        /*? if >=1.21 {*//*
        return Identifier.of(MODID, path);
        *//*?} else {*/
        return new Identifier(MODID, path);
        /*?} */
    }
}
