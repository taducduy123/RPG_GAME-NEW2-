package GAMESTAGE;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import CHARACTER.*;
import ITEM.*;
import MAP.*;


public class GameStageTest implements Serializable
{
    private Player player;
    private Inventory invent;
    private Map map;

    private int currentMapNo;
    private int stageNo;
    


    private boolean isExit; 

    private final int inventorySize = 10;
    private static Scanner input = new Scanner(System.in);

//-------------------------------------------------------------------------------------------------------------------

    //Constructor
    public GameStageTest()
    {
        this.currentMapNo = 1;
        this.stageNo = 1;
        this.isExit = false;

        this.player = new Player(null);
        this.map = new Map("src\\InputFile\\map"+ currentMapNo + ".txt");
        this.invent = new Inventory(inventorySize);
    }

//---------------------------------------------- Map-Stage Section ----------------------------------------------------
    
    public void nextmap()
    {
        (this.currentMapNo)++;
        this.map = new Map("src\\InputFile\\map"+ currentMapNo + ".txt");
    }

    public void resetstage()
    {
        this.stageNo = 1;
        this.currentMapNo = 1;
        this.map = new Map("src\\InputFile\\map"+ currentMapNo + ".txt");
    }

    public int getStageNo()
    {
        return this.stageNo;
    }

    public int getCurrentMapNo()
    {
        return this.currentMapNo;
    }

    public void updateMap()
    {
        this.map.doWork(this.player, this.invent);
    }

    
//---------------------------------------- win-loose-exit-door condition-----------------------------------------

    public boolean win()                //(win = true ==> loose == false)  //(win = false ==> loose == true || false)
    {
        if(this.map.numberOfMonsters() == 0){
            return true;
        }
        else
            return false;
    }

    public boolean isExit()
    {
        return this.isExit;
    }

    public void setExitState(boolean stat)
    {
        this.isExit = stat;
    }

    public boolean loose()
    {
        if (this.player.getHP() == 0)
            return true;
        else
            return false;
    }

    public boolean isPlayeratDoor()
    {
        return this.map.containDoorAt(this.player.getX(), this.player.getY());
    }

    public boolean isBossStage()
    {
        for(int i = 0; i < this.map.numberOfMonsters(); i++)
        {
            if(this.map.getMonsterAtIndex(i) instanceof Boss)
                return true;
        }
        return false;
    }


//--------------------------------------------------- Player Section ---------------------------------------------------

    public void playerAction()
    {   // reminder: after changing the attack function should have
        // [2. attack (availible)] and [2. attack (not availible)] || solution: None

        int choice;
        System.out.println("------Now it Your turn to go-------");
        System.out.println("1. Move");
        System.out.println("2. Attack");
        System.out.println("3. Inventory");
        System.out.println("4. exit");
        choice = input.nextInt();
        input.nextLine();
        switch (choice){
            case 1:
                playerMove();
                break;
            case 2:
                playerAttack();
                break;
            case 3:
                playerInvent();
                break;
            case 4:
                this.isExit = true;
                break;
            default:
                System.out.println("Invalid choice please choose again!");
                playerAction();
                break;
        }
    }


    public void playerMove(){
        int choice;
        System.out.println("\n------------------------------------------------------\n");
        System.out.println("1. Move Up");
        System.out.println("2. Move Down");
        System.out.println("3. Move Left");
        System.out.println("4. Move Right");
        System.out.println("5. No Move");
        System.out.println("6. Back to menu");
        System.out.print("Enter your choice: ");
        choice = input.nextInt();
        input.nextLine();            
        switch (choice) {
            case 1:
                this.player.moveUp(this.map);
                break;
            
            case 2:
                this.player.moveDown(this.map);
                break;

            case 3:
                this.player.moveLeft(this.map);
                break;
            
            case 4:
                this.player.moveRight(this.map);
                break;
            
            case 5:
                break;
            
            case 6:
                playerAction();
                break;
            default:
                break;
        }
    }


