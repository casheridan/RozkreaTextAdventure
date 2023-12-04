import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Character {
    private final String name;
    private int health;
    private final int armorClass;
    private final Item weapon;
    private final CharType type;
    private boolean isDead;
    private Map<Integer, Dialogue> dialogs;
    private ArrayList<Quest> quests;
    private int condition;

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
        this.condition = 0;
    }

    public Character(String name, int health, int armorClass, Item weapon, CharType type, JSONObject jsonData) {
        this.name = name;
        this.health = health;
        this.isDead = false;
        this.armorClass = armorClass;
        this.weapon = weapon;
        this.type = type;
        this.dialogs = new HashMap<>();
        populateDialog(jsonData);
        this.condition = 0;
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

    public int getCondition() {
        return condition;
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

    public void advanceDialog(int val) {
        condition = val;
    }

    public void populateDialog(JSONObject jsonData) {
        JSONObject characterDialogues = (JSONObject) jsonData.get(this.name);
        characterDialogues.forEach((key, value) -> {
            JSONObject dialogueObj = (JSONObject) value;
            String text = (String) dialogueObj.get("text");
            Integer reward = dialogueObj.get("reward") != null ? Integer.parseInt((String) dialogueObj.get("reward")) : null;
            Integer questId = dialogueObj.get("quest") != null ? Integer.parseInt((String) dialogueObj.get("quest")) : null;
            Integer jumpPoint = dialogueObj.get("jump") != null ? Integer.parseInt((String) dialogueObj.get("jump")) : null;

            this.dialogs.put(Integer.parseInt((String) key), new Dialogue(text, reward, questId, jumpPoint));
        });
    }

    public Dialogue getDialogueBasedOnCondition(int condition) {
        return this.dialogs.getOrDefault(condition, null);
    }
}
