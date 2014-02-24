import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NaturalLogGenerator {

	public static void main(String[] args) {
		
		Scene input = new Scene();
		
		try { 
			
			input = ReadFile.reader();
			
		} catch (IOException e) {
			
			System.out.print("Something wrong with input File");
			
		}
		
		Statistics.sceneInfo(input);
		Statistics.showFrames(input);
		
//		String[] filter = {"situation", "activity", "action", "expectation"};
		String[] filter = {"situation"};
//		String[] filter = {""};
//		input.filterScene(filter, true).showAgeingOf();
//		input.filterScene(filter,false).showAgeingOf();
		Scene testScene = input.filterScene(filter, true);
		
		TemplatePool tPool = TreeGenerator.synTrees();
		
//		TreeGenerator.treeTest();
		
		KnowledgeBase knowledge = new KnowledgeBase();
		
		LanguageModule.showMatches(knowledge, tPool, testScene);

		List<String> resultLog = new ArrayList<String>();
		
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println("Log");
		System.out.println();
		resultLog = LanguageModule.LogBuilder(testScene, knowledge, tPool);
		
		for (int i = 0; i < resultLog.size(); i++) {
			
			System.out.println(resultLog.get(i));
			
		}
	}

}
