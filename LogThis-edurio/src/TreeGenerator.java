import java.util.List;
import java.util.Stack;

public class TreeGenerator {

	public static TemplatePool synTrees(boolean verbose){
		/*
		 * http://erg.delph-in.net/logon
		 * 
		 * S = Satz	
		 * D = Determinierer (Der, Ein)
		 * N = Nomen
		 * V = Verb
		 * P = Präposition (Nach, Vor)
		 * adj = Adjektiv
		 * adv = Adverb
		 * NP = Nominalphrase 
		 * VP = Verbalphrase
		 * AP = Adjektivphrase 
		 * AdvP = Adverbphrase
		 * PP = Präpositionalphrase
		 */
		TemplatePool tPool = new TemplatePool();

		List<SynTemplate> synTemps = TemplateBuilder.buildFromFile("templatesFile.dat", verbose);

		for (int i = 0; i < synTemps.size(); i++) {

			tPool.addTemplate(synTemps.get(i));
		}

		return tPool;
	}

	/**
	 * Container that creates a new tree from a given list of strings
	 * 
	 * @param treeList the List that the tree should be build from
	 * @param topic the topic for which error messages should be given
	 * @param verbose flag for showing building information
	 * 
	 * @return the treeRoot
	 */
	public static Node buildTreeFromList(List<String> treeList, String topic, boolean verbose) {
	
		Node treeRoot = new Node();
	
		if (treeList.get(0).matches("[a-zA-Z0-9<>\\^\\*_,\\(\\)]*")) {
	
			if (treeList.size() == 1) {											// formula has just one line
	
				treeRoot = TreeGenerator.formulaTreebuilder(treeList.get(0));
				
				if (verbose) {
					System.out.print("formula result: ");
					treeRoot.showLeafs();
					System.out.println();
				}
	
			} else {
	
				System.out.println("ERROR - tree formula can only be one line");
			}
		} else if (TreeGenerator.quobiTreeValidator(treeList)) {
	
			treeRoot = TreeGenerator.quobiTreeBuilder(treeList);
			
			if (verbose) {
				System.out.print("quobi result: ");
				treeRoot.showLeafs();
				System.out.println();
			}
	
		} else {
	
			System.out.println("ERROR - "+topic+" has no excepted representation");
		}
		
		return treeRoot;
	}

	/**
	 * Builds a tree from haystack and returns the root
	 * haystack format is LaTeX quobitree
	 * and every line that does not start with "\" is ignored 
	 * 
	 * @param haystack the haystack to be searched and sorted
	 *
	 * @return treeRoot the root of the new tree
	 */
	public static Node quobiTreeBuilder(List<String> haystack) {
		
		Node treeRoot = new Node();
		
		Stack<Node> nodeStack = new Stack<Node>();
		
		boolean rightChild = true;
		boolean terminal = false;
		boolean substitute = false;
		boolean slot = false;
		boolean foot = false;
		
		String lastLine = "";
		/*
		 * Read file, split lines into Nodes
		 */
		for (String line : haystack) {
			
			if (line.equals(lastLine) && line.startsWith("\\branch{1}{N}")) {	// ignore double Noun lines
				
			} else if (line.equals(lastLine) && line.startsWith("\\branch{1}{V}")) {	// ignore double Verb lines
				
			} else {
			
				lastLine = line;
				String data = "";
				Node tmpNode = new Node();
				int branch = 0;
							
				if (line.startsWith("\\")) {									// only use lines starting with "\"
					
					if (line.startsWith("\\leaf{\\emph{")) {					// if leaf
						
						data = line.substring(12, line.length()-2);
						if (data.endsWith("^")) {								// check and set substitute flag
							data = data.substring(0, data.length()-1);
							substitute = true;
						
						} else if (data.endsWith("*")) {						// check and set foot flag
							data = data.substring(0, data.length()-1);
							foot = true;
						
						} else {												// else set terminal flag
							
							if (data.startsWith("<") && data.endsWith(">")) {
								
								slot = true;
								
							}
							
							terminal = true;
						}
						
					} else if (line.startsWith("\\branch{1}{")) {				// set branch length 1
						
						data = line.substring(11, line.length()-1);
						branch = 1;
						
					} else if (line.startsWith("\\branch{2}{")) {				// set branch length 2
						
						data = line.substring(11, line.length()-1);
						branch = 2;
						
					} else {
						
						System.out.println("undefined = "+ line);
					}
						
					tmpNode.setData(data);										// create the new node with data and flags
					tmpNode.setTerminal(terminal);
					tmpNode.setSubstitute(substitute);
					tmpNode.setSlot(slot);
					tmpNode.setFoot(foot);
					
					terminal 	= false;										// reset flags
					substitute 	= false;
					slot 		= false;
					foot 		= false;
					
					rightChild = true;
						
					for (int i = 0; i < branch; i++) {							// if branch > 1
						
						if (rightChild) {
							
							tmpNode.setRightChild(nodeStack.pop());				// last element on stack is right child
							rightChild = false;
						
						}else {
							
							tmpNode.setLeftChild(nodeStack.pop());				// next element on stack is left child
							
						}
					}
					nodeStack.push(tmpNode);									// put tree on stack
				}
			}
		}
		
		treeRoot = nodeStack.pop();
		
		return treeRoot;
	}

