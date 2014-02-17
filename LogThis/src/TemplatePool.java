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

	/**
	 * add a single template to the end of the pool
	 * 
	 * @param template
	 */
	public void addTemplate(SynTemplate template) {
		
		List<SynTemplate> tmpPool = new ArrayList<SynTemplate>(Arrays.asList(this.pool));
		
		tmpPool.add(template);
		
		SynTemplate[] tmpArray = new SynTemplate[tmpPool.size()];
		tmpArray = tmpPool.toArray(tmpArray);
		
		this.pool = tmpArray; 
		
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
