package com.ganesha.plugin.basicweb.wizards.newmodule.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewSearchCriteriaDialog extends Dialog {
	private Text txtLabel;
	private Text txtType;
	private Text txtName;

	public NewSearchCriteriaDialog(Shell parentShell) {
		super(parentShell);
	}

	public String getValueLabel() {
		return txtLabel.getText();
	}

	public String getValueName() {
		return txtName.getText();
	}

	public String getValueType() {
		return txtType.getText();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, 0, IDialogConstants.OK_LABEL, true);
		createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
		txtName.setFocus();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) composite.getLayout();
		gridLayout.numColumns = 3;

		Label lblName = new Label(composite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblName.setText("Name");

		txtName = new Text(composite, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse...");

		Label lblLabel = new Label(composite, SWT.NONE);
		lblLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblLabel.setText("Label");

		txtLabel = new Text(composite, SWT.BORDER);
		txtLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		new Label(composite, SWT.NONE);

		Label lblType = new Label(composite, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblType.setText("Type");

		txtType = new Text(composite, SWT.BORDER);
		txtType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		new Label(composite, SWT.NONE);

		return composite;
	}
}