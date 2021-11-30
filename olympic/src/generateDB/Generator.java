package generateDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;


import enums.AppEnum;

public class Generator {
	static ArrayList<String> v1 = new ArrayList<String>();
	public Generator () {
		
	}
	public static void main(String[] args) {
		
		
        
	}

	public static void crawling()
	{
		Scanner myObj = new Scanner(System.in); 
		System.out.println("Enter URL link:\n");
	    String url = myObj.nextLine(); 
		crawl(1, url, new ArrayList<String>());
		writeToFile("/Users/harshil java/olympic/src/utils/output1.txt");
	}	
	
	private static void crawl (int level, String url, ArrayList<String> visited) 
    {
        if(level <=2 ) 
        {
            Document doc = request(url, visited);
            if (doc!= null) 
            {
                for (Element link : doc.select("a[href]")) 
                {
                     String next_link = link.absUrl("href");
                     if(visited.contains(next_link) == false) 
                     {
                         crawl(level++, next_link, visited);
                     }
                }
            }
        }
       // System.out.println("done-crawl");
    }
	
	private static Document request(String url, ArrayList<String> v) 
    {
    	try 
        {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if(con.response().statusCode() == 200) 
            {
                System.out.println("Link: " + url);
                //System.out.println(doc.title());
                v.add(url);
                v1.add(url);
               
                return doc;
           
            }
          //  System.out.println("done");
            return null;
        }
        catch (IOException e ) 
    	{
            return null;
        }
        catch(IllegalArgumentException ie)
        {
        	return null;
        }
    }
    
    public static void writeToFile(String filename) 
    {
        FileWriter writer;
        try 
        {
            writer = new FileWriter(filename);
           for(int i =0;i<v1.size();i++)
           {
                    
                    
                    //System.out.println(v1.get(i));
                    writer.append(v1.get(i)+ System.lineSeparator());
           } 
           writer.close();
           System.out.println("done-crawling");
        } catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }
	
	public static void generateDB() {
		ArrayList<String> data = new ArrayList<>();
		try {
			Scanner sc = new Scanner(new File(AppEnum.URLLIST_PATH.getValues()));
			while (sc.hasNext()) {
				data.add(sc.next());
			}
			System.out.println("Fetching...");
			for (int i = 0; i < data.size(); i++) {
				Document document = Jsoup.connect(data.get(i)).userAgent("Mozilla").timeout(30000000).ignoreHttpErrors(true).get();
				document.outputSettings(new Document.OutputSettings().prettyPrint(false)); // makes html() preserve
																							// linebreaks and spacing
				document.select("br").append("\\n");
				document.select("p").prepend("\\n\\n");
				String str = document.html().replaceAll("\\\\n", "\n");
				String op = Jsoup.clean(str, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));

				FileWriter fw = new FileWriter(
						AppEnum.WEBPAGES_PATH.getValues()+"\\" + i + ".txt"); // Destination
																														// File
																														// Location
				BufferedWriter output = new BufferedWriter(fw);

				output.write(op);
				output.newLine();

				output.close();
				System.out.println("i: "+i);
			}
			System.out.println("Finished...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
