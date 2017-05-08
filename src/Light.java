import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;        
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

class Light {

  //////////////// Variables /////////////////////////

  // Light position.
  float lightX=1f;
  float lightY=1f;
  float lightZ=1f;
  float dLight=0.05f;

  // Material and light from the red book.
  final float ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
  final float position[] = { 0.0f, 0.0f, 1.0f, 1.0f };
  final float mat_diffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };
  final float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
  final float mat_shininess[] = { 50.0f };

  final float[] colorBlack  = {0.0f,0.0f,0.0f,1.0f};
  final float[] colorWhite  = {1.0f,1.0f,1.0f,1.0f};
  final float[] colorGray   = {0.4f,0.4f,0.4f,1.0f};
  final float[] colorDarkGray = {0.2f,0.2f,0.2f,1.0f};
  final float[] colorRed    = {1.0f,0.0f,0.0f,1.0f};
  final float[] colorGreen  = {0.0f,1.0f,0.0f,1.0f};
  final float[] colorBlue   = {0.0f,0.0f,0.6f,1.0f};
  final float[] colorYellow = {1.0f,1.0f,0.0f,1.0f};
  final float[] colorLightYellow = {.5f,.5f,0.0f,1.0f};


  ///////////////// Funciones /////////////////////////

  public Light() {}

 /////////////// Define Luz y Material /////////

  public void init( GLAutoDrawable drawable )
    {
      GL2 gl = drawable.getGL().getGL2();
      // Establece un material por default.
      setSomeWhiteMaterial( gl, GL.GL_FRONT_AND_BACK );

      // Alguna luz de ambiente global.
      //float lmodel_ambient[] = { 0.3f, 0.3f, 0.3f, 1.0f };
      float lmodel_ambient[] = { 0f, 0f, 0f, 1.0f };

      gl.glLightModelfv( GL2.GL_LIGHT_MODEL_AMBIENT, 
			 lmodel_ambient, 0 );

      gl.glLightModeli( GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 
			//GL.GL_TRUE );
                        GL.GL_FALSE );

      gl.glLightModeli( GL2.GL_LIGHT_MODEL_TWO_SIDE, 
			//GL.GL_TRUE );
			GL.GL_FALSE );
      
      // First Switch the lights on.
      gl.glEnable( GL2.GL_LIGHTING );
      //gl.glDisable( GL.GL_LIGHTING );
      //gl.glEnsable( GL.GL_LIGHT0 );
      //gl.glEnable( GL.GL_LIGHT1 );
      //gl.glEnable( GL.GL_LIGHT2 );
      gl.glEnable( GL2.GL_LIGHT3 ); 
      //gl.glEnable( GL.GL_LIGHT4 ); // Posicional en Origen


      // Light 0.
      //	      
      //
      gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0 );
      //gl.glLightfv( GL.GL_LIGHT0, GL.GL_DIFFUSE, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0 );	

      // Light 1.
      //
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_AMBIENT, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_DIFFUSE, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPECULAR, colorWhite, 0 );
      //gl.glLightfv( GL.GL_LIGHT1, GL.GL_SPECULAR, colorRed, 0 );
      //
      gl.glLightf( GL2.GL_LIGHT1, GL2.GL_CONSTANT_ATTENUATION, 0.5f );

      // Light 2.
      //
      gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_AMBIENT, colorBlack, 0 );
      gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_DIFFUSE, colorDarkGray, 0 );
      gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_SPECULAR, colorDarkGray, 0 );
      //
      gl.glLightf( GL2.GL_LIGHT2, GL2.GL_CONSTANT_ATTENUATION, 0.8f );

      // Light 3.
      //
      gl.glLightfv( GL2.GL_LIGHT3, GL2.GL_AMBIENT, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT3, GL2.GL_DIFFUSE, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT3, GL2.GL_SPECULAR, colorWhite, 0 );
      //
      gl.glLightf( GL2.GL_LIGHT3, GL2.GL_CONSTANT_ATTENUATION, 0.3f );

      // Light 4.
      //
      //gl.glLightfv( GL.GL_LIGHT4, GL.GL_AMBIENT, colorWhite, 0 );
      //gl.glLightfv( GL.GL_LIGHT4, GL.GL_DIFFUSE, colorWhite, 0 );
      gl.glLightfv( GL2.GL_LIGHT4, GL2.GL_SPECULAR, colorWhite, 0 );
      //
      gl.glLightf( GL2.GL_LIGHT4, GL2.GL_CONSTANT_ATTENUATION, 0.3f );

    }

  public void initPosition( GL2 gl )
    {
      // Establece la posicion y dirección (spotlight) y SPOT_CUTOFF
      //
      //float posLight1[] = { 0f, 0f, 0f, 1.0f };
      //float posLight1[] = { 1.0f, 1.f, 1.f, 0.0f };
      //float posLight1[] = { 2f, 4f, 1f, 1.0f };
      //float posLight1[] = { 0f, 5f, -3f, 1.0f };
      float posLight1[] = { lightX, lightY, lightZ, 1.0f };
      float spotDirection1[] = { -1.f, -1.f, -1.f };
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_POSITION, posLight1, 0 );
      gl.glLightf( GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 15.0F);
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, spotDirection1, 0 );
      gl.glLightf( GL2.GL_LIGHT1, GL2.GL_SPOT_EXPONENT, 50f  );
      
      // Light2
      //
      float posLight2[] = { .5f, 1.f, 3.f, 0.0f };
      gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_POSITION, posLight2, 0 );

      // Light3
      //
      float posLight3[] = { .5f, 1.f, 3.f, 0.0f };
      gl.glLightfv( GL2.GL_LIGHT3, GL2.GL_POSITION, posLight3, 0 );

      // Light4
      //
      float posLight4[] = { 0f, 0f, 0f, 1f };
      gl.glLightfv( GL2.GL_LIGHT4, GL2.GL_POSITION, posLight4, 0 );

    }


  /////////////// Move light ////////////////////////////

  // Move light 0.
  public void moveLightX( boolean positivDirection ) {
    lightX += positivDirection ? dLight : -dLight;
  }
  public void moveLightY( boolean positivDirection ) {
    lightY += positivDirection ? dLight : -dLight;
  }
  public void moveLightZ( boolean positivDirection ) {
    lightZ += positivDirection ? dLight : -dLight;
  }

  public void animate( GL2 gl, GLU glu , GLUT glut ) 
    {
      float posLight1[] = { lightX, lightY, lightZ, 1.f };
      gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_POSITION, posLight1, 0 );
      drawLight( gl, glu, glut );
      //lightX += 0.003f;
      //lightY += 0.003f;
    }


 /////////////// Define Material /////////////////////

  public void setLightSphereMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorYellow, 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorYellow, 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorYellow, 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorYellow, 0 );
      //gl.glMaterialfv( face, GL.GL_EMISSION, colorLightYellow , 0 );
      //gl.glMaterialfv( face, GL.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeMaterial( GL2 gl, int face, float rgba[], int offset )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, rgba, offset );
      gl.glMaterialfv(face, GL2.GL_DIFFUSE, rgba, offset );
      gl.glMaterialfv(face, GL2.GL_SPECULAR, rgba, offset );
      gl.glMaterialfv(face, GL2.GL_SHININESS, rgba, offset );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv(face, GL2.GL_DIFFUSE, mat_diffuse, 0 );
      gl.glMaterialfv(face, GL2.GL_SPECULAR, mat_specular, 0 );
      gl.glMaterialfv(face, GL2.GL_SHININESS, mat_shininess, 0 );
    }

  public void setSomeWhiteMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorWhite , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorWhite , 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorWhite , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeGrayMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorGray , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorGray , 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorGray , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeDarkGrayMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorDarkGray , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorDarkGray , 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorDarkGray , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }


  public void setSomeYellowMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorBlack , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorLightYellow, 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorYellow , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 5 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeBlueMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorBlue , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorBlue, 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorBlue , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeRedMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorRed , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorRed , 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorRed , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  public void setSomeGreenMaterial( GL2 gl, int face )
    {
      gl.glMaterialfv( face, GL2.GL_AMBIENT, colorBlack , 0 );
      gl.glMaterialfv( face, GL2.GL_DIFFUSE, colorBlack , 0 );
      gl.glMaterialfv( face, GL2.GL_SPECULAR, colorGreen , 0 );
      gl.glMateriali( face, GL2.GL_SHININESS, 4 );
      gl.glMaterialfv( face, GL2.GL_EMISSION, colorBlack , 0 );
    }

  /////////////////// dibujos /////////////////////////

  ///////////////// Dibuja una Esfera con Luz ///////////////


  public void drawLight( GL2 gl, GLU glu , GLUT glut ) 
    {
      setLightSphereMaterial( gl, GL.GL_FRONT_AND_BACK );
      gl.glPushMatrix(); {
	gl.glTranslatef( lightX, lightY, lightZ );
	glut.glutSolidSphere( 0.1f, 20, 20 );
      } gl.glPopMatrix();
    }
  
  public void drawTeaPotWithLight( GL gl, GLUT glut ) 
    {
      glut.glutSolidTeapot( 1.0f, false );
    }

}