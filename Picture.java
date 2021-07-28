import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URI;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */



public class Picture extends SimplePicture implements MouseListener
{
   private int zoomFactor = 1;
   private int numberBase = 1;
   
  public void getRow(MouseEvent e)
   {
      int cursorY = e.getY();
      int pictureY = (int) (cursorY / zoomFactor + numberBase);
      System.out.println(pictureY);
   }
   public void getCol(MouseEvent e)
   {
      int cursorX = e.getX();
      int pictureX = (int) (cursorX / zoomFactor + numberBase);
      System.out.println(pictureX);
   }
/**
* Method called when the mouse is moved with no buttons down
* @param e the mouse event
*/
   public void mouseMoved(MouseEvent e){}

/**
* Method called when the mouse is clicked
* @param e the mouse event
*/
   public void mouseClicked(MouseEvent e)
   {
      getRow(e);
      getCol(e);
   }

/**
* Method called when the mouse button is pushed down
* @param e the mouse event
*/ 
   public void mousePressed(MouseEvent e){}


/**
* Method called when the mouse button is released
* @param e the mouse event
*/
   public void mouseReleased(MouseEvent e){}

/**
* Method called when the component is entered (mouse moves over it)
* @param e the mouse event
*/
   public void mouseEntered(MouseEvent e){}

/**
* Method called when the mouse moves over the component
* @param e the mouse event
*/
   public void mouseExited(MouseEvent e){}

///////////////////// constructors ///////////////////////////////////**
/* Constructor that takes no arguments 
*/
   public Picture ()
   {
   /* not needed but use it to show the implicit call to super()
   * child constructors always call a parent constructor 
   */
      super();  
   }

/**
* Constructor that takes a file name and creates the picture 
* @param fileName the name of the file to create the picture from
*/
   public Picture(String fileName)
   {
   // let the parent class handle this fileName
      super(fileName);
   }

/**
* Constructor that takes the width and height
* @param height the height of the desired picture
* @param width the width of the desired picture
*/
   public Picture(int height, int width)
   {
   // let the parent class handle this width and height
      super(width,height);
   }

/**
* Constructor that takes a picture and creates a 
* copy of that picture
* @param copyPicture the picture to copy
*/
   public Picture(Picture copyPicture)
   {
   // let the parent class do the copy
      super(copyPicture);
   }

/**
* Constructor that takes a buffered image
* @param image the buffered image to use
*/
   public Picture(BufferedImage image)
   {
      super(image);
   }

////////////////////// methods ///////////////////////////////////////

   public Picture getPicture()
   {
      return this;
   }	

/**
* Method to return a string with information about this picture.
* @return a string with information about the picture such as fileName,
* height and width.
*/
   public String toString()
   {
      String output = "Picture, filename " + getFileName() + 
         " height " + getHeight() 
         + " width " + getWidth();
      return output;
   }

/** 
* Method to set the blue to 0 
* 
*/
   public void zeroBlue()
   {
      Pixel[][] pixels = this.getPixels2D();
      for(int row = 0; row< pixels.length; row++)
      {
         for(int col = 0; col<pixels[row].length; col++)
         {
            Pixel pixelObj = pixels[row][col];
            //**COMPLETE THE METHOD HERE***
         }
      }
   }
   
   public void zeroRed()
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void zeroGreen()
   {
      //**COMPLETE THE METHOD HERE***
   }	
 
/** 
* Method to keep just the blue 
* 
*/       
   public void keepOnlyBlue()
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void keepOnlyGreen()
   {
      //**COMPLETE THE METHOD HERE***
   }	
   public void keepOnlyRed()
   {
      //**COMPLETE THE METHOD HERE***
   }
/** 
* Method to negate the picture
* 
*/
   public void negate()
   {
      //**COMPLETE THE METHOD HERE***
   }            
 
/** 
* Method to grayscale the picture
*/
   public void grayscale()
   {
      //**COMPLETE THE METHOD HERE***           
   }              
 
/** Method that mirrors the picture around a 
 * vertical mirror in the center of the picture
 * from left to right */
   public void mirrorVertical()
   {
      //**COMPLETE THE METHOD HERE*** 
   }
   
   public void mirrorVerticalRightToLeft()
   {
      //**COMPLETE THE METHOD HERE***
   } 
   
   public void mirrorHorizontal()
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void mirrorHorizontalBotToTop()
   {
      //**COMPLETE THE METHOD HERE***
   }
	
   public void mirrorDiagonal()
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void mirrorDiagonalOpposite()
   {
      //**COMPLETE THE METHOD HERE***
   }	
    
/** copy from the passed fromPic to the
 * specified startRow and startCol in the
 * current picture
 * @param fromPic the picture to copy from
 * @param startRow the start row to copy to
 * @param startCol the start col to copy to
 */
   public void copy(Picture fromPic, int startRow, int startCol)
   {
      Pixel fromPixel = null;
      Pixel toPixel = null;
      Pixel[][] toPixels = this.getPixels2D();
      Pixel[][] fromPixels = fromPic.getPixels2D();
      for (int fromRow = 0, toRow = startRow; 
      fromRow < fromPixels.length &&
      toRow < toPixels.length; 
      fromRow++, toRow++)
      {
         for (int fromCol = 0, toCol = startCol; 
         fromCol < fromPixels[0].length &&
         toCol < toPixels[0].length;  
         fromCol++, toCol++)
         {
            fromPixel = fromPixels[fromRow][fromCol];
            toPixel = toPixels[toRow][toCol];
            toPixel.setColor(fromPixel.getColor());
         }
      }   
   }
 
