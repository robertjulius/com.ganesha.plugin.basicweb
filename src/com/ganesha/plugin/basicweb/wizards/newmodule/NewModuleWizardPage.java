package com.ganesha.plugin.basicweb.wizards.newmodule;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewModuleWizardPage extends WizardPage {

	public static final String NAME = NewModuleWizardPage.class.getName();
	private Text moduleName;

	public NewModuleWizardPage() {
		super(NAME);
		setTitle("New Module");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label lblModuleName = new Label(container, SWT.NULL);
		lblModuleName.setText("&Container:");

		moduleName = new Text(container, SWT.BORDER | SWT.SINGLE);
		moduleName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		moduleName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		validateInput();
		setControl(container);
	}

	public String getModuleName() {
		return moduleName.getText();
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void validateInput() {
		if (getModuleName().length() == 0) {
			updateStatus("Module Name must be specified");
			return;
		}

		updateStatus(null);
	}
}