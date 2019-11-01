package nl.tudelft.jacoco;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import nl.tudelft.utils.Utilities;

/**
 * Task for executing test cases
 * 
 * @author annibale.panichella
 *
 */
public class TestExecutionTask  implements Callable<List<Result>>{

	private URL[] urls;
	private List<String> testClasses;
	private List<Result> results = new ArrayList<Result>();

	public TestExecutionTask(String cp, List<String> pTestClasses){
		try {
			// Load the jar
			urls = Utilities.createURLs(cp);
			testClasses = pTestClasses;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Result> call() throws ClassNotFoundException, IOException {
			URLClassLoader cl = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());	

			//GGG START
			for (URL u : urls){
				System.out.println("urls: "+u.toString());
			}
			//GGG END
			
			System.out.println("Running the tests: "+testClasses);
			for (String test : testClasses){
				
				if  (test.contains("_scaffolding")) {
					System.out.println("Skipped scaffolding test "+test);
					continue;
				}
				
				String[] classAndMethod = test.split("#");
				String testCaseClassName = classAndMethod[0];
				if(System.getProperty("os.name").startsWith("Windows")) {
					testCaseClassName = testCaseClassName.replace("\\", ".");
				}
				
				// load the test case
				final Class<?> testClass = cl.loadClass(testCaseClassName);
				Request request = Request.method(testClass, classAndMethod[1]);
				
				// Here we execute our test target class through its Runnable interface:
				JUnitCore junit = new JUnitCore();
				Result result = junit.run(request);

				results.add(result);

				//Main.debug("Failure: "+result.getFailures());
				//for (Failure fail : result.getFailures()){
				//	Main.debug("Failing Tests: "+fail.getTestHeader()+"\n"+fail.getException()+"\n"+fail.getDescription()+"\n"+fail.getMessage());
				//}
			}
			System.out.println("Executions terminated");
			cl.close();
			return results;
	}
	
	/** 
	 * Count the number of failing test methods
	 * @return number of failing test methods
	 */
	public int countFailingTests(){
		int count = 0;
		
		for (Result result : this.results){
			count += result.getFailureCount();
		}
		return count;
	}
	
	public List<Result> getExecutionResults(){
		return this.results;
	}

}
