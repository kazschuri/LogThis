
public class Statistics {

	//TODO
	public static void showFrames(Scene scene) {
		
		for (int i = 0; i < scene.getSequences().length; i++) {

			if (i % 25 == 0) {

				System.out.println();

			}

			System.out.print(scene.getSequences()[i].getNumberOfFrames()+", ");
		}
		
		System.out.println();
	}
	
	//TODO
	public static void sceneInfo(Scene scene) {

		int numberOfSequences = scene.getSequences().length;

		String generalInformation = "Scene of " + scene.getNumberOfFrames() + " Frames in "+ numberOfSequences + " Sequences\r\n";
		generalInformation += "Starting at " + scene.getStartTime()+ " and ending at " + scene.getEndTime() + " this Scene is ";
		generalInformation += (scene.getEndTime() - scene.getStartTime()) + " timeunits long\r\n";
		generalInformation += "That calculates to " + (scene.getEndTime() - scene.getStartTime())/numberOfSequences + " timeunits per Sequence and "; 
		generalInformation += (scene.getEndTime() - scene.getStartTime())/scene.getNumberOfFrames() + " timeunits per Frame.";

		System.out.println(generalInformation);
	}

}
