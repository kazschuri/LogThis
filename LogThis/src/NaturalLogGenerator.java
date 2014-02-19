import java.io.IOException;


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
		
		String[] filter = {"situation", "activity", "action", "expectation"};
//		String[] filter = {"situation"};
//		String[] filter = {""};
//		input.filterScene(filter, true).showAgeingOf();
//		input.filterScene(filter,false).showAgeingOf();
		Scene testScene = input.filterScene(filter, true);
		
		TemplatePool tPool = TreeGenerator.synTrees();
		
//		TreeGenerator.treeTest();
		System.out.println("test");
		LanguageModule.showMatches(tPool, testScene);
		
		
		
	}

}
