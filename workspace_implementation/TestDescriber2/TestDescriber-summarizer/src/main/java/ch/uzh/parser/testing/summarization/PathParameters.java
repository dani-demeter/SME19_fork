package ch.uzh.parser.testing.summarization;

import java.util.ArrayList;
import java.util.List;

public class PathParameters {

	public String sourceFolder = "";
	public String pBinFolder = "";
	public String testBinFolder = "";
	public List<String> classesFiles = new ArrayList<String>();
	public List<String> testsFiles = new ArrayList<String>();
	public List<String> testBinFiles = new ArrayList<String>();
	
	//constuctor
	public PathParameters(String srcFolder, String srcBinFolder, List<String> clsFiles, List<String> tstFiles) {
		sourceFolder = srcFolder;
		pBinFolder = srcBinFolder;
		classesFiles.addAll(clsFiles);
		testsFiles.addAll(tstFiles);
	}
	
	protected static PathParameters createPathParameters(){
		
		 //START CONFIGURATION FOR TASK 1
		String sourceFolder = "C:/Users/glopez/eclipse-workspace/SME_project/Task1/src/";
		String pBinFolder = "C:/Users/glopez/eclipse-workspace/SME_project/Task1/bin/";
		
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("org/magee/math/Rational.java"); 

		List<String> testsFiles = new ArrayList<String>();
		testsFiles.add("org/magee/math/TestRational.java");

		List<String> testBinFiles = new ArrayList<String>(); //Empty because in same folder
		String testBinFolder = "";
		//END CONFIGURATION FOR TASK 1
		
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, classesFiles, testsFiles);

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
