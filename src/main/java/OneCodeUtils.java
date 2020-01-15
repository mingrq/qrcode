import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import sun.misc.BASE64Encoder;

/**
 * ZXing条形码编码/解码
 */
public class OneCodeUtils {
    //图片编码格式
    private String FORMAT_NAME = "PNG";
    // 条形码宽度
    private int WIDTH = 160;
    // 条形码高度
    private int HEIGHT = 50;
    //条形码编码格式
    private BarcodeFormat FORMAT = BarcodeFormat.EAN_13;

    public OneCodeUtils() {

    }

    /**
     *
     * @param Width      条形码宽度  -1默认60
     * @param Height     条形码高度  -1默认60
     * @param Format     条形码编码格式   null默认BarcodeFormat.EAN_13
     */
    public OneCodeUtils(int Width, int Height, BarcodeFormat Format) {
        if (Width > 0)
            WIDTH = Width;
        if (Height > 0)
            HEIGHT = Height;
        if (Format != null)
            FORMAT = Format;
    }

    /**
     * 设置条形码尺寸
     *
     * @param Width      条形码宽度  -1默认60
     * @param Height     条形码高度  -1默认60
     * @param Format     条形码编码格式   null默认BarcodeFormat.EAN_13
     */
    public void setOneCodeSize( int Width, int Height, BarcodeFormat Format) {
        if (Width > 0)
            WIDTH = Width;
        if (Height > 0)
            HEIGHT = Height;
        if (Format != null)
            FORMAT = Format;
    }

    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, FORMAT, codeWidth, height, null);
            MatrixToImageWriter.writeToFile(bitMatrix, FORMAT_NAME, new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 条形码编码Base64
     *
     * @param contents
     */
    public String encodeBase64(String contents) {
        String png_base64 = null;
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, WIDTH);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, FORMAT, codeWidth, HEIGHT, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            MatrixToImageWriter.writeToStream(bitMatrix, FORMAT_NAME, baos);
            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = "data:image/jpeg;base64," + png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        } catch (Exception e) {
            e.printStackTrace();
        }
        return png_base64;
    }


    /**
     * 条形码解码
     *
     * @param imgPath 条形码路径
     * @return String
     */
    public String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
