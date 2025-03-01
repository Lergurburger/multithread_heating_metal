//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

/* 
class Task implements Runnable{
    private int x;
    private int y; 
    
    public Task(int sent_x,int sent_y,sent_T,sent_DT) 
    { 
        x = sent_x; 
        y = sent_y;
        T = sent_T;
    } 

    public void run() 
    {
        try
        { 
            double change = 0;
            for(int z=0;z<T[y].length;z++){
                // X value
                if(x==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x-1][y][z] - T[x][y][z]);}
                if(x==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x+1][y][z] - T[x][y][z]);}
                // Y value
                if(y==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x][y-1][z] - T[x][y][z]);}
                if(y==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x][y+1][z] - T[x][y][z]);}
                // Z value
                if(z==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x][y][z-1] - T[x][y][z]);}
                if(z==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                else{ change += 0.01 * t * (T[x][y][z+1] - T[x][y][z]);}
                // critical region
                synchronized(DT){
                    DT[x][y][z] += change;
                    change = 0;
                } 
            }
            
        } 
        
        catch(Exception e) 
        { 
            e.printStackTrace(); 
        }
    }
}
*/

public class App {
    // global variables
    public static double r = 0; // track # of times it looped
    public static int TA = 300; // ambient temperature
    public static double t = 0.10; // time
    public static double[][][] T = new double[10][10][10]; // original array
    public static double[][][] DT= new double[10][10][10]; // change array
    //static final int MAX_T = 10;
 
    public static void main(String[] args){
        // Declerations
        double[][][] T = new double[10][10][10]; // original array
        double[][][] DT= new double[10][10][10]; // change array
        init(T); // initialize the original array with starting temp
    /*

        //ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        while(T[5][5][5]<TA-10){
            for(int x=0;x<T.length;x++){ 
                for(int y=0;y<T[x].length;y++){
                    //Runnable r = new Task(x,y); 
                    //pool.execute(r);
                }
            }

            for(int x=0;x<T.length;x++){
                for(int y=0;y<T[x].length;y++){
                    for(int z=0;z<T[y].length;z++){
                        T[x][y][z] += DT[x][y][z];
                    }
                }
            }

            r++;
            reset(DT);

            System.out.println("r = "+r+" | T[5][5][5] = "+T[5][5][5]);
        }
        //print(T);
        System.out.println("total r = "+r);
    }
    */
 
// single thread test
//*
        // loops through 3D array and does operations based on the indece's location on the array
        while(T[5][5][5]<TA-10){
            for(int x=0;x<T.length;x++){ 
                for(int y=0;y<T[x].length;y++){
                    for(int z=0;z<T[y].length;z++){
                        // X value
                        if(x==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x-1][y][z] - T[x][y][z]);}
                        if(x==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x+1][y][z] - T[x][y][z]);}
                        // Y value
                        if(y==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y-1][z] - T[x][y][z]);}
                        if(y==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y+1][z] - T[x][y][z]);}
                        // Z value
                        if(z==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y][z-1] - T[x][y][z]);}
                        if(z==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y][z+1] - T[x][y][z]);}
                    }
                }
            }

            // Add changes to original array
            for(int x=0;x<T.length;x++){
                for(int y=0;y<T[x].length;y++){
                    for(int z=0;z<T[y].length;z++){
                        T[x][y][z] += DT[x][y][z];
                    }
                }
            }
            r++;
            reset(DT);
            //System.out.println("r = "+r+" | T[5][5][5] = "+T[5][5][5]);
        } // end loop

        //print(T);
        System.out.println("total r = "+r);
    }
//*/

// sets array to have 72 starting temp
    private static void init(double[][][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                for(int k=0;k<a[j].length;k++){
                    a[i][j][k] = 72.00;
                }
            }
        }
    }

    // resets array back to zero for next loop
    private static void reset(double[][][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                for(int k=0;k<a[j].length;k++){
                    a[i][j][k] = 0.00;
                }
            }
        }
    }

