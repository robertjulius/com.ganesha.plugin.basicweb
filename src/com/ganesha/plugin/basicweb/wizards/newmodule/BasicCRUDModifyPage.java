package com.ganesha.plugin.basicweb.wizards.newmodule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.ganesha.plugin.Utils;
import com.ganesha.plugin.basicweb.wizards.RowItem;

public class BasicCRUDModifyPage extends WizardPage {

	public static final String NAME = BasicCRUDModifyPage.class.getName();
	private Table table;

	public BasicCRUDModifyPage() {
		super(NAME);
		setTitle("Modify Page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(140);
		tblclmnName.setText("Name");

		TableColumn tblclmnLabel = new TableColumn(table, SWT.NONE);
		tblclmnLabel.setWidth(140);
		tblclmnLabel.setText("Label");

		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(150);
		tblclmnType.setText("Type");

		Composite pnlButton = new Composite(container, SWT.NONE);
		pnlButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false,
				1, 1));
		pnlButton.setLayout(new GridLayout(1, false));

		Button btnAdd = new Button(pnlButton, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd.setText("Add");

		Button btnRemove = new Button(pnlButton, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = table.getSelectionIndex();
				if (i >= 0) {
					table.remove(i);
				}
			}
		});
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove.setText("Remove");

		Button btnUp = new Button(pnlButton, SWT.NONE);
		btnUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowUp(table);
			}
		});
		btnUp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnUp.setText("Up");

		Button btnDown = new Button(pnlButton, SWT.NONE);
		btnDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowDown(table);
			}
		});
		btnDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnDown.setText("Down");
	}

	public List<RowItem> getFields() {
		List<RowItem> rowItems = new ArrayList<>();
		for (int i = 0; i < table.getItemCount(); ++i) {
			rowItems.add(RowItem.createFromTableItem(table.getItem(i)));
		}
		return rowItems;
	}

	public void initFields() {
		try {
			IWizard wizard = getWizard();
			NewModuleWizardPage page = (NewModuleWizardPage) wizard
					.getPage(NewModuleWizardPage.class.getName());

			@SuppressWarnings("restriction")
			IField fields[] = page.getEntity().getFields();
			List<RowItem> rowItems = new ArrayList<>();
			for (int i = 0; i < fields.length; ++i) {
				if (fields[i].getElementName().equals("serialVersionUID")) {
					continue;
				}
				String name = fields[i].getElementName();
				name = "new" + name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				String label = Utils.camelToHuman(name);
				String type = Signature.toString(fields[i].getTypeSignature());
				rowItems.add(new RowItem(name, label, type, null));
			}
			table.setItemCount(rowItems.size());
			for (int i = 0; i < rowItems.size(); ++i) {
				rowItems.get(i).assignToTableItem(table.getItem(i));
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}
}
