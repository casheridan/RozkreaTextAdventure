import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TextAdventure {
    public static void main(String[] args) throws IOException, ParseException {
        AdventureModel adv = new AdventureModel();
        adv.startGame();
    }
}
