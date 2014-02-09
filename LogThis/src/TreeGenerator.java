

public class TreeGenerator {

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
		Node sent_07 = new Node("s(np^,vp(v(is,_),_))");	// Sentence with np^ and vp -> v = is
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

				
		sent_07.adjoin(sent_03);				// is empty
		sent_07.substitute(sent_02);			// d^[_/the 04/this 09] n^[~place 08/~space 10/~area 11] is empty 
		sent_07.substitute(sent_04);
		sent_07.substitute(sent_08);
		
		sent_07.showTerminal();
		
		/*
		 * a human/user/person enters the workspace
		 * a human enters the workspace with hanging arms
		 * a human enters with his arms hanging down/by his side
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
