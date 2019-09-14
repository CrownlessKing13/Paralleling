

//import java.util.Arrays;
import java.util.Scanner;

public class Assigment3{


   public static void main(String[] args){
   Scanner sc = new Scanner(System.in);
   System.out.println("Do you want to test on Sequential or Parallel?");
   String Test = sc.nextLine();
   int testnum = 0;
   if(Test.equals("Sequential")){
      testnum = 1;
   }else if(Test.equals("Parallel")){
      testnum = 2;
   }
   System.out.println("What is name of Inputfile?");
   String fName = sc.nextLine();
   
   System.out.println("What is name of Outputfile?");
   String outfName = sc.nextLine();
   sc.close();
   
   CloudData cd = new CloudData();
   cd.readData(fName);
   
   int x = cd.dimx;
   int y = cd.dimy;
   int t = cd.dimt;
   if(testnum==1){
      Sequential sq = new Sequential(x,y,t,cd);
      tick();
       for (int i = 0; i < 200; i++) {
        cd.windx = sq.xavg(); 
        cd.windy = sq.yavg(); 
        sq.clasify();   
       }      
      float time=tock();
      System.out.println("Time Taken : " + time/200 + " second");
      cd.writeData(outfName);
   }
   
   else if(testnum==2){
      
      parallel.letsparallel(cd);
      cd.writeData(outfName);
      
   }
   
   }
   
   public static long startTime=0;
    
   /**
   * Starts Timer
    */
    public static void tick(){
        startTime=System.currentTimeMillis();
    }
    
     /**
    * @return Time taken since after tick() method is used
  */
    public static float tock(){
        return (System.currentTimeMillis()-startTime)/1000.0f;
    }

}