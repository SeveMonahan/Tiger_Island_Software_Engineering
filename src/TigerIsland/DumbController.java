package TigerIsland;

import TigerIsland.*;

import java.util.ArrayList;

public class DumbController implements PlayerController {
    Color color;

    public DumbController(Color color){
        this.color = color;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile){
        ArrayList<GameStateBeforeBuildAction> beforeBuildActions = gameStateWTile.getChildren();

        for(GameStateBeforeBuildAction midpoint : beforeBuildActions){
            ArrayList<GameStateEndOfTurn> endpoints = midpoint.getChildren();
            if(endpoints.size() != 0){
                return endpoints.get(0);
            }
        }

        return null;
    }
}
