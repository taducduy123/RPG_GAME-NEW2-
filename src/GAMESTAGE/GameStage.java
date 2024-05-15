package GAMESTAGE;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import CHARACTER.*;
import ITEM.*;
import MAP.Map;

public class GameStage 
{
    protected Player player;                        //current player
    protected Map map;                              //current map
    protected Inventory inventory;                  //current inventory 

    private int stage;                              //current stage
    private boolean winFlag;
    private boolean loseFlag;

    private static Scanner input = new Scanner(System.in);

//---------------------------------------------------------------------------

    //Constructor
    public GameStage(Player player, Map map, Inventory inventory, int stage)
    {
        this.player = player;
        this.map = map;
        this.inventory = inventory;
        this.stage = stage;
        this.winFlag = false;
        this.loseFlag = false;

    }


//-------------------------------------------------- Getter Methods -------------------------------------------------
    public boolean getWinFlag()
    {return this.winFlag;}

    public boolean getLoseFlag()
    {return this.loseFlag;}

//-------------------------------------------------- Start Game -----------------------------------------------------

    public void run(){
        
        System.out.println("\n***********************************************************");
        System.out.println(">>>>>>>>>>>>>>>>>>> Welcome to Stage #" + this.stage + " <<<<<<<<<<<<<<<<<<<");
        System.out.println("***********************************************************\n");
        System.out.println("Press any key to continue................");
        input.nextLine();

        //Print the initial state of player
        System.out.println("\n*****************************************************\n");
        this.player.showState();
        this.map.drawMap(player);

        //Game Loop   
        MainMenu(this.map, this.player, this.inventory);
                
    }

//------------------------------------------------- Main Menu --------------------------------------------------------  

    public void MainMenu(Map m, Player obj, Inventory i){
        int choice;
        do
        {
            System.out.println("\n------------------------------------------------------\n");
            System.out.println("1. Move");
            System.out.println("2. Show Inventory");
            System.out.println("3. Attack");
            System.out.println("4. Back To Home");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();                               //Consume keyboard buffer

            switch (choice) {
                case 1:
                    moveControl();
                    break;
                case 2:
                    inventoryControl();
                    break;
                case 3:
                    attackMenu();         
                    break;
                case 4:                    
                    //System.out.println("Thanks for playing");
                    break;               
                default:
                    System.out.println("Invalid choice");
                    break;
            }           
        } while(choice != 4 && winFlag == false && loseFlag == false);
    }

    
//---------------------------------- Menu for Move ---------------------------------------

