package edu.lancs.game.entity;

/**
 * Created by Shez on 26/02/2017.
 */
import java.util.Random;
public class Loot {

        public Loot(){
        }

        public int giveGold(){
            Random random = new Random();
            return random.nextInt(30 - 10 + 1) + 10;
        }

        public int giveHealth(){
            Random random = new Random();
            return random.nextInt(20 - 5 + 1) + 5;
        }

        public void chanceOfTreasure(Player p){
            Random ran = new Random();
            int n = ran.nextInt(2);
            int gold = giveGold();
            int health = giveHealth();
            //System.out.println(n);
            if(n == 1){
                p.setGold(p.getGold() + gold);
                System.out.print("You have found a bag with "+gold+" gold!");
            }else{
                int test = (p.getHealth() + health);
                if(test >= 100){
                    p.setHealth(100);
                    System.out.print("You have found an HP Potion, your health is full!");
                }else{
                    p.setHealth(p.getHealth() + health);
                    System.out.print("You have found an HP Potion, it restored "+health+" health!");
                }
            }
        }
    }
