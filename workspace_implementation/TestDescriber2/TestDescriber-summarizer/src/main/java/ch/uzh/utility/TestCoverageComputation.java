package ch.uzh.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ch.uzh.parser.JavaFileParser;
import ch.uzh.parser.bean.ClassBean;
import nl.tudelft.jacoco.JaCoCoRunner;
import nl.tudelft.jacoco.JacocoResult;
import nl.tudelft.utils.commandline.TestCaseParser;
/**
 * Class responsible for the test coverage computation (using the Jacoco API).
 * 
 * @author @sebastiano panichella
 *
 */
public class TestCoverageComputation {

	//Production code (i.e., the Java class) to consider for the test coverage computation
	Vector<ClassBean> productionClass = null;
	//Test class to consider for the test coverage computation
	Vector<ClassBean> testClass = null;
	
	// Map used to store the covered lines
    Map<String, List<Integer>> coverage = null;
    
 // number of test methods in 'test_case'
 	List<String> listTestMethods = null;
	
 	// folder binary code 
 	String pBinFolder = null;
	
 // outcome of the test coverage computation
 List<String> testsCoverage = null;
 
 // the list contains the fully classified path of the java class  
 List<String> pathJavaClass = new ArrayList<String>();

 // the list contains the fully classified path of the test class  
 List<String> pathTestClass = new ArrayList<String>();
	
	
	public TestCoverageComputation(Vector<ClassBean> productionClass, Vector<ClassBean> testClass,
			                       List<String> pathJavaClass, List<String> pathTestClass, String pBinFolder) {
		super();
		this.productionClass = productionClass;
		this.testClass = testClass;
		this.pBinFolder = pBinFolder;
		this.pathJavaClass =  pathJavaClass;
		this.pathTestClass = pathTestClass;
		
		// extract the number of test methods in 'test_case'
		this.listTestMethods  = TestCaseParser.findTestMethods(pBinFolder, convert2PackageNotation(pathTestClass.get(0)));
		System.out.println(listTestMethods);
		
		 // initialization of the list that will 
		//  contain the outcome of the test coverage computation
		this.testsCoverage = new ArrayList<String>();
		
		// Map used to store the covered lines
		Map<String, List<Integer>> coverage = new HashMap<String, List<Integer>>();
		//get directory from system property
		String project_dir = System.getProperty("user.dir");
		List<File> jars = new ArrayList<File>();
		jars.add(new File(pBinFolder));


		for (String tc : listTestMethods){
			String temp_file = project_dir+"/temp1";
			JaCoCoRunner runner = new JaCoCoRunner(new File(temp_file), jars);
			runner.run(convert2PackageNotation(this.pathJavaClass.get(0)), convert2PackageNotation(this.pathTestClass.get(0))+"#"+tc, jars);
			JacocoResult results = runner.getJacocoResults();
			System.out.println(results.getBranchesCovered());
			coverage.put(tc, new ArrayList<Integer>(results.getCoveredLines()));

			String covered = results.getCoverageInfoAsString();
			if (covered != null)
				testsCoverage.add(covered);
		}
		
	}
	
	protected static String convert2PackageNotation(String url){
		String path = url.replace(File.separator, ".");
		if (path.endsWith(".java"))
			path = path.substring(0, path.length()-5);
		return path;
	}

	
	public Map<String, List<Integer>> getCoverage() {
		return coverage;
	}



	public void setCoverage(Map<String, List<Integer>> coverage) {
		this.coverage = coverage;
	}



	public List<String> getListTestMethods() {
		return listTestMethods;
	}



	public void setListTestMethods(List<String> listTestMethods) {
		this.listTestMethods = listTestMethods;
	}



	public String getpBinFolder() {
		return pBinFolder;
	}



	public void setpBinFolder(String pBinFolder) {
		this.pBinFolder = pBinFolder;
	}



	public List<String> getTestsCoverage() {
		return testsCoverage;
	}



	public void setTestsCoverage(List<String> testsCoverage) {
		this.testsCoverage = testsCoverage;
	}



	public Vector<ClassBean> getProductionClass() {
		return productionClass;
	}
	
	public void setProductionClass(Vector<ClassBean> productionClass) {
		this.productionClass = productionClass;
	}
	
	public Vector<ClassBean> getTestClass() {
		return testClass;
	}
	
	public void setTestClass(Vector<ClassBean> testClass) {
		this.testClass = testClass;
	}

	
	
}