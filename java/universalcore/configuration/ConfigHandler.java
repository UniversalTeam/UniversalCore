package universalcore.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ConfigHandler{

    public File configFile;
    public String MODID;

    public ConfigHandler(File configFile, String modID, String description){
        this.configFile = configFile;
        this.modID = modID;
        this.mainTag = new ConfigTagParent(modID, description);
    }

    public ConfigHandler(File configFile, String modID)
    {
        this(configFile, modID, null);
    }

    public ConfigHandler(File configFile, Mod modInstance)
    {
        this(configFile, modInstance.getModId());
    }

    public ConfigHandler(File configFile, Mod modInstance, String description)
    {
        this(configFile, modInstance.getModId(), description);
    }

    public void load()
    {
        if (!configFile.exists())
            return;

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.configFile)));
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(String.format("Unable to find %s's config file at: %s", this.modID, this.configFile.getAbsolutePath()), e);
        }
    }

    public void save()
    {
        try
        {
            if (!configFile.exists())
            {
                Files.createParentDirs(configFile);
                configFile.createNewFile();
            }

            PrintWriter writer = new PrintWriter(this.configFile);

            this.mainTag.printTags(writer);

            writer.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(String.format("Unable to find %s's config file at: %s", this.modID, this.configFile.getAbsolutePath()), e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Failed to write/create %s's config file at: %s", this.modID, this.configFile.getAbsolutePath()), e);
        }
    }

}