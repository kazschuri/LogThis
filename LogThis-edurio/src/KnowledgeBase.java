import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class KnowledgeBase {

	List<String> formerTopics;
	List<String> currentTopics;
	
	/**
	 * Constructor with parameters
	 * @param formerTopics
	 * @param currentTopics
	 */
	public KnowledgeBase(List<String> formerTopics, List<String> currentTopics) {
		super();
		this.formerTopics = formerTopics;
		this.currentTopics = currentTopics;
	}
	
	/**
	 * Constructor of empty knowledgebase
	 */
	public KnowledgeBase() {
		super();
		formerTopics = new ArrayList<String>();
		currentTopics = new ArrayList<String>();
		
	}

	
	/**
	 * push current Topics to formerTopics and clear currentTopics
	 */
	public void retireCurrentTopics() {
		
		formerTopics.clear();
		if(currentTopics.size()>0) {
		
			formerTopics.addAll(currentTopics);
			currentTopics.clear();
			
		}
		
		
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
	 * check all Templates if they are applicable in this knowledge state
	 * @param applicableTemplates the templates to check
	 * 
	 * @return possibleTemplates
	 */
	public List<SynTemplate> checkAllTemplates(List<SynTemplate> applicableTemplates) {
		
		List<SynTemplate> possibleTemplates = new ArrayList<SynTemplate>();
		
		boolean applicable = false;
		
		for (int i = 0; i < applicableTemplates.size(); i++) {

			applicable = this.checkTemplate(applicableTemplates.get(i));
			if (applicable) {
				
				possibleTemplates.add(applicableTemplates.get(i));
			}
		}
		
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
	 * @param pool the pool of templates that is available
	 * @param log the current resulting log
	 * @param sequence the current sequence
	 * @param detail the detail level that is required
	 * @param info flag if more information is needed
	 * @return log with new sentences
	 */
	public List<String> checkAndPickTemplates(TemplatePool pool, List<String> log, Sequence sequence, int detail, Boolean info) {

		List<SynTemplate> possibleTemplates = this.checkAllTemplates(pool.findApplicableTemplates(this, sequence, detail)); // cross check with knowledge Database

		if (possibleTemplates.size() > 0) {
			Random generator = new Random(System.currentTimeMillis());

			int pick = generator.nextInt(possibleTemplates.size());					// pick one Template at random
			
			String tempSentence = possibleTemplates.get(pick).buildSentence(sequence).trim(); // build the Sentence
			
			if (info) {
				String formattedTemplateName = GeneralMethods.addLeadingZeros(pool.size() , possibleTemplates.get(pick).getSynTemplateName());
				tempSentence += " Template"+ formattedTemplateName + "";
			}
			
			log.add(tempSentence);	// add the Sentence

			this.addToKnowledge(possibleTemplates.get(pick));						// add new Information to knowledge Database

			log = this.checkAndPickTemplates(pool, log, sequence, detail, info); 	// recheck with new knowledgebase

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

	
	/**
	 * finding unmentioned Topics in a Sequence and presenting them
	 * @param sequence the sequence to search
	 * @param updatedLog the current state of the log
	 * @return updatedLog the updated state of the log
	 */
	public List<String> findUnmentionedTopicsFrom(Sequence sequence,
			List<String> updatedLog) {
		
		String[] dyingTypes = sequence.getDyingTypes();
		String[] newbornTypes = sequence.getNewbornTypes();
		String[] growingTypes = sequence.getGrowingTypes();
		
		for (int i = 0; i < dyingTypes.length; i++) {
			
			if (!this.currentTopicsIncludes(dyingTypes[i])) {
				
				updatedLog.add("Missing Template for dying Topic: " + dyingTypes[i]);
			}
		}

		for (int i = 0; i < newbornTypes.length; i++) {
			
			if (!this.currentTopicsIncludes(newbornTypes[i])) {
				
				updatedLog.add("Missing Template for newborn Topic: " + newbornTypes[i]);
			}
		}
		
		for (int i = 0; i < growingTypes.length; i++) {
			
			if (!this.currentTopicsIncludes(growingTypes[i])) {
				
				updatedLog.add("Missing Template for growing Topic: " + growingTypes[i]);
			}
		}

		
		return updatedLog;
	}

	
}
