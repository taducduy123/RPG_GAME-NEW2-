package GameMain;

import MAP.Map;
import java.util.Scanner;
import CHARACTER.Player;
import GAMESTAGE.GameStage;
import ITEM.Inventory;

public class GameMain
{
    private Player player;
    private Map map;
    private Inventory inventory;
    private int stageNo;
    private GameStage gameStage; 

    private final int inventorySize = 10;
    private final int maxStage = 3;
    private String[] path = new String[]{"src/InputFile/map1.txt", "src/InputFile/map2.txt", "src/InputFile/map3.txt"};
    private String playerName;

    private static Scanner input = new Scanner(System.in);
//----------------------------------------------------------------------------

    //Constructor
    public GameMain()
    {
        
    }

    public void start()
    {
        //Greeting
        System.out.print("Enter you name: ");
        this.playerName = input.nextLine();
        System.out.println("\n**************************************************");
        System.out.println("Hello " + this.playerName + "! Let's start the game.");
        System.out.println("Press any key to continue: ");
        input.nextLine();

        //Build current game state
        this.player = new Player(playerName);
        this.map = new Map(path[0]);
        this.inventory = new Inventory(inventorySize);
        this.stageNo = 1;
        this.gameStage = new GameStage(player, map, inventory, stageNo);

        //Run main loop
        runHomeMenu();
    }


    public void runHomeMenu()
    {
        int choice;
        do
        {      
            System.out.println("\n******************************************************************");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> HOME MENU <<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("******************************************************************");
            System.out.println("1. New Game");
            System.out.println("2. Continue Game");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();                //Consume keyboard buffer

            switch (choice) 
            {
                case 1:
                        resetGame();
                        this.runStagesLoop();
                        break;
                case 2:
                        this.runStagesLoop();
                        break;
                case 3:
                        System.out.println("See you next time!");
                        break;
                default:
                        System.out.println("ERROR: Invalid Choice!");
                        break;

            }
        } while (choice != 3);
    }
 
    
    public void runStagesLoop()
    {
        boolean backToHome = false;
        while (!backToHome) 
        {
            if(gameStage != null && gameStage.getWinFlag() == false && gameStage.getLoseFlag() == false)
            {
                this.gameStage.run();
            }
            backToHome = updateGameState();
        }
    }


    public boolean updateGameState()
    {
        boolean shouldBackHome = false;

        //Update current player, map, inventory, stateNo, then gameStage
        if(this.stageNo != this.maxStage)
        {
            if(this.gameStage.getWinFlag() == true)
            {
                this.stageNo = this.stageNo + 1;
                this.player = new Player(this.playerName);
                this.map = new Map(path[stageNo - 1]);  
                
                //Update current gameStage
                this.gameStage = new GameStage(player, map, inventory, stageNo); 
            }
            else if (this.gameStage.getLoseFlag() == true)
            {
                shouldBackHome = true;
                this.stageNo = 1;
                this.player = new Player(this.playerName);
                this.map = new Map(path[stageNo - 1]);
                this.inventory.clear();

                //Update current gameStage
                this.gameStage = new GameStage(player, map, inventory, stageNo); 
            }
            else
            {
                shouldBackHome = true;        //back to HomeMenu
            }        
        }
        else                           //Now we are at the final stage
        {
            if(this.gameStage.getWinFlag() == true)
            {
                shouldBackHome = true;
                System.out.println("CONGRATULATION! YOU WIN ENTIRE GAME!!!!!!!");                     
            }
            else if (this.gameStage.getLoseFlag() == true)
            {
                shouldBackHome = true;
                this.stageNo = 1;
                this.player = new Player(this.playerName);
                this.map = new Map(path[stageNo - 1]);
                this.inventory.clear();

                //Update current gameStage
                this.gameStage = new GameStage(player, map, inventory, stageNo); 
            }
            else
            {
                shouldBackHome = true;        //back to HomeMenu
            }
        }
        return shouldBackHome;
    }
    

    public void resetGame()
    {
        this.player = new Player(playerName);
        this.map = new Map(path[0]);
        this.inventory.clear();
        this.stageNo = 1;
        this.gameStage = new GameStage(player, map, inventory, stageNo);
    }

    



    //Embedded Main
    public static void main(String[] args) 
    {
        GameMain game = new GameMain();
        game.start();
    }
}
