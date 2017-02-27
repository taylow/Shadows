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
    private boolean isDisabled;
    private boolean hasExited;

    private Texture defaultTexture;
    private Texture selectTexture;
    private Texture disabledTexture;

    private String text;

    public Button(Window window, Scene parentScene, float xPos, float yPos, float width, float height) {
        this.window = window;
        this.parentScene = parentScene;
        this.isSelected = false;
        this.isDisabled = false;

        setSize(new Vector2f(width, height));
        setPosition(new Vector2f(xPos, yPos));
    }

    /***
     * What happens when the button is actually clicked.
     */
    public abstract void click();

    /***
     * What happens when the mouse goes over the button.
     */
    public abstract void mouseOver();

    /***
     * Sets the default texture (the one that will be used by default (i.e. not the hover one)).
     *
     * @param defaultTexture - Texture to be used by default
     */
    public void setDefaultTexture(Texture defaultTexture) {
        this.defaultTexture = defaultTexture;
        setTexture(defaultTexture);
    }

    /***
     * Sets the select texture (the one that will be used when the user hovers over it).
     *
     * @param selectTexture - Texture to be used when hovering
     */
    public void setSelectTexture(Texture selectTexture) {
        this.selectTexture = selectTexture;
    }

    /***
     * Sets the disabled texture (the one that will be used when the button is disabled).
     *
     * @param disabledTexture - Texture to be used when disabled
     */
    public void setDisabledTexture(Texture disabledTexture) {
        this.disabledTexture = disabledTexture;
    }

    /***
     * Returns whether or not the button is selected.
     *
     * @return - Button select state (hover)
     */
    public boolean isSelected() {
        return isSelected;
    }

    /***
     * Sets whetehr or not the button is currently selected (hovered)
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
        if(isDisabled) {
            setTexture(disabledTexture);
        } else if (isSelected) {
            setTexture(selectTexture); // switches the texture to the hover/selected one
            if (hasExited) {
                hasExited = false;
                mouseOver(); // runs the hover code (not always needed)
            }
        } else {
            setTexture(defaultTexture); // switches the texture back to the default (no longer hovering)
            hasExited = true;
        }
    }

    /***
     * Returns the text that is being displayed on the button.
     *
     * @return - Text being displayed on the button
     */
    public String getText() {
        return text;
    }

    /***
     * Sets the text to be displayed on the button.
     * TODO: This feature has not been needed and so far has not been implemented. Do not use.
     *
     * @param text - Text to be displayed on the button
     */
    public void setText(String text) {
        this.text = text;
    }

    /***
     * Returns the parent scene that this button can return to.
     * FIXME: Somewhat strange way to achieve a back button. Works perfectly fine, there are just better ways.
     *
     * @return - Parent scene of the scene this button is in
     */
    public Scene getParentScene() {
        return parentScene;
    }

    public Window getWindow() {
        return window;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
        if(disabled) {
            setTexture(disabledTexture);
        } else {
            setTexture(defaultTexture);
        }
    }

    public boolean isDisabled() {
        return isDisabled;
    }
}
