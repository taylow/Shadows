package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

import static edu.lancs.game.Constants.MAP_TILE_SCALE;

public class MiniMap {

    private Level[][] levels;
    private ArrayList<RectangleShape> mapTiles;

    private Window window;

    public MiniMap(Window window, Level[][] levels) {
        //TODO: basically, the way I'd do this is look at the 2D array of the Levels and get their position and draw then as little squares. Speak to Taylor for help on this.
        this.window = window;
        this.levels = levels;
    }

    public void updateMap() {
        int levelXoffset = 0;
        int levelYoffset = 0;
        mapTiles = new ArrayList<>();

        for(int column = 0; column < levels.length; column++) {
            for(int row = 0; row < levels[column].length; row++) {
                RectangleShape rectangleShape = new RectangleShape();

                System.out.println(getWindow().getView().getCenter().x);

                rectangleShape.setSize(new Vector2f(levels[column][row].getWidth() * MAP_TILE_SCALE, levels[column][row].getHeight() * MAP_TILE_SCALE));
                rectangleShape.setFillColor(new Color(levels[column][row].getLevelColour(), 110));

                // draws the rectangle at the centre of teh screen, using the offsets
                rectangleShape.setPosition(getWindow().getView().getCenter().x + (levelXoffset * MAP_TILE_SCALE) - 100,
                        getWindow().getView().getCenter().y + (levelYoffset * MAP_TILE_SCALE) - 100);

                mapTiles.add(rectangleShape);

                levelXoffset += levels[column][row].getWidth() + 10;

            }
            levelYoffset += levels[column][0].getHeight() + 10;
            levelXoffset = 0;
        }
    }

    public ArrayList<RectangleShape> getMapTiles() {
        return mapTiles;
    }

    public Window getWindow() {
        return window;
    }
}
