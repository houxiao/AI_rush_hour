package rushHour;

import java.io.*;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.*;

public class SMA {
	private class SMap extends Map {
		private int dep;
		private int func;
		private int count = 0;
		private SMap prev = null;
		private String path;

		private void set(String tmp, int depth) {
			set(tmp);
			dep = depth;
			func = dep + heuristic();
		}

		private void copy(SMap s){
			dep=s.dep;
			path=s.path;
			path=s.path;
		}
		
		private int heuristic() {
			int heuristic = 6 - iceTruck;
			for (int i = iceTruck + 2; i < 6; i++)
				if (board[2][i] == 1)
					heuristic += 1;
			return heuristic;
		}
		
		private SMap fidnNextNode() {
			if (count == -1) return null;
	        int index = ++count;
	        SMap node = this;
	        for (int i = 0; i < node.carNumber; i++) {
	            char[] c = node.info.substring(i * 5, i * 5 + 4).toCharArray();
	            int leng = 2;
	            if (c[0] == 'B') leng = 3;
	            int x = c[2] - 'A', y = c[3] - '1';
	            if (c[1] == 'H') {
	                if (y + leng < 6 && node.board[x][y + leng]==0) {
	                    if (--index == 0) {
	                    	SMap nextnode = new SMap();
	                        StringBuffer nextstring = new StringBuffer(node.info);
	                        nextstring.setCharAt(5 * i + 3, (char) ('1' + y + 1));
	                        nextnode.set(nextstring.toString(), dep + 1);
	                        nextnode.path = (char) (i + '1') + "->1";
	                        nextnode.prev = this;
	                        return nextnode;
	                    }
	                }
	                if (y - 1 >= 0 && node.board[x][y - 1]==0) {
	                    if (--index == 0) {
	                    	SMap nextnode = new SMap();
	                        StringBuffer nextstring = new StringBuffer(node.info);
	                        nextstring.setCharAt(5 * i + 3, (char) ('1' + y - 1));
	                        nextnode.set(nextstring.toString(), dep + 1);
	                        nextnode.path = (char) (i + '1') + "<-1";
	                        nextnode.prev = this;
	                        return nextnode;
	                    }
	                }
	            } else if (c[1] == 'V') {
	                if (x + leng < 6 && node.board[x + leng][y]==0) {
	                    if (--index == 0) {
	                    	SMap nextnode = new SMap();
	                        StringBuffer nextstring = new StringBuffer(node.info);
	                        nextstring.setCharAt(5 * i + 2, (char) ('A' + x + 1));
	                        nextnode.set(nextstring.toString(), dep + 1);
	                        nextnode.path = (char) (i + '1') + "↓1";
	                        nextnode.prev = this;
	                        return nextnode;
	                    }
	                }
	                if (x - 1 >= 0 && node.board[x - 1][y]==0) {
	                    if (--index == 0) {
	                    	SMap nextnode = new SMap();
	                        StringBuffer nextstring = new StringBuffer(node.info);
	                        nextstring.setCharAt(5 * i + 2, (char) ('A' + x - 1));
	                        nextnode.set(nextstring.toString(), dep + 1);
	                        nextnode.path = (char) (i + '1') + "↑1";
	                        nextnode.prev = this;
	                        return nextnode;
	                    }
	                }
	            }
	        }
	        count = -1;
	        return null;	
		}	
	}
	
	private static int MAXQUEUE = 500;
    private int nodeVisited = 0;
    private SMap map;
    private HashSet<SMap> set = new HashSet<>();
    private Queue<SMap> candidate = new PriorityQueue<>(MAXQUEUE, new Comparator<SMap>() {
        @Override
        public int compare(SMap smap1, SMap smap2) {
            return smap1.func - smap2.func;
        }
    });

    public void init(File file) throws FileNotFoundException {
        @SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
        SMap newMap = new SMap();
        newMap.set(scan.nextLine(), 0);
        map = newMap;
        candidate.add(newMap);
    }

    public void Solve() {
        while (!candidate.isEmpty()) {
        	SMap selectedNode = candidate.peek();
        	nodeVisited++;
            if (selectedNode.success()) {
            	StringBuilder str = new StringBuilder(selectedNode.path);
                SMap preNode = selectedNode.prev;
                while (preNode.prev != null) {
                    str.insert(0, preNode.path + ", ");
                    preNode = preNode.prev;
                }
                System.out.println("["+str.append(", 1->1, 1->1").toString()+"]");
                System.out.println("Visited nodes " + nodeVisited);
                return;
            }
            SMap nextNode = selectedNode.fidnNextNode();
            if (nextNode == null) {
                set.add(candidate.poll());continue;
            }
            if (set.contains(nextNode)) {
                for (Iterator<SMap> iterator = set.iterator(); iterator.hasNext();) {
                	SMap candNode = iterator.next();
                    if (candNode.equals(nextNode) && candNode.dep > nextNode.dep) {
                    	candNode.copy(nextNode);
                    	candNode.prev = selectedNode;
                    	candNode.count = 0;
                        candidate.add(candNode);
                        iterator.remove();
                    }
                }
            }else if (candidate.contains(nextNode)) {
                for (Iterator<SMap> iterator = candidate.iterator(); iterator.hasNext(); ) {
                	SMap candNode = iterator.next();
                    if (candNode.equals(nextNode) && candNode.dep > nextNode.dep) {
                    	candNode.copy(nextNode);
                    	candNode.prev = selectedNode;
                    	candNode.count = 0;
                    }
                }
            }else candidate.add(nextNode);
        }
        System.out.println("failed");
    }
}
