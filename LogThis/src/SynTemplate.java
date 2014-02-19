import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynTemplate {

	private Node template;
	private List<List<Node>> mustUseTrees;
	private List<List<Node>> canUseTrees;
	private Node[] slots;
	private LinkedConditions linConds;
	private String[] topics;
	private SlotCondition slotConnections;
	
	/**
	 * @param template
	 * @param mustUseTrees
	 * @param canUseTrees
	 * @param slots
	 * @param linConds
	 * @param topics
	 */
	public SynTemplate(Node template, List<List<Node>> mustUseTrees,
			List<List<Node>> canUseTrees, Node[] slots, LinkedConditions linConds,
			String[] topics) {
		super();
		this.template = template;
		this.mustUseTrees = mustUseTrees;
		this.canUseTrees = canUseTrees;
		this.slots = slots;
		this.linConds = linConds;
		this.topics = topics;
	}
	
	public SynTemplate() {
		super();
		this.template = new Node();

    	List<List<Node>> mustList = new ArrayList<List<Node>>();
		this.mustUseTrees = mustList;
		List<List<Node>> canList = new ArrayList<List<Node>>();
		this.canUseTrees = canList;
		
		Node tmpNode = new Node();
    	Node[] tmpNodes1 = {tmpNode};
		this.slots = tmpNodes1;
		
		this.linConds = new LinkedConditions();
		String[] tmpStrings = {""};
		this.topics = tmpStrings;
	}
	
	/**
	 * picks a random set of all possible permuations of the nodes of a list of lists
	 * 
	 * @param list the list to permute
	 * @return the randomSet
	 */
	public static int[] pickRandomPermutation(List<List<Node>> list){
		
		Random generator = new Random(System.currentTimeMillis());
		
		int[] randomSet = new int[list.size()];
		
		for (int i = 0; i < list.size(); i++) {						// from each list
			
			randomSet[i] = generator.nextInt(list.get(i).size());	// pick one node at random
			
		}
				
		return randomSet;
	}
	
	/**
	 * show and return a random set of all possible permutations of the nodes of a list of lists and
	 * can drop a random number of nodes from the set
	 * 
	 * @param list the list to permute
	 * @param dropNodes the flag if random nodes should be dropped
	 * @return the randomPermutation
	 */
	public static List<Node> displayRandomPermutation(List<List<Node>> list, boolean dropNodes) {
		
		Random generator = new Random(System.currentTimeMillis());
		int[] randomSet = new int[list.size()];
		boolean drop = false;
		
		randomSet = pickRandomPermutation(list);
		
		List<Node> randomPermutation = new ArrayList<Node>();
		
		for (int i = 0; i < randomSet.length; i++) {					// from the randomSet of nodes
			
			if (dropNodes) {
				
				drop = generator.nextBoolean();
			}
			
			if (!drop) {												// decide if it should be dropped
				
				randomPermutation.add(list.get(i).get(randomSet[i]));	// otherwise add it to the result
				list.get(i).get(randomSet[i]).showLeafs();				// and show its leafs
				System.out.print(" / ");
			}
		}
		
		return randomPermutation;
	}
	
	/**
	 * returns a List of all permutations of the nodes of a given list of lists
	 * 
	 * @param list the list to permute
	 * @param startLevel the level of the list on which to start (normally 0)
	 * @param parentElements the element coming from the parent (start with empty element)
	 * @param permutationList the list of permutations (start with an empty list)
	 * @return the permutation list
	 */	
	public static List<List<Node>> getAllPermutationsOf(List<List<Node>> list, int startLevel,
			List<Node> parentElements, List<List<Node>> permutationList) {
		
//		int listSize = list.get(startLevel).size()-1;
		
		for (int i = 0; i < list.get(startLevel).size(); i++) {				// cycle through all nodes of a list

			List<Node> parentNode = new ArrayList<Node>();
			parentNode.addAll(parentElements);								// keep all nodes coming from the list before
			parentNode.add(list.get(startLevel).get(i));					// add the current node
			
			if (startLevel < list.size()-1) {								// if this is not the last list of the list of lists
				
				permutationList = getAllPermutationsOf(list, startLevel+1, 	// check the next list
						parentNode, permutationList);
		
			} else {														// if it is the last list

//				System.out.println(element+liste.get(startIndex).get(i));
				permutationList.add(parentNode);							// just add the current node and its predecessors as a result
				
			}

		}
		return permutationList;
	}
	
	//TODO
	public boolean isApplicable(Sequence sequence) {
		
		boolean fulfilled = false;
		
		fulfilled = this.linConds.checkLinConds(sequence);
		
		return fulfilled;
	}

	/**
	 * adjoins a copy of given nodes into the template
	 * 
	 * @param nodes the nodes to copy an adjoin
	 */
	public void adjoinCopyToTemplate(Node... nodes) {
		
		for (Node tree : nodes) {
			
			this.template.adjoin(Node.copyOf(tree));
		}
	}

	/**
	 * substitutes a copy of given nodes into the template
	 * 
	 * @param nodes the nodes to copy an substitute 
	 */
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
	
	/**
	 * show all Trees that are currently registered in mustUseTrees
	 */
	public void showMustUseTrees() {
		System.out.println();
		System.out.println("MustUseTrees: ");
		for (int i = 0; i < mustUseTrees.size(); i++) {
			
			for (int j = 0; j < mustUseTrees.get(i).size(); j++) {
				
				mustUseTrees.get(i).get(j).showLeafs();
				System.out.print("/ ");
			}
			System.out.println();
		}
	}
	
	/**
	 * show all Trees that are currently registered in canUseTrees
	 */
	public void showCanUseTrees() {
		System.out.println();
		System.out.println("CanUseTrees: ");
		for (int i = 0; i < canUseTrees.size(); i++) {

			for (int j = 0; j < canUseTrees.get(i).size(); j++) {

				canUseTrees.get(i).get(j).showLeafs();
				System.out.print("/ ");
			}
			System.out.println();
		}
	}
	
	/**
	 * method to return a random set of Nodes from all permutations of mustUseTrees
	 *   
	 * @return the resultSet
	 */
	public List<Node> getSetFromMustUseTrees() {
		
		List<Node> resultSet = new ArrayList<Node>();
		
		resultSet = displayRandomPermutation(this.mustUseTrees, false);
		
		return resultSet;		
		
	}
	
	/**
	 * method to return a random set of Nodes from all permutations of canUseTrees
	 * the set is randomly reduced, because the Nodes <b>can</b> be used, and do not have to be used
	 *  
	 * @return the resultSet
	 */
	public List<Node> getSetFromCanUseTrees() {

		List<Node> resultSet = new ArrayList<Node>();

		resultSet = displayRandomPermutation(this.canUseTrees, true);

		return resultSet;		

	}
	
	/**
	 * a List of nodes gets added to mustUseList. All Nodes in this List
	 * are interchangeable alternatives for each other. Otherwise they have
	 * to be added separately.
	 * 
	 * @param list the list to add
	 */
	public void addListToMust(List<Node> list) {
		
		this.mustUseTrees.add(list);
		
	}
	
	/**
	 * a List of nodes gets added to mustUseList. All Nodes in this List
	 * are interchangeable alternatives for each other. Otherwise they have
	 * to be added separately.
	 * 
	 * @param nodes the nodes to add
	 */
	public void addMultipleChoiceToMust(Node... nodes) {
		
		List<Node> tmpList = new ArrayList<Node>();
		for (Node node : nodes) {
			
			tmpList.add(node);
			
		}
		
		this.mustUseTrees.add(tmpList);
		
	}
	
	/**
	 * a List of nodes gets added to canUseList. All Nodes in this List
	 * are interchangeable alternatives for each other. Otherwise they have
	 * to be added separately.
	 * 
	 * @param list the list to add
	 */
	public void addListToCan(List<Node> list) {
		
		this.canUseTrees.add(list);
		
	}
	
	/**
	 * a List of nodes gets added to canUseList. All Nodes in this List
	 * are interchangeable alternatives for each other. Otherwise they have
	 * to be added separately.
	 * 
	 * @param nodes the nodes to add
	 */
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
	
		return slots;
	}

	/**
	 * @param slotFillers the slotFillers to set
	 */
	public void setSlotFillers(Node[] slotFillers) {
	
		this.slots = slotFillers;
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
	 * add one set of condition and link to linConds
	 * 
	 * @param condition the condition to add
	 */
	public void addLinConds(Condition condition, String link) {
		
		this.getLinConds().addCondition(condition);
		this.getLinConds().addLink(link);
		
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
