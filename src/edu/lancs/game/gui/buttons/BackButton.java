package edu.lancs.game.gui.buttons;

import edu.lancs.game.Debug;
import edu.lancs.game.Window;
import edu.lancs.game.scenes.Scene;

import static edu.lancs.game.Constants.MENU_BUTTON_HEIGHT;
import static edu.lancs.game.Constants.MENU_BUTTON_WIDTH;

public class BackButton extends Button {

    private Window window;

    public BackButton(Window window, Scene parentScene, float xPos, float yPos) {
        super(window, parentScene, xPos, yPos, MENU_BUTTON_WIDTH / 2, MENU_BUTTON_HEIGHT / 2);
        setDefaultTexture(window.getResourceManager().getTextures("back_default"));
        setSelectTexture(window.getResourceManager().getTextures("back_hover"));
    }

    @Override
    public void click() {
        Debug.print("[Button] Back Button");
        getWindow().setCurrentScene(0); // takes you back to the menu scene FIXME: this is a little hard coded as 0 could not be the menu (it should never not be, but it "could" be)
    }

    @Override
    public void mouseOver() {
        getWindow().getResourceManager().getSound("menu_click").play(); // plays the menu click sound upon mouseover
    }
}
