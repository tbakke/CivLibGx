package mygdx.CivClick.util;

import mygdx.CivClick.core.Game;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Whiplash
 */
public class Loader {

    private final String savefilename = "Civclicker.sav";
    public Game game = null;

    public Loader() {

    }

    /**
     * Tries to load a previous save file.
     *
     * @return whether or not a save file was found.
     */
    public boolean LoadGame() {
        Console.println("Attempting to load file...", Console.Type.s);
        try {
            ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(savefilename));
            game = (Game) OIS.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            Console.println(e.getMessage(), Console.Type.s);
            return false;
        }
    }

}
