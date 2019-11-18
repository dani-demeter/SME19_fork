package ch.uzh.TestDescriber.TestDescriber.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;

import ch.uzh.TestDescriber.TestDescriber.views.ListViewPart.TreeParent;
import ch.uzh.TestDescriber.TestDescriber.views.ListViewPart.ViewContentProvider;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class TestsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "ch.uzh.TestDescriber.TestDescriber.views.TestsView";

	@Inject IWorkbench workbench;
	
//	private TableViewer viewer;
	private Action action1;
	private Action doubleClickAction;
	private String testPath;
	RowLayout rowLayout;
	Composite viewParent;
	List<Widget> widgets;
	 

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
//		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
//		
//		viewer.setContentProvider(ArrayContentProvider.getInstance());
//		viewer.setInput(new String[] { "One", "Two", "Three" });
//		viewer.setLabelProvider(new ViewLabelProvider());
//
//		// Create the help context id for the viewer's control
//		workbench.getHelpSystem().setHelp(viewer.getControl(), "ch.uzh.TestDescriber.TestDescriber.viewer");
//		getSite().setSelectionProvider(viewer);
		widgets = new ArrayList<Widget>();
		
		viewParent = parent;
		
        RowLayout rowLayout = new RowLayout();
        viewParent.setLayout(rowLayout);
        
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
	}
	
	public void setTestPath(String path) {
		testPath = path;
		initialize();
	}
	
	private void initialize() {
		// Delete old widgets
		for (Widget widget : widgets) {
			widget.dispose();
		}
		widgets = new ArrayList<Widget>();
		
		// Create new layout
        RowLayout rowLayout = new RowLayout();
        viewParent.setLayout(rowLayout);
		
        // Open given file path
        File testFile = new File(testPath);
        String labelText = "";
        if (testFile.exists() && testFile.isFile()) {
    		try {
    			List<String> allLines = Files.readAllLines(Paths.get(testFile.getAbsolutePath()));
    			int index = 0;
    			for (String line:allLines) {
    				if (line.contains("@Test" )) {
    					int indexSearch = index - 1;
    					boolean isClass = false;
    					while (indexSearch > index - 3) {
    						if (allLines.get(indexSearch).contains("public class")) {
    							isClass = true;
    							break;
    						}
        					indexSearch--;
    					}
    					
    					
    					if (isClass) {
    						// Handle preceding class comment
    						String classComment = "";
        					indexSearch = index - 1;
        					int indexEnd = 0;
        					int indexStart = 0;
        					while (indexSearch > index - 10) {
        						if (allLines.get(indexSearch).contains("*/")) {
        							indexEnd = indexSearch;
        						} else if (allLines.get(indexSearch).contains("/*")) {
        							indexStart = indexSearch;
        							break;
        						}
            					indexSearch--;
        					}
        					if(indexStart != 0 && indexEnd != 0) {
	        					for (int i = indexStart; i < indexEnd; i++) {
	        						String comment = allLines.get(i).replace("\t", "").replace("/*", "").replace("*/", "").replace("*", "").replace("  ", "");
	        						if (!comment.isEmpty()) {
	        							classComment += comment + "\n";
	        							labelText += comment + "\n";
	        						}
	        					}
	        					labelText += "\n";
        					}
    						
    					} else {
        					// Handle function name
        					String functionName = "";
        					indexSearch = index - 1;
        					while (indexSearch < index + 2) {
        						if (allLines.get(indexSearch).contains("public")) {
        							String pattern = "public [^\\s]+ ([^\\s]+) *\\(";
        							Pattern r = Pattern.compile(pattern);
        							Matcher m = r.matcher(allLines.get(indexSearch));
        							if (m.find()) {
        								functionName = m.group(1);
        							}
        							labelText += "----- " + functionName + " -----\n";
        							break;
        						}
            					indexSearch++;
        					}
    						
    						// Handle preceding function comment
    						String functionComment = "";
        					indexSearch = index - 1;
        					int indexEnd = 0;
        					int indexStart = 0;
        					while (indexSearch > index - 10) {
        						if (allLines.get(indexSearch).contains("*/")) {
        							indexEnd = indexSearch;
        						} else if (allLines.get(indexSearch).contains("/*")) {
        							indexStart = indexSearch;
        							break;
        						}
            					indexSearch--;
        					}
        					if(indexStart != 0 && indexEnd != 0) {
	        					for (int i = indexStart; i < indexEnd; i++) {
	        						String comment = allLines.get(i).replace("\t", "").replace("/*", "").replace("*/", "").replace("*", "").replace("  ", "").replace("OVERVIEW: ", "");
	        						if (!comment.isEmpty()) {
	        							functionComment += comment + "\n";
	        							labelText += comment + "\n";
	        						}
	        					}
	        					labelText += "\n";
        					}

        					// Handle following function comment
        					indexStart = 0;
        					indexSearch = index;
        					indexEnd = 0;
        					while (indexSearch < index + 10) {
        						if (indexStart != 0 && !allLines.get(indexSearch).contains("//")) {
        							indexEnd = indexSearch;
        							break;
        						} else if (indexStart == 0 && allLines.get(indexSearch).contains("//")) {
        							indexStart = indexSearch;
        						}
            					indexSearch++;
        					}
        					if(indexStart != 0 && indexEnd != 0) {
	        					for (int i = indexStart; i < indexEnd; i++) {
	        						String comment = allLines.get(i).replace("\t", "").replace("//", "").replace("  ", "");
	        						if (!comment.isEmpty()) {
	        							functionComment += comment + "\n";
	        							labelText += comment + "\n";
	        						}
	        					}
	        					labelText += "\n";
        					}
    					}
    				}
    				index++;
    			}
    		} catch (IOException e) {
            	Label label = new Label(viewParent, SWT.WRAP);
            	label.setText("Could not read given file.");
            	widgets.add(label);
    			e.printStackTrace();
    		}
            Label label = new Label(viewParent, SWT.WRAP);
            label.setText(labelText);
        	widgets.add(label);
        	
            Button button = new Button(viewParent, SWT.WRAP);
            button.setText("View test file");
        	widgets.add(button);

//            button.setLayoutData(new RowData(100, 40));
        } else {
        	Label label = new Label(viewParent, SWT.WRAP);
        	label.setText("Could not find given file.");
        	widgets.add(label);
        }
        
        // Refresh view with new layout
        viewParent.pack();
        viewParent.layout(true);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TestsView.this.fillContextMenu(manager);
			}
		});
//		Menu menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				initialize();
			}
		};
		action1.setText("Refresh");
		action1.setToolTipText("Refresh test");
		try {
			URL url = new URL("platform:/plugin/org.eclipse.ui/icons/full/elcl16/refresh_nav.png");
			ImageDescriptor image = ImageDescriptor.createFromURL(url);
			action1.setImageDescriptor(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		doubleClickAction = new Action() {
			public void run() {
//				IStructuredSelection selection = viewer.getStructuredSelection();
//				Object obj = selection.getFirstElement();
//				showMessage("Double-click detected on "+ obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
//		viewer.addDoubleClickListener(new IDoubleClickListener() {
//			public void doubleClick(DoubleClickEvent event) {
//				doubleClickAction.run();
//			}
//		});
	}
	
	private void showMessage(String message) {
//		MessageDialog.openInformation(
//			viewer.getControl().getShell(),
//			"TestDescriber Test",
//			message);
	}

	@Override
	public void setFocus() {
//		viewer.getControl().setFocus();
	}
}
