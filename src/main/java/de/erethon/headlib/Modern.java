package de.erethon.headlib;

import de.erethon.headlib.HeadLib.InternalsProvider;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * This implementation works for every modern mc version (1.20.5+)
 */
class Modern implements InternalsProvider
{
    @Override
    public ItemStack newPlayerHead(int amount)
    {
        return new ItemStack(Material.PLAYER_HEAD, amount);
    }

    @Override
    public String getTextureValue(ItemStack item)
    {
        return NBT.modifyComponents(item, nbt -> {
            ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
            ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").get(0);
            return propertiesNbt.getString("value");
        });
    }

    @Override
    public ItemStack setSkullOwner(ItemStack item, Object itemB)
    {
        String textureValue = NBT.modifyComponents((ItemStack) itemB, nbtB -> {
            ReadWriteNBT profileB = nbtB.getOrCreateCompound("minecraft:profile");
            ReadWriteNBT propertiesB = profileB.getCompoundList("properties").get(0);
            return propertiesB.getString("value");
        });

        NBT.modifyComponents(item, nbt -> {
            ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
            ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
            propertiesNbt.setString("name", "textures");
            propertiesNbt.setString("value", textureValue);
        });
        return item;
    }

    @Override
    public Object createOwnerCompound(String id, String textureValue)
    {
        ItemStack dummy  = new ItemStack(Material.PLAYER_HEAD);
        NBT.modifyComponents(dummy, nbt -> {
            ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
            ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
            propertiesNbt.setString("name", "textures");
            propertiesNbt.setString("value", textureValue);
        });
        return dummy;
    }
}
