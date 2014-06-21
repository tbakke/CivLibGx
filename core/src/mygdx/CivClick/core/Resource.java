package mygdx.CivClick.core;

import mygdx.CivClick.util.Console;
import mygdx.CivClick.util.Shorthand;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public class Resource implements Serializable, Invokable {

    private final String name, container; // Container is the building that holds the resource.
    private double amount, minamount, maxamount;
    private float gathermultiplier = 1.0f; // Collection multiplier for resources that use it.

    /**
     * Creates a new resource object.
     *
     * @param name sets the name of the resource.
     */
    public Resource(String name) {
        this.name = name;
        container = null;
    }

    /**
     * Creates a new resource object.
     *
     * @param name sets the name of the resource.
     * @param gathermultiplier sets the gather multiplier.
     */
    public Resource(String name, float gathermultiplier) {
        this.name = name;
        container = null;
        this.gathermultiplier = gathermultiplier;
    }

    /**
     * Creates a new resource object.
     *
     * @param name sets the name of the resource.
     * @param container sets the container the resource will be stored in.
     */
    public Resource(String name, String container) {
        this.name = name;
        this.container = container;
        maxamount = 100;
    }

    /**
     * Creates a new resource object.
     *
     * @param name sets the name of the resource.
     * @param container sets the container the resource will be stored in.
     * @param gathermultiplier sets the gather multiplier.
     */
    public Resource(String name, String container, float gathermultiplier) {
        this.name = name;
        this.container = container;
        this.gathermultiplier = gathermultiplier;
        maxamount = 100;
    }

    /**
     *
     * @return the name of the resource.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the amount of resource.
     *
     * @param value how much to set.
     */
    public void setAmount(double value) {
        amount = Math.max(Math.min(value, maxamount), minamount);
    }

    /**
     *
     * @return the amount of resource.
     */
    public double getAmount() {
        return amount;
    }

    public String displayAmount() {
        return Shorthand.parse(amount) + " ";
    }

    public String displayMaxAmount() {
        return "Max: " + Shorthand.parse(maxamount);
    }

    /**
     * Adds to resource.
     *
     * @param value how much to add to the resource.
     */
    public void addAmount(double value) {
        if (!limitReached()) {
            amount += value;
        } else {
            amount = maxamount;
        }
    }

    /**
     * Removes from the resource.
     *
     * @param value how much to remove from the resource.
     */
    public void subtractAmount(double value) {
        amount = Math.max(minamount, (amount - value));
    }

    /**
     * Sets the limit of the resource.
     *
     * @param value what to set the limit to.
     */
    public void setMaxAmount(double value) {
        maxamount = Math.max(minamount, value);
    }

    /**
     *
     * @return the limit of the resource.
     */
    public double getMaxAmount() {
        return maxamount;
    }

    /**
     *
     * @return the container the resource is stored in.
     */
    public String getContainer() {
        return container;
    }

    /**
     *
     * @return the gather multiplier of the resource.
     */
    public float getGatherMultiplier() {
        return gathermultiplier;
    }

    /**
     * Sets the gather multiplier of the resource.
     *
     * @param value what to set the multiplier to.
     */
    public void setGatherMultiplier(float value) {
        gathermultiplier = value;
    }

    /**
     * Tests if the resource is maxed out.
     *
     * @return whether or not amount is equal to or greater than the maximum
     * amount.
     */
    public boolean limitReached() {
        return amount >= maxamount;
    }

    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        switch (tmp[2]) {
            case "getname":
                Console.println(getName());
                break;
            case "setamount":
                setAmount(Double.parseDouble(command.replaceAll("\\D+", "")));
                break;
            case "getamount":
                Console.println(getAmount());
                break;
            case "addamount":
                addAmount(Double.parseDouble(command.replaceAll("\\D+", "")));
                break;
            case "subtractamount":
                subtractAmount(Double.parseDouble(command.replaceAll("\\D+", "")));
                break;
            case "setmaxamount":
                setMaxAmount(Double.parseDouble(command.replaceAll("\\D+", "")));
                break;
            case "getmaxamount":
                Console.println(getMaxAmount());
                break;
            case "getcontainer":
                Console.println(getContainer());
                break;
            case "getgathermultiplier":
                Console.println(getGatherMultiplier());
                break;
            case "setgathermultiplier":
                setGatherMultiplier(Float.parseFloat(tmp[3].replaceAll("\\D+", "") + "." + tmp[4].replaceAll("\\D+", "")));
                break;
            case "limitreached":
                Console.println(limitReached());
                break;
            case "help":
                Console.println("getname: Displays the resource name.");
                Console.println("setamount: Sets the amount.");
                Console.println("getamount: Displays the amount.");
                Console.println("addamount: Adds to the amount.");
                Console.println("subtractamount: Subtracts from the amount.");
                Console.println("setmaxamount: Sets the max amount.");
                Console.println("getmaxamount: Displays the max amount.");
                Console.println("getcontainer: Displays the container.");
                Console.println("getgathermultiplier: Displays the gather multi.");
                Console.println("setgathermultiplier: Sets the gather multi.");
                Console.println("limitreached: Displays state of limitreached.");
                Console.println("help: Displays these messages.");
                break;
            default:
                Console.println("Invalid command.");
                Console.println(" @" + this.getName());
        }
    }
}
