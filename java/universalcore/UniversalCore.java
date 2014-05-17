package universalcore;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import universalcore.libs.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Blocks;

@Mod(modid = ReferenceCore.MODID, name = ReferenceCore.MODNAME, version = ReferenceCore.VERSION, dependencies = "required-after:Forge@[10.12.1.1082,)")
public class UniversalCore
{

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){

        MCMod.MetaMcMod();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){


    }
}