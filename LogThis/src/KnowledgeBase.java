import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KnowledgeBase {

	String[] formerTopics;
	String[] currentTopics;
	
	/**
	 * @param formerTopics
	 * @param currentTopics
	 */
	public KnowledgeBase(String[] formerTopics, String[] currentTopics) {
		super();
		this.formerTopics = formerTopics;
		this.currentTopics = currentTopics;
	}
	
	//TODO
	public KnowledgeBase() {
		super();
		this.formerTopics = new String[0];
		this.currentTopics = new String[0];
		
	}

	
	/**
	 * add a single topic to the end of currentTopics
	 * 
	 * @param topic the topic to add
	 */
	public void addToCurrentTopics(String topic) {
		
		List<String> tmpList = new ArrayList<String>(Arrays.asList(this.currentTopics));
		
		if (this.currentTopics.length == 0) {
			
			tmpList = new ArrayList<String>();
			
		}
		
		tmpList.add(topic);
		
		String[] tmpArray = new String[tmpList.size()];
		tmpArray = tmpList.toArray(tmpArray);
		
		this.currentTopics = tmpArray; 
	}

	//TODO
	public void retireCurrentTopics() {
		
		this.formerTopics = new String[this.currentTopics.length];
		this.formerTopics = this.currentTopics;
		this.currentTopics = new String[0];
		
	}
	
	/**
	 * show all current Topics
	 */
	public void showCurrentTopics() {

		for (int i = 0; i < this.currentTopics.length; i++) {
			
			System.out.println(this.currentTopics[i]);
			
		}
	}

	/**
	 * show all retired Topics
	 */
	public void showFormerTopics() {

		for (int i = 0; i < this.formerTopics.length; i++) {
			
			System.out.println(this.formerTopics[i]);
			
		}
	}

	/**
	 * @return the formerTopics
	 */
	public String[] getFormerTopics() {
	
		return formerTopics;
	}

	/**
	 * @param formerTopics the formerTopics to set
	 */
	public void setFormerTopics(String[] formerTopics) {
	
		this.formerTopics = formerTopics;
	}

	/**
	 * @return the currentTopics
	 */
	public String[] getCurrentTopics() {
	
		return currentTopics;
	}

	/**
	 * @param currentTopics the currentTopics to set
	 */
	public void setCurrentTopics(String[] currentTopics) {
	
		this.currentTopics = currentTopics;
	}
		
	
}
