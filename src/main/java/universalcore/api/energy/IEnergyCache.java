package universalcore.api.energy;

/**
 *
 * @author UniversalRed
 *
 * If you don't know what i mean by Draw or Withdraw, it basically means Receive or extract
 * and WithDrawn and drawn mean maxExtract and MaxReceive (So that You aren't confused)
 *
 */

public interface IEnergyCache {

    /**
     *
     * @param maxDrawn The max amount of energy that you are expecting
     * @return
     */

    int drawEnergy(int maxDrawn);

    /**
     *
     * @param maxWithDrawn The max amount of energy that you expect the Tile Entity to Extract
     * @return
     */

    int withdrawEnergy(int maxWithDrawn);

    /**
     *
     * @return the amount of energy currently stored.
     */

    int getEnergyStored();

    /**
     *
     * @return the maximum amount of energy that can be stored.
     */

    int getMaxEnergyStored();
}
