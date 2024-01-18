package Chess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    private Camera camera;
    private Viewport viewport;
    private ExtendViewport extendView; //main viewport
    private OrthographicCamera extendCam;
    private ShapeRenderer shapeRenderer;
    private Vector2 mousePos;
    private Engine engine;

    private ArrayList<Square> board;
    private boolean gameTurn;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        shapeRenderer = new ShapeRenderer();
        extendCam = new OrthographicCamera();
        extendView = new ExtendViewport(900,900, extendCam);
        Vector2 mousePos = new Vector2();
        engine = new Engine();
        board = new ArrayList<Square>();
        board = engine.initializeBoard(board);
        gameTurn = true;
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //extendView.apply();
        //camZoom(extendCam);
        //extendCam.update();
        //batch.setProjectionMatrix(extendCam.combined);
        //shapeRenderer.setProjectionMatrix(extendCam.combined);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        engine.drawBoard(board, shapeRenderer, batch);
        engine.renderMouse(shapeRenderer);
        if(gameTurn)
        {
            engine.turn(board, gameTurn);
            System.out.println(gameTurn + " Current");
        }



    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public void renderSquare(ShapeRenderer shapeRenderer)
    {

    }
}
