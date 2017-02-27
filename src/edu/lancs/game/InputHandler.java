package edu.lancs.game;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.JoystickButtonEvent;
import org.jsfml.window.event.JoystickEvent;
import org.jsfml.window.event.JoystickMoveEvent;

import static edu.lancs.game.Constants.*;
import static org.jsfml.window.Joystick.Axis.X;
import static org.jsfml.window.Joystick.Axis.Y;
import static org.jsfml.window.event.Event.Type.JOYSTICK_BUTTON_PRESSED;
import static org.jsfml.window.event.Event.Type.JOYSTICK_BUTTON_RELEASED;

public class InputHandler {

    private Window window;
    private boolean wKeyPressed;
    private boolean aKeyPressed;
    private boolean sKeyPressed;
    private boolean dKeyPressed;
    private boolean spaceKeyPressed;
    private boolean ctrlKeyPressed;

    private Vector2i mousePosition;

    public InputHandler(Window window) {
        this.window = window;

        // sets the current directions and space to none
        wKeyPressed = false;
        aKeyPressed = false;
        sKeyPressed = false;
        dKeyPressed = false;
        sKeyPressed = false;
        ctrlKeyPressed = false;

        mousePosition = new Vector2i(1, 1);
    }

    /***
     * Sets the current key event to whether or not it is currently being pressed.
     * This makes movement smooth as the movement speed is not linked to the refresh rate of the keyboard.
     *
     * @param key - Key currently performing an event
     */
    public void processInputs(Keyboard.Key key) {
        switch (key) {
            case W:
                wKeyPressed = Keyboard.isKeyPressed(key); // sets to W's press value
                break;

            case S:
                sKeyPressed = Keyboard.isKeyPressed(key); // sets to S's press value
                break;

            case D:
                dKeyPressed = Keyboard.isKeyPressed(key); // sets to D's press value
                break;

            case SPACE:
                spaceKeyPressed = Keyboard.isKeyPressed(key); // sets to space's press value
                break;

            case LCONTROL:
                ctrlKeyPressed = Keyboard.isKeyPressed(key); // sets to ctrl's press value
                break;

            case A:
                aKeyPressed = Keyboard.isKeyPressed(key); // sets to A's press value
                break; //TODO: You would need to remove this break as A is in the Konami Code

            // konami code easter egg
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
            case B:
                //TODO: Enter Konami Code easter egg here
                break;

        }
    }

    /***
     * Processes Joystick axis and maps them to keyboard buttons.
     *
     * @param event - JoystickMoveEvent
     */
    public void processInputs(JoystickMoveEvent event) {
        switch(event.joyAxis) {
            case X:
                if (event.position < -JOYSTICK_DEADZONE_X)
                    aKeyPressed = true;
                else
                    aKeyPressed = false;

                if (event.position > JOYSTICK_DEADZONE_X)
                    dKeyPressed = true;
                else
                    dKeyPressed = false;

                break;

            case Y:
                if(event.position < -JOYSTICK_DEADZONE_Y)
                    wKeyPressed = true;
                else
                    wKeyPressed = false;

                if(event.position > JOYSTICK_DEADZONE_Y)
                    sKeyPressed = true;
                else
                    sKeyPressed = false;

                break;
        }
    }

    /***
     * Processes Joystick buttons and maps them to keyboard buttons.
     * A = 0
     * B = 1
     * X = 2
     * Y = 3
     * LB = 4
     * RB = 5
     * BACK = 6
     * START = 7
     * L_AXIS_3 = 8
     * R_AXIS_3 = 9
     *
     * @param event - JoystickButtonEvent
     */
    public void processInputs(JoystickButtonEvent event) {
        System.out.println(event.type);
        switch(event.button) {
            case ATTACK_BUTTON:
                spaceKeyPressed = event.type == JOYSTICK_BUTTON_PRESSED;
                break;

            case MINIMAP_BUTTON:
                ctrlKeyPressed = event.type == JOYSTICK_BUTTON_PRESSED;
                break;
        }
    }

    /***
     * Updates the mouse position every time the mouse is moved.
     *
     * @param event - MouseEvent from the moving mouse
     */
    public void updateMouseMovement(Event event) {
        mousePosition = event.asMouseEvent().position; // updates mouse position
    }

    /***
     * Returns whether or not the A key is pressed.
     *
     * @return - A keys pressed value
     */
    public boolean isaKeyPressed() {
        return aKeyPressed;
    }

    /***
     * Returns whether or not the D key is pressed.
     *
     * @return - D keys pressed value
     */
    public boolean isdKeyPressed() {
        return dKeyPressed;
    }

    /***
     * Returns whether or not the S key is pressed.
     *
     * @return - S keys pressed value
     */
    public boolean issKeyPressed() {
        return sKeyPressed;
    }

    /***
     * Returns whether or not the W key is pressed.
     *
     * @return - W keys pressed value
     */
    public boolean iswKeyPressed() {
        return wKeyPressed;
    }

    /***
     * Returns whether or not the SPACE key is pressed.
     *
     * @return - SPACE keys pressed value
     */
    public boolean isSpaceKeyPressed() {
        return spaceKeyPressed;
    }

    /***
     * Returns whether or not the LCONTROL key is pressed.
     *
     * @return - LCONTROL keys pressed value
     */
    public boolean isCtrlKeyPressed() {
        return ctrlKeyPressed;
    }

    /***
     * Returns whether or not any of the directional keys are being used (useful for preventing attacking while moving).
     *
     * @return - A keys pressed value
     */
    public boolean isMoveing() {
        return wKeyPressed || aKeyPressed || sKeyPressed || dKeyPressed;
    }

    /***
     * Returns the current mouse position.
     *
     * @return - Current mouse position
     */
    public Vector2i getMousePosition() {
        return mousePosition;
    }

}
