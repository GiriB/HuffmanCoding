package huffman.coding;

import java.util.ArrayList;
import java.util.Scanner;

class Node {

	Node left;
	Node right;
	int freq;
	char info;
	char type;

	Node() {
		type = 'N';
		info = '\0';
		freq = 0;
	}
}

public class HuffmanCoding{
	Node root;
	String encoded;
	String decoded;
	int FOUND;
	int DONE;

	Huffmkan_ver_3() {
		root = null;
		encoded = "";
		FOUND = 0;
		decoded = "";
		DONE = 0;
	}

	void display_tree(Node ROOT) {
		if (ROOT != null) {
			display_tree(ROOT.left);

			if (isValid(ROOT))
				System.out.print(ROOT.info + " " + ROOT.freq);// + " - " +
																// ROOT.freq);

			display_tree(ROOT.right);

		}
	}

	void decoding(String S, Node ROOT) {

		if (ROOT != null) {
			if (S.length() >= 1 && DONE == 0) {
				char ch = S.charAt(0);

				if (root.type == 'R') {
					S += root.info;
					decoded += S;
					decoding(S.substring(1), root);
				} else if (isValid(ROOT)) {
					decoded += ROOT.info;
					decoding(S, root);
				} else if (ch == '1') {
					decoding(S.substring(1), ROOT.right);
				} else if (ch == '0') {
					decoding(S.substring(1), ROOT.left);
				}

			} else if (isValid(ROOT)) {
				decoded += ROOT.info;
				DONE = 1;
			}

			// } else {
			// if (ch == '0' && isValid(ROOT.left)) {
			// {
			// decoded += ROOT.right.info;
			// }// System.out.println("Went OUT OF TREE !!");
			// } else if (ch == '1') {
			// decoded += ROOT.right.info;
			// } else {
			// System.out.println("Invalid ! Input!");
			// }
			//
			// }
		}

		else {
			System.out.println("Went OUT OF TREE !!");
		}

	}

	String Decode_String(String S) {
		
		String dec = "";
		if (S.length() != 0) {
			decoding(S, root);
			dec = decoded;
			reset();
		} else {
			System.out.println("Invalid input to decode !!");
			reset();
			return null;
		}
		return dec;

	}

	void encoding(char element, Node ROOT, String S) {
		// System.out.println("Inside encoding");
		if (root.type == 'R' && root.info == element) {
			// there is only one element
			S += '1';
			encoded = S;
			FOUND = 1;
			return;
		}

		if (ROOT != null && FOUND == 0) {

			// System.out.println(ROOT.info + " " + S);

			if (isValid(ROOT) && ROOT.info == element) {
				// System.out.println("assigning" + S);
				encoded = S;
				FOUND = 1;
			}

			encoding(element, ROOT.right, S + '1');
			encoding(element, ROOT.left, S + '0');

		}
	}

	// encodes and return the code as a string
	String Encode_Element(char element) {
		String iffound = "";
		reset();

		// finding the element and encoding
		encoding(element, root, "");

		if (FOUND == 1) // if the element is found in the tree
		{
			// System.out.println("FOUND" + encoded);
			iffound = encoded;
			reset();
			return iffound;
		} else {
			System.out.println("Not FOUND");
			reset();
			return null; // otherwise return null
		}
	}

	// resets the value of FOUND and encoded
	private void reset() {
		// TODO Auto-generated method stub
		FOUND = 0;
		DONE = 0;
		encoded = "";
		decoded = "";
	}

	// if info is a Valid char
	boolean isValid(Node ROOT) {
		// TODO Auto-generated method stub
		return ROOT.type == 'L';
	}

	void create_tree_onelement(Node t) {
		// TODO Auto-generated method stub
		t.type = 'R'; // only one element
		root = t;

	}

	Node create_tree(Node t1, Node t2) {
		Node t = new Node();
		// System.out.print(t.type);
		t.freq = t1.freq + t2.freq;
		// System.out.println(t.freq);

		if (t1.freq >= t2.freq) {
			t.right = t2;
			t.left = t1;

		} else {
			t.left = t2;
			t.right = t1;
		}

		root = t;

		return t;
	}

}

class HuffmanTester {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Node> list = new ArrayList<Node>();
		Huffmkan_ver_3 H = new Huffmkan_ver_3();
		Node t;
		int i = 0;

		System.out.println("What is your message ??");
		String s = sc.nextLine();
		if (s.length() == 0) {
			System.out
					.println("You didn't enter anything !!! Why ?? :( \n BYE!!");
			System.exit(0);
		}
		// System.out.println("Size is " +s.length());

		while (i < s.length()) {
			if (!isPresent(s.charAt(i), list)) {
				t = new Node();
				t.info = s.charAt(i);
				t.type = 'L';
				for (int j = 0; j < s.length(); j++) {
					if (s.charAt(j) == t.info) {
						t.freq++;
					}
				}

				list.add(t);
			}
			i++;
		}
		// System.out.println("The list is :");
		// for (i = 0; i < list.size(); i++) {
		// System.out.println(" " + list.get(i).info + " " + list.get(i).freq);
		// }

		rearrange(list);

		System.out.println("The list after arranging is :");
		for (i = 0; i < list.size(); i++) {
			System.out.println(" " + list.get(i).info + " " + list.get(i).freq);
		}

		if (list.size() > 1) {
			while (list.size() >= 2) // should be greater than equal to 2
			{
				Node p = H.create_tree(list.get(0), list.get(1));
				list.remove(0);
				list.remove(0);

				

				if (list.size() != 0) {

					list.add(p);
					// System.out.println("adding in p");
					rearrange(list);
				}
			}
		} else {
			System.out.println("calling one_element_create_tree");
			H.create_tree_onelement(list.get(0));
		}

		H.display_tree(H.root);
		String ENCODED = "";

		System.out.println("\nThe encoded message is :");
		i = 0;
		while (i < s.length()) {
			ENCODED += H.Encode_Element(s.charAt(i));
			i++;
		}

		System.out.print(ENCODED);

		System.out.print("\nThe decoded message is :");
		System.out.println(H.Decode_String(ENCODED));
		ENCODED = "";

		System.out.println("Your message");
		s = sc.nextLine();

		i = 0;
		while (i < s.length()) {
			if (H.Encode_Element(s.charAt(i)) != null)
				ENCODED += H.Encode_Element(s.charAt(i));
			i++;
		}
		System.out.println("\nThe encoded message is :");
		System.out.println(ENCODED);

		System.out.print("\nThe decoded message is :");
		System.out.println(H.Decode_String(ENCODED));
		// H.display_tree(H.root);
		// System.out.println();

	}

	private static boolean isPresent(char charAt, ArrayList<Node> list) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).info == charAt) {
				return true;
			}
		}
		return false;
	}

	private static void rearrange(ArrayList<Node> list) {
		// TODO Auto-generated method stub

		// selection sort
		int minfreq;
		Node temp;
		int minpos;
		for (int i = 0; i < list.size(); i++) {
			minpos = i;
			minfreq = list.get(i).freq;
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(j).freq < minfreq) {
					minpos = j;
					minfreq = list.get(j).freq;
				}

			}
			temp = list.get(i);
			list.set(i, list.get(minpos));
			list.set(minpos, temp);

		}
	}
}
