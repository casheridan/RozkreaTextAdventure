import java.util.Objects;

public class TalkQuest extends Quest {
    private final String questName;
    private final String questDesc;
    private final int questId;
    private final String relAllyName;
    private boolean completed;
    private final int reward;
    private final boolean isTalkQuest, isDefendQuest;

    public TalkQuest(String name, String desc, String allyName, int reward, int id) {
        this.questName = name;
        this.questDesc = desc;
        this.questId = id;
        this.relAllyName = allyName;
        this.reward = reward;
        completed = false;
        this.isTalkQuest = true;
        this.isDefendQuest = false;
    }

    @Override
    public void satisfyRequirement(Object obj) {
        // Implementation or call the specific method
        if (obj instanceof Character) {
            satisfyRequirement((Character) obj);
        }
    }

    public void satisfyRequirement(Character ally) {
        if (Objects.equals(ally.getName(), relAllyName)) completeObjective();
    }

    public void completeObjective() {
        completed = true;
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
        return "Talk to: " + relAllyName;
    }

    @Override
    public int getQuestId() {
        return questId;
    }

    @Override
    public boolean getCompleted() {
        return completed;
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
