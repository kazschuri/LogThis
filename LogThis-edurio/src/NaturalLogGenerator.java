import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NaturalLogGenerator {

	public static void main(String[] args) {
		
		Scene input = new Scene();
		
		try { 
			
			input = InputProcessor.fileReader();
			
		} catch (IOException e) {
			
			System.out.print("Something wrong with input File");
			
		}
		
		Statistics.sceneInfo(input);
		Statistics.showFrames(input);
		
//		String[] filter = {"situation", "activity", "action", "expectation"};
//		String[] filter = {"situation", "action"};
//		String[] filter = {""};
		String[] filter = {"hmm_movement","hmm_pose","hmm_compound","intent_next","intent_target","object"};
//		String[] filter = {"hmm_movement","hmm_pose","hmm_compound","intent_placement","intent_next","intent_target","object"};
		Scene testScene = InputProcessor.filterScene(input, filter, false);
		
		TemplatePool tPool = TreeGenerator.synTrees(true);
		
		KnowledgeBase knowledge = new KnowledgeBase();
		
		TextModule.showMatches(knowledge, tPool, testScene, 1);

		List<String> resultLog = new ArrayList<String>();
		
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println("Log");
		System.out.println();
		resultLog = TextModule.LogBuilder(testScene, knowledge, tPool, 4, true, false);
		
		for (int i = 0; i < resultLog.size(); i++) {
			System.out.println(resultLog.get(i));
			
		}

	}

}
