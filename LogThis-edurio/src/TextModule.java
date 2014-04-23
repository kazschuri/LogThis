import java.util.ArrayList;
import java.util.List;

public class TextModule {


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
	public static List<String> LogBuilder(Scene scene, KnowledgeBase knowledge, 
							TemplatePool pool, int detail, Boolean verbose) {
		
		List<String> naturalLog = new ArrayList<String>();

		for (int i = 0; i < scene.getSequences().length; i++) {

			knowledge.retireCurrentTopics();										// reset the knowledgebase for current Sequence
//			if (verbose) {
				
				naturalLog.add("----- Sequence "+i+" -----");
//			}
			
			naturalLog = knowledge.checkAndPickTemplates(pool, naturalLog, scene.getSequenceAt(i), detail); // pick as long as there are free topics
			if (verbose) {
			
				naturalLog = knowledge.findUnmentionedTopicsFrom(scene.getSequenceAt(i), naturalLog);
				
			}
		}
		
		return naturalLog;
	}
	
	/**
	 * generates a List of Templates, that are applicable for this Scene
	 * 
	 * @param pool the templatePool to use
	 * @param scene the scene to analyse
	 * TODO detail
	 * @return templateList the list of applicable Templates
	 */
	public static List<List<SynTemplate>> findAllApplicableTemplatesForScene(KnowledgeBase knowledge, TemplatePool pool, Scene scene, int detail) {
		
		List<List<SynTemplate>> templates = new ArrayList<List<SynTemplate>>();
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			templates.add(i, pool.findApplicableTemplates(knowledge, scene.getSequenceAt(i), detail));
			
		}
		
		return templates;
	}
	
	/**
	 * shows all Sequences and the applicable Templates
	 * @param pool the Templatepool to use
	 * @param scene the Scene to analyse
	 * TODO detail
	 */
	public static void showMatches(KnowledgeBase knowledge, TemplatePool pool, Scene scene, int detail) {
		
		List<List<SynTemplate>> templates = new ArrayList<List<SynTemplate>>();
				
		templates = findAllApplicableTemplatesForScene(knowledge, pool, scene, detail);
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			scene.showAgeingAt(i);
			for (int j = 0; j < templates.get(i).size(); j++) {
				
				templates.get(i).get(j).showTreeInfo();
				
			}
		}
		
	}
}
