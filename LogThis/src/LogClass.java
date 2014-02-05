import java.io.IOException;


public class LogClass {

	public static void main(String[] args) {
		
		Scene input = new Scene();
		
		try { 
			
			input = ReadFile.reader();
			System.out.println(input.info());
			
		} catch (IOException e) {
			
			System.out.print("Something wrong with input File");
			
		}
		
		for (int i = 0; i < input.getSequences().length; i++) {
			
			if (i % 25 == 0) {
			
				System.out.println();
			
			}
			
			System.out.print(input.getSequences()[i].getNumberOfFrames()+", ");
			
		}
		
		System.out.println();
		
		//input.showAgeingOf();
		System.out.println();
		System.out.println(input.info());
		String[] filter = {"situation", "activity", "action", "expectation"};
//		String[] filter = {"situation"};
//		String[] filter = {""};
		input.filterScene(filter, true).showAgeingOf();
//		input.filterScene(filter,false).showAgeingOf();
//		Scene testScene = input.filterScene(filter, true);
		
//		LogGUI.guiing();
		
		TreeGenerator.smallTree();
	}

}
