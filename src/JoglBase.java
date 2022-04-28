/**
 *
 * @author gmendez
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;        
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.*;  // GL constants
import static com.jogamp.opengl.GL2.*; // GL2 constants
import static com.jogamp.opengl.GL2ES3.GL_QUADS;

/**
 * JoglBase Programa Plantilla (GLCanvas)
 */
@SuppressWarnings("serial")
public class JoglBase extends GLCanvas implements GLEventListener, KeyListener  {
   // Define constants for the top-level container
   private static String TITLE = "Plantilla Base java open gl";  // window's title
   private static final int CANVAS_WIDTH = 640;  // width of the drawable
   private static final int CANVAS_HEIGHT = 480; // height of the drawable
   private static final int FPS = 24; // animator's target frames per second
   private static final float factInc = 5.0f; // animator's target frames per second
   private final float fovy = 45.0f;
   
   private final GLU glu;  // for the GL Utility
   private final GLUT glut;
   
   float rotacion=0.0f;
   float despl=0.0f;
   float despX=0.0f;
   float despY=0.0f;
   float despZ=0.0f;
   
   float camX=2.0f;
   float camY=2.0f;
   float camZ=8.0f;
    
   /** The entry main() method to setup the top-level container and animator */
   public static void main(String[] args) {
      // Run the GUI codes in the event-dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            // Create the OpenGL rendering canvas
            GLCanvas canvas = new JoglBase();
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
            // Create a animator that drives canvas' display() at the specified FPS.
            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
 
            // Create the top-level container
            final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
            JPanel panel1 = new JPanel();
            
            FlowLayout fl = new FlowLayout();
            frame.setLayout(fl);
            
            panel1.add(canvas);
            frame.getContentPane().add(panel1);
            
            frame.addKeyListener((KeyListener) canvas);
            
            frame.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                  // Use a dedicate thread to run the stop() to ensure that the
                  // animator stops before program exits.
                  new Thread() {
                     @Override
                     public void run() {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                     }
                  }.start();
               }
            });
            
            frame.addComponentListener(new ComponentAdapter(){
                    public void componentResized(ComponentEvent ev) {
                            Component c = (Component)ev.getSource();
                            // Get new size
                            Dimension newSize = c.getSize();                            
                            panel1.setSize(newSize);                                                        
                            canvas.setSize(newSize);                            
                    }   
            });
                        
            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop
         }
      });
   }
 
   /** Constructor to setup the GUI for this Component */
   public JoglBase() {
      this.addGLEventListener(this);
      this.addKeyListener(this);
      
      glu = new GLU();                        // get GL Utilities
      glut = new GLUT();
   }
 
   // ------ Implement methods declared in GLEventListener ------
 
   /**
    * Called back immediately after the OpenGL context is initialized. Can be used
    * to perform one-time initialization. Run only once.
    */
   @Override
   public void init(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();     // get the OpenGL graphics context
      
      gl.glClearColor(0.96f, 0.156f, 0.56f, 0.8f); // set background (clear) color
      
      
      gl.glClearDepth(1.0f);      // set clear depth value to farthest
      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do  
      
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity();  // reset the model-view matrix
   }
 
   /**
    * Call-back handler for window re-size event. Also called when the drawable is
    * first set to visible.
    */
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
 
      if (height == 0) height = 1;   // prevent divide by zero
      float aspect = (float)width / height;
 
      // Set the view port (display area) to cover the entire window
      gl.glViewport(0, 0, width, height);

      // Setup perspective projection, with aspect ratio matches viewport
      gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
      gl.glLoadIdentity();             // reset projection matrix
      glu.gluPerspective(fovy, aspect, 0.1, 20.0); // fovy, aspect, zNear, zFar
      
      glu.gluLookAt(this.camX, this.camY, this.camZ, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0); 
   }
 
   /**
    * Called back by the animator to perform rendering.
    */
   @Override
   public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();                
        
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();  // reset the model-view matrix
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);                 
        
        gl.glColor3f(0.0f,0.0f,1.0f);
        
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(-100.0f,0.0f,0.0f);
            gl.glVertex3f(100.0f,0.0f,0.0f);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(0.0f,-100.0f,0.0f);
            gl.glVertex3f(0.0f,100.0f,0.0f);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(0.0f,0.0f,-100.0f);
            gl.glVertex3f(0.0f,0.0f,100.0f);
        gl.glEnd();
        
        gl.glLoadIdentity();
        gl.glRotatef(this.rotacion, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(this.despX,this.despY,this.despZ);        
        
                
        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);            
            gl.glVertex3f(1.0f, 0.0f, 0.0f);            
            gl.glVertex3f(1.0f, 0.0f, -1.0f);
            gl.glVertex3f(0.0f, 0.0f, -1.0f);  
        gl.glEnd();        

        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 1.0f, 0.0f);
            gl.glVertex3f(0.0f, 0.0f, -1.0f);            
            gl.glVertex3f(1.0f, 0.0f, -1.0f);            
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            gl.glVertex3f(0.0f, 1.0f, -1.0f);  
        gl.glEnd();  

        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);            
            gl.glVertex3f(0.0f, 1.0f, 0.0f);            
            gl.glVertex3f(0.0f, 1.0f, -1.0f);
            gl.glVertex3f(0.0f, 0.0f, -1.0f);  
        gl.glEnd();

        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            gl.glVertex3f(1.0f, 0.0f, 0.0f);            
            gl.glVertex3f(1.0f, 1.0f, 0.0f);            
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            gl.glVertex3f(1.0f, 0.0f, -1.0f);  
        gl.glEnd();     
        
        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(0.0f, 1.0f, 0.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);            
            gl.glVertex3f(1.0f, 0.0f, 0.0f);            
            gl.glVertex3f(1.0f, 1.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.0f, 0.0f);  
        gl.glEnd(); 
        
        gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.0f, 0.0f);            
            gl.glVertex3f(1.0f, 1.0f, 0.0f);            
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            gl.glVertex3f(0.0f, 1.0f, -1.0f);  
        gl.glEnd();         
                        
        this.rotacion+=5.0f;
        if (this.rotacion>360){
            this.rotacion = 0;
        }
        
        // System.out.printf("Rotacion %f \n", this.rotacion);
        
            
        gl.glFlush();
            
   }   

   /**
    * Called back before the OpenGL context is destroyed. Release resource such as buffers.
    */
   @Override
   public void dispose(GLAutoDrawable drawable) { }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();
        
        System.out.println("codigo presionado = "+codigo);
        
        switch (codigo){                     
            case KeyEvent.VK_LEFT:
                 this.despX-=0.2f;
                 break;
            case KeyEvent.VK_RIGHT:
                 this.despX+=0.2f;
                 break;  
            case KeyEvent.VK_DOWN:    
                 this.despZ+=0.2f;
                 break;                 
            case KeyEvent.VK_UP:
                 this.despZ-=0.2f;
                 break;
            case KeyEvent.VK_PAGE_UP:
                 this.despY+=0.2f;
                 break;
            case KeyEvent.VK_PAGE_DOWN:
                 this.despY-=0.2f;
                 break;
            case KeyEvent.VK_R:
                 this.rotacion+=5.0f;
                 break;
            case KeyEvent.VK_NUMPAD8:
                 this.camY+=0.2f;
                 break;
            case KeyEvent.VK_NUMPAD2:
                 this.camY-=0.2f;
                 break;
            case KeyEvent.VK_NUMPAD6:
                 this.camX+=0.2f;
                 break;
            case KeyEvent.VK_NUMPAD4:
                 this.camX-=0.2f;
                 break;                 
            case KeyEvent.VK_PLUS:
                 this.camZ+=0.2f;
                 break;                 
            case KeyEvent.VK_MINUS:
                 this.camZ-=0.2f;
                 break;   
                 
        }
        System.out.println("despX ="+this.despX+" - "+"despY ="+this.despY+" - "+"despZ ="+this.despZ); 
        System.out.println("camX ="+this.camX+" - "+"camY ="+this.camY+" - "+"despZ ="+this.despZ); 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }



}
