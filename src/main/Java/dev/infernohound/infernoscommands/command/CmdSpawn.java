package dev.infernohound.infernoscommands.command;

import dev.infernohound.infernoscommands.config.InfernosConfig;
import dev.infernohound.infernoscommands.data.BlockDimPos;
import dev.infernohound.infernoscommands.data.PlayerData;
import dev.infernohound.infernoscommands.data.WorldData;
import dev.infernohound.infernoscommands.util.InfernoTeleporter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
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
        EntityPlayer entityPlayer = getPlayer(ics, ics.getCommandSenderName());

        PlayerData playerData = PlayerData.get(entityPlayer);

        ChunkCoordinates spawn = entityPlayer.worldObj.getSpawnPoint();
        BlockDimPos pos = new BlockDimPos(spawn.posX, spawn.posY, spawn.posZ, 0);


        playerData.setLastPosToCurrentPos();
        InfernoTeleporter.teleport(entityPlayer, pos);
        NBTTagCompound tag = new NBTTagCompound();
        playerData.saveNBTData(tag);
        WorldData.setProxyPlayerData(entityPlayer.getUniqueID(), tag);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender ics) {
        return InfernosConfig.spawn;
    }
}
