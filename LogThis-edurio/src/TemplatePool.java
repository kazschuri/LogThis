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

	/**
	 * find all applicable templates
	 * 
	 * @param knowledge the knowledge base
	 * @param sequence the sequence to use
	 * @param detail the required detail level
	 * 
	 * @return listOfApplicableTemplates
	 */
	public List<SynTemplate> findApplicableTemplates(KnowledgeBase knowledge, Sequence sequence, int detail) {
		
		List<SynTemplate> listOfApplicableTemplates = new ArrayList<SynTemplate>();
		
		for (int i = 0; i < this.pool.length; i++) {

			if(this.pool[i].isApplicable(knowledge, sequence, detail)) {
				
				listOfApplicableTemplates.add(this.pool[i]);
				
			}
		}
		
		return listOfApplicableTemplates;
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
	
	/**
	 * @return size of pool
	 */
	public int size() {
		
		return pool.length;
	}
}

