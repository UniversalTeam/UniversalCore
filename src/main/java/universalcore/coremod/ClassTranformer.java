package universalcore.coremod;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTranformer implements IClassTransformer {

    /**
     *
     * @param name The name of the class that will be modified
     * @param newName The new name of the class (afther modification i think)
     * @param bytes Byte array to be injected
     *
     */
    @Override
    public byte[] transform(String name, String newName, byte[] bytes) {
        //does nothing cause i don't know what needs to be done
        return bytes;
    }

}
