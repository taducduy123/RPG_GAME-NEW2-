package MAP.TILE;
import CHARACTER.Character;

public class WallTile extends Tile
{
    private final String representation = "< >"; 
//-----------------------------------------

    public WallTile()
    {   
        super(true);
    }

    @Override
    public void drawTile(String mark)
    {
        String[] token = this.representation.split(" ");
        mark = "00";
        System.out.printf(" " + token[0] + "%2s" + token[1] + " ", mark);
    }

    @Override
    public void applyEffectTo(Character character) 
    {
        
    }
    
}
