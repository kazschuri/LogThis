import java.util.ArrayList;
import java.util.List;

public class TestClass {
	
	List<List<String>> must;
	List<List<String>> can;
	
	/**
	 * @param must
	 * @param can
	 */
	public TestClass(List<List<String>> must, List<List<String>> can) {
		super();
		this.must = must;
		this.can = can;
	}
	
	public TestClass() {
		
		List<List<String>> mustList = new ArrayList<List<String>>();
		this.must = mustList;
		List<List<String>> canList = new ArrayList<List<String>>();
		this.can = canList;
				
	}

	public void handPerm() {
		
		for (int i = 0; i < must.get(0).size(); i++) {
			
			System.out.print(must.get(0).get(i));
			
			for (int j = 0; j < must.get(1).size(); j++) {
			
				System.out.print("-"+must.get(1).get(j));
				for (int k = 0; k < must.get(2).size(); k++) {
					
					System.out.println("-"+must.get(2).get(k));
					
				}
			}
		}
	}
	
	public static List<String> getAllPermutationsOf(List<List<String>> list, int startLevel,
			String parentElement, List<String> permutationList) {
		
		int listSize = list.get(startLevel).size()-1;
		
		for (int i = 0; i < list.get(startLevel).size(); i++) {

			if (startLevel < list.size()-1) {
				
				permutationList = (getAllPermutationsOf(list, startLevel+1, 
						parentElement+list.get(startLevel).get(i), permutationList));
		
			} else {

//				System.out.println(element+liste.get(startIndex).get(i));
				permutationList.add(parentElement+list.get(startLevel).get(i));
				
			}

		}
		return permutationList;
	}
	
	public void addMultipleChoiceToMust(String... input) {
		
		List<String> tmpList = new ArrayList<String>();
		for (String text : input) {
			
			tmpList.add(text);
			
		}
		
		this.must.add(tmpList);
		
	}
	
	public void addMultipleChoiceToCan(String... input) {
		
		List<String> tmpList = new ArrayList<String>();
		for (String text : input) {
			
			tmpList.add(text);
			
		}
		
		this.can.add(tmpList);
	}
	
	public void showMust() {
		System.out.println();
		System.out.println("Must: ");
		for (int i = 0; i < must.size(); i++) {
			
			for (int j = 0; j < must.get(i).size(); j++) {
				
				System.out.print(must.get(i).get(j)+" / ");
			}
			System.out.println();
			
		}
	}
	
	public void showCan() {
		System.out.println();
		System.out.println("Can: ");
		for (int i = 0; i < can.size(); i++) {
			
			for (int j = 0; j < can.get(i).size(); j++) {
				
				System.out.print(can.get(i).get(j)+" / ");
			}
			System.out.println();
			
		}
	}
	/**
	 * @return the must
	 */
	public List<List<String>> getMust() {
	
		return must;
	}

	/**
	 * @param must the must to set
	 */
	public void setMust(List<List<String>> must) {
	
		this.must = must;
	}

	/**
	 * @return the can
	 */
	public List<List<String>> getCan() {
	
		return can;
	}

	/**
	 * @param can the can to set
	 */
	public void setCan(List<List<String>> can) {
	
		this.can = can;
	}
	
	 
	
	
	
	

}
