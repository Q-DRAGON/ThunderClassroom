package lzketh;

import lzketh.model.Question;

import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class test {
	public int removeDuplicates(int[] nums) {
		if (nums == null || nums.length < 2) {
			return nums.length;
		}
		int j = 1, count = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == nums[i - 1]) {
				count++;
			} else {
				count = 1;
			}

			if (count <= 2) {
				nums[j] = nums[i];
				j++;
			}
		}
		return j;
	}

	public static String getHint(String secret, String guess) {
		int bulls = 0;
		int len = secret.length();
		int[] mapS = new int[10];
		int[] mapG = new int[10];
		for (int i = 0; i < len; i++) {
			char s1 = secret.charAt(i);
			char g1 = guess.charAt(i);
			if (s1 == g1) {
				bulls++;
			} else {
				mapS[s1 - '0'] = mapS[s1 - '0'] + 1;
				mapG[g1 - '0'] = mapG[g1 - '0'] + 1;
			}
		}
		int cows = 0;
		for (int i = 0; i < 10; i++) {
			cows += Math.min(mapG[i], mapS[i]);
		}
		return bulls + "A" + cows + "B";
	}

	public boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < nums.length; i++) {
			int e = nums[i];
			if (set.contains(e)) {
				return true;
			} else {
				set.add(e);
			}
		}
		return false;
	}

	// 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，
	// 使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < nums.length; i++) {
			int e = nums[i];
			if (set.contains(e)) {
				return true;
			} else {
				set.add(e);
			}
			// 维持一个 窗口大小
			if (set.size() > k) {
				set.remove(nums[i - k]);
			}
		}
		return false;
	}

	// 在整数数组 nums 中，是否存在两个下标 i 和 j，
	// 使得 nums [i] 和 nums [j] 的差的绝对值小于等于 t ，且满足 i 和 j 的差的绝对值也小于等于 ķ 。
	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = 0; i < nums.length; ++i) {
			// Find the successor of current element
			Integer s = set.ceiling(nums[i]);
			if (s != null && s <= nums[i] + t) {
				return true;
			}

			// Find the predecessor of current element
			Integer g = set.floor(nums[i]);
			if (g != null && nums[i] <= g + t) {
				return true;
			}

			set.add(nums[i]);
			if (set.size() > k) {
				set.remove(nums[i - k]);
			}
		}
		return false;
	}

	public boolean canJump(int[] nums) {
		int n = nums.length;
		int rightmost = 0;
		for (int i = 0; i < n; ++i) {
			if (i <= rightmost) {
				rightmost = Math.max(rightmost, i + nums[i]);
				if (rightmost >= n - 1) {
					return true;
				}
			}
		}
		return false;
	}

	public static int maxArea(int[] height) {
		int len = height.length;
		int left = 0;
		int right = len - 1;
		int s = Math.min(height[left], height[right]) * (right - left);
		System.out.print(s + "");
		while (left < right) {
			if (height[left] > height[right]) {
				right--;
			} else {
				left++;
			}
			int s1 = Math.min(height[left], height[right]) * (right - left);
			Utils.log("left %s right %s", left, right, s1);
			s = Math.max(s, s1);
		}
		return s;
	}

	public static int trap(int[] height) {
		int left = 0;
		int right = 1;
		int rain = 0;
		int len = height.length;
		while (right < len) {
			if (height[right] >= height[left]) {
				int r1 = (right - left) * Math.min(height[left], height[right]);
				rain += r1;
				Utils.log("left %s right %s rain %s r1 %s", left, right, r1, rain);
				left = right;
			}
			right++;
		}

		return rain;
	}

	public static int strStr(String haystack, String needle) {
		int needleLne = needle.length();
		if (needleLne == 0) {
			return 0;
		}
		int hayLen = haystack.length();
		if (hayLen == needleLne) {
			if (haystack.equals(needle)) {
				return 0;
			} else {
				return -1;
			}
		}
		for (int i = 0; i < hayLen - needleLne + 1; i++) {
			String l = haystack.substring(i, i + needleLne);
			Utils.log("l %s ne %s", l, needle);
			if (l.equals(needle)) {
				return i;
			}
		}
		return -1;
	}

	public String longestCommonPrefix(String[] strs) {
		if (strs.length < 1) {
			return "";
		}
		StringBuilder r = new StringBuilder();
		int index = 0;
		boolean status = true;
		while (status) {
			char c = strs[0].charAt(index);
			for (int i = 1; i < strs.length; i++) {
				String s = strs[i];
				if (s.charAt(index) != c) {
					status = false;
					break;
				}
			}
			if (status) {
				r.append(c);
				index++;
			}
		}
		return r.toString();
	}

	public static int lengthOfLastWord(String s) {
		s = s.stripTrailing();
		int len = s.length();
		int count = 0;
		for (int i = len - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == ' ') {
				break;
			}
			count++;
		}
		return count;
	}

	public static int firstUniqChar(String s) {
		int len = s.length();
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			map.put(c, map.getOrDefault(c, 0) + 1);
		}

		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (map.get(c) == 1) {
				return i;
			}
		}
		return -1;
	}

	public boolean canConstruct3(String ransomNote, String magazine) {
		int[] countR = new int[26];
		int[] countM = new int[26];
		for(char c : ransomNote.toCharArray()) {
			countR[c - 'a'] += 1;
		}
		for(char c : magazine.toCharArray()) {
			countM[c - 'a'] += 1;

		}
		for(int i = 0; i < 26; i++) {
			if(countR[i] > countM[i]) {
				return false;
			}
		}
		return true;
	}

	public static String reverseWords(String s) {
		// 除去开头和末尾的空白字符
		s = s.trim();
		// 正则匹配连续的空白字符作为分隔符分割
		List<String> wordList = Arrays.asList(s.split(" +"));
		Utils.log("%s", wordList);
		Collections.reverse(wordList);
		return String.join(" ", wordList);
	}

	public static String exchange(char[] s, int i1, int i2) {
		s[i1] = (char) (s[i1] + s[i2]);
		s[i2] = (char) (s[i1] - s[i2]);
		s[i1] = (char) (s[i1] - s[i2]);
		return new String(s);
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int val = listToInt(l1) + listToInt(l2);
		return intToList(val);
	}

	public static int listToInt(ListNode l) {
		ListNode c = l;
		int r = 0;
		int mul = 10;
		while (c != null) {
			r *= mul;
			r += c.val;
			c = c.next;
		}
		return r;
	}

	public static ListNode intToList(int num) {
		ListNode r = new ListNode(0);
		if (num == 0) {
			return r;
		}
		while (num > 0) {
			int n = num % 10;
			num = num / 10;
			ListNode newNode = new ListNode(n);
			if (r.next == null) {
				r.next = newNode;
			} else {
				newNode.next = r.next;
				r.next = newNode;
			}
		}
		return r.next;
	}

	public static ListNode swapNextTwo(ListNode node) {
		if (node.next != null && node.next.next != null) {
			ListNode n1 = node.next;
			ListNode n2 = node.next.next;
			node.next = n2;
			n1.next = n2.next;
			n2.next = n1;
		}
		return node;
	}

	public static ListNode reverseList(ListNode head) {
		Stack<Integer> s = new Stack<>();
		ListNode cur = head;
		while (cur != null) {
			s.push(cur.val);
			cur = cur.next;
		}

		ListNode r = new ListNode(0);
		cur = r;
		while (!s.empty()) {
			ListNode node = new ListNode(s.pop());
			cur.next = node;
			cur = cur.next;
		}
		return r;
	}

	public boolean hasCycle(ListNode head) {
		Set<ListNode> set = new HashSet<>();
		ListNode cur = head;
		while (cur != null) {
			set.add(cur);
			if (set.contains(cur.next)) {
				return true;
			}
			cur = cur.next;
		}
		return false;
	}

	public static ListNode mergeKLists(ListNode[] lists) {
		if (lists.length < 1) {
			return null;
		}
		ListNode r = lists[0];
		for (int i = 1; i < lists.length; i++) {
			r = mergeTwoList(r, lists[i]);
		}
		return r;
	}

	public static ListNode mergeTwoList(ListNode l1, ListNode l2) {
		ListNode r = new ListNode(0);
		ListNode cur = r;
		ListNode c1 = l1;
		ListNode c2 = l2;
		while (c1 != null || c2 != null) {
			int val1 = c1 != null ? c1.val : Integer.MAX_VALUE;
			int val2 = c2 != null ? c2.val : Integer.MAX_VALUE;
			if (val1 < val2) {
				cur.next = new ListNode(val1);
				c1 = c1.next;
			} else {
				cur.next = new ListNode(val2);
				c2 = c2.next;
			}
			cur = cur.next;
		}
		return r.next;
	}

	public static ListNode findMiddle(ListNode node) {
		ListNode slow = node;
		ListNode fast = node.next;

		if (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return slow;
	}

	public static int[] twoSum(int[] numbers, int target) {
		int len = numbers.length;
		int slow = 0, fast = len - 1;
		while (slow < fast) {
			int sum = numbers[slow] + numbers[fast];
			if (sum == target) {
				return new int[] {slow + 1, fast + 1};
			} else if (sum < target) {
				slow++;
			} else {
				fast--;
			}
		}
		return null;
	}

	private static void testTwoSum() {
		Utils.log("%s", Arrays.toString(twoSum(new int[]{-1, 0}, -1)));
	    Utils.log("**** TwoSum test finish");
	}

	public int[] sortedSquares(int[] A) {
		for (int i = 0; i < A.length; i++) {
			A[i] = A[i] * A[i];
		}
		Arrays.sort(A);
		return A;
	}

	public static int[] sortedSquares1(int[] A) {
		int len = A.length;
		int[] B = new int[len];
		int head = 0;
		for (int i = 0; i < len; i++) {
			if (A[i] < 0) {
				head = i;
			}
		}
		int tail = head + 1;
		int index = 0;
		while (head >= 0 || tail < len) {
			int val1 = head >= 0 ? A[head] * A[head] : Integer.MAX_VALUE;
			int val2 = tail < len ? A[tail] * A[tail] : Integer.MAX_VALUE;
			if (val1 < val2) {
				B[index] = val1;
				head--;
			} else {
				B[index] = val2;
				tail++;
			}
			index++;
		}
		return B;
	}

	private static void testsortedSquares1() {
		Utils.log("%s", Arrays.toString(sortedSquares1(new int[] {-4,-1,0,3,10})));
	    Utils.log("**** sortedSquares1 test finish");
	}

	private static void testcombinationSum() {
		Solution s = new Solution();
		Utils.log("%s", s.combinationSum(new int[]{10,1,2,7,6,1,5}, 8));
	    Utils.log("**** combinationSum test finish");
	}

	public static void main(String[] args) {
		// testTwoSum();
		// testsortedSquares1();
		testcombinationSum();
	}

}

