import java.util.ArrayList;

public class QuestManager {
    private ArrayList<Quest> quests;
    private ArrayList<Quest> acceptedQuests;

    public void initQuests() {
        quests = new ArrayList<>();
        acceptedQuests = new ArrayList<>();

        quests.add(new FetchQuest("Collect 3 shards", "Colton wants you to find and Collect the 3 shards", 3, 1));
        quests.add(new KillQuest("Kill 3 Slimes", "Colton now needs you to kill 3 of those slimes you have been finding.", 3, 2));
    }

    public ArrayList<Quest> getAcceptedQuests() {
        return acceptedQuests;
    }

    public void acceptQuest(Quest quest) {
        acceptedQuests.add(quest);
        quests.remove(quest);
    }

    public Quest getQuest(int id) {
        return quests.get(id - 1);
    }
}
