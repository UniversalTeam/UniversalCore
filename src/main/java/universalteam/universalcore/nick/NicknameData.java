package universalteam.universalcore.nick;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.entity.player.EntityPlayer;
import universalteam.universalcore.configuration.Config;
import universalteam.universalcore.utils.ServerUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;

public class NicknameData
{
	private static BiMap<String, String> nicknames;
	private static Gson gson;
	private static Type type;
	private static File nicknameFile;

	public static void initialize()
	{
		nicknames = HashBiMap.create();
		gson = new GsonBuilder().setPrettyPrinting().create();
		type = new TypeToken<HashBiMap<String, String>>()
		{
		}.getType();
		nicknameFile = new File(Config.configLocation, "nicknames.json");

		checkFileStructure();
		loadNickNames();
	}

	public static void deInitialize()
	{
		saveNicknames();
	}

	public static void setNickname(String username, String nickname)
	{
		if (Strings.isNullOrEmpty(nickname) && nicknames.containsKey(username))
			nicknames.remove(username);
		else
			nicknames.put(username, nickname);

		updateNickname(username);
	}

	public static void updateNickname(String username)
	{
		EntityPlayer player = ServerUtil.getPlayerForUserName(username);

		if (player != null)
			player.refreshDisplayName();
	}

	public static String getNickname(String username)
	{
		return nicknames.containsKey(username) ? nicknames.get(username) : null;
	}

	public static String getUsername(String nickname)
	{
		return nicknames.inverse().containsKey(nickname) ? nicknames.get(nickname) : null;
	}

	private static void checkFileStructure()
	{
		try
		{
			Files.createParentDirs(nicknameFile);
			nicknameFile.createNewFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void loadNickNames()
	{
		if (!nicknameFile.exists())
			return;

		try
		{
			nicknames = gson.fromJson(new FileReader(nicknameFile), type);

			for (String username : nicknames.keySet())
				updateNickname(username);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void saveNicknames()
	{
		try
		{
			FileWriter writer = new FileWriter(nicknameFile);
			writer.write(gson.toJson(nicknames, type));
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
