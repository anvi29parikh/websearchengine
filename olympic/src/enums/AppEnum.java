package enums;

public enum AppEnum {
	// ENUMS
    URLLIST_PATH("/Users/harshil java/olympic/src/utils/output1.txt"),
    WEBPAGES_PATH("/Users/harshil java/olympic/src/utils/webpages/"),
    //TODO add here

    ;
	// properties
    private final String value;
    
    // Getters
   	public String getValues() {
   		return value;
   	}
   	
   	/**
   	 * @param value
   	 */
   	private AppEnum(String value) {
   		this.value = value;
   	}
}

