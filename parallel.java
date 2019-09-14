

import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;


public class parallel{
    static final ForkJoinPool fjPool = new ForkJoinPool();
    
    
	static Vector invokeP(CloudData cd,int dim){
            return fjPool.invoke(new ParallelSum(cd,0,dim));
        //return fjPool.invoke(new ParallelSum(cd,0,dim));
	}
       
        /**
   * @param cd obj for CloudData
  */
    public static void letsparallel(CloudData cd) {
        Vector avg=new Vector();
        int dim=cd.dim();
        float time=0;
        tick();
        for(int i=0;i<200;i++){
            avg=invokeP(cd,dim);
             }
        time=tock();
        System.out.println("Time Taken : " + time/200 + " second");
        cd.windx=avg.x/cd.dim();
        cd.windy=avg.y/cd.dim();
        
        
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