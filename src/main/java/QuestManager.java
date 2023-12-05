import java.util.ArrayList;

public class QuestManager {
    private ArrayList<Quest> quests;
    private ArrayList<Quest> acceptedQuests;

    public void initQuests() {
        quests = new ArrayList<>();
        acceptedQuests = new ArrayList<>();

        quests.add(new TalkQuest("Find Someone", "I've woken up on this beach knowing nothing, I've got to find someone to find out where I am.", "Stranger", 10, 1));
        quests.add(new KillQuest("Kill 3 Slimes", "Colton now needs you to kill 3 of those slimes you have been finding.", 3, "Dark Slime", 30, 2, false));
        quests.add(new KillQuest("Defend the Merchant", "Walking to Thalud I found 2 men harassing a man, I better save him.", 2, "Bandit", 10, 3, true));
        quests.add(new TalkQuest("Talk to the Merchant in Thalud", "Saving the Merchant he asked me to head into the town so I can buy some gear.", "Merchant Man", 20, 4));
        quests.add(new KillQuest("Take out Rebels", "The Jarl of Thalud seems to be having issues with some rebels in his town.", 5, "Rebel", 50, 5, false));
//        quests.add(new TalkQuest("Find the Jarl", "I've finally gotten into Thalud, maybe I should find the Jarl", "Jarl Yassef", 10, 6));
    }

    public ArrayList<Quest> getAcceptedQuests() {
        return acceptedQuests;
    }

    public void acceptQuest(Quest quest) {
        acceptedQuests.add(quest);
        quests.remove(quest);
    }

    public Quest getQuest(int id) {
        for (Quest quest : quests) {
            if (quest.getQuestId() == id) return quest;
        }
        return null;
    }

    public Quest getAcceptedQuest(int id) {
        for (Quest quest : acceptedQuests) {
            if (quest.getQuestId() == id) return quest;
        }
        return null;
    }

    public void completeQuest(int id) {
        acceptedQuests.removeIf(quest -> quest.getQuestId() == id);
    }
}
