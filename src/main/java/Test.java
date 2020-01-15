public class Test {
//    public static void main(String[] args) throws Exception {
//        String text = "https://blog.csdn.net/qq_36020545/article/details/58653926";  //这里设置自定义网站url
//        String logoPath = "C:\\Users\\002\\Desktop\\test.jpg";
//        String destPath = "C:\\Users\\002\\Desktop\\";
//        QrCodeUtils qrCodeUtils = new QrCodeUtils();
//
//        System.out.println(qrCodeUtils.encodeBase64(text, true));
//    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneCodeUtils oneCodeUtils = new OneCodeUtils();
        String imgPath = "F:/zxing_EAN-13.png";
        String contents = "6926557300360";
        int width = 125, height = 90;
        oneCodeUtils.encode(contents, width, height,imgPath);
        System.out.println(oneCodeUtils.encodeBase64(contents));
        System.out.println("finished zxing EAN-13 encode.");
        String decodeContent = oneCodeUtils.decode(imgPath);
        System.out.println("解码内容如下：" + decodeContent);
        System.out.println("finished zxing EAN-13 decode.");
    }
}
