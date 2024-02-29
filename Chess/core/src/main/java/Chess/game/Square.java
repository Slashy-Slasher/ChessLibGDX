package Chess.game;

import com.badlogic.gdx.math.Vector2;
import com.sun.jdi.VMCannotBeModifiedException;

public class Square
{
    public int x;
    public int y;
    public static int size = 100;
    public int piece;
    public boolean isSelected;

    // P, R, B, N, Q, K
    Square(int x, int y, int piece)
    {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.isSelected = false;
    }
    public void takes(Square square)
    {
        square.piece = this.piece;
        this.piece = 0;
    }
    public Vector2 coords()
    {
        return new Vector2(this.x, this.y);
    }
    public boolean isClicked(Vector2 mousePos)
    {
        if(x < mousePos.x && x + size > mousePos.x && y < mousePos.y && y + size > mousePos.y)
        {
           return true;
        }
        return false;
    }


}
