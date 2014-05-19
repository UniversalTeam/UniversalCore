package universalcore.api.energy.items;

import net.minecraft.item.ItemStack;

/**
 *
 * @author UniversalRed
 *
 * If you don't know what i mean by Draw or Withdraw, it basically means Receive or extract
 * and WithDrawn and drawn mean maxExtract and MaxReceive (So that You aren't confused)
 *
 */

public interface IEnergyItem {

    /**
     *
     * @param container boolean to provideEnergy to the given item that you want to charge energy to
     * @return either boolean to provideEnergy
     */

    boolean provideEnergy(ItemStack container);

    /**
     *
     * @param container the container item which the energy is going to be stored
     * @param maxDrawn The max amount of energy that you are expecting
     * @return
     */

    int drawEnergy(ItemStack container, int maxDrawn);

    /**
     *
     * @param container the container item which the energy is going to be stored in
     * @param maxWithDrawn The max amount of energy that you expect the Tile Entity to Extract
     * @return
     */

    int withdrawEnergy(ItemStack container, int maxWithDrawn);

    /**
     *
     * @return
     */

    int getEnergyStored();

    /**
     *
     * @return
     */

    int getMaxEnergyStored();


}
