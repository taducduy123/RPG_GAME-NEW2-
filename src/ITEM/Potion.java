package ITEM;
import CHARACTER.Character;


public class Potion extends Item
{
    private int HPtoHeal;

//--------------------------------------------------
    //Constructor
    public Potion(String name, int HPtoHeal, int x, int y)
    {
        super(name, 3, x, y);
        this.HPtoHeal = HPtoHeal;
    }   


    @Override
    public String getMark()
    {       
        //Return 2 first char of Name
        String mark = super.getName().substring(0, 2);
        return mark;
    }

   
    @Override
    public void applyEffectTo(Character obj)
    {
        obj.heal(this.HPtoHeal);
    }


    @Override
    public void unapplyEffectTo(Character obj)
    {
        //Nothing to do    
    }  


    @Override
    public String toString()
    {   
        //name [HP to Heal: ]
        String str =  super.getName() + " [HP to Heal: " + this.HPtoHeal  + "]";                              
        return str;
    }
}
