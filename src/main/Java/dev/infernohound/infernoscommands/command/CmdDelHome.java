package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CmdDelHome extends CommandBase {
    @Override
    public String getCommandName() {
        return "delhome";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/delhome <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);
        if (args.length == 0) {
            args = new String[]{"home"};
        }
        if (args.length <= 1) {
            playerData.getHomes().set(args[0].toLowerCase(), null);
            entityPlayer.addChatMessage(new ChatComponentText("Deleted home " + args[0]));
            NBTTagCompound tag = new NBTTagCompound();
            playerData.saveNBTData(tag);
            WorldData.setProxyPlayerData(entityPlayer.getUniqueID(), tag);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return InfernosConfig.home;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);;
        return getListOfStringsMatchingLastWord(args,playerData.getHomes().keyArray());
    }
}
