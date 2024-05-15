package GAMESTAGE;

import java.util.List;

public class BossStage extends GameStageTest
{
    private int[] mapManager;



//------------------------------------------------------

    public BossStage(int stageNo, int currentMapNo, int[] mapIndex)
    {
        super(stageNo, currentMapNo);
        this.mapManager = mapIndex;
    }

    public String toString()
    {
        String str = "mapManager: " + this.mapManager[0] + "---" + this.mapManager[1];
        return str;
    }


}
