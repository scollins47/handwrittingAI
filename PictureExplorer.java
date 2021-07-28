import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File; //rg added
import java.io.IOException;
import java.util.Stack; //rg added

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
//rg added
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * Displays a picture and lets you explore the picture by displaying the row, column, red,
 * green, and blue values of the pixel at the cursor when you click a mouse button or
 * press and hold a mouse button while moving the cursor.  It also lets you zoom in or
 * out.  You can also type in a row and column value to see the color at that location.
 * 
 * Originally created for the Jython Environment for Students (JES). 
 * Modified to work with DrJava by Barbara Ericson
 * Also modified to show row and columns by Barbara Ericson
 * 
 * @author Keith McDermottt, gte047w@cc.gatech.edu
 * @author Barb Ericson, ericson@cc.gatech.edu
 * @author Ria Galanos, ria.galanos@gmail.com - all edits marked RG
 */
public class PictureExplorer implements MouseMotionListener, ActionListener, MouseListener
{
// current indicies
/** row index */
   private int rowIndex = 0; 
/** column index */
   private int colIndex = 0;
   private int mouseX;
   private int mouseY;
   private int mouseX1;
   private int mouseY1;
// main GUI
/** window to hold GUI */
   private JFrame pictureFrame;
/** window that allows the user to scroll to see a large picture */
   private JScrollPane scrollPane;
	
   private JPanel sub_panel;
   private JPanel panel;

// GUI components
/** column label */
   private JLabel colLabel;
/** column previous button */
   private JButton colPrevButton;
/** row previous button */
   private JButton rowPrevButton;
/** column next button */
   private JButton colNextButton;
/** row next button */
   private JButton rowNextButton;
/** row label */
   private JLabel rowLabel;
/** text field to show column index */
   private JTextField colValue;
/** text field to show row index */
   private JTextField rowValue;
/** red value label */
   private JLabel rValue;
/** green value label */
   private JLabel gValue;
/** blue value label */
   private JLabel bValue;
/** color swatch label */
   private JLabel colorLabel;
/** panel to show the color swatch */
   private JPanel colorPanel;
	
/** userInput labels */	
   private JTextField tara2;  // rg added - remember to change
	
 // menu components
/** menu bar */
   private JMenuBar menuBar;
/** zoom menu */
   private JMenu zoomMenu;
/** 25% zoom level */
   private JMenuItem twentyFive;
/** 50% zoom level */
   private JMenuItem fifty;
/** 75% zoom level */
   private JMenuItem seventyFive;
/** 100% zoom level */
   private JMenuItem hundred;
/** 150% zoom level */
   private JMenuItem hundredFifty;
/** 200% zoom level */
   private JMenuItem twoHundred;
/** 500% zoom level */
   private JMenuItem fiveHundred;

// components - rg added
   private JMenu file;
   private JMenuItem open;
   private JMenuItem revertToOrig;
   private JMenuItem saveAs;
   private JMenuItem undo;
	
// picture coordinates for select tool - rg added
   private int x_Upper_Left;
   private int y_Upper_Left;
   private int x_Lower_Right;
   private int y_Lower_Right; 	
 
/** The picture being explored */
   private DigitalPicture picture;
   
/** The stack that holds picture changes - rg added */
   private Stack <DigitalPicture> pictureStack;   
	
/** The unaltered picture when the picture is first loaded - rg added */
   private DigitalPicture orig;	

/** The image icon used to display the picture */
   private ImageIcon scrollImageIcon;

/** The image display */
   private ImageDisplay imageDisplay;

/** the zoom factor (amount to zoom) */
   private double zoomFactor;

/** the number system to use, 0 means starting at 0, 1 means starting at 1 */
   private int numberBase=0;

/**
* Public constructor 
* @param picture the picture to explore
*/
   public PictureExplorer(DigitalPicture picture)
   {
   // set the fields
      this.picture=picture;
      orig=picture;
      zoomFactor=1;
   
   // create the window and set things up
      createWindow();
   
   //adds orig picture to stack - rg added   
      pictureStack = new Stack<DigitalPicture>();
      pictureStack.push(orig);
      
      File origFile = new File(picture.getFileName());
      changePicture(origFile);
       
   }
   
