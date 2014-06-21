package mygdx.CivClick.core;

import mygdx.CivClick.util.Console;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public class Upgrade extends Buyable implements Serializable {

    // Name of the Upgrade, description of the upgrade, what it upgrades in the object.
    private final String name, description, upgrades;
    private final Object upgradedObject; // What object the upgrade affects.
    private final float uvalue; // Upgrade value to be used for other methods/functions.
    private boolean purchased; // Has the upgraded been unlocked?
    // Price setting order for the array is as follows:
    // Wood, Stone, Metal, Skins, Herbs, Leather, Piety, Land.

    /**
     * Creates a building upgrade.
     *
     * @param name the name of the upgrade
     * @param building the building the upgrade affects.
     * @param upgrades what the upgrade affects inside the building.
     * @param uvalue the upgrade value.
     * @param description the description of the upgrade.
     */
    public Upgrade(String name, Building building, String upgrades, float uvalue, String description) {
        cost = new Double[8];
        this.name = name;
        this.upgradedObject = building;
        this.upgrades = upgrades;
        this.description = description;
        this.uvalue = uvalue;
    }

    /**
     * Creates a resource upgrade.
     *
     * @param name the name of the upgrade
     * @param resource the resource the upgrade affects.
     * @param upgrades what the upgrade affects inside the resource.
     * @param uvalue the upgrade value.
     * @param description the description of the upgrade.
     */
    public Upgrade(String name, Resource resource, String upgrades, float uvalue, String description) {
        cost = new Double[8];
        this.name = name;
        this.upgradedObject = resource;
        this.upgrades = upgrades;
        this.description = description;
        this.uvalue = uvalue;
    }

    /**
     * Creates a job upgrade.
     *
     * @param name the name of the upgrade
     * @param job the job the upgrade affects.
     * @param upgrades what the upgrade affects inside the resource.
     * @param uvalue the upgrade value.
     * @param description the description of the upgrade.
     */
    public Upgrade(String name, Occupation job, String upgrades, float uvalue, String description) {
        cost = new Double[8];
        this.name = name;
        this.upgradedObject = job;
        this.upgrades = upgrades;
        this.description = description;
        this.uvalue = uvalue;
    }

    /**
     *
     * @return the name of the upgrade.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return what the upgrade affects inside the object.
     */
    public String Upgrades() {
        return upgrades;
    }

    /**
     *
     * @return the description of the upgrade.
     */
    public String getDescription() {
        return description;
    }

    public float getUValue() {
        return uvalue;
    }

    public boolean isOwned() {
        return purchased;
    }

    private void Lock() {
        purchased = false;
    }

    /*
     Cases for those types (which is String Upgrades)
     Buildings: Cost, BaseCost, Capacity, CostMultiplier
     Resources: maxAmount, GatherMultiplier
     */
    private void Unlock(Building b) {
        switch (upgrades) {
            case "Cost": //Will always modify by percentage.
                //Iterate through Cost[], modify values.
                for (int i = 0; i < 8; i++) {
                    // Math stuff to decrease the current Cost by a percentage.
                    // I.E. A UValue of 25 means that cost is reduced by 25%.
                    double tmp = b.getCost(i);
                    tmp *= uvalue;
                    b.setCost(i, tmp);
                }
                break;
            case "BaseCost": // Will always modify by addition/subtraction.
                //Iterate through BaseCost[], modify values.
                for (int i = 0; i < 8; i++) {
                    double tmp = b.getBaseCost(i);
                    tmp -= uvalue;
                    b.setBaseCost(i, tmp);
                }
                break;
            case "Capacity": // Will always modify by addition/subtraction.
                // Modify capacity.
                b.addCapacity((int) uvalue);
                break;
            case "Multiplier": // Will always modify by addition/subtraction.
                // Modify CostMultiplier.
                float tmp = b.getCostMulti();
                tmp += uvalue;
                b.setCostMulti(tmp);
                break;
            default:
                throw new UnsupportedOperationException("Not supported.");
        }

    }

    private void Unlock(Resource r) {
        switch (upgrades) {
            case "maxAmount": // Will always modify by addition/subtraction.
                // Modify maxAmount.
                Double tmpl = r.getMaxAmount();
                tmpl += (double) (int) uvalue;
                r.setMaxAmount(tmpl);
                break;
            case "Multiplier": // Will always modify by addition/subtraction.
                // Modify GatherMultiplier.
                float tmpf = r.getGatherMultiplier();
                tmpf += uvalue;
                r.setGatherMultiplier(tmpf);
                break;
            default:
                throw new UnsupportedOperationException("Not supported.");
        }
    }

    private void Unlock(Occupation j) {
// This only has maxAmount to modify right now.
        if (!"maxAmount".equals(upgrades)) {
            throw new UnsupportedOperationException("Not supported.");
        }
        // Modify maxAmount.
        double tmp = j.getMaxAmount();
        tmp += (int) uvalue;
        j.setMaxAmount((long) tmp);
    }

    @Override
    public void Buy(ResourceManager rm, int value) {
        Console.println("Buying upgrade.", Console.Type.s);
        if (!purchased && isBuyable(rm)) {
            rm.PayResources(cost);
            if (upgradedObject instanceof Building) {
                Unlock((Building) upgradedObject);
            } else if (upgradedObject instanceof Resource) {
                Unlock((Resource) upgradedObject);
            } else if (upgradedObject instanceof Occupation) {
                Unlock((Occupation) upgradedObject);
            }
        }
    }

    @Override
    public void Sell(ResourceManager rm, int value) {
        // Not used.
    }

    @Override
    protected void UpdateCost(ResourceManager rm, int value) {
        // Not used.
    }

    @Override
    public boolean isSellable(ResourceManager rm) {
        // Not used
        return false;
    }

}
