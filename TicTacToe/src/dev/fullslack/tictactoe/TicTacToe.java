package dev.fullslack.tictactoe;

public class TicTacToe extends Game {
    //private final static int BOARD_SIZE = 9;
    //private final static int ROW_SIZE = (int)Math.sqrt((double)BOARD_SIZE);

    public TicTacToe(Player player1, Player player2) {
        super(player1, player2);

        BOARD_SIZE = 9;
        ROW_SIZE = (int)Math.sqrt((double)BOARD_SIZE);

        fields = new Field[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            fields[i] = new Field(i + 1);
            fields[i].setPlayer(emptyPlayer);
        }
    }

    @Override
    protected void printBoard() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                int arrayPointer = i * ROW_SIZE + j;
                //is field set?
                if (fields[arrayPointer].isSet()) {
                    //yes: print player symbol
                    char symbol = fields[arrayPointer].getPlayer().getSymbol();
                    System.out.print(symbol + " ");
                } else {
                    //no: print field number
                    int number = fields[arrayPointer].getNumber();
                    System.out.print(number + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    protected boolean setField(int fieldNumber) {
        //check for valid input
        if (fieldNumber >= 1 && fieldNumber <= BOARD_SIZE) {
            //check if field is set
            if (!fields[fieldNumber - 1].isSet()) {
                fields[fieldNumber - 1].setPlayer(currentPlayer);
                return true;
            }
        }
        return false;
    }

    /*@Override
    protected boolean checkForWin() {
        boolean gameIsWon = false;
        if (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()) {
            gameIsWon = true;
            currentPlayer.addScore();
        }
        return gameIsWon;
    }*/

    @Override
    protected boolean gameHasEnded() {
        return false;
    }

    @Override
    protected boolean checkRowsForWin() {
        for (int i = 0; i < BOARD_SIZE; i += ROW_SIZE) {
            //if (fields[i].isSet() && fields[i+1].isSet() && fields[i+2].isSet()) {
                if (checkRowCol(fields[i].getPlayer().getSymbol(), fields[i+1].getPlayer().getSymbol(), fields[i+2].getPlayer().getSymbol())) {
                    return true;
                }
            //}
        }
        return false;
    }

    @Override
    protected boolean checkColumnsForWin() {
        for (int i = 0; i < ROW_SIZE; i++) {
            //if (fields[i].isSet() && fields[i+ROW_SIZE].isSet() && fields[i+(ROW_SIZE*2)].isSet()) {
                if (checkRowCol(fields[i].getPlayer().getSymbol(), fields[i+ROW_SIZE].getPlayer().getSymbol(), fields[i+(ROW_SIZE*2)].getPlayer().getSymbol())) {
                    return true;
                }
            //}
        }
        return false;
    }

    @Override
    protected boolean checkDiagonalsForWin() {
        //if (fields[0].isSet() && fields[ROW_SIZE+1].isSet() && fields[(ROW_SIZE*2)+2].isSet()) {
            if (checkRowCol(fields[0].getPlayer().getSymbol(), fields[ROW_SIZE+1].getPlayer().getSymbol(), fields[(ROW_SIZE*2)+2].getPlayer().getSymbol())) {
                return true;
            }
        //}
        //if (fields[2].isSet() && fields[ROW_SIZE+1].isSet() && fields[(ROW_SIZE*2)].isSet()) {
            if (checkRowCol(fields[2].getPlayer().getSymbol(), fields[ROW_SIZE+1].getPlayer().getSymbol(), fields[(ROW_SIZE*2)].getPlayer().getSymbol())) {
                return true;
            }
        //}
        return false;
    }

    private boolean checkRowCol(char c1, char c2, char c3) {
        return (c1 != 'E') && (c1 == c2) && (c2 == c3);
    }
}
