
public class Condition {

	String 	category 	= "";
	String 	subCategory	= "";
	String 	element		= "";
	int		time		= 0;
	boolean shouldExist = true;
	
	/**
	 * @param category
	 * @param subCategory
	 * @param element
	 * @param time
	 * @param exist
	 */
	public Condition(String category, String subCategory, String element,
			int time, boolean exist) {
		super();
		this.category = category;
		this.subCategory = subCategory;
		this.element = element;
		this.time = time;
		this.shouldExist = exist;
	}

	public Condition() {
		super();
		
	}

	
	/**
	 * selects the category and the subcategory as haystack and searches for the element
	 * 
	 * @param sequence the sequence to be searched
	 * @param shouldExist the flag if the value should be in the sequence or not
	 */
	public boolean isElementFulfilled(KnowledgeBase knowledge, Sequence sequence, boolean shouldExist) {
		
		boolean fulfilled = false;
		boolean result = false;
		
		String[] haystack = {""};
		
		if (category.equalsIgnoreCase("Sequence") || 
			category.equalsIgnoreCase("Timestamp")) {
			
			if (subCategory.equalsIgnoreCase("dying")) {
				
				haystack = sequence.getDyingTypes();
				
			} else if (subCategory.equalsIgnoreCase("growing")) {
				
				haystack = sequence.getGrowingTypes();
				
			} else if (subCategory.equalsIgnoreCase("newborn")) {
				
				haystack = sequence.getNewbornTypes();
				
			} else if (subCategory.equalsIgnoreCase("current")) {
				
				haystack = sequence.getConcatenatedTypeNames();
				
			}
				
		} else if (category.equalsIgnoreCase("Knowledge")) {
			
			if(subCategory.equalsIgnoreCase("current")) {
				
				haystack = (String[]) knowledge.currentTopics.toArray(new String[knowledge.currentTopics.size()]);
				
			} else if (subCategory.equalsIgnoreCase("former")) {
				
				haystack = (String[]) knowledge.formerTopics.toArray(new String[knowledge.formerTopics.size()]);
				
			}
		}
		
		result = GeneralMethods.containsElement(haystack, element);	// look in appropriate subcategory for element
		
		if (this.category.equals("Timestamp")) { 		
			
			if (result) {									// timestamp can only be evaluated, if there is a result

				int value = sequence.getElementOfTypeToAgeMap(element);

				if (value >= time && shouldExist) {			// shouldExist = true : value >= time

					fulfilled = true;

				} else if (value < time && !shouldExist) {	// shouldExist = false : value < time

					fulfilled = true;

				} else {

					fulfilled = false;
				}
				
			} else {
				
				fulfilled = false;
			}
						
		} else {
			
			fulfilled = result == shouldExist;				// if element "shouldExist" in subcategory and does -> fulfilled
		}
		
		return fulfilled;
		
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
	 * @return the time
	 */
	public int getTime() {
	
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
	
		this.time = time;
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

}
