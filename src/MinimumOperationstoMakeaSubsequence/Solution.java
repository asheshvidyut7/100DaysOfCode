package MinimumOperationstoMakeaSubsequence;


import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {
    public int minOperations(int[] target, int[] arr) {
        Map<Integer, Integer> index = new HashMap<>();
        Map<Integer, Integer> revIndex = new HashMap<>();
        for (int i = 0; i < target.length; i++) {
            index.put(i, target[i]);
            revIndex.put(target[i], i);
        }
        SegmentTree st = new SegmentTree(target.length);
        int maxValIndex[] = new int[target.length];
        for (int i = 0; i < target.length; i++) {
            maxValIndex[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            if (revIndex.containsKey(arr[i])) {
                int idx = revIndex.get(arr[i]);
                int maxValueBefore = st.query(0, idx - 1);
                if (maxValueBefore != Integer.MIN_VALUE)
                    st.update(idx, maxValueBefore + 1);
                else
                    st.update(idx, 1);
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < target.length; i++) {
            int val = st.query(i, i);
            if (val != Integer.MIN_VALUE) {
                ans = Math.min(ans, target.length - val);
            }
        }
        return ans;
    }

    private class SegmentTreeNode {
        int l, r;
        int maxValue;
        SegmentTreeNode left, right;
        public SegmentTreeNode(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    private class SegmentTree {
        SegmentTreeNode root;
        int ar[];
        public SegmentTree(int n) {
            ar = new int[n];
            root = buildTree(0, n - 1, root, ar);
        }

        private SegmentTreeNode buildTree(int l, int r, SegmentTreeNode node, int ar[]) {
            if (node == null) {
                node = new SegmentTreeNode(l, r);
            }
            if (l < r) {
                int mid = (l + r) >> 1;
                node.left = buildTree(l, mid, node.left, ar);
                node.right = buildTree(mid + 1, r, node.right, ar);
                node.maxValue = Math.max(node.left.maxValue, node.right.maxValue);
            }
            else {
                node.maxValue = ar[l];
            }
            return node;
        }
        public int query(int l, int r) {
            return queryTree(root, l, r);
        }

        private int queryTree(SegmentTreeNode node, int l, int r) {
            if (node == null)
                return Integer.MIN_VALUE;
            if (node.r < l || node.l > r) {
                return Integer.MIN_VALUE;
            }
            if (node.l >= l && node.r <= r) {
                return node.maxValue;
            }
            return Math.max(queryTree(node.left, l, r), queryTree(node.right, l, r));
        }
        public void update(int id, int val) {
            updateTree(id, val, root);
        }

        private void updateTree(int id, int val, SegmentTreeNode node) {
            if (node == null)
                return;
            if (node.l == id && node.r == id) {
                node.maxValue = val;
                return;
            }
            if (node.r < id || node.l > id) {
                return;
            }
            updateTree(id, val, node.left);
            updateTree(id, val, node.right);
            node.maxValue = Math.max(node.left.maxValue, node.right.maxValue);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minOperations(new int[]{16,7,20,11,15,13,10,14,6,8}

                , new int[]{11,14,15,7,5,5,6,10,11,6}));
    }
}
