public class FetchQuest extends Quest {
    private final String questName;
    private final String questDesc;
    private final int questId;
    private final int reward;
    private int currentCount;
    private final int maxAmount;
    private final boolean isTalkQuest, isDefendQuest;

    public FetchQuest(String name, String desc, int maxAmount, int reward, int id) {
        this.questName = name;
        this.questDesc = desc;
        this.maxAmount = maxAmount;
        this.currentCount = 0;
        this.questId = id;
        this.reward = reward;
        this.isTalkQuest = false;
        this.isDefendQuest = false;
    }

    @Override
    public void satisfyRequirement(Object obj) {
        if (obj instanceof Item) {
            satisfyRequirement((Item) obj);
        }
    }

    public void satisfyRequirement(Item item) {
        // implementation
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

    @Override
    public boolean getCompleted() {
        return currentCount == maxAmount;
    }

    @Override
    public int getReward() {
        return reward;
    }

    @Override
    public boolean getIsTalkQuest() { return isTalkQuest; }

    @Override
    public boolean getIsDefendQuest() { return isDefendQuest; }
}
