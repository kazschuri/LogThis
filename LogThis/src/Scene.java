
public class Scene {
    
    private Sequence[] sequences;
    private AgeingSequence[] ageingSequences;
    private int numberOfFrames;

    /**
     * @param sequences
     */
    public Scene(Sequence[] sequences) {
    	
        this.sequences = sequences;
        numberOfFrames = 0;
        for (int i = 0; i < sequences.length; i++) {
			numberOfFrames += sequences[i].getNumberOfFrames();
		}
    }

    /**
     * 
     * @return
     */
    public String info(){
    	
    	int numberOfSequences = this.sequences.length;
    	String generalInformation = "Scene of " + numberOfFrames + " Frames in "+ numberOfSequences + " Sequences";
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
	 * @return the ageingSequences
	 */
	public AgeingSequence[] getAgeingSequences() {
		return ageingSequences;
	}

	
	/**
	 * @param ageingSequences the ageingSequences to set
	 */
	public void setAgeingSequences(AgeingSequence[] ageingSequences) {
		this.ageingSequences = ageingSequences;
	}
    
    
}
