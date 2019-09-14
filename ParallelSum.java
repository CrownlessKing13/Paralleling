


import java.util.concurrent.RecursiveTask;

public class ParallelSum extends RecursiveTask <Vector>  {
	  int lo; // arguments
	  int hi;
	  CloudData cd;
          int[] ind=new int[3];
          double allx=0;
          double ally=0;
	  static final int SEQUENTIAL_CUTOFF=1000;

	  int ans = 0; // result 
	  
           /**
  * @param CloudData Obj from CloudData class
  * @param lo lowest integer for index
  * @param dim Highest integer it can go which is dimension of 3D array
    */
	  ParallelSum(CloudData cd,int lo,int dim) { 
	    this.cd = cd;
            this.lo = lo;
            this.hi = dim;
             }


	  protected Vector compute(){
		  if((hi-lo) < SEQUENTIAL_CUTOFF) {
                      for(int i=lo; i < hi; i++){
		        cd.locate(i, ind);
                        int t=ind[0];
                        int x=ind[1];                        
                        int y=ind[2];
                        allx+=cd.advection[t][x][y].x;                        
                        ally+=cd.advection[t][x][y].y;
                        Clasify(t,x,y,cd);                        
                      }
                        return new Vector(allx,ally);
			
		  }
		  else {
			  ParallelSum left = new ParallelSum(cd,lo,(hi+lo)/2);
			  ParallelSum right= new ParallelSum(cd,(hi+lo)/2,hi);
			  
			  
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
			  return new Vector(rightAns.x+leftAns.x,rightAns.y+leftAns.y);     
		  }
	 }
          
