package universalteam.universalcore.configuration;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import universalteam.universalcore.libs.ReferenceCore;

import java.util.ArrayList;
import java.util.List;

public class UCConfigGUI extends GuiConfig
{
	public UCConfigGUI(GuiScreen parentScreen)
	{
		super(parentScreen, getElements(), ReferenceCore.MODID, false, false, "UniversalCore Config");
	}

	public static List<IConfigElement> getElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new UCDummyCategoryElement("General"));
		list.add(new UCDummyCategoryElement("Tweaks"));
		return list;
	}

	public static class UCDummyCategoryElement extends DummyConfigElement.DummyCategoryElement
	{
		public String categoryName;

		public UCDummyCategoryElement(String categoryName)
		{
			super(categoryName, "", new ConfigElement(Config.config.getCategory(categoryName)).getChildElements());
			this.categoryName = categoryName;
		}

		@Override
		public String getComment()
		{
			return Config.config.getCategory(categoryName).getComment();
		}
	}
}
