package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.generation.Level;
import edu.lancs.game.generation.Tile;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;

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
        mapTiles = new ArrayList<>();

        RectangleShape background = new RectangleShape();
        background.setFillColor(new Color(Color.BLACK, 128));
        mapTiles.add(background);

        // creates background square for MiniMap in centre of screen
        background.setSize(new Vector2f(ROOM_WIDTH_MAX * GAME_LEVEL_WIDTH * MAP_TILE_SCALE, ROOM_HEIGHT_MAX * GAME_LEVEL_HEIGHT * MAP_TILE_SCALE));
        background.setOrigin(background.getSize().x / 2, background.getSize().y / 2);
        background.setPosition(getWindow().getView().getCenter().x, getWindow().getView().getCenter().y);

        for(int row = 0; row < levels.length; row++) {
            for(int column = 0; column < levels[row].length; column++) {
                RectangleShape rectangleShape = new RectangleShape();
                rectangleShape.setSize(new Vector2f(levels[row][column].getWidth() * MAP_TILE_SCALE, levels[row][column].getHeight() * MAP_TILE_SCALE));

                if(levels[row][column].isDiscovered())
                    rectangleShape.setFillColor(new Color(levels[row][column].getLevelColour(), 110));
                else
                    rectangleShape.setFillColor(new Color(levels[row][column].getLevelColour(), 0));

                rectangleShape.setOrigin((levels[row][column].getWidth() * MAP_TILE_SCALE) / 2, (levels[row][column].getHeight() * MAP_TILE_SCALE) / 2);

                // FIXME: Probably the most inefficient maths ever here... Should be redone to use minimal calculations.
                rectangleShape.setPosition((getWindow().getView().getCenter().x + (levels[row][column].getLevelColumnPosition() * ROOM_WIDTH_MAX) * MAP_TILE_SCALE) - background.getSize().x / 2 + (GAME_LEVEL_WIDTH * MAP_TILE_SCALE) / 2,
                        (getWindow().getView().getCenter().y + (levels[row][column].getLevelRowPosition() * ROOM_HEIGHT_MAX) * MAP_TILE_SCALE) - background.getSize().y / 2 + (GAME_LEVEL_HEIGHT * MAP_TILE_SCALE) / 2);

                mapTiles.add(rectangleShape);

                if(levels[row][column].isCurrentLevel()) {
                    rectangleShape.setOutlineColor(Color.RED);
                    rectangleShape.setOutlineThickness(2);
                }
            }
        }
        // make background slightly bigger than the drawing space
        background.setScale(1.1f, 1.1f);
    }

    public ArrayList<RectangleShape> getMapTiles() {
        return mapTiles;
    }

    public Window getWindow() {
        return window;
    }
}
