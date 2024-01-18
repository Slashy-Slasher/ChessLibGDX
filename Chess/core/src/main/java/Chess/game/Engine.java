package Chess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class Engine
{
    /*
    White = (Black - 6)
    --------
    Rook =   1
    Knight = 2
    Bishop = 3
    Queen =  4
    King =   5
    Pawn =   6

    Black = (White + 6)
     */

    //https://www.namecheap.com/visual/font-generator/chess-symbols/
    public ArrayList<Square> initializeBoard(ArrayList<Square> board)
    {
        board = new ArrayList<Square>();
        // "1,2,3,4,5,3,2,1,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,12,12,12,12,12,12,12,7,8,9,10,11,9,8,7"
        String fen = "1,2,3,4,5,3,2,1,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,12,12,12,12,12,12,12,7,8,9,10,11,9,8,7";
        String[] delimentedFen = fen.split(",");
        for(int i = 0; i < 64; i++)
        {
            //board.add(new Square(i,i%8+1, Integer.parseInt(delimentedFen[i])));
            board.add(new Square(200+(i%8)*Square.size + Square.size/2,50+(i/8)*Square.size, Integer.parseInt(delimentedFen[i])));
            //System.out.println((i%8 + 1) + ", " + (i/8 + 1) + " = " + Integer.parseInt(delimentedFen[i]));
        }
        return board;
    }

    public boolean wasClicked(ArrayList<Square> board)
    {
        for(int i = 0; i < 64; i++)
        {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            {
                if(new Vector2(Gdx.input.getX(), Gdx.input.getY()).equals(board.get(i).coords()))
                {
                    return true;
                }
            }

        }

        return false;

    }
    public boolean turn(ArrayList<Square> board, boolean turn)
    {
        ArrayList<Square> boardCopy = deepCopyBoard(board);
        turn = wasClicked(board);
               
        if(!boardCopy.equals(board))
        {
            return !turn;
        }
        else
        {
            return turn;

        }



    }
    public ArrayList<Square> deepCopyBoard(ArrayList<Square> board)
    {
        ArrayList<Square> board2 = new ArrayList<>();
        for (Square copiedSquare  : board) {
            board2.add(new Square(copiedSquare.x, copiedSquare.y, copiedSquare.piece));
        }
        return board2;
    }

    public void drawBoard(ArrayList<Square> board, ShapeRenderer shapeRenderer, SpriteBatch batch)
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f,0,0,1f);
        int startX = 200;
        int startY = 50;
        BitmapFont font = new BitmapFont();
        font.setColor(Color.RED);
        batch.begin();
        for(int i = 0; i < 64; i++)
        {
            shapeRenderer.rect(board.get(i).x, board.get(i).y, Square.size,Square.size);
            if(board.get(i).piece != 0)
            {
                font.draw(batch, board.get(i).piece+"", board.get(i).x + Square.size/2 , board.get(i).y + Square.size/2);
            }

        }
        batch.end();

        shapeRenderer.end();

        /*
        for (int j = 0; j < 8; j++)
        {
            for(int i = 0; i < 8; i++)
            {
                shapeRenderer.rect(200+i*Square.size, 50+j*Square.size, Square.size, Square.size);



            font.draw(batch,      , 200+i*Square.size + Square.size/2, 50-25+j*Square.size+ Square.size/2);
            font.draw(batch,  (i+1)+","+(j+1), 200+i*Square.size + Square.size/2, 50+j*Square.size+ Square.size/2);


                if(board.get(i).piece == 5)
                {

                    //
                    //
                }


            }
        }
        shapeRenderer.end();
        System.out.println();
         */

    }
    public void renderMouse(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            shapeRenderer.circle(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY(), 50);
        }
        shapeRenderer.end();
    }




}
