package com.ganesha.plugin.basicweb.wizards.newproject;

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

public class PackageSettingPage extends WizardPage {

	public static final String NAME = PackageSettingPage.class.getName();

	private Label lblClient;
	private Label lblBasePackage;
	private Text txtClient;
	private Text txtBasePackage;

	public PackageSettingPage() {
		super(NAME);
		setTitle("Package Setting");
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
		lblClient = new Label(container, SWT.NULL);
		lblClient.setText("Client:");

		txtClient = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtClient.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtClient.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});
		lblBasePackage = new Label(container, SWT.NULL);
		lblBasePackage.setText("Base Package:");

		txtBasePackage = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtBasePackage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtBasePackage.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		initialPage();
		validateInput();
		setControl(container);
	}

	public String getBasePackage() {
		return txtBasePackage.getText();
	}

	public String getClient() {
		return txtClient.getText();
	}

	private void initialPage() {
		txtClient.setText("kingdavid");
		txtBasePackage.setText("website");
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void validateInput() {
		if (getClient().length() == 0) {
			updateStatus("Client must be filled");
			return;
		}

		if (getBasePackage().length() == 0) {
			updateStatus("Base Package must be filled");
			return;
		}

		updateStatus(null);
	}
}