package mygdx.CivClick.bldg;
import mygdx.CivClick.core.Building;

import mygdx.CivClick.core.PopulationManager;
import mygdx.CivClick.core.ResourceManager;

/**
 *
 * @author Tyler
 */
public class Hut extends Building {

    public Hut(String name, int capacity) {
        super(name, capacity);

    }

    @Override
    public void Buy(ResourceManager rm, PopulationManager pm, int value) {
        rm.PayResources(cost);
        addAmount(value);
        UpdateCost(rm, value);
        pm.addMaxAmount((long) getCapacity());
    }

    @Override
    public void Sell(ResourceManager rm, PopulationManager pm, int value) {
        subtractAmount(value);
        UpdateCost(rm, value);
        rm.RefundResources(cost);
        pm.subtractMaxAmount((long) getCapacity());
    }
}
