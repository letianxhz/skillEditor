package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfLogicOrder {

	private int sceneSn;
	
	private String order;
	
	private static Map<Integer, ConfLogicOrder> datas = new HashMap<>();
	
	private static boolean modifyFlag = false;
	
	private ConfLogicOrder() {
		this.sceneSn = 0;
		this.order = "";
	}
	
	public int getSceneSn() {
		return sceneSn;
	}

	public String getOrder() {
		return order;
	}
	
	/**
	 * 获取顺序
	 * @return
	 */
	public List<Integer> getOrderList() {
		List<Integer> list = new ArrayList<>();
		
		String[] arrList = order.split(",");
		for (int i=0; i<arrList.length; ++i) {
			if (!arrList[i].isEmpty()) {
				list.add(Integer.valueOf(arrList[i]));
			}
		}
		
		return list;
	}
	
	/**
	 * 设置顺序
	 * @param list
	 */
	public void setOrderList(List<Integer> list) {
		String order = "";
		for (int i=0; i<list.size(); ++i) {
			order += list.get(i).toString();
			if ((i+1) != list.size()) {
				order += ",";
			}
		}
		
		if (!this.order.equals(order)) {
			this.order = order;
			
			modifyFlag = true;
		}
	}
	
	//----------------------------------------------------------
	// static function
	
	public static Map<Integer, ConfLogicOrder> getDatas() {
		return datas;
	}

	public static boolean isModifyFlag() {
		return modifyFlag;
	}

	public static ConfLogicOrder find(int sceneSn) {
		return datas.get(sceneSn);
	}
	
	/**
	 * 获取并创建
	 * @param sceneSn
	 * @return
	 */
	public static ConfLogicOrder getOrCreate(int sceneSn) {
		if (datas.containsKey(sceneSn)) {
			return datas.get(sceneSn);
		} else {
			ConfLogicOrder confLogicOrder = new ConfLogicOrder();
			confLogicOrder.sceneSn = sceneSn;
			confLogicOrder.order = "";
			
			datas.put(confLogicOrder.sceneSn, confLogicOrder);
			
			modifyFlag = true;
			
			return confLogicOrder;
		}
	}
	
	public static boolean load(String fileName) {
		/*XSSFWorkbook xssfWorkBook = SheetUtils.openXssfWorkBook(fileName);
		if (null == xssfWorkBook) {
			return false;
		}
		
		XSSFSheet xssfSheetInst = xssfWorkBook.getSheet("logicOrder");
		if (null == xssfSheetInst) {
			System.out.println("未读取到顺序数据。");
			return false;
		}
		
		datas.clear();
		
		int lastRowNum = xssfSheetInst.getLastRowNum();
		
		for (int i=0; i<=lastRowNum; ++i) {
			XSSFRow xssfRow = xssfSheetInst.getRow(i);

			int columnIndex = 0;
			
			ConfLogicOrder confLogicOrder = new ConfLogicOrder();
			confLogicOrder.sceneSn = SheetUtils.getNumericCellValue(xssfRow.getCell(columnIndex++));
			confLogicOrder.order = SheetUtils.getStringCellValue(xssfRow.getCell(columnIndex++));
			
			
			if (datas.containsKey(confLogicOrder.sceneSn)) {
				System.out.println("ConfLogicOrder have repeated data:" + confLogicOrder.sceneSn);
				continue;
			}
			
			datas.put(confLogicOrder.sceneSn, confLogicOrder);
		}*/
		
		return true;
	}
	
	/**
	 * 保存
	 * @param fileName
	 * @return
	 */
	public static boolean save(String fileName) {
		/*if (false == modifyFlag) {
			System.out.println("ConfLogicOrder没有数据修改，不进行保存!");
			return true;
		}
		
		XSSFWorkbook xssfWorkBook = SheetUtils.openXssfWorkBook(fileName);
		if (null == xssfWorkBook) {
			return false;
		}
		
		XSSFSheet xssfSheetInst = xssfWorkBook.getSheet("logicOrder");
		if (null != xssfSheetInst) {
			int lastRowNum = xssfSheetInst.getLastRowNum();
			
			for (int i=0; i<=lastRowNum; ++i) {
				XSSFRow xssfRow = xssfSheetInst.getRow(i);
				xssfSheetInst.removeRow(xssfRow);
			}
		} else {
			xssfSheetInst = xssfWorkBook.createSheet("logicOrder");
		}
		
		// 写入数据
		int rowIndex = 0;
		for (ConfLogicOrder confLogicOrder : datas.values()) {
			XSSFRow xssfRow = xssfSheetInst.createRow(rowIndex++);
			
			int columnIndex = 0;
			xssfRow.createCell(columnIndex++).setCellValue(confLogicOrder.sceneSn);
			xssfRow.createCell(columnIndex++).setCellValue(confLogicOrder.order);
		}
		
		if (SheetUtils.saveXssfSheet(fileName, xssfWorkBook)) {
			modifyFlag = false;
		}*/
		
		return true;
	}
}
