package org.eclipse.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class GraphicComponent extends Canvas {
    private List<com.PIVS.model.Point> pointList;
    private Color colorLine = new Color(null, 10, 10, 235);
    private Color colorPoint = new Color(null, 235, 10, 10);
    private Color colorAxis = new Color(null, 0, 0, 0);
    private Point bias = new Point(0, 0);
    private Rectangle rectangle = new Rectangle(0, 0, 1000, 1000);
    private ScrollBar scrollBarH;
    private ScrollBar scrollBarV;
    private double scale = 1;
    private boolean ctrlIsPress = false;

    public GraphicComponent(Composite composite) {
        super(composite, SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
        pointList = new ArrayList<>();
        initListeners();
        setBackground(new Color(null, 255, 255, 255));
        initPaintListener();
    }

    private void initListeners() {
        this.addMouseMoveListener(new MouseMoveListener() {
            int startX = -1;
            int startY = -1;

            @Override
            public void mouseMove(MouseEvent mouseEvent) {
                if (mouseEvent.stateMask == 524288) {
                    if (startY != -1 && startX != -1) {
                        int pageH = rectangle.height;
                        int pageW = rectangle.width;
                        int areaH = getClientArea().height;
                        int areaW = getClientArea().width;
                        if (pageH > areaH + bias.y + mouseEvent.y && pageW > areaW + bias.x + mouseEvent.x) {
//                            System.out.println("000000");
                        }
                    } else {
                        startX = mouseEvent.x;
                        startY = mouseEvent.y;
                    }
                } else {
                    if (startY != -1 && startY != -1)
                        startX = -1;
                    startY = -1;
                }
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseScrolled(MouseEvent mouseEvent) {
                if (ctrlIsPress) {
                    if (mouseEvent.count == 3) {
                        scale += 0.1;
                        while (pointList.size() != 0 && updateSize()) ;
                    } else if (scale - 0.1 > 0.2) {
                        scale -= 0.1;
                        while (pointList.size() != 0 && updateSize()) ;
                    }
                    redraw();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.keyCode == 262144)
                    ctrlIsPress = true;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.keyCode == 262144)
                    ctrlIsPress = false;
            }
        });

        scrollBarH = getHorizontalBar();

        scrollBarH.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event e) {
                int hSelection = scrollBarH.getSelection();
                int destX = -hSelection - bias.x;
                scroll(destX, 0, 0, 0, rectangle.width, rectangle.height, false);
                bias.x = -hSelection;
                redraw();
            }
        });

        scrollBarV = getVerticalBar();

        scrollBarV.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event e) {
                int vSelection = scrollBarV.getSelection();
                int destY = -vSelection - bias.y;
                scroll(0, destY, 0, 0, rectangle.width, rectangle.height, false);
                bias.y = -vSelection;
                redraw();
            }
        });

        addListener(SWT.Resize, new Listener() {
            public void handleEvent(Event e) {
                rectangle = new Rectangle(0, 0, getClientArea().width, getClientArea().height);
                resizeEvent();
                redraw();
            }
        });
    }

    private void resizeEvent() {
        Rectangle client = getClientArea();
        scrollBarH.setMaximum(rectangle.width);
        scrollBarV.setMaximum(rectangle.height);
        scrollBarH.setThumb(Math.min(rectangle.width, client.width));
        scrollBarV.setThumb(Math.min(rectangle.height, client.height));
        int hPage = rectangle.width - client.width;
        int vPage = rectangle.height - client.height;
        int hSelection = scrollBarH.getSelection();
        int vSelection = scrollBarV.getSelection();
        if (hSelection >= hPage) {
            if (hPage <= 0)
                hSelection = 0;
            bias.x = -hSelection;
        }
        if (vSelection >= vPage) {
            if (vPage <= 0)
                vSelection = 0;
            bias.y = -vSelection;
        }
    }

    public void addData(com.PIVS.model.Point point) {
        pointList.add(point);
        updateSize();
        redraw();
    }

    private boolean updateSize() {
        boolean isUpdateSize = false;
        com.PIVS.model.Point point = pointList.get(pointList.size() - 1);
        int x = rectangle.width - (int) (point.getNumberElement() * 100 * scale + 50 + 200 * scale);
        int y = rectangle.height - (int) ((point.getTime() * 100 * scale) + 50 + 200 * scale);
        if (x < 0) {
            rectangle.width += (int) (300 * scale);
            isUpdateSize = true;
        } else if ((x + (300 * scale)) > (500 * scale) && rectangle.width != getClientArea().width) {
            rectangle.width -= (int) (250 * scale);
            if (rectangle.width < getClientArea().width)
                rectangle.width = getClientArea().width;
            isUpdateSize = true;
        }
        if (y < 0) {
            rectangle.height += (int) (300 * scale);
            isUpdateSize = true;
        } else if ((y + (300 * scale)) > (500 * scale) && rectangle.height != getClientArea().height) {
            rectangle.height -= (int) (250 * scale);
            if (rectangle.height < getClientArea().height) {
                rectangle.height = getClientArea().height;
            }
            isUpdateSize = true;
        }
        resizeEvent();
        return isUpdateSize;
    }

    public void removeAll() {
        pointList.clear();
        rectangle.height = getClientArea().height;
        rectangle.width = getClientArea().width;
        resizeEvent();
        redraw();
    }

    private void initPaintListener() {
        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent paintEvent) {
                GC gc = paintEvent.gc;

                gc.setForeground(colorAxis);
                gc.setLineWidth(3);
                gc.drawLine(50 + bias.x, rectangle.height - 50 + bias.y, rectangle.width, rectangle.height - 50 + bias.y);
                gc.drawLine(50 + bias.x, rectangle.height - 50 + bias.y, 50 + bias.x, 0);

                gc.drawText("0", 40 + bias.x, rectangle.height - 40 + bias.y);

                for (int i = 0; i < (rectangle.width - 50) / (scale * 100); i++) {
                    gc.drawLine(50 + (int) (scale * 100 * (i + 1)) + bias.x, rectangle.height - 50 + bias.y, 50 + (int) (scale * 100 * (i + 1)) + bias.x, rectangle.height - 40 + bias.y);
                    gc.drawText(String.valueOf(i + 1), 50 + (int) (scale * 100 * (i + 1)) + bias.x, rectangle.height - 30 + bias.y, true);
                }

                for (int i = 0; i < (rectangle.height - 50) / (scale * 100); i++) {
                    gc.drawLine(50 + bias.x, rectangle.height - 50 + bias.y - (int) (scale * 100 * (i + 1)), 40 + bias.x, rectangle.height - 50 + bias.y - (int) (scale * 100 * (i + 1)));
                    gc.drawText(String.valueOf(i + 1), 20 + bias.x, rectangle.height - 50 + bias.y - (int) (scale * 100 * (i + 1)), true);
                }

                gc.setForeground(colorLine);
                gc.setBackground(colorPoint);

                int prevX = 50 + bias.x;
                int prevY = rectangle.height - 50 + bias.y;

                for (com.PIVS.model.Point point : pointList) {
                    int x = (int) (point.getNumberElement() * 100 * scale) + bias.x + 50;
                    int y = rectangle.height - (int) (point.getTime() * 100 * scale) - 50 + bias.y;
                    gc.fillOval(x - 5, y - 5, 10, 10);
                    gc.drawLine(prevX, prevY, x, y);
                    prevX = x;
                    prevY = y;
                }
            }
        });
        redraw();
    }
}
