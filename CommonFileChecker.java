package codeCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommonFileChecker {
	
	public static void commonChecker(String file) throws FileNotFoundException, IOException {

		int lineCount = 0;
		int commentLineCount = 0;
		int singleLineCommentCount = 0;
		int commentLinesWithBlockCount = 0;
		int blockLineCommentsCount = 0;
		int toDoCount = 0;
		
		boolean withinCommentBlock = false;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	lineCount++;
		    	//remove Quotation content for special casa, eg: "//this is not a comment"
		    	String clearedLine = removeQuationContent(line);
			  	
		    	if (withinCommentBlock) {
		    		commentLinesWithBlockCount++;
		    		commentLineCount++;
		    		//check the end of block comment
		    		if (hasBlockCommentEndSymbol(clearedLine)){
		    			withinCommentBlock = false;
		    		}
		    	} else {
		    		if (hasSingleLineCommentBlockSymbol(clearedLine)) {
		    			//check single line block comment
			    		commentLinesWithBlockCount++;
			    		commentLineCount++;
			    		blockLineCommentsCount++;
		    		} else if (hasBlockCommentStartSymbol(clearedLine)) {
		    			//check the start of block comment
		    			withinCommentBlock = true;
		    			blockLineCommentsCount++;
		    			commentLineCount++;
		    			commentLinesWithBlockCount++;
		    		} else if (hasSingleLineCommentSymbol(clearedLine)){
		    			//check in-line comment
		    			singleLineCommentCount++;
		    			commentLineCount++;
		    			if (hasTODO(clearedLine)){
		    				toDoCount++;
		    			} 
		    		}
		    	}
		    }
		}
		
		print(lineCount, commentLineCount, singleLineCommentCount, commentLinesWithBlockCount, 
    		blockLineCommentsCount, toDoCount);
	}

	private static String removeQuationContent(String line) {
		return line.replaceAll("\".*\"", "quotationContent");
	}
	
	private static boolean hasSingleLineCommentBlockSymbol(String line) {
		return line.matches(".*\\/\\*.*\\*\\/.*");
	}
	
	private static boolean hasBlockCommentStartSymbol(String line) {
		return line.indexOf("/*") >= 0;
	}
	
	private static boolean hasBlockCommentEndSymbol(String line) {
		return line.indexOf("*/") >= 0;
	}
	
	private static boolean hasSingleLineCommentSymbol(String line) {
		return line.indexOf("//") >= 0;
	}
	
	private static boolean hasTODO(String line) {
		return line.indexOf("TODO") >= 0;
	}

	private static void print(int lineCount, int commentLineCount, int singleLineCommentCount, 
		int commentLinesWithBlockCount, int blockLineCommentsCount, int toDoCount) {
		System.out.println("Total # of lines: " + lineCount);
		System.out.println("Total # of comment lines: " + commentLineCount);
		System.out.println("Total # of single line comments: " + singleLineCommentCount);
		System.out.println("Total # of comment lines within block comments: " + commentLinesWithBlockCount);
		System.out.println("Total # of block line comments: " + blockLineCommentsCount);
		System.out.println("Total # of TODOs: " + toDoCount);
    }
}
