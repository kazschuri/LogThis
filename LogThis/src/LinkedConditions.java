
public class LinkedConditions {

	Condition[] conditions;
	String[] links;
	
	/**
	 * @param conditions
	 * @param links AND, OR, XOR
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

	
	/**
	 * connects the conditions with the logical operators stored in the links array
	 * the operator to connect condition i and i+1 is link[i]
	 * 
	 * @param sequence the sequence to check
	 * @return the currentResult
	 */
	public boolean checkLinConds(Sequence sequence) {
		
		boolean currentResult = false;
		
		for (int i = 0; i < this.conditions.length; i++) {
			
			boolean newResult = conditions[i].isFulfilled(sequence, conditions[i].isShouldExist());
			
			if (i == 0) {
				
				currentResult = newResult;
				
			}else if (links[i-1].equals("AND")) {

				currentResult = currentResult && newResult;

			}else if (links[i-1].equals("OR")) {

				currentResult = currentResult || newResult;

			}else if (links[i-1].equals("XOR")) {

				currentResult = currentResult ^ newResult;

			}
		}
		
		return currentResult;
	}
	
}
