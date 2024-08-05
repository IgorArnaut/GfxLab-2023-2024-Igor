package xyz.marsavic.gfxlab.graphics3d.textures;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Texture;

import java.io.InputStream;

public class ImageTexture implements Texture {

    private final Image image;
    private final PixelReader pr;

    private ImageTexture(String filename)
    {
        InputStream is = ImageTexture.class.getResourceAsStream(filename);
        assert is != null;
        this.image = new Image(is);
        this.pr = image.getPixelReader();
    }

    public static ImageTexture create(String filename)
    {
        return new ImageTexture(filename);
    }

    @Override
    public Color getColorAt(Vector uv) {
        // Dimenzije slike
        double width = image.getWidth();
        double height = image.getHeight();

        // Koordinate u i v u novom opsegu
        Vector uv2 = Vector.xy((uv.x() + 1) / 2, (uv.y() + 1) / 2);

        // Koordinate i i j
        Vector ij = uv2.mul(Vector.xy(width, height));
        ij = ij.round();
        // Pomeranje i i j
        ij = ij.add(Vector.xy(0 * ij.x(), 0 * ij.y()));
        ij = ij.mod(Vector.xy(width, height));
        // Flipovanje j
        ij = ij.withY(height - ij.y() - 1);

        return Color.rgb(
                pr.getColor(ij.xInt(), ij.yInt()).getRed(),
                pr.getColor(ij.xInt(), ij.yInt()).getGreen(),
                pr.getColor(ij.xInt(), ij.yInt()).getBlue()
        );
    }

}
