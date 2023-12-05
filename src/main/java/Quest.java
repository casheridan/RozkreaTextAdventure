public abstract class Quest {
    public abstract String getQuestName();

    public abstract String getQuestDescription();

    public abstract int getReward();

    public abstract String getRequirements();

    public abstract void satisfyRequirement(Object obj);

    public abstract boolean getCompleted();

    public abstract int getQuestId();

    public abstract boolean getIsTalkQuest();

    public abstract boolean getIsDefendQuest();
}
