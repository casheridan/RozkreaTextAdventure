import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AdventureModel {
    private Player player;
    private QuestManager questManager;

    public AdventureModel() {}

    public void initGame() throws IOException, ParseException {
        Room[] rooms = new Room[12];
        Item[] items = new Item[10];
        Character[] characters = new Character[8];
        questManager = new QuestManager();
        questManager.initQuests();

        JSONParser parser = new JSONParser();
        Reader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/dialog.json")));
        JSONObject jsonObj = (JSONObject) parser.parse(reader);

        items[0] = new Item("Short Sword", Item.ItemType.WEAPON, 6, 0, 5);
        items[1] = new Item("Padded Armor", Item.ItemType.ARMOR, 11, 5);
        items[2] = new Item("Health Potion", Item.ItemType.POTION, 10);
        items[3] = new Item("Gold Pouch", Item.ItemType.CONSUMABLE, 1);
        items[4] = new Item("Long Sword", Item.ItemType.WEAPON, 10, 2, 15);
        items[5] = new Item("Chainmail", Item.ItemType.ARMOR, 13, 15);
        items[6] = new Item("Map of Hitpoints", Item.ItemType.MISC, 5);

        characters[0] = new Character("Dark Slime", 3, 5, null, Character.CharType.ENEMY, 4, 1);
        characters[1] = new Character("Stranger", 5, 9, items[0], Character.CharType.ALLY, jsonObj);
        characters[2] = new Character("Merchant Man", 5, 9, items[0], Character.CharType.ALLY, jsonObj);
        characters[3] = new Character("Bandit", 1, 1, items[0], Character.CharType.ENEMY, 7, 2);
        characters[5] = new Character("Jarl Yassef", 1, 1, null, Character.CharType.ALLY, jsonObj);
        characters[6] = new Character("Advisor Jax", 1, 1, null, Character.CharType.ALLY, jsonObj);
        characters[7] = new Character("Rebel", 5, 9, items[0], Character.CharType.ENEMY, 10, 5);
        ArrayList shopItems = new ArrayList();
        shopItems.add(items[2]);
        shopItems.add(items[4]);
        shopItems.add(items[5]);
        characters[4] = new Character("Juno", 1, 1, null, Character.CharType.MERCHANT, shopItems);

        // Progression order is backwards to have link backs.

        rooms[11] = new Room("Rebel Base", "You walk up to the door and kick it in. You find 3 rebels sitting at a table with a map on it. They climb to their feet.", 4);
        rooms[11].addCharacter(characters[7]);
        rooms[11].addCharacter(characters[7]);
        rooms[11].addCharacter(characters[7]);

        rooms[10] = new Room("Back Alley", "Walking in between buildings you find some rebels stading around a door. \nThey shout at you to scram or face the consequences.", 4);
        rooms[10].addCharacter(characters[7]);
        rooms[10].addCharacter(characters[7]);
        rooms[10].addExit(rooms[11]);

        rooms[9] = new Room("Thalud Keep", "You reach the end of the road and find a keep. You open the door and walk into a giant room. \n A jarl sits on a throne in the center of the room with his advisor to his right.", 3);
        rooms[9].addCharacter(characters[5]);
        rooms[9].addCharacter(characters[6]);

        rooms[8] = new Room("Merchant Shop", "You see a sign above an ornate building with the name Junos Shop. \nEntering the shop you see armor and weapons lined on the walls and a counter with potions lining it. \nAt this counter you see the Merchant you saved on the road.", 3);
        rooms[8].addCharacter(characters[4]);

        rooms[7] = new Room("Thalud Gate", "You walk up the road and each an open gate to Thalud. \nWalking down the street you see buildings and shops line the street with people busying around the street.", 3);
        rooms[7].addExit(rooms[8]);
        rooms[7].addExit(rooms[9]);
        rooms[7].addExit(rooms[10]);
        rooms[8].addExit(rooms[7]);
        rooms[9].addExit(rooms[7]);
        rooms[10].addExit(rooms[7]);

        rooms[6] = new Room("Road to Thalud", "Heading down the road you find a pair of bandits harassing a man ", 2, questManager.getQuest(3));
        rooms[6].addCharacter(characters[3]);
        rooms[6].addCharacter(characters[3]);
        rooms[6].addCharacter(characters[2]);
        rooms[6].addExit(rooms[7]);
        rooms[7].addExit(rooms[6]);

        rooms[5] = new Room("To Thalud", "You walk into the cave, you see torches lining the path. You walk for about an hour until you find a light at the end of the tunnel. \nReaching it you are blinded by a the light of the sun. Your eyes adjust to the sun and you see a town \nin the distance with a road leading to it. Looking around you find some gold.", 2);
        rooms[5].addItem(items[3]);
        rooms[5].addExit(rooms[6]);
        rooms[6].addExit(rooms[5]);

        rooms[4] = new Room("Cave Entrance", "There is a large entrance to a cave here, you see a sign pointing inwards saying 'Thalud'. You see a dark slime hopping your way around the entrance of the hole.", 1);
        rooms[4].addCharacter(characters[0]);
        rooms[4].addExit(rooms[5]);
        rooms[5].addExit(rooms[4]);

        rooms[3] = new Room("Deep Forest", "As you walk deeper into the forest the trees get denser and denser and the light gets darker and darker. You come up apon some slimes blocking your path.", 1);
        rooms[3].addCharacter(characters[0]);
        rooms[3].addCharacter(characters[0]);
        rooms[3].addItem(items[2]);

        rooms[2] = new Room("Forest", "Trees now surround you and the light of the sun gets a bit darker, covered by the overhang of the leaves above. You see a man leaning up against a tree", 0);
        rooms[2].addCharacter(characters[1]);
        rooms[2].addExit(rooms[3]);
        rooms[2].addExit(rooms[4]);
        rooms[3].addExit(rooms[2]);
        rooms[4].addExit(rooms[2]);

        rooms[1] = new Room("Hill", "You pass along the shore. Eventually you reach a hill with some grass. You decide to climb it and reach the top. \nYou are greeted by a gross dark slime in front of you.", 0);
        rooms[1].addItem(items[2]);
        rooms[1].addCharacter(characters[0]);
        rooms[1].addExit(rooms[2]);
        rooms[2].addExit(rooms[1]);


        rooms[0] = new Room("Layland Shores", "You are on a beach with waves crashing into the sand. \nLooking around you are encompassed by rocks on either side of you, funneling up to a big hill. \nThere are some things lying on the beach around you.", 0);
        rooms[0].addItem(items[0]);
        rooms[0].addItem(items[1]);
        rooms[0].addItem(items[3]);
        rooms[0].addExit(rooms[1]);
        rooms[1].addExit(rooms[0]);

        player = new Player(rooms[0], 10);
    }

    public void startGame() throws IOException, ParseException {
        initGame();

        Room currentLocation = player.getCurrentRoom();
        Scanner input = new Scanner(System.in);
        String cmd;

        System.out.println("Welcome to the Realms of Rozkrea: A Text Adventure");
        System.out.println("Please use the 'help' command for the list of commands and their uses.\n");

        //Beginning Stuff
        System.out.println("You awake on a beach with no idea who you are, \nall you remember is getting tossed off a ship in the middle of a heavy storm. \nYou look up to see the storm off in the distance.\n");
        questManager.acceptQuest(questManager.getQuest(1));
        System.out.println("Started Quest 'Find Someone', check your journal.\n");
        System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
        System.out.println("Exits: ");
        currentLocation.displayExits();

        do {
            currentLocation = player.getCurrentRoom();
            cmd = input.next().toUpperCase();

            switch (cmd) {
                case "GO", "TRAVEL" -> {
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
                        if (Objects.equals(exit.getName().toUpperCase(), roomName) && player.getCurrentLevel() >= exit.getRoomRequirement()) {
                            currentLocation = exit;
                            if (currentLocation.getTriggerQuest() != null) {
                                questManager.acceptQuest(questManager.getQuest(4));
                                System.out.println("Started new quest, check journal for more information.");
                            }
                            player.setCurrentRoom(exit);
                            System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                            System.out.println("Exits: ");
                            currentLocation.displayExits();
                            counter++;
                        }
                        else if (Objects.equals(exit.getName().toUpperCase(), roomName) && player.getCurrentLevel() < exit.getRoomRequirement()) {
                            System.out.println("You need to be level " + exit.getRoomRequirement() + " in order to enter this area.");
                            counter++;
                        }
                    }
                    if (counter == 0) {
                        System.out.println("Could not find area '" + roomName + "'. Please choose from one of the possible exits.");
                        System.out.println("Exits: ");
                        currentLocation.displayExits();
                    }
                }
                case "LOOK", "L" -> {
                    System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                    System.out.println("Exits: ");
                    currentLocation.displayExits();
                }
                case "INVENTORY", "I" -> player.printInventory();
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
                        String takeIn = input.next();
                        try {
                            if (takeIn.equalsIgnoreCase("ALL")) {
                                for(Item item : currentLocation.getContents()) {
                                    player.addItem(item);
                                }
                                currentLocation.takeAllRoomItems();
                            }
                            else {
                                Item roomItem = currentLocation.takeRoomItem(Integer.parseInt(takeIn));
                                player.addItem(roomItem);
                            }
                        }
                        catch (IndexOutOfBoundsException e) {
                            System.out.println("Cannot find item in world.");
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Please use a number.");
                        }
                    }
                }
                case "DROP", "D" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot drop items while you are in combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    try {
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
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Item not found.");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Please use a number.");
                    }
                }
                case "EQUIP", "E" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot equip items in the middle of combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    try {
                        Item equippingItem = player.getInventory().get(Integer.parseInt(input.next()) - 1);
                        if (equippingItem != null && equippingItem.getType() != Item.ItemType.CONSUMABLE) {
                            player.equipItem(equippingItem);
                            System.out.println("You equipped " + equippingItem.getName() + ".");
                        }
                        else {
                            System.out.println("Could not find item in inventory.");
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Item not found.");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Please use a number.");
                    }
                }
                case "UNEQUIP", "UN" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot unequip items in the middle of combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    try {
                        Item unequippingItem = player.getInventory().get(Integer.parseInt(input.next()) - 1);
                        if (unequippingItem != null && unequippingItem.getType() != Item.ItemType.CONSUMABLE) {
                            player.unequipItem(unequippingItem);
                            System.out.println("You unequipped " + unequippingItem.getName() + ".");
                        }
                        else {
                            System.out.println("Could not find item in inventory.");
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Item not found.");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Please use a number.");
                    }
                }
                case "USE", "U" -> {
                    try {
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
                            System.out.println("Cannot use " + itemToUse.getName());
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Item not found.");
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Please use a number.");
                    }
                }
                case "FIGHT", "F" -> {
                    try {
                        int characterNum = Integer.parseInt(input.next());
                        if (currentLocation.getCharacters().isEmpty()) {
                            System.out.println("There is no one to Attack.");
                        } else {
                            try {
                                Character targetEnemy = currentLocation.getCharacter(characterNum);
                                if (targetEnemy.getType().equals(Character.CharType.ENEMY)) {
                                    player.fightEnemy(targetEnemy);
                                    for (Character entity : currentLocation.getCharacters()) {
                                        if (entity.getType() == Character.CharType.ENEMY && entity.getHealth() >= 0) {
                                            entity.fightPlayer(player);
                                        }
                                    }
                                    if (targetEnemy.getDeathStatus()) {
                                        currentLocation.killCharacter(characterNum);
                                        int enemyGold = targetEnemy.getGoldReward();
                                        player.addGold(enemyGold);
                                        System.out.println("You killed " + targetEnemy.getName() + " and got " + enemyGold + "gp.");
                                        for (Quest quest : questManager.getAcceptedQuests()) {
                                            quest.satisfyRequirement(targetEnemy);
                                        }
                                    }
                                } else System.out.println("You cannot attack allies!");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Can't find entity.");
                            }
                        }
                    }
                    catch(NumberFormatException e) {
                            System.out.println("Please give a number");
                    }
                }
                case "STATS", "S" -> {
                    System.out.println("Level: " + player.getCurrentLevel());
                    System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                    System.out.println("Gold: " + player.getCurrentGold() + "gp");
                    Item armor = player.getEquippedArmor();
                    Item rightHand = player.getRightHand();
                    if (armor != null) System.out.println("Armor: " + armor.getName() + " | AC: " + armor.getModifier());
                    else System.out.println("Armor: None");
                    if (rightHand != null) System.out.println("Right Hand: " + rightHand.getName() + " | 1d" + rightHand.getDiceMax() + " + " + rightHand.getModifier());
                    else System.out.println("Right Hand: Empty");
                }
                case "JOURNAL", "J" -> {
                    System.out.println("Journal: \n");
                    int counter = 1;
                    if (questManager.getAcceptedQuests().isEmpty()) {
                        System.out.println("You have not accepted any quests.");
                    }
                    else {
                        for (Quest quest : questManager.getAcceptedQuests()) {
                            System.out.println(counter + ": " + quest.getQuestName() + " | Description: " + quest.getQuestDescription());
                            System.out.println(quest.getRequirements());
                        }
                    }
                }
                case "TALK", "T" -> {
                    if (enemyCheck(currentLocation)) {
                        System.out.println("You cannot talk to allies in the middle of combat!");
                        input = new Scanner(System.in);
                        break;
                    }

                    try {
                        Character ally = currentLocation.getCharacter(Integer.parseInt(input.next()));

                        if (!questManager.getAcceptedQuests().isEmpty()) {
                            Quest q = null;
                            for (Quest quest : questManager.getAcceptedQuests()) {
                                if (quest.getIsTalkQuest()) {
                                    quest.satisfyRequirement(ally);
                                    q = quest;
                                }
                                if (quest.getIsDefendQuest()) {
                                    quest.satisfyRequirement(ally);
                                    q = quest;
                                }
                            }
                            if (q != null) {
                                player.addGold(q.getReward());
                                player.increaseLevel();
                                questManager.getAcceptedQuest(q.getQuestId());
                                questManager.completeQuest(q.getQuestId());
                                System.out.println(q.getQuestName() + " completed. " + q.getReward() + "gp added and gained a level.");
                            }
                        }

                        if (ally.getType() == Character.CharType.MERCHANT) {
                            System.out.println("Hello, what would you like to buy?");
                            ally.showShop();
                            System.out.print("Select number of item to buy: ");
                            try {
                                Item buyItem = ally.getShopItem(Integer.parseInt(input.next()));
                                player.addItem(buyItem);
                                player.removeGold(buyItem.getItemPrice());
                            }
                            catch (IndexOutOfBoundsException e) {
                                System.out.println("Can't find shop item.");
                            }
                            catch (NumberFormatException e) {
                                System.out.println("Please use a number.");
                            }
                        }
                        else {
                            Dialogue selectedDialogue = ally.getDialogueBasedOnCondition(ally.getCondition());
                            int relId = selectedDialogue.getRelQuestId();
                            Quest relQuest = null;
                            if (selectedDialogue.getRelQuestId() != 0) {
                                try {
                                    relQuest = questManager.getQuest(relId);
                                }
                                catch (IndexOutOfBoundsException e) {
                                    relQuest = questManager.getAcceptedQuest(relId);
                                }
                                if (relQuest == null) {
                                    relQuest = questManager.getAcceptedQuest(relId);
                                }
                            }
                            if (relQuest != null && ally.getActiveQuest() != relQuest) {
                                System.out.println(ally.getName() + ": " + selectedDialogue.getText() + " | Reward: " + relQuest.getReward() + "\n");
                                System.out.println("y/n");
                                do {
                                    String decision = input.next();
                                    if (decision.equalsIgnoreCase("Y")) {
                                        System.out.println(relQuest.getQuestName() + " added to journal.");
                                        questManager.acceptQuest(relQuest);
                                        ally.setActiveQuest(relQuest);
                                        ally.advanceDialog(selectedDialogue.getDialogJumpPoint());
                                        break;
                                    } else if (decision.equalsIgnoreCase("N")) {
                                        System.out.println("Quest denied");
                                        break;
                                    } else {
                                        System.out.println("Please select a valid option.");
                                    }
                                } while (true);
                            }
                            else if (relQuest != null && relQuest == ally.getActiveQuest()) {
                                if (relQuest.getCompleted()) {
                                    ally.advanceDialog(selectedDialogue.getDialogJumpPoint());
                                    selectedDialogue = ally.getDialogueBasedOnCondition(ally.getCondition());
                                    System.out.println(ally.getName() + ": " + selectedDialogue.getText());
                                    player.addGold(relQuest.getReward());
                                    player.increaseLevel();
                                    questManager.completeQuest(relQuest.getQuestId());
                                    System.out.println("You have completed " + relQuest.getQuestName() + " | You have recieved: " + relQuest.getReward() + "gp and have gone up a level.");
                                    ally.advanceDialog(selectedDialogue.getDialogJumpPoint());
                                }
                                else {
                                    System.out.println(ally.getName() + ": " + selectedDialogue.getText());
                                }
                            }
                            else {
                                System.out.println(ally.getName() + ": " + selectedDialogue.getText());
                            }
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Please select a number for the character");
                    }
                    catch (IndexOutOfBoundsException e) {
                        System.out.println("Entity not found.");
                    }
                }
                case "HELP" -> showMenu();
                case "EXIT" -> System.exit(0);
                default -> System.out.println("Please choose a valid command");
            }
        } while(true);
    }

    public void showMenu() {
        System.out.println("Available commands:");
        System.out.println("Go <full area name>");
        System.out.println("Look");
        System.out.println("Attack <enemy #>");
        System.out.println("Inventory <item #>");
        System.out.println("Stats");
        System.out.println("Journal");
        System.out.println("Talk");
        System.out.println("Take <item #> or All");
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
