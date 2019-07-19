package dev.fullslack.tictactoe;

public class FourInARow extends Game {
    private static int COLUMN_SIZE = 6;

    public FourInARow(Player player1, Player player2) {
        super(player1, player2);

        BOARD_SIZE = 42;
        ROW_SIZE = 7;

        fields = new Field[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            fields[i] = new Field(i + 1);
            fields[i].setPlayer(emptyPlayer);
        }
    }

    @Override
    protected void printBoard() {
        for (int i = 0; i < COLUMN_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                int arrayPointer = i * ROW_SIZE + j;
                //is field set?
                if (fields[arrayPointer].isSet()) {
                    //yes: print player symbol
                    char symbol = fields[arrayPointer].getPlayer().getSymbol();
                    System.out.print(symbol + " ");
                } else {
                    //no: print field number
                    //int number = fields[arrayPointer].getNumber() - 1;
                    //System.out.print(number + " ");
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("1 2 3 4 5 6 7");
    }

    @Override
    protected boolean setField(int columnNumber) {
        //check for valid input
        if (columnNumber >= 1 && columnNumber <= ROW_SIZE) {
            //check if field is set
            /*if (!fields[fieldNumber + 34].isSet()) {
                fields[fieldNumber + 34].setPlayer(currentPlayer);
                return true;
            }*/
            /*for (int i = 34 + columnNumber; i >= 0; i -= ROW_SIZE) {
                //check if field is set
                if (!fields[i].isSet()) {
                    fields[i].setPlayer(currentPlayer);
                    return true;
                }
            }*/
            for (int i = ROW_SIZE-2; i >= 0; i--) {
                //check if field is set
                if (!fields[i * ROW_SIZE + columnNumber - 1].isSet()) {
                    fields[i * ROW_SIZE + columnNumber - 1].setPlayer(currentPlayer);
                    return true;
                }
            }
        }
        return false;
    }

    /*@Override
    protected boolean checkForWin() {
        return checkRowsForWin();
    }*/

    @Override
    protected boolean checkRowsForWin() {
        for (int i = 0; i < COLUMN_SIZE; i++) {
            for (int j = 0; j < 4; j++) {
                int arrayPointer = i * ROW_SIZE + j;
                /*System.out.print(i * ROW_SIZE + j + " ");
                System.out.print(i * ROW_SIZE + j + 1 + " ");
                System.out.print(i * ROW_SIZE + j + 2 + " ");
                System.out.print(i * ROW_SIZE + j + 3 + " | ");*/
                if (checkRowCol(fields[arrayPointer].getPlayer().getSymbol(),
                                fields[arrayPointer + 1].getPlayer().getSymbol(),
                                fields[arrayPointer + 2].getPlayer().getSymbol(),
                                fields[arrayPointer + 3].getPlayer().getSymbol())) {
                    return true;
                }
            }
            //System.out.println();
        }
        return false;
    }

    @Override
    protected boolean checkColumnsForWin() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < 3; j++) {
                /*System.out.print(ROW_SIZE * j + i + " ");
                System.out.print(ROW_SIZE * (j+1) + i + " ");
                System.out.print(ROW_SIZE * (j+2) + i + " ");
                System.out.print(ROW_SIZE * (j+3) + i + " | ");*/
                if (checkRowCol(fields[ROW_SIZE * j + i].getPlayer().getSymbol(),
                        fields[ROW_SIZE * (j+1) + i].getPlayer().getSymbol(),
                        fields[ROW_SIZE * (j+2) + i].getPlayer().getSymbol(),
                        fields[ROW_SIZE * (j+3) + i].getPlayer().getSymbol())) {
                    return true;
                }
            }
            //System.out.println();
        }
        return false;
    }

    @Override
    protected boolean checkDiagonalsForWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int value = j + 3 + i * COLUMN_SIZE + i;
                /*System.out.print(value + " ");
                System.out.print(value + COLUMN_SIZE + " ");
                System.out.print(value + COLUMN_SIZE * 2 + " ");
                System.out.print(value + COLUMN_SIZE * 3 + " | ");*/
                if (checkRowCol(fields[value].getPlayer().getSymbol(),
                        fields[value + COLUMN_SIZE].getPlayer().getSymbol(),
                        fields[value + COLUMN_SIZE * 2].getPlayer().getSymbol(),
                        fields[value + COLUMN_SIZE * 3].getPlayer().getSymbol())) {
                    return true;
                }
            }
            //System.out.println();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int value = i * ROW_SIZE;
                /*System.out.print(j + value + " ");
                System.out.print(j + (COLUMN_SIZE + 2) + value + " ");
                System.out.print(j + (COLUMN_SIZE + 2) * 2 + value + " ");
                System.out.print(j + (COLUMN_SIZE + 2) * 3 + value + " | ");*/
                if (checkRowCol(fields[j + value].getPlayer().getSymbol(),
                        fields[j + (COLUMN_SIZE + 2) + value].getPlayer().getSymbol(),
                        fields[j + (COLUMN_SIZE + 2) * 2 + value].getPlayer().getSymbol(),
                        fields[j + (COLUMN_SIZE + 2) * 3 + value].getPlayer().getSymbol())) {
                    return true;
                }
            }
            //System.out.println();
        }
        return false;
    }

    private boolean checkRowCol(char c1, char c2, char c3, char c4) {
        return (c1 != 'E') && (c1 == c2) && (c2 == c3) && (c3 == c4);
    }

    @Override
    protected boolean gameHasEnded() {
        return false;
    }
}
