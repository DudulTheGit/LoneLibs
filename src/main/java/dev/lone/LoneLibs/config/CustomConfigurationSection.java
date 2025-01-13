package dev.lone.LoneLibs.config;

import dev.lone.LoneLibs.EnumUtil;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Wrapper for the {@link ConfigurationSection} which introduces some useful methods.
 */
public class CustomConfigurationSection
{
    public final ConfigurationSection s;

    public CustomConfigurationSection(ConfigurationSection section)
    {
        this.s = section;
    }

    public CustomConfigurationSection(ConfigFile config, String path)
    {
        this.s = config.getSection(path);
    }

    public @org.jetbrains.annotations.NotNull Set<String> getKeys(boolean deep)
    {
        return s.getKeys(deep);
    }

    public @org.jetbrains.annotations.NotNull Map<String, Object> getValues(boolean deep)
    {
        return s.getValues(deep);
    }

    public boolean contains(@org.jetbrains.annotations.NotNull String path)
    {
        return s.contains(path);
    }

    public boolean contains(@org.jetbrains.annotations.NotNull String path, boolean ignoreDefault)
    {
        return s.contains(path, ignoreDefault);
    }

    public boolean isSet(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isSet(path);
    }

    public @Nullable String getCurrentPath()
    {
        return s.getCurrentPath();
    }

    public @org.jetbrains.annotations.NotNull String getName()
    {
        return s.getName();
    }

    public @Nullable Configuration getRoot()
    {
        return s.getRoot();
    }

    public @Nullable ConfigurationSection getParent()
    {
        return s.getParent();
    }

    public @Nullable Object get(@org.jetbrains.annotations.NotNull String path)
    {
        return s.get(path);
    }

    public @Nullable Object get(@org.jetbrains.annotations.NotNull String path, @Nullable Object def)
    {
        return s.get(path, def);
    }

    public void set(@org.jetbrains.annotations.NotNull String path, @Nullable Object value)
    {
        s.set(path, value);
    }

    public @org.jetbrains.annotations.NotNull ConfigurationSection createSection(@org.jetbrains.annotations.NotNull String path)
    {
        return s.createSection(path);
    }

    public @org.jetbrains.annotations.NotNull ConfigurationSection createSection(@org.jetbrains.annotations.NotNull String path, @org.jetbrains.annotations.NotNull Map<?, ?> map)
    {
        return s.createSection(path, map);
    }

    public @Nullable String getString(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getString(path);
    }

    public @Nullable String getString(@org.jetbrains.annotations.NotNull String path, @Nullable String def)
    {
        return s.getString(path, def);
    }

    public boolean isString(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isString(path);
    }

    public int getInt(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getInt(path);
    }

    public int getInt(@org.jetbrains.annotations.NotNull String path, int def)
    {
        return s.getInt(path, def);
    }

    public boolean isInt(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isInt(path);
    }

    public boolean getBoolean(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getBoolean(path);
    }

    public boolean getBoolean(@org.jetbrains.annotations.NotNull String path, boolean def)
    {
        return s.getBoolean(path, def);
    }

    public boolean isBoolean(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isBoolean(path);
    }

    public double getDouble(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getDouble(path);
    }

    public double getDouble(@org.jetbrains.annotations.NotNull String path, double def)
    {
        return s.getDouble(path, def);
    }

    public boolean isDouble(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isDouble(path);
    }

    public long getLong(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getLong(path);
    }

    public long getLong(@org.jetbrains.annotations.NotNull String path, long def)
    {
        return s.getLong(path, def);
    }

    public boolean isLong(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isLong(path);
    }

