package dev.infernohound.infernoscommands.command;

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

public class CmdWarp extends CommandBase {
    @Override
    public String getCommandName() {
        return "warp";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/warp <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer player = getPlayer(ics, ics.getCommandSenderName());
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        WorldData data = WorldData.getSave(player.worldObj);
        if (args.length == 0) {
            player.addChatMessage(new ChatComponentText("Please give a name for the warp").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }
        if (args.length <= 1) {
            BlockDimPos pos = data.getWarps().get(args[0].toLowerCase());
            if (args[0].equalsIgnoreCase("list")) {
                Warp list = data.getWarps();
                if (list == null || list.size() <= 0) {
                    player.addChatMessage(new ChatComponentText("No Warps Exist").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                } else {
                    for (String str : list.keySet()) {
                        player.addChatMessage(new ChatComponentText(str).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
                    }
                }
                return;
            }
            if (pos == null) {
                player.addChatMessage(new ChatComponentText("Warp " + args[0].toLowerCase() + " not found.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }
            p.setLastPos(p.getCurrentPos());
            InfernoTeleporter.teleport(player, pos);
            player.addChatMessage(new ChatComponentText("Teleported to " + args[0].toLowerCase()));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender ics, String[] args) {
        EntityPlayer player = getPlayer(ics, ics.getCommandSenderName());
        WorldData data = WorldData.getSave(player.worldObj);
        return getListOfStringsMatchingLastWord(args,data.getWarps().keyArray());
    }
}
