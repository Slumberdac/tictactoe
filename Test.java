import java.util.ArrayList;
import java.util.Random;

public class Test {

    private static final Random rng = new Random();
    private static int failures = 0;

    public static void main(String[] args) {
        fixedPositions();
        randomGames(200);
        System.out.println(failures == 0 ? "\nTOUS LES TESTS PASSENT" : "\nECHECS : " + failures);
    }

    private static void fixedPositions() {
        check("X, plateau vide", Mark.X, new Board(),
                "[(0,0), (0,1), (0,2), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]", 549945, 30709);

        Board b = new Board();
        b.play(new Move(0, 0), Mark.X);
        check("O, X en (0,0)", Mark.O, b, "[(1,1)]", 59704, 4089);

        b = new Board();
        b.play(new Move(0, 0), Mark.X);
        b.play(new Move(0, 1), Mark.X);
        b.play(new Move(1, 0), Mark.O);
        b.play(new Move(1, 1), Mark.O);
        check("X, gagne en (0,2)", Mark.X, b, "[(0,2)]", 156, 64);

        b = new Board();
        b.play(new Move(0, 0), Mark.X);
        b.play(new Move(0, 1), Mark.X);
        b.play(new Move(1, 1), Mark.O);
        check("O, bloque en (0,2)", Mark.O, b, "[(0,2)]", 934, 220);
    }

    private static void check(String name, Mark cpuMark, Board board,
                              String expected, int mmNodes, int abNodes) {
        CPUPlayer cpu = new CPUPlayer(cpuMark);
        String mm = cpu.getNextMoveMinMax(board).toString();
        int mmN = cpu.getNumOfExploredNodes();
        String ab = cpu.getNextMoveAB(board).toString();
        int abN = cpu.getNumOfExploredNodes();

        boolean ok = mm.equals(expected) && ab.equals(expected) && mmN == mmNodes && abN == abNodes;
        if (!ok) failures++;
        System.out.println((ok ? "OK    " : "ECHEC ") + name);
        if (!ok) {
            System.out.println("  attendu : " + expected + " MM=" + mmNodes + " AB=" + abNodes);
            System.out.println("  obtenu  : MM " + mm + " (" + mmN + ")  AB " + ab + " (" + abN + ")");
        }
    }

    private static void randomGames(int games) {
        int wins = 0, draws = 0, losses = 0;

        for (int g = 0; g < games; g++) {
            Mark cpuMark = rng.nextBoolean() ? Mark.X : Mark.O;
            CPUPlayer cpu = new CPUPlayer(cpuMark);
            Board board = new Board();

            for (int turn = 0; ; turn++) {
                int score = board.evaluate(cpuMark);
                if (score == 100) { wins++; break; }
                if (score == -100) {
                    losses++;
                    failures++;
                    System.out.println("DEFAITE du CPU (" + cpuMark + ") :");
                    board.printBoard();
                    break;
                }
                if (board.possibleMoves().isEmpty()) { draws++; break; }

                Mark current = turn % 2 == 0 ? Mark.X : Mark.O;
                Move move;
                if (current == cpuMark) {
                    ArrayList<Move> mm = cpu.getNextMoveMinMax(board);
                    ArrayList<Move> ab = cpu.getNextMoveAB(board);
                    if (!mm.toString().equals(ab.toString())) {
                        failures++;
                        System.out.println("MM != AB : " + mm + " vs " + ab);
                        board.printBoard();
                    }
                    move = ab.get(rng.nextInt(ab.size()));
                } else {
                    ArrayList<Move> moves = board.possibleMoves();
                    move = moves.get(rng.nextInt(moves.size()));
                }
                board.play(move, current);
            }
        }
        System.out.println("\n" + games + " parties contre un joueur aléatoire : "
                + wins + " victoires, " + draws + " nulles, " + losses + " défaites");
    }
}