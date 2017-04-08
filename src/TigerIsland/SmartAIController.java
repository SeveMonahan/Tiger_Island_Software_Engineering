package TigerIsland;

import java.util.ArrayList;

public class SmartAIController implements PlayerController {
    Color color;

    public SmartAIController(Color color){
        this.color = color;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile) {
        ArrayList<GameStateBeforeBuildAction> beforeBuildActions = gameStateWTile.getChildren();

        ArrayList<GameStateEndOfTurn> final_children = new ArrayList<GameStateEndOfTurn>();

        for (GameStateBeforeBuildAction gameState : beforeBuildActions) {
            ArrayList<GameStateEndOfTurn> leaf_list = gameState.getChildren();
            for (GameStateEndOfTurn new_final_child : leaf_list) {
                final_children.add(new_final_child);
            }

        }

        GameStateEndOfTurn result = final_children.get(0);

        int bestScoreSoFar = 0;

        for (GameStateEndOfTurn current_child : final_children) {

            if (current_child.activePlayerScore() > bestScoreSoFar) {
                result = current_child;
                bestScoreSoFar = current_child.activePlayerScore();
            }
        }

        return result;
    }
}
