package dev.fullslack.tictactoe;

public class Field {
    private int number;
    private Player player;

    public Field(int number) {
        this.number = number;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumber() {
        return number;
    }

    public boolean isSet() {
        return (player.getSymbol() != 'E');
    }
}
