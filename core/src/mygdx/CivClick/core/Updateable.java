package mygdx.CivClick.core;

/**
 *
 * @author Whiplash
 */
public interface Updateable {

    /**
     * Time dependent update method.
     *
     * @param game
     */
    public void TDUpdate(Game game);

    /**
     * Time independent update method.
     *
     * @param game
     */
    public void TIDUpdate(Game game);

}
