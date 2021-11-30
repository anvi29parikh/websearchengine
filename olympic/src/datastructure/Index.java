package datastructure;

import java.util.ArrayList;

public class Index {
	private String page;
	private long frequency;
	private String word;
	private ArrayList<Long> position;
	
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public long getFrequency() {
		return frequency;
	}
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	public ArrayList<Long> getPosition() {
		return position;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void setPosition(ArrayList<Long> position) {
		this.position = position;
	}
	
	public Index(String page, long frequency, String word, ArrayList<Long> position) {
		super();
		this.page = page;
		this.frequency = frequency;
		this.word = word;
		this.position = position;
	}
	public Index() {
		super();
		// TODO Auto-generated constructor stub
	}
}
