import java.util.ArrayList;

public class Room {
    private final String name;
    private final String desc;
    private final ArrayList<Room> exits;
    private final ArrayList<Item> contents;
    private final ArrayList<Character> characters;
    private int roomRequirement;
    private Quest triggerQuest;

    public Room(String name, String desc, int req) {
        this.name = name;
        this.desc = desc;
        this.roomRequirement = req;
        this.exits = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.characters = new ArrayList<>();
    }

    public Room(String name, String desc, int req, Quest quest) {
        this.name = name;
        this.desc = desc;
        this.roomRequirement = req;
        this.exits = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.triggerQuest = quest;
    }

    public Quest getTriggerQuest() { return triggerQuest; }

    public void addExit(Room room) {
        exits.add(room);
    }

    public void addItem(Item item) {
        contents.add(item);
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public String getName() {
        return name;
    }

    public int getRoomRequirement() { return this.roomRequirement; }

    public String getDescription() {
        StringBuilder tempDesc = new StringBuilder(desc);

        if(characters.isEmpty()) {
            tempDesc.append("\n\nThere is no one here.\n");
        }
        else {
            tempDesc.append("\n\nThere are ").append(characters.size()).append(" entities here.\n");
            for (int i = 0; i < characters.size(); i++) {
                tempDesc.append(i + 1).append(": ").append(characters.get(i).getName()).append("\n");
            }
        }

        if(contents.isEmpty()) {
            tempDesc.append("\nThere are no items here.\n");
        }
        else {
            tempDesc.append("\nThere are ").append(contents.size()).append(" items here.\n");
            for (int i = 0; i < contents.size(); i++) {
                tempDesc.append(i + 1).append(": ").append(contents.get(i).getName()).append("\n");
            }
        }

        return tempDesc.toString();
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public Character getCharacter(int charNum) {
        return characters.get(charNum - 1);
    }

    public ArrayList<Item> getContents() { return contents; }

    public ArrayList<Room> getExits() {
        return exits;
    }

    public Item takeRoomItem(int itemNum) {
        Item itemToTake = contents.get(itemNum - 1);
        contents.remove(itemNum - 1);
        return itemToTake;
    }

    public void takeAllRoomItems() {
        contents.removeAll(contents);
    }

    public void displayExits() {
        for(Room exit : exits) {
            System.out.println(exit.getName());
        }
    }

    public void killCharacter(int characterNum) {
        characters.remove(characterNum - 1);
    }
}
