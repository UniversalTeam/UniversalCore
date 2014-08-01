package universalteam.universalcore.version;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.NBTTagCompound;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static universalteam.universalcore.UniversalCore.logger;

public class UCVersion
{
	public String modName = "";
	public String newVersion = "";
	public String updateURL = "";
	public boolean isDirectLink = false;
	public String changelog = "";

	public String versionFileLink;
	public String currentVersion;

	public boolean isRead = false;

	public UCVersion(String currentVersion, String versionFileLink)
	{
		this.currentVersion = currentVersion;
		this.versionFileLink = versionFileLink;
	}

	public UCVersion fromJson()
	{
		InputStream stream;

		try
		{
			URL url = new URL(versionFileLink);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(1000);
			con.setReadTimeout(1000);
			stream = con.getInputStream();
		}
		catch (Exception e)
		{
			logger.warning("Failed to read %s's version file (%s), the version check will not work!", modName, versionFileLink);
			return null;
		}

		return fromJson(stream);
	}

	public UCVersion fromJson(InputStream stream)
	{
		InputStreamReader reader = new InputStreamReader(stream);
		JsonObject node = new JsonParser().parse(reader).getAsJsonObject();

		if (node.has("modName"))
			modName = node.get("modName").getAsString();
		if (node.has("newVersion"))
			newVersion = node.get("newVersion").getAsString();
		if (node.has("updateURL"))
			updateURL = node.get("updateURL").getAsString();
		if (node.has("isDirectLink"))
			isDirectLink = node.get("isDirectLink").getAsBoolean();
		if (node.has("changelog"))
			changelog = node.get("changelog").getAsString();

		isRead = true;

		return this;
	}

	public NBTTagCompound toNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();

		compound.setString("modDisplayName", modName);
		compound.setString("oldVersion", currentVersion);
		compound.setString("newVersion", newVersion);
		compound.setString("updateURL", updateURL);
		compound.setBoolean("isDirectLink", isDirectLink);
		compound.setString("changeLog", changelog);

		return compound;
	}
}