   public PictureExplorer()
   {
      this.picture = null;
      orig = null;
      zoomFactor=1;
   
   // create the window and set things up
      createWindow();
   
   //adds orig picture to stack - rg added   
      pictureStack = new Stack<DigitalPicture>();
      pictureStack.push(orig);
      
      File origFile = new File(picture.getFileName());
      changePicture(origFile);
       
   }

      

/**
* Changes the number system to start at one
*/
   public void changeToBaseOne()
   {
      numberBase=1;
   }

/**
* Set the title of the frame
*@param title the title to use in the JFrame
*/
   public void setTitle(String title)
   {
      pictureFrame.setTitle(title);
   }
	
/**
* Method to create and initialize the picture frame
*/
   private void createAndInitPictureFrame()
   {
      pictureFrame = new JFrame();        // create the JFrame
      pictureFrame.setResizable(true);    // allow the user to resize it
      pictureFrame.getContentPane().setLayout(new BorderLayout());   // use border layout
      pictureFrame.setContentPane(setUpSideMenu());//rg added
      pictureFrame.setVisible(true);      //rg added
      pictureFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // when close stop
      pictureFrame.setTitle(picture.getTitle());
      PictureExplorerFocusTraversalPolicy newPolicy = new PictureExplorerFocusTraversalPolicy();
      pictureFrame.setFocusTraversalPolicy(newPolicy);
   }

//rg added
   private JPanel setUpSideMenu()
   {
      JPanel panel = new JPanel();  
      panel.setLayout(new BorderLayout());
         	         
      JPanel sub_panel = new JPanel();
      sub_panel.setLayout(new GridLayout(15, 1));
   	
      // JButton button_modRed = new JButton("Modify Red");
//       button_modRed.addActionListener(new Listener_ModRed());
//       sub_panel.add(button_modRed);
//    	
//       JButton button_modBlue = new JButton("Modify Blue");
//       button_modBlue.addActionListener(new Listener_ModBlue());
//       sub_panel.add(button_modBlue);
//    	
//       JButton button_modGreen = new JButton("Modify Green");
//       button_modGreen.addActionListener(new Listener_ModGreen());
//       sub_panel.add(button_modGreen);
//    	
//       JButton button_zero = new JButton("Zero Color");
//       button_zero.addActionListener(new Listener_Zero());
//       sub_panel.add(button_zero);
//    	
//       JButton button_keepOnly = new JButton("Keep Only");
//       button_keepOnly.addActionListener(new Listener_KeepOnly());
//       sub_panel.add(button_keepOnly);
//    
//       JButton button_negate = new JButton("Negate");
//       button_negate.addActionListener(new Listener_Negate());
//       sub_panel.add(button_negate);
//    	
//       JButton button_grayScale = new JButton("Grayscale");
//       button_grayScale.addActionListener(new Listener_GrayScale());
//       sub_panel.add(button_grayScale);
//    	
//       JButton button_sepiaTone = new JButton("Sepia Tone");
//       button_sepiaTone.addActionListener(new Listener_SepiaTone());
//       sub_panel.add(button_sepiaTone);
//    	
//       JButton button_blur = new JButton("Blur");
//       button_blur.addActionListener(new Listener_Blur());
//       sub_panel.add(button_blur);
//       
//       JButton button_rainbowBar = new JButton("RainbowBar");
//       button_rainbowBar.addActionListener(new Listener_RainbowBar());
//       sub_panel.add(button_rainbowBar);
//       
//       JButton button_greenScreen = new JButton("Green Screen");
//       button_greenScreen.addActionListener(new Listener_GreenScreen());
//       sub_panel.add(button_greenScreen);
//    	
//       JButton button_posterize = new JButton("Posterize");
//       button_posterize.addActionListener(new Listener_Posterize());
//       sub_panel.add(button_posterize);
//    	
//       JButton button_colorSplash = new JButton("Color Splash");
//       button_colorSplash.addActionListener(new Listener_ColorSplash());
//       sub_panel.add(button_colorSplash);
//    	
//       JButton button_mirrorLR = new JButton("Mirror L/R");
//       button_mirrorLR.addActionListener(new Listener_MirrorLR());
//       sub_panel.add(button_mirrorLR);
//    	
//       JButton button_mirrorTB = new JButton("Mirror T/B");
//       button_mirrorTB.addActionListener(new Listener_MirrorTB());
//       sub_panel.add(button_mirrorTB);
//       
//       JButton button_mirrorDiag = new JButton("Mirror Diag");
//       button_mirrorDiag.addActionListener(new Listener_MirrorDiag());
//       sub_panel.add(button_mirrorDiag);
//    	
//       JButton button_pixelate = new JButton("Pixelate");
//       button_pixelate.addActionListener(new Listener_Pixelate());
//       sub_panel.add(button_pixelate);
//    	
//       JButton button_edgeDetector = new JButton("Edge Detector");
//       button_edgeDetector.addActionListener(new Listener_EdgeDetector());
//       sub_panel.add(button_edgeDetector);
//    	
//       JButton button_encode = new JButton("Encode");
//       button_encode.addActionListener(new Listener_Encode());
//       sub_panel.add(button_encode);
//    	
//       JButton button_decode = new JButton("Decode");
//       button_decode.addActionListener(new Listener_Decode());
//       sub_panel.add(button_decode);
//       

      JButton button_readText = new JButton("readText");
      button_readText.addActionListener(new Listener_readText());
      sub_panel.add(button_readText);
      
      JButton button_goTo = new JButton("goTo");
      button_goTo.addActionListener(new Listener_goTo());
      sub_panel.add(button_goTo);
     
      panel.add(sub_panel, BorderLayout.EAST);
      return panel;
   }

/**
* Method to create the menu bar, menus, and menu items
*/
   private void setUpMenuBar()
   {
     //create file chooser - rg added
      file = new JMenu("File");
      open = new JMenuItem("Open");
      saveAs = new JMenuItem("Save As");
   	
     //create revertToOrig button - rg added
      revertToOrig = new JMenuItem("Revert to Orig");
      undo = new JMenuItem("Undo");
   
     //create menu
      menuBar = new JMenuBar();
      zoomMenu = new JMenu("Zoom");
      twentyFive = new JMenuItem("25%");
      fifty = new JMenuItem("50%");
      seventyFive = new JMenuItem("75%");
      hundred = new JMenuItem("100%");
      hundred.setEnabled(false);
      hundredFifty = new JMenuItem("150%");
      twoHundred = new JMenuItem("200%");
      fiveHundred = new JMenuItem("500%");
   
   // add the action listeners
      twentyFive.addActionListener(this);
      fifty.addActionListener(this);
      seventyFive.addActionListener(this);
      hundred.addActionListener(this);
      hundredFifty.addActionListener(this);
      twoHundred.addActionListener(this);
      fiveHundred.addActionListener(this);
   
   // add the menu items to the menus
      menuBar.add(file);//rg added
      zoomMenu.add(twentyFive);
      zoomMenu.add(fifty);
      zoomMenu.add(seventyFive);
      zoomMenu.add(hundred);
      zoomMenu.add(hundredFifty);
      zoomMenu.add(twoHundred);
      zoomMenu.add(fiveHundred);
      menuBar.add(zoomMenu);
    	      
   //rg added
      revertToOrig.addActionListener(new Listener_RevertToOrig());      
      open.addActionListener(new Listener_Open());
      saveAs.addActionListener(new Listener_SaveAs());
      undo.addActionListener(new Listener_Undo());
      file.add(open);
      file.add(saveAs);
      file.add(revertToOrig);
      file.add(undo);
   	
            
   // set the menu bar to this menu
      pictureFrame.setJMenuBar(menuBar);
   }
//Ria added from Barb's shape stuff
/** An inner class for handling the mouse listener interface */
   private class MyMouseAdapter extends MouseAdapter
   {
   /** Method to handle when the user presses down the button */
      public void mousePressed(MouseEvent e)
      {
         mouseX = x_Upper_Left = e.getX();
         mouseY = y_Upper_Left = e.getY();
      }
   /** Method to handle when the user releases the mouse */
      public void mouseReleased(MouseEvent e)
      {
         mouseX1 = x_Lower_Right = e.getX();
         mouseY1 = y_Lower_Right = e.getY();
        
      }
   }
   
//rg added listeners for open, save as, and revertToOrig buttons
   private class Listener_Open implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         JFileChooser chooser = new JFileChooser();
      	//chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
         chooser.setFileFilter(filter);
         int returnVal = chooser.showOpenDialog(pictureFrame);
         if(returnVal == JFileChooser.APPROVE_OPTION) 
         {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
            changePicture(chooser.getSelectedFile());
         }
         updatePicture();
      }
   }
	
   private class Listener_SaveAs implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         saveAs();
      }
   }	
	
   private class Listener_RevertToOrig implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         revertToOrig();
      }
   }	
   
   private class Listener_Undo implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         undo();
      }
   }      
   
