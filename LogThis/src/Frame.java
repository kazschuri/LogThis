
public class Frame {
	
	private Tick[] tickArray;
	
	/**
	 * empty frame
	 */
	public Frame() {
		
		Tick nullTick = new Tick();
		Tick[] nullTickArray = new Tick[1];
		nullTickArray[0] = nullTick;
		this.tickArray = nullTickArray;
		
	}


	/**
	 * @param tickArray
	 */
	public Frame(Tick[] tickArray) {
		
		this.tickArray = tickArray;
		
	}


	public int getArrayLength() {
		
		return this.tickArray.length;
		
	}


	/**
	 * @return the typesOfThisFrame
	 */
	public String[] getTypes() {
		
		String[] typesOfThisFrame = new String[this.tickArray.length];
		
		for (int i=0; i<this.tickArray.length; i++) {
			
			typesOfThisFrame[i] = (this.tickArray[i].getType()); 
		}
		
		return typesOfThisFrame;
	}

	/**
	 * @return the parentTypesOfThisFrame
	 */
	public String[] getParentTypes() {
		
		String[] parentTypesOfThisFrame = new String[this.tickArray.length];
		
		for (int i=0; i<this.tickArray.length; i++) {
			
			parentTypesOfThisFrame[i] = (this.tickArray[i].getParentType()); 
		}
		
		return parentTypesOfThisFrame;
	}
	/**
	 * @return the concatenatedTypesOfThisFrame
	 */
	public String[] getConcatenatedTypes() {
		
		String[] concatenatedTypesOfThisFrame = new String[this.tickArray.length];
		
		for (int i=0; i<this.tickArray.length; i++) {
			
			concatenatedTypesOfThisFrame[i] = (this.tickArray[i].getConcatenatedTypes()); 
		}
		
		return concatenatedTypesOfThisFrame;
	}
	/**
	 * @return the timestampsOfThisFrame
	 */
	public int[] getTimestamps() {
		
		int[] timestampsOfThisFrame = new int[this.tickArray.length];
		
		for (int i=0; i<this.tickArray.length; i++) {
			
			timestampsOfThisFrame[i] = (this.tickArray[i].getTimestamp());
		}
		
		return timestampsOfThisFrame;
		
	}


	/**
	 * @return single timestamp
	 */
	public int getTimestampAt( int index ) {
		
		return this.tickArray[index].getTimestamp();
		
	}

	
	/**
	 * @param frameB
	 * @return the decision if types match
	 */
	public boolean equalTo( Frame frameB ) {
		
		boolean decision = false;
		
		int sizeOfFrameA = this.tickArray.length;
		
		if (sizeOfFrameA == frameB.tickArray.length){ 		// if size of frames is equal, possible same frame 
			
			decision = true;
			
			String[] typesOfFrameA = this.getConcatenatedTypes();
			String[] typesOfFrameB = frameB.getConcatenatedTypes();
			
			for (int i = 0; i < sizeOfFrameA; i++) {
				
				if (typesOfFrameA[i].compareTo(typesOfFrameB[i]) != 0){	// at first differing type, return false
					
					decision = false;
					break;
					
				}
			}
		}
		
		return decision;
		
	}


	/**
	 * @return the tickArray
	 */
	public Tick[] getTickArray() {
		
		return tickArray;
		
	}

	
	/**
	 * @param tickArray the tickArray to set
	 */
	public void setTickArray(Tick[] tickArray) {
		
		this.tickArray = tickArray;
		
	}


}
