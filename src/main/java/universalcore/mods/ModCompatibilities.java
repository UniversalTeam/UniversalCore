package universalcore.mods;

import cpw.mods.fml.common.Loader;

public class ModCompatibilities {

  public static boolean UE = false;
  public static boolean RF = false;
  public static boolean EU = false;
  public static boolean MJ = false;

    public static void idModLoaded(){

        if(Loader.isModLoaded("UniversalElectricity")){
           UE = true;
            if(UE == true){
                System.out.println("UniversalElectricity has been installed, starting Compatibility Sequence...");
                   PluginUE.loadPlugin();
                System.out.println("Compatibility Sequence initialized");

            }
        }else{
            UE = false;
            if(UE == false){
                System.out.println("UniversalElectricity is not installed, Skipping Compatibility Sequence...");
                System.out.println("Compatibility Sequence Skipped");
            }
        }
        if(Loader.isModLoaded("CofHCore") || Loader.isModLoaded("EnderIO")){
            RF = true;
            if(RF == true){
                System.out.println("CofHCore/EnderIO has been installed, starting Compatibility Sequence...");
                PluginThermalExpansion.loadPlugin();
                PluginEnderIO.loadPlugin();
                System.out.println("Compatibility Sequence initialized");
            }

        }else{
            RF = false;
            if(RF == false){
                System.out.println("CofHCore/EnderIO is not installed, Skipping Compatibility Sequence...");
                System.out.println("Compatibility Sequence Skipped");
            }

        }

        if(Loader.isModLoaded("IndustrialCraft2")){
            EU = true;
            if(EU == true){
                System.out.println("IndustrialCraft2 has been installed, starting Compatibility Sequence...");
                PluginIC2.loadPlugin();
                System.out.println("Compatibility Sequence initialized");
            }

        }else{
            EU = false;
            if(EU == false){
                System.out.println("IndustrialCraft2 is not installed, Skipping Compatibility Sequence...");
                System.out.println("Compatibility Sequence Skipped");
            }

        }

        if(Loader.isModLoaded("BuildCraft|Core")){
            MJ = true;
            if(MJ == true){
                System.out.println("BuildCraft|Core has been installed, starting Compatibility Sequence...");
                PluginBuildcraft.loadPlugin();
                System.out.println("Compatibility Sequence initialized");
            }

        }else{
            MJ = false;
            if(MJ == false){
                System.out.println("BuildCraft|Core is not installed, Skipping Compatibility Sequence...");
                System.out.println("Compatibility Sequence Skipped");
            }

        }
    }
}
