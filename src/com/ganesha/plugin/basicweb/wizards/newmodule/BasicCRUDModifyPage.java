package com.ganesha.plugin.basicweb.wizards.newmodule;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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

		Label lblSearchResults = new Label(container, SWT.NONE);
		lblSearchResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		lblSearchResults.setText("Show Fields");
		new Label(container, SWT.NONE);

		table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(107);
		tblclmnName.setText("Name");

		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(125);
		tblclmnType.setText("Type");

		TableColumn tblclmnEditableOnUpdate = new TableColumn(table, SWT.NONE);
		tblclmnEditableOnUpdate.setWidth(141);
		tblclmnEditableOnUpdate.setText("Editable on update");

		Composite pnlButtonResults = new Composite(container, SWT.NONE);
		pnlButtonResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		pnlButtonResults.setLayout(new GridLayout(1, false));

		Button btnAdd = new Button(pnlButtonResults, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd.setText("Add");

		Button btnRemove = new Button(pnlButtonResults, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove.setText("Remove");

	}
}
