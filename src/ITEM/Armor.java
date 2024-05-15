package ITEM;
import CHARACTER.Character;


public class Armor extends Item
{
    private int defense;
    private int HP;
    
//---------------------------------------

    //Constructor
    public Armor(String name, int defense, int HP, int x, int y)
    {
        super(name, 2, x, y);
        this.defense = defense;
        this.HP = HP;
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
        obj.setDefense(obj.getDefense() + this.defense);        //new defense = current defense + defense of weapon
        obj.setMaxHp(obj.getHP() + this.HP);                    //new maxHp = current maxHp + hp of weapon
    }


    @Override
    public void unapplyEffectTo(Character obj)
    {
        obj.setDefense(obj.getDefense() - this.defense);        //new defense = current defense - defense of weapon
        obj.setMaxHp(obj.getHP() - this.HP);                    //new maxHp = current maxHp - hp of weapon      
    }  


    @Override
    public String toString()
    {   
        //name [bonus HP: , bonus Defense:]
        String str =  super.getName() + " [bonus HP: " + this.HP + ", bonus Defense: " + this.defense + "]";                              
        return str;
    }
}
