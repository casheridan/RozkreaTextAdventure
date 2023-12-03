import java.util.Random;

public class Item {
    private final String name;
    private final ItemType type;
    public enum ItemType {
        WEAPON,
        ARMOR,
        POTION,
        CONSUMABLE
    }
    private int diceMax;
    private int modifier;

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public Item(String name, ItemType type, int mod) {
        this.name = name;
        this.type = type;
        this.diceMax = 0;
        this.modifier = mod;
    }

    public Item(String name, ItemType type, int dice, int mod) {
        this.name = name;
        this.type = type;
        this.diceMax = dice;
        this.modifier = mod;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getDiceMax() {
        return diceMax;
    }

    public int getModifier() {
        return modifier;
    }

    public void use(Player player) {
        if (type == ItemType.POTION) {
            // 0 = health, 1 = experience
            if (modifier == 0) {
                Random rand = new Random();
                int max = 8, min = 1;
                int healRoll = rand.nextInt(max - min + 1) + min;
                player.setHealth(player.getCurrentHealth() + healRoll);
                System.out.println("You gained " + healRoll + " health.");
            }
            else {
                // TODO: Add potion of experience
            }
        }
        else {
            // TODO: when we have more items that are consumable add effects here
        }
    }
}