//rg added
   private void updatePicture ()
   {
      BufferedImage bimg = picture.getBufferedImage();
      imageDisplay.setImage(bimg);
      imageDisplay.revalidate();
      checkScroll();        
      repaint();
   }  
//rg added	 
   private void changePicture (File file)
   {
      DigitalPicture newPic = new Picture(file.getAbsolutePath());
      this.picture=newPic;
      this.orig=newPic;
      pictureFrame.setTitle(file.getName());
      repaint();
      BufferedImage bimg = picture.getBufferedImage();
      imageDisplay.setImage(bimg);
      imageDisplay.revalidate();
      checkScroll(); 
      pictureStack.push(newPic);       
   }	
//rg added 	
   private void saveAs()
   {
      //this.picture.write(this.picture.getFileName());
   	//need to get info to save
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
         //chooser.setFileFilter(filter);
      int returnVal = chooser.showSaveDialog(pictureFrame);
      if(returnVal == JFileChooser.APPROVE_OPTION) 
      {
         File file = chooser.getSelectedFile();
         this.picture.write(file.toString());
      }
   
   }
   private void revertToOrig() // rg - reverts back to original picture that was opened
   {	
      File newFile = new File(orig.getFileName());
      changePicture(newFile);
      updatePicture();
   }	
   private void undo() // rg - changes picture back to previous edit
   {	
      File newFile = new File((pictureStack.pop()).getFileName());
      changePicture(newFile);
      updatePicture();
   }	

