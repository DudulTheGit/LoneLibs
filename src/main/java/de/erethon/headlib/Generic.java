package de.erethon.headlib;

import de.erethon.headlib.HeadLib.InternalsProvider;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * This implementation works for every other mc version
 */
class Generic implements InternalsProvider
{
    @Override
    public ItemStack newPlayerHead(int amount)
    {
        return new ItemStack(Material.PLAYER_HEAD, amount);
    }

    @Override
    public String getTextureValue(ItemStack item)
    {
        NBTItem nbt = new NBTItem(item);

        NBTCompound skullOwner = nbt.getCompound("SkullOwner");
        if(skullOwner == null)
            return null;

        NBTCompound properties = skullOwner.getCompound("Properties");
        if (properties == null)
            return null;

        NBTCompoundList textures = properties.getCompoundList("textures");
        if (textures == null || textures.size() == 0)
            return null;

        for (ReadWriteNBT base : textures)
        {
            if (base.hasTag("Value"))
            {
                return base.getString("Value");
            }
        }
        return null;
    }

    @Override
    public ItemStack setSkullOwner(ItemStack item, Object compound)
    {
        NBTItem nbt = new NBTItem(item);
        nbt.getOrCreateCompound("SkullOwner").mergeCompound((NBTCompound) compound);
        return nbt.getItem();
    }

    @Override
    public Object createOwnerCompound(String id, String textureValue)
    {
        NBTItem dummy = new NBTItem(new ItemStack(Material.PLAYER_HEAD), true);
        NBTCompound skullOwner = dummy.getOrCreateCompound("SkullOwner");
        skullOwner.setUUID("Id", UUID.fromString(id));

        NBTCompound properties = skullOwner.addCompound("Properties");
        NBTCompoundList textures = properties.getCompoundList("textures");
        NBTListCompound value = textures.addCompound();
        value.setString("Value", textureValue);
        textures.addCompound(value);

        return skullOwner;
    }
}
