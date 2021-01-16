package MaximumHeightbyStackingCuboids;

import java.util.Arrays;

public class Solution {
    public int maxHeight(int[][] cuboidsArr) {
        int cuboids[][] = new int[cuboidsArr.length + 1][3];
        for (int i = 1; i <= cuboidsArr.length; i++) {
            cuboids[i][0] = cuboidsArr[i - 1][0];
            cuboids[i][1] = cuboidsArr[i - 1][1];
            cuboids[i][2] = cuboidsArr[i - 1][2];
        }
        int n = cuboids.length;
        for (int i = 0; i < n; i++) {
            Arrays.sort(cuboids[i]);
        }
        Arrays.sort(cuboids, (a, b) -> {
            if (a[0] != b[0]) {
                return -Integer.compare(a[0], b[0]);
            }
            if (a[1] != b[1]) {
                return -Integer.compare(a[1], b[1]);
            }
            return -Integer.compare(a[2], b[2]);
        });
        int dp[] = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = cuboids[i][2];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (cuboids[j][0] >= cuboids[i][0] && cuboids[j][1] >= cuboids[i][1] && cuboids[j][2] >= cuboids[i][2])
                    dp[i] = Math.max(dp[i], dp[j] + cuboids[i][2]);
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
