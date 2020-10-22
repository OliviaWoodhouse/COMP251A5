package A5;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class islands {
	private ArrayList<Character> satellite = new ArrayList<Character>();
	private Integer width;
	private Integer height;
	
	islands(ArrayList<Character> s, int w, int h) {
		this.width = w;
		this.height = h;
		for (int i=0;i<s.size();i++) {
			this.satellite.add(s.get(i));
		}
	}
	
	public static int findIsland(islands island) {
		int numIslands = 0;
		ArrayList<Character> tempSatellite = new ArrayList<Character>();
		for (int i=0;i<island.satellite.size();i++) {
			tempSatellite.add(island.satellite.get(i));
		}
		int iterator = 0;
		while (tempSatellite.contains('-')) {
			if (tempSatellite.get(iterator)=='-') {
				DFS(tempSatellite, iterator, island.width);
				numIslands++;
				iterator++;
			} else {
				iterator++;
			}
		}
		return numIslands;
	}
	
	public static void DFS(ArrayList<Character> satellite, int iterator, int width) {
		satellite.set(iterator, '#');
		int tempIterator = iterator+width;
		while((tempIterator<satellite.size())&&(satellite.get(tempIterator)=='-')) {
			DFS(satellite,tempIterator,width);
		}
		int originalIteratorValue = iterator;
		iterator++;
		int onLine = iterator%width;
		while((iterator<satellite.size())&&(onLine<width)&&(satellite.get(iterator)=='-')) {
			tempIterator = iterator+width;
			while((tempIterator<satellite.size())&&(satellite.get(tempIterator)=='-')) {
				DFS(satellite, tempIterator, width);
			}
			satellite.set(iterator, '#');
			iterator++;
			onLine++;
		}
		iterator = originalIteratorValue-1;
		onLine = iterator%width;
		while((iterator>=0)&&(onLine>=0)&&(satellite.get(iterator)=='-')) {
			tempIterator = iterator+width;
			while((tempIterator<satellite.size())&&(satellite.get(tempIterator)=='-')) {
				DFS(satellite, tempIterator, width);
			}
			satellite.set(iterator, '#');
			iterator--;
			onLine--;
		}
	}
	
	public static ArrayList<islands> readFromFile(String file) throws RuntimeException{
		ArrayList<islands> allProblems = new ArrayList<islands>();
		try {
			Scanner f = new Scanner(new File(file));
			String line1 = f.nextLine();
			int numProblems = Integer.parseInt(line1);
			for (int i=0;i<numProblems;i++) {
				String infoLine[] = f.nextLine().split("\\s+");
				int h = Integer.parseInt(infoLine[0]);
				int w = Integer.parseInt(infoLine[1]);
				ArrayList<Character> c = new ArrayList<Character>();
				for (int j=0;j<h;j++) {
					String satelliteLine = f.nextLine();
					for (int k=0;k<w;k++) {
						c.add(satelliteLine.charAt(k));
					}
				}
				islands tempIsland = new islands(c,w,h);
				allProblems.add(tempIsland);
			}
			
			
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
		return allProblems;
	}
	
	public static void writeToFile(ArrayList<Integer> outputInfo) throws RuntimeException{
		try {
			File file = new File("testIslands_solution.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (int i=0;i<outputInfo.size();i++) {
				int temp = outputInfo.get(i);
				String tempString = temp+"\n";
				writer.write(tempString);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Problem writing to file!");
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		String file = "testIslands.txt";
		ArrayList<islands> allProblems = readFromFile(file);
		ArrayList<Integer> outputInfo = new ArrayList<Integer>();
		for (int i=0;i<allProblems.size();i++) {
			int numIslands = findIsland(allProblems.get(i));
			outputInfo.add(numIslands);
		}
		writeToFile(outputInfo);
	}
}
