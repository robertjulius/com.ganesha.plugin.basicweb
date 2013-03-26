package com.ganesha.plugin.basicweb.wizards.newmodule;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.internal.core.search.JavaWorkspaceScope;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

@SuppressWarnings("restriction")
public class NewModuleWizardPage extends WizardPage {

	public static final String NAME = NewModuleWizardPage.class.getName();
	private Text txtModuleName;
	private Text txtEntity;
	private SourceType entity;

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
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label lblModuleName = new Label(container, SWT.NULL);
		lblModuleName.setText("Module Name:");

		txtModuleName = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtModuleName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtModuleName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});

		validateInput();
		setControl(container);
		new Label(container, SWT.NONE);

		Label lblEntity = new Label(container, SWT.NONE);
		lblEntity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEntity.setText("Entity:");

		txtEntity = new Text(container, SWT.BORDER);
		txtEntity.setEditable(false);
		txtEntity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entity = openTypeDialog();
				txtEntity.setText(entity.getFullyQualifiedName());
			}
		});
		btnBrowse.setText("Bro&wse...");
		txtEntity.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInput();
			}
		});
	}

	public SourceType getEntity() {
		return entity;
	}

	public String getModuleName() {
		return txtModuleName.getText();
	}

	private SourceType openTypeDialog() {
		SourceType sourceType = null;

		Shell parent = getShell();
		IRunnableContext context = new ProgressMonitorDialog(getShell());

		IJavaSearchScope scope = new JavaWorkspaceScope();

		try {
			SelectionDialog dialog = JavaUI.createTypeDialog(parent, context,
					scope, IJavaElementSearchConstants.CONSIDER_CLASSES, false);

			dialog.open();
			sourceType = (SourceType) dialog.getResult()[0];

		} catch (JavaModelException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}

		return sourceType;
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

		if (getEntity() == null) {
			updateStatus("Entity must be specified");
			return;
		}

		updateStatus(null);
	}
}