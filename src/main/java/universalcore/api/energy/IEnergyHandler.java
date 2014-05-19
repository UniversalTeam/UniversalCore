package universalcore.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 *
 * @author UniversalRed
 *
 * If you don't know what i mean by Draw or Withdraw, it basically means Receive or extract
 * and WithDrawn and drawn mean maxExtract and MaxReceive (So that You aren't confused)
 *
 */
public interface IEnergyHandler {

    /**
     *
     * @param direction The Direction of which you are accepting Energy from
     * @param maxDrawn The max amount of energy that you are expecting from that adjacent tile
     * @return
     */

    int drawEnergy(ForgeDirection direction, int maxDrawn);

    /**
     *
     * @param direction The Direction of which you are accepting Energy from
     * @param maxWithDrawn The max amount of energy that you expect the Tile Entity to Extract
     * @return
     */

    int withdrawEnergy(ForgeDirection direction, int maxWithDrawn);

    /**
     *
     * @param direction Returns the amount of energy currently stored within the Tile Entity
     * @return
     */

    int getEnergyStored(ForgeDirection direction);

    /**
     *
     * @param direction Returns the maximum amount of energy the Tile entity can store
     * @return
     */

    int getMaxEnergyStored(ForgeDirection direction);

}
