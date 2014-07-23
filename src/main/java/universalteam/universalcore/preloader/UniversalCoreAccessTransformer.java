package universalteam.universalcore.preloader;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@Deprecated
public class UniversalCoreAccessTransformer extends AccessTransformer
{

	public static UniversalCoreAccessTransformer instance;
	private static List<String> mapFileList = Lists.newLinkedList();

	public UniversalCoreAccessTransformer() throws IOException
	{
		super();

		instance = this;

		for (String file : mapFileList)
			readMapFile(file);
	}

	public static void addTransformerMap(String mapFile)
	{
		if (instance == null)
			mapFileList.add(mapFile);
		else
			instance.readMapFile(mapFile);
	}

	private void readMapFile(String mapFile)
	{
		try
		{
			Method parentMapFile = AccessTransformer.class.getDeclaredMethod("readMapFile", String.class);
			parentMapFile.setAccessible(true);
			parentMapFile.invoke(this, mapFile);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