    public void playerAttack(){ // Reminder: need to improve this function (too complex and should have print out in menu if avalible) || solution: None
        //Find all monsters in range of player
        ArrayList<Monster> targets = new ArrayList<Monster>();
        for(int i = 0; i < this.map.numberOfMonsters(); i++){
            if(player.collideMonster(this.map.getMonsterAtIndex(i)))
                targets.add(this.map.getMonsterAtIndex(i));
        }

        //Print all monsters in range so that player can pick one to attack
        if(targets.size() == 0)
        {
            System.out.println(">> No monster in range to attack! (press any key to continue)");
            input.nextLine();
        }
        else
        {
            System.out.printf("|%10s | %20s | %10s |\n", "No.",
                                                        "Name",
                                                        "HP");
            for(int i = 0; i < targets.size(); i++){
                System.out.printf("|%10s | %20s | %10s |\n", i + 1, 
                        targets.get(i).getName() + "(" + targets.get(i).getMark() + ")",
                        targets.get(i).getHP() + "/" + targets.get(i).getMaxHp());
            }
            int choice;
            System.out.print("Choose a number (0: Exit || 1 - " + targets.size() + ") to attack monster: ");
            choice = input.nextInt();
            if(choice > 0){
                targets.get(choice - 1).takeDamage(this.player.getAttack());
            }
            else if(choice < 0)
                System.out.println("Invalid choice");
            else{           
                System.out.println("\n------------------------------------------------------\n");
                playerAction();
            }
        }
    }

    
    public void playerInvent(){ // Reminder: need to improve this funtion (too complex) || solution: add more methods to inventory
        System.out.println("\n------------------------------------------------------\n");
        if(this.invent.isEmpty())         
            System.out.println("Empty inventory");
        else{
            this.invent.displayInventory();
            System.out.println("Attack weapon: " + this.player.getCurrentWeapon());
            System.out.println("Defense weapon: " + this.player.getCurrentArmor());
            int choice, choice1;
            boolean status = true;
            do{              
                System.out.print("Enter a number to show item (Exit: 0 | Range: 1 - " + this.invent.size() + "): ");
                choice = input.nextInt();
                if(choice == 0) {
                    this.showGraphic();
                    status = false;
                    }
                else if(0 < choice && choice <= this.invent.size()){
                    boolean status1 = true;
                    System.out.println("\n------------------------------------------------------\n");  
                    if(this.invent.getItem(choice - 1) instanceof Weapon)
                        System.out.println((this.invent.getItem(choice - 1)).toString());
                    else if(this.invent.getItem(choice - 1) instanceof Armor)
                        System.out.println((this.invent.getItem(choice - 1)).toString());
                    else if(this.invent.getItem(choice - 1) instanceof Potion)
                        System.out.println(this.invent.getItem(choice - 1).toString());
                    do{               
                        System.out.println("1. Use item");
                        System.out.println("2. Remove item");
                        System.out.println("3. Back");
                        System.out.print("Enter your choice: ");
                        choice1 = input.nextInt();
                        if(choice1 == 1){
                            if(this.invent.getItem(choice - 1) instanceof Weapon)
                                this.player.equipWeapon(this.invent.getItem(choice - 1));
                            else if (this.invent.getItem(choice - 1) instanceof Armor)
                                this.player.equipArmor(this.invent.getItem(choice - 1));
                            else if (this.invent.getItem(choice -1) instanceof Potion){
                                this.player.equipPotion(this.invent.getItem(choice -1));
                                this.invent.removeItem(choice - 1);
                            }
                            System.out.println("Equip sucessfully");
                            System.out.println("\n------------------------------------------------------\n");
                            this.invent.displayInventory();
                            System.out.println("Current weapon: " + this.player.getCurrentWeapon());
                            System.out.println("Current armor: " + this.player.getCurrentArmor());
                            status1 = false;
                        }       
                        else if(choice1 == 2){
                            if(this.invent.getItem(choice - 1) instanceof Weapon && this.invent.getItem(choice - 1).getInUse() == true)
                                this.player.unequipWeapon();
                            else if(this.invent.getItem(choice - 1) instanceof Armor && this.invent.getItem(choice - 1).getInUse() == true)
                                this.player.unequipArmor();
                            this.invent.removeItem(choice - 1);
                            System.out.println("Remove sucessfully");
                            System.out.println("\n------------------------------------------------------\n");
                            this.invent.displayInventory();
                            System.out.println("Current weapon: " + this.player.getCurrentWeapon());
                            System.out.println("Current armor: " + this.player.getCurrentArmor());
                            status1 = false;
                        }
                        else if(choice1 == 3){            
                            System.out.println("\n------------------------------------------------------\n");
                            this.invent.displayInventory();
                            System.out.println("Current weapon: " + this.player.getCurrentWeapon());
                            System.out.println("Current armor: " + this.player.getCurrentArmor());
                            status1 = false;
                        }
                        else System.out.println("Invalid choice");
                    } while (status1 == true);          
                }
                else System.out.println("Invalid choice");
            }while(status == true);
            playerAction();
        }
    }


//---------------------------------------------------- Reset Player -------------------------------------------
    public void resetPlayerWhenDied(){
        this.player = new Player(null);
        this.invent = new Inventory(inventorySize);
    }

    
    public void resetPlayerWhenNextStage(){
        this.player.setXY(0, 0, map);
        this.player.heal(this.player.getMaxHp());
    }


//---------------------------------------------------- Monster Section -----------------------------------------
    public void monsterAction()
    {
        for(int i = 0; i < this.map.numberOfMonsters(); i++)
        {
            this.map.getMonsterAtIndex(i).doWork(this.player, this.map);
        }
    }


//----------------------------------------------------- Graphic Section ----------------------------------------
    public void showGraphic()
    {
        this.map.drawMap(this.player);
        this.player.showState();
    }


//---------------------------------------------------- work with files -----------------------------------------

    public void save(String filename) 
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameStageTest load(String filename) 
    {
        GameStageTest stage = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            stage = (GameStageTest) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("");
        }
        return stage;
    }

    public boolean delete(String filename) 
    {
        File file = new File(filename);
        return file.delete();
    }






//---------------------------------------------------- Embedded Main
    public static void main(String[] args) 
    {
        GameStageTest g = new GameStageTest();
        g.setExitState(true);

        g.save("Test.ser");


        GameStageTest gLoad = GameStageTest.load("Test.ser");

        System.out.println(gLoad.isExit());

    }
}


