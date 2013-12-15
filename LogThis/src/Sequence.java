import java.util.ArrayList;
import java.util.List;


public class Sequence {

    private String[] newbornTypes;
    private String[] growingTypes;
    private String[] dyingTypes;
    private int[][] timestamps;
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


//	/**
//	 * compare two Sequences A.diff(B) and return
//	 * three Arrays of Types
//	 * 
//	 * First: Types only found in A
//	 * Second: Types found in both
//	 * Third: Types only found in B
//	 * 
//	 * @param sequenceB the sequence with which to compare for difference
//	 * @return the arraysOfDifference 
//	 */
//	public String[][] diff ( Sequence sequenceB ) {
//		
//		List<String> typesOfAList	= new ArrayList<String>();
//		List<String> typesOfBList	= new ArrayList<String>();
//		
//		List<String> typesOfA	= new ArrayList<String>();
//		List<String> typesOfAB	= new ArrayList<String>();
//		List<String> typesOfB 	= new ArrayList<String>();
//		
//		for (int i = 0; i < this.getTypeNames().length; i++) {
//			
//			typesOfAList.add(this.getTypeNames()[i]);
//			
//		}
//		
//		for (int i = 0; i < sequenceB.getTypeNames().length; i++) {
//			
//			typesOfBList.add(sequenceB.getTypeNames()[i]);
//			
//		}
//		
//		for (int i = 0; i < typesOfAList.size(); i++) {			// look at each element of oldTypeList
//			
//			if (typesOfBList.contains(typesOfAList.get(i))) {	// if contained in newTypeList
//				
//				typesOfAB.add(typesOfAList.get(i));				// add to List with elements that stay the same
//				
//			} else {
//				
//				typesOfA.add(typesOfAList.get(i));				// else it is one of the elements that are only in old list
//				
//			}
//		}
//		
//		for (int i = 0; i < typesOfBList.size(); i++) {			
//			
//			if (!typesOfAB.contains(typesOfBList.get(i))) {		// find elements that are in new but not in same
//				
//				typesOfB.add(typesOfBList.get(i));				// they are from the new sequence
//				
//			}
//		}
//		
//		String[][] arraysOfDifference = new String[3][];
//		
//		arraysOfDifference[0] = new String[typesOfA.size()];
//		arraysOfDifference[1] = new String[typesOfAB.size()];
//		arraysOfDifference[2] = new String[typesOfB.size()];
//
//		arraysOfDifference[0] = typesOfA.toArray(arraysOfDifference[0]);
//		arraysOfDifference[1] = typesOfAB.toArray(arraysOfDifference[1]);
//		arraysOfDifference[2] = typesOfB.toArray(arraysOfDifference[2]);
//		
//		return arraysOfDifference;
//		
//	}


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
    
    
}
