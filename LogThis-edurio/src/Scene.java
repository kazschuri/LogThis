import java.text.SimpleDateFormat;



public class Scene {
    
    private Sequence[] sequences;
    private int numberOfFrames;
    private int startTime;
    private int endTime;

    public Scene(){
    	Sequence tmpSeq		= new Sequence();
    	Sequence[] tmpSequences = {tmpSeq};
    	this.sequences		= tmpSequences;
    	this.numberOfFrames = 0;
    	this.startTime		= 0;
    	this.endTime		= 0;
    }
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

	//TODO
	/**
	 * output the difference between the sequences of a scene
	 */
	public void showAgeingAt(int index){
		
		long startTime = System.currentTimeMillis();
		Sequence[] sequences = this.getSequences();

		String[][] dyingTypes	= new String[sequences.length][];
		String[][] growingTypes = new String[sequences.length][];
		String[][] newbornTypes = new String[sequences.length][];

		int stop = index + 1;
		
		if (stop > sequences.length) {
			
			stop = sequences.length;
		}

		for (int i = index; i < stop; i++) {					// get the types for each sequence

			dyingTypes[i]	= sequences[i].getDyingTypes();
			growingTypes[i] = sequences[i].getGrowingTypes();
			newbornTypes[i] = sequences[i].getNewbornTypes();

		}
		
		for (int j = index; j < stop; j++) {

			int sizeOfSequence = sequences[j].getNumberOfFrames();

			System.out.println("_________________________________________________________________________");
			System.out.println();
			System.out.println("Sequence "+(j-1)+" zu Sequence "+j+" ("+sizeOfSequence+" Frames):");
			System.out.println();

			if (growingTypes[j].length!=0){
				System.out.println("Growing Types:");

				for (int k = 0; k < growingTypes[j].length; k++) {	

					System.out.println(" - "+growingTypes[j][k]+								// show all growing types from this sequence
							" ("+sequences[j].getTypeToAgeMap().get(growingTypes[j][k])+")"); 

				}
				System.out.println();
			}

			if (newbornTypes[j].length!=0){

				System.out.println("Newborn Types: ");

				for (int k = 0; k < newbornTypes[j].length; k++) {

					System.out.println(" - "+newbornTypes[j][k]+								// show all newborn types from this sequence
							" ("+sequences[j].getTypeToAgeMap().get(newbornTypes[j][k])+")");
				}

				System.out.println();
			}

			if (dyingTypes[j].length!=0){

				System.out.println("Dying Types:");

				for (int k = 0; k < dyingTypes[j].length; k++) {

					System.out.println(" - "+dyingTypes[j][k]+									// show all dying types from this sequence
							" ("+sequences[j].getTypeToAgeMap().get(dyingTypes[j][k])+")");

				}
			}
		}
		System.out.println();
		System.out.println((System.currentTimeMillis()-startTime)+" milliseconds to show output log");
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
	
	/**
	 * TODO
	 * @param sequenceAt
	 * @return
	 */
	public String elapsedTime(Sequence sequence, Boolean inMinutes) {
		
		String outputTime = "";
		
		int duration = sequence.getLastTimestamp()-sequence.getFirstTimestamp();
		outputTime = duration + " milliseconds";
		
		if (inMinutes) {
			
//			String dateHours = new SimpleDateFormat("h").format(duration); // find some other way to present hours. it is always the first hour of the day ... 
			String dateMinutes= new SimpleDateFormat("m").format(duration);
			String dateSeconds= new SimpleDateFormat("s").format(duration);
			String dateMilliseconds = new SimpleDateFormat("S").format(duration);
			
			String formattedOutputTime = "";
			String minSingle = " minutes";
			String secSingle = " seconds";
			String milliSingle = " milliseconds";
			
			if (dateMinutes.equals("1")) {
			
				minSingle = " minute";
				
				if (dateSeconds.equals("1")) {

					secSingle = " second";
					
					if (dateMilliseconds.equals("1")) {
			
						milliSingle = " millisecond";
						
					}
				}
			}
			
			if (dateMinutes.equals("0")) {
				
				if (dateSeconds.equals("0")) {
				
					if (dateMilliseconds.equals("0")) {
						
					} else {
						
						formattedOutputTime = dateMilliseconds + milliSingle;
					}
					
				} else {
					
					formattedOutputTime = dateSeconds + secSingle + " and " + dateMilliseconds + milliSingle;
				}
				
			} else {
				
				formattedOutputTime = dateMinutes + minSingle + ", " + dateSeconds + secSingle + " and " + dateMilliseconds + milliSingle;
			}
			outputTime = formattedOutputTime;
//			System.out.println(duration + " = " + formattedOutputTime);
		}
		
		return outputTime;
	}
    
}
