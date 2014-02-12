import java.util.ArrayList;
import java.util.List;

public class TestClass {

	List<List<Node>> lino;

	
	/**
	 * @param lino
	 */
	public TestClass(List<List<Node>> lino) {
		super();
		this.lino = lino;
	}

	public TestClass() {
	
		super();
		List<List<Node>> tmpListe = new ArrayList<List<Node>>();
		this.lino = tmpListe;
		
	}
	/**
	 * @return the lino
	 */
	public List<List<Node>> getLino() {
	
		return lino;
	}


	/**
	 * @param lino the lino to set
	 */
	public void setLino(List<List<Node>> lino) {
	
		this.lino = lino;
	}
	
	public void addLino(List<Node> lin) {
		
		this.lino.add(lin);
		
	}
	
	public void showLino() {
		
		for (int i = 0; i < lino.size(); i++) {
			
			for (int j = 0; j < lino.get(i).size(); j++) {
				
				System.out.print(lino.get(i).get(j).getData()+" ");
			}
			System.out.println();
			
		}
	}
}
