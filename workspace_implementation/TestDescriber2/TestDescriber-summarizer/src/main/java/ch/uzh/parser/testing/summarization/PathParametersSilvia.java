package ch.uzh.parser.testing.summarization;

import java.util.ArrayList;
import java.util.List;

public class PathParametersSilvia {

	/*
	public String sourceFolder = "";
	public String pBinFolder = "";
	public String testSrcFolder = ""; //GGG not used yet
	public String testBinFolder = "";
	public String jarProjectFolder = "";
	public List<String> classesFiles = new ArrayList<String>();
	public List<String> testsFiles = new ArrayList<String>();
	public List<String> prefixTestMethods = new ArrayList<String>(); //optional
	public List<String> nameTestMethods = new ArrayList<String>(); // required when prefixTestMethods is set
	
	public List<String> getNameTestMethods() {
		return nameTestMethods;
	}

	public void setNameTestMethods(List<String> nameTestMethods) {
		this.nameTestMethods = nameTestMethods;
	}

	public List<String> getPrefixTestMethods() {
		return prefixTestMethods;
	}

	public void setPrefixTestMethods(List<String> prefixTestMethods) {
		this.prefixTestMethods = prefixTestMethods;
	}

	//constuctor
	public PathParametersSilvia(String srcFolder, String srcBinFolder, String tstSrcFolder, String tstBinFolder, List<String> clsFiles, List<String> tstFiles, String jarPrjFolder) {
		sourceFolder = srcFolder;
		pBinFolder = srcBinFolder;
		testSrcFolder = tstSrcFolder;
		testBinFolder = tstBinFolder;
		classesFiles.addAll(clsFiles);
		testsFiles.addAll(tstFiles);
		jarProjectFolder = jarPrjFolder;
	}
	*/
	
	protected static PathParameters createPathParameters_task1(){
		//START CONFIGURATION FOR TASK 1
		String jarProjectFolder = "D:/Uni/repositories/SME19_TestDescriber/SME19_TestDescriberProject/workspace_implementation/Task1/bin/"; //TODO GGG handle null?
		String sourceFolder = "D:/Uni/repositories/SME19_TestDescriber/SME19_TestDescriberProject/workspace_implementation/Task1/src/";
		String pBinFolder = "D:/Uni/repositories/SME19_TestDescriber/SME19_TestDescriberProject/workspace_implementation/Task1/bin/";
		String testSrcFolder = sourceFolder;
		String testBinFolder = pBinFolder;
		
		List<String> classesFiles = new ArrayList<String>();
		classesFiles.add("org/magee/math/Rational.java"); 

		List<String> testsFiles = new ArrayList<String>();
		//testsFiles.add("org/magee/math/TestRational.java");
		testsFiles.add("org/magee/math/TestRationalExtended.java");

		//END CONFIGURATION FOR TASK 1		
		
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles, jarProjectFolder);

		return pathParameters;
	}
	
	//createPathParameters_ofbiz
	protected static PathParameters createPathParametersSilvia_ofbiz(){
		
		String jarProjectFolder;
		String sourceFolder;
		String pBinFolder;
		String testSrcFolder;
		String testBinFolder; 
		// CUT class under test
		List<String> classesFiles = new ArrayList<String>();
		// Java file containing the Unit Tests to be applied to the CUT
		List<String> testsFiles = new ArrayList<String>();
		List<String> prfxTM = new ArrayList<String>();
		List<String> nameTM = new ArrayList<String>();
		
		// TimeDurationTests START// WORKS with TD!!!!!!!!!!!!!!!!!!!
		/*
		jarProjectFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		sourceFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/framework/base/src/main/java/";
        pBinFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		testSrcFolder = sourceFolder;
		testBinFolder = pBinFolder; 
		classesFiles.add("org/apache/ofbiz/base/util/TimeDuration.java");
		testsFiles.add("org/apache/ofbiz/base/util/test/TimeDurationTests.java");
		prfxTM.add("public void test");
		nameTM.add("test");
		*/
		// TimeDurationTests END// WORKS with TD!!!!!!!!!!!!!!!!!!!
		
		// StringUtilTests START // WORKS with TD!!!!!!!!!!!!!!!!!!!
		/*
		jarProjectFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		sourceFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/framework/base/src/main/java/";
        pBinFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		testSrcFolder = sourceFolder;
		testBinFolder = pBinFolder; 

		classesFiles.add("org/apache/ofbiz/base/util/StringUtil.java");
		
		testsFiles.add("org/apache/ofbiz/base/util/test/StringUtilTests.java");
		prfxTM.add("public void test");
		nameTM.add("test");
		*/
		// StringUtilTests END // WORKS with TD!!!!!!!!!!!!!!!!!!!
		
		
		// UtilIOTests START // half works
		jarProjectFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		sourceFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/framework/base/src/main/java/";
        pBinFolder = "C:/Data/workspaces/loca_SME19_TDOfB/apache-ofbiz-16.11.06/build/classes/java/main/";
		testSrcFolder = sourceFolder;
		testBinFolder = pBinFolder; 

		classesFiles.add("org/apache/ofbiz/base/util/UtilIO.java");

		testsFiles.add("org/apache/ofbiz/base/util/test/UtilIOTests.java");
		// UtilIOTests END // half works
		
		
		PathParameters pathParameters = new PathParameters(sourceFolder, pBinFolder, testSrcFolder, testBinFolder, classesFiles, testsFiles, jarProjectFolder);
		pathParameters.setPrefixTestMethods(prfxTM);
		pathParameters.setNameTestMethods(nameTM);

		return pathParameters;
	}

}
