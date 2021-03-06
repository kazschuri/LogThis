import java.util.ArrayList;
import java.util.List;

//	Initialbaum
//	- innere Knoten Nichtterminal
//	- ̈Blätter entweder Terminalsymbole oder Nichtterminal mit Substitutionsmarker
	
//	Hilfsbaum
//	- innere Knoten Nichtterminal
//	- ̈Blätter entweder Terminalsymbole oder Nichtterminal mit Substitutionsmarker
//	- genau ein Blatt Fußknoten
//	- Fußknoten = Nichtterminalsymbol = Wurzelknoten

public class Node {

	private boolean terminal = false;
	private boolean substitute = false;
	private boolean foot = false;
	private boolean slot = false;
	
	private String data = "";
	private Node parent = null;
	private Node leftChild = null;
	private Node rightChild = null;
	/**
	 * full constructor
	 * 
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param slot
	 * @param data
	 * @param parent
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(boolean terminal, boolean substitute, boolean foot, boolean slot,
			String data, Node parent, Node leftChild, Node rightChild) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
		this.slot = slot;
		this.data = data;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	/**
	 * constructor without children
	 * 
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param slot
	 * @param data
	 * @param parent
	 */
	public Node(boolean terminal, boolean substitute, boolean foot, boolean slot,
			String data, Node parent) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
		this.slot = slot;
		this.data = data;
		this.parent = parent;
	}
	
	/**
	 * constructor without reference to other nodes (no parent, no children)
	 * 
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param slot
	 * @param data
	 */
	public Node(boolean terminal, boolean substitute, boolean foot, boolean slot,
			String data) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
		this.slot = slot;
		this.data = data;
	}
	/**
	 * constructor for terminal node without references to other nodes
	 * 
	 * @param terminal
	 * @param data
	 */
	public Node(boolean terminal, String data){
		
		super();
		this.terminal = terminal;
		this.data = data;
	}

	/**
	 * construct Node from haystack-formula
	 *  	
	 * @param haystack the haystack to build the tree from
	 */
	public Node(String haystack){
		
		super();
		Node newNode = TreeGenerator.formulaTreebuilder(haystack);
		
		this.copyTree(newNode);
		
	}
	
	public Node(){
		super();
	}
	
	/**
	 * substitution operation on synTAG
	 * 
	 * @param sub the tree to substitute into this
	 * @return success as true, if substitute was successfull
	 */
	public boolean substitute(Node sub){
		
		boolean success = false;
		
		if (this.substitute && this.data.equals(sub.data)) {			// if this is a substitute node with the right data

			if (this.parent.leftChild.data.equals(sub.data)) {			// if it is a left child
				
				this.parent.setLeftChild(sub);							// substitute the left child of parent with sub
			
			} else if (this.parent.rightChild.data.equals(sub.data)) {	// if it is a right child
				
				this.parent.setRightChild(sub);							// substitute the right child of parent with sub
				
			}
			success = true;
			
		} else {														// if that was not a substitute node go recursively
			
			if (this.leftChild != null) {
				
				success = this.leftChild.substitute(sub);
				
			}
			if (this.rightChild != null && !success) {
				
				success = this.rightChild.substitute(sub);
				
			}
			
		}
		
		return success;
	}
	
	/**
	 * adjoin operation on trees
	 * 
	 * @param adj the tree to adjoin in target tree
	 * @return success as true, if adjoin was successfull
	 */
	public boolean adjoin(Node adj){
		
		boolean success = false;
		
		if (adj.parent == null) {										// check that adj is the root node 
			
			Node adjFoot = adj.getNode(adj.data, true);					// find foot node of adjoining tree
						
			if (adjFoot.getData().equals(adj.getData())) {				// check that adj is an possible adjoining tree
				
				Node partialTreeRoot = this.getNode(adj.getData(), false);	// save partial tree which gets separated through adjoin
				
				if (partialTreeRoot.data.equals(adj.getData())) {			// check that there is an appropriate node in tree
				
					Node mainTreeLeaf = partialTreeRoot.parent ;				// save leaf of main tree on which to adjoin
					
					adjFoot.leftChild = partialTreeRoot.leftChild;				// parse partial tree to foot of adjoining tree 
					adjFoot.rightChild = partialTreeRoot.rightChild;
					adjFoot.foot = false;										// remove the foot tag
					
					if (mainTreeLeaf.leftChild.data.equals(adj.data)) {			// find out if tree has to be adjoined on left child
						
						mainTreeLeaf.leftChild = adj;
						
					} else if (mainTreeLeaf.rightChild.data.equals(adj.data)) {	// or right child
						
						mainTreeLeaf.rightChild = adj;
						
					}
					
					success = true;
				}				
			}
			
		} else {														// if it wasn't the root
			
			success = this.adjoin(adj.parent);							// search for it recursively
			
		}
		
		return success;
	}
	
	/**
	 * finding a node with same data and foot-flag
	 *  
	 * @param searchData the data to search for
	 * @param foot the flag of foot to search for
	 * @return result node
	 */
	public Node getNode(String searchData, boolean foot) {
		
		Node result = new Node(); 
		
		if (this.foot == foot && this.data.equals(searchData)) {			// if this has the right foot flag and the searched data				
				
				result = this;												// return this node
				
		} else {
			
			if (this.leftChild != null) {									
				
				Node tmpResult = this.leftChild.getNode(searchData, foot);	// else look at left child
				
				if (tmpResult.data.equals(searchData)) {
					
					result = tmpResult;
				}
			}
			if (this.rightChild != null) {
				
				Node tmpResult = this.rightChild.getNode(searchData, foot);	// else look at right child
				
				if (tmpResult.data.equals(searchData)) {
					
					result = tmpResult;
				}
			}
		}
		
		return result;
		
	}
	
	/**
	 * create a List of current tree
	 * 
	 * @return nodeList
	 */
	public List<String> listTree(){
		
		List<String> nodeList	= new ArrayList<String>();
		List<String> leftList	= new ArrayList<String>();
		List<String> rightList	= new ArrayList<String>();
		
		String augmentedData = this.data;
		
		if (this.foot) {
			
			augmentedData += "*";
		}
		
		if (this.substitute) {
			
			augmentedData += "^";
		}

		nodeList.add(augmentedData);
		
		if (this.leftChild == null && this.rightChild == null) {
			
		} else {
			if (this.leftChild != null) {									
				
				leftList.addAll(this.leftChild.listTree());
				
			} else {
				
				leftList.add("_");
				
			}
			if (this.rightChild != null) {
	
				rightList.addAll(this.rightChild.listTree());
				
			} else {
				
				rightList.add("_");
				
			}
		
		nodeList.add("(");
		nodeList.addAll(leftList);
		if (leftList.size() == 0 || rightList.size() == 0) {
			
		} else {
			nodeList.add(",");
		}
		nodeList.addAll(rightList);
		nodeList.add(")");
		}
		return nodeList;
	}
	
	/**
	 * shows a formal representation of the tree
	 * 
	 * @return the resulting Tree
	 */
	public String showTree(){
		
		List<String> nodeList = this.listTree();
		
		String result = "";
		
		for (int i = 0; i < nodeList.size(); i++) {
			
			result += nodeList.get(i);
			
		}
		
		return result;
		
	}
	
	/**
	 * show all the terminal symbols from left to right
	 */
	public void showTerminal()
	{
		if (this.terminal) {
			
			System.out.print(data+" ");
			
		} else {
			
//			System.out.print(data+" ");
			
			if (this.leftChild != null) {
			
				this.leftChild.showTerminal();
				
			}
			
			if (this.rightChild != null) {
			
				this.rightChild.showTerminal();
				
			}
		}
	}

	/**
	 * get all the terminal strings from left to right
	 */
	public List<String> getTerminalStrings(){
		
		List<String> result = new ArrayList<String>();
		
		if (this.terminal) {
			
			result.add(data);
//			System.out.print(data+" ");
			
		} else {
			
//			System.out.print(data+" ");
			
			if (this.leftChild != null) {
			
				result.addAll(this.leftChild.getTerminalStrings());
				
			}
			
			if (this.rightChild != null) {
			
				result.addAll(this.rightChild.getTerminalStrings());
				
			}
		}
		
		return result;
	}
	
	/**
	 * show all the slot symbols from left to right
	 */
	public void showSlots()
	{
		if (this.slot) {
			
			System.out.print(data+" ");
			
		} else {
			
			
			if (this.leftChild != null) {
			
				this.leftChild.showSlots();
			}
			
			if (this.rightChild != null) {
			
				this.rightChild.showSlots();
			}
		}
	}
	
	/**
	 * show all the leaf symbols from left to right
	 */
	public void showLeafs()
	{
		if (this.leftChild == null && this.rightChild == null) {
			
			System.out.print(data);
			
			if (foot) {
				
				System.out.print("*");
				
			} else if (substitute) {
				
				System.out.print("^");
				
			}
			
			System.out.print(" ");
		}
		
		if (this.leftChild != null) {
		
			this.leftChild.showLeafs();
			
		}
		
		if (this.rightChild != null) {
		
			this.rightChild.showLeafs();
		}
	}
	
	/**
	 * Method to create a copy of given Tree
	 * 
	 * @param treeRoot the treeRoot to create a copy of 
	 * @return clonedTree 
	 */
	public static Node copyOf(Node treeRoot) {
		
		Node clonedTree = new Node();
		
		clonedTree.copyTree(treeRoot);
		
		return clonedTree;
		
	}
	
	/**
	 * Method to copy all the data of rootNode to objectNode and work down to children
	 * 
	 * @param root the root of the tree to copy
	 */
	private void copyTree(Node root) {
		
		this.terminal	= root.terminal;
		this.substitute	= root.substitute;
		this.foot		= root.foot;
		this.data		= root.data;
		
		if (root.leftChild != null) {
			
			Node tmpLeft = new Node();
			this.setLeftChild(tmpLeft);			
			this.leftChild.copyTree(root.leftChild);
		}
		
		if (root.rightChild != null) {
		
			Node tmpRight= new Node();
			this.setRightChild(tmpRight);			
			this.rightChild.copyTree(root.rightChild);
		}
		
	}
	
	/**
	 * @return the terminal
	 */
	public boolean isTerminal() {
		return terminal;
	}
	
	/**
	 * @param terminal the terminal to set
	 */
	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}
	
	/**
	 * @return the substitute
	 */
	public boolean isSubstitute() {
		return substitute;
	}
	
	/**
	 * @param substitute the substitute to set
	 */
	public void setSubstitute(boolean substitute) {
		this.substitute = substitute;
	}
	
	/**
	 * @return the foot
	 */
	public boolean isFoot() {
		return foot;
	}
	
	/**
	 * @param foot the foot to set
	 */
	public void setFoot(boolean foot) {
		this.foot = foot;
	}
	
	/**
	 * @return the slot
	 */
	public boolean isSlot() {
	
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(boolean slot) {
	
		this.slot = slot;
		
		if (slot) {
			this.terminal = true;
		}
		   
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the leftChild
	 */
	public Node getLeftChild() {
		return leftChild;
	}
	
	/**
	 * @param leftChild the leftChild to set
	 */
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
		this.leftChild.setParent(this);
		this.terminal= false;
		this.foot = false;
		
	}
	
	/**
	 * 
	 * @param terminal the flag for a terminal 
	 * @param substitute the flag for a substitute node
	 * @param foot the flag for a foot node
	 * @param slot the flag for a slot
	 * @param data the data to set
	 */
	public void setLeftChild(boolean terminal, boolean substitute, boolean foot, boolean slot,
								String data)
	{
	
		this.leftChild = new Node(terminal, substitute, foot, slot, data, this);
		this.terminal= false;
		this.foot = false;
		
	}
	
	/**
	 * @return the rightChild
	 */
	public Node getRightChild() {
		return rightChild;
	}
	
	/**
	 * 
	 * @param terminal the flag for a terminal 
	 * @param substitute the flag for a substitute node
	 * @param foot the flag for a foot node
	 * @param slot the flag for a slot
	 * @param data the data to set
	 */
	public void setRightChild(boolean terminal, boolean substitute, boolean foot, boolean slot,
								String data)
	{
	
		this.rightChild = new Node(terminal, substitute, foot, slot, data, this);
		this.terminal= false;
		this.foot = false;
	}
	
	/**
	 * @param rightChild the rightChild to set
	 */
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
		this.rightChild.setParent(this);
		this.terminal= false;
		this.foot = false;
	}
	
}