	/**
	 * Validator method for quobi trees, returns true if it has the right nomenclature
	 * It does not check, if the tree is sound in itself
	 * 
	 * @param representation the representation that is to be validated
	 * 
	 * @return if it isCorrectSyntax
	 */
	public static boolean quobiTreeValidator(List<String> representation) {
		
		boolean isCorrectSyntax = false;
		for (int i = 1; i < representation.size(); i++) {
		
			if (representation.get(i).matches("\\\\(leaf|branch)[\\\\a-zA-Z0-9{}\\-<>\\^\\*]*")) {
				
				isCorrectSyntax = true;
				
			} else {
				System.out.println(representation.get(i));
				isCorrectSyntax = false;
				break;
			}
		}
		
		if (!isCorrectSyntax) {
			
			System.out.println("ERROR - Base-Tree quobitree is wrong");
						
		}
		
		return isCorrectSyntax;
	}

	/**
	 * search in haystack for root of tree
	 * 
	 * @param haystack the haystack to be searched
	 * 
	 * @return rootNode the rootNode of the tree
	 */
	public static Node formulaTreebuilder(String haystack){
		
		Node rootNode = new Node();
		
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

		rootNode.setData(rootData);
		findChildrenIn(childrenData, rootNode);
		
		return rootNode;
		
	}
	
	/**
	 * search in haystack and add Children to parentNode
	 * 
	 * divide the haystack in four parts, left and right node 
	 * and the sub-haystacks for each node. Call recursive 
	 * function on sub-haystacks
	 * 
	 * @param haystack the haystack in which to search
	 * @param parentNode the parentNode of the searched for Children
	 */
	public static void findChildrenIn(String haystack, Node parentNode){
		
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
			
			leftNode = new Node(terminal, leftSubstitute, leftFoot, false, leftNodeStraws, parentNode);
			
			parentNode.setLeftChild(leftNode);
			
		}
		terminal = false;
		
		if(rightSubHaystack.isEmpty() && !rightSubstitute && !rightFoot){
			
			terminal = true;
			
		}
		
		if (!rightNodeStraws.contentEquals("_")) {
		
			rightNode = new Node(terminal, rightSubstitute, rightFoot, false, rightNodeStraws, parentNode);
			parentNode.setRightChild(rightNode);
		
		}
		
		terminal = false;
		
		if (!leftSubHaystack.isEmpty()) {
			findChildrenIn(leftSubHaystack, leftNode);
			
		}
		
		if (!rightSubHaystack.isEmpty()) {
			findChildrenIn(rightSubHaystack, rightNode);
			
		}
	}
}
