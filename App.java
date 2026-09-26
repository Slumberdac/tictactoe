import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Board board = new Board();

        Scanner input = new Scanner(System.in);
        Random rng = new Random();

        String choice;
        Mark playerMark = Mark.EMPTY;

        do {
            System.out.print("Sélectionnez votre symbole (X, O) :");
            choice = input.next();
            switch (choice) {
                case "X":
                    playerMark = Mark.X;
                    break;
                case "O":
                    playerMark = Mark.O;
                    break;
                default:
                    System.err.println("Choix incorrect!");
                    break;
            }
        } while (!choice.equals("X") && !choice.equals("O"));

        CPUPlayer cpu = new CPUPlayer(playerMark == Mark.X ? Mark.O : Mark.X);
        CPUPlayer player = new CPUPlayer(playerMark);
        int turn = 0;
        Mark currentMark;

        while (true) {
            board.printBoard();
            Move move;
            currentMark = turn % 2 == 0?Mark.X:Mark.O;

            if (board.evaluate(playerMark) != 0 || board.possibleMoves().isEmpty()) {
                switch (board.evaluate(currentMark)) {
                    case 100:
                        System.out.println("GAGNÉ!");
                        break;
                    case -100:
                        System.out.println("PERDU!");
                        break;
                    default:
                        System.out.println("MATCH NUL!");
                        break;
                }
                break;
            }
            if (playerMark == currentMark) {
                // System.out.println("VOTRE TOUR!");
                // System.out.print("Quelle rangée ? (1,2,3) : ");
                // int row = input.nextInt() - 1;

                // System.out.print("Quelle Colonne ? (1,2,3) : ");
                // int column = input.nextInt() - 1;

                // move = new Move(row, column);

                // ArrayList<Move> possibleMoves = player.getNextMoveMinMax(board);
                ArrayList<Move> possibleMoves = player.getNextMoveAB(board);
                move = possibleMoves.get(rng.nextInt(possibleMoves.size()));
            } else {
                System.out.println("TOUR DE L'ORDINATEUR!");
                // ArrayList<Move> possibleMoves = cpu.getNextMoveMinMax(board);
                ArrayList<Move> possibleMoves = cpu.getNextMoveAB(board);
                move = possibleMoves.get(rng.nextInt(possibleMoves.size()));
            }

            try {
                board.play(move, currentMark);
            } catch (Exception e) {
                System.err.println(e);
                continue;
            }
            System.out.println();
            turn++;
        }
        input.close();
    }
}
