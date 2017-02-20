package edu.lancs.game.entity.behaviour;

import edu.lancs.game.InputHandler;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

public class PlayerShootBehaviour {
    private InputHandler input;
    private Sprite playerSprite;
    private Clock launchCooldown = new Clock(); // firing cooldown.
    private boolean isProjectileLaunched;

    public PlayerShootBehaviour(InputHandler input, Sprite playerSprite) {
        this.input = input;
        this.playerSprite = playerSprite;
    }

    public void update() {
        if (input.isMouseClicked() == true && launchCooldown.getElapsedTime().asSeconds() > 0.1f) {
            isProjectileLaunched = true;
            launchCooldown.restart();
        } else isProjectileLaunched = false;
    }

    public boolean isProjectileLaunched() {
        return isProjectileLaunched;
    }
}
