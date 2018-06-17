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
    public static final int CTRL_KEY_MASK = 262144;
    public static final int WHEEL_COUNT = 3;
    public static final int STEP_GRID = 100;
    public static final double STEP_SCALE = 0.1;
    public static final double MIN_SCALE = 0.2;
    public static final int OFFSET_START_GRAPHIC = 50;
    public static final int DIAMETER_POINT = 10;
    public static final int LINE_WIDTH = 3;
    public static final int STEP_INCREMENT_RESIZE = 300;
    public static final int STEP_REDUCTION_RESIZE = 201;
    private List<GraphicPoint> pointList;
    private Color colorLine = new Color(null, 10, 10, 235);
    private Color colorPoint = new Color(null, 235, 10, 10);
    private Color colorAxis = new Color(null, 0, 0, 0);
    private Point bias = new Point(0, 0);
    private Rectangle rectangle = new Rectangle(0, 0, 1000, 1000);
    private ScrollBar scrollBarH;
    private ScrollBar scrollBarV;
    private double scale = 1;
    private boolean ctrlIsPress = false;
    private GraphicPoint pointMaxHeight;

    public GraphicComponent(Composite composite) {
        super(composite, SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
        pointList = new ArrayList<>();
        initListeners();
        setBackground(new Color(null, 255, 255, 255));
        initPaintListener();
    }

    private void initListeners() {
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseScrolled(MouseEvent mouseEvent) {
                if (ctrlIsPress) {
                    if (mouseEvent.count == WHEEL_COUNT) {
                        scale += STEP_SCALE;
                        while (updateSize()) ;
                    } else if (scale - STEP_SCALE > MIN_SCALE) {
                        scale -= STEP_SCALE;
                        while (updateSize()) ;
                    }
                    redraw();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.keyCode == CTRL_KEY_MASK)
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
            }
        });

        scrollBarV = getVerticalBar();

        scrollBarV.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event e) {
                int vSelection = scrollBarV.getSelection();
                int destY = -vSelection - bias.y;
                scroll(0, destY, 0, 0, rectangle.width, rectangle.height, false);
                bias.y = -vSelection;
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

    public void addData(GraphicPoint point) {
        if (pointList.size() == 0) {
            pointMaxHeight = point;
            pointList.add(point);
        } else {
            if (point.getTime() > pointMaxHeight.getTime()) {
                pointMaxHeight = point;
            }
            pointList.add(point);
        }
        updateGraphic();
        redraw();
    }

    private boolean updateSize() {
        boolean isUpdateSize = false;
        if (pointMaxHeight == null || pointList.size() == 0) return false;
        int x = rectangle.width - (int) (pointList.get(pointList.size() - 1).getNumberElement() * STEP_GRID * scale + OFFSET_START_GRAPHIC);
        int y = rectangle.height - (int) ((pointMaxHeight.getTime() * STEP_GRID * scale) + OFFSET_START_GRAPHIC);
        boolean condition_increment_size_width = x - 200 * scale < 0;
        if (condition_increment_size_width) {
            rectangle.width += (int) ((STEP_INCREMENT_RESIZE - x) * scale);
            isUpdateSize = true;
        } else {
            boolean condition_reduction_size_width = x > (500 * scale) && rectangle.width != getClientArea().width;
            if (condition_reduction_size_width) {
                rectangle.width -= (int) (x - STEP_REDUCTION_RESIZE * scale);
                if (rectangle.width < getClientArea().width)
                    rectangle.width = getClientArea().width;
                isUpdateSize = true;
            }
        }
        boolean condition_increment_size_height = y - 200 * scale < 0;
        if (condition_increment_size_height) {
            rectangle.height += (int) ((STEP_INCREMENT_RESIZE - y) * scale);
            isUpdateSize = true;
        } else {
            boolean condition_reduction_size_height = y > (500 * scale) && rectangle.height != getClientArea().height;
            if (condition_reduction_size_height) {
                rectangle.height -= (int) (y - STEP_REDUCTION_RESIZE * scale);
                if (rectangle.height < getClientArea().height) {
                    rectangle.height = getClientArea().height;
                }
                isUpdateSize = true;
            }
        }
        resizeEvent();
        return isUpdateSize;
    }

    private void updateGraphic() {
        int x = rectangle.width - (int) (pointList.get(pointList.size() - 1).getNumberElement() * STEP_GRID * scale + OFFSET_START_GRAPHIC);
        int y = rectangle.height - (int) ((pointMaxHeight.getTime() * STEP_GRID * scale) + OFFSET_START_GRAPHIC);
        boolean resizeScroll = x - 200 * scale < 0 || y - 200 * scale < 0;
        boolean changeScale = resizeScroll && scale - STEP_SCALE > MIN_SCALE;
        if (changeScale) {
            while (scale - STEP_SCALE > MIN_SCALE) {
                scale -= STEP_SCALE;
                x = rectangle.width - (int) (pointList.get(pointList.size() - 1).getNumberElement() * STEP_GRID * scale + OFFSET_START_GRAPHIC);
                y = rectangle.height - (int) ((pointMaxHeight.getTime() * STEP_GRID * scale) + OFFSET_START_GRAPHIC);
                resizeScroll = x - 200 * scale < 0 || y - 200 * scale < 0;
                if (!resizeScroll) return;
            }
        }
        updateSize();
    }

    public void removeAll() {
        pointList.clear();
        rectangle.height = getClientArea().height;
        rectangle.width = getClientArea().width;
        pointMaxHeight = null;
        bias = new Point(0, 0);
        resizeEvent();
        redraw();
    }

    private void initPaintListener() {
        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent paintEvent) {
                GC gc = paintEvent.gc;

                gc.setForeground(colorAxis);
                gc.setLineWidth(LINE_WIDTH);
                gc.drawLine(OFFSET_START_GRAPHIC + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y, rectangle.width, rectangle.height - OFFSET_START_GRAPHIC + bias.y);
                gc.drawLine(OFFSET_START_GRAPHIC + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y, OFFSET_START_GRAPHIC + bias.x, 0);

                gc.drawText("0", OFFSET_START_GRAPHIC - 10 + bias.x, rectangle.height - (OFFSET_START_GRAPHIC - 10) + bias.y);

                for (int i = 0; i < (rectangle.width - OFFSET_START_GRAPHIC) / (scale * STEP_GRID); i++) {
                    gc.drawLine(OFFSET_START_GRAPHIC + (int) (scale * STEP_GRID * (i + 1)) + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y, OFFSET_START_GRAPHIC + (int) (scale * STEP_GRID * (i + 1)) + bias.x, rectangle.height - (OFFSET_START_GRAPHIC - 10) + bias.y);
                    gc.drawText(String.valueOf(i + 1), OFFSET_START_GRAPHIC + (int) (scale * STEP_GRID * (i + 1)) + bias.x, rectangle.height - (i % 2 == 0 ? OFFSET_START_GRAPHIC - 15 : OFFSET_START_GRAPHIC - 30) + bias.y, true);
                }

                for (int i = 0; i < (rectangle.height - OFFSET_START_GRAPHIC) / (scale * STEP_GRID); i++) {
                    gc.drawLine(OFFSET_START_GRAPHIC + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y - (int) (scale * STEP_GRID * (i + 1)), OFFSET_START_GRAPHIC - 10 + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y - (int) (scale * STEP_GRID * (i + 1)));
                    gc.drawText(String.valueOf(i + 1), OFFSET_START_GRAPHIC - 30 + bias.x, rectangle.height - OFFSET_START_GRAPHIC + bias.y - (int) (scale * STEP_GRID * (i + 1)), true);
                }

                gc.setForeground(colorLine);
                gc.setBackground(colorPoint);

                int prevX = OFFSET_START_GRAPHIC + bias.x;
                int prevY = rectangle.height - OFFSET_START_GRAPHIC + bias.y;

                for (GraphicPoint point : pointList) {
                    int x = (int) (point.getNumberElement() * STEP_GRID * scale) + bias.x + OFFSET_START_GRAPHIC;
                    int y = rectangle.height - (int) (point.getTime() * STEP_GRID * scale) - OFFSET_START_GRAPHIC + bias.y;
                    gc.fillOval(x - DIAMETER_POINT / 2, y - DIAMETER_POINT / 2, DIAMETER_POINT, DIAMETER_POINT);
                    gc.drawLine(prevX, prevY, x, y);
                    prevX = x;
                    prevY = y;
                }
            }
        });
        redraw();
    }
}
