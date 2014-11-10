package universalteam.universalcore.configuration;

import cpw.mods.fml.relauncher.FMLInjectionData;
import universalteam.universalcore.libs.ReferenceCore;

import java.io.File;

public class Config
{
	public static final File mcDir = (File) FMLInjectionData.data()[6];
	public static final File configLocation = new File(mcDir, "config" + File.separator + ReferenceCore.MODID);
}
