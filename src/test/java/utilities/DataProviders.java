package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name="LoginData")
	public String[][] getdata() throws IOException {
		
		String path = ".\\testData\\Opencart_LoginData.xlsx";
		
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int rows = xlutil.getRowCount("Sheet1");
		int  cell = xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][] = new String[rows][cell];
		
		for(int r=1;r<=rows;r++)//1 why because 0 row in excel is header part not needed
		{
			for(int c=0;c<cell;c++)//0 this is cell starts from 0
			{
				logindata[r-1][c]= xlutil.getCellData("Sheet1", r, c);//array index starts from 0
			}
		}
		return logindata;
		
	}
	
	//DataProvider2
	
	//DataProvider3
	
	//DataProvider4
}