    public @Nullable List<?> getList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getList(path);
    }

    public @Nullable List<?> getList(@org.jetbrains.annotations.NotNull String path, @Nullable List<?> def)
    {
        return s.getList(path, def);
    }

    public boolean isList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isList(path);
    }

    public @org.jetbrains.annotations.NotNull List<String> getStringList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getStringList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Integer> getIntegerList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getIntegerList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Boolean> getBooleanList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getBooleanList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Double> getDoubleList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getDoubleList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Float> getFloatList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getFloatList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Long> getLongList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getLongList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Byte> getByteList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getByteList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Character> getCharacterList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getCharacterList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Short> getShortList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getShortList(path);
    }

    public @org.jetbrains.annotations.NotNull List<Map<?, ?>> getMapList(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getMapList(path);
    }

    public <T> @Nullable T getObject(@org.jetbrains.annotations.NotNull String path, @org.jetbrains.annotations.NotNull Class<T> clazz)
    {
        return s.getObject(path, clazz);
    }

    public <T> @Nullable T getObject(@org.jetbrains.annotations.NotNull String path, @org.jetbrains.annotations.NotNull Class<T> clazz, @Nullable T def)
    {
        return s.getObject(path, clazz, def);
    }

    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@org.jetbrains.annotations.NotNull String path, @org.jetbrains.annotations.NotNull Class<T> clazz)
    {
        return s.getSerializable(path, clazz);
    }

    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@org.jetbrains.annotations.NotNull String path, @org.jetbrains.annotations.NotNull Class<T> clazz, @Nullable T def)
    {
        return s.getSerializable(path, clazz, def);
    }

    public @Nullable Vector getVector(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getVector(path);
    }

    public @Nullable Vector getVector(@org.jetbrains.annotations.NotNull String path, @Nullable Vector def)
    {
        return s.getVector(path, def);
    }

    public boolean isVector(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isVector(path);
    }

    public @Nullable OfflinePlayer getOfflinePlayer(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getOfflinePlayer(path);
    }

    public @Nullable OfflinePlayer getOfflinePlayer(@org.jetbrains.annotations.NotNull String path, @Nullable OfflinePlayer def)
    {
        return s.getOfflinePlayer(path, def);
    }

    public boolean isOfflinePlayer(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isOfflinePlayer(path);
    }

    public @Nullable ItemStack getItemStack(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getItemStack(path);
    }

    public @Nullable ItemStack getItemStack(@org.jetbrains.annotations.NotNull String path, @Nullable ItemStack def)
    {
        return s.getItemStack(path, def);
    }

    public boolean isItemStack(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isItemStack(path);
    }

    public @Nullable Color getColor(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getColor(path);
    }

    public @Nullable Color getColor(@org.jetbrains.annotations.NotNull String path, @Nullable Color def)
    {
        return s.getColor(path, def);
    }

    public boolean isColor(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isColor(path);
    }

    public @Nullable Location getLocation(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getLocation(path);
    }

    public @Nullable Location getLocation(@org.jetbrains.annotations.NotNull String path, @Nullable Location def)
    {
        return s.getLocation(path, def);
    }

    public boolean isLocation(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isLocation(path);
    }

    public @Nullable ConfigurationSection getConfigurationSection(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getConfigurationSection(path);
    }

    public boolean isConfigurationSection(@org.jetbrains.annotations.NotNull String path)
    {
        return s.isConfigurationSection(path);
    }

    public @Nullable ConfigurationSection getDefaultSection()
    {
        return s.getDefaultSection();
    }

    public void addDefault(@org.jetbrains.annotations.NotNull String path, @Nullable Object value)
    {
        s.addDefault(path, value);
    }

    public @org.jetbrains.annotations.NotNull List<String> getComments(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getComments(path);
    }

    public @org.jetbrains.annotations.NotNull List<String> getInlineComments(@org.jetbrains.annotations.NotNull String path)
    {
        return s.getInlineComments(path);
    }

    public void setComments(@org.jetbrains.annotations.NotNull String path, @Nullable List<String> comments)
    {
        s.setComments(path, comments);
    }

    public void setInlineComments(@org.jetbrains.annotations.NotNull String path, @Nullable List<String> comments)
    {
        s.setInlineComments(path, comments);
    }

    //<editor-fold desc="New methods">
    public <T extends Enum<T>> T getEnum(String path, Class<T> type)
    {
        return ConfigFile.getEnum(s, path, type, null);
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> type, T defaultValue)
    {
        return ConfigFile.getEnum(s, path, type, defaultValue);
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> type, T defaultValue, Consumer<String> errorCallback)
    {
        String str = s.getString(path);
        T t = str != null ? EnumUtil.safeGet(str.toUpperCase(), type, defaultValue) : defaultValue;
        if (t == null)
            errorCallback.accept(str);
        return t;
    }

    public List<String> getKeysList(String path)
    {
        return getKeysList(path, false);
    }

    /**
     * Gets a list containing all keys in this section.
     * <p>
     * If deep is set to true, then this will contain all the keys within any
     * child {@link ConfigurationSection}s (and their children, etc.). These
     * will be in a valid path notation for you to use.
     * <p>
     * If deep is set to false, then this will contain only the keys of any
     * direct children, and not their own children.
     *
     * @param deep Whether to get a deep list, as opposed to a shallow
     *             list.
     * @return List of keys contained within this ConfigurationSection.
     */
    @NotNull
    public List<String> getKeysList(String path, boolean deep)
    {
        ConfigurationSection tmp = s.getConfigurationSection(path);
        if (tmp == null)
            return new ArrayList<>();
        return new ArrayList<>(tmp.getKeys(deep));
    }

    /**
     * Gets a list containing all keys in this section.
     * <p>
     * If deep is set to true, then this will contain all the keys within any
     * child {@link ConfigurationSection}s (and their children, etc).
     * These will be in a valid path notation for you to use.
     * <p>
     * If deep is set to false, then this will contain only the keys of any
     * direct children, and not their own children.
     *
     * @param deep Whether to get a deep list, as opposed to a shallow
     *             list.
     * @return List of keys contained within this ConfigurationSection.
     */
    @NotNull
    public List<String> getKeysList(boolean deep)
    {
        return new ArrayList<>(s.getKeys(deep));
    }

    public boolean hasKey(String path)
    {
        return s.contains(path);
    }

    public Integer getInt(@org.jetbrains.annotations.NotNull String path, Integer def)
    {
        if (hasKey(path))
            return s.getInt(path);
        return def;
    }

    public float getFloat(String path, float defaultValue)
    {
        if (hasKey(path))
            return (float) s.getDouble(path);
        return defaultValue;
    }

    public float getFloat(String path)
    {
        return getFloat(path, 0f);
    }

    public Float getFloat(String path, Float defaultValue)
    {
        if (hasKey(path))
            return (float) s.getDouble(path);
        return defaultValue;
    }

    public Boolean getBoolean(String path, Boolean defaultValue)
    {
        if (hasKey(path))
            return s.getBoolean(path);
        return defaultValue;
    }
    //</editor-fold>
}
