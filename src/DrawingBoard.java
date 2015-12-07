
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by m3libea on 5/12/15.
 */
public class DrawingBoard extends JFrame
{
    //Panels
    private Container content;
    private JPanel buttonsNorth;
    private JPanel buttonsSouth;
    private DrawPanel drawPanel;

    //North
    public static   JButton rectanglebtn;
    public static JButton circlebtn;
    public static JButton linebtn;

    //South
    private JButton savebtn;
    private JButton loadbtn;
    private JButton colorbtn;
    private JButton exitbtn;
    private JButton brushbtn;
    private JButton clearbtn;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new DrawingBoard());
    }

    public DrawingBoard()
    {
        setTitle("Drawing Board");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildPanel();
        setVisible(true);
    }

    public void buildPanel()
    {
        content = getContentPane();
        content.setLayout(new BorderLayout());

        createButtons();
        content.add(buttonsSouth, BorderLayout.SOUTH);
        content.add(buttonsNorth, BorderLayout.NORTH);

        drawPanel = new DrawPanel();
        content.add(drawPanel, BorderLayout.CENTER);
    }

    public void createButtons()
    {
        JLabel info = new JLabel();
        //Create north panel
        buttonsNorth = new JPanel();
        buttonsNorth.setBackground(Color.black);

        //Create rectangle button
        rectanglebtn = new JButton("Rectangle");
        rectanglebtn.setToolTipText("Draw a rectangle");
        rectanglebtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPanel.setShapeChoice(DrawPanel.ShapeChoice.RECTANGLE);
                rectanglebtn.setForeground(Color.RED);
            }
        });
        //Create circle button
        circlebtn = new JButton("Circle");
        circlebtn.setToolTipText("Draw a circle");
        circlebtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPanel.setShapeChoice(DrawPanel.ShapeChoice.CIRCLE);
                circlebtn.setForeground(Color.RED);
            }
        });
        //Create Line Button
        linebtn = new JButton("Line");
        linebtn.setToolTipText("Draw a line");
        linebtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPanel.setShapeChoice(DrawPanel.ShapeChoice.LINE);
                linebtn.setForeground(Color.RED);
            }
        });

        buttonsNorth.add(rectanglebtn);
        buttonsNorth.add(circlebtn);
        buttonsNorth.add(linebtn);


        //Create South Panel
        buttonsSouth = new JPanel();
        buttonsSouth.setBackground(Color.black);

        //Create Save button and set Action Listener
        savebtn = new JButton("Save");
        savebtn.setToolTipText("Save your draw");
        savebtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Open FileChooser Dialog
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION)
                {
                    //Get file and save it
                    File f = jfc.getSelectedFile();
                    try
                    {
                        ImageIO.write((BufferedImage) drawPanel.getImage(), "png", f);
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Create Load Button
        loadbtn = new JButton("Load");
        loadbtn.setToolTipText("Load a draw");
        loadbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION)
                {
                    File f = jfc.getSelectedFile();
                    if(f == null || f.getName().equals(""))
                    {
                       System.err.println("File not Found");
                    }
                    else
                    {
                        try
                        {
                            //Read image from firle
                            BufferedImage image = ImageIO.read(f.getAbsoluteFile());
                            //Set image on component
                            drawPanel.setImage(image);
                        }
                        catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        //Create Color Button
        colorbtn = new JButton("Color");
        colorbtn.setToolTipText("Choose the color to draw");
        colorbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Show Color Chooser Dialog and get the color from user
                Color brushColor = JColorChooser.showDialog(null, "Choose a color", drawPanel.getColor());
                drawPanel.changeColor(brushColor);
            }
        });

        //Create Exit Button
        exitbtn = new JButton("Exit");
        exitbtn.setToolTipText("Click to close the program");
        exitbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });


        //Create Brush Button
        brushbtn = new JButton("Brush");
        brushbtn.setToolTipText("Change the thickness of the line");
        brushbtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Get thickness of the line from user. If not a number, show an error message
                try{
                    float input = Float.parseFloat(JOptionPane.showInputDialog(null, "Thickness for the line. Introduce a Number"));
                    drawPanel.setLineSize(input);
                }
                catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "That is not a number");
                }
            }
        });

        //Create Clear Button
        clearbtn = new JButton("Clear");
        clearbtn.setToolTipText("Empty the screen");
        clearbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clear();
            }
        });

        //Add buttons to panel
        buttonsSouth.add(savebtn);
        buttonsSouth.add(loadbtn);
        buttonsSouth.add(colorbtn);
        buttonsSouth.add(brushbtn);
        buttonsSouth.add(clearbtn);
        buttonsSouth.add(exitbtn);
    }
}
