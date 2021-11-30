import java.util.ArrayList;
import java.util.Scanner;

import datastructure.WSETasks;
import generateDB.Generator;

public class application {
	public static void main(String[] args) {
		WSETasks wseTasks = new WSETasks();
		
		
        
		System.out.println("Welcome to Olympics web search engine");
		while(true) {
			System.out.println("Please select your choice : ");
			System.out.println("1. Crawling");
			System.out.println("2. Generate DB");
			System.out.println("3. Re-Indexing");
			System.out.println("4. Search");
			System.out.println("5. Suggestions");
			System.out.println("6. Auto complete");
			System.out.println("7. Exit");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch(choice) {
			
			case 1: 
				System.out.println("Crawling website");	
				Generator.crawling();
				//System.out.println("Indexing...");
				//wseTasks.readFiles();
				System.out.println("Websiter Crawled");
				break;
			case 2: 
				System.out.println("Generating database");	
				Generator.generateDB();
				System.out.println("Indexing...");
				wseTasks.readFiles();
				System.out.println("Database generated");
				break;
			case 3: 
				System.out.println("Re-Indexing..");
				wseTasks.readFiles();
				System.out.println("Re-Indexing finished");
				break;
			case 4: 
				System.out.println("Enter word for search");
				String word = sc.next();
				wseTasks.search(word);
				break;
			case 5:
				System.out.println("Enter word to get suggestions");
				String wordForSuggestion = sc.next();
				wseTasks.suggestWords(wordForSuggestion, null, false);
				break;
			case 6:
				System.out.println("Enter word to get auto-complete suggestions");
				String wordForAutoComplete = sc.next();
				wseTasks.autoCompleteSuggetions(wordForAutoComplete);
				break;
			case 7:
				System.out.println("Exiting...");
				sc.close();
				System.out.println("Thanks for using!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Input");
				break;
			}	
		}
	}
}
