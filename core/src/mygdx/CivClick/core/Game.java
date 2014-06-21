package mygdx.CivClick.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Whiplash
 */
public class Game implements Serializable {

    public ResourceManager rm;
    public PopulationManager pm;
    public BuildingManager bm;
    public List<Updateable> updatelist;

    public Game() {
        rm = new ResourceManager();
        pm = new PopulationManager();
        bm = new BuildingManager();
        updatelist = new ArrayList<>();
        updatelist.add(rm);
        updatelist.add(pm);
    }

}
