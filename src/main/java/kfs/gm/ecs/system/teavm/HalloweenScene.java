package kfs.gm.ecs.system.teavm;

import org.teavm.jso.canvas.CanvasRenderingContext2D;

public class HalloweenScene {

    private CanvasRenderingContext2D g;
    private double stars[][];

    public HalloweenScene(CanvasRenderingContext2D g) {
        this.g = g;
        this.stars = null;
    }

    public void updateStars(int count, int width, int height) {
        stars = new double[count][];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new double[2];
            stars[i][0] = Math.random() * width;
            stars[i][1] = Math.random() * 0.9*height;
        }
    }

    public void drawScene(int width, int height) {
        // pozadí
        g.setFillStyle("#2a2a4a");
        g.fillRect(0, 0, width, height);

        // hvězdy
        if (stars == null || stars.length == 0) {
            updateStars(50, width, height);
        }
        g.setFillStyle("white");
        for (int i = 0; i < 50; i++) {
            g.fillRect(stars[i][0], stars[i][1], 2, 2);
        }

        // měsíc
        g.setFillStyle("rgba(255,255,200,0.3)");
        g.beginPath();
        g.arc(650, 80, 40, 0, Math.PI * 2);
        g.fill();

        drawChurchSilhouette(120, height, 1.5);
        drawGraveyard(200, height, 1.2);
        drawGraveyard(350, height, .7);
        drawGraveyard(450, height, 1.0);

    }

    // 🎃 Dýně
    public void drawPumpkin(double x, double y, double size) {
        g.save();
        g.translate(x, y);
        g.scale(size, size);

        // tělo (elipsa)
        g.save();
        g.scale(1.3, 1.0);
        g.setFillStyle("orange");
        g.beginPath();
        g.arc(0, 0, 40, 0, Math.PI * 2);
        g.fill();
        g.restore();

        // stonek
        g.setFillStyle("green");
        g.fillRect(-8, -55, 16, 10);

        // oči
        g.setFillStyle("black");
        drawTriangle( -25, -10, 15, 15, true);
        drawTriangle( 10, -10, 15, 15, true);

        // pusa
        g.beginPath();
        g.moveTo(-30, 15);
        g.lineTo(-15, 30);
        g.lineTo(0, 15);
        g.lineTo(15, 30);
        g.lineTo(30, 15);
        g.closePath();
        g.fill();

        g.restore();
    }

    // 👻 Duch
    public void drawGhost(double x, double y, double size) {
        g.save();
        g.translate(x, y);
        g.scale(size, size);

        g.setFillStyle("white");
        g.beginPath();
        g.arc(0, -10, 25, Math.PI, 0, false);
        g.lineTo(25, 30);
        g.lineTo(15, 25);
        g.lineTo(5, 30);
        g.lineTo(-5, 25);
        g.lineTo(-15, 30);
        g.lineTo(-25, 25);
        g.closePath();
        g.fill();

        g.setFillStyle("black");
        g.beginPath();
        g.arc(-10, -10, 3, 0, Math.PI * 2);
        g.arc(10, -10, 3, 0, Math.PI * 2);
        g.fill();

        g.restore();
    }

    // 🦇 Netopýr
    public void drawBat(double x, double y, double size, double time) {
        g.save();
        g.translate(x, y);
        g.scale(size, size);

        double flap = Math.sin(time * 4) * 0.7; // rozsah mávání křídel

        g.setFillStyle("black");
        g.beginPath();
        g.arc(0, 0, 10, Math.PI, 0, false); // hlava

        // levé křídlo
        g.save();
        g.rotate(-flap);
        g.arc(-20, 0, 15, 0, Math.PI, false);
        g.restore();

        // pravé křídlo
        g.save();
        g.rotate(flap);
        g.arc(20, 0, 15, 0, Math.PI, false);
        g.restore();

        g.closePath();
        g.fill();

        g.restore();
    }

    // 🕷️ Pavouk
    public void drawSpider(double x, double y, double size) {
        g.save();
        g.translate(x, y);
        g.scale(size, size);

        g.setFillStyle("black");
        g.beginPath();
        g.arc(0, 0, 10, 0, Math.PI * 2);
        g.fill();

        g.setStrokeStyle("black");
        for (int i = 0; i < 4; i++) {
            double yOffset = (i - 1.5) * 5;
            g.beginPath();
            g.moveTo(-10, yOffset);
            g.lineTo(-25, yOffset - 5);
            g.stroke();

            g.beginPath();
            g.moveTo(10, yOffset);
            g.lineTo(25, yOffset - 5);
            g.stroke();
        }

        g.restore();
    }

    // 🐱 Kočka
    void drawCatAnimated(double x, double y, double size, double time) {
        g.save();
        g.translate(x, y);
        g.scale(size, size);

        // tělo
        g.setFillStyle("black");
        g.beginPath();
        g.arc(0, 0, 20, 0, Math.PI * 2);
        g.fill();

        // --- ocas (houpe se) ---
        double tailAngle = Math.sin(time * 2) * 0.4; // ±0.4 rad
        g.save();
        g.translate(18, 10); // umístění ocasu
        g.rotate(tailAngle);
        g.setStrokeStyle("black");
        g.setLineWidth(4);
        g.beginPath();
        g.moveTo(0, 0);
        g.quadraticCurveTo(15, -10, 25, 5); // zakřivený ocas
        g.stroke();
        g.restore();

        // --- hlava ---
        g.setFillStyle("black");
        g.beginPath();
        g.arc(0, -25, 15, 0, Math.PI * 2);
        g.fill();

        // --- uši ---
        g.beginPath();
        g.moveTo(-10, -35);
        g.lineTo(-3, -50);
        g.lineTo(3, -35);
        g.moveTo(10, -35);
        g.lineTo(3, -50);
        g.lineTo(-3, -35);
        g.fill();

        // --- oči (mrkání + záře) ---
        double blink = Math.abs(Math.sin(time * 3)); // 0–1
        double eyeHeight = 3 * blink + 0.5;
        double glow = (Math.sin(time * 5) + 1) / 2;  // jas (0–1)
        String color = "rgba(255,255,0," + (0.4 + 0.6 * glow) + ")";
        g.setFillStyle(color);

        // levé oko
        g.save();
        g.translate(-6, -25);
        g.scale(1, eyeHeight / 3.0);  // místo ellipse
        g.beginPath();
        g.arc(0, 0, 3, 0, Math.PI * 2);
        g.fill();
        g.restore();

        // pravé oko
        g.save();
        g.translate(6, -25);
        g.scale(1, eyeHeight / 3.0);
        g.beginPath();
        g.arc(0, 0, 3, 0, Math.PI * 2);
        g.fill();
        g.restore();

        // --- pusa (zívnutí) ---
        double yawn = Math.max(0, Math.sin(time * 0.8));
        if (yawn > 0.9) {
            double mouthOpen = (yawn - 0.9) * 20;
            g.save();
            g.translate(0, -15);
            g.scale(1, mouthOpen / 3.0 + 0.3);
            g.setFillStyle("black");
            g.beginPath();
            g.arc(0, 0, 4, 0, Math.PI * 2);
            g.fill();
            g.restore();
        }

        g.restore();
    }

    void drawTriangle(double x, double y, double w, double h, boolean up) {
        g.beginPath();
        if (up) {
            g.moveTo(x, y + h);
            g.lineTo(x + w / 2, y);
            g.lineTo(x + w, y + h);
        } else {
            g.moveTo(x, y);
            g.lineTo(x + w / 2, y + h);
            g.lineTo(x + w, y);
        }
        g.closePath();
        g.fill();
    }

    void drawChurchSilhouette(double x, double y, double scale) {
        g.save();
        g.translate(x, y);
        g.scale(scale, scale);
        g.setFillStyle("#111"); // temně šedá (ne čistě černá, aby trochu vystoupila na tmavém pozadí)

        g.beginPath();
        // hlavní budova
        g.moveTo(-20, 0);
        g.lineTo(-20, -30);
        g.lineTo(20, -30);
        g.lineTo(20, 0);
        g.closePath();
        g.fill();

        // věž
        g.beginPath();
        g.moveTo(-10, -30);
        g.lineTo(-10, -70);
        g.lineTo(10, -70);
        g.lineTo(10, -30);
        g.closePath();
        g.fill();

        // kříž
        g.setStrokeStyle("#111");
        g.setLineWidth(3);
        g.beginPath();
        g.moveTo(0, -75);
        g.lineTo(0, -85);
        g.moveTo(-5, -80);
        g.lineTo(5, -80);
        g.stroke();

        g.restore();
    }

    void drawGraveyard(double x, double y, double scale) {
        g.save();
        g.translate(x, y);
        g.scale(scale, scale);
        g.setStrokeStyle("#111");
        g.setLineWidth(2);

        for (int i = 0; i < 6; i++) {
            double gx = i * 15 + Math.sin(i * 1.3) * 2;
            double gy = Math.cos(i) * 2;

            // základ hrobu
            g.beginPath();
            g.moveTo(gx - 3, gy);
            g.lineTo(gx + 3, gy);
            g.stroke();

            // kříž
            g.beginPath();
            g.moveTo(gx, gy);
            g.lineTo(gx, gy - 10 - Math.random() * 5);
            g.moveTo(gx - 3, gy - 6);
            g.lineTo(gx + 3, gy - 6);
            g.stroke();
        }

        g.restore();
    }

}

