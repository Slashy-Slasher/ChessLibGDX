package Chess.game;

import com.badlogic.gdx.math.Vector2;

public class Square
{
    public int x;
    public int y;
    public static int size = 100;
    public int piece;

    // P, R, B, N, Q, K
    Square(int x, int y, int piece)
    {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    public void takes(Square square)
    {
        square.piece = this.piece;
    }
    public Vector2 coords()
    {
        return new Vector2(this.x, this.y);
    }


}
