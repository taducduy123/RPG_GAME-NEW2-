package MAP.TILE;
import CHARACTER.Character;


public class WaterTile extends Tile
{
    private int hpToHeal = 5;
    private final String representation = "( )"; 

//---------------------------------------------------
    public WaterTile()
    {   
        super(false);
    }

    @Override
    public void drawTile(String mark)
    {
        String[] token = this.representation.split(" ");
        System.out.printf(" " + token[0] + "%2s" + token[1] + " ", mark);
    }

    @Override
    public void applyEffectTo(Character character)
    {
        character.heal(hpToHeal);
    }    

    
}
