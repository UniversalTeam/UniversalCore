package universalcore.preloader;

import codechicken.core.launch.DepLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class UniversalCoreLoadingPlugin implements IFMLLoadingPlugin {

	public UniversalCoreLoadingPlugin() {
		DepLoader.load();
	}

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
