import java.util.ArrayList;
import java.util.List;


public class Sequence {

    private String[] newbornTypes;
    private String[] growingTypes;
    private String[] dyingTypes;
    private int[][] timestamps;
    private int firstTimestamp;
    private int lastTimestamp;
    private int numberOfFrames;
    
    
	public Sequence() {
		String[] nullTypeArray = {""};
//		this.typeNames = nullTypeArray;
		this.newbornTypes = nullTypeArray;
		this.growingTypes = nullTypeArray;
		this.dyingTypes = nullTypeArray;
		
		int[][] nullTimeStamps = new int[1][];
		int[] nullArray = {0,0};
		nullTimeStamps[0] = nullArray;
		this.timestamps = nullTimeStamps;
		this.numberOfFrames = 0;
	}
	
	

    /**
	 * @param typeNames
	 * @param timestamps
	 * @param previousSequence
	 */
	public Sequence(String[] typeNames, int[][] timestamps, Sequence previousSequence) {
		this.timestamps = timestamps;
		this.firstTimestamp = timestamps[0][0];
		
		int tmpLength = timestamps.length;
		this.lastTimestamp = timestamps[tmpLength-1][timestamps[tmpLength-1].length-1];
		for (int i = 0; i < tmpLength; i++) {
			
			if (this.firstTimestamp > timestamps[i][0]) {
			
				this.firstTimestamp = timestamps[i][0];	
			
			}
			
			if (this.lastTimestamp > timestamps[i][timestamps[i].length-1]){
				
				this.lastTimestamp = timestamps[i][timestamps[i].length-1];
			}
						
		}
		 
		this.numberOfFrames = this.timestamps.length;
		
		List<String> allTypesOfB	= new ArrayList<String>();
		
		List<String> typesOfA	= new ArrayList<String>();
		List<String> typesOfAB	= new ArrayList<String>();
		List<String> typesOfB 	= new ArrayList<String>();
		
		String[] tmpS = previousSequence.getConcatenatedTypeNames();		
		for (int i = 0; i < tmpS.length; i++) {
			
			allTypesOfB.add(tmpS[i]);
			
		}
		
		for (int i = 0; i < typeNames.length; i++) {		// look at each type of A
			
			if (allTypesOfB.contains(typeNames[i])) {		// if contained in B
				
				typesOfAB.add(typeNames[i]);				// add to List of Types which are contained in A and B
				
			} else {
				
				typesOfA.add(typeNames[i]);					// else it is one of the Types that are only in A
				
			}
		}
		
		for (int i = 0; i < allTypesOfB.size(); i++) {			
			
			if (allTypesOfB.get(i)=="") {
				
			} else if (!typesOfAB.contains(allTypesOfB.get(i))) {		// find Types that are in B but not in A
				
				typesOfB.add(allTypesOfB.get(i));				// add them to Types of B
				
			}
		}
		
		
		String[] arrayOfA = new String[typesOfA.size()];
		String[] arrayOfAB  = new String[typesOfAB.size()];
		String[] arrayOfB  = new String[typesOfB.size()];
		
		arrayOfA = typesOfA.toArray(arrayOfA);
		arrayOfAB = typesOfAB.toArray(arrayOfAB);
		arrayOfB = typesOfB.toArray(arrayOfB);
		
		this.newbornTypes = arrayOfA;
		this.growingTypes = arrayOfAB;
		this.dyingTypes = arrayOfB;
		
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
     * @return the concatenatedTypeNames
     */
    public String[] getConcatenatedTypeNames() {
    	int concatLength = newbornTypes.length + growingTypes.length;
    	String[] concatenatedTypeNames = new String[concatLength];

    	System.arraycopy(newbornTypes, 0, concatenatedTypeNames, 0, newbornTypes.length);
    	System.arraycopy(growingTypes, 0, concatenatedTypeNames, newbornTypes.length, growingTypes.length);
    	
    	return concatenatedTypeNames;
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
     * 
     * @return the numberOfFrames
     */
    public int getNumberOfFrames() {
        return this.numberOfFrames;
    }
	/**
	 * @param numberOfFrames the numberOfFrames to set
	 */
	public void setNumberOfFrames(int numberOfFrames) {
		this.numberOfFrames = numberOfFrames;
	}



	/**
	 * @return the firstTimestamp
	 */
	public int getFirstTimestamp() {
		return firstTimestamp;
	}



	/**
	 * @param firstTimestamp the firstTimestamp to set
	 */
	public void setFirstTimestamp(int firstTimestamp) {
		this.firstTimestamp = firstTimestamp;
	}



	/**
	 * @return the lastTimestamp
	 */
	public int getLastTimestamp() {
		return lastTimestamp;
	}



	/**
	 * @param lastTimestamp the lastTimestamp to set
	 */
	public void setLastTimestamp(int lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}
    
    
}
