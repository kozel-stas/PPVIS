package com.PPVIS;

import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ButtonEight extends Canvas {
    private ButtonEight buttonEight = this;
    private String text = "";
    private Color backgroundColor;
    private Color backgroundColorClicked;
    private Color backgroundColorMouseOn;
    private int radiusLittleCircle;
    private int radiusBigCircle;

    public ButtonEight(Composite parent, int style) {
        super(parent, style);
        radiusLittleCircle = 20;
        radiusBigCircle = 30;
        setDefaultLayoutData();
        setDefaultColor();
        setDefaultRegionAndColor();
       // initListener();
    }

    public ButtonEight(Composite parent, int style, int radiusLittleCircle, int radiusBigCircle) {
        super(parent, style);
        this.radiusLittleCircle = radiusLittleCircle;
        this.radiusBigCircle = radiusBigCircle;
        setDefaultLayoutData();
        setDefaultColor();
        setDefaultRegionAndColor();
        initListener();
    }

    private void initListener() {
        this.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                final int lenChar = 10;
                int offSetX = 3;
                int offSetY = radiusLittleCircle + radiusBigCircle - 3;
                int offSetXText = (radiusBigCircle * 2 - text.length() * lenChar) / 2;
                offSetXText=offSetXText>0?offSetXText:0;
                e.gc.drawString(text, offSetX + offSetXText, offSetY, true);
            }
        });
        this.addMouseTrackListener(new MouseTrackAdapter() {
            public void mouseEnter(MouseEvent e) {
                buttonEight.setBackground(backgroundColorMouseOn);
                redraw();
            }

            public void mouseExit(MouseEvent e) {
                buttonEight.setBackground(backgroundColor);
                redraw();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                buttonEight.setBackground(backgroundColorClicked);
                redraw();
            }

            public void mouseUp(MouseEvent e) {
                buttonEight.setBackground(backgroundColorMouseOn);
                redraw();
            }

        });

    }

    private void setDefaultRegionAndColor() {
        final int offSetX = 32;
        final int offSetYLittle = 22;
        final int offSetYBig = offSetYLittle + radiusLittleCircle + radiusBigCircle - 12;
        Point point = new Point(this.getBounds().x, this.getBounds().y);
        Region region = new Region();
        region.add(polygonCircle(radiusLittleCircle, point.x + offSetX, point.y + offSetYLittle));
        region.add(polygonCircle(radiusBigCircle, point.x + offSetX, point.y + offSetYBig));
        this.setRegion(region);
        this.setBackground(backgroundColor);
    }

    private void setDefaultLayoutData() {
        RowData rowData = new RowData();
        rowData.width = (radiusBigCircle + 2) * 2;
        rowData.height = (radiusLittleCircle + radiusBigCircle) * 2;
        this.setLayoutData(rowData);
    }

    private void setDefaultColor() {
        backgroundColor = new Color(null, 255, 0, 0);
        backgroundColorMouseOn = new Color(null, 245, 0, 0);
        backgroundColorClicked = new Color(null, 200, 0, 0);
    }

    private int[] polygonCircle(int radius, int offsetX, int offsetY) {
        final int accuracy = 6;
        int[] polygon = new int[4 * accuracy * radius];
        for (int i = 0; i < accuracy * radius + 1; i++) {
            int x = i - radius;
            int y = (int) Math.sqrt(radius * radius - x * x);
            polygon[2 * i] = offsetX + x;
            polygon[2 * i + 1] = offsetY + y;
            polygon[4 * accuracy * radius - 2 * i - 2] = offsetX + x;
            polygon[4 * accuracy * radius - 2 * i - 1] = offsetY - y;
        }
        return polygon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text != null) this.text = text;
        redraw();
    }

}
