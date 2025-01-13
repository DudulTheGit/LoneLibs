package dev.lone.LoneLibs;

import org.jetbrains.annotations.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LoneUtils
{
    /**
     * @param str check if it is numeric
     */
    public static boolean isNumeric(String str)
    {
        if (str == null)
            return false;

        int length = str.length();
        for (int i = 0; i < length; ++i)
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                if(str.charAt(i) != '.')
                    return false;
            }
        }

        return true;
    }

    public static boolean isAir(@Nullable ItemStack item)
    {
        return item == null || item.getType() == Material.AIR;
    }

    public static int count(ItemStack[] contents)
    {
        int count = 0;
        for (ItemStack content : contents)
        {
            if (!isAir(content))
                count++;
        }
        return count;
    }
}