class Node {
	Node next;
	int val;

	public Node(int val) {
		this.val = val;
		this.next = null;
	}
}

class MyLinkedList {
	public Node head;
	public int len;
	/** Initialize your data structure here. */
	public MyLinkedList() {
		this.len = 0;
		this.head = new Node(-1);
	}

	/** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
	public int get(int index) {
		if (index > len - 1) {
			return -1;
		}
		Node cur = this.head;
		while (index > -1) {
			index--;
			cur = cur.next;
		}
		return cur.val;
	}

	/** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
	public void addAtHead(int val) {
		Node n = new Node(val);
		n.next = this.head.next;
		this.head.next = n;
		this.len++;
	}

	/** Append a node of value val to the last element of the linked list. */
	public void addAtTail(int val) {
		Node n = new Node(val);
		Node cur = this.head;
		while (cur.next != null) {
			cur = cur.next;
		}
		cur.next = n;
		this.len++;
	}

	/** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
	public void addAtIndex(int index, int val) {
		if (index == len) {
			this.addAtTail(val);
		} else if (index <= len - 1) {
			Node n = new Node(val);
			Node cur = this.head;
			while (index > 0) {
				cur = cur.next;
				index--;
			}
			n.next = cur.next;
			cur.next = n;
			this.len++;
		} else if (index < 0) {
			this.addAtHead(val);
		}
	}

	/** Delete the index-th node in the linked list, if the index is valid. */
	public void deleteAtIndex(int index) {
		if (index >= 0 && index < len) {
			Node cur = this.head;
			while (index > 0) {
				cur = cur.next;
				index--;
			}
			cur.next = cur.next.next;
			this.len--;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("len: ");
		sb.append(len);
		sb.append(" | ");
		Node n = this.head.next;
		while (n != null) {
			sb.append(n.val);
			sb.append("->");
			n = n.next;
		}
		return sb.toString();
	}
}

class Solution1 {
	ArrayList<String> rs = new ArrayList<>();

	public List<String> letterCombinations(String digits) {
		if (digits.length() > 0) {
			findCombination(digits, 0, "");
		}
		return rs;
	}

