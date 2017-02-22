package edu.lancs.game.gui;

import edu.lancs.game.Window;
import edu.lancs.game.entity.Player;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

import static edu.lancs.game.Constants.*;

public class Lighting {
    private ArrayList<RectangleShape> lighting;
    private Player player;
    private Window window;

    public Lighting(Window window, Player player) {
        //TODO: Lighting is pretty laggy as 1000s of little squares are being created and deleted as the game runs
        this.window = window;
        this.player = player;
        lighting = new ArrayList<>();
    }

    public void generateLighting(int radius) {
        lighting = new ArrayList<>();
        int width = GAME_WIDTH / LIGHTING_TILE_WIDTH;
        int height = GAME_HEIGHT / LIGHTING_TILE_HEIGHT;
        float offsetX = getWindow().getView().getCenter().x - (getWindow().getView().getSize().x / 2);
        float offsetY = getWindow().getView().getCenter().y - (getWindow().getView().getSize().y / 2);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int lightingX = x * LIGHTING_TILE_HEIGHT;
                int lightingY = y * LIGHTING_TILE_HEIGHT;
                int cameraX = GAME_WIDTH / 2;
                int cameraY = GAME_HEIGHT / 2;

                double distance = Math.sqrt(Math.pow(lightingX - cameraX, 2) + Math.pow(lightingY - cameraY, 2));

                RectangleShape lightingTile = new RectangleShape(new Vector2f(lightingX, lightingY));
                lightingTile.setSize(new Vector2f(LIGHTING_TILE_WIDTH, LIGHTING_TILE_HEIGHT));
                lightingTile.setPosition(new Vector2f(offsetX + lightingX, offsetY + lightingY));

                int brightness = ((int) distance / radius) * 75; //FIXME: Can be improved to have a more accurate radial effect

                if(distance > radius)
                    lightingTile.setFillColor(new Color(Color.BLACK, brightness));
                else
                    lightingTile.setFillColor(new Color(Color.WHITE, 0));
                lighting.add(lightingTile);
            }
        }
    }

    public ArrayList<RectangleShape> getLighting() {
        return lighting;
    }

    public Player getPlayer() {
        return player;
    }

    public Window getWindow() {
        return window;
    }
}
