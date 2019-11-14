package ch.uzh.parser.testing.summarization;

import java.util.ArrayList;
import java.util.List;

public class PathParameters {

	public String sourceFolder = "";
	public String pBinFolder = "";
	public String testSrcFolder = ""; //GGG not used yet
	public String testBinFolder = "";
	public List<String> classesFiles = new ArrayList<String>();
	public List<String> testsFiles = new ArrayList<String>();
	public List<String> testBinFiles = new ArrayList<String>();
	
	//constuctor
	public PathParameters(String srcFolder, String srcBinFolder, String tstSrcFolder, String tstBinFolder, List<String> clsFiles, List<String> tstFiles) {
		sourceFolder = srcFolder;
		pBinFolder = srcBinFolder;
		testSrcFolder = tstSrcFolder;
		testBinFolder = tstBinFolder;
		classesFiles.addAll(clsFiles);
		testsFiles.addAll(tstFiles);
	}
	
	protected static PathParameters createPathParameters(){
		//START CONFIGURATION FOR TASK 1
		String sourceFolder = "C:/Data/workspaces/local_SME19_TestDescriberProject/SME19_TestDescriberProject/workspace_implementation/Task1/src/";
		String pBinFolder = "C:/Data/workspaces/local_SME19_TestDescriberProject/SME19_TestDescriberProject/workspace_implementation/Task1/bin/";
		String testSrcFolder = sourceFolder;
		String testBinFolder = pBinFolder;
		
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("org/magee/math/Rational.java"); 

		List<String> testsFiles = new ArrayList<String>();
		testsFiles.add("org/magee/math/TestRational.java");

		List<String> testBinFiles = new ArrayList<String>(); //Empty because in same folder
		//END CONFIGURATION FOR TASK 1		
		
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles);

		return pathParameters;
	}
	
	protected static PathParameters createPathParameters_gson(){
		
		// START CONFIGURATION FOR GSON

		String testSrcFolder = "C:/Data/uzh_2019_2/Software_Maintenance_and_Evolution/project/gson-master/gson/src/test/java/com/google/gson/"; ///CommentsTest.java";
		String testBinFolder = "C:/Data/uzh_2019_2/Software_Maintenance_and_Evolution/project/gson-master/gson/target/test-classes/com/google/gson/";
		
		// START CONFIGURATION FOR GSON CommentsTest
		// Test Case:  /gson/src/test/java/com/google/gson/CommentsTest.java
		// Class to test: com.google.gson.reflect.TypeToken
		String sourceFolder = "C:/Data/uzh_2019_2/Software_Maintenance_and_Evolution/project/gson-master/gson/src/main/java/com/google/gson/reflect/";
		String pBinFolder = "C:/Data/uzh_2019_2/Software_Maintenance_and_Evolution/project/gson-master/gson/target/classes/com/google/gson/reflect/";
		
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("TypeToken.java");
		 
		List<String> testsFiles = new ArrayList<String>();
		testsFiles.add("ParameterizedTypeTest.java");
		//testsFiles.add("CommentsTest.java");
		
		
		List<String> testBinFiles = new ArrayList<String>();
		testBinFiles.add("ParameterizedTypeTest");
		//testBinFiles.add("CommentsTest");

		// END CONFIGURATION FOR GSON CommentsTest
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles);

		return pathParameters;
	}

	protected static PathParameters createPathParameters_task1_Mac(){
		//START CONFIGURATION FOR TASK 1
		String sourceFolder = "/Users/panichella/git/SME19_TestDescriberProject/workspace_implementation/Task1/src/";
		String pBinFolder = "/Users/panichella/git/SME19_TestDescriberProject/workspace_implementation/Task1/bin/";
		String testSrcFolder = sourceFolder;
		String testBinFolder = pBinFolder;
		
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("org/magee/math/Rational.java"); 

		List<String> testsFiles = new ArrayList<String>();
		testsFiles.add("org/magee/math/TestRational.java");

		List<String> testBinFiles = new ArrayList<String>(); //Empty because in same folder
		//END CONFIGURATION FOR TASK 1		
		
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles);

		return pathParameters;
	}

	protected static PathParameters createPathParameters_gson_Mac(){
		
		// START CONFIGURATION FOR GSON
		// START CONFIGURATION FOR GSON CommentsTest
		// Test Case:  /gson/src/test/java/com/google/gson/CommentsTest.java
		// Class to test: com.google.gson.reflect.TypeToken
		String testSrcFolder = "/Users/panichella/Downloads/gson-master/gson/src/test/java/com/google/gson/"; 		
		String testBinFolder = "/Users/panichella/Downloads/gson-master/gson/target/test-classes/com/google/gson/";
		
		String sourceFolder = "/Users/panichella/Downloads/gson-master/gson/src/main/java/com/google/gson/reflect/";
		String pBinFolder = "/Users/panichella/Downloads/gson-master/gson/target/classes/com/google/gson/reflect/";
				
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("TypeToken.java");
		 
		List<String> testsFiles = new ArrayList<String>();
		testsFiles.add("ParameterizedTypeTest.java");
		//testsFiles.add("CommentsTest.java");
		
		
		List<String> testBinFiles = new ArrayList<String>();
		testBinFiles.add("ParameterizedTypeTest");
		//testBinFiles.add("CommentsTest");

		// END CONFIGURATION FOR GSON CommentsTest
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles);

		return pathParameters;
	}
	
	
	
	/*
	 * 		String sourceFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/Task1/src/";
        String pBinFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/Task1/bin/";
        
        //String sourceFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/commons-cli-1.4-src/src/";
        //String pBinFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/commons-cli-1.4-src/src/main/java/target/";
        
		//String sourceFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/apache-ofbiz-16.11.04/src/framework/base/src/main/java/";
        //String pBinFolder = "/Users/setup/Desktop/Publication/VENERA/TD-Extention/Eclipse/workspace2-modified-implementation-extention1/apache-ofbiz-16.11.04/src/build/classes/main/";
        
		//List<String> classesFiles = new ArrayList<String>();
		//classesFiles.add("/main/java/org/apache/commons/cli/Option.java");

		//List<String> testsFiles = new ArrayList<String>();
		//testsFiles.add("test/java/org/apache/commons/cli/OptionTest.java");

		List<String> classesFiles = new ArrayList<String>();
		//classesFiles.add("org/magee/math/Rational.java");
		classesFiles.add("org/magee/math/Rational.java");
		//classesFiles.add("org/apache/ofbiz/base/util/cache/UtilCache.java");
		//classesFiles.add("org/apache/ofbiz/base/util/TimeDuration.java");

		
		List<String> testsFiles = new ArrayList<String>();
		//testsFiles.add("org/magee/math/Rational_ESTest.java");
		testsFiles.add("org/magee/math/TestRational.java");
		//testsFiles.add("org/apache/ofbiz/base/util/cache/test/UtilCacheTests.java");
		//testsFiles.add("org/apache/ofbiz/base/util/test/TimeDurationTests.java");
		
	 */
	
}
