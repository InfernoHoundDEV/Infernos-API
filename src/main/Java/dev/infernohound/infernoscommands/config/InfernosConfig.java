package dev.infernohound.infernoscommands.config;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class InfernosConfig {

    public static Configuration config;

    // Commands
    public static boolean back;
    public static boolean home;
    public static boolean spawn;
    public static boolean tpa;
    public static boolean tplast;
    public static boolean rtp;
    public static boolean warp;

    // Homes
    public static int maxHomes;

    // Timestamps
    public static boolean timestamp;
    public static EnumChatFormatting timestampColor;

    // Random Teleport
    public static int randomRadius;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {

        // Commands
        config.addCustomCategoryComment("Commands", "Commands Setting");
        back = config.get("Commands", "back", true).getBoolean();
        home = config.get("Commands", "home", true).getBoolean();
        spawn = config.get("Commands", "spawn", true).getBoolean();
        tpa = config.get("Commands", "tpa", true).getBoolean();
        tplast = config.get("Commands", "tplast", true).getBoolean();
        rtp = config.get("Commands", "rtp", true).getBoolean();
        warp = config.get("Commands", "warp", true).getBoolean();

        // Homes
        config.addCustomCategoryComment("Home", "Homes Settings");
        maxHomes = config.get("homes", "maxHomes", 1).getInt();

        // Time Stamps
        config.addCustomCategoryComment("Timestamps", "Timestamp Settings");
        timestamp = config.get("Timestamps", "Timestamps", true).getBoolean();
        String timestampColorString = config.getString("color", "Timestamps", "yellow", "The color of time in the timestamps");
        timestampColor = EnumChatFormatting.getValueByName(timestampColorString);
        if (timestampColor == null) {
            timestampColor = EnumChatFormatting.YELLOW;
        }

        // Random Teleport
        config.addCustomCategoryComment("RandomTP", "Random Teleport Settings");
        randomRadius = config.getInt("x", "RandomTP", 2560000, 0, 2147483647, "Max radius of random teleport");

        config.save();
    }
}
