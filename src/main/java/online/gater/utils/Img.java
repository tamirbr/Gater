package online.gater.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Img {
    private static final int MAX_IMAGE_SIZE = 600;

    // Crop image to square
    public static byte[] cropImage(byte[] src) {
        if(src != null) {
            // convert byte array to BufferedImage
            BufferedImage bImageFromConvert = byteToBufferedImage(src);
            BufferedImage dest;
            int height = bImageFromConvert.getHeight();
            int width = bImageFromConvert.getWidth();
            // if not square
            if (height != width) {
                if (height > width) {
                    dest = bImageFromConvert.getSubimage(0, (height - width) / 2, width, width);
                } else {
                    dest = bImageFromConvert.getSubimage((width - height) / 2, 0, height, height);
                }
                resizeImage(dest);
                src = bufferedImageToByte(dest);
            }
        }
        return src;
    }

    // resize image
    private static BufferedImage resizeImage(BufferedImage originalImage){
    	if(originalImage.getWidth() > MAX_IMAGE_SIZE){
	    	BufferedImage newImage = new BufferedImage(MAX_IMAGE_SIZE,MAX_IMAGE_SIZE,originalImage.getType());
	        Graphics2D g = newImage.createGraphics();
	        g.drawImage(originalImage, 0, 0, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, null);
	        g.dispose();
	        return newImage;
    	}
    	return originalImage;
    }

    // convert BufferedImage to byte array
    private static byte[] bufferedImageToByte(BufferedImage dest){
        byte[] src = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(dest, "jpg", baos);
            baos.flush();
            src = baos.toByteArray();
            baos.close();

        } catch (IOException e) {

        }
        return src;
    }

    // convert byte array to BufferedImage
    public static BufferedImage byteToBufferedImage(byte[] src){
        InputStream in = new ByteArrayInputStream(src);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bImageFromConvert;
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.isEmpty();
    }
}
