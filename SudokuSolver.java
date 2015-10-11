package Practice.SudokuProject;

import java.io.*;

public class SudokuSolver 
{
    enum Status {VALID,INVALID};
    enum Row_Col{SAFE, NOT_SAFE};
    
   int puzzle[][]={}; 
   
   int data[];  

   SudokuSolver() //Default constructor
   {
       System.out.println("Default called.");
        data = new int[9];//
        for(int i=0;i < 9;i++)
        {
            data[i]=i+1;
            //System.out.print(data[i]+" ");
        }
        System.out.println("");
       MySet all = new MySet(data);
   } 
   
   SudokuSolver(int p[][]) //parameterize constructor
{    
    this();
    System.out.println("Parametrized called.");
    
    puzzle = new int[9][9]; //memory allocation
    puzzle = p;
    for(int i=0;i < 9;i++)
    System.out.println(data[i]);
}
  

   Row_Col isSafe(int x[][], int row, int col)//If value of x[row][col] is not existing in full row and col then SAFE
   {
       Row_Col ans = Row_Col.SAFE;
       
       int check = x[row][col];//row=6 ; col = 4
       
             for (int i = 0; i < 9 ; i++) //for Row
                {
                    if (check == x[row][i] && i!=col) 
                    {
                        ans = Row_Col.NOT_SAFE;
                    }
                }
             for (int i = 0; i < 9 ; i++) //for Column
                {
                    if (check == x[i][col] && i!=row) 
                    {
                        ans = Row_Col.NOT_SAFE;
                    }
                }
            
        return ans;
    }
   
   Status verify_3x3Matrix(int x[][], int row, int col)//TO verify number is valid for internal 3x3 Matrix
   {
       int check = x[row][col];//row = 6 ; col = 4
       
       int base_row = 3 * (row / 3);// 3 * ( 6 / 3 ) = 3 * 2 = 6
       int base_col = 3 * (col / 3);// 3 * ( 4 / 3 ) = 3 * 1 = 3
       for(int i = 0; i < 3; ++i)
       {
           int row1 = base_row + i;
           for(int j=0; j<3; ++j)               
           {
               int col1 = base_col + j;
               
               if(row1 != row && col1 != col && check == x[row1][col1])
               {
                   return Status.INVALID;
               }
                           
           }               
       }
       return Status.VALID;
   }
   
   
   
Status verify(int x[][])
{
    Status ans = Status.INVALID;
    Row_Col rc = Row_Col.NOT_SAFE;
    
    for (int row = 0; row < 9; row++) 
    {
        for (int col = 0; col < 9; col++) 
        {
            if (x != null) 
            {
                rc = isSafe(x, row, col);
                if (rc == Row_Col.SAFE) 
                {
                    ans = verify_3x3Matrix(x, row, col);
                }
            }

        }
    }
 
    return ans;
}

void solvePuzzle(int puzz[][])
{
    int temp[][] = puzz;
    
    
    
}

   void printPuzzle()
   {
       for (int i = 0; i < 9; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                    if(j==0)
                    System.out.print("|");
                    System.out.print( puzzle[i][j]);
                    if(j==2 || j==5 || j==8)
                    System.out.print("|");
            }
            System.out.println(" ");
            if(i==2||i==5||i==8)
                System.out.println("----+---+---+");
        }
   }

    public static void main(String[] args) 
    {
      int data[][]={
                       {4,0,0,   0,0,0,   8,0,5},
                       {0,3,0,   0,0,0,   0,0,0}, 
                       {0,0,0,   7,0,0,   0,0,0},
                       
                       {0,2,0,   0,0,0,   0,6,0},
                       {0,0,0,   0,8,0,   4,0,0},
                       {0,0,0,   0,1,0,   0,0,0},
                       
                       {4,0,0,   6,0,3,   0,7,0},
                       {5,0,0,   2,0,0,   0,0,0},
                       {1,0,4,   0,0,0,   0,0,0},
                      };
      System.out.println("Making s1 ...");
      SudokuSolver s1= new SudokuSolver();
      System.out.println("Making s ...");
      SudokuSolver s= new SudokuSolver(data);
      
      
      s.printPuzzle();
      Status ans = s.verify(data);
        System.out.println("Status :"+ans);
}
}
