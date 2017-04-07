package TigerIsland;

public class Referee {
    PlayerController controller_1;
    PlayerController controller_2;
    OutputPlayerActions output;
    TileBag tileBag;

    public Referee(PlayerController controller_1, PlayerController controller_2, OutputPlayerActions output, TileBag tileBag) {
        this.controller_1 = controller_1;
        this.controller_2 = controller_2;
        this.output = output;
        this.tileBag = tileBag;
    }
}
