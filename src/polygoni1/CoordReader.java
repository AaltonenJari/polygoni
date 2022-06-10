package karsinta1;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class CoordReader {

	private String coordFileName = null;
	private List<Point2D> coordList = null;

	public boolean initialize(String fileName) {
		boolean status = false;
		this.coordFileName = fileName;
		FileHandler fileHandler = new FileHandler();
		try {
			File polygonFile = fileHandler.openFile(fileName, "r");
			if (polygonFile != null) {
				Scanner polygonReader = fileHandler.getFileReader(polygonFile);
				if (polygonFile != null) {
					//Käsitellään tiedoston rivit
					if (fileHandler.getLines(polygonReader)) {
						this.coordList = fileHandler.getCoordList();
						status = true;
					}
					//Suljetaan reader
					fileHandler.closeReaderFile(polygonReader);
				}
			}
			
		} catch (Exception e) {
		      System.out.println("Tiedostoa ei löydy.");
		      e.printStackTrace();
		}

		return status;
	}

	public String getCoordFileName() {
		return coordFileName;
	}

	public void setCoordFileName(String coordFileName) {
		this.coordFileName = coordFileName;
	}

	public List<Point2D> getCoordList() {
		return coordList;
	}

	public void setCoordList(List<Point2D> coordList) {
		this.coordList = coordList;
	}

}
