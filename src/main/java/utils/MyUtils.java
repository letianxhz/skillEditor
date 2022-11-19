package utils;

import config.ConfProperties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import model.ActionInfoOfComboBox;
import model.CondInfoOfComboBox;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static utils.CsvUtil.read;

/**
 * @author Administrator
 *
 */
public class MyUtils {

	public static final String RELATION_EMPTY = "空关系";
	public static final String RELATION_AND = "AND关系";
	public static final String RELATION_OR = "OR关系";

	/**
	 * 关系转换
	 * @param relation
	 * @return
	 */
	public static String relationToName(int relation) {
		switch (relation) {
			case 1:
				return RELATION_AND;
			case 2:
				return RELATION_OR;
			default:
				return RELATION_EMPTY;
		}
	}

	/**
	 * 关系转换
	 * @param relationName
	 * @return
	 */
	public static int relationToValue(String relationName) {
		if (RELATION_AND.equals(relationName)) {
			return 1;
		} else if (RELATION_OR.equals(relationName)) {
			return 2;
		} else {
			return 0;
		}
	}


	/**
	 * 拼接符号定义"["
	 */
	public static final String JOIN_SIGNAL_LEFT = "[";

	/**
	 * 拼接符号定义"]"
	 */
	public static final String JOIN_SIGNAL_RIGHT = "]";

	/**
	 * 拼接符号定义 不匹配
	 */
	public static final String JOIN_SIGNAL_NOT_MATCH = "#";

	/**
	 * 拼接数值和显示名称
	 * @param value
	 * @param name
	 * @return
	 */
	public static String formatColumnValue(String value, String name) {
		return MyUtils.JOIN_SIGNAL_LEFT + value + MyUtils.JOIN_SIGNAL_RIGHT + name;
	}


