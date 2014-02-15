import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TemplateFileReader {

	// TODO javadoc
	public static List<List<String>> processFile(String filename) {

		String[] content = null;
		
		try { 

			content = readFile(filename);

		} catch (IOException e) {

			System.out.print("Something wrong with input File");

		}
		List<List<String>> contentSplit = new ArrayList<List<String>>();
		int part = 0;
		List<String> partList = new ArrayList<String>();
		
		for (int i = 0; i < content.length; i++) {
			
			if (content[i].matches("<<[-\\w]*>>") && i > 0) {

				contentSplit.add(partList);
				partList = new ArrayList<String>();
			}
			
			partList.add(content[i].trim());
		}
		
		contentSplit.add(partList);
		
		return contentSplit;
		
	}

	// TODO javadoc
	private static String[] readFile(String filename) throws IOException {
		
		FileReader fr = new FileReader("file/"+filename);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		List<String> contentList = new ArrayList<String>();
				
		while ( (line = br.readLine()) != null ) {
			
			if (line.trim().isEmpty()) {
				
			} else if(line.trim().startsWith("#")) {
				
			} else {
				
				contentList.add(line);

			}	
		}
		
		br.close();
		
		String[] content = new String[contentList.size()];
		content = contentList.toArray(content);
				
		return content;
	}
	
	public static void fileDistributor(List<List<String>> contentLists) {
		
		Node templateNode 				= new Node();
		List<List<Node>> mustUseTrees 	= new ArrayList<List<Node>>();
		List<List<Node>> canUseTrees 	= new ArrayList<List<Node>>();;
//		Node[] slotFillers;
		LinkedConditions linConds 		= new LinkedConditions();
//		String[] topics;
		
		for (int i = 0; i < contentLists.size(); i++) {
			
			if (contentLists.get(i).get(0).equalsIgnoreCase("<<Base-Tree>>")) {
				System.out.println(contentLists.get(i).get(0));
				System.out.println(contentLists.get(i).size()-1);
				
				templateNode = TreeGenerator.buildNodeFromList(contentLists.get(i).subList(1, contentLists.get(i).size()), contentLists.get(i).get(0));
										
			} else if (contentLists.get(i).get(0).equalsIgnoreCase("<<Must-Trees>>")||contentLists.get(i).get(0).equalsIgnoreCase("<<Can-Trees>>")) {
				
				boolean mustTree = false;
				boolean canTree = false;
				
				if (contentLists.get(i).get(0).equalsIgnoreCase("<<Must-Trees>>")) {
					
					mustTree = true;
					
				} else if (contentLists.get(i).get(0).equalsIgnoreCase("<<Can-Trees>>")) {
					
					canTree = true;
					
				} 
				System.out.println(contentLists.get(i).get(0));
				System.out.println(contentLists.get(i).size()-1);
				
				List<String> tmpList = new ArrayList<String>();
				List<Node> tmpNodeList = new ArrayList<Node>();
				
				for (String line : contentLists.get(i).subList(1, contentLists.get(i).size())) {
					
					if (line.equals("<set>")) {
						
						tmpNodeList = new ArrayList<Node>();
						
					} else if (line.equals("</set>")) {
						
						if (canTree) {
							
							canUseTrees.add(tmpNodeList);
							
						}else if (mustTree) {
							
							mustUseTrees.add(tmpNodeList);
						}						
						
					} else if (line.equals("<tree>")) {
						
						tmpList = new ArrayList<String>();
						
					} else if (line.equals("</tree>")) {

						tmpNodeList.add(TreeGenerator.buildNodeFromList(tmpList, contentLists.get(i).get(0)));

					} else {
						
						tmpList.add(line);
						
					}
					
				}
			} else if (contentLists.get(i).get(0).equalsIgnoreCase("<<linkedConditions>>")) {
				
				System.out.println(contentLists.get(i).get(0));
				System.out.println(contentLists.get(i).size()-1);
				
			} else {
				
				System.out.println("ERROR - \""+contentLists.get(i).get(0)+"\" is not a recognized Determiner in Template File");
				
			}
			
		}
		
	}
}
