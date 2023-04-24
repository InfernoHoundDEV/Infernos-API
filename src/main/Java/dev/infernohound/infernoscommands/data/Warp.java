package dev.infernohound.infernoscommands.data;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Warp {

    private final HashMap<String, BlockDimPos> warps;

    public Warp() {
        this.warps = new HashMap<>();
    }


    public void readFromNBT(NBTTagCompound tag, String str) {
        warps.clear();

        NBTTagCompound tag1 = (NBTTagCompound) tag.getTag(str);

        if(tag1 != null && !tag1.hasNoTags())
        {
            for(String str1 : getMapKeys(tag1))
            {
                warps.put(str1, new BlockDimPos(tag1.getIntArray(str1)));
            }
        }
    }

    private String[] getMapKeys(NBTTagCompound tag)
    {
        if(tag == null || tag.hasNoTags()) return new String[0];

        Set<String> strSet = tag.func_150296_c();

        String[] strArray = new String[strSet.size()];

        int index = 0;
        for (String str : strSet) {
            strArray[index++] = str;
        }
        return strArray;
    }

    public void writeToNBT(NBTTagCompound tag, String s)
    {
        NBTTagCompound tag1 = new NBTTagCompound();
        for(Map.Entry<String, BlockDimPos> e : warps.entrySet())
            tag1.setIntArray(e.getKey(), e.getValue().toIntArray());
        tag.setTag(s, tag1);
    }

    public Set<String> keySet()
    { return warps.keySet(); }

    public String[] keyArray()
    {
        int size = warps.keySet().size()+1;
        String[] strArray = new String[size];

        int index = 0;
        for (String str : warps.keySet()) {
            strArray[index++] = str;
        }
        strArray[size-1] = "list";
        return strArray;
    }

    public BlockDimPos get(String s)
    { return warps.get(s); }

    public boolean set(String s, BlockDimPos pos)
    {
        if(pos == null) return warps.remove(s) != null;
        return warps.put(s, pos.copy()) == null;
    }
    public int size()
    { return warps.size(); }
}
