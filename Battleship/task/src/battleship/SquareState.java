package battleship;

public enum SquareState {

    HIT('X'),
    MISS('M'),
    SHIP('O'),
    OCEAN('~'),
    FOG('~');

    public final Character c;

    SquareState(char c) {
        this.c = c;
    }
}
