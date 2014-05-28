package universalteam.universalcore.api.compat;

import java.util.Collection;

public interface IPluginListener
{
	public String getModID();

	public void handle(Collection<Class<?>> plugins);
}
