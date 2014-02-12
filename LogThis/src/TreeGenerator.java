import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class TreeGenerator {
	
	/**
	 * Container to build a tree from a given file and returns the root
	 * File format is LaTeX quobitree with leading whitespace
	 * and every line that does not start with " \" is ignored
	 *
	 * this Container could handle exception ...
	 * 
	 * @param filename the name of the file
	 * @return treeRoot
	 */
	public static Node treeBuilder(String filename) {
		
		Node treeRoot = new Node();
		
		try { 
			
			treeRoot = treeReader(filename);	// actual treebuilding is done in the filereader 
			
		} catch (IOException e) {
			
			System.out.print("Something wrong with input File");
			
		}
				
//		System.out.println("Finished");
					
//		System.out.println(treeRoot.showTree());
//		treeRoot.showLeafs();
//		System.out.println();
		
		return treeRoot; 
	}
	/**
	 * Builds a tree from a given file and returns the root
	 * File format is LaTeX quobitree with leading whitespace
	 * and every line that does not start with " \" is ignored 
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Node treeReader(String filename)  throws IOException {
		
		FileReader fr = new FileReader("file/"+filename);
		BufferedReader br = new BufferedReader(fr);
		
		Stack<Node> nodeStack = new Stack<Node>();
		
		String line = "";
		boolean rightChild = true;
		boolean terminal = false;
		boolean substitute = false;
		boolean slot = false;
		boolean foot = false;
		
		String lastLine = "";
		/*
		 * Read file, split lines into Nodes
		 */
		while ( (line = br.readLine()) != null ) {
			
			if (line.equals(lastLine) && line.startsWith(" \\branch{1}{N}")) {	// ignore double Noun lines
				
			} else if (line.equals(lastLine) && line.startsWith(" \\branch{1}{V}")) {	// ignore double Noun lines
				
			} else {
			
				lastLine = line;
				String data = "";
				Node tmpNode = new Node();
				int branch = 0;
							
				if (line.startsWith(" \\")) {									// only use lines starting with " \"
					
					if (line.startsWith(" \\leaf{\\emph{")) {					// if leaf
						
						data = line.substring(13, line.length()-2);
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
						
					} else if (line.startsWith(" \\branch{1}{")) {				// set branch length 1
						
						data = line.substring(12, line.length()-1);
						branch = 1;
						
					} else if (line.startsWith(" \\branch{2}{")) {				// set branch length 2
						
						data = line.substring(12, line.length()-1);
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
		br.close();
		
		return nodeStack.pop();
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
		sentence_01.setTemplate(treeBuilder("sentence01-base.dat"));
		
//		sentence_01.showTreeInfo();
		
		SynTemplate sentence_02 = new SynTemplate();
		sentence_02.setTemplate(treeBuilder("sentence02-base.dat"));
		
//		sentence_02.showTreeInfo();
		sentence_02.addMultipleChoiceToMust(treeBuilder("sentence02-must-sub5-sub1.dat"),treeBuilder("sentence02-must-sub5-sub2.dat"),treeBuilder("sentence02-must-sub5-sub3.dat"));
		sentence_02.addMultipleChoiceToMust(treeBuilder("sentence02-must-sub5.dat"),treeBuilder("sentence02-must-sub6.dat"),treeBuilder("sentence02-must-sub7.dat"));
		sentence_02.addMultipleChoiceToMust(treeBuilder("sentence02-must-sub3-sub1.dat"),treeBuilder("sentence02-must-sub3-sub2.dat"),treeBuilder("sentence02-must-sub3-sub3.dat"));
		sentence_02.addMultipleChoiceToMust(treeBuilder("sentence02-must-sub1.dat"),treeBuilder("sentence02-must-sub2.dat"),treeBuilder("sentence02-must-sub3.dat"));
		
		
		sentence_02.addMultipleChoiceToCan(treeBuilder("sentence02-can-sub1.dat"),treeBuilder("sentence02-can-sub2.dat"));
//		sentence_02.substituteCopyInTemplate(treeBuilder("sentence02-can-sub1.dat"));		
		
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
		
		SynTemplate.displayRandomPermutation(sentence_02.getMustUseTrees());

		
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
}
