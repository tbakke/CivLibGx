package mygdx.CivClick.core;

import mygdx.CivClick.util.Console;
import mygdx.CivClick.util.Shorthand;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public class Building extends Buyable implements Serializable, Invokable {

    private final String name;
    private double amount = 0, capacity;
    private float costmultiplier = 1.0f;
    // Price setting order for the array is as follows:
    // Wood, Stone, Metal, Skins, Herbs, Leather, Land.
    private final Double[] basecost;

    /**
     * Creates a new building object. Cost multiplier defaults to 1.0 if not
     * specified.
     *
     * @param name sets the name of the building.
     * @param capacity sets the capacity of the building.
     * @param costmultiplier sets the cost multiplier for the building.
     */
    public Building(String name, int capacity, float costmultiplier) {
        basecost = new Double[8];
        cost = new Double[8];
        this.costmultiplier = costmultiplier;
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Creates a new building object. Cost multiplier defaults to 1.0 if not
     * specified.
     *
     * @param name sets the name of the building.
     * @param capacity sets the capacity of the building.
     */
    public Building(String name, int capacity) {
        basecost = new Double[8];
        cost = new Double[8];
        this.name = name;
        this.capacity = capacity;
    }

    /**
     *
     * @return the name of the building.
     */
    public String getName() {
        return name;
    }

    public String displayAmount() {
        return Shorthand.parse(amount) + " ";
    }

    /**
     * Initializes the basecost[] after object creation.
     *
     * @param woodcist
     * @param stonecost
     * @param metalcost
     * @param skinscost
     * @param herbscost
     * @param leathercost
     * @param pietycost
     * @param landcost
     */
    public void initBaseCost(Double woodcist, Double stonecost, Double metalcost, Double skinscost, Double herbscost, Double leathercost, Double pietycost, Double landcost) {
        basecost[0] = woodcist;
        basecost[1] = stonecost;
        basecost[2] = metalcost;
        basecost[3] = skinscost;
        basecost[4] = herbscost;
        basecost[5] = leathercost;
        basecost[6] = pietycost;
        basecost[7] = landcost;
        cost = basecost.clone();

    }

    @Override
    public boolean isSellable(ResourceManager rm) {
        return rm.land.getAmount() - cost[7] >= 0 && amount - 1 >= 0;
    }

    /**
     * Buys a stockpile building.
     *
     * @param rm the resource manager.
     * @param value how many to buy.
     */
    @Override
    public void Buy(ResourceManager rm, int value) {
        Console.println("Buying " + this.name + "at cost of " + displayCost(), Console.Type.s);
        rm.PayResources(cost);
        addAmount(value);
        UpdateCost(rm, value);
        rm.UpdateResourceLimit(this);
    }

    /**
     * Buys a job building.
     *
     * @param rm the resource manager.
     * @param pm the population manager.
     * @param value how many to sell.
     */
    public void Buy(ResourceManager rm, PopulationManager pm, int value) {
        Console.println("Buying " + this.name + "at cost of " + displayCost(), Console.Type.s);
        rm.PayResources(cost);
        addAmount(value);
        UpdateCost(rm, value);
        pm.UpdateJobLimit(this);
    }

    /**
     * Sells a stockpile building.
     *
     * @param rm the resource manager.
     * @param value how many to sell.
     */
    @Override
    public void Sell(ResourceManager rm, int value) {
        Console.println("Selling " + this.name + "at cost of " + displayCost(), Console.Type.s);
        subtractAmount(value);
        UpdateCost(rm, value);
        rm.RefundResources(cost);
        rm.UpdateResourceLimit(this);
    }

    /**
     * Sells a job building.
     *
     * @param rm the resource manager.
     * @param pm the population manager.
     * @param value how many to sell.
     */
    public void Sell(ResourceManager rm, PopulationManager pm, int value) {
        Console.println("Selling " + this.name + "at cost of " + displayCost(), Console.Type.s);
        subtractAmount(value);
        UpdateCost(rm, value);
        rm.RefundResources(cost);
        pm.UpdateJobLimit(this);
    }

    /**
     * Updates the cost of the building.
     *
     * @param rm the resource manager.
     * @param value not used (currently).
     */
    @Override
    public void UpdateCost(ResourceManager rm, int value) {
        Double[] NewCost = cost.clone();
        for (int i = 0; i < NewCost.length - 1; i++) {
            if (NewCost[i] > 0) {
                Double temp = ((costmultiplier * (getAmount() * getAmount())) + getBaseCost(i));
                NewCost[i] = temp;
            } else {

            }
            setCost(NewCost);
        }
    }

    /**
     * Sets the base cost of the building.
     *
     * @param index the index of BaseCost[] to set.
     * @param value the value to be used to set the cost.
     */
    public void setBaseCost(int index, Double value) {
        basecost[index] = Math.max(value, 0);
    }

    /**
     * Retrieves the base cost of the building.
     *
     * @param index the index of BaseCost[].
     * @return the value at BaseCost[Index].
     */
    public Double getBaseCost(int index) {
        return basecost[index];
    }

    /**
     * Adds buildings.
     *
     * @param value how many buildings to add.
     */
    public void addAmount(int value) {
        amount += Math.max(0, value);
    }

    /**
     * Sets the amount of buildings.
     *
     * @param value how many buildings to set.
     */
    public void setAmount(int value) {
        amount = Math.max(0, value);
    }

    /**
     * Removes buildings.
     *
     * @param value how many buildings to remove.
     */
    public void subtractAmount(int value) {
        amount -= Math.max(0, value);
    }

    /**
     *
     * @return the amount of buildings.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the building capacity.
     *
     * @param value what to set the capacity to.
     */
    public void setCapacity(int value) {
        capacity = Math.max(0, value);
    }

    /**
     * Adds to the building capacity.
     *
     * @param value how much capacity to add.
     */
    public void addCapacity(int value) {
        capacity += Math.max(0, value);
    }

    /**
     *
     * @return the capacity of the building.
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     *
     * @return the cost multiplier of the building.
     */
    public float getCostMulti() {
        return costmultiplier;
    }

    /**
     * Sets the cost multiplier of the building.
     *
     * @param value what to set the multiplier to.
     */
    public void setCostMulti(float value) {
        costmultiplier = value;
    }

    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        switch (tmp[2]) {
            case "getname":
                Console.println(getName());
                break;
            case "setbasecost":
                setBaseCost(Integer.parseInt(tmp[3].replaceAll("\\D+", "")), Double.parseDouble(tmp[4].replaceAll("\\D+", "")));
                break;
            case "getbasecost":
                Console.println(getBaseCost(Integer.parseInt(tmp[3].replaceAll("\\D+", ""))));
                break;
            case "addamount":
                addAmount(Integer.parseInt(tmp[3].replaceAll("\\D+", "")));
                break;
            case "setamount":
                setAmount(Integer.parseInt(tmp[3].replaceAll("\\D+", "")));
                break;
            case "subtractamount":
                subtractAmount(Integer.parseInt(tmp[3].replaceAll("\\D+", "")));
                break;
            case "getamount":
                Console.println(getAmount());
                break;
            case "setcapacity":
                setCapacity(Integer.parseInt(tmp[3].replaceAll("\\D+", "")));
                break;
            case "addcapacity":
                addCapacity(Integer.parseInt(tmp[3].replaceAll("\\D+", "")));
                break;
            case "getcostmultiplier":
                Console.println(getCostMulti());
                break;
            case "setcostmultiplier":
                setCostMulti(Float.parseFloat(tmp[3].replaceAll("\\D+", "") + "." + tmp[4].replaceAll("\\D+", "")));
                break;
            case "help":
                Console.println("getname: Displays the name.");
                Console.println("setbasecost: Sets the basecost at given index.");
                Console.println("getbasecost: Gets the basecost at given index.");
                Console.println("addamount: Adds to the amount.");
                Console.println("setamount: Sets the amount.");
                Console.println("subtractamount: Subtracts from the amount.");
                Console.println("getamount: Displays the amount.");
                Console.println("setcapacity: Sets the capacity.");
                Console.println("addcapacity: Adds to the capacity.");
                Console.println("getcostmultiplier: Displays the cost multi.");
                Console.println("setcostmultiplier: Sets the cost multi.");
                Console.println("help: Displays these messages.");
                break;
            default:
                Console.println("Invalid command.");
                Console.println(" @" + this.getName());
        }
    }

}
