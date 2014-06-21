package mygdx.CivClick.core;

import mygdx.CivClick.util.Console;
import mygdx.CivClick.util.Shorthand;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public class Occupation implements Serializable, Invokable {

    private final String name, building;
    private long amount = 0, minamount = 0, maxamount = 1;

    /**
     * Creates a new job object.
     *
     * @param name sets the name of the job.
     */
    public Occupation(String name) {
        this.name = name;
        building = null;
    }

    /**
     * Creates a new job object.
     *
     * @param name sets the name of the job.
     * @param building sets the building the job works out of.
     */
    public Occupation(String name, String building) {
        this.name = name;
        this.building = building;
    }

    /**
     *
     * @return t he name of the job.
     */
    public String getName() {
        return name;
    }

    public String displayAmount() {
        return Shorthand.parse(amount) + " ";
    }

    public String displayMaxAmount() {
        return "Max: " + Shorthand.parse(maxamount);
    }

    /**
     * Adds jobs.
     *
     * @param value how many jobs to add.
     */
    public void addAmount(long value) {
        amount += Math.max(0, value);
    }

    /**
     * Sets the amount of jobs.
     *
     * @param value what to set the amount of jobs to.
     */
    public void setAmount(long value) {
        amount = Math.max(minamount, value);
    }

    /**
     * Removes jobs.
     *
     * @param value how many jobs to remove.
     */
    public void subtractAmount(long value) {
        amount = Math.max(minamount, (amount - value));
    }

    /**
     *
     * @return the amount of jobs.
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Sets the max amount of jobs.
     *
     * @param value what to set the max amount of jobs to.
     */
    public void setMaxAmount(long value) {
        maxamount = Math.max(minamount, value);
    }

    /**
     *
     * @return the max amount of jobs.
     */
    public long getMaxAmount() {
        return maxamount;
    }

    /**
     *
     * @return the building job uses.
     */
    public String getJobBuiling() {
        return building;
    }

    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        switch (tmp[2]) {
            case "getname":
                Console.println(getName());
                break;
            case "addmount":
                addAmount(Long.parseLong(command.replaceAll("\\D+", "")));
                break;
            case "setamount":
                setAmount(Long.parseLong(command.replaceAll("\\D+", "")));
                break;
            case "subtractamount":
                subtractAmount(Long.parseLong(command.replaceAll("\\D+", "")));
                break;
            case "getamount":
                Console.println(getAmount());
                break;
            case "setmaxamount":
                setMaxAmount(Long.parseLong(command.replaceAll("\\D+", "")));
                break;
            case "getmaxamount":
                Console.println(getMaxAmount());
                break;
            case "getjobbuilding":
                Console.println(getJobBuiling());
                break;
            case "help":
                Console.println("getname: Displays the name.");
                Console.println("addmount: Adds to the amount.");
                Console.println("setamount: Sets the amount.");
                Console.println("subtractamount: Subtracts from the amount.");
                Console.println("getamount: Displays the amount.");
                Console.println("setmaxamount: Sets the max amount.");
                Console.println("getmaxamount: Displays the max amount.");
                Console.println("getjobbuiling: Displays where it's worked at.");
                Console.println("help: Displays these messages.");
                break;
            default:
                Console.println("Invalid command.");
                Console.println(" @" + this.getName());
        }
    }

}
