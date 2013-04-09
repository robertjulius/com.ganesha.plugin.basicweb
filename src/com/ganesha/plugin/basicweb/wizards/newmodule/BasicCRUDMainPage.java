package com.ganesha.plugin.basicweb.wizards.newmodule;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class BasicCRUDMainPage extends WizardPage {

	public static final String NAME = BasicCRUDMainPage.class.getName();
	private Table tableCriterias;
	private Table tableResults;

	public BasicCRUDMainPage() {
		super(NAME);
		setTitle("Main Page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Composite pnlCriterias = new Composite(container, SWT.NONE);
		pnlCriterias.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		pnlCriterias.setLayout(new GridLayout(2, false));

		Label lblSearchCriterias = new Label(pnlCriterias, SWT.NONE);
		lblSearchCriterias.setText("Search Criterias");
		new Label(pnlCriterias, SWT.NONE);

		tableCriterias = new Table(pnlCriterias, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableCriterias.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		tableCriterias.setHeaderVisible(true);
		tableCriterias.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(tableCriterias, SWT.NONE);
		tblclmnName.setWidth(150);
		tblclmnName.setText("Name");

		TableColumn tblclmnType = new TableColumn(tableCriterias, SWT.NONE);
		tblclmnType.setWidth(160);
		tblclmnType.setText("Type");

		Composite pnlButtonCriterias = new Composite(pnlCriterias, SWT.NONE);
		pnlButtonCriterias.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, true, 1, 1));
		pnlButtonCriterias.setLayout(new GridLayout(1, false));

		Button btnAdd = new Button(pnlButtonCriterias, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd.setText("Add");

		Button btnRemove = new Button(pnlButtonCriterias, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove.setText("Remove");

		Composite pnlResults = new Composite(container, SWT.NONE);
		pnlResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		pnlResults.setLayout(new GridLayout(2, false));

		Label lblSearchResults = new Label(pnlResults, SWT.NONE);
		lblSearchResults.setText("Search Results");
		new Label(pnlResults, SWT.NONE);

		tableResults = new Table(pnlResults, SWT.BORDER | SWT.FULL_SELECTION);
		tableResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		tableResults.setHeaderVisible(true);
		tableResults.setLinesVisible(true);

		TableColumn tblclmnName_1 = new TableColumn(tableResults, SWT.NONE);
		tblclmnName_1.setWidth(150);
		tblclmnName_1.setText("Name");

		Composite pnlButtonResults = new Composite(pnlResults, SWT.NONE);
		pnlButtonResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		pnlButtonResults.setLayout(new GridLayout(1, false));

		Button btnAdd_1 = new Button(pnlButtonResults, SWT.NONE);
		btnAdd_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd_1.setText("Add");

		Button btnRemove_1 = new Button(pnlButtonResults, SWT.NONE);
		btnRemove_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove_1.setText("Remove");

	}

	public void initSearchCriterias() {
		try {
			IWizard wizard = this.getWizard();

			NewModuleWizardPage page = (NewModuleWizardPage) wizard
					.getPage(NewModuleWizardPage.class.getName());

			@SuppressWarnings("restriction")
			IField fields[] = page.getEntity().getFields();

			tableCriterias.setItemCount(fields.length);
			int itemCount = tableCriterias.getItemCount();
			for (int i = 0; i < itemCount; ++i) {
				TableItem item = tableCriterias.getItem(i);
				item.setText(0, fields[i].getElementName());
				item.setText(1,
						Signature.toString(fields[i].getTypeSignature()));
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}

	public void initSearchResults() {
		try {
			IWizard wizard = this.getWizard();

			NewModuleWizardPage page = (NewModuleWizardPage) wizard
					.getPage(NewModuleWizardPage.class.getName());

			@SuppressWarnings("restriction")
			IField fields[] = page.getEntity().getFields();

			tableResults.setItemCount(fields.length);
			int itemCount = tableResults.getItemCount();
			for (int i = 0; i < itemCount; ++i) {
				TableItem item = tableResults.getItem(i);
				item.setText(0, fields[i].getElementName());
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}
}
