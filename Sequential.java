

import java.lang.Math;
//import java.text.DecimalFormat;
public class Sequential{
      int dimx, dimy, dimt;
      CloudData cd;
      
       /**
  * @param dimx Maximum Height
  * @param dimy Maximum Width
  * @param dimt Maximum TimeStep
  * @param cd   obj from CloudData
  */
      public Sequential(int dimx,int dimy, int dimt, CloudData cd){
      this.dimx = dimx;
      this.dimy = dimy;
      this.dimt = dimt;
      this.cd = cd;
      
      }
   
       /**
   * @return Returns X Global average
  */
      public double xavg(){
      double avg=0;
      int c = 0;
      for(int t = 0; t < dimt; t++){
				 for(int x = 0; x < dimx; x++){
					for(int y = 0; y < dimy; y++){
						avg+=cd.advection[t][x][y].x;
                  c++;
					}
				 }
		     }
      avg = avg/c;
      //DecimalFormat df = new DecimalFormat("#.00000");
      //avg = Double.parseDouble(df.format(avg));
      return avg;
      }
      
      /**
   * @return Returns Y Global average
  */
      public double yavg(){
      double avg=0;
      int c = 0;
      for(int t = 0; t < dimt; t++){
				 for(int x = 0; x < dimx; x++){
					for(int y = 0; y < dimy; y++){
						avg+=cd.advection[t][x][y].y;
                  c++;
					}
				 }
		     }
      avg = avg/c;
      //DecimalFormat df = new DecimalFormat("#.000");
      //avg = Double.parseDouble(df.format(avg));
      return avg;
      }
      
      /**
   *  Clasifies winds 
  */
      public void clasify(){
      double avg=0;
       for(int t = 0; t < dimt; t++){
				 for(int x = 0; x < dimx; x++){
					for(int y = 0; y < dimy; y++){
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
		     }
           
                
      }
      
      




}