   public void copySection(Picture fromPic, int startRow, int startCol, 
                           int finRow, int finCol, int startToRow, int startToCol)
   {
      Pixel fromPixel=null;
      Pixel toPixel=null;
      Pixel[][] toPixels = this.getPixels2D();
      Pixel[][] fromPixels = fromPic.getPixels2D();
      for( int fromRow = startRow, toRow = startToRow; fromRow < (finRow) 
                   && toRow < toPixels.length; fromRow ++,toRow++)
      {
         for(int fromCol = startCol, toCol = startToCol; fromCol < finCol
              &&  toCol < toPixels[0].length; fromCol++, toCol++)
         {
            fromPixel = fromPixels[fromRow][fromCol];
            toPixel = toPixels[toRow][toCol];
            toPixel.setColor(fromPixel.getColor());
         }
      }
   }
      
/** 
* Method to sepia tone the picture
* 
*/
   public void sepiatone()
   {
      //**COMPLETE THE METHOD HERE***
   }	

/** Method to show large changes in color 
 * @param edgeDist the distance for finding edges
 */
   public void edgeDetection(int edgeDist)
   {
      //**COMPLETE THE METHOD HERE***
   }
	
/** 
* Method to pixelate the picture (low bit video game effect)
* 
*/
   public void pixelate()
   {
      //**COMPLETE THE METHOD HERE***
   }

/** 
* Method to posterize the picture - reduce to only 3-4 colors of your choice
* 
*/
   public void posterize()
   {
     //**COMPLETE THE METHOD HERE***
   }

/** 
* Method to blur the picture
* 
*/
   public void blur()
   {
   //**COMPLETE THE METHOD HERE***
   }	
   
//Method for copying the left side of a picture (with green background) to the right side
   public void greenScreen()
   {
      //**COMPLETE THE METHOD HERE***  
   }


   public void rainbowBar()
   {
      Pixel pixel = null;
      Pixel[][] pixels = this.getPixels2D();
      int numRows = pixels.length/6;
      for(int row = 0; row< pixels.length; row++)
      {
         for(int col = 0; col<pixels[row].length; col++)
         {
            pixel = pixels[row][col];
            //red orange yellow green blue purple
            if (row<numRows)//red
            {
               pixel.setRed((pixel.getRed()+255)/2);
            }               
            else if (row<numRows*2)//orange
            {
               pixel.setRed((pixel.getRed()+255)/2);
               pixel.setGreen((pixel.getGreen()+127)/2);
               pixel.setBlue((pixel.getBlue()+44)/2);   
            }
            else if (row<numRows*3)//yellow
            {
               pixel.setRed((pixel.getRed()+255)/2);
               pixel.setGreen((pixel.getGreen()+255)/2);
            }
            else if (row<numRows*4)//green
            {
               pixel.setGreen((pixel.getGreen()+255)/2); 
            }
            else if (row<numRows*5)//blue
            {
               pixel.setBlue((pixel.getBlue()+255)/2); 
            }
            else //purple
            {
               pixel.setRed((pixel.getRed()+143)/2);
               pixel.setBlue((pixel.getBlue()+255)/2);
            }
         }
      }          
   }			
   		
/** 
* Method to calculate the distance between two colors
* 
*/        
   public double distance(Pixel p, Color c)
   {
   
      return Math.sqrt( Math.pow ( Math.abs (p.getRed()-c.getRed() ),2 ) +
                   Math.pow ( Math.abs (p.getGreen()-c.getGreen() ),2 ) +
             		 Math.pow ( Math.abs (p.getBlue()-c.getBlue() ),2 ) );	
   }

/** 
* Method to color splash a picture
* Given a specific color c, make all pixels grayscale except those within a certian distance of the color c
*/  	
   public void colorSplash(Color c)
   {
      //**COMPLETE THE METHOD HERE***	
   }	

/** 
* Method to modify red by some parameter 
* 
*/  
   public void modifyRed(int x)
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void modifyBlue(int y)
   {
      //**COMPLETE THE METHOD HERE***
   }
   
   public void modifyGreen(int z)
   {
      //**COMPLETE THE METHOD HERE***
   }
   
 /** Hide a black and white message in the current
    * picture by changing the red to even and then
    * setting it to odd if the message pixel is black 
    * @param messagePict the picture with a message
    */
   public void encode(Picture messagePict)
   {
      //**COMPLETE THE METHOD HERE***
   }
 /**
   * Method to decode a message hidden in the
   * red value of the current picture
   * @return the picture with the hidden message
   */
   public Picture decode()
   {
      //**COMPLETE THE METHOD HERE***
      return null;
   }
   
   public void pointOne()
   {
      
   }
   
   public void pointTwo()
   {
      
   }
  
   public Picture highlight()
   {
      return null;
   }
   
   public void goTo()
   {
   try {
         Desktop desktop = Desktop.getDesktop();
         desktop.browse(new URI("http://google.com"));
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println("test");
   }
   
   public String readText()
   {
      return "";
   }
/* Main method for testing - each class in Java can have a main 
* method 
*/
   public static void main(String[] args) 
   {
      Picture pic = new Picture();
      pic.explore();

   }
}
