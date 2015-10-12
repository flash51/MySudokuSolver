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
       MySet all = new MySet(data); //Set of All Numbers used to fill Sudoku
   } 
   
   SudokuSolver(int p[][]) //parameterize constructor
{    
    this();
    System.out.println("Parametrized called.");
    
    puzzle = new int[9][9]; //memory allocation
    puzzle = p;
//    for(int i=0;i < 9;i++) // To see Data elements
//    System.out.print(data[i]+" ");
//    System.out.println("\n");
}
   int[] rcSafe (int x[][], int data[], int row, int col)
   {
       Row_Col ans = Row_Col.NOT_SAFE;
       int set[] = new int[9];
       int c=0;
       for(int i=0; i < 9;i++)
       {
           if(data[i]!= x[row][i] && i!=col)
           {
               for(int j=0; j < 9; j++)
             {
                  if(data[i]!=x[i][col] && i!=row)
                    {
                       set[c]=data[i];
                        c++;
                    }
             }
           }
       }
       return set;
   }
   
   int[] box(int x[][], int set[], int row, int col)
   {
     int check = x[row][col];//row = 6 ; col = 4
     int c = 0;  
       
       if(check == 0)
           return null;
       
       int set1[] = new int[9]; 
       int base_row = 3 * (row / 3);// 3 * ( 6 / 3 ) = 3 * 2 = 6
       int base_col = 3 * (col / 3);// 3 * ( 4 / 3 ) = 3 * 1 = 3
       for(int i = 0; i < 3; ++i)
       {
           int row1 = base_row + i;
           for(int j=0; j < 3; ++j)               
           {
               int col1 = base_col + j;
               
               if(row1 != row && col1 != col && set[i] != x[row1][col1])
               {
                   set1[c]=set[i];
                   c++;
               }
           }               
       }
       return set1;  
   }
  void makeSet(int x[][])
  {
      MySet all = new MySet(data); 
      MySet ans = new MySet();
      int set[]=new int[9];
      for(int row = 0; row < 9; row++)
      {
          for(int col =0; col< 9; col++)
          {
             if(x[row][col]== 0)
             {
                 //Row_Col a = rcSafe(x,data,row,col); START HERE AGAIN !!!!!!
                 //if( a==Row_Col.SAFE)
                 {
                     
                 }
                 
             }
          }
      }
  }

   Row_Col isSafe(int x[][], int row, int col)//If value of x[row][col] is not existing in full row and col then SAFE
   {
       Row_Col ans = Row_Col.SAFE;
       
       int check = x[row][col];//row=6 ; col = 4
       if(check != 0)
       {
             for (int i = 0; i < 9 ; i++) //for Row
                {
                    if (check == x[row][i] && i!=col) 
                    {
                        System.out.println("isSafe: row" + row + " " + i);
                        System.out.println("isSafe: col"+ col+" "+ i);
                        ans = Row_Col.NOT_SAFE;
                        break;
                    }
                }
             for (int i = 0; i < 9 ; i++) //for Column
                {
                    if (check == x[i][col] && i!=row) 
                    {
                        System.out.println("isSafe: col" +  + col + " " + i);
                        ans = Row_Col.NOT_SAFE;
                        break;
                    }
                }
       }
        return ans;
    }
   
   Status verify_3x3Matrix(int x[][], int row, int col)//TO verify number is valid for internal 3x3 Matrix
   {
       int check = x[row][col];//row = 6 ; col = 4
       
       if(check == 0)
           return Status.VALID;
       
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
    Status ans = Status.VALID;
    Row_Col rc = Row_Col.SAFE;
        if (x != null) 
        {
            for (int row = 0; row < 9; row++) 
            {
                for (int col = 0; col < 9; col++) 
                {
                    rc = isSafe(x, row, col); //
                        if (rc == Row_Col.SAFE) 
                        {
                            //System.out.println("kya");
                            ans = verify_3x3Matrix(x, row, col);
                            if(ans == Status.INVALID)
                            {
                                System.out.println("This : " + row + " " + col);
                                break;
                            }
                        } 
                        else 
                        {
                            //System.out.println("helo");
                            //System.out.println("This : " + row + " " + col);
                            ans = Status.INVALID;
                            break;
                        }
                }
                if(ans == Status.INVALID)
                    break;
            }
            return ans;
        }

        return null;
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
                       
                       {0,2,0,   9,0,0,   0,6,0},
                       {0,0,0,   0,8,0,   4,0,0},
                       {0,0,0,   0,1,0,   0,0,0},
                       
                       {0,0,0,   6,0,3,   0,7,0},
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