    public void moveControl() {
        int choice;
        boolean status = true;
        do {
            System.out.println("\n------------------------------------------------------\n");
            System.out.println("1. Move Up");
            System.out.println("2. Move Down");
            System.out.println("3. Move Left");
            System.out.println("4. Move Right");
            System.out.println("5. No Move");
            System.out.println("6. Back To Menu (Attack & Inventory)");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();                           //Consume keyboard buffer

            switch (choice) {
                case 1:
                    player.moveUp(map);
                    updateGame(); 
                    if(this.winFlag == true || this.loseFlag == true)
                    {break;}                   
                    map.drawMap(player);
                    player.showState();
                    messageToShow();
                    break;

                case 2:
                    player.moveDown(map);
                    updateGame();
                    if(this.winFlag == true || this.loseFlag == true)
                    {break;}   
                    map.drawMap(player);
                    player.showState();
                    messageToShow();
                    break;

                case 3:
                    player.moveLeft(map);
                    updateGame();
                    if(this.winFlag == true || this.loseFlag == true)
                    {break;}   
                    map.drawMap(player);
                    player.showState();
                    messageToShow();
                    break;

                case 4:
                    player.moveRight(map);
                    updateGame();
                    if(this.winFlag == true || this.loseFlag == true)
                    {break;}   
                    map.drawMap(player);
                    player.showState();
                    messageToShow();
                    break;

                case 5:
                    updateGame();
                    if(this.winFlag == true || this.loseFlag == true)
                    {break;}   
                    map.drawMap(player);
                    player.showState();
                    messageToShow();
                    break;

                case 6: 
                    
                    status = false;
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (status == true  && winFlag == false && loseFlag == false);
    }
    

//---------------------------------- Menu for Inventory ---------------------------------------   
    public void inventoryControl(){
        System.out.println("\n------------------------------------------------------\n");
        if(inventory.isEmpty())         
            System.out.println("Empty inventory");
        else{
            inventory.displayInventory();
            System.out.println("Attack weapon: " + player.getCurrentWeapon());
            System.out.println("Defense weapon: " + player.getCurrentArmor());
            int choice, choice1;
            boolean status = true;
            do{              
                System.out.print("Enter a number to show item (Exit: 0 | Range: 1 - " + inventory.size() + "): ");
                choice = input.nextInt();
                if(choice == 0) 
                    status = false;
                else if(0 < choice && choice <= inventory.size()){
                    boolean status1 = true;
                    System.out.println("\n------------------------------------------------------\n");  
                    if(inventory.getItem(choice - 1) instanceof Weapon)
                        System.out.println((inventory.getItem(choice - 1)).toString());
                    else if(inventory.getItem(choice - 1) instanceof Armor)
                        System.out.println((inventory.getItem(choice - 1)).toString());
                    else if(inventory.getItem(choice - 1) instanceof Potion)
                        System.out.println(inventory.getItem(choice - 1).toString());
                    do{               
                        System.out.println("1. Use item");
                        System.out.println("2. Remove item");
                        System.out.println("3. Back");
                        System.out.print("Enter your choice: ");
                        choice1 = input.nextInt();
                        if(choice1 == 1){
                            if(inventory.getItem(choice - 1) instanceof Weapon)
                                player.equipWeapon(inventory.getItem(choice - 1));
                            else if (inventory.getItem(choice - 1) instanceof Armor)
                                player.equipArmor(inventory.getItem(choice - 1));
                            else if (inventory.getItem(choice -1) instanceof Potion){
                                player.equipPotion(inventory.getItem(choice -1));
                                inventory.removeItem(choice - 1);
                            }
                            System.out.println("Equip sucessfully");
                            System.out.println("\n------------------------------------------------------\n");
                            inventory.displayInventory();
                            System.out.println("Current weapon: " + player.getCurrentWeapon());
                            System.out.println("Current armor: " + player.getCurrentArmor());
                            status1 = false;
                        }       
                        else if(choice1 == 2){
                            if(inventory.getItem(choice - 1) instanceof Weapon && inventory.getItem(choice - 1).getInUse() == true)
                                player.unequipWeapon();
                            else if(inventory.getItem(choice - 1) instanceof Armor && inventory.getItem(choice - 1).getInUse() == true)
                                player.unequipArmor();
                            inventory.removeItem(choice - 1);
                            System.out.println("Remove sucessfully");
                            System.out.println("\n------------------------------------------------------\n");
                            inventory.displayInventory();
                            System.out.println("Current weapon: " + player.getCurrentWeapon());
                            System.out.println("Current armor: " + player.getCurrentArmor());
                            status1 = false;
                        }
                        else if(choice1 == 3){            
                            System.out.println("\n------------------------------------------------------\n");
                            inventory.displayInventory();
                            System.out.println("Current weapon: " + player.getCurrentWeapon());
                            System.out.println("Current armor: " + player.getCurrentArmor());
                            status1 = false;
                        }
                        else System.out.println("Invalid choice");
                    } while (status1 == true);          
                }
                else System.out.println("Invalid choice");
            }while(status == true);
            System.out.println("\n------------------------------------------------------\n");
            map.drawMap(player);
            player.showState();
        }
    }


//---------------------------------- Menu for Attack ---------------------------------------  
    public void attackMenu(){
        //Find all monsters in range of player
        ArrayList<Monster> targets = new ArrayList<Monster>();
        for(int i = 0; i < map.numberOfMonsters(); i++){
            if(player.collideMonster(map.getMonsterAtIndex(i)))
                targets.add(map.getMonsterAtIndex(i));
        }

        //Print all monsters in range so that player can pick one to attack
        if(targets.size() == 0)
        {
            System.out.println(">> No monster in range to attack!");
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
                targets.get(choice - 1).takeDamage(player.getAttack());
                updateGame();
                map.drawMap(player);
                player.showState();
                targets.clear();
            }
            else if(choice < 0)
                System.out.println("Invalid choice");
            else{           
                System.out.println("\n------------------------------------------------------\n");
                map.drawMap(player);
            }
        }
    }


//-------------------------------------------- Update Objects ------------------------------------------------

    //Update player + items + monsters + door +  game state
    public void updateGame()
    {
        updateMonsters();
        updateMap();
        updateGameState();
    }

    //Update monsters
    public void updateMonsters()
    {
        for(int i = 0; i < map.numberOfMonsters(); i++)
        {
            map.getMonsterAtIndex(i).doWork(player, map);
        }
    }
    
    //Update map
    public void updateMap()
    {
        map.doWork(player,inventory);
    }
   
    //Update game state
    public void updateGameState()
    {
        if(this.player.getHP() <= 0)        //if player died
        {
            this.loseFlag = true;                   //loseFlag
            System.out.println("\n*******************************************************\n");
            System.out.println("WARNING: You died! Let's start at the beginning");
            System.out.println("\n*******************************************************\n");
            System.out.print("Press any key to continue:");
            input.nextLine();
        }
        else
        {
            if(this.map.checkDoorOpen() == true)        //if door is open
            {
                System.out.println("\n>> NOW DOOR IS OPEN!!!!");
                if(this.map.containDoorAt(this.player.getX(), this.player.getY())) //if player moves into the door
                {
                    this.winFlag = true;                                //winFlag
                    System.out.println("\n*******************************************************\n");
                    System.out.println("Congratulation! You passed stage #" + this.stage + "!!!!");
                    System.out.println("\n*******************************************************\n");
                    System.out.print("Press any key to continue:");
                    input.nextLine();
                }
            }
        }
    }


//--------------------------------------------- Other useful methods -----------------------------------------

    public void messageToShow()
    {      
        if(player.detectMonsters(map) > 0)
        {
            JOptionPane.showMessageDialog(null, "WARNING: " 
                                                            + player.detectMonsters(map) 
                                                            + " monster(s) in front of you!!!");
        }          
    }

   


    //Embedded Main
    public static void main(String[] args) 
    {
        final String path = "src\\InputFile\\map3.txt";
        final int inventorySize = 10;
        final int stage = 3;

        Player player = new Player(null);
        Map map = new Map(path);
        Inventory inventory = new Inventory(inventorySize);

        GameStage gstage = new GameStage(player, map, inventory, stage);

        //player = 300, map = 1, inventory = curent , stage = 1  ==> 
        //gstage.run();

    //*gstage = new GameStage(player, update_map, inventory, update_stage)

        /*  GameStage stage = new GameStage();
            // load game
            stage.next


        */
    }
}
