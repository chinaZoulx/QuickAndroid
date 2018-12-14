package org.chris.zxing.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class QRCodeParse {
    public static Bitmap createQRCode(String contentStr) throws WriterException {
        return createQRCode(contentStr, null);
    }

    /**
     * 生成QRCode（二维码）
     *
     * @param contentStr
     * @param logo       logo图片
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String contentStr, Bitmap logo) throws WriterException {
        if (contentStr == null || contentStr.equals("")) {
            return null;
        }
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(contentStr, BarcodeFormat.QR_CODE, 300, 300, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return setupLogo(bitmap, logo);
    }

    public static Bitmap setupLogo(Bitmap qrCode, Bitmap logo) {
        if (logo == null || qrCode == null) return qrCode;
        //获取图片的宽高
        int srcWidth = qrCode.getWidth();
        int srcHeight = qrCode.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0 || logoWidth == 0 || logoHeight == 0) return qrCode;

        logo = cutToSquareBitmap(logo, Math.min(logoWidth, logoHeight));//裁剪
        logoWidth = logo.getWidth();
        logoHeight = logo.getHeight();

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 7 / logoWidth;
        int logoLeft = (srcWidth - logoWidth) / 2;//Logo位置
        int logoTop = (srcHeight - logoHeight) / 2;
        int logoBorderSize = (int) (3 / scaleFactor);//logo边框
        int logoBorderRadius = 5;//logo边框圆角

        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(qrCode, 0, 0, null);

            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);//缩放

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            int borderLeft = logoLeft - logoBorderSize;
            int borderRight = logoLeft + logoWidth + logoBorderSize;
            int borderTop = logoTop - logoBorderSize;
            int borderBottom = logoTop + logoHeight + logoBorderSize;
            canvas.drawRoundRect(new RectF(borderLeft, borderTop, borderRight, borderBottom), logoBorderRadius, logoBorderRadius, paint);
            canvas.drawBitmap(logo, logoLeft, logoTop, null);

            canvas.save();//canvas.save(Canvas.ALL_SAVE_FLAG)
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    /**
     * 解析图片二维码
     *
     * @param qrCodeImg
     * @return
     */
    public static String parseQRCode(@NonNull Bitmap qrCodeImg) {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);

        try {
            Result result = new QRCodeReader().decode(Bitmap2BinaryBitmap(qrCodeImg), hints);
            return result.getText();
        } catch (Exception ex) {
            return "";
        }
    }

    private static BinaryBitmap Bitmap2BinaryBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        //获取像素
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

        return new BinaryBitmap(new HybridBinarizer(source));
    }

    /**
     * 裁剪为正方形位图
     *
     * @param bitmap
     * @param reqLength 希望取得的长度
     * @return
     */
    public static Bitmap cutToSquareBitmap(Bitmap bitmap, int reqLength) {
        if (null == bitmap || reqLength <= 0) return null;

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg >= reqLength && heightOrg >= reqLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = reqLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg);
            int scaledWidth = widthOrg > heightOrg ? longerEdge : reqLength;
            int scaledHeight = widthOrg > heightOrg ? reqLength : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - reqLength) / 2;
            int yTopLeft = (scaledHeight - reqLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, reqLength, reqLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }

        return result;
    }

}
