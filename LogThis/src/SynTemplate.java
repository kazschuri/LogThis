import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    	List<List<Node>> mustList = new ArrayList<List<Node>>();
		this.mustUseTrees = mustList;
		List<List<Node>> canList = new ArrayList<List<Node>>();
		this.canUseTrees = canList;
		
		Node tmpNode = new Node();
    	Node[] tmpNodes1 = {tmpNode};
		this.slotFillers = tmpNodes1;
		
		this.linConds = new LinkedConditions();
		String[] tmpStrings = {""};
		this.topics = tmpStrings;
	}
	
	public void buildAllPossibleTrees() {
		
		for (int i = 0; i < mustUseTrees.size(); i++) {
			
			
		}
		
	}

	public static int[] pickRandomPermutation(List<List<Node>> list){
		Random generator = new Random(System.currentTimeMillis());
		
		int[] randomSet = new int[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			
			randomSet[i] = generator.nextInt(list.get(i).size());
			
		}
				
		return randomSet;
	}
	
	public static List<Node> displayRandomPermutation(List<List<Node>> list) {
		
		int[] randomSet = new int[list.size()];
		
		randomSet = pickRandomPermutation(list);
		
		List<Node> randomNodes = new ArrayList<Node>();
		
		for (int i = 0; i < randomSet.length; i++) {
			
			randomNodes.add(list.get(i).get(randomSet[i]));
			list.get(i).get(randomSet[i]).showLeafs();
			System.out.print(" / ");
			
		}
		
		return randomNodes;
	}
	
	
	public static List<List<Node>> getAllPermutationsOf(List<List<Node>> list, int startLevel,
			List<Node> parentElements, List<List<Node>> permutationList) {
		
//		int listSize = list.get(startLevel).size()-1;
		
		for (int i = 0; i < list.get(startLevel).size(); i++) {

			List<Node> parentNode = new ArrayList<Node>();
			parentNode.addAll(parentElements);
			parentNode.add(list.get(startLevel).get(i));
			
			if (startLevel < list.size()-1) {
				
				permutationList = getAllPermutationsOf(list, startLevel+1, 
						parentNode, permutationList);
		
			} else {

//				System.out.println(element+liste.get(startIndex).get(i));
				permutationList.add(parentNode);
				
			}

		}
		return permutationList;
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
	
	public void showCanUseTrees() {
		System.out.println();
		System.out.println("CanUseTrees: ");
		for (int i = 0; i < canUseTrees.size(); i++) {
			
			for (int j = 0; j < canUseTrees.get(i).size(); j++) {
				
				mustUseTrees.get(i).get(j).showLeafs();
				System.out.print("/ ");
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
