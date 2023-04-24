package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.*;

public class CmdBack extends CommandBase {
    @Override
    public String getCommandName() {
        return "back";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/back";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) getPlayer(ics, ics.getCommandSenderName());

        if(WorldData.getPlayerList().containsKey(player.getDisplayName())) {
            PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());

            if (p.getLastPos() == null) {
                ics.addChatMessage(new ChatComponentText("You can only back once!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                return;
            }

            InfernoTeleporter.teleport(player, p.getLastPos());
            p.setLastPos(null);
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
