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

public class DatabaseSettingPage extends WizardPage {

	public static final String NAME = DatabaseSettingPage.class.getName();

	private Label lblUrl;
	private Label lblUsername;
	private Label lblPassword;
	private Text txtUrl;
	private Text txtUserName;
	private Text txtPassword;

	public DatabaseSettingPage() {
		super(NAME);
		setTitle("Database Setting");
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
		lblUrl = new Label(container, SWT.NULL);
		lblUrl.setText("URL:");

		txtUrl = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtUrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtUrl.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});
		lblUsername = new Label(container, SWT.NULL);
		lblUsername.setText("User Name:");

		txtUserName = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtUserName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtUserName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		lblPassword = new Label(container, SWT.NONE);
		lblPassword.setText("Password:");

		txtPassword = new Text(container, SWT.BORDER);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		txtPassword.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		initForm();
		validateInput();
		setControl(container);
	}

	public String getPassword() {
		return txtPassword.getText();
	}

	public String getUrl() {
		return txtUrl.getText();
	}

	public String getUserName() {
		return txtUserName.getText();
	}

	private void initForm() {
		txtUrl.setText("jdbc:mysql://localhost:3306/iseng");
		txtUserName.setText("root");
		txtPassword.setText("root");
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void validateInput() {
		if (getPassword().length() == 0) {
			updateStatus("Password must be filled");
			return;
		}

		if (getUrl().length() == 0) {
			updateStatus("URL must be filled");
			return;
		}

		if (getUserName().length() == 0) {
			updateStatus("User Name must be filled");
			return;
		}

		updateStatus(null);
	}
}