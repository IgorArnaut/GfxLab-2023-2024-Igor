package xyz.marsavic.gfxlab.graphics3d.textures;

import javafx.scene.image.PixelReader;
import xyz.marsavic.geometry.Vector;
import xyz.marsavic.gfxlab.Color;
import xyz.marsavic.gfxlab.graphics3d.Material;
import xyz.marsavic.gfxlab.graphics3d.Texture;

import java.io.InputStream;

public class ImageTexture implements Texture {

    private final javafx.scene.image.Image image;
    private final PixelReader pr;

    private ImageTexture(String filename)
    {
        InputStream is = ImageTexture.class.getResourceAsStream(filename);
        assert is != null;
        this.image = new javafx.scene.image.Image(is);
        this.pr = image.getPixelReader();
    }

    public static ImageTexture create(String filename)
    {
        return new ImageTexture(filename);
    }

    public Color colorAt(Vector uv) {
        // Dimenzije slike
        double width = image.getWidth();
        double height = image.getHeight();

        // Koordinate u i v pomerene u opseg [0, 1]
        Vector uv2 = Vector.xy(uv.x() * 2 - 1, (uv.y() + 1) / 2);

        // Koordinate i i j
        Vector ij = uv2.mul(Vector.xy(width, height)).round();
        // Pomeranje i i j
        // ij = ij.add(Vector.xy(0 * ij.x(), 0 * ij.y()));
        // ij = ij.mod(Vector.xy(width, height));
        // Flipovanje j
        ij = ij.withY(height - ij.y() - 1);

        return Color.rgb(
                pr.getColor(ij.xInt(), ij.yInt()).getRed(),
                pr.getColor(ij.xInt(), ij.yInt()).getGreen(),
                pr.getColor(ij.xInt(), ij.yInt()).getBlue()
        );
    }

    @Override
    public Material at(Vector uv) {
        return Material.matte(colorAt(uv)); // [1]
    }

}
// [1] https://medium.com/@dbildibay/ray-tracing-adventure-part-iv-678768947371