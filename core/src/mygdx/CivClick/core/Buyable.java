package mygdx.CivClick.core;

import mygdx.CivClick.util.Shorthand;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public abstract class Buyable implements Serializable {

    protected Double[] cost;
    private static final String[] costlabels = {"W", "S", "M", "Sk", "H", "Le", "P", "L"};

    /**
     * Tests the object that implements Buyable if it can be purchased.
     *
     * @param rm the resource manager.
     * @return the comparison of the objects cost[] and the relevant resources.
     */
    public boolean isBuyable(ResourceManager rm) {
        return cost[0] <= rm.wood.getAmount()
                && cost[1] <= rm.stone.getAmount()
                && cost[2] <= rm.metal.getAmount()
                && cost[3] <= rm.skins.getAmount()
                && cost[4] <= rm.herbs.getAmount()
                && cost[5] <= rm.leather.getAmount()
                && cost[6] <= rm.piety.getAmount()
                && cost[7] <= rm.land.getMaxAmount();

    }

    public abstract boolean isSellable(ResourceManager rm);

    public abstract void Buy(ResourceManager rm, int value);

    public abstract void Sell(ResourceManager rm, int value);

    /**
     * Set the cost of the object that implements Buyable.
     *
     * @param values the double[] to be used to set the cost.
     */
    public void setCost(Double[] values) {
        cost = values;
    }

    /**
     * Set the cost of the object that implements Buyable.
     *
     * @param index the index of the Cost[] to set.
     * @param value the value to be used to set the cost.
     */
    public void setCost(int index, Double value) {
        cost[index] = Math.max(value, 0);
    }

    /**
     * Set the cost of the object that implements Buyable.
     *
     * @param WoodCost
     * @param StoneCost
     * @param MetalCost
     * @param SkinsCost
     * @param HerbsCost
     * @param LeatherCost
     * @param PietyCost
     * @param LandCost
     */
    public void setCost(Double WoodCost, Double StoneCost, Double MetalCost, Double SkinsCost, Double HerbsCost, Double LeatherCost, Double PietyCost, Double LandCost) {
        cost[0] = WoodCost; // W
        cost[1] = StoneCost; // S
        cost[2] = MetalCost; // M
        cost[3] = SkinsCost; // Sk
        cost[4] = HerbsCost; // H
        cost[5] = LeatherCost; // Le
        cost[6] = PietyCost; // P
        cost[7] = LandCost; // L
    }

    /**
     * Retrieves the cost of the object that implements Buyable.
     *
     * @return the double[] of the cost.
     */
    public Double[] getCost() {
        return cost;
    }

    /**
     * Retrieves the cost of the object that implements Buyable.
     *
     * @param index the index of the Cost[].
     * @return the value at Cost[Index].
     */
    public Double getCost(int index) {
        return cost[index];
    }

    public String displayCost() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cost.length; i++) {
            if (cost[i] > 0) {
                sb.append(costlabels[i]).append(": ").append(Shorthand.parse(cost[i])).append(" ");
            }
        }
        return sb.toString();
    }

    protected abstract void UpdateCost(ResourceManager rm, int Value);

}
