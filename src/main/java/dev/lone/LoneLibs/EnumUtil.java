package dev.lone.LoneLibs;

import org.jetbrains.annotations.Nullable;
import org.bukkit.SoundCategory;

public class EnumUtil
{
    public static <T extends Enum<T>> boolean isValid(String str, Class<T> enumType)
    {
        try
        {
            Enum.valueOf(enumType, str);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    @Nullable
    public static <T extends Enum<T>> T safeGet(String str, Class<T> enumType)
    {
        return safeGet(str, enumType, null);
    }

    public static <T extends Enum<T>> T safeGet(String str, Class<T> enumType, T fallback)
    {
        try
        {
            return Enum.valueOf(enumType, str);
        }
        catch(Exception e)
        {
            return fallback;
        }
    }

    public static SoundCategory sound(String str, SoundCategory def)
    {
        if(str == null)
            return def;
        return EnumUtil.safeGet(str.toUpperCase(), SoundCategory.class, SoundCategory.MASTER);
    }

    public static SoundCategory sound(String str)
    {
        return sound(str, SoundCategory.MASTER);
    }
}
