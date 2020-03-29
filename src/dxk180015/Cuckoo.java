package dxk180015;

/**
 * Short Project 8: Hashing
 * @author Dishant Khanna(dxk180015) and Tushar Gonawala(txg170003)
 *
 */
public class Cuckoo<T> {

	int numHash; // Number of Hash functions
	int capacity; // Len of Hash Table
	Entry<T>[][] hashTable;
	int size;
	double loadFactor = 0.5; // open-addressing default
	int threshold; // limit for no of replacements on collision

	// Entry corresponding to an element in Hash Table
	class Entry<E> {
		E element;

		public Entry(E element) {
			this.element = element;
		}
	}

	public Cuckoo() {
		size = 0;
		numHash = 3;
		capacity = 1024;
		hashTable = new Entry[capacity][numHash];
		threshold = (int) Math.log((double) capacity);
	}

	// Code extracted from Java HashMap:
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	/**
	 * Generates a Hash value for an i in {1, .., numHash}
	 *
	 * @param i number of hash function from "numHash" hash functions.
	 * @param x value which hash value is to be computed
	 * @return hash value as an int
	 */
	private int hashFunction(int i, T x) {
		switch (i) {
			case 1:
				// Hash Function 1
			return indexFor(hash(x.hashCode()), hashTable.length);
			default:
				// Hash Function 2, for i > 1
			return (hashFunction(1,x) + i * (1 + x.hashCode() % 9)) & (hashTable.length - 1);
		}
	}

	/**
	 * Adds the specified element to this set if it is not already present.
	 * @param x the element to be added
	 * @return true if successful insertion, false otherwise
	 */
	public boolean add(T x) {
		double fraction = 0;
		// Reject Duplicates
		if (contains(x)) { return false; }

		int i = 1;
		int cell = i - 1;
		int location = hashFunction(i, x);

		// While we are able to find a free spot/ cell among numHash spots meant for x.
		// just like finding/waiting for a full-time job from all applied job applications :P
		while (i <= numHash) {
			cell = i - 1;
			location = hashFunction(i++, x);

			if (hashTable[location][cell] == null) {
				hashTable[location][cell] = new Entry<T>(x);
				size++;
				fraction = (double) size / capacity;
				if (loadFactor < fraction) {
					rehash();
				}
				return true;
			}
		}

		i = 1;
		int count = 0;
		while (count < threshold) {
			count++;
			cell = i - 1;
			location = hashFunction(i, x);

			if (hashTable[location][cell] == null) {
				hashTable[location][cell] = new Entry<T>(x);
				size++;
				fraction = (double) size / capacity;
				if (loadFactor < fraction) {
					rehash();
				}
				return true;
			}
			// replace it with it's place holder, when can't insert x,
			else {
				T temp = (T) hashTable[location][cell].element;
				hashTable[location][cell].element = x;
				x = temp;
			}
			i = (i == numHash) ? 1 : (i + 1);
		}
		fraction = (double) size / capacity;
		if (loadFactor < fraction) {
			rehash();
		}
		return false;
	}

