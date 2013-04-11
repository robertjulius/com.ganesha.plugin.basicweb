package com.ganesha.plugin.basicweb.wizards;

import java.io.Serializable;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.ganesha.plugin.Utils;

public class RowItem implements Serializable {
	private static final long serialVersionUID = -3350574568355885396L;

	private String label;
	private String name;
	private String type;
	private String format;
	private boolean trueFalse;

	public RowItem() {
		// DO NOTHING
	}

	public RowItem(IField field) throws IllegalArgumentException,
			JavaModelException {
		this.name = field.getElementName();
		this.label = Utils.camelToHuman(this.name);
		this.type = Signature.toString(field.getTypeSignature());
	}

	public RowItem(String name, String label, String type, String format) {
		this.name = name;
		this.label = label;
		this.type = type;
		this.format = format;
	}

	public static RowItem createFromTableItem(TableItem tableItem) {
		RowItem rowItem = new RowItem();

		Table table = tableItem.getParent();
		TableColumn[] tableColumns = table.getColumns();
		for (int i = 0; i < tableColumns.length; ++i) {
			TableColumn tableColumn = tableColumns[i];
			String columnName = tableColumn.getText();
			if (columnName.equals("Label")) {
				rowItem.setLabel(tableItem.getText(i));
			} else if (columnName.equals("Name")) {
				rowItem.setName(tableItem.getText(i));
			} else if (columnName.equals("Type")) {
				rowItem.setType(tableItem.getText(i));
			} else if (columnName.equals("Editable")) {
				rowItem.setTrueFalse(tableItem.getChecked());
			}
		}

		return rowItem;
	}

	public void assignToTableItem(TableItem tableItem) {
		Table table = tableItem.getParent();
		TableColumn[] tableColumns = table.getColumns();
		for (int i = 0; i < tableColumns.length; ++i) {
			TableColumn tableColumn = tableColumns[i];
			String columnName = tableColumn.getText();
			if (columnName.equals("Label")) {
				tableItem.setText(i, getLabel());
			} else if (columnName.equals("Name")) {
				tableItem.setText(i, getName());
			} else if (columnName.equals("Type")) {
				tableItem.setText(i, getType());
			} else if (columnName.equals("Editable")) {
				tableItem.setChecked(getTrueFalse());
			}
		}
	}

	public String getFormat() {
		return format;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public boolean getTrueFalse() {
		return trueFalse;
	}

	public String getType() {
		return type;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTrueFalse(boolean trueFalse) {
		this.trueFalse = trueFalse;
	}

	public void setType(String type) {
		this.type = type;
	}
}
