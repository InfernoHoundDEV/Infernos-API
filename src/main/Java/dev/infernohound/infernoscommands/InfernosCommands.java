package dev.infernohound.infernoscommands;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import dev.infernohound.infernoscommands.command.*;
import dev.infernohound.infernoscommands.event.PlayerListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = InfernosCommands.MODID, version = InfernosCommands.VERSION)
public class InfernosCommands {
    public static final String MODID = "infernoscommands";
    public static final String VERSION = "1.1.0";

    public static Configuration config;

//    public static boolean back = true;
//    public static boolean home = true;
//    public static boolean spawn = true;
//    public static boolean tpa = true;
//    public static boolean tplast = true;
//    public static boolean rtp = true;
//    public static boolean warp = true;
    public static int maxHomes = 1;
    public static int rPosX = 5000;
    public static int rPosZ = 5000;

    public static Logger logger;

    @Mod.Instance
    public InfernosCommands instance = null;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        FMLCommonHandler.instance().bus().register(new PlayerListener());
    }

    @Mod.EventHandler
    public void perInit(FMLPreInitializationEvent event) {

        logger = LogManager.getLogger(MODID);

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
//        config.addCustomCategoryComment("commands", "Commands");
        config.addCustomCategoryComment("rtp", "Random Teleport");
        config.addCustomCategoryComment("homes", "Homes");

//        back = config.get("commands", "back", true).getBoolean();
//        home = config.get("commands", "home", true).getBoolean();
//        spawn = config.get("commands", "spawn", true).getBoolean();
//        tpa = config.get("commands", "tpa", true).getBoolean();
//        tplast = config.get("commands", "tplast", true).getBoolean();
//        rtp = config.get("commands", "rtp", true).getBoolean();
//        warp = config.get("commands", "warp", true).getBoolean();
        maxHomes = config.get("homes", "maxHomes", 1).getInt();
        Property rotPosX = config.get("rtp", "rPosX", 5000);
        rotPosX.comment = "Max x direction range for rtp (min = 100, max = 100000)";
        rPosX = rotPosX.getInt();
        Property rotPosZ = config.get("rtp", "rPosY", 5000);
        rotPosZ.comment = "Max z direction range for rtp (min = 100, max = 100000)";
        rPosZ = rotPosZ.getInt();

        config.save();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdBack());
        event.registerServerCommand(new CmdDelHome());
        event.registerServerCommand(new CmdDelWarp());
        event.registerServerCommand(new CmdHome());
        event.registerServerCommand(new CmdRandomTP());
        event.registerServerCommand(new CmdSetHome());
       event.registerServerCommand(new CmdSetWarp());
        event.registerServerCommand(new CmdSpawn());
        event.registerServerCommand(new CmdTPA());
        event.registerServerCommand(new CmdTPAccept());
        event.registerServerCommand(new CmdTPAHere());
        event.registerServerCommand(new CmdTPDeny());
        event.registerServerCommand(new CmdWarp());
    }

}
