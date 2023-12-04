public class FetchQuest extends Quest {
    private final String questName;
    private final String questDesc;
    private final int questId;
    private int currentCount;
    private final int maxAmount;

    public FetchQuest(String name, String desc, int maxAmount, int id) {
        this.questName = name;
        this.questDesc = desc;
        this.maxAmount = maxAmount;
        this.currentCount = 0;
        this.questId = id;
    }

    @Override
    public String getQuestName() {
        return questName;
    }

    @Override
    public String getQuestDescription() {
        return questDesc;
    }

    @Override
    public String getRequirements() {
        return "Collected: " + currentCount + "/" + maxAmount;
    }

    @Override
    public int getQuestId() {
        return questId;
    }
}
