
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Helper {
   private BufferedImage image;
   private final String TAG = "C:/Users/User/Desktop/project 4-29-19/";
   private double[][] pixelated_image;
   private int[] raster;
   private int hThreashold;
   private int lThreashold;

   public Helper(String urlOfPic) {
      image = loadImage(/* TAG + */urlOfPic);
      pixelated_image = getImageIn2DArray(image);

   }

   public Helper(BufferedImage image) {
      this.image = image;
      pixelated_image = getImageIn2DArray(image);
   }

   public static void main(String[] args) {
      Helper a = new Helper("naruto.jpg");
      a.setHighThreashold(180);
      a.setLowThreashold(150);
      a.outputImage();
   }

   public int[][] changetoIntArray(double[][] list) {
      int[][] returnedArray = new int[list.length][list[0].length];
      for (int i = 0; i < list.length; i++) {
         for (int j = 0; j < list[0].length; j++) {
            returnedArray[i][j] = (int) list[i][j];
         }
      }
      return returnedArray;
   }

   public void hysteresisThreasholding(double[][] list) {
      for (int i = 1; i < list.length; i++) {
         for (int j = 1; j < list[0].length; j++) {
            if (list[i][j] < lThreashold) {
               list[i][j] = Double.NaN;
            }
            if (list[i][j] > hThreashold) {
               list[i][j] = 255;
            }
         }
      }
   }

   public void setPixelatedImage(double[][] list) {
      pixelated_image = list;

   }

   public void setHighThreashold(int h) {
      hThreashold = h;
   }

   public void setLowThreashold(int l) {
      lThreashold = l;
   }

   public void process() {
      pixelated_image = cannyOperator(pixelated_image);
   }

   private static int counter = 0;

   public void outputImage() {
      double[][] image = pixelated_image;
      BufferedImage outputImage = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_3BYTE_BGR);
      for (int i = 0; i < image.length; i++) {
         for (int j = 0; j < image[0].length; j++) {
            if (image[i][j] < lThreashold) {
               outputImage.setRGB(i, j, 0);
            } else {
               outputImage.setRGB(i, j, 255);
            }

         }
      }

      try {
         ImageIO.write(outputImage, "jpg", new File("outputImage" + counter + ".jpg"));
         counter++;
         System.out.println("Outputted Image Succefully");
      } catch (IOException e) {
         System.out.println("error setting image");
      }
   }

   // pre: an image name on the computer
   // post: reads the image
   private BufferedImage loadImage(String imgName) {
      try {
         return ImageIO.read(new File(imgName));
      } catch (IOException e) {
         System.out.println("Image not Found");
      }
      return null;
   }

   private double[][] getImageIn2DArray(BufferedImage image) {
      int width = image.getWidth();
      int height = image.getHeight();
      double[][] arr = new double[width][height];
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[0].length; j++) {
            Color temp = new Color(image.getRGB(i, j));
            arr[i][j] = ((temp.getBlue() * .11 + temp.getGreen() * .59 + temp.getRed() * .3));
            // arr[i][j] = ((temp.getBlue()+temp.getGreen()+temp.getRed())/3);
         }
      }
      return arr;
   }

   private double[][] sobelHelperY(double[][] list) {
      double[][] image = new double[list.length][list[0].length];
      // -1,-2,-1
      // 0,0,0
      // 1,2,1
      for (int i = 1; i < list.length - 1; i++) {
         for (int j = 1; j < list[0].length - 1; j++) {
            image[i][j] = ((list[i - 1][j - 1] * -1) + (list[i][j - 1] * -2) + list[i + 1][j - 1] * -1)
                  + ((list[i - 1][j + 1] * 1) + (list[i][j + 1] * 2) + (list[i + 1][j + 1] * 1));
         }
      }
      return image;
   }

   private double[][] sobelHelperX(double[][] list) {
      double[][] image = new double[list.length][list[0].length];
      // -1,0,1
      // -2,0,2
      // -1,0,1
      for (int i = 1; i < list.length - 1; i++)
         for (int j = 1; j < list[0].length - 1; j++)
            image[i][j] = ((list[i - 1][j - 1] * -1) + (list[i - 1][j] * -2) + (list[i - 1][j + 1] * -1)
                  + (list[i + 1][j - 1] * 1) + (list[i + 1][j] * 2) + (list[i + 1][j + 1] * 1));

      return image;
   }

   private double[][] cannyHelper(double[][] list) {
      double[][] Gx = sobelHelperX(list);
      double[][] Gy = sobelHelperY(list);
      double[][] GradientMagnitude = new double[Gx.length][Gx[0].length];
      for (int i = 1; i < Gx.length - 1; i++)
         for (int j = 1; j < Gx[0].length - 1; j++) {
            double temp = Math.abs(Math.toDegrees(Math.atan(Gy[i][j] / Gx[i][j])));// array of all the GradientMagnitude
            GradientMagnitude[i][j] = temp;
         }
      // -------- nonmax supp ---------
      for (int i = 1; i < GradientMagnitude.length - 1; i++) {
         for (int j = 1; j < GradientMagnitude[0].length - 1; j++) {
            if ((GradientMagnitude[i][j] < 22.5)) {// 0
               if (GradientMagnitude[i][j] > GradientMagnitude[i][j + 1]
                     && GradientMagnitude[i][j] > GradientMagnitude[i][j - 1]) {
                  GradientMagnitude[i + 1][j] = Double.NaN;
                  GradientMagnitude[i - 1][j] = Double.NaN;
               }
            } else if ((GradientMagnitude[i][j] < 67.5)) {// 45
               if (GradientMagnitude[i][j] > GradientMagnitude[i - 1][j + 1]
                     && GradientMagnitude[i][j] > GradientMagnitude[i + 1][j - 1]) {
                  GradientMagnitude[i - 1][j + 1] = Double.NaN;
                  GradientMagnitude[i + 1][j - 1] = Double.NaN;
               }
            } else if ((GradientMagnitude[i][j] < 112.5)) {// 90
               if (GradientMagnitude[i][j] > GradientMagnitude[i][j + 1]
                     && GradientMagnitude[i][j] > GradientMagnitude[i][j - 1]) {
                  GradientMagnitude[i][j + 1] = Double.NaN;
                  GradientMagnitude[i][j - 1] = Double.NaN;
               }
            } else if ((GradientMagnitude[i][j] < 157.5)) {// 135
               if (GradientMagnitude[i][j] > GradientMagnitude[i + 1][j + 1]
                     && GradientMagnitude[i][j] > GradientMagnitude[i - 1][j - 1]) {
                  GradientMagnitude[i + 1][j + 1] = Double.NaN;
                  GradientMagnitude[i - 1][j - 1] = Double.NaN;
               }
            }
         }
      }

      return GradientMagnitude;

   }
   // private double[][] cannyHelper2(double[][]list){//finds Gradient
   // double[][]img = cannyHelper(list);
   // double[][]im2 = new double[img.length][img[0].length];
   // int w =img.length-2;
   // int l =img[0].length-2;

   // for(int i=0; i<w; i++){//Non-Maximum Suppression
   // for(int j=0; j<l; j++){
   // double q =255;
   // double r =255;
   // //finds edge if its 45-
   // if((j>0&&j<img[0].length)&&((0<=img[i][j]&&img[i][j]<22.5)||(157.5<=img[i][j]&&img[i][j]<=180))){
   // q = img[i][j+1];
   // r = img[i][j-1];
   // }
   // else
   // if((i>0&&i<img.length&&j>0&&j<img[0].length)&&(22.5<=img[i][j]&&img[i][j]<67.5)){
   // q = img[i+1][j-1];
   // r = img[i-1][j+1];
   // }
   // else if((i>0&&i<img.length)&&(67.5<=img[i][j]&&img[i][j]<112.5)){
   // q = img[i+1][j];
   // r = img[i-1][j];
   // }
   // else
   // if((i>0&&i<img.length&&j>0&&j<img[0].length)&&(112.5<=img[i][j]&&img[i][j]<157.5)){
   // q = img[i-1][j-1];
   // r = img[i+1][j+1];
   // }

   // if(img[i][j]>=q&&img[i][j]>=r){
   // im2[i][j]=img[i][j];
   // }
   // else{
   // im2[i][j]=0;
   // }

   // }
   // }

   // return im2;
   // }
   private double[][] sobelHelper(double[][] list) {
      double[][] Gx = getRidOfOuterLayer(sobelHelperX(gaussianBlur(list)));
      double[][] Gy = getRidOfOuterLayer(sobelHelperY(gaussianBlur(list)));
      double[][] G = new double[list.length - 1][list[0].length - 1];
      for (int i = 0; i < Gx.length; i++)
         for (int j = 0; j < Gx[0].length; j++)
            G[i][j] = Math.sqrt(Math.pow(Gx[i][j], 2) + Math.pow(Gy[i][j], 2));
      return G;
   }

   private double[][] cannyOperator(double[][] list) {
      double[][] temp = cannyHelper(list);

      hysteresisThreasholding(temp);

      return temp;
   }

   private double averagePixels(double TL, double TM, double TR, double ML, double MM, double MR, double BL, double BM,
         double BR) {
      double average = ((TL * 1) + (TM * 2) + (TR * 1) + (ML * 2) + (MM * 4) + (MR * 2) + (BL * 1) + (BM * 2)
            + (BR * 1)) / 16.0;
      return average;
   }

   private double[][] getRidOfOuterLayer(double[][] image) {
      double[][] returnImage = new double[image.length - 1][image[0].length - 1];
      for (int i = 1; i < image.length; i++)
         for (int j = 1; j < image[0].length; j++)
            returnImage[i - 1][j - 1] = image[i][j];
      return returnImage;
   }

   private double[][] gaussianBlur(double[][] pixels) {
      double[][] imageInArray = new double[pixels.length][pixels[0].length];
      for (int i = 1; i < pixels.length - 1; i++) {
         for (int j = 1; j < pixels[i].length - 1; j++) {
            imageInArray[i][j] = averagePixels(pixels[i - 1][j - 1], pixels[i - 1][j], pixels[i - 1][j + 1],
                  pixels[i][j - 1], pixels[i][j], pixels[i][j + 1], pixels[i + 1][j - 1], pixels[i + 1][j],
                  pixels[i + 1][j + 1]);// 3x3 kernal
         }
      }
      return imageInArray;
   }

   public double[][] getImage() {
      return this.getImageIn2DArray(image);
   }
}