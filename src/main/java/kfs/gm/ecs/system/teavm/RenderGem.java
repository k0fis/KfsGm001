package kfs.gm.ecs.system.teavm;

import org.teavm.jso.canvas.*;

public class RenderGem {

    static void drawFancyDiamond(CanvasRenderingContext2D ctx, double x, double y, double w, double h, double angle) {
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(angle);

        drawFancyDiamond(ctx, 0, 0, w, h);

        ctx.restore();
    }

    static void drawFancyDiamond(CanvasRenderingContext2D ctx, double x, double y, double w, double h) {
        double hw = w / 2;
        double hh = h / 2;
        double topY = y - hh;
        double bottomY = y + hh;
        double midY = y;

        // body
        double leftX = x - hw;
        double rightX = x + hw;
        //double topMidY = y - hh * 0.5;

        // hlavní tvar
        ctx.beginPath();
        ctx.moveTo(x, topY);
        ctx.lineTo(rightX, midY);
        ctx.lineTo(x, bottomY);
        ctx.lineTo(leftX, midY);
        ctx.closePath();

        ctx.setFillStyle("rgba(0,255,255,0.3)");
        ctx.fill();
        ctx.setStrokeStyle("cyan");
        ctx.stroke();

        // vnitřní čáry – simulace faset
        ctx.beginPath();
        ctx.moveTo(x, topY);
        ctx.lineTo(x - hw * 0.5, midY);
        ctx.moveTo(x, topY);
        ctx.lineTo(x + hw * 0.5, midY);
        ctx.moveTo(leftX, midY);
        ctx.lineTo(x, bottomY);
        ctx.moveTo(rightX, midY);
        ctx.lineTo(x, bottomY);
        ctx.stroke();

        // horní trojúhelníky – fasety
        ctx.beginPath();
        ctx.moveTo(leftX, midY);
        ctx.lineTo(x, topY);
        ctx.lineTo(x - hw * 0.5, midY);
        ctx.moveTo(rightX, midY);
        ctx.lineTo(x, topY);
        ctx.lineTo(x + hw * 0.5, midY);
        ctx.setFillStyle("rgba(0,255,255,0.5)");
        ctx.fill();
    }

}
