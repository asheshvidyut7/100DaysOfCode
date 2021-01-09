package MaximumXORWithanElementFromArray;

import java.io.*;
import java.util.*;

public class Solution {
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int sortedQ[][] = new int[queries.length][queries[0].length + 1];
        for (int i = 0; i < queries.length; i++) {
            sortedQ[i][0] = i;
            sortedQ[i][1] = queries[i][0];
            sortedQ[i][2] = queries[i][1];
        }
        Arrays.sort(sortedQ, Comparator.comparingInt(a -> a[2]));
        Arrays.sort(nums);
        int indexNum = 0;
        Trie t = new Trie();
        int ans[] = new int[queries.length];
        for (int i = 0; i < sortedQ.length; i++) {
            int m = sortedQ[i][2];
            while(indexNum < nums.length && nums[indexNum] <= m) {
                t.insert(nums[indexNum]);
                indexNum++;
            }
            if (indexNum != 0)
                ans[sortedQ[i][0]] = (int)t.query(sortedQ[i][1]);
            else
                ans[sortedQ[i][0]] = -1;
        }
        return ans;
    }

    class TrieNode {
        public TrieNode children[];
        public TrieNode() {
            children = new TrieNode[2];
        }
    }
    class Trie {
        TrieNode root;

        int MAX_BIT = 35;

        public void insert(int num) {
            root = insertTrie(num, root, MAX_BIT);
        }
        public TrieNode insertTrie(int n, TrieNode node, int bitPos) {
            if (bitPos == -2) {
                return null;
            }
            int bitAtPosition = ((n & (1L << bitPos)) != 0) ? 1 : 0;
            if (node == null) {
                node = new TrieNode();
            }
            node.children[bitAtPosition] = insertTrie(n, node.children[bitAtPosition], bitPos - 1);
            return node;
        }

        public long query(int num) {
            return queryTree(num, root, MAX_BIT, 0L);
        }

        public long queryTree(int n, TrieNode node, int bitPos, long ans) {
            if (node == null)
                return ans;
            if (bitPos == -2) {
                return ans;
            }
            int bitAtPosition = ((n & (1L << bitPos)) != 0) ? 1 : 0;
            if (node.children[bitAtPosition ^ 1] != null) {
                ans |= (1L << (bitPos));
                return queryTree(n, node.children[bitAtPosition ^ 1], bitPos - 1, ans);
            }
            return queryTree(n, node.children[bitAtPosition], bitPos - 1, ans);
        }
    }
}
