package mygdx.CivClick;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mygdx.CivClick.core.CivUI;
import mygdx.CivClick.core.Game;
import mygdx.CivClick.core.Updateable;
import mygdx.CivClick.util.Console;
import mygdx.CivClick.util.Loader;
import mygdx.CivClick.util.Saver;

public class CivClicker extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static boolean isrunning = true, devmode = false;
        public static Game game;
        private static CivUI ui;
        private static Saver saver;
        private static Loader loader;
        
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
        
         private static void Initialize(String[] args) {
        saver = new Saver();
        loader = new Loader();
        if (loader.LoadGame()) {
            game = loader.game;
            if (args != null) {
                for (String s : args) {
                    switch (s) {
                        case "-d":
                            devmode = true;
                            break;
                        case "-l":
                            Console.StartLog();
                            break;
                    }
                }
            }
            ui = new CivUI(game, devmode);
            Console.println("Save file found,\n loading game...");
            Console.println("Game loaded!");
        } else {
            game = new Game();
            if (args != null) {
                for (String s : args) {
                    switch (s) {
                        case "-d":
                            devmode = true;
                            break;
                        case "-l":
                            Console.StartLog();
                            break;
                    }
                }
            }
            ui = new CivUI(game, devmode);
            Console.println("No save file found.");
            Console.println("New game created!");
        }
        ui.setVisible(true);
    }

    /**
     * Restarts the game by creating a new instance of Game.
     *
     * @return the new game.
     */
    public static Game Restart() {
        Console.println("Game Restarting.");
        game = new Game();
        return game;
    }

    /**
     * Saves the current game.
     */
    public static void Save() {
        saver.Save(game);
    }

    /**
     * Loads a game from a .sav file.
     *
     * @return the loaded game if true, otherwise the new game.
     */
    public static Game Load() {
        if (loader.LoadGame()) {
            game = loader.game;
            Console.println("Game loaded!");
            return game;

        }
        return game;
    }
    
    public static void run (String[] args){
        // Game loop stuff.
        long lastloop = System.nanoTime();
        int targetfps = 60;
        long targettime = 1000000000 / targetfps;
        double delta = 0;

        Initialize(args);

        while (isrunning) {
            //Game loop stuff.
            long Now = System.nanoTime();
            long updateLength = Now - lastloop;
            delta += ((double) updateLength / 1000000000);
            lastloop = Now;

            if (delta >= 1) { // Time dependent updates are handled first
                for (Updateable u : game.updatelist) { // before TID updates.
                    u.TDUpdate(game);
                }
                saver.TDUpdate(game);
                delta = 0;
            }
            for (Updateable u : game.updatelist) { // Time Independent updates
                u.TIDUpdate(game);
                ui.TIDUpdate(game);
            }

            try {
                // Hacky way of keeping the sleep value out of negatives.
                // Ideally the thread sleep will be 16 milliseconds regardless.
                if ((lastloop - System.nanoTime() + targettime) / 1000000 < 0) {
                    Thread.sleep(16);
                } else {
                    Thread.sleep((lastloop - System.nanoTime() + targettime) / 1000000);
                }
            } catch (InterruptedException e) {
                Console.println(e.getMessage(), Console.Type.s);
            }
        }

    }
}
