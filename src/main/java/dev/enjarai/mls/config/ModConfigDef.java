package dev.enjarai.mls.config;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// @Config(name = "moderate-loading-screen", wrapperName = "ModConfig")
public class ModConfigDef {

    //@SectionHeader("colors")

    public Color backgroundColor = Color.getHSBColor(16,16,16);

    //@RangeConstraint(min = 0, max = 100)
    public byte logoOpacity = 100;

    //@RangeConstraint(min = 0, max = 100)
    public byte barOpacity = 100;

    //@SectionHeader("iconOptions")

    public boolean showTater = false;

    public boolean modsOnlyOnce = false;

    public List<String> modIdBlacklist = new ArrayList<>(List.of(
            "forge",
            "minecraft"
    ));

    //@RangeConstraint(min = 8, max = 128)
    public int iconSize = 32;

    //@SectionHeader("screenTypes")

    public ScreenTypes screenType = ScreenTypes.STACKING;

    public Orientation orientation = Orientation.DOWN;

    //@Nest
    public StackingConfig stackingConfig = new StackingConfig();

    public static class StackingConfig {
        //@RangeConstraint(min = 1, max = 60)
        public int cycleSeconds = 20;
    }
}
