
package edu.lancs.game.entity.behaviour;
import org.jsfml.system.Clock;

public class StatsBehaviour
{
    private int currentHealth = 20;
    private int maxHealth = 20;
    private int armor = 100;

    private int totalKills = 0;
    private int totalDamageDealt = 0;

    private Clock timer = new Clock();
    private Clock shootingClock = new Clock();


    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public void setCurrentHealth( int currentHealth )
    {
        this.currentHealth = currentHealth;
    }
}

