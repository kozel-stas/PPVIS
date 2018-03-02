package com.PPVIS;

import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Button;

public class ButtonShape {

    private static int[] circle(int r, int offsetX, int offsetY) {
        int[] polygon = new int[8 * r + 4];
        // x^2 + y^2 = r^2
        for (int i = 0; i < 2 * r + 1; i++) {
            int x = i - r;
            int y = (int)Math.sqrt(r*r - x*x);
            polygon[2*i] = offsetX + x;
            polygon[2*i+1] = offsetY + y;
            polygon[8*r - 2*i - 2] = offsetX + x;
            polygon[8*r - 2*i - 1] = offsetY - y;
        }
        return polygon;
    }

    public static void setRegion(Button button){
        Region region = new Region();
        region.add(circle(20, button.getBounds().x+50, button.getBounds().y+50));
        region.subtract(circle(10,button.getBounds().x+50,button.getBounds().y+50));
        region.add(circle(30, button.getBounds().x+50, button.getBounds().y+50+38));
        region.subtract(circle(20,button.getBounds().x+50,button.getBounds().y+50+38));
        button.setRegion(region);
    }

}
