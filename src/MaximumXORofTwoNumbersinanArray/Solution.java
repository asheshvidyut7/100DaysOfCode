package MaximumXORofTwoNumbersinanArray;

public class Solution {

    class TrieNode {
        public TrieNode children[];
        public TrieNode() {
            children = new TrieNode[2];
        }
    }
    class Trie {
        TrieNode root;

        int MAX_BIT = 35;

        public void insert(long num) {
            root = insertTrie(num << 1, root, MAX_BIT);
        }
        public TrieNode insertTrie(long n, TrieNode node, int bitPos) {
            if (bitPos == -1) {
                return null;
            }
            int bitAtPosition = ((n & (1L << bitPos)) != 0) ? 1 : 0;
            if (node == null) {
                node = new TrieNode();
            }
            node.children[bitAtPosition] = insertTrie(n, node.children[bitAtPosition], bitPos - 1);
            return node;
        }

        public long query(long num) {
            return queryTree(num << 1, root, MAX_BIT, 0L) >> 1;
        }

        public long queryTree(long n, TrieNode node, int bitPos, long ans) {
            if (node == null)
                return ans;
            if (bitPos == -1) {
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
    public int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();
        for (int i = 0; i < nums.length; i++) {
            trie.insert(nums[i]);
        }
        long ans = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            ans = Math.max(ans, trie.query(nums[i]));
        }
        return (int)(ans);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findMaximumXOR(new int[]{2, 4}));
        System.out.println(new Solution().findMaximumXOR(new int[]{3,10,5,25,2,8}));
        System.out.println(new Solution().findMaximumXOR(new int[]{14,70,53,83,49,91,36,80,92,51,66,70}));
    }
}
