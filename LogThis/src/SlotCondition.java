
public class SlotCondition {

	private String name = "";
	private String category = "";
	private String element = "";
	
	/**
	 * @param name
	 * @param category
	 * @param element
	 */
	public SlotCondition(String name, String category, String element) {
		super();
		this.name = name;
		this.category = category;
		this.element = element;
	}

	//TODO
	public String fillSlot(Sequence sequence) {
		
		String filler = "";
		
		if (category.equalsIgnoreCase("Sequence")) {
			
			if (this.element.equalsIgnoreCase("start")) {
				
				filler = String.valueOf(sequence.getFirstTimestamp());
				
			} else if (this.element.equalsIgnoreCase("stop")) {
				
				filler = String.valueOf(sequence.getLastTimestamp());
			}
						
		} else {
			
			boolean elementIsFound = false;
			String[] haystack = {""};
			
			if (this.category.equals("dying")) {
				
				haystack = sequence.getDyingTypes();
				
			}else if (this.category.equals("growing")) {
				
				haystack = sequence.getGrowingTypes();
				
			}else if (this.category.equals("newborn")) {
				
				haystack = sequence.getNewbornTypes();
				
			}else if (this.category.equals("current")) {
				
				haystack = sequence.getConcatenatedTypeNames();
			}
			
			elementIsFound = GeneralMethods.containsElement(haystack, this.element);	// look in appropriate category for element

			if (elementIsFound) {

				filler = String.valueOf(sequence.getElementOfTypeToAgeMap(this.element));
			}

		}

		return filler;
		
	}
	/**
	 * @return the name
	 */
	public String getName() {
	
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	
		this.name = name;
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
	
	
}
