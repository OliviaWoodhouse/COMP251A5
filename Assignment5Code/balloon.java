package A5;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

class singleBalloon{
	public Integer index;
	public Integer height;
	public int[] neighbors;	//neighbors refer to any balloons that are to the right and at H-1 of current balloon
	
	singleBalloon(int i, int h,int[] n){
		this.index = i;
		this.height = h;
		this.neighbors = n; //neighbors will contain the index of each neighbor (NOT the height)
	}
}

public class balloon{
	private Integer count = 0;
	private ArrayList<singleBalloon> balloons = new ArrayList<singleBalloon>();
	
	balloon() {
	}
	
	balloon(int c, int[] h) {
		this.count = c;
		ArrayList<singleBalloon> b = new ArrayList<singleBalloon>();
		for (int i=0;i<c-1;i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j=i+1;j<c;j++) {
				if (h[j]==h[i]-1) {
					temp.add(j);
				}
			}
			int[] tempNeighbors = new int[temp.size()];
			for (int j=0;j<tempNeighbors.length;j++) {
				tempNeighbors[j] = temp.get(j);
			}
			singleBalloon currentBalloon = new singleBalloon(i, h[i],tempNeighbors);
			b.add(currentBalloon);
		}
		int i = c-1;
		int[] tempNeighbors = new int[0];
		singleBalloon currentBalloon = new singleBalloon(i,h[i],tempNeighbors);
		b.add(currentBalloon);
		this.balloons = b;
	}
	
	public static int solve(balloon problem) {
		int numArrows = 0;
		ArrayList<singleBalloon> alreadyPopped = new ArrayList<singleBalloon>();
		int iterator = 0;
		while (alreadyPopped.size()!=problem.count) {
			singleBalloon currentBalloon = problem.balloons.get(iterator);
			if (alreadyPopped.contains(currentBalloon)) {
				iterator++;
				continue;
			}
			if (currentBalloon.neighbors.length == 0) {
				numArrows++;
				alreadyPopped.add(currentBalloon);
				iterator++;
				continue;
			} else {
				numArrows++;
				alreadyPopped.add(currentBalloon);
				int alreadyUsed = 0;
				singleBalloon tempBalloon = problem.balloons.get(currentBalloon.neighbors[alreadyUsed]);
				while (alreadyPopped.contains(tempBalloon)) {
					alreadyUsed++;
					if (alreadyUsed>=currentBalloon.neighbors.length) {
						break;
					}
					tempBalloon = problem.balloons.get(currentBalloon.neighbors[alreadyUsed]);
				}
				if (alreadyUsed>=currentBalloon.neighbors.length) {
					iterator++;
					continue;
				}
				boolean hasNeighbors = true;
				while (hasNeighbors) {
					currentBalloon = problem.balloons.get(currentBalloon.neighbors[alreadyUsed]);
					alreadyPopped.add(currentBalloon);
					if (currentBalloon.neighbors.length == 0) {
						hasNeighbors = false;
					} else {
						alreadyUsed = 0;
						tempBalloon = problem.balloons.get(currentBalloon.neighbors[alreadyUsed]);
						while (alreadyPopped.contains(tempBalloon)) {
							alreadyUsed++;
							if (alreadyUsed>=currentBalloon.neighbors.length) {
								break;
							}
							tempBalloon = problem.balloons.get(currentBalloon.neighbors[alreadyUsed]);
						}
						if (alreadyUsed>=currentBalloon.neighbors.length) {
							hasNeighbors = false;
						}
					}
				}
				iterator++;
			}
		}
		return numArrows;
	}
	
	public static ArrayList<balloon> readFromFile(String file) throws RuntimeException {
		ArrayList<balloon> info = new ArrayList<balloon>();
		try {
			Scanner f = new Scanner(new File(file));
			String line1 = f.nextLine();
			int iterator = Integer.parseInt(line1);
			String[] line2 = f.nextLine().split("\\s+");
			int i = 0;
			while (f.hasNextLine() && i<iterator) {
				int currCount = Integer.parseInt(line2[i]);
				int[] heights = new int[currCount];
				String[] line = f.nextLine().split("\\s+");
				for (int j=0;j<currCount;j++) {
					heights[j] = Integer.parseInt(line[j]);
				}
				balloon currentProblem = new balloon(currCount, heights);
				info.add(currentProblem);
				i++;
			}
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
		return info;
	}
	
	public static void writeToFile(ArrayList<Integer> outputInfo) throws RuntimeException {
		try {
			File file = new File("testBalloons_solution.txt");
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
	
	public static void main(String[] args){
		String file = "testBalloons.txt";
		ArrayList<balloon> inputInfo = readFromFile(file);
		ArrayList<Integer> outputInfo = new ArrayList<Integer>();
		for (int i=0;i<inputInfo.size();i++) {
			int numArrows = solve(inputInfo.get(i));
			outputInfo.add(numArrows);
		}
		writeToFile(outputInfo);
	}
}

















