package universalteam.universalcore.api.energy.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 *
 * @author UniversalRed
 *
 */
public interface IItemElectricManager {

    /**
     *
     * @param container Item's energy
     * @param entity entity holding Item
     */

    void drawEnergyFromArmour(ItemStack container, EntityLivingBase entity);

    /**
     *
     * @param container Item's Energy
     * @param maxDrawn The max amount of energy that you are expecting
     * @return
     */

    int drawEnergy(ItemStack container, int maxDrawn);

    /**
     *
     * @param container Item's Energy
     * @param maxWithDrawn The max amount of energy that you expect the container to Extract
     * @return
     */

    int withdrawEnergy(ItemStack container, int maxWithDrawn);

    /**
     *
     * @param container Item's Energy
     * @param storage How much is in the container already
     * @return
     */

    boolean isUsable(ItemStack container, int storage);

    /**
     *
     * @param container Item's Energy
     * @param storage How much is in the container already
     * @param entity entity holding Item
     * @return
     */

    boolean Usable(ItemStack container, int storage, EntityLivingBase entity);

}
