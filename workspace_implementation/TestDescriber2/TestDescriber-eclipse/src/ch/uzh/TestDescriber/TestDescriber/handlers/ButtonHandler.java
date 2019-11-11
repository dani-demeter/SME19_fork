package ch.uzh.TestDescriber.TestDescriber.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class ButtonHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"TestDescriber",
				"TestDescriber plugin installed and activated.");
		
		// Open TestDescriber explorer view
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("ch.uzh.TestDescriber.TestDescriber.views.ListViewPart");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
