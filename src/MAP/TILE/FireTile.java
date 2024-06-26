package MAP.TILE;
import CHARACTER.Character;

public class FireTile extends Tile
{
    private int lossHP = 10;
    private final String representation = "{ }"; 
//---------------------------------------------------
    public FireTile()
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
        character.takeDamage(lossHP + character.getDefense());
    }


    //Embedded Main
    public static void main(String[] args) 
    {
        FireTile F = new FireTile();
        F.drawTile("X");
        //F.drawTile("Y");
    }

}