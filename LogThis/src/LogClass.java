import java.io.IOException;


public class LogClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
			
			SequenceAgeingAnalyzer.cutAndDiff(input);
			
		} catch (IOException e)
		{
			System.out.print("Something wrong with input File");
		}
		
		
	}

}
