package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.jupiter.api.Test;

public class PostManTest {

    @Test
    public void foo() {
        NetworkClientMock mock = new NetworkClientMock(new String[]{
                "NEW CHALLENGE 346 YOU WILL PLAY 2 MATCHES",
                "BEGIN ROUND 1 OF 2",
                "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 34",
                "MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE",
                "GAME B MOVE 0 PLAYER 6 PLACED GRASS+LAKE AT 3 -1 -2 4 FOUNDED SETTLEMENT AT 1 0 -1",
                "GAME A MOVE 0 PLAYER 3 PLACED GRASS+LAKE AT 1 1 -2 6 FOUNDED SETTLEMENT AT 1 2 -3",
                "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 0 PLACE ROCK+JUNGLE",
                "GAME A MOVE 0 PLAYER 6 PLACED ROCK+JUNGLE AT 3 -1 -2 4 FOUNDED SETTLEMENT AT 1 0 -1",
                "GAME B MOVE 0 PLAYER 3 PLACED GRASS+LAKE AT 1 1 -2 6 FOUNDED SETTLEMENT AT 1 2 -3",
                "MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS: MOVE 0 PLACE ROCK+JUNGLE",
                "GAME A MOVE 0 PLAYER 6 PLACED ROCK+JUNGLE AT 3 -1 -2 4 FOUNDED SETTLEMENT AT 1 0 -1",
                "GAME B MOVE 0 PLAYER 3 PLACED ROCK+JUNGLE AT -1 -1 2 3 FOUNDED SETTLEMENT AT -1 -2 3",
                "MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 1 PLACE LAKE+ROCK"
        }
        );

        PostMan postMan = PostMan.grabPostMan(mock);

        postMan.setpid(6);

        postMan.main_loop();

        for(String foo : mock.get_array()){
            System.out.println(foo);
        }
    }
}
