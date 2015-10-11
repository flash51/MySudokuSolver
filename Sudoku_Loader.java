package Practice;

import java.io.*;

public class Sudoku_Loader
{ 
    static byte AllPuzzles[][]={}; //initialization
    static byte puzzle[][] = new byte[9][9];
    static int len = 0;
    boolean valid= true;
    
    Sudoku_Loader(){}  //deafult constructor
       
    void getSudoku(byte b[])
        {
             len = b.length / 81;
            System.out.println("Number of Sudoku Puzzles :"+len);  
            
            AllPuzzles = new byte[len][]; //Memory Allocation 
            
            for(int i=0; i < len;i++)
            {
                AllPuzzles[i]= new byte[81]; //Memory allocation of internal array
                for(int j=0;j<81;j++)
                {
                    if( b[81*i+j] == '.' || b[81*i+j]>'0' && b[81*i+j]<='9')
                    {
                        AllPuzzles[i][j]=b[81*i+j];
                    }
                }
            }
            
        }
    
    
    byte[][] getPuzzle(byte b[][],int pos)
    {
        if (pos >= len) 
        { 
            valid=false;
            System.out.println("Invalid !!!! ");
        } 
        else 
        {
                           // puzzle = new byte[9][9]; //memory allocation
            for (int i = 0; i < 9; i++) 
            {
                for (int j = 0; j < 9; j++) {
                    puzzle[i][j] = b[pos][9 * i + j];
                }
            }
        }
        return puzzle;
    }
    
    void printPuzzle()
    {
        if(valid == true);
        {
            System.out.println("----+---+---+");
       for (int i = 0; i < 9; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                    if(j==0)
                    System.out.print("|");
                    System.out.print((char) puzzle[i][j]);
                    if(j==2 || j==5 || j==8)
                    System.out.print("|");
            }
            System.out.println(" ");
            if(i==2||i==5||i==8)
                System.out.println("----+---+---+");
        }
        }
    }
    
    
   void printAllPuzzles()
   {
       for(int i=0; i<AllPuzzles.length;i++)
       {
           for(int j=0;j<AllPuzzles[i].length;j++)
           {
                System.out.print((char)AllPuzzles[i][j]);            
           }
           System.out.println(" ");
       }
   }
   
  
   
    public static void main(String[] args) throws IOException
    {
        FileInputStream fis = new FileInputStream("/home/ankur/Downloads/msk_009.txt");
        
        byte b[]= new byte[fis.available()];
        fis.read(b);
        
        Sudoku_Loader s = new Sudoku_Loader();
        s.getSudoku(b);
      // s.printAllPuzzles();
        s.getPuzzle(AllPuzzles,0);
        s.printPuzzle();
        
    }

    
}
