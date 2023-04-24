package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

public class CmdSpawn extends CommandBase {
    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/spawn";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) getPlayer(ics, ics.getCommandSenderName());

        PlayerData p = WorldData.getPlayerList().get(player.getDisplayName());

        ChunkCoordinates spawn = player.worldObj.getSpawnPoint();
        BlockDimPos pos = new BlockDimPos(spawn.posX, spawn.posY, spawn.posZ, 0);


        p.setLastPos(p.getCurrentPos());
        InfernoTeleporter.teleport(player, pos);
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
