package Chess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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


    private int tileSelectedIndex = -1;
    private long lastInput =   System.currentTimeMillis();
    private ArrayList<String> turnHistory = new ArrayList<String>();

    public ArrayList<Square> initializeBoard(ArrayList<Square> board)
    {
        board = new ArrayList<Square>();
        // "1,2,3,4,5,3,2,1,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,12,12,12,12,12,12,12,7,8,9,10,11,9,8,7"
        String fen = "1,2,3,4,5,3,2,1,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,12,12,12,12,12,12,12,7,8,9,10,11,9,8,7";
        turnHistory.add(fen);
        System.out.print(fen);
        String[] delimentedFen = fen.split(",");
        for(int i = 0; i < 64; i++)
        {
            //board.add(new Square(i,i%8+1, Integer.parseInt(delimentedFen[i])));
            board.add(new Square(200+(i%8)*Square.size + Square.size/2,50+(i/8)*Square.size, Integer.parseInt(delimentedFen[i])));
            //System.out.println((i%8 + 1) + ", " + (i/8 + 1) + " = " + Integer.parseInt(delimentedFen[i]));
        }
        return board;
    }

    public ArrayList<Square> setBoard(ArrayList<Square> board)
    {
        String fen = turnHistory.get(turnHistory.size()-1);
        turnHistory.add(fen);
        String[] delimentedFen = fen.split(",");
        for(int i = 0; i < 64; i++)
        {
            board.get(i).piece = Integer.parseInt(delimentedFen[i]);
            //board.add(new Square(200+(i%8)*Square.size + Square.size/2,50+(i/8)*Square.size, Integer.parseInt(delimentedFen[i])));
        }
        return board;
    }

    private String exportBoard(ArrayList<Square> board)
    {
        String boardToString = "";
        for(int i = 0; i < board.size(); i++)
        {
            boardToString += (board.get(i).piece + ",");
        }
        //System.out.println(boardToString);
        return boardToString;
    }

    public boolean timeCheck()
    {
        boolean check = false;
        if(System.currentTimeMillis() > lastInput+500)
        {
            lastInput = System.currentTimeMillis();
            check = true;
        }
        return check;
    }


    public void selectionCheck(ArrayList<Square> board, OrthographicCamera cam)
    {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&  timeCheck())
        {
            lastInput = System.currentTimeMillis();
            if(tileSelectedIndex == -1)
            {
                for (int i = 0; i < board.size(); i++)
                {
                    //Vector2 mousePos = new Vector2(getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), cam));
                    Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
                    if (board.get(i).isClicked(mousePos)) {
                        board.get(i).isSelected = true;
                        tileSelectedIndex = i;

                    }
                }
            }
            else
            {
                for (int i = 0; i < board.size(); i++)
                {
                    //Vector2 mousePos = new Vector2(getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), cam));
                    Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
                    if (board.get(i).isClicked(mousePos) && !board.get(i).isSelected && board.get(tileSelectedIndex).piece != 0) {

                        board.get(tileSelectedIndex).takes(board.get(i));
                        board.get(tileSelectedIndex).isSelected = false;
                        tileSelectedIndex = -1;
                        turnHistory.add(exportBoard(board));
                        System.out.println(turnHistory.get(turnHistory.size()-1));
                    }
                }
            }


        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            for (Square square : board) {
                square.isSelected = false;
                tileSelectedIndex = -1;
            }
        }
    }
    public boolean wasClicked(ArrayList<Square> board)
    {
        for(int i = 0; i < 64; i++)
        {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            {
                //System.out.println(Gdx.input.getX());
                //System.out.println(board.get(i).coords().x);
                if(board.get(i).coords().x*Square.size > Gdx.input.getX())
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
            if(!board.get(i).isSelected)
            {
                shapeRenderer.rect(board.get(i).x, board.get(i).y, Square.size,Square.size);
            }
            if(board.get(i).piece != 0)
            {
                if(board.get(i).piece >= 7)
                {
                    font.setColor(Color.BLACK);
                }
                else
                {
                    font.setColor(Color.WHITE);
                }
                font.draw(batch, board.get(i).piece+"", board.get(i).x + Square.size/2 , board.get(i).y + Square.size/2);

            }
        }
        for(int i = 0; i < 64; i++)
        {
            if(board.get(i).isSelected)
            {
                shapeRenderer.setColor(1f,0,0,1f);
                shapeRenderer.rect(board.get(i).x, board.get(i).y, Square.size,Square.size);
                shapeRenderer.setColor(0f,0,0,1f);
                if(board.get(i).piece >= 7)
                {
                    font.setColor(Color.BLACK);
                }
                else
                {
                    font.setColor(Color.WHITE);
                }
                if(board.get(i).piece != 0)
                {
                    font.draw(batch, board.get(i).piece+"", board.get(i).x + Square.size/2 , board.get(i).y + Square.size/2);
                }
            }

        }
        batch.end();

        shapeRenderer.end();
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
