import java.util.ArrayList;
import java.util.List;


public class Sequence {

    private String[] newbornTypes;
    private String[] growingTypes;
    private String[] dyingTypes;
    private int firstTimestamp;
    private int lastTimestamp;
    private int numberOfFrames;
    
    
	public Sequence() {
		String[] nullTypeArray = {""};
		this.newbornTypes = nullTypeArray;
		this.growingTypes = nullTypeArray;
		this.dyingTypes = nullTypeArray;
		
		this.firstTimestamp = 0;
		this.lastTimestamp = 0;
		this.numberOfFrames = 0;
	}
	
	/**
	 * Sequence constructor with all parameters as input
	 * @param newbornTypes
	 * @param growingTypes
	 * @param dyingTypes
	 * @param timestamps
	 * @param firstTimestamp
	 * @param lastTimestamp
	 * @param numberOfFrames
	 */
	public Sequence(String[] newbornTypes, String[] growingTypes, String[] dyingTypes, int firstTimestamp, int lastTimestamp, int numberOfFrames) {
		this.newbornTypes 	= newbornTypes;
		this.growingTypes	= growingTypes;
		this.dyingTypes		= dyingTypes;
		this.firstTimestamp	= firstTimestamp;
		this.lastTimestamp	= lastTimestamp;
		this.numberOfFrames	= numberOfFrames;
		
	}

    /**
     * Sequence constructor that infers the ageing of Types and Timestamps from previous Sequence
	 * @param typeNames
	 * @param timestamps
	 * @param previousSequence
	 */
	public Sequence(String[] typeNames, int[][] timestamps, Sequence previousSequence) {
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
		 
		this.numberOfFrames = timestamps.length;
		
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
	 * @param filter the filter to use
	 * @param containsFilter include or exclude filter arguments
	 * 
	 * @return the filteredSequence
	 */
	public Sequence filterSequence(String[] filter, boolean containsFilter){
		
		String[] tmpNewbornTypes;
		String[] tmpGrowingTypes;
		String[] tmpDyingTypes;
		if (containsFilter) {
			
			tmpNewbornTypes = containsPartsOf(this.getNewbornTypes(), filter);
			tmpGrowingTypes = containsPartsOf(this.getGrowingTypes(), filter);
			tmpDyingTypes = containsPartsOf(this.getDyingTypes(), filter);
			
		}else {
			
			tmpNewbornTypes = doesNotContainPartsOf(this.getNewbornTypes(), filter);
			tmpGrowingTypes = doesNotContainPartsOf(this.getGrowingTypes(), filter);
			tmpDyingTypes = doesNotContainPartsOf(this.getDyingTypes(), filter);
			
		}
		Sequence filteredSequence = new Sequence(tmpNewbornTypes, tmpGrowingTypes, tmpDyingTypes, this.getFirstTimestamp(), this.getLastTimestamp(), this.getNumberOfFrames());

		return filteredSequence;
		
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
