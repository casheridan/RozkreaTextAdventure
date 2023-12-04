public class KillQuest extends Quest {
    private final String questName;
    private final String questDesc;
    private final int questId;
    private int currentCount;
    private final int maxAmount;

    public KillQuest(String name, String desc, int maxAmount, int id) {
        this.questName = name;
        this.questDesc = desc;
        this.maxAmount = maxAmount;
        this.currentCount = 0;
        this.questId = id;
    }

    public void increaseCount() {
        currentCount++;
        if (currentCount > maxAmount) currentCount = maxAmount;
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
        return "Killed: " + currentCount + "/" + maxAmount;
    }

    @Override
    public int getQuestId() {
        return questId;
    }
}