	public void findCombination(String digits, int index, String com) {
		if (index == digits.length()) {
			rs.add(com.toString());
		} else {
			char s = digits.charAt(index);
			String words = getWordsByNum(s);
			for (int i = 0; i < words.length(); i++) {
				findCombination(digits, index + 1, com + words.substring(i, i + 1));
			}
		}
	}

	private String getWordsByNum(char num) {
		String[] letterMap = {
				" ",    //0
				"",     //1
				"abc",  //2
				"def",  //3
				"ghi",  //4
				"jkl",  //5
				"mno",  //6
				"pqrs", //7
				"tuv",  //8
				"wxyz"  //9
		};
		return letterMap[num - '0'];
	}
}

class Solution {
	List<List<Integer>> res=new ArrayList<>();

	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		if (candidates==null) {
			return res;
		}
		Arrays.sort(candidates);
		dfs(target, 0, new Stack<Integer>(), candidates);
		return res;
	}
	//深度遍历
	private void dfs(int target, int index, Stack<Integer> pre, int[] candidates) {
		//等于零说明结果符合要求
		if (target==0){
			res.add(new ArrayList<>(pre));
			return;
		}
		//遍历，index为本分支上一节点的减数的下标
		for (int i = index; i < candidates.length; i++){
			//如果减数大于目标值，则差为负数，不符合结果
			// 减掉可能重复的分支
			if (i > index && candidates[i] == candidates[i - 1]) {
				continue;
			}
			if (candidates[i] <= target){
				pre.push(candidates[i]);
				//目标值减去元素值
				dfs(target - candidates[i], i + 1, pre, candidates);
				//每次回溯将最后一次加入的元素删除
				pre.pop();
			}
		}
	}
}

class Solution3 {
	List<List<Integer>> rs = new ArrayList<>();
	public List<List<Integer>> combine(int n, int k) {
		dfs(n, k, new Stack<>());
		return rs;
	}

	public void dfs(int n, int k, Stack<Integer> nums) {
		if (k == 0) {
			rs.add(new ArrayList<>(nums));
		} else {
			for (int i = n; i > 0; i--) {
				nums.push(i);
				dfs(i - 1, k - 1, nums);
				nums.pop();
			}
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution3().combine(5, 3));
	}
}

class Solution4 {
	List<List<Integer>> rs = new ArrayList<>();

	public List<List<Integer>> subsets(int[] nums) {
		rs.add(new ArrayList<>());
		dfs(nums, 0, new ArrayList<>());

		return rs;
	}

	public void dfs(int[] nums, int index, ArrayList<Integer> list) {
		int len = nums.length;
		for (int i = index; i < len; i++) {

			ArrayList<Integer> newList = new ArrayList<>(list);
			newList.add(nums[i]);
			Utils.log("index %s i %s old %s new %s", index, i, list, newList);
			rs.add(newList);
			dfs(nums, i + 1, newList);
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution4().subsets(new int[] {1, 2, 3}));
	}
}

class Solution5 {
	List<List<Integer>> rs = new ArrayList<>();

	public List<List<Integer>> combinationSum3(int k, int n) {
		dfs(k, 1, n, new ArrayList<>());
		return rs;
	}

	public void dfs(int k, int index, int n, ArrayList<Integer> list) {
		if (n == 0 && k == 0) {
			Utils.log("get target %s", k, n, list);
			rs.add(new ArrayList<>(list));
		}
		if (k < 0 || n < 0) {
			return;
		}
		for (int i = index; i < 10; i++) {
			ArrayList<Integer> l = list;
			l.add(i);
			dfs(k - 1, i + 1, n - i, l);
			l.remove(l.size() - 1);
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution5().combinationSum3(2, 7));
	}
}

class Solution6 {
	List<List<Integer>> rs = new ArrayList<>();
	int[] nums;

	public List<List<Integer>> permute(int[] nums) {
		this.nums = nums;
		int[] userd = new int[nums.length];
		dfs(0, userd, new ArrayList<>());
		return rs;
	}

	public void dfs(int index, int[] used, List<Integer> output) {
		if (index == nums.length) {
			rs.add(new ArrayList<>(output));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (used[i] != 1) {
					output.add(nums[i]);
					used[i] = 1;
					dfs(index + 1, used, output);
					used[i] = 0;
					output.remove(output.size() - 1);
				}
			}
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution6().permute(new int[]{1, 1, 2}));
	}
}

class Solution7 {
	public List<String> letterCasePermutation(String S) {
		List<StringBuilder> rList = new LinkedList<>();
		rList.add(new StringBuilder());
		for (int i = 0; i < S.length(); i++) {
			char c = S.charAt(i);
			if (Character.isDigit(c)) {
				for (StringBuilder sb : rList) {
					sb.append(c);
				}
			} else {
				for (int j = 0; j < rList.size(); j++) {
					StringBuilder sb = rList.get(j);
					StringBuilder sb1 = new StringBuilder(sb);
					sb.append(Character.toUpperCase(c));
					sb1.append(Character.toLowerCase(c));
					rList.add(sb1);
				}
			}
		}
		List<String> r = new ArrayList<>();
		for (StringBuilder sb : rList) {
			r.add(sb.toString());
		}
		return r;
	}
}


class Solution8 {
	List<String> rs = new ArrayList<>();
	public List<String> generateParenthesis(int n) {
		dfs(n, n, new StringBuilder());
		return rs;
	}

	public void dfs(int left, int right, StringBuilder res) {
		Utils.log("res %s", res);
		if (left == 0 && right == 0) {
			rs.add(res.toString());
		}

		if (right > 0 && left > 0) {
			res.append("(");
			dfs(left - 1, right, res);
			res.deleteCharAt(res.length() - 1);
		}

		if (right > 0) {
			if (right > left) {
				res.append(")");
				dfs(left, right - 1, res);
				res.deleteCharAt(res.length() - 1);
			}
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution8().generateParenthesis(3));
	}
}

class Solution9 {
	List<StringBuilder> rs = new ArrayList<>();
	String target;
	public List<String> removeInvalidParentheses(String s) {
		rs.add(new StringBuilder());
		this.target = s;
		dfs(0, 0, 0);
		List<String> r = new ArrayList<>();
		for (StringBuilder sb : rs) {
			r.add(sb.toString());
		}
		return r;
	}