	/**
	 * 弹出提示框
	 * @param title
	 * @param header
	 * @param content
	 */
	public static void alertDialogOk(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * 设置选中的条件下拉列表框选中项
	 * @param comboBox
	 * @param conditionSn
	 */
	public static void setSelectedCmbCondition(ComboBox<CondInfoOfComboBox> comboBox, String conditionSn) {
		if (conditionSn.isEmpty()) {
			comboBox.getSelectionModel().select(null);
			return;
		}
		for (CondInfoOfComboBox condInfoOfCombBox : comboBox.getItems()) {
			if (Objects.equals(condInfoOfCombBox.getConfCondition().getId(), conditionSn)) {
				comboBox.getSelectionModel().select(condInfoOfCombBox);
				break;
			}
		}
	}

	/**
	 * 设置选中的动作下拉列表框选中项
	 * @param comboBox
	 * @param actionSn
	 */
	public static void setSelectedCmbAction(ComboBox<ActionInfoOfComboBox> comboBox, String actionSn) {
		if (actionSn.isEmpty()) {
			comboBox.getSelectionModel().select(null);
			return;
		}
		for (ActionInfoOfComboBox actionInfoOfCombBox : comboBox.getItems()) {
			if (Objects.equals(actionInfoOfCombBox.getConfAction().getId(), actionSn)) {
				comboBox.getSelectionModel().select(actionInfoOfCombBox);
				break;
			}
		}
	}

	/**
	 * 判断数值类型
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str.length() == 0) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean  csv2Excel(String csvFullPatch, String fileName) {
		OutputStream os = null;
		try{
			String dataDirectoryPath = ConfProperties.getKeyValue(ConfProperties.CONF_DIR_TMP_PATH);
			String eventFileName = dataDirectoryPath + fileName;
			File f = new File(fileName);
			InputStream inputStream = new FileInputStream(csvFullPatch);
			List<String[]> dataList = read(inputStream, "UTF-8");
			HSSFWorkbook result = new HSSFWorkbook();
			HSSFSheet sheet = result.createSheet(fileName);

			for (int rowNum = 0; rowNum < dataList.size(); rowNum++) {
				String[] data = dataList.get(rowNum);
				HSSFRow row = sheet.createRow(rowNum);
				for (int columnNum = 0; columnNum < data.length; columnNum++) {
					HSSFCell cell = row.createCell(columnNum);
					cell.setCellValue(data[columnNum]);
				}
			}
			File xlsFile = null;
			String xlsPath = eventFileName+".xls";
			xlsFile = new File(xlsPath);
			xlsFile.createNewFile();
			os = new FileOutputStream(xlsFile);
			result.write(os);
		}catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				assert os != null;
				os.close();
			}catch (Exception e1){

			}
		}
		return true;
	}

	/**
	 * 将excel表格转成csv格式
	 * @param oldFilePath
	 * @param newFilePath
	 */
	public static void excelToCsv(String oldFilePath,String newFilePath){
		String buffer = "";
		Workbook wb =null;
		Sheet sheet = null;
		Row row = null;
		Row rowHead = null;
		List<Map<String,String>> list = null;
		String cellData = null;
		String filePath =oldFilePath ;

		wb = readExcel(filePath);
		if(wb != null){
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheet = wb.getSheetAt(i);
				// 标题总列数
				rowHead = sheet.getRow(i);
				if (rowHead == null) {
					continue;
				}
				//总列数colNum
				int colNum = rowHead.getPhysicalNumberOfCells();
				String[] keyArray = new String[colNum];
				Map<String, Object> map = new LinkedHashMap<>();

				//用来存放表中数据
				list = new ArrayList<Map<String,String>>();
				//获取第一个sheet
				sheet = wb.getSheetAt(i);
				//获取最大行数
				int rownum = sheet.getPhysicalNumberOfRows();
				//获取第一行
				row = sheet.getRow(0);
				//获取最大列数
				int colnum = row.getPhysicalNumberOfCells();
				for (int n = 0; n<rownum; n++) {
					row = sheet.getRow(n);
					for (int m = 0; m < colnum; m++) {

						cellData =  getCellFormatValue(row.getCell(m)).toString();

						buffer +=cellData;
					}
					buffer = buffer.substring(0, buffer.lastIndexOf(",")).toString();
					buffer += "\n";

				}

				String savePath = newFilePath;
				File saveCSV = new File(savePath);
				try {
					if(!saveCSV.exists())
						saveCSV.createNewFile();
					BufferedWriter writer = new BufferedWriter(new FileWriter(saveCSV));
					writer.write(buffer);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}


	//读取excel
	public static Workbook readExcel(String filePath){
		Workbook wb = null;
		if(filePath==null){
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			if(".xls".equals(extString)){
				return wb = new HSSFWorkbook(is);
			}else if(".xlsx".equals(extString)){
				return wb = new XSSFWorkbook(is);
			}else{
				return wb = null;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	public static Object getCellFormatValue(Cell cell){
		Object cellValue = null;
		if(cell!=null) {
			var ct = cell.getCellType();

			if (ct == CellType.NUMERIC) {
				String cellva = getValue(cell);
				cellValue = cellva.replaceAll("\n", " ") + ",";
			} else if (ct == CellType.FORMULA) {
				//判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					//转换为日期格式YYYY-mm-dd
					cellValue = String.valueOf(cell.getDateCellValue()).replaceAll("\n", " ") + ",";
				} else {
					//数字
					cellValue = String.valueOf(cell.getNumericCellValue()).replaceAll("\n", " ") + ",";
				}
			} else if (ct == CellType.STRING) {
				cellValue = String.valueOf(cell.getRichStringCellValue()).replaceAll("\n", " ") + ",";
			} else {
				cellValue = ",";
			}
		}
		return cellValue;
	}

	/**
	 * 此方法为去掉转csv时数字等默认加上的小数点
	 * 如果不需要刻意不调用此方法
	 */
	public static  String getValue(Cell hssfCell) {
		if (hssfCell.getCellType() == CellType.BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == CellType.NUMERIC) {
			// 返回数值类型的值
			Object inputValue = null;// 单元格值
			Long longVal = Math.round(hssfCell.getNumericCellValue());
			Double doubleVal = hssfCell.getNumericCellValue();
			if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
				inputValue = longVal;
			}
			else{
				inputValue = doubleVal;
			}
			DecimalFormat df = new DecimalFormat("#");    //在此处更改小数点及位数，按自己需求选择；
			return String.valueOf(df.format(inputValue));      //返回String类型
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

}
