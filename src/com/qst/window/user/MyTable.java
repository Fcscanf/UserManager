package com.qst.window.user;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class MyTable extends JTable {

	private static final long serialVersionUID = -8084084488865863596L;

	public MyTable(DefaultTableModel dtm) {
		super(dtm);
	}

	public JTableHeader getTableHeader() {// 定义表格头
		// 获得表格头对象
		JTableHeader tableHeader = super.getTableHeader();
		// 设置表格列不可重排
		tableHeader.setReorderingAllowed(false);
		// 获得表格头的单元格对象
		DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
		// 设置列名居中显示
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		return tableHeader;
	}

	// 重写Jtbale类的getDefaultRenderer(Class<?>columnClass)方法
	public TableCellRenderer getDCellRenderer(Class<?> columnClass) {
		// 定义单元格
		// 获得表格的单元格对象
		DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
		// 设置单元格内容居中显示
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		return cr;

	}

	// 重写Jtable类的isCellEditable(int row,int column)方法
	public boolean isCellEditable(int row, int column) {// 表格不可编辑
		return false;
	}
	
	public void hideCol(int index) {
		
		TableColumn tc = this.getColumnModel().getColumn(index);
		tc.setWidth(index);
		tc.setMinWidth(index);
		
		this.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(index);
		this.getTableHeader().getColumnModel().getColumn(1).setMinWidth(index);
	}

}
