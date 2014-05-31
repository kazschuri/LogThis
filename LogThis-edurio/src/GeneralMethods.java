import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GeneralMethods {

	/**
	 * finding an element in an array
	 * 
	 * @param haystack the haystack to search in
	 * @param needle the needle to search for
	 * 
	 * @return true if found
	 */
	public static boolean containsElement(String[] haystack, String needle) {
		
		boolean found = false;
		
		for (int i = 0; i < haystack.length; i++) {
			
			if (haystack[i].contains(needle)) {
				
				found = true;
				break;
				
			}
		}
		
		return found;
	}
	/**
	 * TODO
	 * @param input
	 * @return
	 */
	public static List<String> removeDuplicate(List<String> input){
        
        Set<String> inputSet = new HashSet<String>(input);

        List<String> output = new ArrayList<String>(inputSet);
        
        return output;
    }
	
	/**
	 * @param size
	 * @param synTemplateName
	 * @return
	 * TODO
	 */
	public static String addLeadingZeros(int size, int number) {
		
		int neededDigits = String.valueOf(size).length();
		int currentDigits = String.valueOf(number).length();
		
		String result = "";
		for (int i = 0; i < neededDigits - currentDigits; i++) {
			result += "0"; 	
		}
		
		result += String.valueOf(number);
		return result;
	}
}
