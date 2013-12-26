import java.io.IOException;


public class LogClass {

	public static void main(String[] args) {
		
		Scene input;
		try 
		{ 
			input = ReadFile.reader();
			System.out.println(input.info());
			
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
			String[] filter = {"situation", "activity", "action"};
//			String[] filter = {"situation"};
//			input.includeOnly(filter).showAgeingOf();
			input.excludeFromScene(filter).showAgeingOf();
			
		} catch (IOException e)
		{
			System.out.print("Something wrong with input File");
		}
		
		
	}

}
