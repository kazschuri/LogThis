
public class LinkedConditions {

	Condition[] conditions;
	String[] links;
	
	/**
	 * @param conditions
	 * @param links
	 */
	public LinkedConditions(Condition[] conditions, String[] links) {
		
		super();
		this.conditions = conditions;
		this.links = links;
	}

	public LinkedConditions() {
	
		super();
	
		Condition tmpCond = new Condition();
		Condition[] tmpConds = {tmpCond};
		this.conditions = tmpConds;
		
		String[] tmpStrings = {""};
		this.links = tmpStrings;
	}

	/**
	 * @return the conditions
	 */
	public Condition[] getConditions() {
	
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(Condition[] conditions) {
	
		this.conditions = conditions;
	}

	/**
	 * @return the links
	 */
	public String[] getLinks() {
	
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(String[] links) {
	
		this.links = links;
	}
	
	
}
