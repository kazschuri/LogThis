
public class SynTemplate {

	private Node template;
	private Node[][] mustUseTrees;
	private Node[][] canUseTrees;
	private Node[] slotFillers;
	private LinkedConditions linConds;
	private String[] topics;
	
	/**
	 * @param template
	 * @param mustUseTrees
	 * @param canUseTrees
	 * @param slotFillers
	 * @param linConds
	 * @param topics
	 */
	public SynTemplate(Node template, Node[][] mustUseTrees,
			Node[][] canUseTrees, Node[] slotFillers, LinkedConditions linConds,
			String[] topics) {
		super();
		this.template = template;
		this.mustUseTrees = mustUseTrees;
		this.canUseTrees = canUseTrees;
		this.slotFillers = slotFillers;
		this.linConds = linConds;
		this.topics = topics;
	}
	
	public SynTemplate() {
		super();
		this.template = new Node();

		Node tmpNode = new Node();
    	Node[] tmpNodes1 = {tmpNode};
    	Node[][] tmpNodes2 = {tmpNodes1}; 
		this.mustUseTrees = tmpNodes2;
		this.canUseTrees = tmpNodes2;
		this.slotFillers = tmpNodes1;
		this.linConds = new LinkedConditions();
		String[] tmpStrings = {""};
		this.topics = tmpStrings;
	}

//	public void adjoinToTemplate(Node... nodes) {
//		
//		Node tmpNode = new Node();
//		tmpNode.
//		
//	}
	/**
	 * @return the template
	 */
	public Node getTemplate() {
	
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(Node template) {
	
		this.template = template;
	}

	/**
	 * @return the mustUseTrees
	 */
	public Node[][] getMustUseTrees() {
	
		return mustUseTrees;
	}

	/**
	 * @param mustUseTrees the mustUseTrees to set
	 */
	public void setMustUseTrees(Node[][] mustUseTrees) {
	
		this.mustUseTrees = mustUseTrees;
	}

	/**
	 * @return the canUseTrees
	 */
	public Node[][] getCanUseTrees() {
	
		return canUseTrees;
	}

	/**
	 * @param canUseTrees the canUseTrees to set
	 */
	public void setCanUseTrees(Node[][] canUseTrees) {
	
		this.canUseTrees = canUseTrees;
	}

	/**
	 * @return the slotFillers
	 */
	public Node[] getSlotFillers() {
	
		return slotFillers;
	}

	/**
	 * @param slotFillers the slotFillers to set
	 */
	public void setSlotFillers(Node[] slotFillers) {
	
		this.slotFillers = slotFillers;
	}

	/**
	 * @return the linConds
	 */
	public LinkedConditions getLinConds() {
	
		return linConds;
	}

	/**
	 * @param linConds the linConds to set
	 */
	public void setLinConds(LinkedConditions linConds) {
	
		this.linConds = linConds;
	}

	/**
	 * @return the topics
	 */
	public String[] getTopics() {
	
		return topics;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(String[] topics) {
	
		this.topics = topics;
	}

}
