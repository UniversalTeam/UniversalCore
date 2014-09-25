package universalteam.universalcore.configuration;

import net.minecraft.client.Minecraft;
import universalteam.universalcore.libs.ReferenceCore;

import java.io.File;

public class Config
{
	public static final File configLocation = new File(Minecraft.getMinecraft().mcDataDir, "config" + File.separator + ReferenceCore.MODID);
}
