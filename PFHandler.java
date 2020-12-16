package assignment1;

import java.util.*;

public class PFHandler {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		String type = keyboard.nextLine();
		int frames = keyboard.nextInt();
		String input = keyboard.nextLine();
		String[] splitter = new String[2];
		ArrayList<Integer> pid = new ArrayList<Integer>();
		ArrayList<Integer> frame = new ArrayList<Integer>();
		int c = 0;
		input = keyboard.nextLine();
		while (input.length() != 0) {
			if (input.length() == 0)
				break;
			splitter = input.split(" ");
			pid.add(Integer.parseInt(splitter[0]));
			frame.add(Integer.parseInt(splitter[1]));

			c += 1;
			input = keyboard.nextLine();

		}
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> dupindexes = new ArrayList<Integer>();
		for (int i = 0; i < c; i++) {

			boolean exist = true;
			if (map.containsKey(pid.get(i))) {
				ArrayList<Integer> key = map.get(pid.get(i));
				for (int j = 0; j < key.size(); j++) {
					if (key.get(j) == frame.get(i)) {
						dupindexes.add(i);
						exist = false;
						break;
					}
				}
				if (exist)
					key.add(frame.get(i));

			} else {
				ArrayList<Integer> arrlist = new ArrayList<Integer>();
				arrlist.add(frame.get(i));
				map.put(pid.get(i), arrlist);
			}

		}

		System.out.println("PID Page Faults");
		ArrayList<Integer> numpids = new ArrayList<Integer>();
		for (int i = 0; i < c; i++) {
			boolean exist = true;
			if (numpids.size() == 0)
				numpids.add(pid.get(i));
			for (int j = 0; j < numpids.size(); j++) {
				if (numpids.get(j) == pid.get(i)) {
					exist = false;
					break;
				}
			}
			if (exist)
				numpids.add(pid.get(i));

		}
		ArrayList<Integer> original = makeDeepCopyInteger(numpids);
		Collections.sort(numpids);
		for (int i = 0; i < numpids.size(); i++) {
			ArrayList<Integer> key = map.get(numpids.get(i));
			System.out.println(numpids.get(i) + " " + key.size());

		}

		if (type.equals("LOCAL")) {
			HashMap<Integer, ArrayList<Integer>> lmap = new HashMap<Integer, ArrayList<Integer>>();
			int frame_count = 0;
			for (int i = 0; i < original.size(); i++) {
				ArrayList<Integer> key = map.get(original.get(i));
				ArrayList<Integer> newp = new ArrayList<Integer>();
				for (int j = 0; j < key.size(); j++) {
					newp.add(frame_count);
					frame_count += 1;
				}
				lmap.put(original.get(i), newp);
			}

			for (int i = 0; i < numpids.size(); i++) {
				System.out.println("Process " + numpids.get(i) + " page table");
				System.out.println("P# F#");
				ArrayList<Integer> framecounts = lmap.get(numpids.get(i));
				ArrayList<Integer> pages = map.get(numpids.get(i));
				for (int j = 0; j < pages.size(); j++) {
					System.out.println(pages.get(j) + " " + framecounts.get(j));
				}
			}

		} else {

			boolean[] checker = new boolean[c];
			for (int i = 0; i < c; i++) {
				checker[i] = true;
			}
			for (int i = 0; i < dupindexes.size(); i++) {
				int index = dupindexes.get(i);
				checker[index] = false;
			}
			int pagecount = 0;
			for (int i = 0; i < numpids.size(); i++) {
				ArrayList<Integer> pages = map.get(numpids.get(i));

				pagecount += pages.size();
			}
			if (pagecount <= frames) {
				for (int x = 0; x < numpids.size(); x++) {
					System.out.println("Process " + numpids.get(x) + " page table");
					System.out.println("P# F#");
					int framecont = 0;
					for (int i = 0; i < c; i++) {
						if (checker[i] == true) {
							if (pid.get(i) == numpids.get(x)) {
								System.out.println(frame.get(i) + " " + framecont);
							}
							framecont += 1;
						}

					}
				}
			} else {
				for (int x = 0; x < numpids.size(); x++) {
					System.out.println("Process " + numpids.get(x) + " page table");
					System.out.println("P# F#");
					int framecont = 0;
					for (int i = frames; i < c; i++) {
						if (checker[i] == true) {
							if (pid.get(i) == numpids.get(x)) {
								System.out.println(frame.get(i) + " " + framecont);
							}
							framecont += 1;
						}

					}
				}

			}
		}
	}

//Previous code found to create deepcopy array. Online Java and done in 264
	private static ArrayList<Integer> makeDeepCopyInteger(ArrayList<Integer> old) {
		ArrayList<Integer> copy = new ArrayList<Integer>(old.size());
		for (Integer i : old) {
			copy.add(new Integer(i));
		}
		return copy;
	}

}
