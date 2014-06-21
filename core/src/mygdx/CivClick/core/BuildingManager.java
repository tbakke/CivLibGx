package mygdx.CivClick.core;

import mygdx.CivClick.bldg.*;
import mygdx.CivClick.util.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Whiplash
 */
public class BuildingManager implements Serializable, Invokable {

    private final List<Building> buildinglist = new ArrayList<>();
    Hut hut = new Hut("Hut", 2);
    Cottage cottage = new Cottage("Cottage", 4);
    Cabin cabin = new Cabin("Cabin", 6);
    House house = new House("House", 10);
    Smithey smithey = new Smithey("Smithey", 1);
    TradingPost tradingpost = new TradingPost("Trading Post", 1);
    HuntingLodge huntinglodge = new HuntingLodge("Hunting Lodge", 1);
    GreenHouse greenhouse = new GreenHouse("Green House", 1);
    School school = new School("School", 1);
    Barn barn = new Barn("Barn", 200);
    LumberYard lumberyard = new LumberYard("LumberYard", 200);
    StoneStockpile stonestockpile = new StoneStockpile("StoneStockpile", 200);
    StorageStockpile storagestockpile = new StorageStockpile("Storage", 500);
    Church church = new Church("Church", 1);
    Apothecary apothecary = new Apothecary("Apothecary", 1);
    Tannery tannery = new Tannery("Tannery", 1);
    Graveyard graveyard = new Graveyard("Graveyard", 100);
    Farm farm = new Farm("Farm", 10);
    Barracks barracks = new Barracks("Barracks", 100);

    public BuildingManager() {
        hut.initBaseCost(20d, 0d, 0d, 0d, 0d, 0d, 0d, 1d);
        cottage.initBaseCost(50d, 5d, 0d, 0d, 0d, 0d, 0d, 5d);
        cabin.initBaseCost(100d, 0d, 0d, 0d, 0d, 0d, 0d, 15d);
        house.initBaseCost(150d, 10d, 0d, 0d, 0d, 0d, 0d, 20d);
        smithey.initBaseCost(175d, 50d, 0d, 0d, 0d, 0d, 0d, 25d);
        tradingpost.initBaseCost(250d, 100d, 0d, 0d, 0d, 0d, 0d, 100d);
        huntinglodge.initBaseCost(150d, 0d, 0d, 0d, 0d, 0d, 0d, 10d);
        greenhouse.initBaseCost(50d, 150d, 0d, 0d, 0d, 0d, 0d, 20d);
        school.initBaseCost(300d, 200d, 0d, 0d, 0d, 0d, 0d, 150d);
        barn.initBaseCost(50d, 0d, 0d, 0d, 0d, 0d, 0d, 10d);
        lumberyard.initBaseCost(25d, 0d, 0d, 0d, 0d, 0d, 0d, 10d);
        stonestockpile.initBaseCost(25d, 25d, 0d, 0d, 0d, 0d, 0d, 10d);
        storagestockpile.initBaseCost(50d, 0d, 0d, 0d, 0d, 0d, 0d, 10d);
        church.initBaseCost(50d, 250d, 0d, 0d, 0d, 0d, 0d, 75d);
        apothecary.initBaseCost(50d, 25d, 0d, 0d, 0d, 0d, 0d, 20d);
        tannery.initBaseCost(100d, 25d, 0d, 0d, 0d, 0d, 0d, 50d);
        graveyard.initBaseCost(0d, 50d, 0d, 0d, 0d, 0d, 0d, 50d);
        farm.initBaseCost(10d, 10d, 0d, 0d, 0d, 0d, 0d, 100d);
        barracks.initBaseCost(100d, 100d, 0d, 0d, 0d, 0d, 0d, 0d);
        buildinglist.add(hut);
        buildinglist.add(cottage);
        buildinglist.add(cabin);
        buildinglist.add(house);
        buildinglist.add(smithey);
        buildinglist.add(tradingpost);
        buildinglist.add(huntinglodge);
        buildinglist.add(greenhouse);
        buildinglist.add(school);
        buildinglist.add(barn);
        buildinglist.add(lumberyard);
        buildinglist.add(stonestockpile);
        buildinglist.add(storagestockpile);
        buildinglist.add(church);
        buildinglist.add(apothecary);
        buildinglist.add(tannery);
        buildinglist.add(graveyard);
        buildinglist.add(farm);
        buildinglist.add(barracks);
    }

    /**
     * Buys buildings which are used as stock piles.
     *
     * @param b the building type to be bought.
     * @param value amount of buildings to buy.
     * @param rm the resource manager.
     */
    public void addBuilding(Building b, int value, ResourceManager rm) {
        int tmp = 0;
        while (b.isBuyable(rm) && tmp < value) {
            b.Buy(rm, 1);
            tmp++;
        }
    }

    /**
     * Buys buildings which are used for jobs.
     *
     * @param b the building type to be bought.
     * @param value amount of buildings to buy.
     * @param rm the resource manager.
     * @param pm the population manager.
     */
    public void addBuilding(Building b, int value, ResourceManager rm, PopulationManager pm) {
        int tmp = 0;
        while (b.isBuyable(rm) && tmp < value) {
            b.Buy(rm, pm, 1);
            tmp++;
        }
    }

    /**
     * Sells buildings which are used as stock piles.
     *
     * @param b the building type to be bought.
     * @param value amount of buildings to buy.
     * @param rm the resource manager.
     */
    public void removeBuilding(Building b, int value, ResourceManager rm) {
        int tmp = 0;
        while (b.isSellable(rm) && tmp < value) {
            b.Sell(rm, 1);
            tmp++;
        }
    }

    /**
     * Sells buildings which are used for jobs.
     *
     * @param b the building type to be bought.
     * @param value amount of buildings to buy.
     * @param rm the resource manager.
     * @param pm the population manager.
     */
    public void removeBuilding(Building b, int value, ResourceManager rm, PopulationManager pm) {
        int tmp = 0;
        while (b.isSellable(rm) && tmp < value) {
            b.Sell(rm, pm, 1);
            tmp++;
        }
    }

    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        try {
            switch (tmp[1]) {
                case "help":
                    Console.println("Usage: bm.[building identifier].[building command]");
                    break;
                default:
                    for (Building b : buildinglist) {
                        if (b.getName().toLowerCase().equals(tmp[1])) {
                            b.execute(command);
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
