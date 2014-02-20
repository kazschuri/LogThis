import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TemplatePool {

	private SynTemplate[] pool;

	
	/**
	 * @param pool
	 */
	public TemplatePool(SynTemplate[] pool) {
		super();
		this.pool = pool;
	}

	public TemplatePool() {
		super();
		SynTemplate tmpSyn = new SynTemplate();
		SynTemplate[] tmpArr = {tmpSyn};
		this.pool = tmpArr;
	}
	
	/**
	 * add a single template to the end of the pool
	 * 
	 * @param template
	 */
	public void addTemplate(SynTemplate template) {
		
		List<SynTemplate> tmpPool = new ArrayList<SynTemplate>(Arrays.asList(this.pool));
		
		if (this.pool[0].getTemplate().getData().equals("")) {
			
			tmpPool = new ArrayList<SynTemplate>();
			
		}
		
		tmpPool.add(template);
		
		SynTemplate[] tmpArray = new SynTemplate[tmpPool.size()];
		tmpArray = tmpPool.toArray(tmpArray);
		
		this.pool = tmpArray; 
//		System.out.println(pool.length);
		
	}

	// TODO
	public SynTemplate[] findApplicableTemplates(Sequence sequence) {
		
		List<SynTemplate> listOfSequences = new ArrayList<SynTemplate>();
		
		for (int i = 0; i < this.pool.length; i++) {
			
			if(this.pool[i].isApplicable(sequence)) {
				
				listOfSequences.add(this.pool[i]);
				
			}
		}
		SynTemplate[] applicableSequences = new SynTemplate[listOfSequences.size()];
		applicableSequences = listOfSequences.toArray(applicableSequences);
		
		return applicableSequences;
	}
	
	/**
	 * @return the pool
	 */
	public SynTemplate[] getPool() {
	
		return pool;
	}


	/**
	 * @param pool the pool to set
	 */
	public void setPool(SynTemplate[] pool) {
	
		this.pool = pool;
	}
	
}
