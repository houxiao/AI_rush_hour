package rushHour;

import java.io.File;
import java.io.FileNotFoundException;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("./src/easy1.txt");
		
		System.out.print("IDDFS: ");
		IDDFS iddfs = new IDDFS();
		iddfs.init(f);
		iddfs.solve();
		System.out.println();
		
		System.out.print("SMA*:  ");
        SMA sma = new SMA();
        sma.init(f);
        sma.Solve();
	}
}
