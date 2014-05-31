import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TemplateBuilder {

	/**
	 * builds a template from a given input file
	 * file needs to have the correct syntax
	 * 
	 * @param filename the filename
	 * @param verbose flag for showing building information
	 * 
	 * @return the synTemplate
	 */
	public static List<SynTemplate> buildFromFile(String filename, boolean verbose) {
		
		List<List<String>> rawTemplates = new ArrayList<List<String>>();
		rawTemplates = divideFileIntoTemplates(filename, verbose);
		
		List<SynTemplate> synTemplateList = new ArrayList<SynTemplate>();
		
		for (int i = 0; i < rawTemplates.size(); i++) {
			
			List<List<String>> rawContent = rawTemplateSplitter(rawTemplates.get(i), verbose);
			
			SynTemplate synTemplate = templateBuilder(i, rawContent, verbose);
			synTemplateList.add(synTemplate);
						
		}
		return synTemplateList;
		
	}
	
	/**
	 * @param filename
	 * @param verbose
	 * @return
	 * TODO
	 */
	private static List<List<String>> divideFileIntoTemplates(String filename,
			boolean verbose) {
		if (verbose) {
			
			System.out.println();
			System.out.println("divide File into Templates");
			System.out.println("---------------------------------------");
			System.out.println();
		}
		String[] content = null;
		
		try { 

			content = readFile(filename);

		} catch (IOException e) {
			
			System.out.print("Something wrong with input File");

		}
		List<List<String>> listOfTemplates = new ArrayList<List<String>>();
		List<String> tmpTemplate = new ArrayList<String>();
		
		for (int i = 0; i < content.length; i++) {
			
			if (content[i].equalsIgnoreCase("<template>")) {
				
				tmpTemplate = new ArrayList<String>();
				
			} else if (content[i].equalsIgnoreCase("</template>")) {

				listOfTemplates.add(tmpTemplate);
				
			} else {
				
				tmpTemplate.add(content[i]);
			}
		}
		if (verbose) {
			
			System.out.println("there are "+listOfTemplates.size()+" Templates in this file");
			System.out.println();
		}
		return listOfTemplates;
	}
	/**
	 * Method to split up a SynTemplate file into different parts
	 * Splits file after every occurence of "words" and "-" in "<< >>"
	 *  
	 * @param filename the file to split
	 * @param verbose flag for showing building information
	 * 
	 * @return the split content
	 */
	private static List<List<String>> rawTemplateSplitter(List<String> templateList, boolean verbose) {

		List<List<String>> contentSplit = new ArrayList<List<String>>();
		List<String> partList = new ArrayList<String>();
		
		if (verbose) {
			System.out.println();
			System.out.println("templateFileSplitter");
			System.out.println("---------------------------------------");
			System.out.println();
			
			System.out.println(templateList.get(0));
		}
		
		for (int i = 0; i < templateList.size(); i++) {
			
			if (templateList.get(i).matches("<<[-\\w]*>>") && i > 0) {
				
				if (verbose) {
		
					System.out.println(partList.size()+" lines");
					System.out.println();
					System.out.println(templateList.get(i));
				}
				
				contentSplit.add(partList);
				partList = new ArrayList<String>();
			}
			
			partList.add(templateList.get(i).trim());
		}
		
		if (verbose) {
			
			System.out.println(partList.size()+" lines");
			System.out.println();
		}
		contentSplit.add(partList);
		
		return contentSplit;
		
	}

	/**
	 * reads File, ignoring empty lines and lines starting with "#"
	 * also trimming leading and trailing whitespaces
	 * 
	 * @param filename the file to read
	 * 
	 * @return content the content to return 
	 * @throws IOException
	 */
	private static String[] readFile(String filename) throws IOException {
		
		FileReader fr = new FileReader("file/"+filename);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		List<String> contentList = new ArrayList<String>();
				
		while ( (line = br.readLine()) != null ) {
			
			if (line.trim().isEmpty()) {
				
			} else if(line.trim().startsWith("#")) {
				
			} else {
				
				contentList.add(line.trim());

			}	
		}
		
		br.close();
		
		String[] content = new String[contentList.size()];
		content = contentList.toArray(content);
				
		return content;
	}
	
	/**
	 * Builds the SynTemplate by analyzing the content and distributing
	 * the pieces to various builders and into the new SynTemplate
	 * 
	 * @param listsOfContent the listsOfContent to work with
	 * @param verbose flag for showing building information
	 * 
	 * @return resultTemplate the resulting Template
	 */
	private static SynTemplate templateBuilder(int synTemplateName, List<List<String>> listsOfContent, boolean verbose) {
		
		if (verbose) {
			
			System.out.println();
			System.out.println("templateBuilder");
			System.out.println("---------------------------------------");
			System.out.println();
		}
		SynTemplate resultTemplate		= new SynTemplate();
		Node templateNode 				= new Node();
		List<List<Node>> mustUseTrees 	= new ArrayList<List<Node>>();
		List<List<Node>> canUseTrees 	= new ArrayList<List<Node>>();
		SlotCondition[] slots 			= new SlotCondition[0];
		LinkedConditions linConds 		= new LinkedConditions();
		List<String> topics 			= new ArrayList<String>();
		int detailLevel					= 0;
//TODO
//		String[] topics;
		
		for (int i = 0; i < listsOfContent.size(); i++) {
			
			List<String> currentList = listsOfContent.get(i);
			
			if (currentList.get(0).equalsIgnoreCase("<<Base-Tree>>")) {
				
				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
				}
				
				templateNode = TreeGenerator.buildTreeFromList(currentList.subList(1, 
									currentList.size()), 
									currentList.get(0),
									verbose);
										
			} else if (	currentList.get(0).equalsIgnoreCase("<<Must-Trees>>")||		// must and can have the same structure
						currentList.get(0).equalsIgnoreCase("<<Can-Trees>>")) {		// so one method
				
				boolean mustTree = false;
				boolean canTree = false;
				
				if (currentList.get(0).equalsIgnoreCase("<<Must-Trees>>")) {		// determine if must-tree			
					
					mustTree = true;
					
				} else if (currentList.get(0).equalsIgnoreCase("<<Can-Trees>>")) {	// or can-tree and set respective flag
					
					canTree = true;
				} 

				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
				}
				
				List<String> tmpList = new ArrayList<String>();
				List<Node> tmpNodeList = new ArrayList<Node>();
				
				for (String line : currentList.subList(1, currentList.size())) {
					
					if (line.equals("<set>")) {
						
						tmpNodeList = new ArrayList<Node>();
						
					} else if (line.equals("</set>")) {
						
						if (verbose) {
							System.out.println();
						}
						
						if (canTree) {
							
							canUseTrees.add(tmpNodeList);
							
						}else if (mustTree) {
							
							mustUseTrees.add(tmpNodeList);
						}						
						
					} else if (line.equals("<tree>")) {
						
						tmpList = new ArrayList<String>();
						
					} else if (line.equals("</tree>")) {

						tmpNodeList.add(TreeGenerator.buildTreeFromList(tmpList, currentList.get(0), verbose));
						
					} else {
						
						tmpList.add(line);
						
					}
					
				}
			} else if (currentList.get(0).equalsIgnoreCase("<<linkedConditions>>")) {

				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
					
				}
				String category 		= "";
				String subcategory 		= "";
				String element 			= "";
				int time				= 0;
				boolean exists 			= false;
				String link 			= "";
				LinkedConditions tmpLinConds = new LinkedConditions();
				boolean firstCond		= true;
				
				for (String line : currentList.subList(1, currentList.size())) {
					
					if (line.startsWith("<cond>")) {
						
					} else if (line.startsWith("<category=")) {

						category = line.substring(10, line.length()-1);
						
					} else if (line.startsWith("<subcat=")) {

						subcategory = line.substring(8, line.length()-1);
						
					} else if (line.startsWith("<element=")) {

						element = line.substring(9, line.length()-1);
					
					} else if (line.startsWith("<time=")) {

						time = Integer.parseInt(line.substring(6, line.length()-1));
						
					} else if (line.startsWith("<exists=")) {
						
						if (line.substring(8, line.length()-1).equalsIgnoreCase("true")) {
							
							exists = true;
							
						} else {
							
							exists = false;
						}

					} else if (line.startsWith("<link=")) {

						link = line.substring(6, line.length()-1);
						
					} else if (line.startsWith("</cond>")) {
						
						Condition tmpCond = new Condition(category, subcategory, element, time, exists);

						if (verbose) {
							
							if (!firstCond) {
								System.out.println(link);	
							}
							
							System.out.println(	"category: "+category+" subcategory: "+subcategory+
												" element: "+element+" time: "+time+" exists: "+exists);
						}
						
						if (firstCond) {						// prevent an empty entry in LinkedConditions
							
							Condition[] tmpCondArray = {tmpCond};
							String[] tmpLinkArray = {link};
							
							tmpLinConds = new LinkedConditions(tmpCondArray, tmpLinkArray);
							firstCond = false;
							
						} else {
						
							tmpLinConds.addCondition(tmpCond);
							tmpLinConds.addLink(link);
							
						}
						
					}
				}
				
				linConds = tmpLinConds;

			} else if (currentList.get(0).equalsIgnoreCase("<<slotConditions>>")) {
				
				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
					
				}
				
				String name				= "";
				String category 		= "";
				String element 			= "";
				
				List<SlotCondition> tmpSlotConds = new ArrayList<SlotCondition>();
				
				for (String line : currentList.subList(1, currentList.size())) {
					
					if (line.startsWith("<slot>")) {
						
					} else if (line.startsWith("<name=")) {

						name = line.substring(6, line.length()-1);

					} else if (line.startsWith("<category=")) {

						category = line.substring(10, line.length()-1);
						
					} else if (line.startsWith("<element=")) {

						element = line.substring(9, line.length()-1);
					
					} else if (line.startsWith("</slot>")) {
						
						SlotCondition tmpSlot = new SlotCondition(name, category, element);

						if (verbose) {
							
							System.out.println(	"name: "+name+" category: "+category+" element: "+element);
						}
						
						tmpSlotConds.add(tmpSlot);
					}
				}
				
				slots = new SlotCondition[tmpSlotConds.size()];
				slots = tmpSlotConds.toArray(slots);
				
			} else if (currentList.get(0).equalsIgnoreCase("<<topics>>")) {
				
				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
					
				}
				String topic	= "";
				
				for (String line : currentList.subList(1, currentList.size())) {
					
					if (line.startsWith("<topic=")) {
						
						topic = line.substring(7, line.length()-1);
						topics.add(topic);
						
						if (verbose) {
							
							System.out.println(	"topic: "+topic);
						}
					}
				}
				
			} else if (currentList.get(0).equalsIgnoreCase("<<templateLevel>>")) {
				
				if (verbose) {
					
					System.out.println();
					System.out.println(currentList.get(0));
					System.out.println();
					
				}
				
				for (String line : currentList.subList(1, currentList.size())) {
					
					if (line.startsWith("<level=")) {
						
						detailLevel = Integer.parseInt(line.substring(7, line.length()-1));
						
						if (verbose) {
							
							System.out.println(	"level: "+detailLevel);
						}
					}
				}
				
			} else {
				
				System.out.println("ERROR - \""+currentList.get(0)+"\" is not a recognized Determiner in Template File");
				
			}
			
		}
		
		resultTemplate.setSynTemplateName(synTemplateName);
		resultTemplate.setTemplate(templateNode);
		resultTemplate.setMustUseTrees(mustUseTrees);
		resultTemplate.setCanUseTrees(canUseTrees);
		resultTemplate.setLinConds(linConds);
		resultTemplate.setSlotCondition(slots);
		resultTemplate.setTopics(topics);
		resultTemplate.setDetailLevel(detailLevel);
		
		return resultTemplate;
	}
}
