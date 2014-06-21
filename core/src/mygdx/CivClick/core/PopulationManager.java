package mygdx.CivClick.core;

import mygdx.CivClick.job.*;
import mygdx.CivClick.util.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Whiplash
 */
public class PopulationManager implements Updateable, Serializable, Invokable {

    private long minpopamount = 0, popamount = 0, maxpopamount = 0, starvationpercentage = 1;
    private boolean starving;
    private double starvationmultiplier = 1.0f;
    public TownshipTitle title;
    private final Random rand;

    // Declare all new occupations here
    // and add them to the occupationlist in the constructor.
    private final List<Occupation> occupationlist = new ArrayList<>();
    Unemployed unemployed = new Unemployed("Unemployed");
    Dead unburieddead = new Dead("Unburied Dead");
    Dead dead = new Dead("Dead", "Graveyard");
    Farmer farmer = new Farmer("Farmer", "Farm");
    Forester forester = new Forester("Forester");
    Miner miner = new Miner("Miner");
    Tanner tanner = new Tanner("Tanner", "Tannery");
    Blacksmith blacksmith = new Blacksmith("Blacksmith", "Smithey");
    Apothecary apothecary = new Apothecary("Apothecary", "Apothecary");
    Soldier soldier = new Soldier("Soldier", "Barracks");
    Gardener gardner = new Gardener("Gardener", "Greenhouse");
    Hunter hunter = new Hunter("Hunter", "Hunting Lodge");
    Cleric cleric = new Cleric("Cleric", "Church");
    Sick sick = new Sick("Sick");

    public PopulationManager() {
        maxpopamount = 10; // Default population limit.
        occupationlist.add(unemployed); // Unemployed is the "default" worker group.
        occupationlist.add(unburieddead);
        occupationlist.add(dead);
        occupationlist.add(farmer);
        occupationlist.add(forester);
        occupationlist.add(miner);
        occupationlist.add(tanner);
        occupationlist.add(blacksmith);
        occupationlist.add(apothecary);
        occupationlist.add(soldier);
        rand = new Random();
        title = new TownshipTitle(TownshipTitle.Title.Hamlet);
    }

    /**
     *
     * @return the total amount of the population.
     */
    public double getPopulationAmount() {
        return popamount;
    }

    /**
     *
     * @return the population limit.
     */
    public double getMaxPopulationAmount() {
        return maxpopamount;
    }

    /**
     * Sets the population limit.
     *
     * @param value what to set the limit to.
     */
    public void setMaxAmount(long value) {
        maxpopamount = Math.max(minpopamount, value); // Value clamping.
    }

    /**
     * Adds to the population limit.
     *
     * @param value how much to add.
     */
    public void addMaxAmount(int value) {
        maxpopamount += value;
    }

    /**
     * Adds to the population limit.
     *
     * @param value how much to add.
     */
    public void addMaxAmount(long value) {
        maxpopamount += value;
    }

    /**
     * Subtracts from the population limit.
     *
     * @param value how much to subtract.
     */
    public void subtractMaxAmount(int value) {
        maxpopamount -= Math.max(value, minpopamount);
    }

    /**
     * Subtracts from the population limit.
     *
     * @param value how much to subtract.
     */
    public void subtractMaxAmount(long value) {
        maxpopamount -= Math.max(value, minpopamount);
    }

    private void KillPopulation(long value) {
        int i = 0;
        int k = 7;
        while (i < value && popamount > 0) {
            int j = rand.nextInt(Math.min(Math.max(8 - k, 0), 8));
            if (occupationlist.get(j).getAmount() > 0 && !occupationlist.get(j).equals(unburieddead) && !occupationlist.get(j).equals(dead)) {
                occupationlist.get(j).subtractAmount(1);
                unburieddead.addAmount(1);
                UpdatePopulation();
                i++;
                k = 7;
            }
            k--;
        }
    }

    private void BuryDead() {
        if (unburieddead.getAmount() > 0 && cleric.getAmount() > 0) {
            if (unburieddead.getAmount() - cleric.getAmount() >= 0 && dead.getAmount() + (unburieddead.getAmount() - cleric.getAmount()) < dead.getMaxAmount()) {
                unburieddead.subtractAmount(cleric.getAmount());
                dead.addAmount(cleric.getAmount());
            }
        }
    }

