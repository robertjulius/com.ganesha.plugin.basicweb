package com.ganesha.plugin.basicweb.wizards.newmodule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.ganesha.plugin.Utils;
import com.ganesha.plugin.basicweb.wizards.RowItem;
import com.ganesha.plugin.basicweb.wizards.newmodule.dialogs.NewSearchCriteriaDialog;

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
		tblclmnName.setWidth(140);
		tblclmnName.setText("Name");

		TableColumn tblclmnLabel = new TableColumn(tableCriterias, SWT.NONE);
		tblclmnLabel.setWidth(140);
		tblclmnLabel.setText("Label");

		TableColumn tblclmnType = new TableColumn(tableCriterias, SWT.NONE);
		tblclmnType.setWidth(150);
		tblclmnType.setText("Type");

		Composite pnlButtonCriterias = new Composite(pnlCriterias, SWT.NONE);
		pnlButtonCriterias.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, true, 1, 1));
		pnlButtonCriterias.setLayout(new GridLayout(1, false));

		Button btnAdd = new Button(pnlButtonCriterias, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openNewDialog();
			}
		});
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd.setText("Add");

		Button btnRemove = new Button(pnlButtonCriterias, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = tableCriterias.getSelectionIndex();
				if (i >= 0) {
					tableCriterias.remove(i);
				}
			}
		});
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove.setText("Remove");

		Button btnUp = new Button(pnlButtonCriterias, SWT.NONE);
		btnUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowUp(tableCriterias);
			}
		});
		btnUp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnUp.setText("Up");

		Button btnDown = new Button(pnlButtonCriterias, SWT.NONE);
		btnDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowDown(tableCriterias);
			}
		});
		btnDown.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1));
		btnDown.setText("Down");

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
		tblclmnName_1.setWidth(140);
		tblclmnName_1.setText("Name");

		TableColumn tblclmnLabel_1 = new TableColumn(tableResults, SWT.NONE);
		tblclmnLabel_1.setWidth(140);
		tblclmnLabel_1.setText("Label");

		Composite pnlButtonResults = new Composite(pnlResults, SWT.NONE);
		pnlButtonResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		pnlButtonResults.setLayout(new GridLayout(1, false));

		Button btnAdd_1 = new Button(pnlButtonResults, SWT.NONE);
		btnAdd_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		btnAdd_1.setText("Add");

		Button btnRemove_1 = new Button(pnlButtonResults, SWT.NONE);
		btnRemove_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = tableResults.getSelectionIndex();
				if (i >= 0) {
					tableResults.remove(i);
				}
			}
		});
		btnRemove_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnRemove_1.setText("Remove");

		Button btnUp_1 = new Button(pnlButtonResults, SWT.NONE);
		btnUp_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowUp(tableResults);
			}
		});
		btnUp_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1));
		btnUp_1.setText("Up");

		Button btnDown_1 = new Button(pnlButtonResults, SWT.NONE);
		btnDown_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Utils.moveRowDown(tableResults);
			}
		});
		btnDown_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		btnDown_1.setText("Down");
	}

	public List<RowItem> getSearchCriterias() {
		List<RowItem> rowItems = new ArrayList<>();
		for (int i = 0; i < tableCriterias.getItemCount(); ++i) {
			rowItems.add(RowItem.createFromTableItem(tableCriterias.getItem(i)));
		}
		return rowItems;
	}

	public List<RowItem> getSearchResult() {
		List<RowItem> rowItems = new ArrayList<>();
		for (int i = 0; i < tableResults.getItemCount(); ++i) {
			rowItems.add(RowItem.createFromTableItem(tableResults.getItem(i)));
		}
		return rowItems;
	}

	public void initSearchCriterias() {
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
				name = "search" + name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				String label = Utils.camelToHuman(name);
				String type = Signature.toString(fields[i].getTypeSignature());
				rowItems.add(new RowItem(name, label, type, null));
			}
			tableCriterias.setItemCount(rowItems.size());
			for (int i = 0; i < rowItems.size(); ++i) {
				rowItems.get(i).assignToTableItem(tableCriterias.getItem(i));
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}

	public void initSearchResults() {
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
				rowItems.add(new RowItem(fields[i]));
			}
			tableResults.setItemCount(rowItems.size());
			for (int i = 0; i < rowItems.size(); ++i) {
				rowItems.get(i).assignToTableItem(tableResults.getItem(i));
			}
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}

	private void openNewDialog() {
		Dialog dialog = new NewSearchCriteriaDialog(getShell());
		dialog.open();
	}
}
