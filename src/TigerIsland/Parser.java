package TigerIsland;

public class Parser {

    private int challenges;
    private int cid;
    private int gid;
    private int orientation;
    private String pid;
    private int rid;
    private int rounds;
    private int score;
    private String terrain;
    private String tileDescriptor;
    private double movePlanningTime;

    private String tournamentPassword;
    private String username;
    private String password;

    public Parser(String tournamentPassword, String username, String password) {
        this.tournamentPassword = tournamentPassword;
        this.username = username;
        this.password = password;
    }
    public void setChallenges(int challenges) {
        this.challenges = challenges;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setTileDescriptor(String tileDescriptor) {
        this.tileDescriptor = tileDescriptor;
    }

    public void setMovePlanningTime(double movePlanningTime) {
        this.movePlanningTime = movePlanningTime;
    }

    public void readMessage(String message) {
        if(message == "WELCOME TO ANOTHER EDITION OF THUNDERDOME!")
            enterTournamentPassword();
        else if(message == "TWO SHALL ENTER, ONE SHALL LEAVE")
            enterUsernameAndPassword();
        else if(message.contains("WAIT FOR THE TOURNAMENT TO BEGIN"))
            waitForTournamentToBegin();
    }
    private void enterTournamentPassword() {
        //Output Tournament Password
        System.out.println(tournamentPassword);
    }
    private void enterUsernameAndPassword() {
        //Output Username and Password
        System.out.print(username);
        System.out.println(password);
    }
    private void waitForTournamentToBegin() {
        //Wait for the tournament to begin
    }
}

