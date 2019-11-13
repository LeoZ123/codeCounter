package codeCounter;

import java.io.IOException;

public class CommentCounter {
	
	public static void main(String[] args) { 
		
		String fileName = "sample3.py";
		
		boolean isPythonFile = fileName.substring(fileName.lastIndexOf('.') + 1).equals("py");
				
		try {
			if (isPythonFile){
				//check by pound sign (#)
				PythonFileChecker.pythonChecker(fileName);
			} 
			else{
				//check by slash (// /**/)
				CommonFileChecker.commonChecker(fileName);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