           /**
  * @param t Time Step
  * @param x Height
  * @param y Width
  * @param cd obj from CloudData
  */
   public static void Clasify(int t,int x,int y,CloudData cd){
       int dimx = cd.dimx;
       int dimy = cd.dimy;
       double avg=0;
              
       if(x==0||y==0||x==dimx-1||y==dimy-1){
                     if(x==0){
                        if(y==0){
                        //0,0 0,1 1,0 1,1 4 
                           double x1 = cd.advection[t][0][0].x+cd.advection[t][0][1].x+cd.advection[t][1][0].x+cd.advection[t][1][1].x;
                           x1=x1/4;
                           double y1 = cd.advection[t][0][0].y+cd.advection[t][0][1].y+cd.advection[t][1][0].y+cd.advection[t][1][1].y;
                           y1=y1/4;                           
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));
                                           
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                            
                        }else if(y==dimy-1){
                        //0,dimy-1 0,dimy-2 1,dimy-1 1,dimy-2 4
                        double x1 = cd.advection[t][0][y].x+cd.advection[t][0][y-1].x+cd.advection[t][1][y].x+cd.advection[t][1][y-1].x;
                        x1=x1/4;
                        double y1 = cd.advection[t][0][y].y+cd.advection[t][0][y-1].y+cd.advection[t][1][y].y+cd.advection[t][1][y-1].y;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));            
                                          
                           
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                             cd.classification[t][x][y]=2;
                           }
                        }else{
                        //0,y-1 0,y 0,y+1 1,y-1 1,y 1,y+1 6
                        double x1 = cd.advection[t][0][y-1].x+cd.advection[t][0][y].x+cd.advection[t][0][y+1].x+cd.advection[t][1][y-1].x+cd.advection[t][1][y].x+cd.advection[t][1][y+1].x;
                           x1=x1/6;
                           double y1 = cd.advection[t][0][y-1].y+cd.advection[t][0][y].y+cd.advection[t][0][y+1].y+cd.advection[t][1][y-1].y+cd.advection[t][1][y].y+cd.advection[t][1][y+1].y;
                           y1=y1/6;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));                          
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                        }
                        
                     }else if(x==dimx-1){
                        if(y==0){
                        //dimx-2,0 dimx-2,1 dimx-1,0 dimx-1,1 4  
                        double x1 = cd.advection[t][x-1][0].x+cd.advection[t][x-1][1].x+cd.advection[t][x][0].x+cd.advection[t][x][1].x;
                        x1=x1/4;
                        double y1 = cd.advection[t][x-1][0].y+cd.advection[t][x-1][1].y+cd.advection[t][x][0].y+cd.advection[t][x][1].y;
                        y1=y1/4;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));    
                           //System.out.println(avg+"  "+Math.abs(convection[t][x][y])+" lmao");                      
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           } 
                        }else if(y==dimy-1){
                        //dimx-2,dimy-1 dimx-2,dimy-2 dimx-1,dimy-1 dimx-1,dimy-2 4
                        double x1 = cd.advection[t][x-1][y].x+cd.advection[t][x-1][y-1].x+cd.advection[t][x][y].x+cd.advection[t][x][y-1].x;
                        x1=x1/4;
                        double y1 = cd.advection[t][x-1][y].y+cd.advection[t][x-1][y-1].y+cd.advection[t][x][y].y+cd.advection[t][x][y-1].y;
                        y1=y1/4;  
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));      
                           //System.out.println(avg+"  "+Math.abs(convection[t][x][y])+"asd");                     
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                        }else{
                        //dimx-2,y-1 dimx-2,y dimx-2,y+1 dimx-1,y-1 dimx-1,y dimx-1,y+1 6
                        double x1 = cd.advection[t][x-1][y-1].x+cd.advection[t][x-1][y].x+cd.advection[t][x-1][y+1].x+cd.advection[t][x][y-1].x+cd.advection[t][x][y].x+cd.advection[t][x][y+1].x;
                        x1=x1/6;
                        double y1 = cd.advection[t][x-1][y-1].y+cd.advection[t][x-1][y].y+cd.advection[t][x-1][y+1].y+cd.advection[t][x][y-1].y+cd.advection[t][x][y].y+cd.advection[t][x][y+1].y;
                        y1= y1/6;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));                           
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                        }
                     }else if(y==0){
                        //x-1,0 x-1,1 x,0 x,1 x+1,0 x+1,1 6
                        double x1 = cd.advection[t][x-1][y].x+cd.advection[t][x-1][y+1].x+cd.advection[t][x][y].x+cd.advection[t][x][1].x+cd.advection[t][x+1][y].x+cd.advection[t][x+1][y+1].x;
                        x1=x1/6;
                        double y1 = cd.advection[t][x-1][y].y+cd.advection[t][x-1][y+1].y+cd.advection[t][x][y].y+cd.advection[t][x][1].y+cd.advection[t][x+1][y].y+cd.advection[t][x+1][y+1].y;
                        y1=y1/6;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));                           
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                     
                     }else{
                        //x-1,dimy-2 x-1,dimy-1 x,dimy-2 x,dimy-1 x+1,dimy-2 x+1,dimy-1 6
                        double x1 = cd.advection[t][x-1][y-1].x+cd.advection[t][x-1][y].x+cd.advection[t][x][y-1].x+cd.advection[t][x][y].x+cd.advection[t][x+1][y-1].x+cd.advection[t][x+1][y].x;
                           x1=x1/6;
                        double y1 = cd.advection[t][x-1][y-1].y+cd.advection[t][x-1][y].y+cd.advection[t][x][y-1].y+cd.advection[t][x][y].y+cd.advection[t][x+1][y-1].y+cd.advection[t][x+1][y].y;
                           y1=y1/6;
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));                            
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }
                     }
                     
                  }else
                  {
                   //x-1,y-1 x-1,y x-1,y+1 x,y-1 x,y x,y+1 x+1,y-1 x+1,y x+1,y+1 9
                   double x1 = cd.advection[t][x-1][y-1].x+cd.advection[t][x-1][y].x+cd.advection[t][x-1][y+1].x+cd.advection[t][x][y-1].x+cd.advection[t][x][y].x+cd.advection[t][x][y+1].x+cd.advection[t][x+1][y-1].x+cd.advection[t][x+1][y].x+cd.advection[t][x+1][y+1].x;
                          x1=x1/9;
                   double y1 = cd.advection[t][x-1][y-1].y+cd.advection[t][x-1][y].y+cd.advection[t][x-1][y+1].y+cd.advection[t][x][y-1].y+cd.advection[t][x][y].y+cd.advection[t][x][y+1].y+cd.advection[t][x+1][y-1].y+cd.advection[t][x+1][y].y+cd.advection[t][x+1][y+1].y;
                          y1=y1/9; 
                           avg=Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2));                           
                           if(Math.abs(cd.convection[t][x][y])>avg){
                              cd.classification[t][x][y]=0;
                           }else if(avg>0.2&&Math.abs(cd.convection[t][x][y])<=avg){
                              cd.classification[t][x][y]=1;
                           }else{
                              cd.classification[t][x][y]=2;
                           }

                  }
   
   }
          
          
          
          
          
}



    

