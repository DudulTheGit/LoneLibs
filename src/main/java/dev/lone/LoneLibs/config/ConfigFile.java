package dev.lone.LoneLibs.config;

import dev.lone.LoneLibs.EnumUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import dev.lone.LoneLibs.chat.Comp;
import dev.lone.LoneLibs.chat.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

public class ConfigFile
{
    private final Plugin plugin;
    private final Msg msg;
    private FileConfiguration config;
    private final String filePath;
    private final File file;
    private boolean hasDefaultFile = true;
    private boolean autoAddMissingProperties = false;
    private boolean warnIfNoDefaultFile;
    private String partialFilePath;
    private boolean valid;
    private boolean changed;

    /**
     * @param plugin                   Plugin
     * @param filePath                 Partial path of the file (root = plugins/XXX/)
     * @param hasDefaultFile           If there is a default file in .jar resources
     * @param autoAddMissingProperties Add properties that are missing from user config (useful on plugin updates if you create new properties in the config)
     * @param yamlSeparator
     */
    public ConfigFile(Plugin plugin, Msg msg, boolean isPathRelativeToPluginFolder, String filePath, boolean hasDefaultFile, boolean autoAddMissingProperties, @Nullable Character yamlSeparator)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, autoAddMissingProperties, true, yamlSeparator);
    }

    /**
     * @param plugin                   Plugin
     * @param filePath                 Partial path of the file (root = plugins/XXX/)
     * @param hasDefaultFile           If there is a default file in .jar resources
     * @param autoAddMissingProperties Add properties that are missing from user config (useful on plugin updates if you create new properties in the config)
     */
    public ConfigFile(Plugin plugin, Msg msg, boolean isPathRelativeToPluginFolder, String filePath, boolean hasDefaultFile, boolean autoAddMissingProperties)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, autoAddMissingProperties, true);
    }

    public ConfigFile(Plugin plugin,
                      Msg msg,
                      boolean isPathRelativeToPluginFolder,
                      String filePath,
                      boolean hasDefaultFile,
                      boolean autoAddMissingProperties,
                      boolean warnIfNoDefaultFile)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, autoAddMissingProperties, warnIfNoDefaultFile, false, null);
    }

    public ConfigFile(Plugin plugin,
                      Msg msg,
                      boolean isPathRelativeToPluginFolder,
                      String filePath,
                      boolean hasDefaultFile,
                      boolean autoAddMissingProperties,
                      boolean warnIfNoDefaultFile,
                      @Nullable Character yamlSeparator)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, autoAddMissingProperties, warnIfNoDefaultFile, false, yamlSeparator);
    }

    public ConfigFile(Plugin plugin,
                      Msg msg,
                      boolean isPathRelativeToPluginFolder,
                      String filePath,
                      boolean hasDefaultFile,
                      boolean autoAddMissingProperties,
                      boolean warnIfNoDefaultFile,
                      boolean completeMalformedDataStacktrace)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, autoAddMissingProperties, warnIfNoDefaultFile, completeMalformedDataStacktrace, null);
    }

    public ConfigFile(Plugin plugin,
                      Msg msg,
                      boolean isPathRelativeToPluginFolder,
                      String filePath,
                      boolean hasDefaultFile,
                      boolean autoAddMissingProperties,
                      boolean warnIfNoDefaultFile,
                      boolean completeMalformedDataStacktrace,
                      @Nullable Character yamlSeparator)
    {
        this.plugin = plugin;
        this.msg = msg;
        this.warnIfNoDefaultFile = warnIfNoDefaultFile;

        if (isPathRelativeToPluginFolder)
        {
            this.partialFilePath = filePath;
            this.filePath = plugin.getDataFolder() + File.separator + filePath;
        }
        else
        {
            this.partialFilePath = filePath.replace(plugin.getDataFolder().getAbsolutePath(), "");
            this.filePath = filePath;
        }

        partialFilePath = partialFilePath.replace("\\", "/");

        if (!new File(this.filePath).exists())
            new File(this.filePath).getParentFile().mkdirs();

        this.hasDefaultFile = hasDefaultFile;
        this.autoAddMissingProperties = autoAddMissingProperties;
        this.file = new File(this.filePath);

        try
        {
            config = loadConfiguration(file, yamlSeparator);// Seems to be the cause of lag on plugin load/reload.
            initFile();
            valid = true;
        }
        catch (InvalidConfigurationException | ParserException e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Cannot load file: " + partialFilePath);
            if(completeMalformedDataStacktrace)
                e.printStackTrace();
            else
                msg.warn("Error: " + e.getMessage());
            valid = false;
        }
        catch (Throwable e)
        {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load file: " + partialFilePath);
            e.printStackTrace();
            valid = false;
        }
    }

    public ConfigFile(Plugin plugin, Msg msg, boolean isPathRelativeToPluginFolder, String filePath, boolean hasDefaultFile)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, false, null);
    }

    public ConfigFile(Plugin plugin, Msg msg, boolean isPathRelativeToPluginFolder, String filePath, boolean hasDefaultFile, @Nullable Character yamlSeparator)
    {
        this(plugin, msg, isPathRelativeToPluginFolder, filePath, hasDefaultFile, false, yamlSeparator);
    }

    @NotNull
    public static YamlConfiguration loadConfiguration(@NotNull File file, @Nullable Character yamlSeparator) throws IOException, InvalidConfigurationException
    {
        Validate.notNull(file, "File cannot be null");

        YamlConfiguration config = new YamlConfiguration();
        if(yamlSeparator != null)
            config.options().pathSeparator(yamlSeparator);

        try
        {
            config.load(file);
        }
        catch (FileNotFoundException ignored)
        {
            // Happens the first time since the file obviously doesn't exists.
        }
        return config;
    }

    private ConfigFile(Plugin plugin, Msg msg, String filePath)
    {
        this.plugin = plugin;
        this.msg = msg;
        this.filePath = filePath;
        this.file = new File(this.filePath);
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Inits file taking it from original file in .jar if not exists and adding missing attributes if autoAddMissingProperties
     */
    void initFile()
    {
        if (hasDefaultFile)
        {
            String resourceFilePath = this.filePath
                    .replace("plugins/" + plugin.getName() + "/", "")
                    .replace("plugins\\" + plugin.getName() + "\\", "")
                    .replace("plugins\\" + plugin.getName() + "/", "");
            if (!file.exists())
            {
                InputStream internalFile = tryGetInternalFile(resourceFilePath);
                if(internalFile == null)
                    return;

                // Load the default file from JAR resources
                try
                {
                    FileUtils.copyInputStreamToFile(internalFile, file);
                }
                catch (IOException | NullPointerException e)
                {
                    msg.warn("Failed to load default file: " + resourceFilePath);
                    return;
                }
                catch (Throwable e)
                {
                    msg.error("Failed to load default file: " + resourceFilePath, e);
                    return;
                }
            }
            else
            {
                if (autoAddMissingProperties)
                {
                    InputStream internalFile = tryGetInternalFile(resourceFilePath);
                    if(internalFile == null)
                        return;

                    FileConfiguration c = YamlConfiguration.loadConfiguration((new InputStreamReader(internalFile, StandardCharsets.UTF_8)));
                    for (String k : c.getKeys(true))
                    {
                        if (!config.contains(k))
                            config.set(k, c.get(k));
                    }

                    try
                    {
                        config.save(file);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException("Failed to save file: " + file.getAbsolutePath(), e);
                    }
                }
            }

            try
            {
                config.load(file);
            }
            catch (IOException | InvalidConfigurationException e)
            {
                throw new RuntimeException("Failed to load file: " + file.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Reloads from file
     */
    public void reload()
    {
        config = YamlConfiguration.loadConfiguration(file);
    }

    private InputStream tryGetInternalFile(String resourceFilePath)
    {
        InputStream internalFile = plugin.getResource(resourceFilePath);
        if(internalFile == null && warnIfNoDefaultFile)
            msg.warn("Default file not found for: " + resourceFilePath);
        return internalFile;
    }

    public File getFile()
    {
        return file;
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getPartialFilePath()
    {
        return partialFilePath;
    }

    public boolean isValid()
    {
        return valid;
    }

    public boolean hasChanged()
    {
        return changed;
    }

    public void set(String path, Object value)
    {
        this.config.set(path, value);
        changed = true;
    }

    public void saveOnlyIfChanged()
    {
        if(!changed)
            return;

        try
        {
            this.config.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            this.config.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void save(File newLocation)
    {
        try
        {
            this.config.save(newLocation);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasKey(String path)
    {
        return config.contains(path);
    }

    public void remove(String path)
    {
        config.set(path, null);
        changed = true;
    }

    public List<String> getKey()
    {
        return getSectionKeys("", false);
    }

    public List<String> getSectionKeys(String path)
    {
        return getSectionKeys(path, false);
    }

    public List<String> getSectionKeys(String path, boolean deep)
    {
        if (!hasKey(path))
            return new ArrayList<>();
        //noinspection DataFlowIssue
        return new ArrayList<>(config.getConfigurationSection(path).getKeys(deep));
    }

    public static List<String> getSectionKeys(ConfigurationSection section)
    {
        return getSectionKeys(section, "");
    }

    public static List<String> getSectionKeys(ConfigurationSection section, String path)
    {
        if (!section.contains(path))
            return new ArrayList<>();
        return new ArrayList<>(section.getKeys(false));
    }

    public Map<String, Object> getSectionKeysAndValues(String path)
    {
        if (!hasKey(path))
            return null;
        //noinspection DataFlowIssue
        return config.getConfigurationSection(path).getValues(false);
    }

    @Nullable
    public Map<String, Object> getSectionKeysAndValuesDeep(String path)
    {
        if (!hasKey(path))
            return null;

        Map<String, Object> map = new HashMap<>();

        ConfigurationSection root = config.getConfigurationSection(path);
        if(root == null)
            return null;

        String separator = String.valueOf(config.options().pathSeparator());

        Set<String> keys = root.getKeys(true);
        for (String key : keys)
        {
            Object val = root.get(key);
            if(val instanceof MemorySection)
                continue;

            String[] splitKey = key.split(separator);
            map.put(splitKey[splitKey.length - 1], val);
        }

        return map;
    }

    /**
     * WARNING: might thrown cast exception if your file is malformed and has non-integer values.
     */
    @Nullable
    public List<Integer> getSectionIntegerValuesWithoutKeysDeep(String path)
    {
        if (!hasKey(path))
            return null;

        ConfigurationSection root = config.getConfigurationSection(path);
        if(root == null)
            return null;

        List<Integer> values = new ArrayList<>();
        Set<String> keys = root.getKeys(true);
        for (String key : keys)
        {
            Object val = root.get(key);
            if(val instanceof MemorySection)
                continue;
            values.add((Integer) val);
        }

        return values;
    }

    public Object get(String path, Object defaultValue)
    {
        if (hasKey(path))
            return config.get(path);
        return defaultValue;
    }

    @Nullable
    public String getString(String path)
    {
        return config.getString(path);
    }

    public String getString(String path, String defaultValue)
    {
        if (!hasKey(path))
            return defaultValue;
        return config.getString(path);
    }

    public int getInt(String path)
    {
        return config.getInt(path);
    }

    /**
     * Riturna interno se trovato, se no ritorna defaultValue
     */
    public int getInt(String path, int defaultValue)
    {
        if (hasKey(path))
            return config.getInt(path);
        return defaultValue;
    }

    /**
     * Ritorna double se trovato, se no ritorna defaultValue
     */
    public double getDouble(String path, double defaultValue)
    {
        if (hasKey(path))
            return config.getDouble(path);
        return defaultValue;
    }

    public float getFloat(String path, float defaultValue)
    {
        if (hasKey(path))
            return (float) config.getDouble(path);
        return defaultValue;
    }

    public float getFloat(String path)
    {
        return getFloat(path, 0f);
    }

    @Nullable
    public Float getFloatOptional(String path, @Nullable Float defaultValue)
    {
        if (hasKey(path))
            return (float) config.getDouble(path);
        return defaultValue;
    }

    @Nullable
    public Float getFloatOptional(String path)
    {
        if (hasKey(path))
            return (float) config.getDouble(path);
        return null;
    }

    public boolean getBoolean(String path)
    {
        return config.getBoolean(path);
    }

    public Boolean getBoolean(String path, Boolean defaultValue)
    {
        if (hasKey(path))
            return config.getBoolean(path);
        return defaultValue;
    }

    public Material getMaterial(String path, Material defaultValue)
    {
        if (hasKey(path))
        {
            //noinspection DataFlowIssue
            return EnumUtil.safeGet(config.getString(path).toUpperCase(), Material.class, defaultValue);
        }
        else
            return defaultValue;
    }

    @Nullable
    public Material getMaterial(String path)
    {
        return getMaterial(path, null);
    }

    public List<Material> getMaterials(String path)
    {
        ArrayList<Material> coloredList = new ArrayList<>();

        ArrayList<String> list = (ArrayList<String>) config.getStringList(path);
        for (String matStr : list)
        {
            Material material = EnumUtil.safeGet(matStr.toUpperCase(), Material.class);
            if (material != null)
                coloredList.add(material);
            else
                msg.error("Invalid material '" + matStr + "'. File: '" + filePath + "'");
        }

        return coloredList;
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> type, T defaultValue)
    {
        String str = config.getString(path);
        return str != null ? EnumUtil.safeGet(str.toUpperCase(), type, defaultValue) : defaultValue;
    }

    public <T extends Enum<T>> T getEnum(String path, Class<T> type, T defaultValue, Consumer<String> errorCallback)
    {
        String str = config.getString(path);
        T t = str != null ? EnumUtil.safeGet(str.toUpperCase(), type, defaultValue) : defaultValue;
        if(t == null)
            errorCallback.accept(str);
        return t;
    }

    public static <T extends Enum<T>> T getEnum(ConfigurationSection section, String path, Class<T> type)
    {
        return getEnum(section, path, type, null);
    }

    public static <T extends Enum<T>> T getEnum(ConfigurationSection section, String path, Class<T> type, T defaultValue)
    {
        String str = section.getString(path);
        return str != null ? EnumUtil.safeGet(str.toUpperCase(), type, defaultValue) : defaultValue;
    }

    public String getColored(String path)
    {
        return getColored(path, null);
    }

    public String getColored(String path, String defaultValue)
    {
        if (hasKey(path))
            return convertColor(config.getString(path));
        return defaultValue;
    }

    public String getStripped(String name)
    {
        return ChatColor.stripColor(getColored(name));
    }

    /**
     * Returns a list of strings with colors already converted to bukkit format
     */
    public List<String> getStrings(String path, boolean colored)
    {
        if (colored)
        {
            ArrayList<String> coloredList = new ArrayList<>();
            try
            {
                ArrayList<String> list = (ArrayList<String>) config.getStringList(path);
                for (String entry : list)
                    coloredList.add(convertColor(entry));
            }
            catch (NullPointerException exc)
            {
                exc.printStackTrace();
                notifyMissingProperty(path);
            }

            return coloredList;
        }
        return config.getStringList(path);
    }

    /**
     * Returns a list of strings (without converting colors)
     */
    public List<String> getStrings(String path)
    {
        return getStrings(path, false);
    }

    @Nullable
    public ConfigurationSection getSection(@NotNull String path)
    {
        return config.getConfigurationSection(path);
    }

    @Nullable
    public CustomConfigurationSection getCustomSection(@NotNull String path)
    {
        return new CustomConfigurationSection(getSection(path));
    }

    @Nullable
    public EntityType getEntityType(String key, EntityType type, Consumer<String> failAction)
    {
        if (hasKey(key))
        {
            String val = getString(key).toUpperCase();
            try
            {
                return EntityType.valueOf(val);
            }
            catch (Exception exc)
            {
                failAction.accept(val);
                return null;
            }
        }
        return type;
    }

    public char separator()
    {
        return getConfig().options().pathSeparator();
    }

    private String convertColor(String string)
    {
        return Comp.convertColor(string).replace("\\n", "\n");
    }

    protected void notifyMissingProperty(String path)
    {
        msg.error("MISSING FILE PROPERTY! '" + path + "' in file '" + filePath + "'");
    }
}