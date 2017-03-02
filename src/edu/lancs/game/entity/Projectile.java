package edu.lancs.game.entity;

import edu.lancs.game.Window;
import org.jsfml.system.Vector2f;

import static edu.lancs.game.Constants.GAME_HEIGHT;
import static edu.lancs.game.Constants.GAME_WIDTH;

public class Projectile extends Entity {
    private int damage;
    private Vector2f start;
    private Vector2f destination;
    private boolean hasArrived;

    public Projectile(Window window, Vector2f destination, Vector2f start, int damage, int speed, boolean mouse) {
        super(window, "test", start.x, start.y, false);
        this.damage = damage;
        this.start = start;
        hasArrived = false;
        //FIXME: This only works for mouse position, needs fixing to work towards anywhere (enemy to player)
        if(mouse)
            this.destination = new Vector2f((getWindow().getView().getCenter().x + (destination.x - GAME_WIDTH / 2)), (getWindow().getView().getCenter().y + (destination.y - GAME_HEIGHT / 2)));
        else
            this.destination = destination;

        setScale(0.7f, 0.7f);
    }

    @Override
    public void performAction() {

    }

    @Override
    public void update() {
        moveTowards(destination);
    }

    public void moveTowards(Vector2f destination) {
        float distanceX = destination.x - getPosition().x;
        float distanceY = destination.y - getPosition().y;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        if(distance > 20)
            setPosition(getPosition().x + distanceX / 10, getPosition().y + distanceY / 10);
        else
            hasArrived = true;
    }

    public boolean hasArrived() {
        return hasArrived;
    }

    public int getDamage() {
        return damage;
    }
}
