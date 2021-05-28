import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConnectFour {

    private static final char[] PLAYERS = {'R', 'G'};
    private final int boardHeight;
    private final char[][] grid;
    private static final char[][] arr = new char[6][7];
    private int lastTop = -1, lastCol = -1;

    public ConnectFour(int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        grid = new char[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (j % 2 == 0) {
                    grid[i][j] = '|';
                } else {
                    grid[i][j] = ' ';
                }

            }
        }
    }

    public String toString() {
        return Arrays.stream(grid).map(String::new).collect(Collectors.joining("\n"));
    }

    public String horizontal() {
        return new String(grid[lastTop]);
    }

    public String vertical() {
        StringBuilder sb = new StringBuilder(boardHeight);

        for (int i = 0; i < boardHeight; i++) {
            sb.append(grid[i][lastCol]);
        }
        return sb.toString();
    }

    public boolean checkDiaganal() {
        boolean res;
        int cl;
        int cr;
        int r;
        for (r = 5; 3 <= r; r--) {
            // Проверяем диагональ слева
            for (cl = 0; cl + 3 < 7; cl++) {
                if (arr[r][cl] != '\u0000' || arr[r][cl] != 0) {
                    res = (arr[r][cl] == arr[r - 1][cl + 1]) &&
                            (arr[r][cl] == arr[r - 2][cl + 2]) &&
                            (arr[r][cl] == arr[r - 3][cl + 3]);

                    if (res) {
                        return true;
                    }
                }
            }

            for (cr = 6; 0 <= cr - 3; cr--) {
                if (arr[r][cr] != '\u0000' || arr[r][cr] != 0) {
                    res = (arr[r][cr] == arr[r - 1][cr - 1]) &&
                            (arr[r][cr] == arr[r - 2][cr - 2]) &&
                            (arr[r][cr] == arr[r - 3][cr - 3]);

                    if (res) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean contains(String str, String substring) {
        return str.contains(substring);
    }

    public boolean isWinningPlay() {
        int fill = 0;
        if (lastCol == -1) {
            System.err.println("No movie");
            return false;
        }
        for (int r = 0; r < 6; r++) {
            if (arr[0][r] != 0 && arr[0][r] != '\u0000') {
                fill++;
            }
            if (fill == 6) {
                System.out.println("Game over! draw!");
                break;
            }
        }
        char sym = grid[lastTop][lastCol];
        String streakG = String.format("|%c|%c|%c|%c|", sym, sym, sym, sym);
        String streakV = String.format("%c%c%c%c", sym, sym, sym, sym);
        return contains(horizontal(), streakG) || contains(vertical(), streakV);
    }

    public void chooseAndDrop(char symbol, Scanner input) {
        do {
            int col = input.nextInt();

            if (!(0 < col && col <= 7)) {
                System.out.println("Column must be between 1 - 7 ");
                continue;
            }
            String player;
            if (symbol == 'R') {
                player = "RED";
                System.out.println("\nPlayer " + 1 + " [" + player + "] choose column(1-7): " + col);

            } else {
                player = "GREEN";
                System.out.println("\nPlayer " + 2 + " [" + player + "] choose column(1-7) " + col);
            }

            int n = 0;
            for (int i = boardHeight - 1; i >= 0; i--) {
                switch (col) {
                    case 1:
                        n = 1;
                        break;
                    case 2:
                        n = 3;
                        break;
                    case 3:
                        n = 5;
                        break;
                    case 4:
                        n = 7;
                        break;
                    case 5:
                        n = 9;
                        break;
                    case 6:
                        n = 11;
                        break;
                    case 7:
                        n = 13;
                        break;
                }
                if (grid[i][n] == ' ') {
                    grid[lastTop = i][lastCol = n] = symbol;
                    arr[i][col - 1] = symbol;
                    return;
                }
            }
            System.out.println("Column " + col + "is full.");
        }
        while (true);
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner((System.in))) {
            int height = 6, wight = 15;
            int moves = height * wight;

            ConnectFour board = new ConnectFour(wight, height);

            System.out.println("Use 1-7 to choose a column");
            System.out.println(board);

            for (int player = 0; moves-- > 0; player = 1 - player) {
                char symbol = PLAYERS[player];
                board.chooseAndDrop(symbol, input);
                System.out.println(board);

                if (board.isWinningPlay() || board.checkDiaganal()) {
                    System.out.println("\nPlayer " + symbol + " wins!");
                    break;
                }
            }
            System.out.println("Game over");
        }
    }
}
