import java.util.ArrayList;
import java.util.List;

public class LanguageModule {

	/**
	 * generates a List of Templates, that are applicable for this Scene
	 * 
	 * @param pool the templatePool to use
	 * @param scene the scene to analyse
	 * @return templateList the list of applicable Templates
	 */
	public static List<List<SynTemplate>> findAllApplicableTemplates(TemplatePool pool, Scene scene) {
		
		List<List<SynTemplate>> templateList = new ArrayList<List<SynTemplate>>();
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			templateList.add(i, pool.findApplicable(scene.getSequenceAt(i)));
			
		}
		
		return templateList;
	}
	
	/**
	 * shows all Sequences and the applicable Templates
	 * @param pool the Templatepool to use
	 * @param scene the Scene to analyse
	 */
	public static void showMatches(TemplatePool pool, Scene scene) {
		
		List<List<SynTemplate>> templateList = new ArrayList<List<SynTemplate>>();
		
		templateList = findAllApplicableTemplates(pool, scene);
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			scene.showAgeingAt(i);
			for (int j = 0; j < templateList.get(i).size(); j++) {
				
				templateList.get(i).get(j).showTreeInfo();
				
			}
		}
		
	}
}
