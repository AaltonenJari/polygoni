package karsinta1;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;


public class Ppoig {

	private String polygonFileName = "polygoni.txt";
	private String pointnFileName = "pisteet.txt";
	private String resultFileName = "selvitys.txt";
	List<Point2D> polygon = null;
	List<Point2D> points = null;
	
	public boolean getPolygon() {
		boolean status = false;
		
		CoordReader coordReader = new CoordReader();
		status = coordReader.initialize(polygonFileName);
		if (status) {
			polygon = coordReader.getCoordList();
		}

		return status;
	}
	
	public boolean getPoints() {
		boolean status = false;
		
		CoordReader coordReader = new CoordReader();
		status = coordReader.initialize(pointnFileName);
		if (status) {
			points = coordReader.getCoordList();
		}

		return status;
	}
	
	public boolean saveResults(String fileName, String textLine) {
		boolean status = false;
		
		FileHandler fileHandler = new FileHandler();		
		
		try {
			File resultFile = fileHandler.openFile(fileName, "w");
			if (fileHandler.createFile(resultFile)) {
				FileWriter resultWriter = fileHandler.getWriter(fileName);
				if (resultWriter != null) {
					if (fileHandler.writeLine(resultWriter, textLine)) {
						status = true;
					}
					//Suljetaan writer
					fileHandler.closeWriterFile(resultWriter);
				}
				
			}
		} catch (Exception e) {
		      System.out.println("Tiedostoa ei löydy.");
		      e.printStackTrace();
		}

		return status;
	}
	
	public boolean checkPolygon() {
		
		if (this.polygon == null) {
			return false;
		}
		
		if (this.polygon.isEmpty()) {
			return false;
		}
		
		int vertCount = this.polygon.size();
		//Polygonissa pitää olla vähintään kolme pistettä
		if (vertCount < 3)  {
			return false;
		}
		
		double x0 = this.polygon.get(0).getX();
		double y0 = this.polygon.get(0).getY();
		double xn = this.polygon.get(vertCount-1).getX();
		double yn = this.polygon.get(vertCount-1).getY();
		if (x0 != xn || y0 != yn)
			return false;
			
		return true;
	}

	public int isInside(double pointX, double pointY) {
		
		boolean inPoly = false;
		int vertCount = this.polygon.size();
		for (int i = 0, j = vertCount - 1; i < vertCount; j = i++) {
			double x1 = this.polygon.get(i).getX();
			double y1 = this.polygon.get(i).getY();
			double x2 = this.polygon.get(j).getX();
			double y2 = this.polygon.get(j).getY();
			
	        if (pointX == x1 && pointY == y1 || pointX == x2 && pointY == y2) return 0;
	        if (y1 == y2 && pointY == y1 && x1 <= pointX && pointX <= x2) return 0;
	        	        
	        if ((pointY >= y1 && pointY <= y2 || pointY <= y1 && pointY >= y2) && 
	           (pointY == y1 && y2 >= y1 || pointY == y2 && y1 >= y2)) return 0;
	        
	        
			if (((y1 > pointY) != (y2 > pointY)) &&
		            (pointX < (x2 - x1) * (pointY - y1) / (y2 - y1) + x1))
				inPoly = !inPoly;
		}
		return inPoly? 1 : -1;
	}
	
	public String getPointsInsideResults() {
		String resultString = "";
		
		int pointCount = this.points.size();
		for (int i=0; i < pointCount; i++) {
			double pointX = this.points.get(i).getX();
			double pointY = this.points.get(i).getY();

			int test = isInside(pointX, pointY);
			if (test == 0) {
				resultString += "Piste ("+pointX+", "+pointY + ") on reunalla.\n";
			} else if (test == 1) {
				resultString += "Piste ("+pointX+", "+pointY + ") on sisäpuolella.\n";
			} else {
				resultString += "Piste ("+pointX+", "+pointY + ") on ulkopuolella.\n";

			}
		}
		
		return resultString;
	}
	public void handleTask() {
		//Avataan pistetiedosto ja haetaan pisteet
		if (!this.getPoints())
			return ;
		
		//Avataan plygonin sisältävä tiedosto ja haetaan polygoni
		if (!this.getPolygon())
			return ;

		if (!this.checkPolygon())
			return ;
		
		String textLine = this.getPointsInsideResults();
		this.saveResults(this.resultFileName, textLine);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Ppoig ppoig = new Ppoig();
		//Käynnistetään tehtävä
		ppoig.handleTask();
		
	
	}


}
