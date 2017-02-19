package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.Scene;

import static edu.lancs.game.Constants.*;

public class BackButton extends Button {

    private Window window;

    public BackButton(Window window, Scene parentScene, float xPos, float yPos) {
        super(window, parentScene, xPos, yPos, 128, 128);
        setDefaultTexture(window.getResourceManager().getTextures("back_arrow"));
        setSelectTexture(window.getResourceManager().getTextures("back_arrow"));
    }

    @Override
    public void click() {
        Debug.print("[Button] Back Button");
        getWindow().setCurrentScene(0);
        //TODO: This whole section is fucked up. Need a higher up InputHandler for this
    }
}
