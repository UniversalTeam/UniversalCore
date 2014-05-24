package universalcore.api.compat;

public interface IPluginListener
{
	public String getModID();

	public void handle();

	public void handleClient();
}
