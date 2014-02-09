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
	 * @param data
	 * @param parent
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(boolean terminal, boolean substitute, boolean foot,
			String data, Node parent, Node leftChild, Node rightChild) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
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
	 * @param data
	 * @param parent
	 */
	public Node(boolean terminal, boolean substitute, boolean foot, String data, Node parent) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
		this.data = data;
		this.parent = parent;
	}
	
	/**
	 * constructor without reference to other nodes (no parent, no children)
	 * 
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param data
	 */
	public Node(boolean terminal, boolean substitute, boolean foot,
			String data) 
	{
		super();
		this.terminal = terminal;
		this.substitute = substitute;
		this.foot = foot;
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
//	/**
//	 * constructor for nonterminal node without references to other nodes, no markers
//	 * 
//	 * @param data
//	 */
//	public Node(String data){
//		
//		super();
//		this.data = data;
//	}
	
	public Node(String haystack){
		
		super();
		this.buildTreeOutOf(haystack);
		
		
	}
	
	public Node(){
		super();
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
	
	public String showTree(){
		
		List<String> nodeList = this.listTree();
		
		String result = "";
		
		for (int i = 0; i < nodeList.size(); i++) {
			
			result += nodeList.get(i);
			
		}
		
		return result;
		
	}
	
	/**
	 * search in haystack for root of tree
	 * 
	 * @param haystack the haystack to be searched
	 */
	public void buildTreeOutOf(String haystack){
		
		String[] splitForm = haystack.split("");
		
		int braceCounter = 0;
		String tmpData = "";
		String rootData = "";
		String childrenData ="";
		
		for (int i = 0; i < splitForm.length; i++) {
			if (splitForm[i].equals("(")) {
				if (braceCounter == 0) {
					rootData = tmpData;
					tmpData = "";
				} else {
					
					tmpData += splitForm[i];
					
				}
				braceCounter++;				
				
			} else if (splitForm[i].equals(")")) {
				
				braceCounter--;
				
				if (braceCounter == 0) {
					
					childrenData = tmpData;
					
				} else {
					
					tmpData += splitForm[i];
					
				}
				
			} else {
				
				tmpData += splitForm[i];
				
			}
		}

		this.data = rootData;
		this.findChildrenIn(childrenData);
		
	}
	/**
	 * search in haystack and add Children to tree
	 * 
	 * divide the haystack in four parts, left and right node 
	 * and the sub-haystacks for each node. Call recursive 
	 * function on sub-haystacks
	 * 
	 * @param haystack the haystack in which to search
	 */
	public void findChildrenIn(String haystack){
		
		haystack += "#";									// mark end of haystack
		String[] straws = haystack.split("");
		String leftNodeStraws = "";
		String rightNodeStraws = "";
		String leftSubHaystack = "";
		String rightSubHaystack = "";
		String tmpStraws = "";
		
		Node rightNode = new Node();
		Node leftNode = new Node();
		
		boolean terminal = true;
		
		boolean leftSubstitute = false;
		boolean leftFoot = false;
		
		boolean rightSubstitute = false;
		boolean rightFoot = false;
		
		int braceCounter = 0;								
		boolean left = true;
		/*
		 * mine the haystack for the four elements, left child, right child, left sub-haystack, right sub-haystack
		 */
		
		for (int i = 0; i < straws.length; i++) {			// run once through complete haystack
			
			if (straws[i].equals("(")) {					// open braces are either
			
				terminal = false;
				
				if (braceCounter == 0) {					// the end of the child node if it is the first left brace
					
					if (left) {
						
						leftNodeStraws += tmpStraws;
						
					} else {
						
						rightNodeStraws += tmpStraws;
					}
					
					tmpStraws= "";
					
				} else {									// or part of a sub-haystack if braceCount is > 1
					
					tmpStraws += straws[i];
				}
				
				braceCounter++;
				
			} else if (straws[i].equals(")")) {				// right braces are either
				
				braceCounter--;

				if (braceCounter == 0) {					// the end of the sub-haystack if braceCount is 0
					if (left) {

						leftSubHaystack += tmpStraws;
						
					} else {
						
						rightSubHaystack += tmpStraws;
					}

					tmpStraws = "";
					
				} else {									// or part of the sub-haystack if braceCount is still larger
					
					tmpStraws += straws[i];
				}
				
			} else if (straws[i].equals("^") && braceCounter == 0) {	// flag as substitute if it is outside of main braces
				
				if (left) {
		
					leftSubstitute = true;
					
				} else {
					
					rightSubstitute = true;
				}

			} else if (straws[i].equals("*") && braceCounter == 0) {	// flag as foot if it is outside of main braces
				
				if (left) {
		
					leftFoot = true;
					
				} else {
					
					rightFoot = true;
				}

			} else if (straws[i].equals(",") && braceCounter == 0) {	// , outside of main braces signifies end of left half, meaning
				
				if (terminal) {											// either the left parent if no braces on left side
					
					leftNodeStraws += tmpStraws;

				} else {												// or the left sub-haystack if there were braces
				
					leftSubHaystack += tmpStraws;					// this is probably always "" because of ")"-condition, I am too lazy
				}
				
				tmpStraws = "";
				left = false;										// switch to right parent and children
				terminal = true;									// reset flag
				
			} else if (straws[i].equals("#")) {						// end of haystack -> end of right half, meaning
				
				if (terminal) {										// either the right parent if no braces on right side
					
					rightNodeStraws += tmpStraws;
					
				} else {											// or the right sub-haystack if there were braces
					
					rightSubHaystack += tmpStraws;					// this is probably always "" because of ")"-condition, I am too lazy
				}
				
			} else {
					
					tmpStraws += straws[i];							// no conditions -> still within a word
			}
		}
		/*
		 * creating the tree out of the mined data
		 */
		terminal = false;
				
		if (leftSubHaystack.isEmpty() && !leftSubstitute && !leftFoot) {
			
			terminal = true;
			
		}
		if (!leftNodeStraws.contentEquals("_")) {
			
			leftNode = new Node(terminal, leftSubstitute, leftFoot, leftNodeStraws, this);
			this.setLeftChild(leftNode);
			
		}
		terminal = false;
		
		if(rightSubHaystack.isEmpty() && !rightSubstitute && !rightFoot){
			
			terminal = true;
			
		}
		
		if (!rightNodeStraws.contentEquals("_")) {
		
			rightNode = new Node(terminal, rightSubstitute, rightFoot, rightNodeStraws, this);
			this.setRightChild(rightNode);
		
		}
		
		terminal = false;
		
		if (!leftSubHaystack.isEmpty()) {
			
			leftNode.findChildrenIn(leftSubHaystack);
			
		}
		
		if (!rightSubHaystack.isEmpty()) {
			
			rightNode.findChildrenIn(rightSubHaystack);
			
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
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param data
	 */
	public void setLeftChild(boolean terminal, boolean substitute, boolean foot,
								String data)
	{
	
		this.leftChild = new Node(terminal, substitute, foot, data, this);
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
	 * @param terminal
	 * @param substitute
	 * @param foot
	 * @param data
	 */
	public void setRightChild(boolean terminal, boolean substitute, boolean foot,
								String data)
	{
	
		this.rightChild = new Node(terminal, substitute, foot, data, this);
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