/*
      private static void print(double[][][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                for(int k=0;k<a[j].length;k++){
                    System.out.println("["+i+"]["+j+"]["+k+"] = "+a[i][j][k]);
                }
            }
        }
        System.out.println("------------------------");
        return;
    }
 */

}

/*
             for(int x=0;x<T.length;x++){ 
                for(int y=0;y<T[x].length;y++){
                    for(int z=0;z<T[y].length;z++){
                        // X value
                        if(x==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x-1][y][z] - T[x][y][z]);}
                        if(x==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x+1][y][z] - T[x][y][z]);}
                        // Y value
                        if(y==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y-1][z] - T[x][y][z]);}
                        if(y==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y+1][z] - T[x][y][z]);}
                        // Z value
                        if(z==0){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y][z-1] - T[x][y][z]);}
                        if(z==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                        else{ DT[x][y][z] += 0.01 * t * (T[x][y][z+1] - T[x][y][z]);}
                    }
                }
            }
 */

 // Old multithreading test
 /*
  // multithreading test
        //*
        Runnable r1 = () -> {
                        // loops through 3D array and does operations based on the indece's location on the array
            while(T[5][5][5]<TA-10){
                double change = 0;
                for(int x=0;x<5;x++){ 
                    for(int y=0;y<5;y++){
                        for(int z=0;z<5;z++){
                            // X value
                            if(x==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x-1][y][z] - T[x][y][z]);}
                            if(x==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x+1][y][z] - T[x][y][z]);}
                            // Y value
                            if(y==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y-1][z] - T[x][y][z]);}
                            if(y==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y+1][z] - T[x][y][z]);}
                            // Z value
                            if(z==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y][z-1] - T[x][y][z]);}
                            if(z==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y][z+1] - T[x][y][z]);}
                            // critical region
                            synchronized(DT){
                                DT[x][y][z] += change;
                                change = 0;
                            } 
                        }
                    }
                }
                synchronized(T){
                    // Add changes to original array
                    for(int x=0;x<5;x++){
                        for(int y=0;y<5;y++){
                            for(int z=0;z<5;z++){
                                T[x][y][z] += DT[x][y][z];
                            }
                        }
                    }
                r+=.5;
                reset(DT);
                }
                System.out.println("r = "+r+" | T[5][5][5] = "+T[5][5][5]);
            } // end loop

            System.out.println("total r = "+r);
        };       
        
        Runnable r2 = () -> {
            // loops through 3D array and does operations based on the indece's location on the array
            while(T[5][5][5]<TA-10){
                double change = 0;
                for(int x=5;x<T.length;x++){ 
                    for(int y=5;y<T[x].length;y++){
                        for(int z=5;z<T[y].length;z++){
                            // X value
                            if(x==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x-1][y][z] - T[x][y][z]);}
                            if(x==(T.length-1)){ DT[x][y][z] += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x+1][y][z] - T[x][y][z]);}
                            // Y value
                            if(y==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y-1][z] - T[x][y][z]);}
                            if(y==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y+1][z] - T[x][y][z]);}
                            // Z value
                            if(z==0){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y][z-1] - T[x][y][z]);}
                            if(z==(T.length-1)){ change += 0.012 * t * (TA - T[x][y][z]);}
                            else{ change += 0.01 * t * (T[x][y][z+1] - T[x][y][z]);}
                            // critical region
                            synchronized(DT){
                                DT[x][y][z] += change;
                                change = 0;
                            } 
                        }
                    }
                }
                synchronized(T){
                    // Add changes to original array
                    for(int x=(T.length)/2;x<T.length;x++){
                        for(int y=(T[x].length)/2;y<T[x].length;y++){
                            for(int z=(T[y].length)/2;z<T[y].length;z++){
                                T[x][y][z] += DT[x][y][z];
                            }
                        }
                    }
                r+=.5;
                reset(DT);
                }
                System.out.println("r = "+r+" | T[5][5][5] = "+T[5][5][5]);
            } // end loop

            System.out.println("total r = "+r);
        }; 
  */