    private void CheckSickness(ResourceManager rm) {
        if (rm.sickness.limitReached() | sick.getAmount() > 0) {
            SpreadSickness();
            CureSickness(rm);
        }
    }

    private void CureSickness(ResourceManager rm) { // Apothecaries reduce sickness as long as there are herbs.
        if (rm.sickness.getAmount() - apothecary.getAmount() >= 0) { // Reduces sickness aura thingymajigger.
            rm.sickness.subtractAmount(apothecary.getAmount());
        } else if (sick.getAmount() - apothecary.getAmount() >= 0) { // Cures sick people.
            sick.subtractAmount(apothecary.getAmount());
        }
    }

    private void SpreadSickness() {
        if (unburieddead.getAmount() > 0 && sick.getAmount() < popamount) {
            sick.addAmount(1);
        } else if (sick.getAmount() >= popamount && popamount - (sick.getAmount() * 0.25) >= 0) {
            // Casting to longs to remove decimals.
            KillPopulation((long) (sick.getAmount() * 0.25));
            sick.subtractAmount((long) (sick.getAmount() * 0.25));
        }
    }

    /**
     * Adds a worker to the population, or moves them to a job.
     *
     * @param j the job
     * @param value the amount to add to the population (or job).
     * @param rm the resource manager.
     */
    public void AddWorker(Occupation j, int value, ResourceManager rm) {
        if (j.equals(unemployed)) {
            int tmp = 0;
            while (rm.food.getAmount() >= getNextWorkerCost()
                    && popamount < maxpopamount
                    && tmp < value) {
                j.addAmount(1);
                rm.food.subtractAmount(getNextWorkerCost());
                UpdatePopulation();
                tmp++;
            }

        } else if (j.equals(forester) | j.equals(miner)) { // Tests for either limitless jobs.
            int tmp = 0;
            while (unemployed.getAmount() > 0 && tmp < value) {
                unemployed.subtractAmount(1);
                j.addAmount(1);
                UpdatePopulation();
                tmp++;
            }
        } else { // Do regular job stuff.
            int tmp = 0;
            while (j.getAmount() < j.getMaxAmount()
                    && unemployed.getAmount() > 0
                    && tmp < value) {
                unemployed.subtractAmount(1);
                j.addAmount(1);
                UpdatePopulation();
                tmp++;

            }
        }
    }

    /**
     *
     * @return the cost of the next worker.
     */
    public double getNextWorkerCost() {
        return (double) (1.75f * (popamount * 1.75f) + 20);
    }

    /**
     * Removes a worker from a job.
     *
     * @param j the job
     * @param value amount of workers to remove
     * @param rm the resource manager (unused).
     */
    public void RemoveWorker(Occupation j, int value, ResourceManager rm) {
        if (!j.equals(unemployed) && !j.equals(unburieddead) && !j.equals(dead) && !j.equals(sick)) { // can't remove unemployed, or dead, or sick.
            int tmp = 0;
            while (j.getAmount() > 0 && tmp < value) {
                j.subtractAmount(1);
                unemployed.addAmount(1);
                UpdatePopulation();
                tmp++;
            }
        }
    }

// Hunger upkeep and starvation checking.
    private void PopulationHungerUpkeep(ResourceManager rm) {
        if (rm.food.getAmount() > 0 && popamount > 0) {
            for (int i = 0; i < popamount; i++) {
                rm.food.subtractAmount(1);
                starving = false;
            }
        } else if (rm.food.getAmount() <= 0 && popamount > 0) {
            KillPopulation((int) starvationmultiplier);
            starving = true;
        }
    }

