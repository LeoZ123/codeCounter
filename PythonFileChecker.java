package codeCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PythonFileChecker {
	public static void pythonChecker(String file) throws FileNotFoundException, IOException {

		int lineCount = 0;
		int commentLineCount = 0;
		int singleLineCommentCount = 0;
		int commentLinesWithBlockCount = 0;
		int blockCommentCount = 0;
		int toDoCount = 0;
				
		int state = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				
				lineCount++;
				
				//remove Quotation content for special case, eg: "//this is not a comment"
				String clearedLine = removeQuationContent(line);
				
				//Check if the line contains #
				boolean hasCommentStartSymbol = hasCommentSymbol(clearedLine);
				
				//Check if the line contains TODO
				if(hasCommentStartSymbol && hasTodo(clearedLine)){
					toDoCount++;
				}
				
				//Using finite state machine to check comments
				switch (state) {
					case 0 :
						//state 0: initialial state, start with single line comment
						if (hasCommentStartSymbol) {
							commentLineCount++;
							state = 1;
						}				
						break;
					case 1 :
						//state 1: check if it has block comments
						if (hasCommentStartSymbol) {
							//if following line still have commment symbols, 
							//go to state 2 continue check block comments
							commentLineCount++;
							blockCommentCount++;
							commentLinesWithBlockCount += 2;
							state = 2;
						} else {
							//else previous the line is the single line comment
							//back to state 0
							singleLineCommentCount++;
							state = 0;
						}	
						break;
					case 2 :
						//state 2: block comment checker
						if (hasCommentStartSymbol) {
							//the line still in the block comment, remain state
							commentLineCount++;
							commentLinesWithBlockCount++;
						} else {
							//the previous line is the end of block comment
							//back to state 0
							state = 0;
						}	
						break;
					default:
						break;
				}    		    		  
			}
		}
		
		//If the the program ends with a comment and it is in state 1
		//the previous line is a single line comment => update counter
		if (state == 1){
			singleLineCommentCount++;
		}	
		
		print(lineCount, commentLineCount, singleLineCommentCount, commentLinesWithBlockCount, 
				blockCommentCount, toDoCount);
	}

	private static String removeQuationContent(String line) {
		return line.replaceAll("\".*\"", "quotationContent");
	}

	private static boolean hasCommentSymbol(String line) {
		return line.indexOf("#") >= 0;
	}

	private static boolean hasTodo(String line) {
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
