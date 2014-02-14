
public class Condition {

	String category 	= "";
	String subCategory	= "";
	String element		= "";
	boolean shouldExist = true;
	
	/**
	 * @param category
	 * @param subCategory
	 * @param element
	 * @param exist
	 */
	public Condition(String category, String subCategory, String element,
			boolean exist) {
		super();
		this.category = category;
		this.subCategory = subCategory;
		this.element = element;
		this.shouldExist = exist;
	}

	public Condition() {
		super();
		
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
	
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
	
		this.category = category;
	}

	/**
	 * @return the subCategory
	 */
	public String getSubCategory() {
	
		return subCategory;
	}

	/**
	 * @param subCategory the subCategory to set
	 */
	public void setSubCategory(String subCategory) {
	
		this.subCategory = subCategory;
	}

	/**
	 * @return the element
	 */
	public String getElement() {
	
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(String element) {
	
		this.element = element;
	}

	/**
	 * @return if the element shouldExist
	 */
	public boolean isShouldExist() {
	
		return shouldExist;
	}

	/**
	 * @param shouldExist the flag to set, if element should exist
	 */
	public void setShouldExist(boolean shouldExist) {
	
		this.shouldExist = shouldExist;
	}

	
	/**
	 * selects the category and the subcategory that needs to be searched
	 * 
	 * @param sequence the sequence to be searched
	 * @param shouldExist the flag if the value should be in the sequence or not
	 */
	public boolean isFulfilled(Sequence sequence, boolean shouldExist) {
		
		boolean fulfilled = false;
		boolean result = false;
		
		if (this.category.equals("Sequence")) {
			
			String[] haystack = {""};
			
			if (this.subCategory.equals("dying")) {
				
				haystack = sequence.getDyingTypes();
				
			}else if (this.subCategory.equals("growing")) {
				
				haystack = sequence.getGrowingTypes();
				
			}else if (this.subCategory.equals("newborn")) {
				
				haystack = sequence.getNewbornTypes();
				
			}else if (this.subCategory.equals("current")) {
				
				haystack = sequence.getConcatenatedTypeNames();
				
			}
			
			result = containsElement(haystack, element);
			
		}
		fulfilled = result == shouldExist;
		return fulfilled;
		
	}

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

}
