
public class Condition {

	String category 	= "";
	String subCategory	= "";
	String element		= "";
	boolean exist 		= false;
	
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
		this.exist = exist;
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
	 * @return the exist
	 */
	public boolean isExist() {
	
		return exist;
	}

	/**
	 * @param exist the exist to set
	 */
	public void setExist(boolean exist) {
	
		this.exist = exist;
	}


}
