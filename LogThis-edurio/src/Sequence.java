import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sequence {

	private Scene parentScene;
	private int inScenePosition;
	
    private String[] newbornTypes;
    private String[] growingTypes;
    private String[] dyingTypes;
    private Map<String,Integer> typeToAgeMap = new HashMap<String,Integer>();
    
    private int firstTimestamp = 0;
    private int lastTimestamp = 0;
    private int numberOfFrames = 0;
    
    
    
	public Sequence() {
		String[] nullTypeArray = {""};
		this.newbornTypes	= nullTypeArray;
		this.growingTypes	= nullTypeArray;
		this.dyingTypes		= nullTypeArray;
		this.typeToAgeMap.put("", 0);
	}
	
	/**
	 * Constructor for testing purposes
	 * 
	 * @param newbornTypes
	 * @param growingTypes
	 * @param dyingTypes
	 */
	public Sequence(String[] newbornTypes, String[] growingTypes, String[] dyingTypes) {
		
		this.newbornTypes 	= newbornTypes;
		this.growingTypes	= growingTypes;
		this.dyingTypes		= dyingTypes;
		this.typeToAgeMap.put("", 0);
	}
	/**
	 * Sequence constructor with all parameters as input
	 * @param newbornTypes
	 * @param growingTypes
	 * @param dyingTypes
	 * @param typeToAgeMap
	 * @param timestamps
	 * @param firstTimestamp
	 * @param lastTimestamp
	 * @param numberOfFrames
	 */
	public Sequence(String[] newbornTypes, String[] growingTypes, String[] dyingTypes,
					Map<String,Integer> typeToAgeMap, int firstTimestamp, 
					int lastTimestamp, int numberOfFrames)
	{
	
		this.newbornTypes 	= newbornTypes;
		this.growingTypes	= growingTypes;
		this.dyingTypes		= dyingTypes;
		this.typeToAgeMap	= typeToAgeMap;
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
		
		/*
		 * This should already be right. I don't remember, why I had a second check.
		 */
//		for (int i = 0; i < tmpLength; i++) {
//			
//			if (this.firstTimestamp > timestamps[i][0]) {
//			
//				this.firstTimestamp = timestamps[i][0];	
//			
//			}
//			
//			if (this.lastTimestamp > timestamps[i][timestamps[i].length-1]){
//				
//				this.lastTimestamp = timestamps[i][timestamps[i].length-1];
//			}
//						
//		}
		 
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
		
		
		String[] arrayOfA	= new String[typesOfA.size()];
		String[] arrayOfAB	= new String[typesOfAB.size()];
		String[] arrayOfB	= new String[typesOfB.size()];
		
		arrayOfA	= typesOfA.toArray(arrayOfA);
		arrayOfAB	= typesOfAB.toArray(arrayOfAB);
		arrayOfB	= typesOfB.toArray(arrayOfB);
		
		this.newbornTypes	= arrayOfA;
		this.growingTypes	= arrayOfAB;
		this.dyingTypes		= arrayOfB;
		
		if (this.growingTypes.length!=0){
			
			for (int i = 0; i < growingTypes.length; i++) {	
				
				// increase age from previousSequence by numberOfFrames	
				typeToAgeMap.put(growingTypes[i], previousSequence.getTypeToAgeMap().get(growingTypes[i])+numberOfFrames);	

			}
		}
			
		if (newbornTypes.length!=0){
			
			for (int i = 0; i < newbornTypes.length; i++) {
				
				// create new key with age equal to numberOfFrames
				typeToAgeMap.put(newbornTypes[i], numberOfFrames);
				
			}
		}
		
		if (dyingTypes.length!=0){
			
			for (int i = 0; i < dyingTypes.length; i++) {
				// keep age of previousSequence. Type doesn't age any more
				typeToAgeMap.put(dyingTypes[i], previousSequence.getTypeToAgeMap().get(dyingTypes[i]));
				
			}
		}
		
	}


    /**
     * Do two consecutive sequences have the same types, first one can have newborn and dying, second can have only growing
     * N + G == G
     * 
     * @param secondSequence the Sequence to compare to
     * @return decision if Sequences are equal
     * 
     */
    public boolean equalTo(Sequence secondSequence){
    	
    	boolean decision = false;
    	
    	if (secondSequence.getDyingTypes().length == 0) {							// secondSequence can have no dying types, since they die from newborn or growing of firstSequence
    		
    		if (secondSequence.getNewbornTypes().length == 0) {						// secondSequence can have no newborn types, since they add to newborn and growing of firstSequence 
			
	    		String[] tmpTypes = containsPartsOf (this.getConcatenatedTypeNames() , secondSequence.getGrowingTypes(), true); // all growing types of secondSequence that are included in newborn and growing of firstSequence
	    		
	    		if (tmpTypes.length == secondSequence.getGrowingTypes().length) {	// if included types and growing are same number, they must be equal, according to the other ifs
	    			decision = true;												// so sequences are equal
				}
    		}
		}
    	
    	return decision;
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
			
		tmpNewbornTypes	= containsPartsOf(this.getNewbornTypes(), filter, containsFilter);
		tmpGrowingTypes	= containsPartsOf(this.getGrowingTypes(), filter, containsFilter);
		tmpDyingTypes	= containsPartsOf(this.getDyingTypes(), filter, containsFilter);
			
		Sequence filteredSequence = new Sequence(tmpNewbornTypes, tmpGrowingTypes, tmpDyingTypes, this.getTypeToAgeMap(), this.getFirstTimestamp(), this.getLastTimestamp(), this.getNumberOfFrames());

		return filteredSequence;
	}
    
	/**
	 * checks if array of types includes or excludes filter
	 *  
	 * @param oldArray the oldArray which is to check
	 * @param filter the filter with the types to look for (or the ending parts of types)
	 * @param containsFilter the flag which decides, if filter should look for types included or excluded
	 * @return list of types filtered
	 */
	private static String[] containsPartsOf(String[] oldArray, String[] filter, boolean containsFilter){
		
		List<String> typeList = new ArrayList<String>();
		
		for (int i = 0; i < oldArray.length; i++) { 		// for each element of oldArray  
			
			boolean typeNotIncluded = true;
			
			for (int j = 0; j < filter.length; j++) {		
			
				if (oldArray[i].endsWith(filter[j])){		// check if it is included in filter
					
					if (containsFilter) {					// if include filter and type is included
						
						typeList.add(oldArray[i]);			// add to list
						break;								// move to next element
						
					}else {									// if exclude filter
						
						typeNotIncluded = false;			// set flag to false
						
					}
					
				}
				
			}
			
			if (typeNotIncluded && !containsFilter) {		// if exclude filter and element is not included in filter		
				
				typeList.add(oldArray[i]);					// add element to list
				
			}
			
		}
		
		String[] typeArray = new String[typeList.size()]; 
		typeArray = typeList.toArray(typeArray);
		
		return typeArray;
	}

    /**
	 * @return the parentScene
	 */
	public Scene getParentScene() {
	
		return parentScene;
	}

	/**
	 * @param parentScene the parentScene to set
	 */
	public void setParentScene(Scene parentScene) {
	
		this.parentScene = parentScene;
	}

	/**
	 * @return the inScenePosition
	 */
	public int getInScenePosition() {
	
		return inScenePosition;
	}

	/**
	 * @param inScenePosition the inScenePosition to set
	 */
	public void setInScenePosition(int inScenePosition) {
	
		this.inScenePosition = inScenePosition;
	}

	/**
	 * @return the previousSequence
	 */
	public Sequence getPreviousSequence() {
		
		Sequence previousSequence = parentScene.getSequenceAt(inScenePosition-1);
		return previousSequence;
		
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
	 * @return concatenatedTypeNames of all Types
	 */
	public String[] getAllTypeNames() {
		int concatLength = newbornTypes.length + growingTypes.length + dyingTypes.length;
		String[] concatenatedTypeNames = new String[concatLength];
	
		System.arraycopy(newbornTypes, 0, concatenatedTypeNames, 0, newbornTypes.length);
		System.arraycopy(growingTypes, 0, concatenatedTypeNames, newbornTypes.length, growingTypes.length);
		System.arraycopy(dyingTypes, 0, concatenatedTypeNames, newbornTypes.length+growingTypes.length, dyingTypes.length);
		
		return concatenatedTypeNames;
	}

	/**
	 * @return the typeToAgeMap
	 */
	public Map<String, Integer> getTypeToAgeMap() {
		return typeToAgeMap;
	}

	/**
	 * @param typeToAgeMap the typeToAgeMap to set
	 */
	public void setTypeToAgeMap(Map<String, Integer> typeToAgeMap) {
		this.typeToAgeMap = typeToAgeMap;
	}

	/**
	 * @param key the key for the value to get
	 * @return value of key of typeToAgeMap
	 */
	public int getElementOfTypeToAgeMap(String key) {
		return this.typeToAgeMap.get(key);
	}

	/**
	 * @param typeToAgeMap the typeToAgeMap to set
	 */
	public void setElementOfTypeToAgeMap(String key, int value) {
		this.typeToAgeMap.put(key, value);
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
