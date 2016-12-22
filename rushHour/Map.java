package rushHour;

import java.util.Objects;

public class Map {
	public int[][] board;
	public int iceTruck;
	public int carNumber;
	public String info;

	public void set(String s) {
		board = new int[6][6];
		String[] S = s.split(",");
		carNumber = S.length;
		info = s;
		for (String str : S) {
			if (s == null)
				break;
			switch (str.charAt(0)) {
			case 'I':
				iceTruck = str.charAt(3) - '1';
				board[2][iceTruck] = 1;
				board[2][iceTruck + 1] = 1;
				break;
			case 'C':
				for (int i = 0; i < 2; i++) {
					if (str.charAt(1) == 'H')
						board[(int) (str.charAt(2) - 'A')][(int) (str.charAt(3) - '1') + i] = 1;
					else
						board[(int) (str.charAt(2) - 'A') + i][(int) (str.charAt(3) - '1')] = 1;
				}
				break;
			case 'B':
				for (int i = 0; i < 3; i++) {
					if (str.charAt(1) == 'H')
						board[(int) (str.charAt(2) - 'A')][(int) (str.charAt(3) - '1') + i] = 1;
					else
						board[(int) (str.charAt(2) - 'A') + i][(int) (str.charAt(3) - '1')] = 1;
				}
				break;
			}
		}
	}

	public boolean success() {
		while (iceTruck != 4)
			return false;
		return true;
	}

	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())return false;
		Map other = (Map) obj;
		return info.equals(other.info);
	}
	
	public int hashCode() {
		return Objects.hash(info);
	}
}
