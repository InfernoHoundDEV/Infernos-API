package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.InfernosCommands;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CmdSetHome extends CommandBase {
    @Override
    public String getCommandName() {
        return "sethome";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/sethome <ID>";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayer player = getPlayer(ics, ics.getCommandSenderName());
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        if (args.length == 0) {
            args = new String[]{"home"};
        }
        if (args.length <= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                player.addChatMessage(new ChatComponentText("Can not have a home named list").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }
            int maxHomes = InfernosCommands.maxHomes;
            if(maxHomes <= 0 || (p.getHomes().size() >= maxHomes && !p.getHomes().keySet().contains(args[0].toLowerCase()))) {
                player.addChatMessage(new ChatComponentText("Already have the maximum number of homes!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            p.getHomes().set(args[0].toLowerCase(), p.getCurrentPos());
            player.addChatMessage(new ChatComponentText("Created home " + args[0].toLowerCase()));
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
}