	public void dfs(int index, int left, int right) {
		if (index < this.target.length()) {
			char c = this.target.charAt(index);
			if (c == '(') {
				if (left < this.target.length() / 2) {
					for (StringBuilder sb : rs) {
						sb.append(c);
						dfs(index + 1, left + 1, right);
					}
				}
			} else if (c == ')') {
				if (right >= left) {
					int len = rs.size();
					for (int j = 0; j < len; j++) {
						StringBuilder sb = rs.get(j);
						for (int i = 0; i < sb.length(); i++) {
							char cc = sb.charAt(i);
							if (cc == ')') {
								StringBuilder newSb = new StringBuilder(sb);
								newSb.deleteCharAt(i);
								newSb.append(')');
								rs.add(newSb);
							}
						}
					}
					dfs(index + 1, left, right);
				} else {
					int len = rs.size();
					for (int j = 0; j < len; j++) {
						StringBuilder sb = rs.get(j);
						sb.append(c);
						dfs(index + 1, left, right + 1);
					}
				}
			} else {
				for (StringBuilder sb : rs) {
					sb.append(c);
					dfs(index + 1, left, right);
				}
			}
		}

	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution9().removeInvalidParentheses("(a)())()"));
	}
}

class Solution10 {
	public void solveSudoku(char[][] board) {
		/**
		 * 记录某行，某位数字是否已经被摆放
		 */
		boolean[][] row = new boolean[9][9];
		/**
		 * 记录某列，某位数字是否已经被摆放
		 */
		boolean[][] col = new boolean[9][9];
		/**
		 * 记录某 3x3 宫格内，某位数字是否已经被摆放
		 */
		boolean[][] block = new boolean[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.') {
					int num = board[i][j] - '1';
					row[i][num] = true;
					col[j][num] = true;
					// blockIndex = i / 3 * 3 + j / 3，取整
					block[i / 3 * 3 + j / 3][num] = true;
				}
			}
		}
		dfs(board, row, col, block, 0, 0);
	}

	private boolean dfs(char[][] board, boolean[][] row, boolean[][] col, boolean[][] block, int i, int j) {
		// 找寻空位置
		while (board[i][j] != '.') {
			if (++j >= 9) {
				i++;
				j = 0;
			}
			if (i >= 9) {
				return true;
			}
		}
		for (int num = 0; num < 9; num++) {
			int blockIndex = i / 3 * 3 + j / 3;
			if (!row[i][num] && !col[j][num] && !block[blockIndex][num]) {
				// 递归
				board[i][j] = (char) ('1' + num);
				row[i][num] = true;
				col[j][num] = true;
				block[blockIndex][num] = true;
				if (dfs(board, row, col, block, i, j)) {
					return true;
				} else {
					// 回溯
					row[i][num] = false;
					col[j][num] = false;
					block[blockIndex][num] = false;
					board[i][j] = '.';
				}
			}
		}
		return false;
	}

	private void printBoard(char[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		char[][] board = new char[][]{
				{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
				{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
				{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
				{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
				{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
				{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
				{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
				{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
				{'.', '.', '.', '.', '8', '.', '.', '7', '9'}
		};
		Solution10 solution = new Solution10();
		solution.printBoard(board);
		solution.solveSudoku(board);
		solution.printBoard(board);
	}
}

class Solution11 {
	List<List<String>> rs = new ArrayList<>();
	int n;
	boolean[] col;

	boolean[] hills;
	boolean[] dales;
	StringBuilder disn = new StringBuilder();
	public List<List<String>> solveNQueens(int n) {
		this.n = n;
		this.col = new boolean[n];
		this.hills = new boolean[2 * n - 1];
		this.dales = new boolean[2 * n - 1];
		for (int i = 0; i < n; i++) {
			disn.append(".");
		}
		dfs(0, new ArrayList<>());
		return rs;
	}

	public void dfs(int row, List<String> list) {
		if (row == n) {
			rs.add(new ArrayList<>(list));
		} else {
			for (int i = 0; i < n; i++) {
				if (!this.col[i] && !this.hills[row - i + n - 1] && !this.dales[row + i]) {
					this.disn.setCharAt(i, 'Q');
					list.add(this.disn.toString());
					this.disn.setCharAt(i, '.');

					this.col[i] = true;
					this.hills[row - i + n - 1] = true;  // "hill" diagonals
					this.dales[row + i] = true;

					dfs(row + 1, list);

					this.col[i] = false;
					this.hills[row - i + n - 1] = false;  // "hill" diagonals
					this.dales[row + i] = false;
					list.remove(list.size() - 1);
				}
			}
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution11().solveNQueens(4));
	}
}

class Solution12 {
	int rs = 0;
	int n;
	boolean[] col;

	boolean[] hills;
	boolean[] dales;
	public int solveNQueens(int n) {
		this.n = n;
		this.col = new boolean[n];
		this.hills = new boolean[2 * n - 1];
		this.dales = new boolean[2 * n - 1];
		dfs(0);
		return rs;
	}

	public void dfs(int row) {
		if (row == n) {
			rs++;
		} else {
			for (int i = 0; i < n; i++) {
				if (!this.col[i] && !this.hills[row - i + n - 1] && !this.dales[row + i]) {
					this.col[i] = true;
					this.hills[row - i + n - 1] = true;  // "hill" diagonals
					this.dales[row + i] = true;

					dfs(row + 1);

					this.col[i] = false;
					this.hills[row - i + n - 1] = false;  // "hill" diagonals
					this.dales[row + i] = false;
				}
			}
		}
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution12().solveNQueens(4));
	}
}

class Solution13 {
	String word;
	int wordLen;
	char[][] board;
	boolean[][] used;
	public List<String> findWords(char[][] board, String[] words) {
		List<String> r = new ArrayList<>();
		for (String w : words) {
			if (exist(board, w)) {
				r.add(w);
			}
		}
		return r;
	}
	public boolean exist(char[][] board, String word) {
		this.word = word;
		this.wordLen = word.length();
		this.board = board;
		used = new boolean[board.length][board[0].length];
		List<Integer> index = findFirstWordIndex(word.charAt(0));
		for (Integer i : index) {
			int y = i / board[0].length;
			int x = i - (y * board[0].length);
			if (dfs(x, y, 0)) {
				return true;
			}
		}
		return false;
	}

	public boolean dfs(int x, int y, int len) {
		if (len == wordLen) {
			return true;
		} else {
			// right
			if (x >= 0 && x < this.board[0].length) {
				if (y >= 0 && y < this.board.length) {
					// Utils.log("x %s y %s len %s char %s used %s", x, y, len, board[y][x], used[y][x]);
					if (!used[y][x]) {
						if (board[y][x] == this.word.charAt(len)) {
							used[y][x] = true;
							if (dfs(x + 1, y, len + 1)) {
								return true;
							}
							if (dfs(x - 1, y, len + 1)) {
								return true;
							}
							if (dfs(x, y + 1, len + 1)) {
								return true;
							}
							if (dfs(x, y - 1, len + 1)) {
								return true;
							}
							used[y][x] = false;
						}
					}
				}
			}
			return false;
		}
	}

	public List<Integer> findFirstWordIndex(char c) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			char[] row = board[i];
			for (int j = 0; j < row.length; j++) {
				char w = board[i][j];
				if (w == c) {
					list.add(i * row.length + j);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		char[][] board = {
			{'A','B','C','E'},
			{'S','F','C','S'},
			{'A','D','E','E'}
		};
		Utils.log("%s", new Solution13().exist(board, "ABCB"));
	}
}

class Solution14 {
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		// 先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
		Set<String> wordSet = new HashSet<>(wordList);
		if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
			return 0;
		}
		wordSet.remove(beginWord);

		// 图的广度优先遍历，必须使用的队列和表示是否访问过的 visited （数组，哈希表）
		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int wordLen = beginWord.length();
		// 包含起点，因此初始化的时候步数为 1
		int step = 1;
		while (!queue.isEmpty()) {
			int currentSize = queue.size();
			for (int i = 0; i < currentSize; i++) {
				// 依次遍历当前队列中的单词
				String word = queue.poll();
				char[] charArray = word.toCharArray();

				// 修改每一个字符
				for (int j = 0; j < wordLen; j++) {
					// 一轮以后应该重置，否则结果不正确
					char originChar = charArray[j];
					// Utils.log("word %s char %s", word, originChar);

					for (char k = 'a'; k <= 'z'; k++) {
						if (k == originChar) {
							continue;
						}

						charArray[j] = k;
						String nextWord = String.valueOf(charArray);

						if (wordSet.contains(nextWord)) {
							if (nextWord.equals(endWord)) {
								return step + 1;
							}

							if (!visited.contains(nextWord)) {
								queue.add(nextWord);
								// 注意：添加到队列以后，必须马上标记为已经访问
								visited.add(nextWord);
							}
						}
					}
					// 恢复
					charArray[j] = originChar;
				}
			}
			step++;
		}
		return 0;
	}

	public static void main(String[] args) {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>();
		String[] wordListArray = new String[]{"hot", "dot", "dog", "lot", "log", "hot", "cog", "cot"};
		Collections.addAll(wordList, wordListArray);
		Solution14 solution = new Solution14();
		int res = solution.ladderLength(beginWord, endWord, wordList);
		System.out.println(res);
	}
}

class Solution15 {
	List<List<String>> result = new ArrayList<>();
	String beginWord;
	String endWord;
	Set<String> wordSet;
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		this.beginWord = beginWord;
		this.endWord = endWord;

		this.wordSet = new HashSet<>(wordList);

		if (!wordSet.contains(endWord)) {
			return result;
		}

		Set<String> used = new HashSet<>();
		List<String> list = new ArrayList<>();
		list.add(beginWord);
		dfs(used, list, beginWord);
		return result;
	}

	public void dfs(Set<String> used, List<String> res, String word) {
		int wordLen = word.length();
		char[] charArray = word.toCharArray();
		// 修改每一个字符
		for (int j = 0; j < wordLen; j++) {
			// 一轮以后应该重置，否则结果不正确
			char originChar = charArray[j];
			// Utils.log("word %s char %s", word, originChar);

			for (char k = 'a'; k <= 'z'; k++) {
				if (k == originChar) {
					continue;
				}

				charArray[j] = k;
				String nextWord = String.valueOf(charArray);

				if (wordSet.contains(nextWord)) {
					if (nextWord.equals(endWord)) {
						res.add(nextWord);
						if (res.size() == 5) {
							this.result.add(new ArrayList<>(res));
						}
						res.remove(res.size() - 1);
					}

					if (!used.contains(nextWord)) {
						res.add(nextWord);
						used.add(nextWord);
						dfs(used, res, nextWord);
						used.remove(nextWord);
						res.remove(res.size() - 1);
					}
				}
			}
			// 恢复
			charArray[j] = originChar;
		}
	}

	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		// 先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
		Set<String> wordSet = new HashSet<>(wordList);
		if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
			return 0;
		}
		wordSet.remove(beginWord);

		// 图的广度优先遍历，必须使用的队列和表示是否访问过的 visited （数组，哈希表）
		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int wordLen = beginWord.length();
		// 包含起点，因此初始化的时候步数为 1
		int step = 1;
		while (!queue.isEmpty()) {
			int currentSize = queue.size();
			for (int i = 0; i < currentSize; i++) {
				// 依次遍历当前队列中的单词
				String word = queue.poll();
				char[] charArray = word.toCharArray();

				// 修改每一个字符
				for (int j = 0; j < wordLen; j++) {
					// 一轮以后应该重置，否则结果不正确
					char originChar = charArray[j];
					// Utils.log("word %s char %s", word, originChar);

					for (char k = 'a'; k <= 'z'; k++) {
						if (k == originChar) {
							continue;
						}

						charArray[j] = k;
						String nextWord = String.valueOf(charArray);

						if (wordSet.contains(nextWord)) {
							if (nextWord.equals(endWord)) {
								return step + 1;
							}

							if (!visited.contains(nextWord)) {
								queue.add(nextWord);
								// 注意：添加到队列以后，必须马上标记为已经访问
								visited.add(nextWord);
							}
						}
					}
					// 恢复
					charArray[j] = originChar;
				}
			}
			step++;
		}
		return 0;
	}

	public static void main(String[] args) {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>();
		String[] wordListArray = new String[]{"hot","dot","dog","lot","log","cog"};
		Collections.addAll(wordList, wordListArray);
		Solution15 solution = new Solution15();
		Utils.log("%s", solution.findLadders(beginWord, endWord, wordList));
	}
}

class Solution16 {
	String target;
	Set<String> deadends;

	public int openLock(String[] deadends, String target) {
		Set<String> dead = new HashSet();
		for (String d: deadends) dead.add(d);

		Queue<String> queue = new LinkedList();
		queue.offer("0000");
		queue.offer(null);

		Set<String> seen = new HashSet();
		seen.add("0000");

		int depth = 0;
		while (!queue.isEmpty()) {
			String node = queue.poll();
			if (node == null) {
				depth++;
				if (queue.peek() != null) {
					queue.offer(null);
				}
			} else if (node.equals(target)) {
				return depth;
			} else if (!dead.contains(node)) {
				for (int i = 0; i < 4; ++i) {
					for (int d = -1; d <= 1; d += 2) {
						int y = ((node.charAt(i) - '0') + d + 10) % 10;
						String nei = node.substring(0, i) + ("" + y) + node.substring(i+1);
						if (!seen.contains(nei)) {
							seen.add(nei);
							queue.offer(nei);
						}
					}
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Utils.log("%s", new Solution16().openLock(new String[]{"0201", "0101", "0102", "1212", "2002"}, "0202"));
	}
}

class Solution17 {
	private static final int INF = 1 << 20;
	private Map<String, Integer> wordId; // 单词到id的映射
	private ArrayList<String> idWord; // id到单词的映射
	private ArrayList<Integer>[] edges; // 图的边

	public Solution17() {
		wordId = new HashMap<>();
		idWord = new ArrayList<>();
	}

	public static void main(String[] args) {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>();
		String[] wordListArray = new String[]{"hot","dot","dog","lot","log","cog"};
		Collections.addAll(wordList, wordListArray);
		Utils.log("result %s", new Solution17().findLadders(beginWord, endWord, wordList));
	}

	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		int id = 0;
		// 将wordList所有单词加入wordId中 相同的只保留一个 // 并为每一个单词分配一个id
		for (String word : wordList) {
			if (!wordId.containsKey(word)) {
				wordId.put(word, id++);
				idWord.add(word);
			}
		}
		// 若endWord不在wordList中 则无解
		if (!wordId.containsKey(endWord)) {
			return new ArrayList<>();
		}
		// 把beginWord也加入wordId中
		if (!wordId.containsKey(beginWord)) {
			wordId.put(beginWord, id++);
			idWord.add(beginWord);
		}

		// 初始化存边用的数组
		edges = new ArrayList[idWord.size()];
		for (int i = 0; i < idWord.size(); i++) {
			edges[i] = new ArrayList<>();
		}
		// 添加边
		for (int i = 0; i < idWord.size(); i++) {
			for (int j = i + 1; j < idWord.size(); j++) {
				// 若两者可以通过转换得到 则在它们间建一条无向边
				if (transformCheck(idWord.get(i), idWord.get(j))) {
					edges[i].add(j);
					edges[j].add(i);
				}
			}
		}
		Utils.log("edges %s", Arrays.toString(edges));

		int dest = wordId.get(endWord); // 目的ID
		List<List<String>> res = new ArrayList<>(); // 存答案
		int[] cost = new int[id]; // 到每个点的代价
		for (int i = 0; i < id; i++) {
			cost[i] = INF; // 每个点的代价初始化为无穷大
		}

		// 将起点加入队列 并将其cost设为0
		Queue<ArrayList<Integer>> q = new LinkedList<>();
		ArrayList<Integer> tmpBegin = new ArrayList<>();
		tmpBegin.add(wordId.get(beginWord));
		q.add(tmpBegin);
		cost[wordId.get(beginWord)] = 0;

		// 开始广度优先搜索
		while (!q.isEmpty()) {
			ArrayList<Integer> now = q.poll();
			int last = now.get(now.size() - 1); // 最近访问的点
			if (last == dest) { // 若该点为终点则将其存入答案res中
				ArrayList<String> tmp = new ArrayList<>();
				for (int index : now) {
					tmp.add(idWord.get(index)); // 转换为对应的word
				}
				res.add(tmp);
			} else { // 该点不为终点 继续搜索
				for (int i = 0; i < edges[last].size(); i++) {
					int to = edges[last].get(i);
					// 此处<=目的在于把代价相同的不同路径全部保留下来
					if (cost[last] + 1 <= cost[to]) {
						cost[to] = cost[last] + 1;
						// 把to加入路径中
						ArrayList<Integer> tmp = new ArrayList<>(now); tmp.add(to);
						q.add(tmp); // 把这个路径加入队列
					}
				}
			}
		}
		return res;
	}

	// 两个字符串是否可以通过改变一个字母后相等
	boolean transformCheck(String str1, String str2) {
		int differences = 0;
		for (int i = 0; i < str1.length() && differences < 2; i++) {
			if (str1.charAt(i) != str2.charAt(i)) {
				++differences;
			}
		}
		return differences == 1;
	}
}

class Solution18 {
	List<Integer> rs = new ArrayList<>();

	public List<Integer> inorderTraversal(TreeNode root) {
		if (root != null) {
			inorderTraversal(root.left);
			rs.add(root.val);
			inorderTraversal(root.right);
		}
		return rs;
	}

	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();
		if(root == null) {
			return res;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			if(node.left != null) {
				stack.push(node.left);//和传统先序遍历不一样，先将左结点入栈
			}
			if(node.right != null) {
				stack.push(node.right);//后将右结点入栈
			}
			res.add(0,node.val);                        //逆序添加结点值
		}
		return res;
	}

	public List<Integer> postorderTraversal33(TreeNode root) {//非递归写法
		List<Integer> res = new ArrayList<Integer>();
		if(root == null) {
			return res;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode pre = null;
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode curr = stack.peek();
			if ((curr.left == null && curr.right == null) ||
					(pre != null && (pre == curr.left || pre == curr.right))){
				//如果当前结点左右子节点为空或上一个访问的结点为当前结点的子节点时，当前结点出栈
				res.add(curr.val);
				pre = curr;
				stack.pop();
			} else {
				if (curr.right != null) {
					stack.push(curr.right); //先将右结点压栈
				}
				if (curr.left != null) {
					stack.push(curr.left);   //再将左结点入栈
				}
			}
		}
		return res;
	}

	public List<Integer> inorderTraversal2(TreeNode root) {
		Stack < TreeNode > stack = new Stack < > ();
		TreeNode curr = root;
		while (curr != null || !stack.isEmpty()) {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop();
			rs.add(curr.val);
			curr = curr.right;
		}
		return rs;
	}

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
}

class Solution19 {
	List<List<Integer>> result = new ArrayList<>();
	public List<List<Integer>> levelOrder(Node root) {
		List<Integer> first = new ArrayList<>();
		Node head = new Node(-1, new ArrayList<>() {{add(root);}});
		bfs(head, 0);
		return result;
	}

	public void bfs(Node father, int layer) {
		for (Node n : father.children) {
			if (n != null) {
				if (result.size() - 1 < layer) {
					result.add(new ArrayList<>());
				}
				result.get(layer).add(n.val);
				bfs(n, layer + 1);
			}
		}
	}

	public static void main(String[] args) {
		Node fire = new Node(5, new ArrayList<>());
		Node four = new Node(4, new ArrayList<>());
		Node three = new Node(3, new ArrayList<>());
		Node six = new Node(6, new ArrayList<>());
		Node two = new Node(2, new ArrayList<>());
		Node one = new Node(1, new ArrayList<>());

		List<Node> l1 = new ArrayList<>();
		l1.add(fire);
		l1.add(six);

		List<Node> l2 = new ArrayList<>();
		l2.add(three);
		l2.add(two);
		l2.add(four);

		three.children = l1;
		one.children = l2;

		Utils.log("%s", new Solution19().levelOrder(one));
	}

	static class Node {
		public int val;
		public List<Node> children;

		public Node() {}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	}
}

class Solution20 {
	List<Integer> res = new ArrayList<>();
	public List<Integer> preorder(Node root) {
		List<Integer> list = new LinkedList();
		if (root == null) {
			return list;
		}
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()){
			root = stack.pop();
			list.add(0, root.val);
			int size = root.children.size();
			for (int i = 0; i < size; ++i) {
				stack.push(root.children.get(i));
			}
		}
		return list;
	}

	public void postorder(Node node) {
		Stack<Node> stack = new Stack<>();
		stack.add(node);
		while (!stack.isEmpty()) {
			Utils.log("%s", stack);
			Node tmp = stack.pop();
			stack.push(tmp);
			if (tmp.children.size() == 0) {
				Node n = stack.pop();
				res.add(n.val);
			} else {
				Collections.reverse(tmp.children);
				for (Node n : tmp.children) {
					stack.add(n);
				}
			}
		}
	}

	public static void main(String[] args) {
		Node fire = new Node(5, new ArrayList<>());
		Node four = new Node(4, new ArrayList<>());
		Node three = new Node(3, new ArrayList<>());
		Node six = new Node(6, new ArrayList<>());
		Node two = new Node(2, new ArrayList<>());
		Node one = new Node(1, new ArrayList<>());

		List<Node> l1 = new ArrayList<>();
		l1.add(fire);
		l1.add(six);

		List<Node> l2 = new ArrayList<>();
		l2.add(three);
		l2.add(two);
		l2.add(four);

		three.children = l1;
		one.children = l2;

		Utils.log("%s", new Solution20().preorder(one));
	}


	public void recursive(Node node) {
		if (node != null) {
			res.add(node.val);
			for (Node n : node.children) {
				recursive(n);
			}
		}
	}

	public void iteration(Node node) {
		Stack<Node> stack = new Stack<>();
		stack.add(node);
		while (!stack.isEmpty()) {
			Node tmp = stack.pop();
			res.add(tmp.val);
			Collections.reverse(tmp.children);
			for (Node n : tmp.children) {
				stack.add(n);
			}
		}
	}

	static class Node {
		public int val;
		public List<Node> children;

		public Node() {}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};
}


class Solution21 {
	List<List<Integer>> res = new ArrayList<>();
	Map<Integer, List<location>> map = new HashMap<>();
	int small = Integer.MAX_VALUE;
	int big = Integer.MIN_VALUE;
	public List<List<Integer>> verticalTraversal(TreeNode root) {
		bfs(root, 0, 0);
		// Utils.log("location %s", map);
		for (int i = small; i <= big; i++) {
			List<location> l = map.get(i);
			Collections.sort(l);
			List<Integer> l1 = new ArrayList<>();
			l.forEach(e -> {
				l1.add(e.val);
			});
			res.add(l1);
		}
		return res;
	}

	public void bfs(TreeNode node, int x, int y) {
		if (node != null) {
			if (x < small) {
				small = x;
			}
			if (x > big) {
				big = x;
			}
			location l = new location();
			l.x = x;
			l.y = y;
			l.val = node.val;
			List<location> list = map.getOrDefault(x, new ArrayList<>());
			list.add(l);
			map.put(x, list);

			bfs(node.left, x - 1, y - 1);
			bfs(node.right, x + 1, y - 1);
		}
	}

	public static void main(String[] args) {
		TreeNode node = new TreeNode(3);
		TreeNode node1 = new TreeNode(9);
		TreeNode node2 = new TreeNode(20);
		TreeNode node3 = new TreeNode(15);
		TreeNode node4 = new TreeNode(17);
		node.left = node1;
		node.right = node2;
		node2.left = node3;
		node2.right = node4;
		Utils.log("%s", new Solution21().verticalTraversal(node));
	}

	public class location implements Comparable<location> {
		public int x;
		public int y;
		public int val;

		@Override
		public String toString() {
			return "location{" +
					"x=" + x +
					", y=" + y +
					", val=" + val +
					'}';
		}

		@Override
		public int compareTo(location that) {
			location node1 = this;
			location node2 = that;
			int x1 = node1.x;
			int y1 = node1.y;
			int v1 = node1.val;
			int x2 = node2.x;
			int y2 = node2.y;
			int v2 = node2.val;
			// x小的在前
			if (x1 != x2) return x1 - x2;
			// y大的在前
			if (y1 != y2) return y2 - y1;
			// 节点值小的在前
			return v1 - v2;
		}
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
}

class Solution22 {
	Map<Integer, Integer> map = new HashMap<>();
	int big = Integer.MIN_VALUE;

	public int deepestLeavesSum(TreeNode root) {
		bfs(root, 0);
		int count = map.getOrDefault(big, 0);
		// Utils.log("%s", list
		return count;
	}

	public void bfs(TreeNode node, int y) {
		if (node != null) {
			if (y > big) {
				big = y;
			}
			int count = map.getOrDefault(y, 0);
			count += node.val;
			map.put(y, count);

			bfs(node.left,y + 1);
			bfs(node.right, y + 1);
		}
	}

	public class location {
		public int x;
		public int y;
		public int val;

		@Override
		public String toString() {
			return "location{" +
					"x=" + x +
					", y=" + y +
					", val=" + val +
					'}';
		}
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}


}

class Solution23 {
	public boolean isSameTree(TreeNode p, TreeNode q) {
		return iteration(p, q);
	}

	public boolean iteration(TreeNode p, TreeNode q) {
		if (p != null && q != null) {
			if (p.val != q.val) {
				return false;
			} else {
				if (!iteration(p.left, q.left)) {
					return false;
				}

				if (!iteration(p.right, q.right)) {
					return false;
				}
				return true;
			}
		} else if (p == null && q == null) {
			return true;
		} else {
			return false;
		}
	}

	int big = 0;
	public int maxDepth(TreeNode root) {
		iteration2(root, 1);
		return big;
	}

	public void iteration2(TreeNode node, int y) {
		if (node != null) {
			if (y > big) {
				big = y;
			}
			iteration2(node.left, y + 1);
			iteration2(node.right, y + 1);
		}
	}

	public boolean isBalanced(TreeNode root) {
		int left = maxDepth(root.left);
		big = 0;
		int right = maxDepth(root.right);
		return Math.abs(left - right) < 2;
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
}

class Solution24 {
	// Recursively obtain the height of a tree. An empty tree has -1 height
	private int height(TreeNode root) {
		// An empty tree has height -1
		if (root == null) {
			return -1;
		}
		return 1 + Math.max(height(root.left), height(root.right));
	}

	public boolean isBalanced(TreeNode root) {
		// An empty tree satisfies the definition of a balanced tree
		if (root == null) {
			return true;
		}

		// Check if subtrees have height within 1. If they do, check if the
		// subtrees are balanced
		return Math.abs(height(root.left) - height(root.right)) < 2
				&& isBalanced(root.left)
				&& isBalanced(root.right);
	}

	public int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}

		if ((root.left == null) && (root.right == null)) {
			return 1;
		}

		int min_depth = Integer.MAX_VALUE;
		if (root.left != null) {
			min_depth = Math.min(minDepth(root.left), min_depth);
		}
		if (root.right != null) {
			min_depth = Math.min(minDepth(root.right), min_depth);
		}

		return min_depth + 1;
	}

	// public boolean isSubtree(TreeNode s, TreeNode t) {
	// 	if (s == null && t == null) {
	// 		return true;
	// 	}
	// 	if (s != null && t != null) {
	// 		if (s.val == t.val) {
	// 			return (isSubtree(s.left, t.left) && isSubtree(s.right, t.right));
	// 		} else {
	// 			return isSubtree(s.left, t) || isSubtree(s.right, t);
	// 		}
	// 	}
	// 	return false;
	// }

	public boolean isSubtree(TreeNode s, TreeNode t) {
		if (t == null) {
			return true;   // t 为 null 一定都是 true
		}
		if (s == null) {
			return false;  // 这里 t 一定不为 null, 只要 s 为 null，肯定是 false
		}
		return isSubtree(s.left, t) || isSubtree(s.right, t) || isSameTree(s,t);
	}

	public boolean isSameTree(TreeNode s, TreeNode t){
		if (s == null && t == null) {
			return true;
		}
		if (s == null || t == null) {
			return false;
		}
		if (s.val != t.val) {
			return false;
		}
		return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
	}
	// 给出一个整数数组 A 和一个查询数组 queries。
	//
	// 对于第 i 次查询，有 val = queries[i][0], index = queries[i][1]，
	// 我们会把 val 加到 A[index] 上。然后，第 i 次查询的答案是 A 中偶数值的和。
	//
	// （此处给定的 index = queries[i][1] 是从 0 开始的索引，每次查询都会永久修改数组 A。）
	//
	// 返回所有查询的答案。你的答案应当以数组 answer 给出，answer[i] 为第 i 次查询的答案。
	// 输入：A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
	// 输出：[8,6,2,4]
	// 解释：
	// 开始时，数组为 [1,2,3,4]。
	// 将 1 加到 A[0] 上之后，数组为 [2,2,3,4]，偶数值之和为 2 + 2 + 4 = 8。
	// 将 -3 加到 A[1] 上之后，数组为 [2,-1,3,4]，偶数值之和为 2 + 4 = 6。
	// 将 -4 加到 A[0] 上之后，数组为 [-2,-1,3,4]，偶数值之和为 -2 + 4 = 2。
	// 将 2 加到 A[3] 上之后，数组为 [-2,-1,3,6]，偶数值之和为 -2 + 6 = 4。

	public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
		int S = 0;
		for (int x : A) {
			if (x % 2 == 0) {
				S += x;
			}
		}

		int[] ans = new int[queries.length];

		for (int i = 0; i < queries.length; ++i) {
			int val = queries[i][0], index = queries[i][1];
			if (A[index] % 2 == 0) {
				S -= A[index];
			}
			A[index] += val;
			if (A[index] % 2 == 0) {
				S += A[index];
			}
			ans[i] = S;
		}

		return ans;
	}


	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
}

class Solution25 {
	HashMap<Integer, List<Integer>> map = new HashMap<>();
	int max = Integer.MIN_VALUE;

	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> rs = new ArrayList<>();
		iteration(root, 0);
		for (int i = 0; i <= max; i++) {
			rs.add(map.get(i));
		}
		return rs;
	}

	public void iteration(TreeNode node, int layer) {
		if (node != null) {
			if (layer > max) {
				max = layer;
			}

			List<Integer> list = map.getOrDefault(layer, new ArrayList<>());
			list.add(node.val);
			map.put(layer, list);

			iteration(node.left, layer + 1);
			iteration(node.right, layer + 1);
		}
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}
}

class Solution26 {
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordsSet = new HashSet<>(wordList);
		if (!wordList.contains(endWord)) {
			return 0;
		}
		HashMap<String, List<String>> allComDic = new HashMap<>();
		wordList.forEach(s -> {
			int len = s.length();
			for (int i = 0; i < len; i++) {
				String newWord = s.substring(0, i) + "*" + s.substring(i + 1, len);
				List<String> list = allComDic.getOrDefault(newWord, new ArrayList<>());
				list.add(s);
				allComDic.put(newWord, list);
			}
		});
		// Utils.log("%s", allComDic);
		Queue<String> queue = new LinkedList<>();
		queue.offer(beginWord);

		Set<String> visited = new HashSet<>();
		visited.add(beginWord);

		int wordLen = beginWord.length();
		int step = 1;
		while (!queue.isEmpty()) {
			int currentSize = queue.size();
			for (int i = 0; i < currentSize; i++) {
				String word = queue.poll();
				for (int j = 0; j < wordLen; j++) {
					String s = word;
					String comWord = s.substring(0, j) + "*" + s.substring(j + 1, wordLen);
					List<String> list = allComDic.getOrDefault(comWord, new ArrayList<>());
					for (String nextWord : list) {
						if (nextWord.equals(word)) {
							continue;
						}

						if (nextWord.equals(endWord)) {
							// Utils.log("step %s", step + 1);
							return step + 1;
						} else {
							if (!visited.contains(nextWord)) {
								queue.add(nextWord);
								visited.add(nextWord);
							}
						}
					}
					// Utils.log("newWord %s nextWord %s queue %s visited %s", newWord, list, queue, visited);
				}
			}
			step++;
		}
		// Utils.log("step %s", step);
		return 0;
	}

	public static void main(String[] args) {
		String beginWord = "hit";
		String endWord = "cog";
		List<String> wordList = new ArrayList<>();
		String[] wordListArray = new String[]{"hot","dot","tog","cog"};
		Collections.addAll(wordList, wordListArray);
		new Solution26().ladderLength(beginWord, endWord, wordList);
	}
}