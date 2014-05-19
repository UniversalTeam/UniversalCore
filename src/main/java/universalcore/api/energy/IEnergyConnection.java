package universalcore.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 *
 * @author UniversalRed
 *
 */

public interface IEnergyConnection {

    /**
     *
     * @param direction return true if you want the Tile entity to connect to a side
     *                  OR return false if you DO NOT want the Tile entity to connect to a side
     * @return return either boolean if you want either results
     */

      boolean canDrawConnection(ForgeDirection direction);

}
