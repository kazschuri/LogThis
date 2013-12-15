import java.util.HashMap;
import java.util.Map;


public class SequenceAgeingAnalyzer {
	
	public static void cutAndDiff(Scene scene){
		
		Sequence[] sequences = scene.getSequences();
		
		String[][] oldTypes = new String[sequences.length-1][];
		String[][] sameTypes = new String[sequences.length-1][];
		String[][] newTypes = new String[sequences.length-1][];
				
		
		for (int i = 0; i < sequences.length-1; i++) {
			
//			String[][] sequenceDifference;
			
//			sequenceDifference = sequences[i].diff(sequences[i+1]);
			
//			oldTypes[i] = sequenceDifference[0];
//			sameTypes[i] = sequenceDifference[1];
//			newTypes[i] = sequenceDifference[2];
			
			oldTypes[i] = sequences[i].getDyingTypes();
			sameTypes[i] = sequences[i].getGrowingTypes();
			newTypes[i] = sequences[i].getNewbornTypes();
			
		}
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for (int j = 0; j < sequences.length-1; j++) {
			
			System.out.println("_________________________________________________________________________");
			System.out.println();
			System.out.println("Sequence "+j+" zu Sequence "+(j+1)+":");
			System.out.println();
			
			if (sameTypes[j].length!=0){
				System.out.println("Growing Types:");
				
				for (int k = 0; k < sameTypes[j].length; k++) {
					if(countMap.containsKey(sameTypes[j][k])){
						countMap.put(sameTypes[j][k], countMap.get(sameTypes[j][k])+1);
					} else {
						countMap.put(sameTypes[j][k], 1);
					}
					
					System.out.println(" - "+sameTypes[j][k]+" ("+countMap.get(sameTypes[j][k])+")");
					
				}
				System.out.println();
			}
				
			if (newTypes[j].length!=0){
				
				System.out.println("Newborn Types: ");
				
				for (int k = 0; k < newTypes[j].length; k++) {
					if(countMap.containsKey(newTypes[j][k])){
						countMap.put(newTypes[j][k], countMap.get(newTypes[j][k])+1);	
					} else {
						countMap.put(newTypes[j][k], 1);
					}
					
					System.out.println(" - "+newTypes[j][k]+" ("+countMap.get(newTypes[j][k])+")");
				}
				System.out.println();
			}
			
			if (oldTypes[j].length!=0){
				
				System.out.println("Dying Types:");
				
				for (int k = 0; k < oldTypes[j].length; k++) {
					System.out.println(" - "+oldTypes[j][k]+" ("+countMap.get(oldTypes[j][k])+")");
					
					if(countMap.containsKey(oldTypes[j][k])){
						countMap.put(oldTypes[j][k], 0);
					} else {
						countMap.put(oldTypes[j][k], 0);
					}
					
					
				}
			}
		}
//		for (int i = 0; i < newTypes.length; i++) {
//			System.out.println();
//			System.out.println("    Sequence " + i );
//			System.out.println("----------------------");
//			
//			if (newTypes[i].length > 0) {
//				
//				for (int j = 0; j < newTypes[i].length; j++) {
//					
//					System.out.println(newTypes[i][j]);
//					
//				}
//			}
//		}
	}
	
}
