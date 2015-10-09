package Practice;

import java.io.*;

public class SudokuSolver 
{
    enum Status {VALID,INVALID};
    enum Row_Col{SAFE, NOT_SAFE};
    
   int puzzle[][]={}; 
   
   int data[]={1,2,3,4,5,6,7,8,9};

   SudokuSolver() //Default constructor
   {} 
   
   SudokuSolver(int p[][]) //parameterize constructor
{
    puzzle = new int[9][9]; //memory allocation
    puzzle = p;
}

   Row_Col isSafe(int x[][], int row, int col)//If value of x[row][col] is not existing in full row and col then SAFE
   {
       Row_Col ans= Row_Col.SAFE;
       
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
   
   Status verify_3x3Matrix2(int x[][], int row, int col)
   {
       Status ans = Status.VALID;
       int start_row =0, end_row = 0;
       int start_col=0, end_col = 0;
       
       int check = x[row][col];
       for (int i = 0; i < row; i++)//For Row 
       {
           if(i<=2 && i==row)
           {
               start_row = 0;
               end_row = 2;
           }
           else if(i>2 && i<=5 && i==row)
               {
                 start_row = 3;
                 end_row = 5;
               }
           else if(i>5 && i<=8 && i==row)
               {
                 start_row = 5;
                 end_row = 8;
               }
       }
       for(int i = 0 ;i < col; i++) //For Column
       {
           if(i <= 2 && i== col)
           {
               start_col = 0;
               end_col = 2;
           }
           else if(i > 2 && i <= 5 && i==row)
               {
                 start_col = 3;
                 end_col = 5;
               }
           else if(i > 5 && i <= 8 && i==row)
               {
                 start_col = 6;
                 end_col = 8;
               }
        }
       
       for(int r = start_row; r <= end_row; r++)//for verifying
       {
           for(int c=start_col; c<= end_col; c++)
           {
                  if(x[r][c]==check && x[r][c]!=x[row][col])
                  {
                      ans = Status.INVALID;
                  }
           }
           
       }
       
       return ans;
   }
   
Status verify(int x[][] , int row, int col)
{
    Status ans = Status.INVALID;
    Row_Col rc = Row_Col.NOT_SAFE;
    if(x!= null)
    {
        rc = isSafe(x, row, col);
        if(rc==Row_Col.SAFE)
        {
            ans = verify_3x3Matrix(x, row, col);
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
                       
                       {0,0,0,   6,0,3,   0,7,0},
                       {5,0,0,   2,0,0,   0,0,0},
                       {1,0,4,   0,0,0,   0,0,0},
                      };
      SudokuSolver s= new SudokuSolver(data);
      s.printPuzzle();
    
}
}
