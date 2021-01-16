package FindMinimumTimetoFinishAllJobs;

import java.util.Arrays;

public class Solution {
    public int minimumTimeRequired(int[] jobs, int k) {
        int n = jobs.length;
        long dp[][] = new long[1 << n][k + 1];
        int[] val_mask = new int[1 << n];
        for (int i = 0; i < 1 << n; ++i) {
            for (int j = 0; j < n; ++j) {
                if ((i & (1 << j)) != 0) {
                    val_mask[i] += jobs[j];
                }
            }
        }
        for (int i = 0; i < 1 << n; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < 1 << n; ++i) {
            dp[i][1] = val_mask[i];
        }
        for (int i = 0; i < 1 << n; ++i) {
            for (int j = 2; j <= k; ++j) {
                dp[i][j] = dp[i][j - 1];
                for (int l = 0; l < i; ++l) {
                    if ((i & l) == l) {
                        dp[i][j] = Math.min(dp[i][j], Math.max(dp[l][j - 1], val_mask[i ^ l]));
                    }
                }
            }
        }
        return (int)dp[(1 << n) -  1][k];
    }
}
