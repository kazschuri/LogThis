import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class TreeGenerator {

	//TODO
	/**
	 * Container to build a tree from a given file and returns the root
	 * File format is LaTeX quobitree with leading whitespace
	 * and every line that does not start with " \" is ignored
	 *
	 * this Container could handle exception ...
	 * 
	 * @param filename the name of the file
	 * @return treeRoot
	 * 
	 * Builds a tree from haystack and returns the root
	 * haystack format is LaTeX quobitree
	 * and every line that does not start with " \" is ignored 
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
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
					terminal = false;
					tmpNode.setSubstitute(substitute);
					substitute = false;
					tmpNode.setSlot(slot);
					slot = false;
					tmpNode.setFoot(foot);
					foot = false;
					
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
		
		treeRoot.showLeafs();
		System.out.println();
		return treeRoot;
	}

	
	
	
	
	//TODO
	/**
	 * search in haystack for root of tree
	 * 
	 * @param haystack the haystack to be searched
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
	
	//TODO
	/**
	 * search in haystack and add Children to tree
	 * 
	 * divide the haystack in four parts, left and right node 
	 * and the sub-haystacks for each node. Call recursive 
	 * function on sub-haystacks
	 * 
	 * @param haystack the haystack in which to search
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
	
public static void treeTest (){
		
//		Node node1 = new Node();
//		Node node5 = new Node();
//		Node node7 = new Node();
//		Node node12 = new Node();
//		
//		node1.buildTreeOutOf("np(d^,n(boy,_))");
//		node5.buildTreeOutOf("d(the,_)");
//		node7.buildTreeOutOf("s(np^,vp(_,v(_,left)))");
//		node12.buildTreeOutOf("vp(vp*,adv(today,_))");
//		
//		node1.showTerminal();
//		node1.substitute(node5);
//		
//		System.out.println();
//		
//		node1.showTerminal();
//		
//		node7.substitute(node1);
//		
//		System.out.println();
//		
//		node7.showTerminal();
//		
//		node7.adjoin(node12);
//		
//		System.out.println();
//		
//		node7.showTerminal();
//		System.out.println();
//		System.out.println(node7.showTree());
		
		TestClass test = new TestClass();

		test.addMultipleChoiceToMust("A1", "A2", "A3");
		test.addMultipleChoiceToMust("B1", "B2", "B3");
		test.addMultipleChoiceToMust("C1", "C2", "C3");
		
		test.addMultipleChoiceToCan("H1","H2","H3");
		test.addMultipleChoiceToCan("I1","I2","I3");
		
		test.showMust();
		test.showCan();
		
		test.handPerm();
		List<String> permList = new ArrayList<String>();
		
		TestClass.getAllPermutationsOf(test.must, 0, "", permList);
		
		for (int i = 0; i < permList.size(); i++) {
			
			System.out.println(permList.get(i));
			
		}
	}

	public static void synTrees(){
		/*
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
		
		/*
		 * empty workspace
		 * workspace is empty
		 * no human is in the workarea
		 * nobody there but the bot
		 */
		
		/*
		 * workarea is empty
		 */
		Node sent_02 = new Node("NP(D^,N^)");				// d^ n^
		Node sent_03 = new Node("VP(VP*,ADV(empty,_))"); 	// vp* empty
		Node sent_04 = new Node("D(the,_)");				// d = the
		Node sent_09 = new Node("D(this,_)");				// d = this
		
		Node sent_08 = new Node("N(workplace,_)");			// n = workplace
		Node sent_10 = new Node("N(workspace,_)");			// n = workspace
		Node sent_11 = new Node("N(workarea,_)");			// n = workarea
		
		Node sent_05 = new Node("D(an,_)");					// D = an
		Node sent_06 = new Node("D(a,_)");					// D = a

//http://erg.delph-in.net/logon
		
		SynTemplate sentence_01 = new SynTemplate();
		
//		sentence_01.showTreeInfo();
		
		SynTemplate sentence_02 = new SynTemplate();
		
//		sentence_02.showTreeInfo();
//		sentence_02.showTreeInfo();
//		sentence_02.showMustUseTrees();
//		sentence_02.showCanUseTrees();
		
//		List<List<Node>> permuteList = new ArrayList<List<Node>>();
//		List<Node> parent = new ArrayList<Node>();
//		
//		List<List<Node>> resultList = new ArrayList<List<Node>>();
//		
//		resultList = SynTemplate.getAllPermutationsOf(sentence_02.getMustUseTrees(), 0, parent, permuteList);
//		
//		for (int i = 0; i < resultList.size(); i++) {
//			
//			for (int j = 0; j < resultList.get(i).size(); j++) {
//				
//				resultList.get(i).get(j).showLeafs();
//				System.out.print(" / ");
//			}
//			
//			System.out.println();
//		}
		
		SynTemplate.displayRandomPermutation(sentence_02.getMustUseTrees(), false);
		sentence_02.showTreeInfo();
		sentence_02.showCanUseTrees();
		
		Condition cond2 = new Condition("Sequence", "newborn", "With_Human", true);
		Condition cond1 = new Condition("Sequence", "dying", "No_Human", true);
		Condition cond3 = new Condition("Sequence", "current", "No_Human", false);
		Condition cond4 = new Condition("Sequence", "current", "Ignore", true);
		
		Condition[] condSet1 = {cond1, cond4};
		Condition[] condSet2 = {cond2, cond3};
		
		String[] link1 = {"OR"};
		String[] link2 = {"AND"};
		
		LinkedConditions linCond1 = new LinkedConditions(condSet1, link1);
		LinkedConditions linCond2 = new LinkedConditions(condSet2, link2);
		
		sentence_01.setLinConds(linCond1);
		sentence_02.setLinConds(linCond2);
		
		String[] dye1 = {"No_Human;situation","Follow_Path;expectation"};
		String[] grow1 = {"Left_Arm_Down;action","Right_Arm_Down;action"};
		String[] newborn1 = {"With_Human;situation","Distraction;situation","Stand_Still;activity","Ignore;activity"};
		
		Sequence testSequence1 = new Sequence(newborn1, grow1, dye1);
		
		String[] dye2 = {"Follow_Path;expectation"};
		String[] grow2 = {"No_Human;situation","Left_Arm_Down;action","Right_Arm_Down;action"};
		String[] newborn2 = {"With_Human;situation","Distraction;situation","Stand_Still;activity","Ignore;activity"};
		
		Sequence testSequence2 = new Sequence(newborn2, grow2, dye2);
		
		boolean testResult = false;
		
		testResult = sentence_01.isApplicable(testSequence1);
		System.out.println("sent1 + seq1 = "+testResult);
		
		testResult = sentence_01.isApplicable(testSequence2);
		System.out.println("sent1 + seq2 = "+testResult);

		testResult = sentence_02.isApplicable(testSequence1);
		System.out.println("sent2 + seq1 = "+testResult);

		testResult = sentence_02.isApplicable(testSequence2);
		System.out.println("sent2 + seq2 = "+testResult);
		
		List<List<String>> fileList = new ArrayList<List<String>>();
		fileList = TemplateFileReader.processFile("template01.dat");
		System.out.println(fileList.size());
		
		TemplateFileReader.fileDistributor(TemplateFileReader.processFile("template01.dat"));
		
		
		/*
		 * a human/user/person enters the workspace
		 * a human enters the workspace with hanging arms
		 * a human enters with his arms hanging down/by his side
		 * a worker comes in/into the workspace
		 */
		
		/* 
		 * the worker stands still
		 * a worker stands in the workarea
		 * a human stands in the workspace with arms by his side
		 */
		
		/*
		 * the worker is half turned towards the robot/bot
		 * after a couple of seconds the worker half turns towards the bot
		 * after x seconds the worker half turns towards the bot
		 *  
		 */

		/*
		 * the human ignores the robot
		 * he does not look at the robot
		 * he does not interact with the robot
		 */
		
		
	}
	
	//TODO
	public static Node buildNodeFromList(List<String> treeList, String topic) {

		Node templateNode = new Node();

		if (treeList.get(0).matches("[a-zA-Z0-9<>\\^\\*_,\\(\\)]*")) {

			if (treeList.size() == 1) {						// Determiner plus one line formula

				templateNode = TreeGenerator.formulaTreebuilder(treeList.get(0));

			} else {

				System.out.println("ERROR - tree formula can only be one line");
			}
		} else if (TreeGenerator.quobiTreeValidator(treeList)) {

			templateNode = TreeGenerator.quobiTreeBuilder(treeList);

		} else {

			System.out.println("ERROR - Base-Tree has no excepted representation");
		}
		
		return templateNode;
	}
	
	//TODO
	public static boolean quobiTreeValidator(List<String> representation) {
		
		boolean treeCorrect = false;
		for (int i = 1; i < representation.size(); i++) {
		
			if (representation.get(i).matches("\\\\(leaf|branch)[\\\\a-zA-Z0-9{}\\-<>\\^\\*]*")) {
				
				treeCorrect = true;
				
			} else {
				System.out.println(representation.get(i));
				treeCorrect = false;
				break;
			}
		}
		
		if (!treeCorrect) {
			
			System.out.println("ERROR - Base-Tree quobitree is wrong");
						
		}
		
		return treeCorrect;
	}
}
