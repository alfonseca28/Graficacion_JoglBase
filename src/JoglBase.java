/**
 *
 * @author gmendez
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
public class JoglBase extends GLCanvas implements GLEventListener {
   // Define constants for the top-level container
   private static String TITLE = "Plantilla Base java open gl";  // window's title
   private static final int CANVAS_WIDTH = 640;  // width of the drawable
   private static final int CANVAS_HEIGHT = 480; // height of the drawable
   private static final int FPS = 60; // animator's target frames per second
   private static final float factInc = 5.0f; // animator's target frames per second
   private float fovy = 45.0f;
   
   private GLU glu;  // for the GL Utility
   private GLUT glut;
    
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
            JPanel panel2 = new JPanel();
            
            FlowLayout fl = new FlowLayout();
            frame.setLayout(fl);
            
            panel1.add(canvas);
            frame.getContentPane().add(panel1);
            frame.getContentPane().add(panel2);            
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
   }
 
   // ------ Implement methods declared in GLEventListener ------
 
   /**
    * Called back immediately after the OpenGL context is initialized. Can be used
    * to perform one-time initialization. Run only once.
    */
   @Override
   public void init(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
      glu = new GLU();                        // get GL Utilities
      glut = new GLUT();
      gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // set background (clear) color
      gl.glClearDepth(1.0f);      // set clear depth value to farthest
      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
      gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting  
      
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
      glu.gluPerspective(fovy, aspect, 0.1, 50.0); // fovy, aspect, zNear, zFar
 
      // Enable the model-view transform
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity(); // reset
   }
 
   /**
    * Called back by the animator to perform rendering.
    */
   @Override
   public void display(GLAutoDrawable drawable) {
       
        float aspect = (float)this.getWidth() / this.getHeight();
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers

        float[] lightPos = { 0.0f,5.0f,10.0f,1 };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,lightPos, 0);

        gl.glLoadIdentity();  // reset the model-view matrix
        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);

        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);

        glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
              
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        
        // Aqui inicia el dibujo de objetos
      
        gl.glFlush();
      
   }

    void drawCube(GL2 gl){     

        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Left
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Right
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Right
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Left
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Right
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Right
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Left
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Left
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);	// Bottom Left
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);	// Bottom Right
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);	// Top Right
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);	// Top Left
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Right
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Left
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Left
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Right
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left
        gl.glEnd();
    }
   
    void drawPyramid(float x,float y,float z,float w,float h,GL2 gl){
      float mw=w/2;
      float mh=h/2;
      float xi,xd,ys,yi,zf,zp;
      
      xi=x-mw;
      xd=x+mw;
              
      yi=y-mh;
      ys=y+mh;
              
      zf=z+mw;
      zp=z-mw;              
                   
      gl.glBegin(GL_TRIANGLES); // draw using triangles
         gl.glColor3f(1.0f,0,0);
         gl.glVertex3f(xi, yi, zf);
         gl.glColor3f(0,1.0f,0);
         gl.glVertex3f(x, ys, z);                 
         gl.glColor3f(0,0,1.0f);
         gl.glVertex3f(xd, yi, zf);
      gl.glEnd();
      
      gl.glBegin(GL_TRIANGLES); // draw using triangles
         gl.glColor3f(0.0f,0,1.0f);
         gl.glVertex3f(xd,yi,zf);
         gl.glColor3f(0,1.0f,0);
         gl.glVertex3f(x, ys, z);
         gl.glColor3f(1.0f,0,0);
         gl.glVertex3f(xd, yi,zp);
      gl.glEnd();
      gl.glBegin(GL_TRIANGLES); // draw using triangles
         gl.glColor3f(1.0f,0.0f,0.0f);
         gl.glVertex3f(xd, yi, zp);
         gl.glColor3f(0,1.0f,0);
         gl.glVertex3f(x, ys,z);
         gl.glColor3f(0.0f,0,1.0f);
         gl.glVertex3f(xi, yi, zp);
      gl.glEnd();
      gl.glBegin(GL_TRIANGLES); // draw using triangles
         gl.glColor3f(0.0f,0,1.0f);
         gl.glVertex3f(xi,yi, zp);
         gl.glColor3f(0,1.0f,0);
         gl.glVertex3f(x, ys, z);
         gl.glColor3f(1.0f,0,0.0f);
         gl.glVertex3f(xi, yi, zf);
      gl.glEnd();
      gl.glBegin(GL_QUADS ); // draw using triangles
         gl.glColor3f(1.0f,0,0);
         gl.glVertex3f(xi, yi, zf);
         gl.glColor3f(0,0.0f,1.0f);
         gl.glVertex3f(xd, yi, zf);
         gl.glColor3f(1.0f,0,0.0f);
         gl.glVertex3f(xd, yi, zp);
         gl.glColor3f(0.0f,0.0f,1.0f);
         gl.glVertex3f(xi, yi, zp);
      gl.glEnd();          
   }

   /**
    * Called back before the OpenGL context is destroyed. Release resource such as buffers.
    */
   @Override
   public void dispose(GLAutoDrawable drawable) { }


}