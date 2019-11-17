package ch.uzh.parser.testing.summarization;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import nl.tudelft.jacoco.JaCoCoRunner;
import nl.tudelft.jacoco.JacocoResult;
import nl.tudelft.utils.commandline.TestCaseParser;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.comments.BlockComment;

import ch.uzh.parser.JavaFileParser;
import ch.uzh.parser.bean.ClassBean;
import ch.uzh.parser.bean.MethodBean;
import ch.uzh.utility.TestCoverageComputation;

/**
 * Main parser for Test case Summarization..
 * @author Sebastiano Panichella and Annibale Panichella and Gabriela Lopez Magaña
 *
 */
public class Main_TD_2019 extends SrcSummarization {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 *
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		
		PathParameters pathParameters = PathParameters.createPathParameters(); 

		System.out.println("Step 1: Parsing JAVA CLASSES/JAVA TESTS");
		Vector<ClassBean> productionClass = JavaFileParser.parseJavaClasses(pathParameters.classesFiles, pathParameters.sourceFolder);
		Vector<ClassBean> testClass = JavaFileParser.parseJavaClasses(pathParameters.testsFiles, pathParameters.testSrcFolder);

		System.out.println("Step 2: Running JaCoCo ");
		ClassBean classeTest = testClass.get(0);
		ClassBean clazz = productionClass.get(0);
		
		//class responsible for the test coverage computation (using the Jacoco API).
		TestCoverageComputation testCoverageComputation = new TestCoverageComputation(productionClass, testClass,
				pathParameters);

		List<String> testsCoverage = testCoverageComputation.getTestsCoverage();

		if (testsCoverage.size() == 0) {
			System.out.println("No coverage information are obtained "+testsCoverage);
			return;
		}

		List<MethodBean> testCases = (Vector<MethodBean>) classeTest.getMethods();

		System.out.println("Step 3: Parsing Covered Statements");
		List<String> textContentExecutedOriginalClass = null;
		for(int index=0; index<testCases.size(); index++) {
			textContentExecutedOriginalClass=SrcMLParser.parseSrcML(testCases, index, clazz, pathParameters.sourceFolder, pathParameters.classesFiles, 0, testsCoverage);
		}

		// Derive the import from the class
		clazz.setImports(clazz.extractImports(textContentExecutedOriginalClass));

		System.out.println("Step 4: Generating Summaries as code comments");

		generateTestMethodsSummary(classeTest, testCases, textContentExecutedOriginalClass);

		generateClassUnderTestSummary(pathParameters, classeTest, clazz);
	}

}

