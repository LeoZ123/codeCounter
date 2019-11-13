package codeCounter;

import java.io.IOException;

public class CommentCounter {
	
	public static void main(String[] args) { 
		
		String fileName = "sample3.py";
		
		boolean isPythonFile = fileName.substring(fileName.lastIndexOf('.') + 1).equals("py");
				
		try {
			if (isPythonFile){
				PythonFileChecker.pythonChecker(fileName);
			} 
			else{
				CommonFileChecker.commonChecker(fileName);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
