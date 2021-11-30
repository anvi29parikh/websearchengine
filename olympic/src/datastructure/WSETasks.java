package datastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.AppEnum;
import utils.Sequences;

public class WSETasks {
	private SeparateChainingHashTable<String> H;
	public void generateTable() {
		
	}
	
	public WSETasks() {
		H = new SeparateChainingHashTable<>( );
	}
	
	public void search(String word) {
		List<Index> indexList =  H.search(word);
		if(indexList==null || indexList.isEmpty()) {
			System.out.println("Sorry "+word+" not found!");
			System.out.println("Choose option 4 or 5 to get suggestions or auto-complete suggestion and search again!");
		}else {
			for(Index index : indexList) {
				System.out.println("Page: "+ index.getPage()+" & frequency:"+index.getFrequency());
			}	
		}
	}
	
	public void autoCompleteSuggetions(String word) {
		System.out.println("Finding auto-complete suggestions....");
		List<String> autoCompleteWords = new ArrayList<String>();
		for(String uniqueWord : H.getUniqueWords()) {
			if(uniqueWord.contains(word)) {
				autoCompleteWords.add(uniqueWord);
			}
		}
		if(autoCompleteWords == null || autoCompleteWords.size() == 0) {
			System.out.println("Sorry! we can not find any auto-complete suggestions");
		}else {
			suggestWords(word, autoCompleteWords, true);	
		}
	}
	
	public void readFiles() {
		System.out.println("Reading files...");
		File webPagesFolder = new File(AppEnum.WEBPAGES_PATH.getValues());
		for(final File webPage: webPagesFolder.listFiles()) {
			try {
				Scanner scanner = new Scanner(webPage);
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					for(String word : line.split("\\s")) {
						if(!word.isEmpty() && !word.isBlank()) {
							H.insert(word, webPage.getName());
						}
					}
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void suggestWords (String word, List<String> uniqueWords, Boolean autoComplete) {
		System.out.println("Suggesting word ...");
		int threshold = 2;
		if(autoComplete) {
			threshold = 6;
		}
		int totalSuggestedWords = 0;
		if(uniqueWords == null) {
			uniqueWords = H.getUniqueWords();
		}
		for(String otherWord : uniqueWords) {
			if(!otherWord.equalsIgnoreCase(word)) {
				int distance = Sequences.editDistance(word, otherWord);	
				if(distance <= threshold) {
					totalSuggestedWords++;
					System.out.println(otherWord);
				}
			}
			if(totalSuggestedWords == 5) {
				break;
			}
		}
		if(totalSuggestedWords == 0) {
			System.out.println("Sorry! we don't have any suggestions");
		}
	}
}
