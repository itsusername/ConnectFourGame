import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFour {

    private static final char[] PLAYERS = {'R', 'G'};
    private int boardWidth;
    private int boardHeight;
    private final char[][] grid;
    private int lastTop = -1, lastCol = -1;

    public ConnectFour (int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        grid = new char[boardHeight][];

        for (int i = 0; i < boardHeight; i++) {
            Arrays.fill(grid[i] = new char[boardWidth], '.');
        }
    }

    public String toString() {
        return IntStream.range(0 , boardWidth).mapToObj(Integer::toString).collect(Collectors.joining()) + "\n"
                + Arrays.stream(grid).map(String::new).collect(Collectors.joining("\n"));
    }

    public String horizontal() {
        return new String(grid[lastTop]);
    }

    public String vertical() {
        StringBuilder sb = new StringBuilder(boardHeight);

        for (int i = 0; i < boardHeight; i++) {
            sb.append(grid[boardHeight][lastCol]);
        }
        return sb.toString();
    }

    public String slashDioganal() {
        StringBuilder sb = new StringBuilder(boardHeight);
        for (int i = 0; i < boardHeight; i++) {
            int w = lastCol + lastTop - i;

            if (0 <= w && w < boardWidth) {
                sb.append(grid[i][w]);
            }
        }
        return sb.toString();
    }

    public String backlashDiagonal() {
        StringBuilder sb = new StringBuilder(boardHeight);
        for (int i = 0; i < boardHeight; i++) {
            int w = lastCol - lastTop + i;

            if (0 <= w && w < boardWidth) {
                sb.append(grid[i][w]);
            }
        }
        return sb.toString();
    }

    public  static  boolean contains(String str, String substring) {
        return str.indexOf(substring) >= 0;
    }

    public  boolean isWinningPlay() {
        if(lastCol  == -1) {
            System.err.println("No movie");
            return false;
        }
        char sym = grid[lastTop][lastCol];
        String streak = String.format("%c%c%c%c", sym, sym, sym, sym);

        return contains(horizontal(), streak) ||contains(vertical(), streak)
                || contains(slashDioganal(), streak) || contains(backlashDiagonal(), streak);
    }

    public void chooseAndDrop(char symbol, Scanner input) {
        do {
            System.out.println("\nPlayer " + symbol + "turn: ");
            int col = input.nextInt();
            if (!(0<= col && col < boardWidth)) {
                System.out.println("Column must be between 0 and " + (boardWidth-1));
                continue;
            }

            for ( int i = boardHeight - 1; i >=0; i--) {
                if(grid[i][col] == '.') {

                    grid[lastTop = i][lastCol = col] = symbol;
                    return;
                }
            }
            System.out.println("Column " + col + "is full.");
        } while (true);
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner((System.in))) {
            int height = 6, wight = 7;
            int moves = height * wight;

            ConnectFour board = new ConnectFour(wight, height);

            System.out.println("Use 0-" + (wight-1) + "to choose a column");
            System.out.println(board);

            for (int player = 0; moves-- > 0; player = 1 - player) {
                char symbol = PLAYERS[player];
                board.chooseAndDrop(symbol, input);
                System.out.println(board);

                if (board.isWinningPlay()) {
                    System.out.println("\nPlayer " + symbol + "wins!");
                }
            }
            System.out.println( "Game over");
        }
    }
}
