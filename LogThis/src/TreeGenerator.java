
public class TreeGenerator {

	public static void smallTree (){
		
		Node node1 = new Node("np");
		Node node2 = new Node(false,true,false,"d");
		Node node3 = new Node("n");
		Node node4 = new Node(true,"boy");
		
		Node node5 = new Node("d");
		Node node6 = new Node(true,"the");
		
		Node node7 = new Node("s");
		Node node8 = new Node(false,true,false,"np");
		Node node9 = new Node("vp");
		Node node10 = new Node("v");
		Node node11 = new Node(true, "left");
		
		Node node12 = new Node("vp");
		Node node13 = new Node(false,false,true,"vp");
		Node node14 = new Node("adv");
		Node node15 = new Node(true,"today");
		
		node1.setLeftChild(node2);
		node1.setRightChild(node3);
		node3.setLeftChild(node4);
		
		node5.setLeftChild(node6);
		
		node7.setLeftChild(node8);
		node7.setRightChild(node9);
		node9.setRightChild(node10);
		node10.setRightChild(node11);
		
		node12.setLeftChild(node13);
		node12.setRightChild(node14);
		node14.setLeftChild(node15);
		
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
		
		
	}
}
