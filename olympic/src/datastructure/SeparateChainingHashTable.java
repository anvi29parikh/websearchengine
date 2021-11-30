package datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import utils.IndexComparator;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 * 
 * @author Mark Allen Weiss
 */
public class SeparateChainingHashTable<AnyType> {
	/**
	 * Construct the hash table.
	 */
	public SeparateChainingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * Construct the hash table.
	 * 
	 * @param size approximate table size.
	 */
	public SeparateChainingHashTable(int size) {
		uniqueWords = new ArrayList<String>();
		indexList = new LinkedList[nextPrime(size)];
		for (int i = 0; i < indexList.length; i++)
			indexList[i] = new LinkedList<>();
	}

	/**
	 * Insert into the hash table. If the item is already present, then do nothing.
	 * 
	 * @param x the item to insert.
	 */
	public void insert(AnyType x, String page) {
		List<Index> whichIndexList = indexList[myhash(x)];
		Boolean found = false;
		for (Index index : whichIndexList) {
			if (index.getWord().equalsIgnoreCase(x.toString()) && index.getPage().equalsIgnoreCase(page)) {
				long freq = index.getFrequency();
				freq++;
				index.setFrequency(freq);
				found = true;
			}
		}
		
		if (!found) {
			Index index = new Index(page, 1l, x.toString(), null);
			whichIndexList.add(index);
			if(!uniqueWords.contains(x.toString())) {
				uniqueWords.add(x.toString());
			}
			if (++currentSize > indexList.length)
				rehash(page);
		}
	}

	/**
	 * Find an item in the hash table.
	 * 
	 * @param word the item to search for.
	 * @return true if x isnot found.
	 */
	public boolean contains(String word) {
		Boolean found = false;
		for(String uniqueWord : uniqueWords) {
			
			if(uniqueWord.toLowerCase().equalsIgnoreCase(word.toLowerCase())) {
				found = true;
			}
		}
		return found;
	}
	
	public List<Index> search(String word){
		List<Index> indexlist = new ArrayList<Index>();
		if(contains(word)) {
			String originalIndexedWord = "";
			for(String uniqueWord : uniqueWords) {
				if(uniqueWord.toLowerCase().equalsIgnoreCase(word.toLowerCase())) {
					originalIndexedWord = uniqueWord;
				}
			}
			List<Index> whichIndexList = indexList[myhash((AnyType) originalIndexedWord)];
			Collections.sort(whichIndexList, new IndexComparator());
			for (Index index : whichIndexList) {
				if (index.getWord().equalsIgnoreCase(word.toString())) {
					indexlist.add(index);
				}
			}
		}
		if(indexlist.size() >= 10) {
			return indexlist.subList(0, 10);	
		}
		return indexlist;	
	}
	
	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty() {
		for (int i = 0; i < indexList.length; i++)
			indexList[i].clear();
		currentSize = 0;
	}

	/**
	 * A hash routine for String objects.
	 * 
	 * @param key       the String to hash.
	 * @param tableSize the size of the hash table.
	 * @return the hash value.
	 */
	public static int hash(String key, int tableSize) {
		int hashVal = 0;

		for (int i = 0; i < key.length(); i++)
			hashVal = 37 * hashVal + key.charAt(i);

		hashVal %= tableSize;
		if (hashVal < 0)
			hashVal += tableSize;

		return hashVal;
	}

	private void rehash(String fileName) {
		List<Index>[] oldLists = indexList;

		// Create new double-sized, empty table
		indexList = new List[nextPrime(2 * indexList.length)];
		for (int j = 0; j < indexList.length; j++)
			indexList[j] = new LinkedList<>();

		// Copy table over
		currentSize = 0;
		for (List<Index> list : oldLists)
			for (Index item : list)
				insert((AnyType) item.getWord(), fileName);

	}

	private int myhash(AnyType x) {

		int hashVal = x.hashCode();

		hashVal %= indexList.length;
		if (hashVal < 0)
			hashVal += indexList.length;

		return hashVal;
	}

	private static final int DEFAULT_TABLE_SIZE = 19999999;

	private List<Index>[] indexList;
	private List<String> uniqueWords;
	private int currentSize;
	
	public List<String> getUniqueWords(){
		return uniqueWords;
	}

	/**
	 * Internal method to find a prime number at least as large as n.
	 * 
	 * @param n the starting number (must be positive).
	 * @return a prime number larger than or equal to n.
	 */
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	/**
	 * Internal method to test if a number is prime. Not an efficient algorithm.
	 * 
	 * @param n the number to test.
	 * @return the result of the test.
	 */
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

}
