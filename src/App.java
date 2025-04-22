//*
import java.util.concurrent.*;

public class App {
    // global variables
    static double r = 0; // track # of times it looped
    static int TA = 300; // ambient temperature
    static double t = 1.00; // time
    static final int X = 50, Y = 50, Z = 50;
    static final int THRESHOLD = 10;  // Threshold to stop splitting
    static double[][][] T = new double[X][Y][Z]; // original array
    static double[][][] DT= new double[X][Y][Z]; // change array
 
    public static void main(String[] args){
        // Declerations
        init(T); // initialize the original array with starting temp
        while(T[25][25][25]<TA-10){
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(new ArrayTask(0,X));
            pool.shutdown();

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
            System.out.println("r = "+r+" | T[25][25][25] = "+T[25][25][25]);
        }

        System.out.println("Processing complete:\n\ttotal r = "+r);
    }

    static class ArrayTask extends RecursiveAction{
        int startX,endX;

        ArrayTask(int startX, int endX){
            this.startX = startX;
            this.endX = endX;
        }

        @Override
        protected void compute(){
            if (endX - startX <= THRESHOLD) {
                // Small enough to process directly
                for (int x = startX; x < endX; x++) {
                    for (int y = 0; y < Y; y++) {
                        for (int z = 0; z < Z; z++) {
                            computeValue(x, y, z);
                        }
                    }
                }
            } else {
                // Split task
                int mid = (startX + endX) / 2;
                ArrayTask left = new ArrayTask(startX, mid);
                ArrayTask right = new ArrayTask(mid, endX);
                invokeAll(left, right);  // Fork both subtasks
            }
        }

        private void computeValue(int x, int y, int z) {
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

    // sets array to have 72 starting temp
    private static void init(double[][][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                for(int k=0;k<a[j].length;k++){
                    a[i][j][k] = 72.00;
                }
            }
        }
        return;
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
        return;
    }

    /* // This is used to print the array for testing purposes and so on.
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
//*/

 // Single Thread test
/*
 public class App {
    // global variables
    public static double r = 0; // track # of times it looped
    public static int TA = 300; // ambient temperature
    public static double t = 1.00; // time
 
    public static void main(String[] args){
        // Declerations
        double[][][] T = new double[50][50][50]; // original array
        double[][][] DT= new double[50][50][50]; // change array
        init(T); // initialize the original array with starting temp

        // loops through 3D array and does operations based on the indece's location on the array
        while(T[25][25][25]<TA-10){
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
            System.out.println("r = "+r+" | T[25][25][25] = "+T[25][25][25]);
        } // end loop

        //print(T);
        System.out.println("total r = "+r);
    }


// sets array to have 72 starting temp
    private static void init(double[][][] a){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                for(int k=0;k<a[j].length;k++){
                    a[i][j][k] = 72.00;
                }
            }
        }
        return;
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
        return;
    }

    
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
    

} 
*/