/**
* Create and initialize the scrolling image
*/
   private void createAndInitScrollingImage()
   {
      scrollPane = new JScrollPane();
   
      BufferedImage bimg = picture.getBufferedImage();
      imageDisplay = new ImageDisplay(bimg);
      imageDisplay.addMouseMotionListener(this);
      imageDisplay.addMouseListener(this);
      imageDisplay.setToolTipText("Click a mouse button on a pixel to see the pixel information");
      scrollPane.setViewportView(imageDisplay);
      pictureFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
   }

/**
* Creates the JFrame and sets everything up
*/
   private void createWindow()
   {
   // create the picture frame and initialize it
      createAndInitPictureFrame();
   
   // set up the menu bar
      setUpMenuBar();
   
   //create the information panel
      createInfoPanel();
   
   //creates the scrollpane for the picture
      createAndInitScrollingImage();
   
   // show the picture in the frame at the size it needs to be
      pictureFrame.pack();
      pictureFrame.setVisible(true);
   }

/**
* Method to set up the next and previous buttons for the
* pixel location information
*/
   private void setUpNextAndPreviousButtons()
   {
   // create the image icons for the buttons
      Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("leftArrow.gif"), 
                               "previous index");
      Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("rightArrow.gif"), 
                               "next index");
   // create the arrow buttons
      colPrevButton = new JButton(prevIcon);
      colNextButton = new JButton(nextIcon);
      rowPrevButton = new JButton(prevIcon);
      rowNextButton = new JButton(nextIcon);
   
   // set the tool tip text
      colNextButton.setToolTipText("Click to go to the next column value");
      colPrevButton.setToolTipText("Click to go to the previous column value");
      rowNextButton.setToolTipText("Click to go to the next row value");
      rowPrevButton.setToolTipText("Click to go to the previous row value");
   
   // set the sizes of the buttons
      int prevWidth = prevIcon.getIconWidth() + 2;
      int nextWidth = nextIcon.getIconWidth() + 2;
      int prevHeight = prevIcon.getIconHeight() + 2;
      int nextHeight = nextIcon.getIconHeight() + 2;
      Dimension prevDimension = new Dimension(prevWidth,prevHeight);
      Dimension nextDimension = new Dimension(nextWidth, nextHeight);
      colPrevButton.setPreferredSize(prevDimension);
      rowPrevButton.setPreferredSize(prevDimension);
      colNextButton.setPreferredSize(nextDimension);
      rowNextButton.setPreferredSize(nextDimension);
   
   // handle previous column button press
      colPrevButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     colIndex--;
                     if (colIndex < 0)
                        colIndex = 0;
                     displayPixelInformation(colIndex,rowIndex);
                  }
               });
   
   // handle previous row button press
      rowPrevButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     rowIndex--;
                     if (rowIndex < 0)
                        rowIndex = 0;
                     displayPixelInformation(colIndex,rowIndex);
                  }
               });
   
   // handle next column button press
      colNextButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     colIndex++;
                     if (colIndex >= picture.getWidth())
                        colIndex = picture.getWidth() - 1;
                     displayPixelInformation(colIndex,rowIndex);
                  }
               });
   
   // handle next row button press
      rowNextButton.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     rowIndex++;
                     if (rowIndex >= picture.getHeight())
                        rowIndex = picture.getHeight() - 1;
                     displayPixelInformation(colIndex,rowIndex);
                  }
               });
   }

