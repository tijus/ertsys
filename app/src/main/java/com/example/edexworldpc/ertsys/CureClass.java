package com.example.edexworldpc.ertsys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Edexworld pc on 3/19/2017.
 */

public class CureClass {
    public List<Integer> scores = new ArrayList<Integer>();
    public List<String> PersonalityType = new ArrayList<String>();
    public List<Integer> previousScores = new ArrayList<Integer>();
    public List<Integer> questionQue = new ArrayList<Integer>();
    public int[] scoreResult = new int [10];
    public int [] que = new int [10];
    public int [] clusteredResult = new int[10];
    public String [] personalityStore  = new String[5];
    public String personalityresult = null;
    public int i = 0;
    public CureClass(List<Integer> scores, List<String> PersonalityType, List<Integer> previousScores, List<Integer> questionQue)
    {
        int j = 0;
        this.scores = scores;
        this.PersonalityType = PersonalityType;
        this.previousScores = previousScores;
        this.questionQue = questionQue;
        scoreResult = getIterator(previousScores);
        personalityStore = getStringIterator(PersonalityType);
        que = getIterator(questionQue);
    }
    public CureClass()
    {}

    public int[] getIterator(List<Integer> itr)
    {
        int i = 0;
        int [] iterarr = new int[10];
        for(int itervalue : itr)
        {
            iterarr[i] = itervalue;
            i++;
        }
        return iterarr;
    }

    public String[] getStringIterator(List<String> itr)
    {
        int i = 0;
        String [] iterarr = new String[5];
        for(String itrvalue : itr)
        {
            iterarr[i] = itrvalue;
            i++;
        }
        return iterarr;
    }

    public void getResult()
    {
        String [] personalitystore = new String[5];

        int i = 0;
        for(int scorevalue : scores)
        {
            String personalityItem = personalityStore[i];
            int scoreItem = scorevalue;
            i++;

         if (personalityItem.equalsIgnoreCase("Introversion"))
         {
             if(scoreItem >= 0) {
                 scoreResult[0] = scoreResult[0] + scoreItem;
                 que[0] = que[0] + scoreItem;

             }
             else
             {
                 scoreResult[1] = scoreResult[1] - scoreItem;
                 que[1] = que[1] - scoreItem;

             }
         }
         else if (personalityItem.equalsIgnoreCase("Extroversion")) {
             if(scoreItem >= 0) {
                 scoreResult[1] = scoreResult[1] + scoreItem;
                 que[1] = que[1] + scoreItem;

             }
             else
             {
                 scoreResult[0] = scoreResult[0] - scoreItem;
                 que[0] = que[0] - scoreItem;

             }
         }
         else if (personalityItem.equalsIgnoreCase("Observant"))
         {
             if(scoreItem >=0) {
                 scoreResult[2] = scoreResult[2] + scoreItem;
                 que[2] = que[2] + scoreItem;

             }
             else
             {
                 scoreResult[3] = scoreResult[3] - scoreItem;
                 que[3] = que[3] - scoreItem;

             }
         }
         else if (personalityItem.equalsIgnoreCase("Intuition"))
         {
             if(scoreItem >= 0) {
                 scoreResult[3] = scoreResult[3] + scoreItem;
                 que[3] = que[3] + scoreItem;

             }
             else
             {
                 scoreResult[2] = scoreResult[2] - scoreItem;
                 que[2] = que[2] - scoreItem;

             }
         }
         else if (personalityItem.equalsIgnoreCase("Feeling"))
        {
            if(scoreItem >= 0) {
                scoreResult[4] = scoreResult[4] + scoreItem;
                que[4] = que[4] + scoreItem;

            }
            else
            {
                scoreResult[5] = scoreResult[5] - scoreItem;
                que[5] = que[5] - scoreItem;

            }
        }
         else if (personalityItem.equalsIgnoreCase("Thinking"))
        {
            if(scoreItem >=0)
            {
            scoreResult[5] = scoreResult[5] + scoreItem;
            que[5] = que[5] + scoreItem;
            }
            else
            {
                scoreResult[4] = scoreResult[4] - scoreItem;
                que[4] = que[4] - scoreItem;

            }

        }
        else if (personalityItem.equalsIgnoreCase("Perception"))
         {
             if(scoreItem >=0)
             {
             scoreResult[6] = scoreResult[6] + scoreItem;
             que[6] = que[6] + scoreItem;
             }
             else
             {
                 scoreResult[7] = scoreResult[7] - scoreItem;
                 que[7] = que[7]  - scoreItem;
             }
         }
         else if (personalityItem.equalsIgnoreCase("Judging"))
        {
            if(scoreItem >=0) {
                scoreResult[7] = scoreResult[7] + scoreItem;
                que[7] = que[7] + scoreItem;

            }
            else
            {
                scoreResult[6] = scoreResult[6] - scoreItem;
                que[6] = que[6]- scoreItem;

            }
        }
         else if (personalityItem.equalsIgnoreCase("Assertive"))
        {
            if(scoreItem >=0) {
                scoreResult[8] = scoreResult[8] + scoreItem;
                que[8] = que[8] + scoreItem;

            }
            else
            {
                scoreResult[9] = scoreResult[9] - scoreItem;
                que[9] = que[9] - scoreItem;

            }
        }
        else if (personalityItem.equalsIgnoreCase("Turbulent"))
        {
            if(scoreItem >=0) {
                scoreResult[9] = scoreResult[9] + scoreItem;
                que[9] = que[9] + scoreItem;

            }
            else
            {
                scoreResult[8] = scoreResult[8] - scoreItem;
                que[8] = que[8] - scoreItem;

            }
        }
    }

    }
    public float Clustering(int scResult, int ques, int contestingQues)
    {
        return (((float)scResult) / (((float)ques) + ((float)contestingQues))*100);
    }

