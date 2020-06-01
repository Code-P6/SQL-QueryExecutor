/* 
author @P6
*/

package test.web.ssdp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import commons.DataReader;

import commons.Utils;
import commons.Xls_Reader;


public class queryExecutor implements Runnable {
	private static String userName = DataReader.getData("userName");
	private static String password = DataReader.getData("password");
	private static String dbURL = DataReader.getData("dbURL");
	private static String EDSdatabase = DataReader.getData("database");
	
	protected static String feedSetUpPath;
	
	
	
	public static Map<String, String> getDataSheet(int rowNum, String sheetName) {
        Map<String, String> feedSheetData = new HashMap<>();
        feedSetUpPath = System.getProperty("user.dir") + File.separator +"test-output"+File.separator+ "TestData" + ".xls";
        Xls_Reader xls = new Xls_Reader(feedSetUpPath);
        feedSheetData = xls.readExcelDatabyRowNum(sheetName, rowNum);
        return feedSheetData;
	}
	
	public static int getRowCount(String sheetName){
		feedSetUpPath = System.getProperty("user.dir") + File.separator +"test-output"+File.separator+ "TestData" + ".xls";
        Xls_Reader xls = new Xls_Reader(feedSetUpPath);
		return Xls_Reader.getRowCount(sheetName);
	}
	public String query(){
		
	int noOfRows;
	String query = null;
	for (noOfRows  = getRowCount("Sheet1"); noOfRows<=2; noOfRows--){

		query = getDataSheet(noOfRows, "Sheet1").get("QUERY");
		System.out.println("ROW-->" + noOfRows);
	}

		return query;
	}

	public void run() {
		
	
		String fileName = "Result";
		String EDS = dbURL + EDSdatabase;
		Utils utils = new Utils();
		try {
			int noOfRows;
			String query = null;
			for (noOfRows  = getRowCount("Sheet1"); noOfRows>=2; noOfRows--){
		
				query = getDataSheet(noOfRows, "Sheet1").get("QUERY");
				System.out.println("ROW-->" + noOfRows);
				utils.getDBConnection(userName, password, EDS, query, fileName);
			}
		
		
			
			System.out.println("Thread " + Thread.currentThread().getId() + " is running");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		int n = 1; // Number of threads
		for (int i = 1; i <= n; i++) {
			Thread object = new Thread(new queryExecutor());
			object.start();
		}

	}

}