/**
* Create the pixel location panel
* @param labelFont the font for the labels
* @return the location panel
*/
   public JPanel createLocationPanel(Font labelFont) {
   
   // create a location panel
      JPanel locationPanel = new JPanel();
      locationPanel.setLayout(new FlowLayout());
      Box hBox = Box.createHorizontalBox();
   
   // create the labels
      rowLabel = new JLabel("Row:");
      colLabel = new JLabel("Column:");
   
   // create the text fields
      colValue = new JTextField(Integer.toString(colIndex + numberBase),6);
      colValue.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     displayPixelInformation(colValue.getText(),rowValue.getText());
                  }
               });
      rowValue = new JTextField(Integer.toString(rowIndex + numberBase),6);
      rowValue.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     displayPixelInformation(colValue.getText(),rowValue.getText());
                  }
               });
   
   // set up the next and previous buttons
      setUpNextAndPreviousButtons();
   
   // set up the font for the labels
      colLabel.setFont(labelFont);
      rowLabel.setFont(labelFont);
      colValue.setFont(labelFont);
      rowValue.setFont(labelFont);
   
   // add the items to the vertical box and the box to the panel
      hBox.add(Box.createHorizontalGlue());
      hBox.add(rowLabel);
      hBox.add(rowPrevButton);
      hBox.add(rowValue);
      hBox.add(rowNextButton);
      hBox.add(Box.createHorizontalStrut(10));
      hBox.add(colLabel);
      hBox.add(colPrevButton);
      hBox.add(colValue);
      hBox.add(colNextButton);
      locationPanel.add(hBox);
      hBox.add(Box.createHorizontalGlue());
   
      return locationPanel;
   }
	
/**
* Create the color information panel
* @param labelFont the font to use for labels
* @return the color information panel
*/
   private JPanel createColorInfoPanel(Font labelFont)
   {
   // create a color info panel
      JPanel colorInfoPanel = new JPanel();
      colorInfoPanel.setLayout(new FlowLayout());
   
   // get the pixel at the x and y
      Pixel pixel = new Pixel(picture,colIndex,rowIndex);
   
   // create the labels
      rValue = new JLabel("R: " + pixel.getRed()+ "   ");
      gValue = new JLabel("G: " + pixel.getGreen()+"   ");
      bValue = new JLabel("B: " + pixel.getBlue()+"     ");
   
   // create the sample color panel and label
      colorLabel = new JLabel("Color at location: ");
      colorPanel = new JPanel();
      colorPanel.setBorder(new LineBorder(Color.black,1));
   
   // set the color sample to the pixel color
      colorPanel.setBackground(pixel.getColor());
   
   // set the font
      rValue.setFont(labelFont);
      gValue.setFont(labelFont);
      bValue.setFont(labelFont);
      colorLabel.setFont(labelFont);
      colorPanel.setPreferredSize(new Dimension(25,25));
   
   // add items to the color information panel
      colorInfoPanel.add(rValue);
      colorInfoPanel.add(gValue);
      colorInfoPanel.add(bValue);
      colorInfoPanel.add(colorLabel);
      colorInfoPanel.add(colorPanel);
   
      return colorInfoPanel; 
   }
   private JLabel tara;
	
	 //tara added
   private JPanel userInfoPanel(Font labelFont)
   {
   // create a color info panel
      JPanel userInfoPanel = new JPanel();
      userInfoPanel.setLayout(new FlowLayout());
   	
      tara = new JLabel("hi");
      tara2=new JTextField(5);
      tara.setText("");
      userInfoPanel.add(tara);
      userInfoPanel.add(tara2);
      return userInfoPanel;
   }
/**
* Creates the North JPanel with all the pixel location
* and color information
*/
   private void createInfoPanel()
   {
   // create the info panel and set the layout
      JPanel infoPanel = new JPanel();
      infoPanel.setLayout(new BorderLayout());
   
   // create the font
      Font largerFont = new Font(infoPanel.getFont().getName(),
                            infoPanel.getFont().getStyle(),14);
   
   // create the pixel location panel
      JPanel locationPanel = createLocationPanel(largerFont);
   
   // create the color information panel
      JPanel colorInfoPanel = createColorInfoPanel(largerFont);
   	
   // create the color information panel
      JPanel userInfoPanel = userInfoPanel(largerFont);
   
   // add the panels to the info panel
      infoPanel.add(BorderLayout.NORTH,locationPanel);
      infoPanel.add(BorderLayout.SOUTH,colorInfoPanel); 
      infoPanel.add(BorderLayout.EAST, userInfoPanel);
   
   // add the info panel
      pictureFrame.getContentPane().add(BorderLayout.NORTH,infoPanel);
   } 

