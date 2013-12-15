
public class Tick {
    
    private int timestamp;
    private String type;
    
    /**
     * @param timestamp
     * @param type
     */
    public Tick() {

        this.timestamp = 0;
        this.type = "";
        
    }

    /**
     * @param timestamp
     * @param type
     */
    public Tick(int timestamp, String type) {

        this.timestamp = timestamp;
        this.type = type;
        
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

}
