package sample;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class Calculate {
static int numDraws;



    private static LocalDate calcPrevFriday(LocalDate d) {
        return d.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
    }


    public static int[]  getAvgOfRecent(){



            LocalDate[] drawDates = new LocalDate[numDraws] ;
            int[][] regularSet = new int[numDraws][8];
            int[] avgSet = new int[8];
            //Use this for testing
            LocalDate today = LocalDate.of(2014,1,31);
            //Use this for actual current date
            //LocalDate today = LocalDate.now();



            for (int count = 0; count< numDraws;count++){
                System.out.println("Iteration:" + count);
                if(count == 0){
                    drawDates[0] = calcPrevFriday(today);
                }else{
                    drawDates[count] = calcPrevFriday(drawDates[count-1]);
                }
                System.out.println( "Date:" + drawDates[count]);

            }

            for (int count = 0; count < numDraws;count++){
                regularSet[count] = restApi.getWinningNums(drawDates[count]);
            }

            //calculate the average
            for (int i = 0 ; i< numDraws;i++){
                //   avgSet[i] = Integer.parseInt(regularSet[i]);
                for (int j = 0; j < avgSet.length;j++){
                    avgSet[j] += regularSet[i][j];
                }
            }
        System.out.println("Number Totals: " + avgSet[0]);
            System.out.println("Averages for Numbers: ");
            for( int it = 0; it <avgSet.length;it++ ){
                avgSet[it] = avgSet[it] / numDraws;
                System.out.print(avgSet[it] +",");

            }

          return avgSet;



    }

}