/**
* Method to check that the current position is in the viewing area and if
* not scroll to center the current position if possible
*/
   public void checkScroll()
   {
   // get the x and y position in pixels
      int xPos = (int) (colIndex * zoomFactor); 
      int yPos = (int) (rowIndex * zoomFactor); 
   
   // only do this if the image is larger than normal
      if (zoomFactor > 1) {
      
      // get the rectangle that defines the current view
         JViewport viewport = scrollPane.getViewport();
         Rectangle rect = viewport.getViewRect();
         int rectMinX = (int) rect.getX();
         int rectWidth = (int) rect.getWidth();
         int rectMaxX = rectMinX + rectWidth - 1;
         int rectMinY = (int) rect.getY();
         int rectHeight = (int) rect.getHeight();
         int rectMaxY = rectMinY + rectHeight - 1;
      
      // get the maximum possible x and y index
         int macolIndexX = (int) (picture.getWidth() * zoomFactor) - rectWidth - 1;
         int macolIndexY = (int) (picture.getHeight() * zoomFactor) - rectHeight - 1;
      
      // calculate how to position the current position in the middle of the viewing
      // area
         int viewX = xPos - (int) (rectWidth / 2);
         int viewY = yPos - (int) (rectHeight / 2);
      
      // reposition the viewX and viewY if outside allowed values
         if (viewX < 0)
            viewX = 0;
         else if (viewX > macolIndexX)
            viewX = macolIndexX;
         if (viewY < 0)
            viewY = 0;
         else if (viewY > macolIndexY)
            viewY = macolIndexY;
      
      // move the viewport upper left point
         viewport.scrollRectToVisible(new Rectangle(viewX,viewY,rectWidth,rectHeight));
      }
   }