    private void UpdatePopulation() { // Keeps track of overrall population
        int newpopamount = 0;
        for (Occupation j : occupationlist) {
            if (!j.equals(unburieddead) && !j.equals(dead) && !j.equals(sick)) { // We don't count the dead or the sick.
                newpopamount += j.getAmount();
            }
        }
        popamount = newpopamount;
        if (title.getTitle() != TownshipTitle.Title.Hamlet && popamount < 500) { // Hamlet
            title.setTitle(TownshipTitle.Title.Hamlet);
        } else if (title.getTitle() != TownshipTitle.Title.Village && popamount > 500) { // Village
            if (title.getTitle() != TownshipTitle.Title.Town && popamount > 2500) { // Town
                if (title.getTitle() != TownshipTitle.Title.City && popamount > 5000) { // City 
                    if (title.getTitle() != TownshipTitle.Title.Kingdom && popamount > 50000) { // Kingdom
                        if (title.getTitle() != TownshipTitle.Title.Metropolis && popamount > 100000) // Metro
                        {
                            title.setTitle(TownshipTitle.Title.Metropolis);
                        }
                        title.setTitle(TownshipTitle.Title.Kingdom);
                    }
                    title.setTitle(TownshipTitle.Title.City);
                }
                title.setTitle(TownshipTitle.Title.Town);
            }
            title.setTitle(TownshipTitle.Title.Village);
        }
        // Max amount matching.
        unemployed.setMaxAmount(maxpopamount);
        unburieddead.setMaxAmount(maxpopamount * 100);
        sick.setMaxAmount(maxpopamount);
    }

    /**
     * Updates the the amount of jobs of a specified type that can be worked.
     *
     * @param b the building used to update the relevant job limit.
     */
    public void UpdateJobLimit(Building b) {
        for (Occupation j : occupationlist) {
            if (j.getJobBuiling() != null && j.getJobBuiling().equals(b.getName())) {
                j.setMaxAmount((int) (b.getAmount() * b.getCapacity()));
            }
        }
    }

    // If there is no more stored food, the percentage of starving people go up each second, adding to the number of deaths.
    private void StarvationMultiplierUpdater() {
        if (starving) {
            starvationpercentage++;
            Console.println("Starvation in effect " + starvationpercentage, Console.Type.s);
        } else {
            starvationpercentage = 1; // It resets once there is food and/or people stop starving to death.
        }
        // Formula is: Base 1 plus a base of 1% of the current population plus N% of the population where N increments each second.
        starvationmultiplier = 1 + (popamount * (0.01f + (starvationpercentage * 0.01f)));
    }

    @Override
    public void TDUpdate(Game game) {
        PopulationHungerUpkeep(game.rm);
        StarvationMultiplierUpdater();
        CheckSickness(game.rm);
        BuryDead();
    }

    @Override
    public void TIDUpdate(Game game) {
//        UpdatePopulation();
    }

    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        try {
            switch (tmp[1]) {
                case "getnextworkercost":
                    Console.println(getNextWorkerCost());
                    break;
                case "getpopulationamount":
                    Console.println(getPopulationAmount());
                    break;
                case "getmaxpopulationamount":
                    Console.println(getMaxPopulationAmount());
                    break;
                case "setmaxamount":
                    setMaxAmount(Long.valueOf(command.replaceAll("\\D+", "")));
                    break;
                case "addmaxamount":
                    addMaxAmount(Long.valueOf(command.replaceAll("\\D+", "")));
                    break;
                case "subtractmaxamount":
                    subtractMaxAmount(Long.valueOf(command.replaceAll("\\D+", "")));
                    break;
                case "settitle":
                    title.execute(command);
                    break;
                case "help":
                    Console.println("getnextworkercost: Displays next worker cost.");
                    Console.println("getpopulationamount: Displays the amount.");
                    Console.println("getmaxpopulationamount: Displays the max amount.");
                    Console.println("setmaxamount: Sets the max amount.");
                    Console.println("addmaxamount: Adds to the max amount.");
                    Console.println("subtractmaxamount: Substracts from the max amount.");
                    Console.println("help: Displays these messages.");
                    Console.println("Usage: pm.[job identifier].[job command]");
                    break;
                default:
                    for (Occupation j : occupationlist) {
                        if (j.getName().toLowerCase().equals(tmp[1])) {
                            j.execute(command);
                            return;
                        }
                    }
                    Console.println("Invalid command.");
                    Console.println(" @" + getClass().getName());
            }
        } catch (IndexOutOfBoundsException e) {
            Console.println("Invalid command.");
            Console.println(" @" + getClass().getName());
        }
    }
}
