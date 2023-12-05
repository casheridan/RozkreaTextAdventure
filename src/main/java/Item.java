import java.util.Random;

public class Item {
    private final String name;
    private final ItemType type;
    public enum ItemType {
        WEAPON,
        ARMOR,
        POTION,
        CONSUMABLE,
        MISC
    }
    private int diceMax;
    private int modifier;
    private int itemPrice;

    public Item(String name, ItemType type, int price) {
        this.name = name;
        this.type = type;
        this.itemPrice = price;
    }

    public Item(String name, ItemType type, int mod, int price) {
        this.name = name;
        this.type = type;
        this.diceMax = 0;
        this.modifier = mod;
        this.itemPrice = price;
    }

    public Item(String name, ItemType type, int dice, int mod, int price) {
        this.name = name;
        this.type = type;
        this.diceMax = dice;
        this.modifier = mod;
        this.itemPrice = price;
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

    public int getItemPrice() { return itemPrice; }

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
            switch (name) {
                case "Gold Pouch" -> {
                    Random rand = new Random();
                    int max = 15, min = 5;
                    int gold = rand.nextInt(max - min + 1) + min;
                    player.addGold(gold);
                    System.out.println("Added " + gold + " gold.");
                }
                case "Map of Hitpoints" -> {
                    System.out.println("The map has a bunch of red cicles on certain buildings in Thalud. \nOne circle has a skull with a dagger in it next to the Keep.");
                }
                default -> System.out.println("Invalid Item");
            }
        }
    }
}
