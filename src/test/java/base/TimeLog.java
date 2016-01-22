package base;

import org.junit.After;
import org.junit.Before;


public class TimeLog {
	
	public long startTime;
	
	@Before
	public void before() {
		System.out.println("TEST START");
		startTime = System.currentTimeMillis();
	}
	
	@After
	public void after() {
		System.out.println("TIME COST:" + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
