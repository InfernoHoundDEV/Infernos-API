package dev.infernohound.infernoscommands;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import dev.infernohound.infernoscommands.command.*;
import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.event.PlayerListener;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = InfernosCommands.MODID, version = InfernosCommands.VERSION)
public class InfernosCommands {
    public static final String MODID = "infernoscommands";
    public static final String VERSION = "1.1.2";


    private static Logger logger;

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
        InfernosConfig.init(event.getSuggestedConfigurationFile());
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
