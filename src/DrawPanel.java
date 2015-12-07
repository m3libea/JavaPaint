import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/**
 * Created by m3libea on 5/12/15.
 */
public class DrawPanel extends JComponent
{
    //Image where we are painting
    private Image image;
    private Graphics2D graphics;

    //coordinates old/new
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    //color of the brush
    private Color color;

    //width of the brush
    private float lineSize;

    //Draw, Delete, Filled
    private enum DrawAction
    {
        DRAW,DELETE, FILL
    }

    //Control Shape
    public enum ShapeChoice
    {
        DEFAULT, LINE, CIRCLE, RECTANGLE
    }

    //Action and shape
    private DrawAction da;
    private ShapeChoice shapeChoice;

    public DrawPanel(){

        color = Color.BLACK;
        lineSize = 1;
        shapeChoice = ShapeChoice.DEFAULT;
        setSize(getSize().width,getSize().height);
        da = DrawAction.DRAW;

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                //Store coordinates where first pressed
                oldX = e.getX();
                oldY = e.getY();

                //Draw
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    da = DrawAction.DRAW;
                }
                else
                {
                    //Delete or Fill mode
                    if (shapeChoice == ShapeChoice.DEFAULT)
                    {
                        da = DrawAction.DELETE;
                    }
                    else
                    {
                        da = DrawAction.FILL;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //Options when shape selected
                if(shapeChoice != ShapeChoice.DEFAULT)
                {
                    newX = e.getX();
                    newY = e.getY();

                    graphics.setColor(color);
                    graphics.setStroke(new BasicStroke(lineSize));

                    //Line selected
                    if(shapeChoice == ShapeChoice.LINE)
                    {
                        graphics.drawLine(oldX, oldY, newX, newY);
                        DrawingBoard.linebtn.setForeground(Color.BLACK);
                    }

                    //Rectangle selected
                    if(shapeChoice == ShapeChoice.RECTANGLE)
                    {
                        int width = Math.abs(newX-oldX);
                        int height = Math.abs(newY-oldY);

                        //Draw
                        if(da == DrawAction.DRAW)
                        {
                            graphics.drawRect(oldX,oldY,width,height);
                        }
                        //Filled option
                        else
                        {
                            graphics.fillRect(oldX, oldY, width, height);
                        }
                        DrawingBoard.rectanglebtn.setForeground(Color.BLACK);
                    }

                    //Circle selected
                    if(shapeChoice == ShapeChoice.CIRCLE)
                    {
                        int radius = Math.abs(newX - oldX);
                        //Draw
                        if(da == DrawAction.DRAW)
                        {
                            graphics.drawOval(oldX, oldY, radius, radius);
                        }
                        //Filled option
                        else
                        {
                            graphics.fillOval(oldX, oldY, radius, radius);
                        }
                        DrawingBoard.circlebtn.setForeground(Color.BLACK);
                    }

                    repaint();
                    shapeChoice = ShapeChoice.DEFAULT;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                //Only in case that no shape selected
                if(shapeChoice == ShapeChoice.DEFAULT)
                {
                    newX = e.getX();
                    newY = e.getY();
                    if (graphics != null)
                    {
                        //Color depends of draw or delete.
                        if (da == DrawAction.DRAW)
                        {
                            graphics.setColor(color);
                        } else {
                            graphics.setColor(Color.white);
                        }
                        //Set stroke and draw line. Then repaint
                        graphics.setStroke(new BasicStroke(lineSize));
                        graphics.drawLine(oldX, oldY, newX, newY);
                        repaint();
                        oldX = newX;
                        oldY = newY;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if(image==null)
        {
            //create image we are going to use
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public  void clear()
    {
        //Clear the DrawPanel
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setColor(color);
        repaint();
    }

    public void changeColor(Color c)
    {
        color = c;
    }
    public Color getColor()
    {
        return color;
    }
    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
        //Set image that we load
        graphics = ((BufferedImage)image).createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        repaint();
    }

    public void setLineSize(float lineSize)
    {
        this.lineSize = lineSize;
    }

    public float getLineSize()
    {
        return lineSize;
    }

    public void setShapeChoice(ShapeChoice shapeChoice)
    {
        this.shapeChoice = shapeChoice;
    }

}
