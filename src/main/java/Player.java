import java.util.ArrayList;
import java.util.Random;

public class Player {
    private Room currentRoom;
    private Item rightHand;
    private Item equippedArmor;
    private int health;
    private final int maxHealth;
    private final ArrayList<Item> inventory;

    public Player(Room location, int health) {
        super();
        this.currentRoom = location;
        this.inventory = new ArrayList<>();
        this.maxHealth = health;
        this.health = this.maxHealth;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Item getEquippedArmor() {
        return equippedArmor;
    }

    public int getCurrentHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Item getRightHand() {
        return rightHand;
    }

    public ArrayList<Item> getInventory() { return inventory; }

    public void equipItem(Item item) {
        switch (item.getType()) {
            case ARMOR -> {
                if (equippedArmor != null) System.out.println("You already have a set of armor equipped. You must unequip it first.");
                else equippedArmor = item;
            }
            case WEAPON -> {
                if (rightHand != null) System.out.println("You already have an weapon in hand. You must unequip it first.");
                else rightHand = item;
            }
            case CONSUMABLE -> {

            }
        }
    }

    public void unequipItem(Item item) {
        switch (item.getType()) {
            case ARMOR -> {
                if (equippedArmor != null) System.out.println("You don't have any armor equiped.");
                else equippedArmor = null;
            }
            case WEAPON -> {
                if (rightHand != null) System.out.println("You don't have anything equiped in your right hand.");
                else rightHand = null;
            }
            default -> System.out.println("This item isn't equipable.");
        }
    }

    public void useItem(Item item) {
        item.use(this);
    }

    public Item dropInventoryItem(int itemNum) {
        Item droppedItem = inventory.get(itemNum - 1);
        inventory.remove(itemNum - 1);
        return droppedItem;
    }

    public void removeInventoryItem(int itemNum) {
        inventory.remove(itemNum);
    }

    public void setCurrentRoom(Room location) {
        this.currentRoom = location;
    }

    public void setHealth(int heal) {
        this.health = heal;
        if (health > maxHealth) health = maxHealth;
    }

    public void printInventory() {
        System.out.println("Inventory:\n");

        if(inventory.isEmpty()) {
            System.out.println("No Items in Inventory");
        }
        else {
            for (int i = 0; i < inventory.size(); i++) {
                boolean isEquipped = inventory.get(i).equals(equippedArmor) || inventory.get(i).equals(rightHand);
                System.out.println((i + 1) + ": " + inventory.get(i).getName() + (isEquipped ? " (Equipped)" : ""));
            }
        }
        System.out.println();
    }

    public void addItem(Item item) {
        System.out.println("Picked up " + item.getName());
        inventory.add(item);
    }

    public void fightEnemy(Character enemy) {
        System.out.println("Rolling...");
        Random rand = new Random();
        int max = 20, min = 1;
        int hitRoll = rand.nextInt(max - min + 1) + min;
//        wait(3000);
        System.out.println("You rolled a " + hitRoll + " to hit.");
        if (hitRoll >= enemy.getArmorClass()) {
//            wait(1500);
            System.out.println("You hit! Rolling for damage...");
//            wait(3000);
            if (rightHand != null) {
                rand = new Random();
                max = rightHand.getDiceMax();
                int damageRoll = rand.nextInt(max - min + 1) + min;
                damageRoll += rightHand.getModifier();

                System.out.println("You dealt " + damageRoll + " damage to " + enemy.getName());
                enemy.takeDamage(damageRoll);
            }
            else {
                rand = new Random();
                max = 4;
                int damageRoll = rand.nextInt(max - min + 1) + min;

                System.out.println("You dealt " + damageRoll + " damage to " + enemy.getName());
                enemy.takeDamage(damageRoll);
            }
        }
        else {
            System.out.println("Attack Missed.");
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println("You have died");
            System.exit(0);
        }
    }
}
