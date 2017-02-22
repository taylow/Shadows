package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

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

    /***
     * Draws the map using the level array by creating X and Y amount of RectangleShapes with the level colour.
     * Only draws Rectangles for the levels that have isDiscovered set to true.
     */
    public void updateMap() {
        //TODO: I need to comment this. It was done at 5AM after no sleep
        //FIXME: Also, a re-write/improvement would be nice. It works, just could be slightly more efficient and so the tiles are inline with each door
        int levelXoffset = 0;
        int levelYoffset = 0;
        mapTiles = new ArrayList<>();

        RectangleShape background = new RectangleShape();
        background.setFillColor(new Color(Color.BLACK, 128));
        mapTiles.add(background);

        for(int row = 0; row < levels.length; row++) {
            for(int column = 0; column < levels[row].length; column++) {
                RectangleShape rectangleShape = new RectangleShape();

                rectangleShape.setSize(new Vector2f(levels[row][column].getWidth() * MAP_TILE_SCALE, levels[row][column].getHeight() * MAP_TILE_SCALE));
                if(levels[row][column].isDiscovered())
                    rectangleShape.setFillColor(new Color(levels[row][column].getLevelColour(), 110));
                else
                    rectangleShape.setFillColor(new Color(levels[row][column].getLevelColour(), 0));

                rectangleShape.setOrigin((levels[row][column].getWidth() * MAP_TILE_SCALE) / 2, (levels[row][column].getHeight() * MAP_TILE_SCALE) / 2);
                // draws the rectangle at the centre of teh screen, using the offsets
                rectangleShape.setPosition(getWindow().getView().getCenter().x + (levelYoffset * MAP_TILE_SCALE) - 100 * MAP_TILE_SCALE,
                        getWindow().getView().getCenter().y + (levelXoffset * MAP_TILE_SCALE) - 100 * MAP_TILE_SCALE);

                mapTiles.add(rectangleShape);

                if(levels[row][column].isCurrentLevel()) {
                    rectangleShape.setOutlineColor(Color.RED);
                    rectangleShape.setOutlineThickness(2);
                }

                levelXoffset += levels[row][column].getWidth() + 10;
            }
            levelYoffset += levels[row][0].getHeight() + 10;
            levelXoffset = 0;
        }
        background.setSize(new Vector2f(250 * MAP_TILE_SCALE, 250 * MAP_TILE_SCALE));
        background.setPosition(getWindow().getView().getCenter().x - (250 * MAP_TILE_SCALE) / 2,
                getWindow().getView().getCenter().y - (250 * MAP_TILE_SCALE) / 2);

    }

    public ArrayList<RectangleShape> getMapTiles() {
        return mapTiles;
    }

    public Window getWindow() {
        return window;
    }
}
