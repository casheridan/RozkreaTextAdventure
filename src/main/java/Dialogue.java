public class Dialogue {
    private final String text;
    private final Integer reward;
    private final int relQuestId;
    private final int dialogJumpPoint;

    public Dialogue(String text, Integer reward, int questId, int jumpPoint) {
        this.text = text;
        this.reward = reward;
        this.relQuestId = questId;
        this.dialogJumpPoint = jumpPoint;
    }

    public String getText() {
        return text;
    }

    public Integer getReward() {
        return reward;
    }

    public int getRelQuestId() {
        return relQuestId;
    }

    public int getDialogJumpPoint() {
        return dialogJumpPoint;
    }
}
