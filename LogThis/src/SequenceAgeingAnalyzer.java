import java.util.HashMap;
import java.util.Map;


public class SequenceAgeingAnalyzer {
	
	/**
	 * output the difference between the sequences of a scene
	 * 
	 * @param scene
	 */
	public static void diffOutput(Scene scene){
		
		Sequence[] sequences = scene.getSequences();
		
		String[][] dyingTypes = new String[sequences.length-1][];
		String[][] growingTypes = new String[sequences.length-1][];
		String[][] newbornTypes = new String[sequences.length-1][];
				
		
		for (int i = 0; i < sequences.length-1; i++) {
			
			dyingTypes[i] = sequences[i].getDyingTypes();
			growingTypes[i] = sequences[i].getGrowingTypes();
			newbornTypes[i] = sequences[i].getNewbornTypes();
			
		}
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for (int j = 0; j < sequences.length-1; j++) {
			
			int sizeOfSequence = sequences[j].getNumberOfFrames();
			
			System.out.println("_________________________________________________________________________");
			System.out.println();
			System.out.println("Sequence "+j+" ("+sizeOfSequence+" Frames) zu Sequence "+(j+1)+":");
			System.out.println();
			
			if (growingTypes[j].length!=0){
				System.out.println("Growing Types:");
				
				for (int k = 0; k < growingTypes[j].length; k++) {	
					if(countMap.containsKey(growingTypes[j][k])){ 	// if key is available
						// TODO put variable instead of 1
						countMap.put(growingTypes[j][k], countMap.get(growingTypes[j][k])+sizeOfSequence);	// add x to value at key
					} else {
						// TODO put variable instead of 1
						countMap.put(growingTypes[j][k], sizeOfSequence);		// otherwise create new key with value x					
					}
					
					System.out.println(" - "+growingTypes[j][k]+" ("+countMap.get(growingTypes[j][k])+")");
					
				}
				System.out.println();
			}
				
			if (newbornTypes[j].length!=0){
				
				System.out.println("Newborn Types: ");
				
				for (int k = 0; k < newbornTypes[j].length; k++) {
					if(countMap.containsKey(newbornTypes[j][k])){	// if key is available
						// TODO put variable instead of 1
						countMap.put(newbornTypes[j][k], countMap.get(newbornTypes[j][k])+sizeOfSequence);	// add x to value at key
					} else {
						// TODO put variable instead of 1
						countMap.put(newbornTypes[j][k], sizeOfSequence);		// otherwise create new key with value x
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
	}
	
}
