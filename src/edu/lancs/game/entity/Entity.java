package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public abstract class Entity extends Sprite {
    private Texture texture;
    private ArrayList<Texture> animation;
    private Clock frameClock;
    private int frame;
    private Window window;
    private boolean isAnimated;

    public Entity(Window window, String textureName, float xPos, float yPos, boolean isAnimated) {
        this.window = window;
        this.frameClock = new Clock();
        this.animation = new ArrayList<>();
        this.isAnimated = isAnimated;

        if (isAnimated) {
            animation = window.getResourceManager().getAnimations(textureName); // loads the textureName as an animation
            setTexture(animation.get(0), true); // sets the texture to the first frame of the animation
        } else {
            texture = window.getResourceManager().getTextures(textureName); // loads the textureName as a single texture
            setTexture(texture, true); // sets the texture to the single texture
        }

        setOrigin(Vector2f.div(new Vector2f(getTexture().getSize()), 2)); // sets the origin to the centre
        setPosition(new Vector2f(xPos, yPos)); // sets the position to the ones passed through in the parameters
    }

    /***
     * Special actions such as unlocking a door and such would go in here.
     */
    public abstract void performAction();

    /***
     * Animation, movement, and other things that are frequently updated should be placed in an override of this.
     */
    public abstract void update();

    /***
     * Returns the current frame in the animation.
     *
     * @return - Current animation frame
     */
    public int getFrame() {
        return frame;
    }

    /***
     * Sets the current frame in the animation.
     * Useful for starting a frame at a different point than just the beginning.
     * @param frame - Frame to be animated
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /***
     * Sets the next frame and loops back around to the first when the animation is over.
     * If called without a valid animation loaded into animation, there will be a NullPointerException
     */
    public void nextFrame() {
        if (isAnimated) {
            frame++;
            if (frame >= animation.size())
                frame = 0;

            setTexture(animation.get(frame), true);
        }
    }

    /***
     * Returns the current animation being played.
     * Useful for getting the frames when drawing the actual animation.
     *
     * @return - Animation current being played
     */
    public ArrayList<Texture> getAnimation() {
        return animation;
    }

    /***
     * Sets the current animation to be displayed.
     * As an entity can have multiple animations, this is useful as you can specify which one is being played.
     *
     * @param animation - Animation to be played
     */
    public void setAnimation(ArrayList<Texture> animation) {
        this.animation = animation;
        this.frame = 0; // resets frame to 0 to prevent mismatched animation lengths (NullPointer)
    }

    /***
     * Returns the Window that is passed around.
     * TODO: Improve this by making Window static
     *
     * @return - The Window that is being passed around
     */
    public Window getWindow() {
        return window;
    }
}
