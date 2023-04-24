package dev.infernohound.infernoscommands.data;

import dev.infernohound.infernoscommands.InfernosCommands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class PlayerData implements IExtendedEntityProperties {

    private static final Logger logger = LogManager.getLogger(InfernosCommands.MODID);
    public final static String EXT_PROP_NAME = "InfernoPlayerData";

    private EntityPlayer player;
    private BlockDimPos lastPos;
    private Warp homes;
    private Collection<UUID> tpaRequests;


   public PlayerData(EntityPlayer player) {
       this.player = player;
       lastPos = null;
       homes = new Warp();
       tpaRequests = new HashSet<>();
   }

    public static final void register(EntityPlayer player)
    {
        PlayerData p = get(player);
        player.registerExtendedProperties(PlayerData.EXT_PROP_NAME, new PlayerData(player));
    }

    public static final PlayerData get(EntityPlayer player)
    {
        return (PlayerData) player.getExtendedProperties(EXT_PROP_NAME);
    }

    public BlockDimPos getLastPos() {
        return lastPos;
    }

    public void setLastPos(BlockDimPos lastPos) {
        this.lastPos = lastPos;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public Warp getHomes() {
        return homes;
    }

    public boolean containsTpaRequest(UUID playerUniqueID) {
        return tpaRequests.contains(playerUniqueID);
    }

    public void addTpaRequest(UUID playerUniqueID) {
        tpaRequests.add(playerUniqueID);
    }

    public void removeTpaRequest(UUID playerUniqueID) {
       tpaRequests.remove(playerUniqueID);
    }

    public BlockDimPos getCurrentPos() {
       return new BlockDimPos(player.posX, player.posY, player.posZ, player.worldObj.provider.dimensionId);
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        NBTTagCompound properties = new NBTTagCompound();
        if(lastPos != null) properties.setIntArray("LastPos", lastPos.toIntArray());
        homes.writeToNBT(properties, "Homes");
        tag.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        NBTTagCompound properties = (NBTTagCompound) tag.getTag(EXT_PROP_NAME);
        if(properties.hasKey("LastPos")) {
            if (properties.func_150299_b("LastPos") == Constants.NBT.TAG_INT_ARRAY) {
                lastPos = new BlockDimPos(properties.getIntArray("LastPos"));
            }
        }

        homes.readFromNBT(properties, "Homes");
    }

    @Override
    public void init(Entity entity, World world) {}

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
