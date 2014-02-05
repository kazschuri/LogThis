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
	/**
	 * constructor for nonterminal node without references to other nodes, no markers
	 * 
	 * @param data
	 */
	public Node(String data){
		
		super();
		this.data = data;
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
	 */
	public void substitute(Node sub){
		
		if (this.substitute) {											// if this is a substitute node

			if (this.parent.leftChild.data.equals(sub.data)) {			// if it is a left child
				
				this.parent.setLeftChild(sub);							// substitute the left child of parent with sub 
			
			} else if (this.parent.rightChild.data.equals(sub.data)) {	// if it is a right child
				
				this.parent.setRightChild(sub);							// substitute the right child of parent with sub
				
			}
			
		} else {														// if that was not a substitute node go recursively
			
			if (this.leftChild != null) {
				
				this.leftChild.substitute(sub);
				
			}
			if (this.rightChild != null) {
				
				this.rightChild.substitute(sub);
				
			}
			
		}
	}
	
	public void adjoin(Node adj){
		
		if (adj.parent == null) {										// check that adj is the root node 
			
			Node partialTreeRoot = this.getNode(adj.getData(), false);	// save partial tree which gets separated through adjoin

			Node mainTreeLeaf = partialTreeRoot.parent ;				// save leaf of main tree on which to adjoin
			
			Node adjFoot = adj.getNode(adj.data, true);					// find foot node of adjoining tree  
			
			adjFoot.leftChild = partialTreeRoot.leftChild;				// parse partial tree to foot of adjoining tree 
			adjFoot.rightChild = partialTreeRoot.rightChild;
			adjFoot.foot = false;										// remove the foot tag
			
			if (mainTreeLeaf.leftChild.data.equals(adj.data)) {			// find out if tree has to be adjoined on left child
				
				mainTreeLeaf.leftChild = adj;
				
			} else if (mainTreeLeaf.rightChild.data.equals(adj.data)) {	// or right child
				
				mainTreeLeaf.rightChild = adj;
				
			}
			
		} else {														// if it wasn't the root
			
			this.adjoin(adj.parent);									// search for it recursively
			
		}
		
	}
	
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
