package dev.enjarai.mls.config;


import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder push = BUILDER.push("Moderate Loading Screen");
    public static final ForgeConfigSpec.ConfigValue<Integer> backgroundColor = BUILDER.define("backgroundColor", 0x161616);
    public static final ForgeConfigSpec.ConfigValue<Integer> logoOpacity = BUILDER.defineInRange("logoOpacity", 100, 0, 100);
    public static final ForgeConfigSpec.ConfigValue<Integer> barOpacity = BUILDER.defineInRange("barOpacity", 100, 0, 100);
    public static final ForgeConfigSpec.BooleanValue modsOnlyOnce = BUILDER.define("modsOnlyOnce", false);
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> modIdBlacklist = BUILDER.defineList("modIdBlacklist", Arrays.asList(
            "forge",
            "minecraft"
    ), o -> o instanceof String);

    public static final ForgeConfigSpec.ConfigValue<Integer> iconSize = BUILDER.define("iconSize", 32);
    public static final ForgeConfigSpec.EnumValue<ScreenTypes> screenType = BUILDER.defineEnum("screenType", ScreenTypes.STACKING);
    public static final ForgeConfigSpec.EnumValue<Orientation> orientation = BUILDER.defineEnum("orientation", Orientation.DOWN);
    public static final ForgeConfigSpec.ConfigValue<Integer> cycleSeconds = BUILDER.define("cycleSeconds", 20);
    private static final ForgeConfigSpec.Builder pop = BUILDER.pop();
    public static final ForgeConfigSpec SPEC = BUILDER.build();

}
