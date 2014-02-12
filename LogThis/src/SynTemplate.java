import java.util.ArrayList;
import java.util.List;

public class SynTemplate {

	private Node template;
	private List<List<Node>> mustUseTrees;
	private List<List<Node>> canUseTrees;
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
	public SynTemplate(Node template, List<List<Node>> mustUseTrees,
			List<List<Node>> canUseTrees, Node[] slotFillers, LinkedConditions linConds,
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

    	List<List<Node>> tmpListe = new ArrayList<List<Node>>();
		this.mustUseTrees = tmpListe;
		this.canUseTrees = tmpListe;
		
		Node tmpNode = new Node();
    	Node[] tmpNodes1 = {tmpNode};
		this.slotFillers = tmpNodes1;
		
		this.linConds = new LinkedConditions();
		String[] tmpStrings = {""};
		this.topics = tmpStrings;
	}

	public void adjoinCopyToTemplate(Node... nodes) {
		
		for (Node tree : nodes) {
			
			this.template.adjoin(Node.copyOf(tree));
		}
	}

	public void substituteCopyInTemplate(Node... nodes) {
		
		for (Node tree : nodes) {
			
			this.template.substitute(Node.copyOf(tree));
		}
	}
	
	/**
	 * show information about <b>slots</b>, <b>terminals</b>, <b>leafs</b> and the full <b>tree form</b>
	 */
	public void showTreeInfo() {
		
		System.out.println();
		System.out.print("Leafs:		");
		this.getTemplate().showLeafs();
		System.out.println();
		System.out.print("Slots:		");
		this.getTemplate().showSlots();
		System.out.println();
		System.out.print("Terminals:	");
		this.getTemplate().showTerminal();
		System.out.println();
		System.out.print("Tree Form:	");
		System.out.println(this.getTemplate().showTree());
		
	}
	
	public void showMustUseTrees() {
		
		for (int i = 0; i < mustUseTrees.size(); i++) {
			
			for (int j = 0; j < mustUseTrees.get(i).size(); j++) {
				
				System.out.print(mustUseTrees.get(i).get(j).getData()+" ");
			}
			System.out.println();
			
		}
	}
	
	public void showCanUseTrees() {
		
		for (int i = 0; i < canUseTrees.size(); i++) {
			
			for (int j = 0; j < canUseTrees.get(i).size(); j++) {
				
				System.out.print(canUseTrees.get(i).get(j).getData()+" ");
			}
			System.out.println();
			
		}
	}
	public void addListToMust(ArrayList<Node> list) {
		
		this.mustUseTrees.add(list);
		
	}
	
	public void addMultipleChoiceToMust(Node... nodes) {
		
		List<Node> tmpList = new ArrayList<Node>();
		for (Node node : nodes) {
			
			tmpList.add(node);
			
		}
		
		this.mustUseTrees.add(tmpList);
		
	}
	public void addListToCan(ArrayList<Node> list) {
		
		this.canUseTrees.add(list);
		
	}
	
	public void addMultipleChoiceToCan(Node... nodes) {
		
		List<Node> tmpList = new ArrayList<Node>();
		for (Node node : nodes) {
			
			tmpList.add(node);
			
		}
		
		this.canUseTrees.add(tmpList);
	}
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
	public List<List<Node>> getMustUseTrees() {
	
		return mustUseTrees;
	}

	/**
	 * @param mustUseTrees the mustUseTrees to set
	 */
	public void setMustUseTrees(List<List<Node>> mustUseTrees) {
	
		this.mustUseTrees = mustUseTrees;
	}

	/**
	 * @return the canUseTrees
	 */
	public List<List<Node>> getCanUseTrees() {
	
		return canUseTrees;
	}
	
//	public void getCanUseLists() {
//		
//		for (int i = 0; i < canUseTrees.size(); i++) {
//			
//			for (int j = 0; j < canUseTrees.get(i).size(); j++) {
//				
//				System.out.print(canUseTrees.get(i).get(j).getData()+" ");
//			}
//			System.out.println();
//			
//		}
//	}

	/**
	 * @param canUseTrees the canUseTrees to set
	 */
	public void setCanUseTrees(List<List<Node>> canUseTrees) {
	
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