/**
* Zooms in the on picture by scaling the image.  
* It is extremely memory intensive.
* @param factor the amount to zoom by
*/
   public void zoom(double factor)
   {
   // save the current zoom factor
      zoomFactor = factor;
   
   // calculate the new width and height and get an image that size
      int width = (int) (picture.getWidth()*zoomFactor);
      int height = (int) (picture.getHeight()*zoomFactor);
      BufferedImage bimg = picture.getBufferedImage();
   
   // set the scroll image icon to the new image
      imageDisplay.setImage(bimg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
      imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
      imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
      imageDisplay.revalidate();
      checkScroll();  // check if need to reposition scroll
   }

/**
* Repaints the image on the scrollpane.  
*/
   public void repaint()
   {
      pictureFrame.repaint();
   }

//****************************************//
//               Event Listeners          //
//****************************************//

/**
* Called when the mouse is dragged (button held down and moved)
* @param e the mouse event
*/
   public void mouseDragged(MouseEvent e)
   {
      displayPixelInformation(e);
   }

/**
* Method to check if the given x and y are in the picture
* @param column the horizontal value
* @param row the vertical value
* @return true if the row and column are in the picture 
* and false otherwise
*/
   private boolean isLocationInPicture(int column, int row)
   {
      boolean result = false; // the default is false
      if (column >= 0 && column < picture.getWidth() && row >= 0 && row < picture.getHeight())
         result = true;
      return result;
   }

/**
* Method to display the pixel information from the passed x and y but
* also converts x and y from strings
* @param xString the x value as a string from the user
* @param yString the y value as a string from the user
*/
   public void displayPixelInformation(String xString, String yString)
   {
      int x = -1;
      int y = -1;
      try {
         x = Integer.parseInt(xString);
         x = x - numberBase;
         y = Integer.parseInt(yString);
         y = y - numberBase;
      } 
      catch (Exception ex) {
      }
   
      if (x >= 0 && y >= 0) {
         displayPixelInformation(x,y);
      }
   }

 

/**
* Method to display pixel information for the passed x and y
* @param pictureX the x value in the picture
* @param pictureY the y value in the picture
*/
   private void displayPixelInformation(int pictureX, int pictureY)
   {
   // check that this x and y are in range
      if (isLocationInPicture(pictureX, pictureY))
      {
      // save the current x and y index
         colIndex = pictureX;
         rowIndex = pictureY;
      
      // get the pixel at the x and y
         Pixel pixel = new Pixel(picture,colIndex,rowIndex);
      
      // set the values based on the pixel
         colValue.setText(Integer.toString(colIndex  + numberBase));
         rowValue.setText(Integer.toString(rowIndex + numberBase));
         rValue.setText("R: " + pixel.getRed());
         gValue.setText("G: " + pixel.getGreen());
         bValue.setText("B: " + pixel.getBlue());
         colorPanel.setBackground(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
      } 
      else
      {
         clearInformation();
      }
   
   // notify the image display of the current x and y
      imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
      imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
   }

/**
* Method to display pixel information based on a mouse event
* @param e a mouse event
*/
   private void displayPixelInformation(MouseEvent e)
   {
   
   // get the cursor x and y
      int cursorX = e.getX();
      int cursorY = e.getY();
   
   // get the x and y in the original (not scaled image)
      int pictureX = (int) (cursorX / zoomFactor + numberBase);
      int pictureY = (int) (cursorY / zoomFactor + numberBase);
   
   // display the information for this x and y
      displayPixelInformation(pictureX,pictureY);
   }

/**
* Method to clear the labels and current color and reset the 
* current index to -1
*/
   private void clearInformation()
   {
      colValue.setText("N/A");
      rowValue.setText("N/A");
      rValue.setText("R: N/A");
      gValue.setText("G: N/A");
      bValue.setText("B: N/A");
      colorPanel.setBackground(Color.black);
      colIndex = -1;
      rowIndex = -1;
   }

   private void cropper()
   {  
         BufferedImage bimg = picture.getBufferedImage();  
         mouseX =(int)(x_Upper_Left/zoomFactor + numberBase);
         mouseY =(int)(y_Upper_Left/zoomFactor + numberBase);
         mouseX1 =(int)(x_Lower_Right/zoomFactor + numberBase);
         mouseY1 =(int)(y_Lower_Right/zoomFactor + numberBase);
      
         int width = mouseX1 - mouseX;
         int height = mouseY1 - mouseY;
      
         BufferedImage subImage = bimg.getSubimage(x_Upper_Left, y_Upper_Left,width,height);
         Helper help = new Helper(subImage);
         help.process();
        help.outputImage();
      
       
      
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
      displayPixelInformation(e);
   }

/**
* Method called when the mouse button is pushed down
* @param e the mouse event
*/ 
   public void mousePressed(MouseEvent e)
   {
      displayPixelInformation(e);
      mouseX = x_Upper_Left = e.getX();
      mouseY = y_Upper_Left = e.getY();
   
   }
   
/**
* Method called when the mouse button is released
* @param e the mouse event
*/
   public void mouseReleased(MouseEvent e)
   {
      mouseX1 = x_Lower_Right = e.getX();
      mouseY1 = y_Lower_Right = e.getY();
      cropper();
   }
   
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

/**
* Method to enable all menu commands
*/
   private void enableZoomItems()
   {
      twentyFive.setEnabled(true);
      fifty.setEnabled(true);
      seventyFive.setEnabled(true);
      hundred.setEnabled(true);
      hundredFifty.setEnabled(true);
      twoHundred.setEnabled(true);
      fiveHundred.setEnabled(true);
   }

/**
* Controls the zoom menu bar
*
* @param a the ActionEvent 
*/
   public void actionPerformed(ActionEvent a)
   {
      if(a.getActionCommand().equals("Update"))
      {
         this.repaint();
      }
      if(a.getActionCommand().equals("25%"))
      {
         this.zoom(.25);
         enableZoomItems();
         twentyFive.setEnabled(false);
      }
      if(a.getActionCommand().equals("50%"))
      {
         this.zoom(.50);
         enableZoomItems();
         fifty.setEnabled(false);
      }
      if(a.getActionCommand().equals("75%"))
      {
         this.zoom(.75);
         enableZoomItems();
         seventyFive.setEnabled(false);
      }
      if(a.getActionCommand().equals("100%"))
      {
         this.zoom(1.0);
         enableZoomItems();
         hundred.setEnabled(false);
      }
      if(a.getActionCommand().equals("150%"))
      {
         this.zoom(1.5);
         enableZoomItems();
         hundredFifty.setEnabled(false);
      }
      if(a.getActionCommand().equals("200%"))
      {
         this.zoom(2.0);
         enableZoomItems();
         twoHundred.setEnabled(false);
      }
      if(a.getActionCommand().equals("500%"))
      {
         this.zoom(5.0);
         enableZoomItems();
         fiveHundred.setEnabled(false);
      }
   }

/**
* Class for establishing the focus for the textfields
*/
   private class PictureExplorerFocusTraversalPolicy extends FocusTraversalPolicy 
   {
   /**
   * Method to get the next component for focus
   */
      public Component getComponentAfter(Container focusCycleRoot,
                                    Component aComponent) {
         if (aComponent.equals(colValue))
            return rowValue;
         else 
            return colValue;
      }
   
   /**
   * Method to get the previous component for focus
   */
      public Component getComponentBefore(Container focusCycleRoot,Component aComponent) 
      {
         if (aComponent.equals(colValue))
            return rowValue;
         else 
            return colValue;
      }
   
      public Component getDefaultComponent(Container focusCycleRoot) 
      {
         return colValue;
      }
   
      public Component getLastComponent(Container focusCycleRoot) 
      {
         return rowValue;
      }
   
      public Component getFirstComponent(Container focusCycleRoot) 
      {
         return colValue;
      }
   }

   /*****************************************************************  
	*   Listeners  
	*******************************************************************/	
   private class Listener_ModRed implements ActionListener
   {	      
      public void actionPerformed(ActionEvent e)
      {
         tara2.addActionListener(new Listener_ModRed_TextField());
         tara.setText("Enter red % change (0-200): ");
      }
   }

   private class Listener_ModRed_TextField implements ActionListener
   {
      public void actionPerformed (ActionEvent e)
      {
         int percentChanged = Integer.parseInt(tara2.getText());
         ((Picture)picture).modifyRed(percentChanged);
         updatePicture();
      }
   }		
	 	
   private class Listener_ModBlue implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         tara2.addActionListener(new Listener_ModBlue_TextField());
         tara.setText("Enter Blue % change (0-200): ");         
      }
   }

   private class Listener_ModBlue_TextField implements ActionListener
   {
      public void actionPerformed (ActionEvent e)
      {
         int percentChanged = Integer.parseInt(tara2.getText());
         ((Picture)picture).modifyBlue(percentChanged);
         updatePicture();      
      }
   }	

   private class Listener_ModGreen implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         tara2.addActionListener(new Listener_ModGreen_TextField());
         tara.setText("Enter Green % change (0-200): ");            
      }
   }
   private class Listener_ModGreen_TextField implements ActionListener
   {
      public void actionPerformed (ActionEvent e)
      {
         int percentChanged = Integer.parseInt(tara2.getText());
         ((Picture)picture).modifyGreen(percentChanged);
         updatePicture();            
      }
   }
   private class Listener_Zero implements ActionListener
   {
      public void actionPerformed (ActionEvent e)
      {
         String s = JOptionPane.showInputDialog("Zero Which Color (all lower case): ");
         if(s!=null)
         {
            if(s.equals("green"))
               ((Picture)picture).zeroGreen();
            else if(s.equals("red"))
               ((Picture)picture).zeroRed();
            else if(s.equals("blue"))
               ((Picture)picture).zeroBlue();
            else
               System.out.println("Sorry, that isnt one of the primary colors");
         }
         else
            System.out.println("Sorry, that isnt one of the primary colors");
         updatePicture();
      }	  		 
   }
	
   private class Listener_KeepOnly implements ActionListener
   {
      public void actionPerformed (ActionEvent e)
      {
         String s = JOptionPane.showInputDialog("Only Keep Which Color (all lower case): ");
         if(s!=null)
         {
            if(s.equals("green"))
               ((Picture)picture).keepOnlyGreen();
            else if(s.equals("red"))
               ((Picture)picture).keepOnlyRed();
            else if(s.equals("blue"))
               ((Picture)picture).keepOnlyBlue();
            else
               System.out.println("Sorry, that isnt one of the primary colors");
         }
         else
            System.out.println("Sorry, that isnt one of the primary colors");
         updatePicture();			
      }
   } 

   private class Listener_Posterize implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).posterize();
         updatePicture();
      }
   }	
	
   private class Listener_SepiaTone implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).sepiatone();
         updatePicture();
      }
   }
	
   private class Listener_Negate implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).negate();
         updatePicture();
      }
   }	
   private class Listener_GrayScale implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).grayscale();
         updatePicture();					 
      }
   }
   private class Listener_Blur implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).blur();
         updatePicture();
      }
   }
   private class Listener_GreenScreen implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).greenScreen();
         updatePicture();
      
      }
   }

   private class Listener_RainbowBar implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).rainbowBar();
         updatePicture();
      }
   }
    	
   private class Listener_ColorSplash implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      { 
         Color c = new Color (254,0,0);
         ((Picture)picture).colorSplash(c);
         updatePicture();
      }
   }
	
   private class Listener_MirrorLR implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).mirrorVertical();
         updatePicture(); 
      }
   }
   private class Listener_MirrorTB implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).mirrorHorizontal();
         updatePicture(); 
      }
   }
   private class Listener_MirrorDiag implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).mirrorDiagonal();
         updatePicture(); 
      }
   }
   private class Listener_Pixelate implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).pixelate();
         updatePicture();
      }
   }
   private class Listener_EdgeDetector implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).edgeDetection(10);
         updatePicture(); 
      }
   }
   private class Listener_Encode implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         Picture messagePict = new Picture("msg.jpg");
         
      }
   }

   private class Listener_Decode implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         
      }
   }  
  
   private class Listener_goTo implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         ((Picture)picture).goTo();
      }
   }  
   
   private class Listener_readText implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
    
      }
   }  
   
/**
* Test Main.  
*/
   public static void main( String args[])
   {
      //Picture pix = new Picture(); // rg - this doesn't open a picture
      Picture pix = new Picture("640x480.jpg"); //- rg this explores the 640x480 in the images directory
      pix.explore();
   }
}
