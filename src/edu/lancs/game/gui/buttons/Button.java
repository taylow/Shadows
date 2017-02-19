package edu.lancs.game.gui.buttons;

import edu.lancs.game.Window;
import edu.lancs.game.scenes.Scene;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public abstract class Button extends RectangleShape {
    private Window window;
    private Scene parentScene;

    private boolean isSelected;

    private Texture defaultTexture;
    private Texture selectTexture;

    private String text;

    public Button(Window window, Scene parentScene, float xPos, float yPos, float width, float height) {
        this.window = window;
        this.parentScene = parentScene;
        this.isSelected = false;

        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }

    public abstract void performEvent();

    public void setDefaultTexture(Texture defaultTexture) {
        this.defaultTexture = defaultTexture;
        setTexture(defaultTexture);
    }

    public void setSelectTexture(Texture selectTexture) {
        this.selectTexture = selectTexture;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if(isSelected)
            setTexture(selectTexture);
        else
            setTexture(defaultTexture);
    }

    public void setText(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public Window getWindow() {
        return window;
    }

    public Scene getParentScene() {
        return parentScene;
    }
}
