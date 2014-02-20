import java.util.ArrayList;
import java.util.List;

public class LanguageModule {


	/**
	 * finds possible Templates for each Sequence of a Scene and creates Text
	 * until no Templates are applicable any more
	 * 
	 * @param scene the scene to build into text
	 * @param knowledge the knowledge base in which to store information about chosen Templates
	 * @param pool the pool of Templates to choose from
	 * 
	 * @return the natural log data
	 */
	public List<String> LogBuilder(Scene scene, KnowledgeBase knowledge, 
							TemplatePool pool) {
		
		List<String> naturalLog = new ArrayList<String>();
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			knowledge.retireCurrentTopics();										// reset the knowledgebase for current Sequence
			Sequence sequence = scene.getSequenceAt(i);
			SynTemplate[] templates = pool.findApplicableTemplates(sequence);		// get all Templates for that Sequence
		
			if (templates.length > 0) {												// if there are any

				naturalLog = knowledge.checkAndPickTemplates(templates, naturalLog, sequence); // pick as long as there are free topics
			}
			 
		}
		
		return naturalLog;
	}
	
	/**
	 * generates a List of Templates, that are applicable for this Scene
	 * 
	 * @param pool the templatePool to use
	 * @param scene the scene to analyse
	 * @return templateList the list of applicable Templates
	 */
	public static SynTemplate[][] findAllApplicableTemplatesForScene(TemplatePool pool, Scene scene) {
		
		SynTemplate[][] templates = new SynTemplate[scene.getSequences().length][];
//		List<List<SynTemplate>> templateList = new ArrayList<List<SynTemplate>>();
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			templates[i] = pool.findApplicableTemplates(scene.getSequenceAt(i));
			
		}
		
		return templates;
	}
	
	/**
	 * shows all Sequences and the applicable Templates
	 * @param pool the Templatepool to use
	 * @param scene the Scene to analyse
	 */
	public static void showMatches(TemplatePool pool, Scene scene) {
		
		SynTemplate[][] templates = new SynTemplate[scene.getSequences().length][];
				
		templates = findAllApplicableTemplatesForScene(pool, scene);
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			scene.showAgeingAt(i);
			for (int j = 0; j < templates[i].length; j++) {
				
				templates[i][j].showTreeInfo();
				
			}
		}
		
	}
}
