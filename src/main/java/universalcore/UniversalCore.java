package universalcore;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import universalcore.libs.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Blocks;
import universalcore.mods.ModCompatibilities;
import universalcore.proxies.CommonProxy;

@Mod(modid = ReferenceCore.MODID, name = ReferenceCore.MODNAME, version = ReferenceCore.VERSION, dependencies = "required-after:Forge@[10.12.1.1082,)")
public class UniversalCore{

    @SidedProxy(clientSide = "universalcore.proxies.ClientProxy", serverSide = "universalcore.proxies.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){

        MCMod.MetaMcMod();
        ModCompatibilities.idModLoaded();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){


    }
}