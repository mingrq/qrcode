public class Test {
    public static void main(String[] args) throws Exception {
        String text = "https://blog.csdn.net/qq_36020545/article/details/58653926";  //这里设置自定义网站url
        String logoPath = "C:\\Users\\002\\Desktop\\test.jpg";
        String destPath = "C:\\Users\\002\\Desktop\\";
        QrCodeUtils qrCodeUtils = new QrCodeUtils();

        System.out.println(qrCodeUtils.encodeBase64(text, true));
    }
}
