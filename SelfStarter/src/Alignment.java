import java.util.Arrays;

public class Alignment {
    public static void main(String[] args) {
        String s1 = "AGGGCT";
        String s2 = "AGGCA";
        int misMatchPenalty = 3;
        int gapPenalty = 2;

        getMinPenalty(s1, s2, misMatchPenalty, gapPenalty);
    }

    private static void getMinPenalty(String x, String y, int pxy, int pgap) {
        if (x == null || y == null) {
            System.out.println("Invalid Input for the sequences.");
            return;
        }

        int m = x.length();
        int n = y.length();

        // dp[i][j] represents the minimum penalty between the first i characters of x
        // and the first j characters of y
        int dp[][] = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            dp[i][0] = i * pgap;
        }

        for (int i = 0; i < n; i++) {
            dp[0][i] = i * pgap;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + pxy , // mismatch
                            dp[i - 1][j] + pgap) ,  // y gap
                            dp[i][j - 1] + pgap );  // x gap
                }
            }
        }

        // Reconstructing the solution by tracking dp

        int l = n + m; // maximum possible length

        int i = m;
        int j = n;

        int posX = l;
        int posY = l;

        int resX[] = new int[l + 1];
        int resY[] = new int[l + 1];

        while (i != 0 && j != 0) {
            if (x.charAt(i - 1) == y.charAt(j - 1)) {  // matched position
                resX[posX--] = x.charAt(i - 1);
                resY[posY--] = y.charAt(j - 1);
                i--;
                j--;
            } else if (dp[i - 1][j - 1] + pxy == dp[i][j]) {  // mismatched position
                resX[posX--] = '*';
                resY[posY--] = '*';
                i--;
                j--;
            } else if (dp[i - 1][j] + pgap == dp[i][j]) {  // y gap
                resX[posX--] = x.charAt(i - 1);
                resY[posY--] = '_';
                i--;
            } else if (dp[i][j - 1] + pgap == dp[i][j]) {  // x gap
                resX[posX--] = '_';
                resY[posY--] = y.charAt(j - 1);
                j--;
            }
        }

        while (posX > 0) {
            if (i > 0) {
                resX[posX--] = x.charAt(--i);
            } else {
                resX[posX--] = '_';
            }
        }

        while (posY > 0) {
            if (j > 0) {
                resY[posY--] = y.charAt(--j);
            } else {
                resY[posY--] = '_';
            }
        }

        int id = 1;
        // trimming
        for (i = l; i >= 1; i--) {
            if (resY[i] == '_' && resX[i] == '_') {
                id = i + 1;
                break;
            }
        }

        System.out.println("Minimum Penalty in aligning the sequence is " + dp[m][n]);
        System.out.println("The aligned sequence are:");

        for (i = id; i <= l; i++) {
            System.out.print((char)resX[i]);
        }

        System.out.print("\n");
        for (i = id; i <= l; i++) {
            System.out.print((char)resY[i]);
        }

        return;
    }
}
