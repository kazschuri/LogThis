import java.io.*;
import java.util.*;

public class ReadFile
{
	public static Scene reader() throws IOException
	{
		FileReader fr = new FileReader("file//LogManager-short.dat");
//		FileReader fr = new FileReader("file/LogManager-long.dat");
		BufferedReader br = new BufferedReader(fr);
		
		String line = "";
		List<Tick> tickList = new ArrayList<Tick>();
		List<Frame> frameList = new ArrayList<Frame>();
		List<Sequence> sequenceList = new ArrayList<Sequence>();
		
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
		
		/*
		 * transform List of Ticks to Frames
		 */
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
		
		br.close();
		
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
		Sequence[] sequenceArray = new Sequence[sequenceList.size()]; 
		sequenceArray = sequenceList.toArray(sequenceArray);
		
		Scene sceneOfInput = new Scene(sequenceArray);
		
		return sceneOfInput;
	}
}