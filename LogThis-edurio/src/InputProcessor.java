import java.io.*;
import java.util.*;

public class InputProcessor
{
	public static Scene fileReader() throws IOException
	{
		long startTime = System.currentTimeMillis();
		
//		FileReader fr = new FileReader("file/LogManager-short.dat");
		FileReader fr = new FileReader("file/LogManager-live-tool-2.dat");
//		FileReader fr = new FileReader("file/LogManager-example.dat");
//		FileReader fr = new FileReader("file/LogManager-long.dat");
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		List<Tick> tmpTickList = new ArrayList<Tick>();
		
		/*
		 * Read file, split lines into Type and Timestamp, generate Ticks
		 */
		while ( (line = br.readLine()) != null )
		{
			
			String[] parts = line.split(";",-1);
			int tmpTimestamp = Integer.parseInt(parts[0]);
			Tick tmpTick = new Tick(tmpTimestamp, parts[1], parts[2]);
			tmpTickList.add(tmpTick);
			
		}
		br.close();
		/*
		 * filtering out special hashmarks and adding their content to mark before
		 */
		int lastHashMark = 0;
		for (int i = 0; i < tmpTickList.size(); i++) {
			Tick currentTick = tmpTickList.get(i);
			
			if (currentTick.getType().compareTo("########") == 0) {
				
				lastHashMark = i;
				
			} else if (currentTick.getType().compareTo("#+#+#+#+") == 0) {
				
				if (lastHashMark == 0) {				// if first mark is special hash
					
					tmpTickList.remove(i);				// remove it
					
				} else {
					
					tmpTickList.remove(lastHashMark);	// otherwise remove last normal hash
					currentTick.setType("########");	// and convert special to normal hash
														// thus adding special object to normal
					lastHashMark = i-1;					// set new hashmark
				}
			}
			
		}
		List<Sequence> sequenceList = structureCreator(tmpTickList);
		
		Sequence[] sequenceArray = new Sequence[sequenceList.size()]; 
		sequenceArray = sequenceList.toArray(sequenceArray);
		
		Scene sceneOfInput = new Scene(sequenceArray);
		
		System.out.println((System.currentTimeMillis()-startTime)+" milliseconds to read log");
		return sceneOfInput;
	}

		
	/**
	 * @param tmpTickList
	 */
	private static List<Sequence> structureCreator(List<Tick> tmpTickList) {

		/*
		 * transform List of Ticks to Frames
		 */
	List<Tick> tickList = new ArrayList<Tick>();
	List<Frame> frameList = new ArrayList<Frame>();
	List<Sequence> sequenceList = new ArrayList<Sequence>();
	
		for (int i = 0; i < tmpTickList.size(); i++) {
			Tick currentTick = tmpTickList.get(i);
			
			if ( currentTick.getType().compareTo("########") == 0){ // currentTick equal to ########
				/*
				 * add Frames to frameList
				 */
				Tick[] tickArray = new Tick[tickList.size()]; 
				tickArray = tickList.toArray(tickArray);
				tickList.clear();
				Frame tmpFrame = new Frame(tickArray);
				frameList.add(tmpFrame);
				
			} else {

				tickList.add(currentTick);
				
			}
		}
		
		/*
		 * Arrange Frames into Sequences and create a Scene with them
		 */
		List<Frame> listOfFrames = new ArrayList<Frame>();
		
		Frame stopFrame = new Frame();
		listOfFrames.add(frameList.get(0));			// always start the listOfFrames with first frame
		for (int i=0; i<frameList.size(); i++){
			
			Frame frameA = frameList.get(i);
			Frame frameB;
			if (frameList.size()-1==i){
				frameB = stopFrame;
			} else {
				frameB = frameList.get(i+1);
			}
			
		
			if(frameA.equalTo(frameB)){
				listOfFrames.add(frameB);		// add duplicate frames to listOfFrames
				
			} else {
				
				int frameSize			= frameA.getArrayLength();
				int sequenceLength		= listOfFrames.size();
				int[][] timestampArray	= new int[sequenceLength][frameSize]; 
				
				String[] typeArray		= frameA.getConcatenatedTypes();
				
				for (int j=0; j<sequenceLength; j++){		// for every Frame on the listOfFrames
					
					Frame tmpFrame	= listOfFrames.get(j);	// get the Frame
					timestampArray[j] = tmpFrame.getTimestamps();	// add every Timestamp in this Frame
					
				}
				
				Sequence prevSequence;
				if (sequenceList.isEmpty()) {
					prevSequence = new Sequence();
				} else {
					prevSequence = sequenceList.get(sequenceList.size()-1);
				}
				Sequence tmpSequence = new Sequence(typeArray, timestampArray, prevSequence); // create Sequence out of Types and Times
				
				sequenceList.add(tmpSequence); 					// add Sequence to List
				
				listOfFrames.clear(); 				// rinse and repeat
				listOfFrames.add(frameB);		// but add FrameB as a startingpoint for the next iteration
			}
		}

		return sequenceList;
	}
	
