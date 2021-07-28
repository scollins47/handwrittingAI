//    import javax.swing.*;
//    import java.awt.*;
//    import java.awt.event.*;
//    
//    public class MenuPanel extends JPanel
//    {
//       //private Display display;
//    	//private JLabel label;
//       
//       public MenuPanel()
//       {
//          setLayout(new BorderLayout());
//       	//display = new Display();
//          //add(display, BorderLayout.CENTER );
//       	         
//          JPanel sub_panel = new JPanel();
//          sub_panel.setLayout(new GridLayout(14, 1));
//       	
//          JButton button_modRed = new JButton("Modify Red");
//       	button_modRed.addActionListener(new Listener_ModRed());
//          sub_panel.add(button_modRed);
//       	
//          JButton button_modBlue = new JButton("Modify Blue");
//          button_modBlue.addActionListener(new Listener_ModBlue());
//          sub_panel.add(button_modBlue);
//       	
//          JButton button_modGreen = new JButton("Modify Green");
//          button_modGreen.addActionListener(new Listener_ModGreen());
//          sub_panel.add(button_modGreen);
//       	
//          JButton button_grayScale = new JButton("Grayscale");
//          button_grayScale.addActionListener(new Listener_GrayScale());
//          sub_panel.add(button_grayScale);
//       	
//          JButton button_sepiaTone = new JButton("Sepia Tone");
//          button_sepiaTone.addActionListener(new Listener_SepiaTone());
//          sub_panel.add(button_sepiaTone);
//       	
//       	//---------
//          JButton button_sunsetize = new JButton("Sunsetize");
//          button_sunsetize.addActionListener(new Listener_Sunsetize());
//          sub_panel.add(button_sunsetize);
//       	
//          JButton button_posterize = new JButton("Posterize");
//          button_posterize.addActionListener(new Listener_Posterize());
//          sub_panel.add(button_posterize);
//       	
//          JButton button_colorSplash = new JButton("Color Splash");
//          button_colorSplash.addActionListener(new Listener_ColorSplash());
//          sub_panel.add(button_colorSplash);
//       	
//          JButton button_mirrorLR = new JButton("Mirror L/R");
//          button_mirrorLR.addActionListener(new Listener_MirrorLR());
//          sub_panel.add(button_mirrorLR);
//       	
//          JButton button_mirrorTB = new JButton("Mirror T/B");
//          button_mirrorTB.addActionListener(new Listener_MirrorTB());
//          sub_panel.add(button_mirrorTB);
//       	
//          JButton button_mirrorDiagonal = new JButton("Mirror Diagonal");
//          button_mirrorDiagonal.addActionListener(new Listener_MirrorDiagonal());
//          sub_panel.add(button_mirrorDiagonal);
// 			
// 			JButton button_edgeDetector = new JButton("Edge Detector");
//          button_edgeDetector.addActionListener(new Listener_EdgeDetector());
//          sub_panel.add(button_edgeDetector);
//       	
//          JButton button_encode = new JButton("Encode");
//          button_encode.addActionListener(new Listener_Encode());
//          sub_panel.add(button_encode);
//       	
//          JButton button_decode = new JButton("Decode");
//          button_decode.addActionListener(new Listener_Decode());
//          sub_panel.add(button_decode);
//       
//          add(sub_panel, BorderLayout.EAST);
//       }
//    	
//    	
//       private class Listener_ModRed implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             
// 				
//          }
//       }
//       private class Listener_ModBlue implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
// 				Picture beach = new Picture("beach.jpg");
//     			//beach.explore();
//             beach.modifyBlue(30);
// 				beach.explore();
// 				//beach.repaint();
// 			}
//       }
//       private class Listener_ModGreen implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//            
// 
//          }
//       }
//       private class Listener_GrayScale implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             Picture beach = new Picture("beach.jpg");
//     			//beach.explore();
// 
//             beach.grayscale();
// 				beach.explore();
//          }
//       }
//       private class Listener_SepiaTone implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//       private class Listener_Sunsetize implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//       private class Listener_Posterize implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//    	
//       private class Listener_ColorSplash implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//    	
//       private class Listener_MirrorLR implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//       private class Listener_MirrorTB implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//       private class Listener_MirrorDiagonal implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
// 		private class Listener_EdgeDetector implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//       private class Listener_Encode implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//    
//       private class Listener_Decode implements ActionListener
//       {
//          public void actionPerformed(ActionEvent e)
//          {
//             //label.setText( "Attempts: "+ display.update() );
//          }
//       }
//    
//    
//    
//    
//    
//    }