import java.util.Objects;

public class KillQuest extends Quest {
    private final String questName;
    private final String questDesc;
    private final int questId;
    private final int reward;
    private final String relEnemyName;
    private int currentCount;
    private final int maxAmount;
    private final boolean isTalkQuest, isDefendQuest;

    public KillQuest(String name, String desc, int maxAmount, String enemyName, int reward, int id, boolean isDefend) {
        this.questName = name;
        this.questDesc = desc;
        this.maxAmount = maxAmount;
        this.currentCount = 0;
        this.questId = id;
        this.relEnemyName = enemyName;
        this.reward = reward;
        this.isTalkQuest = false;
        this.isDefendQuest = isDefend;
    }

    public void increaseCount() {
        currentCount++;
        if (currentCount > maxAmount) currentCount = maxAmount;
    }

    @Override
    public void satisfyRequirement(Object obj) {
        // Implementation or call the specific method
        if (obj instanceof Character) {
            satisfyRequirement((Character) obj);
        }
    }

    public void satisfyRequirement(Character enemy) {
        if (Objects.equals(enemy.getName(), relEnemyName)) increaseCount();
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
