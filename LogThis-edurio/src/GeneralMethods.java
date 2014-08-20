import java.text.SimpleDateFormat;
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
	 * @param listWithDuplicates
	 * @return cleaned List
	 */
	public static List<String> removeDuplicate(List<String> listWithDuplicates){
        
        Set<String> setWithDuplicates = new HashSet<String>(listWithDuplicates);

        List<String> cleanedList = new ArrayList<String>(setWithDuplicates);
        
        return cleanedList;
    }
	
	/**
	 * @param neededAmountOfDigits
	 * @param number
	 * @return result
	 */
	public static String addLeadingZeros(int neededAmountOfDigits, int number) {
		
		int neededDigits = String.valueOf(neededAmountOfDigits).length();
		int currentDigits = String.valueOf(number).length();
		
		String result = "";
		for (int i = 0; i < neededDigits - currentDigits; i++) {
			result += "0"; 	
		}
		
		result += String.valueOf(number);
		return result;
	}
	
	/**
	 * Formatting the duration into a String containing numbers and time declarations
	 * @param duration
	 * @return formattedOutputTime
	 */
	public static String formatTime(int duration) {

//		String dateHours = new SimpleDateFormat("h").format(duration); // find some other way to present hours. it is always the first hour of the day ... 
		String dateMinutes= new SimpleDateFormat("m").format(duration);
		String dateSeconds= new SimpleDateFormat("s").format(duration);
		String dateMilliseconds = new SimpleDateFormat("S").format(duration);
		
		String formattedOutputTime = "";
		String minSingle = " minutes";
		String secSingle = " seconds";
		String milliSingle = " milliseconds";
		
		if (dateMinutes.equals("1")) {
		
			minSingle = " minute";
			
			if (dateSeconds.equals("1") && dateMilliseconds.equals("0")) {

				secSingle = " second";
				
				if (dateMilliseconds.equals("1")) {
		
					milliSingle = " millisecond";
					
				}
			}
		}
		
		if (dateMinutes.equals("0")) {
			
			if (dateSeconds.equals("0") && dateMilliseconds.length() <= 2) {
				
				if (dateMilliseconds.equals("0")) {
					
				} else {
					
					formattedOutputTime = dateMilliseconds + milliSingle;
				}
				
			} else {
				
				formattedOutputTime = dateSeconds + "." + dateMilliseconds + secSingle;
			}
			
		} else {
			
			formattedOutputTime = dateMinutes + minSingle + ", " + dateSeconds + "." + dateMilliseconds + secSingle;
		}
		
		return formattedOutputTime;
	}
}
