
public class AgeingSequence {

    private String[] newbornTypes;
    private String[] growingTypes;
    private String[] dyingTypes;
    private int[][] timestamps;
    private int numberOfFrames;
    
    
	/**
	 * @param newbornTypes
	 * @param growingTypes
	 * @param dyingTypes
	 * @param timestamps
	 * @param numberOfFrames
	 */
	public AgeingSequence(String[] newbornTypes, String[] growingTypes,
			String[] dyingTypes, int[][] timestamps, int numberOfFrames) {
		super();
		this.newbornTypes = newbornTypes;
		this.growingTypes = growingTypes;
		this.dyingTypes = dyingTypes;
		this.timestamps = timestamps;
		this.numberOfFrames = numberOfFrames;
	}
	
	/**
	 * @return the newbornTypes
	 */
	public String[] getNewbornTypes() {
		return newbornTypes;
	}
	
	/**
	 * @param newbornTypes the newbornTypes to set
	 */
	public void setNewbornTypes(String[] newbornTypes) {
		this.newbornTypes = newbornTypes;
	}
	
	/**
	 * @return the growingTypes
	 */
	public String[] getGrowingTypes() {
		return growingTypes;
	}
	
	/**
	 * @param growingTypes the growingTypes to set
	 */
	public void setGrowingTypes(String[] growingTypes) {
		this.growingTypes = growingTypes;
	}
	
	/**
	 * @return the dyingTypes
	 */
	public String[] getDyingTypes() {
		return dyingTypes;
	}
	
	/**
	 * @param dyingTypes the dyingTypes to set
	 */
	public void setDyingTypes(String[] dyingTypes) {
		this.dyingTypes = dyingTypes;
	}
	
	/**
	 * @return the timestamps
	 */
	public int[][] getTimestamps() {
		return timestamps;
	}
	
	/**
	 * @param timestamps the timestamps to set
	 */
	public void setTimestamps(int[][] timestamps) {
		this.timestamps = timestamps;
	}
	
	/**
	 * @return the numberOfFrames
	 */
	public int getNumberOfFrames() {
		return numberOfFrames;
	}
	
	/**
	 * @param numberOfFrames the numberOfFrames to set
	 */
	public void setNumberOfFrames(int numberOfFrames) {
		this.numberOfFrames = numberOfFrames;
	}
    
    
}

