
public class Scene {
    
    private Sequence[] sequences;
    private int numberOfFrames;
    private int startTime;
    private int endTime;

    /**
     * @param sequences
     */
    public Scene(Sequence[] sequences) {
    	
        this.sequences = sequences;
        numberOfFrames = 0;
        for (int i = 0; i < sequences.length; i++) {
			numberOfFrames += sequences[i].getNumberOfFrames();
		}
        startTime = sequences[0].getFirstTimestamp();
        endTime = sequences[sequences.length-1].getLastTimestamp();
    }

    /**
     * 
     * @return
     */
    public String info(){
    	
    	int numberOfSequences = this.sequences.length;
    	String generalInformation = "Scene of " + numberOfFrames + " Frames in "+ numberOfSequences + " Sequences\r\n";
    	generalInformation += "Starting at " + this.startTime + " and ending at " + this.endTime + " this Scene is " + (this.endTime - this.startTime) + " long\r\n";
    	generalInformation += "That calculates to " + (this.endTime - this.startTime)/numberOfSequences + " per Sequence and " + (this.endTime - this.startTime)/numberOfFrames + "per Frame."; 
    	return generalInformation;
    }
    
    /**
     * @return the sequence at index
     */
    public Sequence getSequenceAt( int index ) {
    	
        return sequences[index];
        
    }
    
    /**
     * @return the sequences
     */
    public Sequence[] getSequences() {
    	
        return sequences;
        
    }

    /**
     * @param sequences the sequences to set
     */
    public void setSequences(Sequence[] sequences) {
    	
        this.sequences = sequences;
        
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

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
    
}
