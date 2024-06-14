package xyz.marsavic.gfxlab.graphics3d;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import xyz.marsavic.geometry.Interval;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.Vec3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageTexture extends Texture {

    private Image image;
    private PixelReader pr;

    // Izvor: https://www.tutorialspoint.com/javafx/javafx_images.htm
    public ImageTexture(String filename) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(filename);
        Image image = new Image(inputStream);
    }

    // Izvor: https://raytracing.github.io/books/RayTracingTheNextWeek.html
    @Override
    public Color value(Vector uv, Vec3 p) {
        if (image.getHeight() <= 0)
            return Color.rgb(0, 0, 0);

        double u = Interval.pq(0, 1).clamp(uv.x());
        double v = 1.0 - Interval.pq(0, 1).clamp(uv.y());
        int i = (int) Math.round(u * image.getWidth());
        int j = (int) Math.round(v * image.getHeight());

        pr = image.getPixelReader();
        double red = pr.getColor(i, j).getRed();
        double green = pr.getColor(i, j).getGreen();
        double blue = pr.getColor(i, j).getBlue();

        double colorScale = 1.0 / 255.0;
        return Color.rgb(colorScale * red, colorScale * green, colorScale * blue);
    }
}
