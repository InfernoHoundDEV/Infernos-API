package dev.infernohound.infernoscommands.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

import java.util.HashMap;
import java.util.UUID;

public class WorldData extends WorldSavedData {

    public final static String IDENTIFIER = "InfernoWorldData";
    private static HashMap<UUID, NBTTagCompound> proxyPlayerData = new HashMap<>();
    private static Warp warps = new Warp();

    public WorldData(String identifier) {
        super(identifier);
    }

    public static NBTTagCompound getProxyPlayerData(UUID playerUniqueId) {
        return proxyPlayerData.get(playerUniqueId);
    }
    public static void setProxyPlayerData(UUID playerUniqueId, NBTTagCompound tag) {
        proxyPlayerData.put(playerUniqueId, tag);
    }

    public Warp getWarps() { return warps; }

    public static WorldData getSave(World world) {
        MapStorage storage = world.perWorldStorage;
        WorldData result = (WorldData) storage.loadData(WorldData.class, IDENTIFIER);
        if (result == null) {
            result = new WorldData(IDENTIFIER);
            storage.setData(IDENTIFIER, result);
        }
        return result;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        warps.readFromNBT(tag,"Warps");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        warps.writeToNBT(tag, "Warps");
    }
}
