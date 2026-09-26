import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class CPUPlayer {

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private Mark mark, opponent;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu) {
        mark = cpu;
        opponent = cpu == Mark.X ? Mark.O : Mark.X;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes() {
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles. Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;
        int best = Integer.MIN_VALUE;
        ArrayList<Move> nextMoves = new ArrayList<Move>();

        for (Move move : board.possibleMoves()) {
            Board child = board.copy();
            child.play(move, mark);
            int score = minMax(child, false);

            if (score > best) {
                best = score;
                nextMoves.clear();
                nextMoves.add(move);
            } else if (score == best) {
                nextMoves.add(move);
            }
        }
        return nextMoves;
    }

    // Retourne la liste des coups possibles. Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes = 0;
        int best = Integer.MIN_VALUE;
        ArrayList<Move> nextMoves = new ArrayList<Move>();

        for (Move move : board.possibleMoves()) {
            Board child = board.copy();
            child.play(move, mark);
            int score = alphaBeta(child, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > best) {
                best = score;
                nextMoves.clear();
                nextMoves.add(move);
            } else if (score == best) {
                nextMoves.add(move);
            }
        }
        return nextMoves;
    }

    private int minMax(Board board, boolean isMax) {
        numExploredNodes++;

        int score = board.evaluate(mark);
        if (score != 0 || board.possibleMoves().isEmpty()) {
            return score;
        }

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Mark toPlay = isMax ? mark : opponent;

        for (Move move : board.possibleMoves()) {
            Board child = board.copy();
            child.play(move, toPlay);
            int childScore = minMax(child, !isMax);
            best = isMax ? Math.max(best, childScore) : Math.min(best, childScore);
        }
        return best;
    }

    private int alphaBeta(Board board, boolean isMax, int alpha, int beta) {
        numExploredNodes++;

        int score = board.evaluate(mark);
        if (score != 0 || board.possibleMoves().isEmpty()) {
            return score;
        }

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Mark toPlay = isMax ? mark : opponent;

        for (Move move : board.possibleMoves()) {
            Board child = board.copy();
            child.play(move, toPlay);
            int childScore = alphaBeta(child, !isMax, alpha, beta);
            best = isMax ? Math.max(best, childScore) : Math.min(best, childScore);
            if (isMax){
                alpha = Math.max(alpha, childScore);
            } else {
                beta = Math.min(beta, childScore);
            }
            if (alpha>=beta) {
                break;
            }
        }
        return best;
    }

}
