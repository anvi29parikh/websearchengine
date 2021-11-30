package utils;

import java.util.Comparator;

import datastructure.Index;

public class IndexComparator implements Comparator<Index> {
	@Override
	public int compare(Index o1, Index o2) {
		// TODO Auto-generated method stub
		return (int) (o2.getFrequency()-o1.getFrequency());
	}
}