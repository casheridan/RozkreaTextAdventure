import java.util.Objects;
import java.util.Scanner;

public class AdventureModel {
    private Player player;

    public AdventureModel() {}

    public void initGame() {
        Room[] rooms = new Room[16];
        Item[] items = new Item[10];
        Character[] characters = new Character[5];

        items[0] = new Item("Short Sword",  Item.ItemType.WEAPON, 8, 0);
        items[1] = new Item("Padded Armor",  Item.ItemType.ARMOR, 11);
        items[2] = new Item("Health Potion", Item.ItemType.POTION);

        characters[0] = new Character("Dark Slime", 3, 5, null, Character.CharType.ENEMY);
        characters[1] = new Character("Colton", 5, 9, items[0], Character.CharType.ALLY);

        // Progression order is backwards to have link backs.

        rooms[2] = new Room("Forest", "This area contains the first ally quest giver.");
        rooms[2].addCharacter(characters[1]);

        rooms[1] = new Room("Hill", "You pass along the shore. Eventually you reach a hill with some grass. You decide to climb it and reach the top.");
        rooms[1].addItem(items[2]);
        rooms[1].addCharacter(characters[0]);
        rooms[1].addExit(rooms[2]);
        rooms[2].addExit(rooms[1]); // Link back to previous room

        rooms[0] = new Room("Layland Shores", "You are on a beach with waves crashing into the sand. Looking around you are encompassed by rocks on either side of you, funneling up to a big hill.");
        rooms[0].addItem(items[0]);
        rooms[0].addItem(items[1]);
        rooms[0].addExit(rooms[1]);
        rooms[1].addExit(rooms[0]);

        player = new Player(rooms[0], 10);
    }

    public void startGame() {
        initGame();

        Room currentLocation = player.getCurrentRoom();
        Scanner input = new Scanner(System.in);
        String cmd;

        System.out.println("Welcome to the Realms of Rozkrea: A Text Adventure");
        System.out.println("Please use the 'help' command for the list of commands and their uses.");
        System.out.println("\n");
        System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
        System.out.println("Exits: ");
        currentLocation.displayExits();

        do {
            currentLocation = player.getCurrentRoom();
            cmd = input.next().toUpperCase();

            switch (cmd) {
                case "GO" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot leave the area, you are in combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    String roomName;
                    roomName = input.nextLine().toUpperCase().substring(1);
                    int counter = 0;
                    if (roomName.equals(currentLocation.getName().toUpperCase())) {
                        System.out.println("You are already at " + roomName);
                        break;
                    }
                    for (Room exit : currentLocation.getExits()) {
                        if (Objects.equals(exit.getName().toUpperCase(), roomName)) {
                            currentLocation = exit;
                            player.setCurrentRoom(exit);
                            System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                            System.out.println("Exits: ");
                            currentLocation.displayExits();
                            counter++;
                        }
                    }
                    if (counter == 0) {
                        System.out.println("Could not find area '" + roomName + "'. Please choose from one of the possible exits.");
                        System.out.println("Exits: ");
                        currentLocation.displayExits();
                    }
                }
                case "LOOK" -> {
                    System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                    System.out.println("Exits: ");
                    currentLocation.displayExits();
                }
                case "INVENTORY" -> player.printInventory();
                case "TAKE" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot loot the area, you are in combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    if (currentLocation.getContents().isEmpty()) {
                        System.out.println("There is nothing in the area to take.");
                        input = new Scanner(System.in);
                    }
                    else {
                        Item roomItem = currentLocation.takeRoomItem(Integer.parseInt(input.next()));
                        player.addItem(roomItem);
                    }
                }
                case "DROP" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot drop items while you are in combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    if (player.getInventory().isEmpty()) {
                        System.out.println("There is nothing in your inventory to drop.");
                        input = new Scanner(System.in);
                    }
                    else {
                        Item droppedItem = player.dropInventoryItem(Integer.parseInt(input.next()));
                        currentLocation.addItem(droppedItem);
                        System.out.println("You dropped " + droppedItem.getName());
                    }
                }
                case "EQUIP" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot equip items in the middle of combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    Item equippingItem = player.getInventory().get(Integer.parseInt(input.next()) - 1);
                    if (equippingItem != null && equippingItem.getType() != Item.ItemType.CONSUMABLE) {
                        player.equipItem(equippingItem);
                        System.out.println("You equipped " + equippingItem.getName() + ".");
                    }
                    else {
                        System.out.println("Could not find item in inventory.");
                    }
                }
                case "UNEQUIP" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot unequip items in the middle of combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    Item unequippingItem = player.getInventory().get(Integer.parseInt(input.next()) - 1);
                    if (unequippingItem != null && unequippingItem.getType() != Item.ItemType.CONSUMABLE) {
                        player.unequipItem(unequippingItem);
                        System.out.println("You unequipped " + unequippingItem.getName() + ".");
                    }
                    else {
                        System.out.println("Could not find item in inventory.");
                    }
                }
                case "USE" -> {
                    int itemNum = Integer.parseInt(input.next()) - 1;
                    Item itemToUse = player.getInventory().get(itemNum);
                    if (itemToUse == null) System.out.println("Could not find item in inventory.");
                    else if (itemToUse.getType() == Item.ItemType.POTION) {
                        player.useItem(itemToUse);
                        player.removeInventoryItem(itemNum);
                    }
                    else if (itemToUse.getType() == Item.ItemType.CONSUMABLE && !enemyCheck(currentLocation)) {
                        player.useItem(itemToUse);
                        player.removeInventoryItem(itemNum);
                    }
                    else {
                        System.out.println("Cannot use " + itemToUse.getName() + " in combat.");
                    }


                }
                case "ATTACK" -> {
                    int characterNum = Integer.parseInt(input.next());
                    Character targetEnemy = currentLocation.getCharacter(characterNum);
                    if (targetEnemy.getType().equals(Character.CharType.ENEMY)) {
                        player.fightEnemy(targetEnemy);
                        targetEnemy.fightPlayer(player);
                        if (targetEnemy.getDeathStatus()) {
                            currentLocation.killCharacter(characterNum);
                            System.out.println("You killed " + targetEnemy + ".");
                        }
                    }
                    else System.out.println("You cannot attack allies!");
                }
                case "STATS" -> {
                    System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                    Item armor = player.getEquippedArmor();
                    Item rightHand = player.getRightHand();
                    if (armor != null) System.out.println("Armor: " + armor.getName() + " | AC: " + armor.getModifier());
                    else System.out.println("Armor: None");
                    if (rightHand != null) System.out.println("Right Hand: " + rightHand.getName() + " | 1d" + rightHand.getDiceMax() + " + " + rightHand.getModifier());
                    else System.out.println("Right Hand: Empty");
                }
                case "HELP" -> showMenu();
                case "EXIT" -> System.exit(0);
                default -> System.out.println("Please choose a valid command");
            }
        } while(true);
    }

    public void showMenu() {
        System.out.println("Available commands:");
        System.out.println("Go <location>");
        System.out.println("Look");
        System.out.println("Attack <enemy #>");
        System.out.println("Inventory <item #>");
        System.out.println("Stats");
        System.out.println("Take <item #>");
        System.out.println("Drop <item #>");
        System.out.println("Use <item #>");
        System.out.println("Equip <item #>");
        System.out.println("Unequip <item #>");
        System.out.println("Exit");
        System.out.println();
    }

    private boolean enemyCheck(Room currentLocation) {
        for(Character entity : currentLocation.getCharacters()) {
            if (entity.getType() == Character.CharType.ENEMY) {
                return true;
            }
        }
        return false;
    }
}
