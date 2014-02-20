import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KnowledgeBase {

	List<String> formerTopics;
	List<String> currentTopics;
	
	/**
	 * @param formerTopics
	 * @param currentTopics
	 */
	public KnowledgeBase(List<String> formerTopics, List<String> currentTopics) {
		super();
		this.formerTopics = formerTopics;
		this.currentTopics = currentTopics;
	}
	
	//TODO
	public KnowledgeBase() {
		super();
		formerTopics = new ArrayList<String>();
		currentTopics = new ArrayList<String>();
		
	}

	
	//TODO
	public void retireCurrentTopics() {
		
		formerTopics.clear();
		formerTopics.addAll(currentTopics);
		currentTopics.clear();
		
	}
	
	/**
	 * show all current Topics
	 */
	public void showCurrentTopics() {

		for (int i = 0; i < currentTopics.size(); i++) {
			
			System.out.println(currentTopics.get(i));
			
		}
	}

	/**
	 * show all retired Topics
	 */
	public void showFormerTopics() {

		for (int i = 0; i < formerTopics.size(); i++) {
			
			System.out.println(formerTopics.get(i));
			
		}
	}

	/**
	 * @return the formerTopics
	 */
	public List<String> getFormerTopics() {
	
		return formerTopics;
	}

	/**
	 * @param formerTopics the formerTopics to set
	 */
	public void setFormerTopics(List<String> formerTopics) {
	
		this.formerTopics = formerTopics;
	}

	/**
	 * @return the currentTopics
	 */
	public List<String> getCurrentTopics() {
	
		return currentTopics;
	}

	/**
	 * @param currentTopics the currentTopics to set
	 */
	public void setCurrentTopics(List<String> currentTopics) {
	
		this.currentTopics = currentTopics;
	}

	
	/**
	 * checks all Templates if they are applicable in this knowledge state
	 * @param templates the templates to check
	 * 
	 * @return possibleTemplates
	 */
	public SynTemplate[] checkAllTemplates(SynTemplate[] templates) {
		
		List<SynTemplate> applicableList = new ArrayList<SynTemplate>();
		
		boolean applicable = false;
		
		for (int i = 0; i < templates.length; i++) {
			
			applicable = this.checkTemplate(templates[i]);
			if (applicable) {
				
				applicableList.add(templates[i]);
			}
		}
		
		//TODO check what happens, if List is empty
		
		SynTemplate[] possibleTemplates = new SynTemplate[applicableList.size()];
		possibleTemplates = applicableList.toArray(possibleTemplates);
		
		return possibleTemplates;
	}
	
	/**
	 * checks if Template still has topics, that are not already in currentTopics
	 * 
	 * @param template the template to check
	 * @return TRUE if there is at least one topic in Template, that is not in currentTopics
	 */
	public boolean checkTemplate(SynTemplate template) {
		
		boolean applicable = false;

		for (int i = 0; i < template.getTopics().size(); i++) {
						
			applicable = !this.currentTopicsIncludes(template.getTopics().get(i)); // if topic is included, template not applicable
			
			if (applicable) { 								// if there is one topic that is not in currentTopic return True
				
				break;
			}
		}
		
		return applicable;
	}
	
	/**
	 * checks currentTopics for element
	 * 
	 * @param element the element to check
	 * @return TRUE if element is included
	 */
	public boolean currentTopicsIncludes(String element) {
		
		boolean included = false;
	
		for (int i = 0; i < currentTopics.size(); i++) {
			
			if (currentTopics.get(i).equalsIgnoreCase(element)) {
				
				included = true;
				break;
			}
		}
		
		return included;
	}

	
	/**
	 * checking all available Templates against the Knowledge Base, pick one 
	 * and start again, until no more applicable Templates
	 * 
	 * @param applicableTemplates the Templates that are available from this Sequence
	 * @return a String representation of all the chosen Templates
	 */
	public List<String> checkAndPickTemplates(SynTemplate[] applicableTemplates, List<String> log) {

		SynTemplate[] possibleTemplates = this.checkAllTemplates(applicableTemplates); // cross check with knowledge Database
		
		if (possibleTemplates.length > 0) {
			
			Random generator = new Random(System.currentTimeMillis());
			
			int pick = generator.nextInt(possibleTemplates.length);		// pick one Template at random

			log.add(possibleTemplates[pick].buildSentence());			// build the Sentence
			
			this.addToKnowledge(possibleTemplates[pick]);				// add new Information to knowledge Database
			
			log = this.checkAndPickTemplates(applicableTemplates, log); 	// recheck with same Templates but new knowledgebase
			
		} 
				
		return log;
	}

	
	/**
	 * adding the topics of the chosen synTemplate to the roster of current topics
	 * duplicate entries are removed
	 * 
	 * @param synTemplate the Template which topics are added to current topics
	 */
	private void addToKnowledge(SynTemplate synTemplate) {
		
		currentTopics.addAll(synTemplate.getTopics());
		
		currentTopics = GeneralMethods.removeDuplicate(currentTopics);
			
	}

}
