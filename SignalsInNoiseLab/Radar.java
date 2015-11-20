import java.lang.Math;
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class Radar
{
    private int ROWS;
    private int COLS;
    
    private double TProb = .0001;
    
    // stores whether each cell triggered detection for the current scan of the radar
    // (true represents a detected monster, which may be a false positive)
    private boolean[][] currentScan;
    
    // value of each cell is incremented for each scan in which that cell triggers detection
    private int[][] accumulator;
    
    private boolean[][][] scans;
    
    private boolean Aa = false;
    
    // location of the monster
    private double[] monsterLocationRow;
    private double[] monsterLocationCol;

    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;
    
    // number of scans of the radar since construction
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize the currentScan 2D array and the accumulator 2D array
        
        
        //
        // !!! add code here !!!
        //
        
        
        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method for the unit test
        monsterLocationRow = new double[]{(int)(Math.random() * rows), (int)(Math.random() * rows), (int)(Math.random() * rows)};
        monsterLocationCol = new double[]{(int)(Math.random() * cols), (int)(Math.random() * cols), (int)(Math.random() * cols)};

        ROWS = rows;
        COLS = cols;
        
        scans = new boolean[1000][ROWS][COLS];
        
        currentScan = new boolean[rows][cols];
        accumulator = new int[rows][cols];
        
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * 
     */
    public void scan()
    {
        // algorithm for performing a scan:
        //    1. set all cells in the currentScan 2D array to false
        //    2. set the location of the monster in the currentScan 2D array
        //    3. inject noise into the grid
        //    4. update the accumulator 2D array based on the state of the currentScan 2D array
        //    5. increment the numScans instance variable
        
        
        //
        // !!! add code here !!!
        //
        
        for(int x=0; x<monsterLocationRow.length; x++){
            if((int)(Math.random() / (TProb * monsterLocationRow.length)) == 0){
                monsterLocationRow[x] = (Math.random() * ROWS);
                monsterLocationCol[x] = (Math.random() * COLS);
            }
            setMonsterLocation((int)monsterLocationRow[x], (int)monsterLocationCol[x], x);
        }
        injectNoise();
        Aa = !Aa;
        for(int x=0; x<currentScan.length; x++){
            for(int y=0; y<currentScan[x].length; y++){
                if(currentScan[x][y]) accumulator[x][y] ++;
                if(Aa && accumulator[x][y] > 0) accumulator[x][y] --;
            }
        }
        for(int x=scans.length-1; x>0; x--){
            scans[x] = scans[x - 1];
        }
        scans[0] = currentScan;
        numScans ++;
        
        
    }

    /**
     * Sets the location of the monster
     * 
     * @param   row     the row in which the monster is located
     * @param   col     the column in which the monster is located
     * @pre row and col must be within the bounds of the radar grid
     */
    public void setMonsterLocation(int row, int col, int monsterID)
    {
        // remember the row and col of the monster's location
        monsterLocationRow[monsterID] = row;
        monsterLocationCol[monsterID] = col;
        
        // update the radar grid to show that something was detected at the specified location
        currentScan[row][col] = true;
    }
    
     /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        int CUTOFF = 250;
        for(int x=0; x < scans.length && x <= CUTOFF && scans[x][row][col]; x++){
            if(x >= CUTOFF){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the number of times that the specified location in the radar grid has triggered a
     *  detection since the constructor of the radar object.
     * 
     * @param   row     the row of the location to query for accumulated detections
     * @param   col     the column of the location to query for accumulated detections
     * @return the number of times that the specified location in the radar grid has
     *          triggered a detection since the constructor of the radar object
     */
    public int getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
    
    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }
    
    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    private void injectNoise()
    {
        // Iterate through all cells in the currentScan 2D array to inject noise by setting false positives.
        // The noiseFraction instance variable is the probability that a given cell will be
        // detected as a false positive. Use the Math.random method to determine if each cell should be set
        // as a false positive.
        
        
        //
        // !!! add code here !!!
        //
        currentScan = new boolean[ROWS][COLS];
        for(int x=0; x<currentScan.length; x++){
            for(int y=0; y<currentScan[x].length; y++){
                if((int)(noiseFraction / Math.random()) == 0){
                    currentScan[x][y] = true;
                }
            }
        }
        
        
    }
    
}
