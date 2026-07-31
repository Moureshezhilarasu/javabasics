import java.util.*;

public class ZPATTERN {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = Integer.parseInt(sc.nextLine());
        for (int t = 0; t < T; t++) {
            String s = sc.nextLine().toLowerCase();
            int len = s.length();
            int n = (int)Math.sqrt(len);
            if (n * n != len) {
                System.out.println("NO");
                continue;
            }
            char[][] grid = new char[n][n];
            int idx = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    grid[i][j] = s.charAt(idx++);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 || i == n - 1 || j == n - i - 1)
                        System.out.print(grid[i][j] + "  ");
                    else
                        System.out.print("   ");
                }
                System.out.println();
            }
        }
    }
}
