package mygdx.CivClick.core;

import mygdx.CivClick.util.Console;
import java.io.Serializable;

/**
 *
 * @author Whiplash
 */
public class TownshipTitle implements Invokable, Serializable {
    
    private Title title;
    
    public TownshipTitle(Title title) {
        this.title = title;
    }
    
    public Title getTitle() {
        return title;
    }
    
    public void setTitle(Title title) {
        this.title = title;
    }
    
    @Override
    public void execute(String command) {
        String[] tmp = command.split(delimiter);
        try {
            switch (tmp[2]) {
                case "hamlet":
                    setTitle(Title.Hamlet);
                    break;
                case "village":
                    setTitle(Title.Village);
                    break;
                case "town":
                    setTitle(Title.Town);
                    break;
                case "city":
                    setTitle(Title.City);
                    break;
                case "kingdom":
                    setTitle(Title.Kingdom);
                    break;
                case "metropolis":
                    setTitle(Title.Metropolis);
                    break;
                case "help":
                    Console.println("Usage: Hamlet, Village, Town, City, Kingdom, Metropolis");
                    break;
                default:
                    Console.println("Invalid command.");
                    Console.println(" @" + getClass().getName());
            }
        } catch (IndexOutOfBoundsException e) {
            Console.println("Invalid command.");
            Console.println(" @" + getClass().getName());
        }
    }
    
    public enum Title {
        
        Hamlet, Village, Town, City, Kingdom, Metropolis
    }
}
