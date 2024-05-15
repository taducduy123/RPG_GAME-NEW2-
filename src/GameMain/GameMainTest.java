package GameMain;

import java.util.Scanner;

import GAMESTAGE.GameStageTest;




public class GameMainTest 
{
    private GameStageTest stage;
    private MapManager mManager;                        //new new new 

    private final int maxMapNum = 4;
    private final String fileName = "Data.ser";

    private static Scanner input = new Scanner(System.in);

//----------------------------------------------------------------

    //Constructor
    public GameMainTest()
    {
        this.mManager = new MapManager(maxMapNum, new int[]{1,1,2});                //new new new
    }


    
    public void Run()
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
                        this.stage = new GameStageTest();
                        startStage();
                        break;

                case 2: // reminder: add the sistuation where it cannot find the file
                        this.stage = GameStageTest.load(fileName);
                        if(this.stage == null)
                        {
                            System.out.println("Not Found Previous Game Data!");
                            break;
                        }
                        this.stage.setExitState(false);
                        startStage();
                        break;

                case 3:

                        System.out.println("See you next time!");
                        input.close();
                        break;

                default:
                        System.out.println("ERROR: Invalid Choice!");
                        break;

            }
        } while (choice != 3);
    }
 
    //run a specific stage with current player, map, inventory (Notice when player want to pause program)
    public void startStage()  //~~ Stages Loop
    {
        this.showTitle();                                   //new new new

        stage.showGraphic();
        boolean isBossMap  = this.stage.isBossStage();

        do 
        {
            stage.playerAction();
            if(!stage.isExit())
            {
                stage.monsterAction();
                stage.updateMap();
                stage.showGraphic();
            }
            stage.save(fileName);
        } while (!stage.win() && !stage.loose() && !stage.isExit());     //==> status of stage = 

         
        if(stage.loose())
        {
            stage.resetstage();
            stage.resetPlayerWhenDied();
            stage.delete(fileName);
            System.out.println(">> You died!! Let's start at beginning (Press any [Enter] to continue):" );
            input.nextLine();
        }
                 
        else if(stage.win())
        {
            if(!isBossMap)
            {
                while(!this.stage.isPlayeratDoor() && !stage.isExit())
                {
                    stage.playerAction();
                    stage.updateMap();
                    stage.showGraphic();
                    stage.save(fileName);
                }

                if(this.stage.isPlayeratDoor())
                {
                    this.stage.resetPlayerWhenNextStage();
                    this.stage.nextmap();
                    this.startStage();
                    stage.save(fileName);
                }
                
            }
            else
            {
                if(this.stage.getCurrentMapNo() != this.maxMapNum)
                {
                    this.stage.nextmap();
                    this.startStage();
                    stage.save(fileName);
                }
                else
                {
                    while(!this.stage.isPlayeratDoor() && !stage.isExit())
                    {
                        stage.playerAction();
                        stage.updateMap();
                        stage.showGraphic();
                        stage.save(fileName);
                    }

                    if(this.stage.isPlayeratDoor())
                    {
                        System.out.println("CONGRATULATION! YOU WIN ENTIRE GAME!!! (Press [Enter] to continue):");
                        input.nextLine();
                        stage.save(fileName);
                    }
                }
            }
            
        }     
    }



    //show title for each Stage                     //new new new
    public void showTitle()
    {
        if(this.mManager.getCorrespondingPhaseNo(this.stage.getCurrentMapNo()) == -1)       //if now is regular stage
        {
            System.out.println("\n**********************************************************************************************");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Welcome to Stage #" + this.stage.getStageNo() + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("**********************************************************************************************");
            System.out.println("Press [Enter] to continue: ");
            input.nextLine();
        }
        else                                                                                //if now is boss stage
        {
            System.out.println("\n***********************************************************************************************");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Welcome to Stage #" + this.stage.getStageNo() 
                                                                                    + " (phase #" 
                                                                                    + mManager.getCorrespondingPhaseNo(this.stage.getCurrentMapNo()) 
                                                                                    + ") <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("***********************************************************************************************");
            System.out.println("Press [Enter] to continue: ");
            input.nextLine();
        }
    }

    
    public static void main(String[] args) {
        GameMainTest gmt = new GameMainTest();
        gmt.Run();
    }
}
