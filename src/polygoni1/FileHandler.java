package karsinta1;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
	String fileName = "";
	List<Point2D> coordList = new ArrayList<Point2D>(1000);
	
	public File openFile(String filename, String mode) {
		this.fileName = filename;
		File fileObj = new File(filename);
		//If the file is opened for reading, check that file exists
		if (mode == "r") {
			if (!fileObj.exists()) {
				System.out.println("Tiedostoa " + this.fileName + " ei löydy.");
				fileObj = null;
			}
		}
		
		return fileObj;
	}
	
	public Scanner getFileReader(File fileObj) {
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(fileObj);
		} catch (FileNotFoundException e) {
			System.out.println("Tiedostoa " + this.fileName + " ei löydy.");
			e.printStackTrace();
		}
		return fileReader;
	}
	
	public boolean getLines(Scanner fileReader) {
		boolean status = false;
		boolean fuundLines = false;
		
		 while (fileReader.hasNextLine()) {
			 fuundLines = true;
			 String textLine = this.getLine(fileReader);
			 if (textLine != null) {
				 try {
					 this.parsePoints(textLine);
					 status = true;
				 } catch (Exception e) {
					 System.out.println("rivin tulkinta epäonnistui, Tiedosto " + this.fileName);
					 e.printStackTrace();
				 }
			 }
		 }
		 if (!fuundLines) {
			 System.out.println("Tiedostossa " + this.fileName + " ei ole rivejä");
		 }
		 return status;
	}
	public String getLine(Scanner fileReader) {
		String textLine = null;
		try {
			textLine = fileReader.nextLine();
		} catch (Exception e) {
			System.out.println("Luku epäonnistui, Tiedosto " + this.fileName);
			e.printStackTrace();
		}
		
		return textLine;
	}
	
	public void closeReaderFile(Scanner fileReader) {
		try {
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Tiedoston sulku epäonnistui, Tiedosto " + this.fileName);
			e.printStackTrace();
		}
	}

	public void closeWriterFile(FileWriter fileWriter) {
		try {
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Tiedoston sulku epäonnistui, Tiedosto " + this.fileName);
			e.printStackTrace();
		}
	}

	public boolean createFile(File fileObj) {
		boolean fileOK = false;
		try {
			fileObj.createNewFile();
			fileOK = true;
			
		} catch (IOException e) {
			System.out.println("File create failed, file: " + fileObj.getName());
			e.printStackTrace();
		}
		return fileOK;
	}
	
	public FileWriter getWriter(String filename) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}
	
	public boolean writeLine(FileWriter writer, String line) {
		boolean success = false;
		try {
			writer.write(line);
			success = true;
		} catch (IOException e) {
			System.out.println("Tiedostoon kirjoitus epäonnistui, Tiedosto " + this.fileName);
			e.printStackTrace();
		}
		return success;
	}
	
	public void parsePoints(String line) {
		Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(line);
		int index = 0;
		double x = 0.0;
		double y = 0.0;
		
		while(m.find()) {
            double value = Double.parseDouble(m.group());
            if (index%2 == 0) {
            	x = value;
            } else {
            	y = value;
            	Point2D.Double pointDouble = new Point2D.Double(x,y);
            	coordList.add(pointDouble);
            }
            index++;
        }
	}

	public List<Point2D> getCoordList() {
		return this.coordList;
	}
}
