import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class Board {
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark) {
        if (board[m.getRow()][m.getCol()] == Mark.EMPTY) {
            board[m.getRow()][m.getCol()] = mark;
        } else {
            throw new IllegalArgumentException("Espace déjà occupé");
        }
    }

    // retourne 100 pour une victoire
    // -100 pour une défaite
    // 0 pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark) {
        Mark opponentMark = mark == Mark.X ? Mark.O : Mark.X;
        if (hasWon(mark)) {
            return 100;
        } else if (hasWon(opponentMark)) {
            return -100;
        } else {
            return 0;
        }
    }

    /**
     * Looks for any 3 consecutive instance of the specified mark (i.e if it has
     * won)
     *
     * @param mark
     * @return
     */
    public boolean hasWon(Mark mark) {
        return (mark == board[0][0] && mark == board[0][1] && mark == board[0][2]) || // First Row
                (mark == board[1][0] && mark == board[1][1] && mark == board[1][2]) || // Second Row
                (mark == board[2][0] && mark == board[2][1] && mark == board[2][2]) || // Third Row
                (mark == board[0][0] && mark == board[1][0] && mark == board[2][0]) || // First Column
                (mark == board[0][1] && mark == board[1][1] && mark == board[2][1]) || // Second Column
                (mark == board[0][2] && mark == board[1][2] && mark == board[2][2]) || // Third Column
                (mark == board[0][0] && mark == board[1][1] && mark == board[2][2]) || // ↘ Diagonal
                (mark == board[0][2] && mark == board[1][1] && mark == board[2][0]); // ↙ Diagonal
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.printf("[%s]", board[i][j] != Mark.EMPTY ? board[i][j].toString() : " ");
            }
            System.out.println();
        }
    }

    public ArrayList<Move> possibleMoves() {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
        return possibleMoves;
    }

    public Board copy() {
        Board b = new Board();
        for (int i = 0; i < 3; i++) {
            b.board[i] = board[i].clone();
        }
        return b;
    }
}