	/**
	 * If x is there is the Collection.
	 * @param x the input element
	 * @return true if present, false otherwise
	 */
	public boolean contains(T x) {
		int i = 1;
		int cell = 0;
		int location = hashFunction(1, x);

		while (i <= numHash) {
			cell = i - 1;
			location = hashFunction(i++, x);

			if ((hashTable[location][cell] != null) &&
				(x.equals(hashTable[location][cell].element))) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the specified element from this set if it is present.
	 * @param x the element to be removed
	 * @return true, if successfully removed, false otherwise
	 */
	public boolean remove(T x) {

		int i = 1;
		int cell = 0;
		int location = hashFunction(1, x);

		while (i <= numHash) {
			cell = i - 1;
			location = hashFunction(i++, x);

			if ((hashTable[location][cell] != null) &&
				(x.equals(hashTable[location][cell].element))) {

				hashTable[location][cell] = null;
				size--;
				return true;
			}
		}
		return false;
	}

	public int size() {
		return size;
	}

	private void rehash() {
		Entry<T>[][] temp = hashTable;
		size = 0; // as a new hash table is to be created
		capacity = capacity * 2;
		hashTable = new Entry[capacity][numHash];
		threshold = (int) Math.log((double) capacity);

		int location = 0;
		int cell = 0;
		Entry<T> e = null;

		while (location < temp.length) {
			cell = 0;
			while (cell < numHash) {
				e = temp[location][cell++];
				if (e != null) {
					add(e.element);
				}
			}
			location++;
		}
	}

	public void printHashTable2() {
		System.out.println("\nHash Table: ");
		System.out.format("%40s", "+--------------------------------------+\n");
		System.out.format("%-11s%-14s%-13s%-2s", "| Location", "| Cell 1", "| Cell 2", " |\n");
		System.out.println("|--------------------------------------|");

		int location = 0;
		while (location < hashTable.length) {
			Entry<T> c1 = hashTable[location][0];
			Entry<T> c2 = hashTable[location][1];
			Integer nothing = null;

			if (c1 != null) {
				if (c2 != null) {
					System.out.format("%-11s%-14s%-13s%-2s", "| "+ location, "| "
						+ c1.element, "| " + c2.element, " |\n");
				}
				else {
					System.out.format("%-11s%-14s%-13s%-2s", "| "+ location, "| "
						+ c1.element, "| " + nothing, " |\n");
				}
			}
			else {
				if (c2 != null) {
					System.out.format("%-11s%-14s%-13s%-2s", "| "+ location, "| "
						+ nothing, "| " + c2.element, " |\n");
				}
				else {
					System.out.format("%-11s%-14s%-13s%-2s", "| "+ location, "| "
						+ nothing, "| " + nothing, " |\n");
				}
			}
			location++;
		}

		System.out.format("%40s","+--------------------------------------+\n\n");
		System.out.println("Size = " + size + " Capacity = " + capacity);
	}

	/**
	 * Calculate distinct elements in an array
	 * @param arr: Array of Integers which may or may not have duplicates.
	 * @return: returns the count of distinct elements in the provided array.
	 */
	public static<T> int distinctElements(T[] arr){
		Cuckoo<T> dist = new Cuckoo<>();

		for (T e : arr) { dist.add(e); }
		return dist.size();
	}

	//-------------------------- MAIN METHOD ----------------------------------
	public static void main(String[] args) {
		Cuckoo<Integer> ch = new Cuckoo<>();

		int N = 18;
		//int[] num =       {24, 20, 53, 1, 12, 0, 3, 24, 0, 45, 42, 30, 12, 50, 24, 49, 26, 17};
		//int[] operation = {59, 33, 6, 11, 54, 2, 6, 97, 25, 73, 32, 18, 79, 19, 97, 22, 36, 60};
		int[] num =       {187, 121, 62, 166, 35, 43, 3, 24, 0, 45, 42, 30, 12, 50, 24, 49, 26, 17};
		int[] operation = {20, 33, 6, 11, 54, 2, 6, 97, 25, 73, 32, 18, 79, 19, 97, 22, 36, 60};

		System.out.println("Key \t h1(x) \t h2(x)");
		for (int i = 0; i < N; i++) {
			System.out.println(num[i] + " \t " + ch.hashFunction(1, num[i]) + " \t "
				+ ch.hashFunction(2, num[i]));
		}

		System.out.println("\nOperations Timeline: ");
		for (int i = 0; i < N; i++) {

			if (operation[i] < 67) {
				System.out.println("Add "+num[i]+" \t\t" + ch.add(num[i]) + " "
					+ ch.size() + " " + ch.capacity);
			}
			else if (operation[i] < 84) {
				System.out.println("Contains "+num[i]+" \t" + ch.contains(num[i]) + " "
					+ ch.size() + " " + ch.capacity);
			}
			else {
				System.out.println("Remove "+num[i]+" \t" + ch.remove(num[i]) + " "
					+ ch.size() + " " + ch.capacity);
			}
		}

		System.out.println("\n\nKey \th1(x) \th2(x)");
		for (int i = 0; i < N; i++) {
			System.out.println(num[i] + "\t" + ch.hashFunction(1, num[i]) + "\t"
				+ ch.hashFunction(2, num[i]));
		}

		ch.printHashTable2();
	}
}
