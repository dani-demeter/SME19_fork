package nl.tudelft.jacoco;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.utils.Utilities;

public class JaCoCoRunner {
	
	private List<File> extraClassPath = null;
	
	private JacocoResult jacoco_result;
	
	private File temporary_directory;

	/** 
	 * Runner for JaCoCo tasks. All files that would be generated by JaCoCo will be stored in the folder specified
	 * by the first parameter
	 * @param temp_directory
	 * @param classpath
	 */
	public JaCoCoRunner(File temp_directory, List<File> classpath){
		
		if (classpath == null) {
			extraClassPath = Collections.emptyList();
		} else {
			extraClassPath = new ArrayList<File>(classpath);
		}

		try {
			temporary_directory = temp_directory;
			Utilities.cleanDirectory(temporary_directory);
		} catch (IOException ioe) {
			System.out.println("Unable to clean bin directory! Aborting test execution!");
			return;
		}

	}

	/** 
	 * This method invokes {@link JaCoCoWrapper} to derive the coverage information for the
	 * specified CUT (first parameter) when running the specified test case (second parameter)
	 * @param cut class under test
	 * @param testCase test case to run (the name has to include the packages)
	 * @param instrumentedJar (folder or jar containing all *.class files)
	 */
	public void run(String cut, String testCase, List<File> instrumentedJar) {		
		try {
			System.out.println("\n=== Run Jacoco for Coverage ===");
			JaCoCoWrapper wrapper = new JaCoCoWrapper(temporary_directory.getAbsolutePath());

			List<String> testClasses = new ArrayList<String>();
			testClasses.add(testCase);
			wrapper.setTestCase(testClasses);

			wrapper.setJarInstrument(instrumentedJar);

			String cp = new Utilities.CPBuilder().and(extraClassPath).build();
			cp = cp.replaceAll("::", ":");
			wrapper.setClassPath(cp);
			wrapper.setTargetClass(cut);

			wrapper.runJaCoCo();
			JacocoResult result = wrapper.getResults();
			result.printResults();
			
			this.jacoco_result = result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JacocoResult getJacocoResults(){
		return this.jacoco_result;
	}
}