package rushHour;

import java.io.*;
import java.util.*;

public class IDDFS {
	public static int MAXDEPTH = 40;
	public HashMap<Map, Integer> hashMap;
	public Stack<String> pathStack = new Stack<>();
	Map map;
	public int nodeVisited=0;

	public void init(File file) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		Map newMap = new Map();
		newMap.set(scan.nextLine());
		map = newMap;
	}

	public boolean Search(Map node, int depth) {
		nodeVisited++;
		if(depth>0) {
			for (int i = 0; i < node.carNumber; i++) {	
				char[] c = node.info.substring(i * 5, i * 5 + 4).toCharArray();
				int len = 2;
				if (c[0] == 'B')
					len = 3;
				int x = c[2] - 'A'; 
				int y = c[3] - '1';
				if (c[1] == 'H') {
					if (y - 1 >= 0 && node.board[x][y - 1] ==0) {
						Map nextNode = new Map();
						StringBuffer strBuffer = new StringBuffer(node.info);
						strBuffer.setCharAt(5 * i + 3, (char) (y-1+'1'));
						nextNode.set(strBuffer.toString());
						Integer old = hashMap.get(nextNode);
						if (old == null || old < depth) {
							hashMap.put(nextNode, depth);
							pathStack.add((char) (i + '1') + "<-1");
							if (Search(nextNode, depth - 1))
								return true;
							pathStack.pop();
						}
					}
					if (y + len < 6 && node.board[x][y + len] ==0) {
						Map nextNode = new Map();
						StringBuffer strBuffer = new StringBuffer(node.info);
						strBuffer.setCharAt(5 * i + 3, (char) (y+1+'1'));
						nextNode.set(strBuffer.toString());
						Integer old = hashMap.get(nextNode);
						if (old == null || old < depth) {
							hashMap.put(nextNode, depth);
							pathStack.add((char) (i + '1') + "->1");
							if (Search(nextNode, depth - 1))
								return true;
							pathStack.pop();
						}
					}
				} 
				else{
					if (x - 1 >= 0 && node.board[x - 1][y] ==0) {
						Map nextNode = new Map();
						StringBuffer strBuffer = new StringBuffer(node.info);
						strBuffer.setCharAt(5 * i + 2, (char) (x-1+'A'));
						nextNode.set(strBuffer.toString());
						Integer old = hashMap.get(nextNode);
						if (old == null || old < depth) {
							hashMap.put(nextNode, depth);
							pathStack.add((char) (i + '1') + "↑1");
							if (Search(nextNode, depth - 1))
								return true;
							pathStack.pop();
						}
					}
					if (x + len < 6 && node.board[x + len][y] ==0) {
						Map nextNode = new Map();
						StringBuffer strBuffer = new StringBuffer(node.info);
						strBuffer.setCharAt(5 * i + 2, (char) (x+1+'A'));
						nextNode.set(strBuffer.toString());
						Integer old = hashMap.get(nextNode);
						if (old == null || old < depth) {
							hashMap.put(nextNode, depth);
							pathStack.add((char) (i + '1') + "↓1");
							if (Search(nextNode, depth - 1))
								return true;
							pathStack.pop();
						}
					}
				}
			}
		}
		if (depth == 0 && node.success()) return true;
		return false;
	}

	public int solve() {
		if (map == null)
			return 0;
		for (int depth = 0; depth < MAXDEPTH; depth++) {
			hashMap = new HashMap<>();
			hashMap.put(map, depth);
			if (Search(map, depth)) {
				pathStack.add("1->1");
				pathStack.add("1->1");
				System.out.println(new StringBuilder(pathStack.toString()));
				System.out.println("Visited nodes " + nodeVisited);
				return depth;
			}
		}
		System.out.print("failed");
		return 0;
	}

}