	/**
	 * @param scene the scene to filter
	 * @param filter the filter to use
	 * @param containsFilter include or exclude filter arguments
	 * 
	 * @return the filteredSequence
	 */
	public static Scene filterScene(Scene scene, String[] filter, boolean containsFilter){
		
		Sequence[] originalSequences	= scene.getSequences();
		List<Sequence> sequenceList		= new ArrayList<Sequence>();
		List<Sequence> aggregatedList	= new ArrayList<Sequence>();
		
		for (int i = 0; i < originalSequences.length; i++) {
			
			Sequence tmpSequence = originalSequences[i].filterSequence(filter, containsFilter);
			sequenceList.add(tmpSequence);
			
		}

		if (originalSequences.length != sequenceList.size()) {
		
			System.out.println("ALARM Ungleiche Größe!");
		}
//		aggregatedList = sequenceList;
		
		Sequence tmpBaseSequence = sequenceList.get(0);
		
		for (int i = 1; i < sequenceList.size(); i++) {
			
			if (tmpBaseSequence.equalTo(sequenceList.get(i))) {
				
				tmpBaseSequence.setLastTimestamp(sequenceList.get(i).getLastTimestamp());
				tmpBaseSequence.setNumberOfFrames(tmpBaseSequence.getNumberOfFrames()+sequenceList.get(i).getNumberOfFrames());
				
				if (sequenceList.get(i).getConcatenatedTypeNames().length != 0){
					
					String[] tmpTypes = sequenceList.get(i).getConcatenatedTypeNames();
					
					for (int j = 0; j < tmpTypes.length; j++) {	
						
						// increase age from previousSequence by numberOfFrames	
						tmpBaseSequence.setElementOfTypeToAgeMap(tmpTypes[j], sequenceList.get(i).getNumberOfFrames() + tmpBaseSequence.getElementOfTypeToAgeMap(tmpTypes[j]));	

					}
				}
				
			} else {
				
				aggregatedList.add(tmpBaseSequence);
				tmpBaseSequence = sequenceList.get(i);
				
			}
		}
		
		aggregatedList.add(tmpBaseSequence);
		
		/*
		 * added a final sequence to set all types to dying
		 */
		Sequence nextToLastSeq = aggregatedList.get(aggregatedList.size()-1);
		Sequence lastSequence = new Sequence();
		lastSequence.setDyingTypes(nextToLastSeq.getConcatenatedTypeNames());
		lastSequence.setFirstTimestamp(nextToLastSeq.getFirstTimestamp());
		lastSequence.setLastTimestamp(nextToLastSeq.getLastTimestamp());
		lastSequence.setNumberOfFrames(1);
		lastSequence.setTypeToAgeMap(nextToLastSeq.getTypeToAgeMap());
		
		aggregatedList.add(lastSequence);
		
		Sequence[] filteredSequences	= new Sequence[aggregatedList.size()];
		filteredSequences = aggregatedList.toArray(filteredSequences);
		
		Scene filteredScene = new Scene(filteredSequences, scene.getNumberOfFrames(), scene.getStartTime(), scene.getEndTime());
		
		return filteredScene;
		
	}
}