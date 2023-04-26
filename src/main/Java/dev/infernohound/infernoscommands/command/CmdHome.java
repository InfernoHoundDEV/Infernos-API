package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.Warp;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;
import java.util.Set;

public class CmdHome extends CommandBase {
    @Override
    public String getCommandName() {
        return "home";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/home <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());
        PlayerData playerData = PlayerData.get(entityPlayer);
        if (args.length == 0) {
            args = new String[]{"home"};
        }
        if (args.length <= 1) {
            BlockDimPos pos = playerData.getHomes().get(args[0].toLowerCase());
            if (args[0].equalsIgnoreCase("list")) {
                Set<String> list = playerData.getHomes().keySet();
                if (list == null || list.size() <= 0) {
                    entityPlayer.addChatMessage(new ChatComponentText("No Homes Exist").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                } else {
                    for (String str : list) {
                        entityPlayer.addChatMessage(new ChatComponentText(str).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
                    }
                }
                return;
            }
            if (pos == null) {
                entityPlayer.addChatMessage(new ChatComponentText("Home " + args[0].toLowerCase() + " not found.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }
            playerData.setLastPosToCurrentPos();
            InfernoTeleporter.teleport(entityPlayer, pos);
            entityPlayer.addChatMessage(new ChatComponentText("Teleported to " + args[0].toLowerCase()));
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
        PlayerData p = PlayerData.get(entityPlayer);
        return getListOfStringsMatchingLastWord(args,p.getHomes().keyArray());
    }
}
