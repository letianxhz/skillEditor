package utils;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class SheetUtils {

	/**
	 * 字段列所在行索引(第3行)
	 */
	public static final int INDEX_OF_NAME_ROW = 2;
	
	/**
	 * 数据列的开始行数索引(第5行)
	 */
	public static final int INDEX_OF_DATA_BEGIN_ROW = 4;
	
	/**
	 * Trigger数据列的开始行数索引(第4行)
	 */
	public static final int INDEX_OF_TRIGGER_BEGIN_ROW = 4;

	/**
	 * 返回指定name值的列的num值
	 * 
	 * @param xssfSheet
	 * @param name
	 * @return
	 */
	public static int getCellNumByName(XSSFSheet xssfSheet, String name) {
		if (null == xssfSheet) {
			return -1;
		}

		XSSFRow xssfRow = xssfSheet.getRow(INDEX_OF_NAME_ROW);

		int firstCellNum = xssfRow.getFirstCellNum();
		int lastCellNum = xssfRow.getLastCellNum();

		for (int i = firstCellNum; i < lastCellNum; ++i) {
			XSSFCell xssfCell = xssfRow.getCell(i);
			if (xssfCell.getCellType() == CellType.STRING) {
				if (xssfCell.getStringCellValue().equals(name)) {
					return i;
				}
			}
		}

		return 0;
	}

	/**
	 * 获取指定列的所有cell
	 * @param xssfSheet
	 * @param num
	 * @return
	 */
	public static ArrayList<XSSFCell> getCellsByNum(XSSFSheet xssfSheet, int num) {
		ArrayList<XSSFCell> cells = new ArrayList<>();

		int firstRowNum = xssfSheet.getFirstRowNum();
		int lastRowNum = xssfSheet.getLastRowNum();

		for (int i = firstRowNum; i <= lastRowNum; ++i) {
			XSSFCell xssfCell = xssfSheet.getRow(i).getCell(num);
			cells.add(xssfCell);
		}

		return cells;
	}
	
	/**
	 * 获取xlsx文件的sheet页
	 * @param fileName
	 * @return
	 */
	public static XSSFWorkbook openXssfWorkBook(String fileName) {
		// 检查文件信息
		File file = new File(fileName);

		if (!file.exists()) {
			System.out.println("没有找到目标文件，路径设置有误。");
			return null;
		}

		XSSFWorkbook workBookOut = null;

		// 读取xls文件
		try {
			InputStream input = new FileInputStream(file);
			workBookOut = new XSSFWorkbook(input);
			input.close();
			return workBookOut;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean saveXssfSheet(String fileName, XSSFWorkbook xssfWorkbook) {
		// 检查文件信息
		File file = new File(fileName);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			xssfWorkbook.write(fileOutputStream);
			fileOutputStream.close();
			return true;
		} catch (IOException e) {
			MyUtils.alertDialogOk("错误", "保存excel数据异常", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public static int getNumericCellValue(XSSFCell xssfCell) {
		if (null != xssfCell) {
			if (xssfCell.getCellType() == CellType.NUMERIC) {
				return (int)xssfCell.getNumericCellValue();
			} else if (xssfCell.getCellType() == CellType.STRING) {
				return Integer.parseInt(xssfCell.getStringCellValue());
			}
		}
		return 0;
	}
	
	public static String getStringCellValue(XSSFCell xssfCell) {
		if (null != xssfCell) {
			if (xssfCell.getCellType() == CellType.STRING) {
				return xssfCell.getStringCellValue();
			} else if (xssfCell.getCellType() == CellType.NUMERIC) {
				return String.valueOf((int)xssfCell.getNumericCellValue());
			} else if (xssfCell.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(xssfCell.getBooleanCellValue());
			}
		}
		return "";
	}
	
	public static boolean getBooleanCellValue(XSSFCell xssfCell) {
		if (null != xssfCell) {
			if (xssfCell.getCellType() == CellType.BOOLEAN) {
				return xssfCell.getBooleanCellValue();
			}
		}
		return false;
	}
}
