import java.util.ArrayList;
import java.util.List;

public class LanguageModule {

	//TODO
	public static List<List<SynTemplate>> textGenerator(TemplatePool pool, Scene scene) {
		
		List<List<SynTemplate>> templateList = new ArrayList<List<SynTemplate>>();
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			templateList.add(i, pool.findApplicable(scene.getSequenceAt(i)));
			
		}
		
		return templateList;
	}
	
	//TODO
	public static void showMatches(TemplatePool pool, Scene scene) {
		
		List<List<SynTemplate>> templateList = new ArrayList<List<SynTemplate>>();
		
		templateList = textGenerator(pool, scene);
		
		for (int i = 0; i < scene.getSequences().length; i++) {
			
			scene.showAgeingAt(i);
			for (int j = 0; j < templateList.get(i).size(); j++) {
				
				templateList.get(i).get(j).showTreeInfo();
				
			}
		}
		
	}
}
