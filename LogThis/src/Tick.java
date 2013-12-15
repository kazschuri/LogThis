
public class Tick {
    
    private int timestamp;
    private String type;
    private String parentType;
    
    /**
     * @param timestamp
     * @param type
     */
    public Tick() {

        this.timestamp = 0;
        this.type = "";
        this.parentType = "";
        
    }

    /**
     * @param timestamp
     * @param type
     * @param parentType
     */
    public Tick(int timestamp, String type, String parentType) {

        this.timestamp = timestamp;
        this.type = type;
        this.parentType = parentType;
        
    }

    /**
     * @return the timestamp
     */
    public int getTimestamp() {
    	
        return timestamp;
        
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(int timestamp) {
    	
        this.timestamp = timestamp;
    }

    /**
     * @return the type
     */
    public String getType() {
    	
        return type;
        
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
    	
        this.type = type;
        
    }

	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

}
