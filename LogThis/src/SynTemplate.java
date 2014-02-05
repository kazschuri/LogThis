
public class SynTemplate {
	
	private SynTree template;
	private SynTree[] synTrees;
	private SynTree[] slotFillers;
	private Condition[] conditions;
	private String[] topics;

	/**
	 * @param template
	 * @param synTrees
	 * @param slotFillers
	 * @param conditions
	 * @param topics
	 */
	public SynTemplate(SynTree template, SynTree[] synTrees,
			SynTree[] slotFillers, Condition[] conditions, String[] topics) {
		super();
		this.template = template;
		this.synTrees = synTrees;
		this.slotFillers = slotFillers;
		this.conditions = conditions;
		this.topics = topics;
	}

	/**
	 * @return the template
	 */
	public SynTree getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(SynTree template) {
		this.template = template;
	}

	/**
	 * @return the synTrees
	 */
	public SynTree[] getSynTrees() {
		return synTrees;
	}

	/**
	 * @param synTrees the synTrees to set
	 */
	public void setSynTrees(SynTree[] synTrees) {
		this.synTrees = synTrees;
	}

	/**
	 * @return the slotFillers
	 */
	public SynTree[] getSlotFillers() {
		return slotFillers;
	}

	/**
	 * @param slotFillers the slotFillers to set
	 */
	public void setSlotFillers(SynTree[] slotFillers) {
		this.slotFillers = slotFillers;
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
