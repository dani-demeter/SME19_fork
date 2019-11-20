package ch.uzh.TestDescriber.TestDescriber.views;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;

import ch.uzh.TestDescriber.TestDescriber.views.ListViewPart.TreeParent;
import ch.uzh.TestDescriber.TestDescriber.views.ListViewPart.ViewContentProvider;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
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
		
//        RowLayout rowLayout = new RowLayout();
//        viewParent.setLayout(rowLayout);
        
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
	}
	
	public void setTestPath(String path) {
		testPath = path;
		initialize();
	}
	
	private String getTestFunctionName(String line) {
		String pattern = "public [^\\s]+ ([^\\s]+) *\\(";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	private String getTestClassName(String line) {
		String pattern = "public class ([^\\s]+) *\\{";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	private String getTestFunctionComment(List<String> allLines, int index, int backward, int forward) {
		String comment = "";
		
		// Get preceding comment start and end
		int maxBackward = index - backward >= 0 ? index - backward : 0;
		int startBackward = -1;
		int endBackward = -1;
		for (int i = index - 1; i >= maxBackward; i--) {
			String line = allLines.get(i);
			if (line.contains("*/")) {
				endBackward = i;
			} else if (line.contains("/*")) {
				startBackward = i;
				break;
			} else if (line.contains("public class")) {
				break;
			}
		}
		
		// Get preceding comment content
		if(startBackward != -1 && endBackward != -1) {
			for (int i = startBackward; i < endBackward; i++) {
				String line = allLines.get(i).replace("\t", "").replace("/*", "").replace("*/", "").replace("*", "").replace("  ", "").replace("OVERVIEW: ", "");
				if (!line.isEmpty()) {
					comment += line + "\n";
				}
			}
		}
		
		// Get following comment
		int maxForward = index + forward < allLines.size() ? index + forward : allLines.size();
		boolean foundComment = false;
		for (int i = index + 1; i < maxForward; i++) {
			String line = allLines.get(i);
			if (line.contains("//")) {
				comment += line.replace("\t", "").replace("//", "").replace("  ", "") + "\n";
				foundComment = true;
			} else if (foundComment == true) {
				break;
			}
		}
		
		// Return comment if found
		if (!comment.isEmpty()) {
			return comment;
		}
		return null;
	}
	
	private String getTestClassComment(List<String> allLines, int index, int backward) {
		String comment = "";
		
		// Get preceding comment start and end
		int maxBackward = index - backward >= 0 ? index - backward : 0;
		int startBackward = -1;
		int endBackward = -1;
		for (int i = index - 1; i >= maxBackward; i--) {
			String line = allLines.get(i);
			if (line.contains("*/")) {
				endBackward = i;
			} else if (line.contains("/*")) {
				startBackward = i;
				break;
			} else if (line.contains("public class")) {
				break;
			}
		}
		
		// Get preceding comment content
		if(startBackward != -1 && endBackward != -1) {
			for (int i = startBackward; i < endBackward; i++) {
				String line = allLines.get(i).replace("\t", "").replace("/*", "").replace("*/", "").replace("*", "").replace("  ", "");
				if (!line.isEmpty()) {
					comment += line + "\n";
				}
			}
		}
		
		// Return comment if found
		if (!comment.isEmpty()) {
			return comment;
		}
		return null;
	}
	
	private void initialize() {
		// Delete old widgets
		for (Widget widget : widgets) {
			widget.dispose();
		}
		widgets = new ArrayList<Widget>();
		
		// Create new layout
		viewParent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewParent.setLayout(new GridLayout(1, false));
	    ScrolledComposite sc = new ScrolledComposite(viewParent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	    sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    sc.setBackground(viewParent.getBackground());
	    Composite composite = new Composite(sc, SWT.NONE);
	    composite.setLayout(new GridLayout(1, false));
	    composite.setBackground(sc.getBackground());
        widgets.add(sc);
        widgets.add(composite);
		
        // Open given file path
        File testFile = new File(testPath);
        if (testFile.exists() && testFile.isFile()) {
    		try {
    			// Get all lines in file
    			List<String> allLines = Files.readAllLines(Paths.get(testFile.getAbsolutePath()));
    			int index = 0;
    			for (String line:allLines) {
    				// Handle function comment
    				if (line.contains("@Test" )) {
        					// Get test function name
        					String testFunctionName = getTestFunctionName(allLines.get(index + 1));
        					if (testFunctionName == null) {
        						index++;
        						continue;
        					}
        					
        					// Create function name label
        		            Label headingLabel = new Label(composite, SWT.WRAP);
        		            headingLabel.setBackground(composite.getBackground());
        		            headingLabel.setText(testFunctionName);
        		            FontData[] fD = headingLabel.getFont().getFontData();
        		            fD[0].setHeight(14);
        		            headingLabel.setFont( new Font(null, fD[0]));
        		        	widgets.add(headingLabel);
    						
							// Get test function comment
							String testFunctionComment = getTestFunctionComment(allLines, index, 10, 10);
        					if (testFunctionComment != null) {
        						// Create function comment label
            		            Label commentLabel = new Label(composite, SWT.WRAP);
            		            commentLabel.setBackground(composite.getBackground());
            		            commentLabel.setText(testFunctionComment + "\n");
            		        	widgets.add(commentLabel);
        					}
        					
    				} else if (line.contains("public class")) {
    					// Get test class name
    					String testClassName = getTestClassName(allLines.get(index));
    					if (testClassName == null) {
    						index++;
    						continue;
    					}
    					
    					// Create class name label
    		            Label headingLabel = new Label(composite, SWT.WRAP);
    		            headingLabel.setBackground(composite.getBackground());
    		            headingLabel.setText(testClassName);
    		            FontData[] fD = headingLabel.getFont().getFontData();
    		            fD[0].setHeight(18);
    		            headingLabel.setFont( new Font(null, fD[0]));
    		        	widgets.add(headingLabel);
    					
    					// Get test class comment
						String testClassComment = getTestClassComment(allLines, index, 10);
    					if (testClassComment != null) {
    						// Create class comment label
        		            Label commentLabel = new Label(composite, SWT.WRAP);
        		            commentLabel.setBackground(composite.getBackground());
        		            commentLabel.setText(testClassComment + "\n");
        		        	widgets.add(commentLabel);
    					}
    				}
    				index++;
    			}
    		} catch (IOException e) {
            	Label label = new Label(composite, SWT.WRAP);
            	label.setBackground(composite.getBackground());
            	label.setText("Could not read given file.");
            	widgets.add(label);
    			e.printStackTrace();
    		}
        	
//            Button button = new Button(viewParent, SWT.WRAP);
//            button.setText("View test file");
//        	widgets.add(button);

//            button.setLayoutData(new RowData(100, 40));
        } else {
        	Label label = new Label(composite, SWT.WRAP);
        	label.setBackground(composite.getBackground());
        	label.setText("Could not find given file.");
        	widgets.add(label);
        }
        
        // Refresh view with new layout
	    sc.setContent(composite);
	    composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        viewParent.pack();
        viewParent.layout(true);
	    viewParent.setSize(viewParent.getParent().getSize());
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
