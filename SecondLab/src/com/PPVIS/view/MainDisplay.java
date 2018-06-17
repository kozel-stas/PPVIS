package com.PPVIS.view;


import com.PPVIS.Conroller.Controller;
import com.PPVIS.Conroller.strategy.FindAverageStrategy;
import com.PPVIS.Conroller.strategy.FindGroupStrategy;
import com.PPVIS.Conroller.strategy.FindMarkStrategy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.eclipse.widgets.FindCompositeAverage;
import org.eclipse.widgets.FindCompositeGroup;
import org.eclipse.widgets.FindCompositeMark;
import org.eclipse.widgets.TableComposite;

import java.io.File;
import java.io.IOException;

public class MainDisplay {
    private static Display display = new Display();
    private Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
    private File file;
    private Controller controller;
    private TableComposite tableComposite;

    public MainDisplay() {
        shell.setBackground(new Color(null, 222, 204, 204));
        shell.setText("TableEditor");
        shell.setSize(1505, 950);
        shell.setModified(false);
        centerWindow();
        initMenuBar();
        controller = new Controller();
        tableComposite = new TableComposite(shell, SWT.NULL);
        tableComposite.setBounds(8, 40);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void centerWindow() {
        Rectangle rectangle = shell.getDisplay().getBounds();
        Point p = shell.getSize();
        int nLeft = (rectangle.width - p.x) / 2;
        int nTop = (rectangle.height - p.y) / 2;
        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

    private void initMenuBar() {
        Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("File");

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);

        MenuItem fileExitItem, fileSaveItem, fileOpenItem;
        fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
        fileSaveItem.setText("Save");
        fileSaveItem.setAccelerator(SWT.CTRL + 'S');
        fileSaveItem.addSelectionListener(new SelectionAdapter() {
            MessageBox messageBox = new MessageBox(shell);

            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                File file;
                if (MainDisplay.this.file == null) {
                    try {
                        file = new File(openFileDialog("Save"));
                    } catch (NullPointerException ex) {
                        return;
                    }
                    try {
                        file.createNewFile();
                        MainDisplay.this.file = file;
                    } catch (IOException e) {
                        messageBox.setMessage("Ошибка при сохранении");
                        messageBox.open();
                        return;
                    }
                }
                if (!controller.save(MainDisplay.this.file)) {
                    messageBox.setMessage("Ошибка при сохранении");
                    messageBox.open();
                } else {
                    messageBox.setMessage("Сохранено");
                    messageBox.open();
                }
            }
        });

        fileOpenItem = new MenuItem(fileMenu, SWT.PUSH);
        fileOpenItem.setText("Open");
        fileOpenItem.setAccelerator(SWT.CTRL + 'O');
        fileOpenItem.addSelectionListener(new SelectionAdapter() {
            MessageBox messageBox = new MessageBox(shell);

            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                File file;
                try {
                    file = new File(openFileDialog("Open"));
                } catch (NullPointerException ex) {
                    return;
                }
                if (!controller.open(file)) {
                    messageBox.setMessage("Ошибка при открытии");
                    messageBox.open();
                } else redraw();
            }
        });

        fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
        fileExitItem.setText("Exit");
        fileExitItem.setAccelerator(SWT.CTRL + 'E');
        fileExitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                shell.close();
            }
        });

        Menu functionMenu;
        MenuItem functionMenuHeader, findMenuItem, addMenuItem, delMenuItem;

        functionMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        functionMenuHeader.setText("Function");

        functionMenu = new Menu(shell, SWT.DROP_DOWN);
        functionMenuHeader.setMenu(functionMenu);

        addMenuItem = new MenuItem(functionMenu, SWT.PUSH);
        addMenuItem.setText("Add");
        addMenuItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                AddDisplay addDisplay = new AddDisplay(display, MainDisplay.this, controller);
            }
        });

        findMenuItem = new MenuItem(functionMenu, SWT.CASCADE);
        findMenuItem.setText("Find");

        Menu findMenu = new Menu(shell, SWT.DROP_DOWN);
        findMenuItem.setMenu(findMenu);

        MenuItem findAverage = new MenuItem(findMenu, SWT.PUSH);
        findAverage.setText("Find surname and average");
        findAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeAverage(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindAverageStrategy());
            }
        });

        MenuItem findGroup = new MenuItem(findMenu, SWT.PUSH);
        findGroup.setText("Find surname and group");
        findGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeGroup(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindGroupStrategy());
            }
        });

        MenuItem findMark = new MenuItem(findMenu, SWT.PUSH);
        findMark.setText("Find surname and mark");
        findMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeMark(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindMarkStrategy());
            }
        });

        delMenuItem = new MenuItem(functionMenu, SWT.CASCADE);
        delMenuItem.setText("Delete");

        Menu deleteMenu = new Menu(shell, SWT.DROP_DOWN);
        delMenuItem.setMenu(deleteMenu);

        MenuItem deleteAverage = new MenuItem(deleteMenu, SWT.PUSH);
        deleteAverage.setText("Delete surname and average");
        deleteAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeAverage(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindAverageStrategy(),MainDisplay.this);
            }
        });

        MenuItem deleteGroup = new MenuItem(deleteMenu, SWT.PUSH);
        deleteGroup.setText("Delete surname and group");
        deleteGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeGroup(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindGroupStrategy(),MainDisplay.this);
            }
        });

        MenuItem deleteMark = new MenuItem(deleteMenu, SWT.PUSH);
        deleteMark.setText("Delete surname and mark");
        deleteMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeMark(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindMarkStrategy(),MainDisplay.this);
            }
        });

        Menu helpMenu;
        MenuItem helpMenuHeader, helpGetHelpItem;

        helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("Help");

        helpMenu = new Menu(shell, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
        helpGetHelpItem.setText("Get Help");

        shell.setMenuBar(menuBar);
///////////////////////////////////////////////////////////////////////////////////////////////menu
        ToolBar toolBar = new ToolBar(shell, SWT.BORDER);

        ToolItem addItem = new ToolItem(toolBar, SWT.PUSH);
        addItem.setText("Add");
        addItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new AddDisplay(display, MainDisplay.this, controller);
            }
        });

        ToolItem separator1 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem findItemAverage = new ToolItem(toolBar, SWT.PUSH);
        findItemAverage.setText("Find surname and average");
        findItemAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeAverage(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindAverageStrategy());
            }
        });

        ToolItem separator5 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem findItemGroup = new ToolItem(toolBar, SWT.PUSH);
        findItemGroup.setText("Find surname and group");
        findItemGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeGroup(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindGroupStrategy());
            }
        });

        ToolItem separator6 = new ToolItem(toolBar, SWT.SEPARATOR);


        ToolItem findItemMark = new ToolItem(toolBar, SWT.PUSH);
        findItemMark.setText("Find surname and mark");
        findItemMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new FindDisplay(shell, controller, new FindCompositeMark(shell, SWT.NULL), new Button(shell, SWT.PUSH), new TableComposite(shell, SWT.NULL, 10), new FindMarkStrategy());
            }
        });

        ToolItem separator2 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemAverage = new ToolItem(toolBar, SWT.PUSH);
        deleteItemAverage.setText("Delete surname and average");
        deleteItemAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeAverage(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindAverageStrategy(),MainDisplay.this);
            }
        });

        ToolItem separator3 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemGroup = new ToolItem(toolBar, SWT.PUSH);
        deleteItemGroup.setText("Delete surname and group");
        deleteItemGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeGroup(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindGroupStrategy(),MainDisplay.this);
            }
        });

        ToolItem separator4 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemMark = new ToolItem(toolBar, SWT.PUSH);
        deleteItemMark.setText("Delete surname and mark");
        deleteItemMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Shell shell = new Shell(display);
                new DeleteDisplay(shell, controller, new FindCompositeMark(shell, SWT.NULL), new Button(shell, SWT.PUSH), new FindMarkStrategy(),MainDisplay.this);
            }
        });

        toolBar.pack();
///////////////////////////////////////////////////////////////////////////////////////toolbar
    }

    public void redraw() {
        tableComposite.redraw(controller.getStudents());
    }

    private String openFileDialog(String type) {
        FileDialog fileDialog = new FileDialog(shell, "Save".equals(type) ? SWT.SAVE : SWT.OPEN);
        fileDialog.setText(type);
        fileDialog.setFilterPath("C:/");
        String[] filterExst = new String[1];
        filterExst[0] = "*.xml";
        fileDialog.setFilterExtensions(filterExst);
        return fileDialog.open();
    }
}
