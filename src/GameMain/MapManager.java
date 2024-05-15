package GameMain;


public class MapManager 
{
    private int numStages;
    private int[] numMapInStage;
    

//-------------------------------------------------------------

    //Constructor
    public MapManager(int numStages, int[] numMapInStage)
    {
        this.numStages = numStages;
        this.numMapInStage = numMapInStage;
    }

    public int getCorrespondingStageNo(int mapIndex)
    {
        int stage = -1;

        boolean found = false;
        int i = 0;
        
        int begInterval = 1;
        int endInvenval;
        while(!found && i < numStages)
        {
            endInvenval = begInterval + numMapInStage[i] - 1;
            if(begInterval <= mapIndex && mapIndex <= endInvenval)
            {
                stage = i + 1;
                found = true;
            }
            begInterval = endInvenval + 1;
            i++;
        }
        return stage;
    }


    public int getCorrespondingPhaseNo(int mapIndex)
    {
        int currentStageNo = getCorrespondingStageNo(mapIndex);
        int phaseNo = -1;

        if(this.numMapInStage[currentStageNo - 1] > 1)
        {
            phaseNo = mapIndex - indexOfFrontMapInStage(currentStageNo) + 1;
        }
        return phaseNo;
    }


    public int indexOfFrontMapInStage(int stageNo)
    {
        int frontMap_Index = -1;
        if(1 <= stageNo && stageNo <= this.numStages)
        {
            frontMap_Index = indexOfRearMapInStage(stageNo) - this.numMapInStage[stageNo - 1] + 1;
        }
        return frontMap_Index;
    }


    public int indexOfRearMapInStage(int stageNo)
    {
        int rearMap_Index = -1;
        if(1 <= stageNo && stageNo <= this.numStages)
        {
            rearMap_Index = 0;
            for(int i = 0; i < stageNo; i++)
            {
                rearMap_Index += this.numMapInStage[i];
            }
        }
        return rearMap_Index;
    }


    public int numberOfMaps()
    {
        int sum = 0;
        for(int i = 0; i < numStages; i++)
        {
            sum += numMapInStage[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        MapManager M = new MapManager(5, new int[]{4,5,2,3,3});
        System.out.println(M.getCorrespondingStageNo(7));
        System.out.println(M.getCorrespondingPhaseNo(7));
    }
}
