package nl.tudelft.jacoco;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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
			
			//GGG remove start
			System.out.println("TestExecutionTask testClasses:");
			for (String cls : pTestClasses) {
				System.out.println("- "+cls);
			}
			//GGG remove end
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Result> call() throws ClassNotFoundException, IOException {
			URL[] newurls =new URL[1]; 
			newurls[0] = urls[1];
			URLClassLoader cl = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());	

			//TODO GGG EXPERIMENT ON LOADERS
			//String resource = "C:/Data/uzh_2019_2/Software_Maintenance_and_Evolution/project/gson-master/gson/target/test-classes/com/google/gson/ParameterizedTypeTest.class";
			//String resource = "/TestDescriber-summarizer/src/main/java/ch/uzh/parser/testing/summarization/Main_TD_2019.java";
			//String resource = "C:/Data/workspaces/local_SME19_TestDescriberProject/SME19_TestDescriberProject/workspace_implementation/TestDescriber2/TestDescriber-summarizer/src/main/java/ch/uzh/parser/testing/summarization/Main_TD_2019.java";
			//String resource ="C:/Data/workspaces/local_SME19_TestDescriberProject/SME19_TestDescriberProject/workspace_implementation/TestDescriber2/TestDescriber-JaCoCo/src/main/java/nl/tudelft/jacoco/TestExecutionTask.java";
			String resource = "C:/Data/workspaces/local_SME19_TestDescriberProject/SME19_TestDescriberProject/workspace_implementation/TestDescriber2/TestDescriber-summarizer/target/classes/";

			URL resource2 = cl.getResource(resource);
			URL resource3= this.getClass().getResource("/");
			String rc =resource3.toString();
			URL rcc = cl.getResource(rc);

			
			URL uu = this.getClass().getResource(rc);
			URL uuu = Thread.currentThread().getContextClassLoader().getResource(rc);
			URL uuuu = ClassLoader.getSystemClassLoader().getResource(rc);
			URL uuuuu = this.getClass().getClassLoader().getResource(rc);
			URL kd = this.getClass().getClassLoader().getResource("ch.uzh.parser.testing.summarization.Main_TD_2019");
			URL kdd = this.getClass().getClassLoader().getResource("org.eclipse.jdt.launching.internal.javaagent.Premain");
			
			//GGG EXPERIMENT END
			
			
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
				
				System.out.println("TestExecutionTask testCaseClassName: " + testCaseClassName);

				
				//TODO GGG DELETE class Loader test
				Field f;
		        try {
		            f = ClassLoader.class.getDeclaredField("classes");
		            f.setAccessible(true);
		            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		            //Vector<Class> classes =  (Vector<Class>) f.get(classLoader);
		            
		            //Vector<Class> classes =  (Vector<Class>) f.get(this.getClass().getClassLoader());
		            Vector<Class> classes =  (Vector<Class>) f.get((ClassLoader)cl);
		            
		            
		            for(Class cls : classes){
		                java.net.URL location = cls.getResource('/' + cls.getName().replace('.',
		                '/') + ".class");
		                if(location.toString().contains("local_SME19_TestDescriberProject")) {
		                	System.out.println("<p>"+location +"<p/>");
		                }
		            }
		        } catch (Exception e) {

		            e.printStackTrace();
		        }		
				//GGG end class Loader test
				
				
				// load the test case
				final Class<?> testClass = cl.loadClass(testCaseClassName);
				Request request = Request.method(testClass, classAndMethod[1]);
				
				// Here we execute our test target class through its Runnable interface:
				JUnitCore junit = new JUnitCore();
				Result result = junit.run(request);

				results.add(result);

				
				System.out.println("Failure: "+result.getFailures());
				for (Failure fail : result.getFailures()){
					System.out.println("Failing Tests: "+fail.getTestHeader()+"\n"+fail.getException()+"\n"+fail.getDescription()+"\n"+fail.getMessage());
				}
				
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
