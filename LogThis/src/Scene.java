import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        this.numberOfFrames = 0;
        for (int i = 0; i < sequences.length; i++) {
			this.numberOfFrames += sequences[i].getNumberOfFrames();
		}
        this.startTime = sequences[0].getFirstTimestamp();
        this.endTime = sequences[sequences.length-1].getLastTimestamp();
    }
    
    /**
     * @param sequences
     * @param frameCountOfAllSequences
     * @param startingTimeOfScene
     * @param endingTimeOfScene
     */
    public Scene(Sequence[] sequences, int frameCountOfAllSequences, int startingTimeOfScene, int endingTimeOfScene) {
    	
        this.sequences = sequences;
        this.numberOfFrames = frameCountOfAllSequences;
        this.startTime = startingTimeOfScene;
        this.endTime = endingTimeOfScene;
        
    }

    /**
     * 
     * @return
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
		
		String[][] dyingTypes = new String[sequences.length][];
		String[][] growingTypes = new String[sequences.length][];
		String[][] newbornTypes = new String[sequences.length][];
				
		
		for (int i = 0; i < sequences.length; i++) {
			
			dyingTypes[i] = sequences[i].getDyingTypes();
			growingTypes[i] = sequences[i].getGrowingTypes();
			newbornTypes[i] = sequences[i].getNewbornTypes();
			
		}
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for (int j = 0; j < sequences.length; j++) {
			
			int sizeOfSequence = sequences[j].getNumberOfFrames();
			
			System.out.println("_________________________________________________________________________");
			System.out.println();
			System.out.println("Sequence "+(j-1)+" zu Sequence "+j+" ("+sizeOfSequence+" Frames):");
			System.out.println();
			
			if (growingTypes[j].length!=0){
				System.out.println("Growing Types:");
				
				for (int k = 0; k < growingTypes[j].length; k++) {	
					if(countMap.containsKey(growingTypes[j][k])){ 	// if key is available
						
						countMap.put(growingTypes[j][k], countMap.get(growingTypes[j][k])+sizeOfSequence);	// increase age by sizeOfSequence
					
					} else {
					
						countMap.put(growingTypes[j][k], sizeOfSequence);		// otherwise create new key with age equal to sizeOfSequence					
					
					}
					
					System.out.println(" - "+growingTypes[j][k]+" ("+countMap.get(growingTypes[j][k])+")");
					
				}
				System.out.println();
			}
				
			if (newbornTypes[j].length!=0){
				
				System.out.println("Newborn Types: ");
				
				for (int k = 0; k < newbornTypes[j].length; k++) {
					
					if(countMap.containsKey(newbornTypes[j][k])){	// if key is available

						countMap.put(newbornTypes[j][k], countMap.get(newbornTypes[j][k])+sizeOfSequence);	// increase age by sizeOfSequence
					
					} else {
					
						countMap.put(newbornTypes[j][k], sizeOfSequence);		// otherwise create new key with age equal to sizeOfSequence

					}
					
					System.out.println(" - "+newbornTypes[j][k]+" ("+countMap.get(newbornTypes[j][k])+")");
				}
				
				System.out.println();
			}
			
			if (dyingTypes[j].length!=0){
				
				System.out.println("Dying Types:");
				
				for (int k = 0; k < dyingTypes[j].length; k++) {
				
					System.out.println(" - "+dyingTypes[j][k]+" ("+countMap.get(dyingTypes[j][k])+")");
					countMap.put(dyingTypes[j][k], 0);
					
				}
			}
		}
		System.out.println();
		System.out.println((System.currentTimeMillis()-startTime)+" milliseconds to show output log");
	}
    
	/**
	 * @param filter with context to include
	 * @param includeFilter which determines, if the filter is included or excluded
	 * 
	 * @return the filteredScene
	 */
	public Scene filterScene(String[] filter, boolean includeFilter){
		
		Sequence[] originalSequences = this.getSequences();
		List<Sequence> sequenceList = new ArrayList<Sequence>();
		Sequence[] filteredSequences = new Sequence[originalSequences.length];
		
		for (int i = 0; i < originalSequences.length; i++) {
			String[] tmpNewbornTypes;
			String[] tmpGrowingTypes;
			String[] tmpDyingTypes;
			if (includeFilter) {
				
				tmpNewbornTypes = containsPartsOf(originalSequences[i].getNewbornTypes(), filter);
				tmpGrowingTypes = containsPartsOf(originalSequences[i].getGrowingTypes(), filter);
				tmpDyingTypes = containsPartsOf(originalSequences[i].getDyingTypes(), filter);
				
			}else {
				
				tmpNewbornTypes = doesNotContainPartsOf(originalSequences[i].getNewbornTypes(), filter);
				tmpGrowingTypes = doesNotContainPartsOf(originalSequences[i].getGrowingTypes(), filter);
				tmpDyingTypes = doesNotContainPartsOf(originalSequences[i].getDyingTypes(), filter);
				
			}
			Sequence tmpSequence = new Sequence(tmpNewbornTypes, tmpGrowingTypes, tmpDyingTypes, originalSequences[i].getFirstTimestamp(), originalSequences[i].getLastTimestamp(), originalSequences[i].getNumberOfFrames());
			sequenceList.add(tmpSequence);
		}

		if (originalSequences.length != sequenceList.size()) {
		
			System.out.println("ALARM Ungleiche Größe!");
		}
		
		filteredSequences = sequenceList.toArray(filteredSequences);
		
		Scene filteredScene = new Scene(filteredSequences, this.getNumberOfFrames(), this.getStartTime(), this.getEndTime());
		
		return filteredScene;
		
	}
	private static String[] containsPartsOf(String[] oldArray, String[] filter){
		
		List<String> typeList = new ArrayList<String>();
		
		for (int i = 0; i < oldArray.length; i++) {
			
			for (int j = 0; j < filter.length; j++) {
			
				if (oldArray[i].endsWith(filter[j])){
					
					typeList.add(oldArray[i]);
					break;
				}
				
			}
			
		}
		String[] typeArray = new String[typeList.size()]; 
		typeArray = typeList.toArray(typeArray);
		
		return typeArray;
	}
	private static String[] doesNotContainPartsOf(String[] oldArray, String[] filter){
		
		List<String> typeList = new ArrayList<String>();
		
		for (int i = 0; i < oldArray.length; i++) {
			boolean typeNotIncluded = true;
			for (int j = 0; j < filter.length; j++) {
			
				if (oldArray[i].endsWith(filter[j])){
					
					typeNotIncluded = false;
					
				}
				
			}
			if (typeNotIncluded) {
				
				typeList.add(oldArray[i]);
				
			}
			
		}
		String[] typeArray = new String[typeList.size()]; 
		typeArray = typeList.toArray(typeArray);
		
		return typeArray;
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
