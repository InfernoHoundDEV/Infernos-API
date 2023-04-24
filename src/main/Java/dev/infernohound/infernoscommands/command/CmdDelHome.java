package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
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
        EntityPlayer player = getPlayer(ics, ics.getCommandSenderName());
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        if (args.length == 0) {
            args = new String[]{"home"};
        }
        if (args.length <= 1) {
            p.getHomes().set(args[0].toLowerCase(), null);
            player.addChatMessage(new ChatComponentText("Deleted home " + args[0]));
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
        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());
        return getListOfStringsMatchingLastWord(args,p.getHomes().keyArray());
    }
}