    public float[] getClusteredResult(int[] scResult, int[] quesResult)
    {
        float[] clusteredResult = new float[10];
        try {

            if(quesResult[0]!=0 || quesResult[1]!=0)
            {
                clusteredResult[0] = Clustering(scResult[0],quesResult[0],quesResult[1]);
                clusteredResult[1] = Clustering(scResult[1],quesResult[1],quesResult[0]);
            }
            if(quesResult[2]!=0 || quesResult[3]!=0)
            {
                clusteredResult[2] = Clustering(scResult[2],quesResult[2],quesResult[3]);
                clusteredResult[3] = Clustering(scResult[3],quesResult[3],quesResult[2]);
            }
            if(quesResult[4]!=0 || quesResult[5]!=0)
            {
                clusteredResult[4] = Clustering(scResult[4],quesResult[4],quesResult[5]);
                clusteredResult[5] = Clustering(scResult[5],quesResult[5],quesResult[4]);
            }
            if(quesResult[6]!=0 || quesResult[7]!=0)
            {
                clusteredResult[6] = Clustering(scResult[6],quesResult[6],quesResult[7]);
                clusteredResult[7] = Clustering(scResult[7],quesResult[7],quesResult[6]);
            }
            if(quesResult[8]!=0 || quesResult[9]!=0)
            {
                clusteredResult[8] = Clustering(scResult[8],quesResult[8],quesResult[9]);
                clusteredResult[9] = Clustering(scResult[9],quesResult[9],quesResult[8]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return clusteredResult;
    }

    public int[] getQue()
    {
        return que;
    }

    public int[] getScoreResult()
    {
        return scoreResult;
    }

    public String getPersonalityTrait(float [] clusteredResult)
    {
        String result = "";
        if (clusteredResult[0] >= clusteredResult[1]) {
            result = "I" ;
        } else {
            result = "E" ;
        }
        if (clusteredResult[2] >= clusteredResult[3]) {
            result = result + "S" ;
        } else {
            result = result + "N" ;
        }
        if (clusteredResult[4] >= clusteredResult[5]) {
            result = result + "F" ;
        } else {
            result = result + "T" ;
        }
        if (clusteredResult[6] >= clusteredResult[7]) {
            result = result + "P" ;
        } else {
            result = result + "J" ;
        }
        if (clusteredResult[8] >= clusteredResult[9]) {
            result = result + "-A" ;
        } else {
            result = result + "-T" ;
        }
        return result;
    }
}
