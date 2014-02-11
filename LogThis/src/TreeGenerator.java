import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
					
		System.out.println(treeRoot.showTree());
		treeRoot.showLeafs();
		System.out.println();
		
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
		boolean foot = false;
		
		String lastLine = "";
		/*
		 * Read file, split lines into Nodes
		 */
		while ( (line = br.readLine()) != null ) {
			
			if (line.equals(lastLine) && line.startsWith(" \\branch{1}{N}")) {	// ignore double Noun lines
				
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
		
		Node node1 = new Node();
		Node node5 = new Node();
		Node node7 = new Node();
		Node node12 = new Node();
		
		node1.buildTreeOutOf("np(d^,n(boy,_))");
		node5.buildTreeOutOf("d(the,_)");
		node7.buildTreeOutOf("s(np^,vp(_,v(_,left)))");
		node12.buildTreeOutOf("vp(vp*,adv(today,_))");
		
		node1.showTerminal();
		node1.substitute(node5);
		
		System.out.println();
		
		node1.showTerminal();
		
		node7.substitute(node1);
		
		System.out.println();
		
		node7.showTerminal();
		
		node7.adjoin(node12);
		
		System.out.println();
		
		node7.showTerminal();
		System.out.println();
		System.out.println(node7.showTree());
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
		Node sent_07 = new Node("s(np^,vp(advp(v(was,_),adv(_,empty)),pp(p(for,_),np(ap(<time>,_),n(minutes,_)))))");	// Sentence with np^ and vp -> v = was
		Node sent_02 = new Node("np(d^,n^)");				// d^ n^
		Node sent_03 = new Node("vp(vp*,adv(empty,_))"); 	// vp* empty
		Node sent_04 = new Node("d(the,_)");				// d = the
		Node sent_09 = new Node("d(this,_)");				// d = this
		
		Node sent_08 = new Node("n(workplace,_)");			// n = workplace
		Node sent_10 = new Node("n(workspace,_)");			// n = workspace
		Node sent_11 = new Node("n(workarea,_)");			// n = workarea
		
		Node sent_05 = new Node("d(an,_)");					// D = an
		Node sent_06 = new Node("d(a,_)");					// D = a


		Node sent_01 = new Node("n(adv(empty,_),n*)");		// n adj = empty -> n*
//http://erg.delph-in.net/logon
		
		SynTemplate sentence_01 = new SynTemplate();
		sentence_01.setTemplate(Node.copyOf(sent_07));
		sentence_01.substituteCopyInTemplate(sent_02);
		
		sentence_01.getTemplate().showTerminal();
		System.out.println();
		sentence_01.getTemplate().showLeafs();
		System.out.println();
		System.out.println(sentence_01.getTemplate().showTree());
		
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
