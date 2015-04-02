package cl.util.validate;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageParser {

    // ------------------------------------------------------------ Private Data

    // 样本的矩阵
    private static List swatches      = null;

    // 样本的值
    private static List swatcheValues = null;

    // 图片文件的矩阵化
    private byte[][]    targetColors;
    
    // ------------------------------------------------------------ Test main method
    public static void main(String[] args) {
        // 加入样本与其样本对应的数值
        String[] files = new String[5];
        String[] values = new String[5];
        files[0]="D:\\cvsjarso\\test\\0411.bmp";
        values[0]="0411";
        files[1]="D:\\cvsjarso\\test\\6351.bmp";
        values[1]="6351";
        files[2]="D:\\cvsjarso\\test\\7479.bmp";
        values[2]="7479";
        files[3]="D:\\cvsjarso\\test\\2441.bmp";
        values[3]="2441";
        files[4]="D:\\cvsjarso\\test\\6327.bmp";
        values[4]="6327";        
//        for (int i = 0; i < files.length; i++){
//            files[i] = "D:\\workspace\\SZXClientAPP\\res\\" + i + ".jpg";
//            values[i] = String.valueOf(i);
//        }
        
        ImageParser parse = new ImageParser(files, values);
        long startime = System.currentTimeMillis();
        try {
            // 解析图片
            System.out.println(parse.parseValue("D:\\cvsjarso\\test\\7647.bmp"));
            long sincetime = System.currentTimeMillis();
            System.out.println("所花时间 = " + (sincetime - startime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------ Constructors

    /**
     * 载入所有样本路径与其样本对应的数值
     * 
     * @param files
     */
    public ImageParser(String[] files, String[] values) {
        // 只允许样本创建一次即可
        if (swatches == null && swatcheValues == null) {
            int fileslength = files.length;
            int valueslength = values.length;
            if(fileslength != valueslength){
                System.out.println("样本文件与样本数值不匹配！请重新设置！");
                return;
            }
            swatches = new ArrayList(fileslength);
            swatcheValues = new ArrayList(valueslength);
            int i = 0;
            try {
                for (; i < files.length; i++) {
                    swatches.add(imageToMatrix(files[i]));
                    swatcheValues.add(i, values[i]);
                }
            } catch (Exception e) {
                System.out.println(files[i] + " can not be parsed");
                e.printStackTrace();
            }
        }
    }

    public ImageParser() {
        super();
        if (swatches == null || swatcheValues == null) {
            System.out.println("您未载入样本，请先载入样本！");
        }
    }

    /**
     * 解析图片的值
     * 
     * @param parseFilePath
     *            给出图片路径
     * @return 返回字符串
     * @throws Exception
     */
    public String parseValue(String parseFilePath) throws Exception {
        StringBuffer result = new StringBuffer();
        targetColors = imageToMatrix(parseFilePath);
        // printMatrix(targetColors);
        int height = targetColors.length;
        int targetWidth = targetColors[0].length;

        int width = 0;
        Iterator it = swatches.iterator();
        while (it.hasNext()) {
            byte[][] bytes = (byte[][]) it.next();
            int templen = bytes[0].length;
            if (templen > width)
                width = templen;
        }
        // System.out.println("MaxWidth = " + width);
        // System.out.println("MaxHeight = " + height);
        int xTag = 0;
        while ((xTag + width) < targetWidth) {
            cout: {
                Iterator itx = swatches.iterator();
                int i = 0;
                while (itx.hasNext()) {
                    byte[][] bytes = (byte[][]) itx.next();
                    byte[][] temp = splitMatrix(targetColors, xTag, 0, width, height);
                    // System.out.println(i++);
                    if (isMatrixInBigMatrix(bytes, temp)) {
                        xTag += width;
                        // System.out.println("new maxtrix: ");
                        // printMatrix(temp);
                        
                        result.append(swatcheValues.get(i));
                        break cout;
                    }
                    i++;
                }
                xTag++;
            }
        }
        return result.toString();
    }

    // ------------------------------------------------------------ Private methods

    /**
     * 判断一个矩阵是否在另外的矩阵中
     * 
     * @param source
     *            源矩阵
     * @param bigMatrix
     *            大的矩阵
     * @return 如果存在就返回 true
     */
    private static final boolean isMatrixInBigMatrix(byte[][] source, byte[][] bigMatrix) {
        if (source == bigMatrix)
            return true;
        if (source == null || bigMatrix == null)
            return false;

        if (source.length > bigMatrix.length)
            return false;

        try {
            for (int i = 0; i < source.length; i++) {
                if (source[i].length > bigMatrix[i].length)
                    return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        int height = source.length;
        int width = source[0].length;
        int x = 0, y = 0;
        int i = 0, j = 0;
        int count = 0;

        int comparecount = height * width;

        for (; i < bigMatrix.length - height + 1; i++) {
            for (j = 0; j < bigMatrix[i].length - width + 1; j++) {
                cout: {
                    x = 0;
                    count = 0;
                    for (int k = i; k < height + i; k++) {
                        y = 0;
                        for (int l = j; l < width + j; l++) {
                            // System.out.println("bytes[" + x + "][" + y + "]"
                            // + " = " + source[x][y] + ", " + "other["
                            // + k + "][" + l + "] = " + bigMatrix[k][l]);
                            if ((source[x][y] & bigMatrix[k][l]) == source[x][y]) {
                                count++;
                            } else
                                break cout;
                            y++;
                        }
                        x++;
                    }
                    // System.out.println("count = " + count);
                    if (count == comparecount)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * 切割矩阵
     * 
     * @param source
     *            源矩阵
     * @param x
     *            X坐标
     * @param y
     *            Y坐标
     * @param width
     *            矩阵的宽度
     * @param height
     *            矩阵的高度
     * @return 切割后的矩阵
     */
    private static final byte[][] splitMatrix(byte[][] source, int x, int y, int width, int height) {
        byte[][] resultbytes = new byte[height][width];
        for (int i = y, k = 0; i < height + y; i++, k++) {
            for (int j = x, l = 0; j < width + x; j++, l++) {

                resultbytes[k][l] = source[i][j];
                // System.out.println("source[" + i + "][" + j + "]" + " = " +
                // source[i][j] + ", " + "resultbytes["
                // + k + "][" + l + "] = " + resultbytes[k][l]);
            }

        }
        return resultbytes;
    }

    /**
     * 图片转换成矩阵数组
     * 
     * @param filePath
     *            文件路径
     * @return 返回矩阵
     * @throws Exception
     *             可能会抛出异常
     */
    private byte[][] imageToMatrix(String filePath) throws Exception {
        // 读入文件
        Image image = ImageIO.read(new File(filePath));
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage src = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        src.getGraphics().drawImage(image, 0, 0, null);

        byte[][] colors = new byte[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = src.getRGB(j, i);
                // 像素进行灰度处理
                String sRed = Integer.toHexString(rgb).substring(2, 4);
                String sGreen = Integer.toHexString(rgb).substring(4, 6);
                String sBlank = Integer.toHexString(rgb).substring(6, 8);
                long ired = Math.round((Integer.parseInt(sRed, 16) * 0.3 + 0.5d));
                long igreen = Math.round((Integer.parseInt(sGreen, 16) * 0.59 + 0.5d));
                long iblank = Math.round((Integer.parseInt(sBlank, 16) * 0.11 + 0.5d));
                long al = ired + igreen + iblank;

                // if (al > 127)
                // System.out.print(" " + " ");
                // else
                // System.out.print(" " + "1");
                // System.out.print(" " + (tempint > = maxint ? 0 : 1));
                // System.out.println("tempInt = " + tempint);

                /* 将图像转换成0,1 */
                // 此处的值可以将来修改成你所需要判断的值
                colors[i][j] = (byte) (al > 127 ? 0 : 1);
            }
            // System.out.println();
        }

        return colors;
    }

    /**
     * 打印矩阵
     * 
     * @param matrix
     */
    private static final void printMatrix(byte[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(" 1");
            }
            System.out.println();
        }
    }
}
