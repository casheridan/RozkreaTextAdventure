import java.util.Random;

public class Character {
    private final String name;
    private int health;
    private final int armorClass;
    private final Item weapon;
    private final CharType type;
    private boolean isDead;

    public enum CharType {
        ENEMY,
        ALLY
    }

    public Character(String name, int health, int armorClass, Item weapon, CharType type) {
        this.name = name;
        this.health = health;
        this.isDead = false;
        this.armorClass = armorClass;
        this.weapon = weapon;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CharType getType() {
        return type;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public boolean getDeathStatus() {
        return isDead;
    }

    public void fightPlayer(Player player) {
        Random rand = new Random();
        int max = 20, min = 1;
        int hitRoll = rand.nextInt(max - min + 1) + min;
        int playerAC = 0;
        if (player.getEquippedArmor() != null) {
            playerAC = player.getEquippedArmor().getModifier();
        }
        if (hitRoll >= playerAC) {
            if (weapon != null) {
                rand = new Random();
                max = weapon.getDiceMax();
                int damageRoll = rand.nextInt(max - min + 1) + min;
                damageRoll += weapon.getModifier();

                System.out.println(name + " dealt " + damageRoll + " damage to you.");
                player.takeDamage(damageRoll);
            }
            else {
                rand = new Random();
                max = 4;
                int damageRoll = rand.nextInt(max - min + 1) + min;

                System.out.println(name + " dealt " + damageRoll + " damage to you.");
                player.takeDamage(damageRoll);
            }
        }
        else {
            System.out.println( name + "'s attack missed.");
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }
}
