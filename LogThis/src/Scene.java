import java.util.ArrayList;
import java.util.List;


public class Scene {
    
    private Sequence[] sequences;
    private int numberOfFrames;
    private int startTime;
    private int endTime;

    /**
     * @param sequences
     */
    public Scene(Sequence[] sequences) {
    	
        this.sequences		= sequences;
        this.numberOfFrames = 0;
        for (int i = 0; i < sequences.length; i++) {
			this.numberOfFrames += sequences[i].getNumberOfFrames();
		}
        this.startTime	= sequences[0].getFirstTimestamp();
        this.endTime	= sequences[sequences.length-1].getLastTimestamp();
    }
    
    /**
     * @param sequences
     * @param frameCountOfAllSequences
     * @param startingTimeOfScene
     * @param endingTimeOfScene
     */
    public Scene(Sequence[] sequences, int frameCountOfAllSequences, int startingTimeOfScene, int endingTimeOfScene) {
    	
        this.sequences 		= sequences;
        this.numberOfFrames	= frameCountOfAllSequences;
        this.startTime		= startingTimeOfScene;
        this.endTime 		= endingTimeOfScene;
        
    }

    /**
     * 
     * @return generalInformation about the Sequence
     */
    public String info(){
    	
    	int numberOfSequences = this.sequences.length;
    	String generalInformation = "Scene of " + numberOfFrames + " Frames in "+ numberOfSequences + " Sequences\r\n";
    	generalInformation += "Starting at " + this.startTime + " and ending at " + this.endTime + " this Scene is ";
    	generalInformation += (this.endTime - this.startTime) + " timeunits long\r\n";
    	generalInformation += "That calculates to " + (this.endTime - this.startTime)/numberOfSequences + " timeunits per Sequence and "; 
    	generalInformation += (this.endTime - this.startTime)/numberOfFrames + " timeunits per Frame."; 
    	return generalInformation;
    }
    
    /**
	 * output the difference between the sequences of a scene
	 */
	public void showAgeingOf(){
		long startTime = System.currentTimeMillis();
		Sequence[] sequences = this.getSequences();
		
		String[][] dyingTypes	= new String[sequences.length][];
		String[][] growingTypes = new String[sequences.length][];
		String[][] newbornTypes = new String[sequences.length][];
				
		
		for (int i = 0; i < sequences.length; i++) {
			
			dyingTypes[i]	= sequences[i].getDyingTypes();
			growingTypes[i] = sequences[i].getGrowingTypes();
			newbornTypes[i] = sequences[i].getNewbornTypes();
			
		}
		for (int j = 0; j < sequences.length; j++) {
			
			int sizeOfSequence = sequences[j].getNumberOfFrames();
			
			System.out.println("_________________________________________________________________________");
			System.out.println();
			System.out.println("Sequence "+(j-1)+" zu Sequence "+j+" ("+sizeOfSequence+" Frames):");
			System.out.println();
			
			if (growingTypes[j].length!=0){
				System.out.println("Growing Types:");
				
				for (int k = 0; k < growingTypes[j].length; k++) {	
										
					System.out.println(" - "+growingTypes[j][k]+ " ("+sequences[j].getTypeToAgeMap().get(growingTypes[j][k])+")");
					
				}
				System.out.println();
			}
				
			if (newbornTypes[j].length!=0){
				
				System.out.println("Newborn Types: ");
				
				for (int k = 0; k < newbornTypes[j].length; k++) {
					
					System.out.println(" - "+newbornTypes[j][k]+" ("+sequences[j].getTypeToAgeMap().get(newbornTypes[j][k])+")");
				}
				
				System.out.println();
			}
			
			if (dyingTypes[j].length!=0){
				
				System.out.println("Dying Types:");
				
				for (int k = 0; k < dyingTypes[j].length; k++) {
				
					System.out.println(" - "+dyingTypes[j][k]+" ("+sequences[j].getTypeToAgeMap().get(dyingTypes[j][k])+")");
					
				}
			}
		}
		System.out.println();
		System.out.println((System.currentTimeMillis()-startTime)+" milliseconds to show output log");
	}

	/**
	 * @param filter the filter to use
	 * @param containsFilter include or exclude filter arguments
	 * 
	 * @return the filteredSequence
	 */
	public Scene filterScene(String[] filter, boolean containsFilter){
		
		Sequence[] originalSequences	= this.getSequences();
		List<Sequence> sequenceList		= new ArrayList<Sequence>();
		Sequence[] filteredSequences	= new Sequence[originalSequences.length];
		
		for (int i = 0; i < originalSequences.length; i++) {
			
			Sequence tmpSequence = originalSequences[i].filterSequence(filter, containsFilter);
			sequenceList.add(tmpSequence);
			
		}

		if (originalSequences.length != sequenceList.size()) {
		
			System.out.println("ALARM Ungleiche Größe!");
		}
		
		filteredSequences = sequenceList.toArray(filteredSequences);
		
		Scene filteredScene = new Scene(filteredSequences, this.getNumberOfFrames(), this.getStartTime(), this.getEndTime());
		
		return filteredScene;
		
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
