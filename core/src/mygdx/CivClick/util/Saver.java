package mygdx.CivClick.util;

import mygdx.CivClick.core.Game;
import mygdx.CivClick.core.Updateable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Whiplash
 */
public class Saver implements Updateable {

    private double currenttime = System.currentTimeMillis();
    private double oldsavetime;
    private double newsavetime;
    private final int defaultsaveinterval = 600; // In seconds.
    private final String savefilename = "Civclicker.sav";
    private int saveinterval; // In seconds;

    public Saver() {
        saveinterval = defaultsaveinterval;
        oldsavetime = currenttime;
        setNewSaveTime();
    }

    /**
     * Resets the save interval to default. Default 60 seconds.
     *
     */
    public void resetSaveInterval() {
        saveinterval = defaultsaveinterval;
    }

    /**
     * Sets the save interval. Default 60 seconds.
     *
     * @param value what to set the save interval to. Cannot be lower than one
     * minute.
     */
    public void setSaveInterval(int value) {
        saveinterval = (Math.max(60, value));
    }

    private boolean checkSaveTime() {
        return currenttime >= newsavetime;
    }

    private void setNewSaveTime() {
        newsavetime = ((currenttime / 1000) + saveinterval) * 1000;
    }

    private void updateCurrentTime() {
        currenttime = System.currentTimeMillis();
    }

    private void AutoSave(Game game) {
        if (checkSaveTime()) {
            Console.println("Attempting to autosave...", Console.Type.s);
            Save(game);
            oldsavetime = newsavetime;
            setNewSaveTime();
            Console.println("Game Saved!");
        }
    }

    /**
     * Attempts to save the game to a .sav file.
     *
     * @param game
     */
    public void Save(Game game) {
        Console.println("Attempting to save...", Console.Type.s);
        try {
            ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(savefilename));
            OOS.writeObject(game);
        } catch (IOException e) {
            Console.println(e.getMessage(), Console.Type.s);
        }
        Console.println("Game Saved!");
    }

    @Override
    public void TDUpdate(Game game) {
        updateCurrentTime();
        AutoSave(game);
    }

    @Override
    public void TIDUpdate(Game game) {

    }

}
