package Practice.SudokuProject;

import java.io.*;

public class SudokuSolver 
{
    enum Status {VALID,INVALID};
    enum Row_Col{SAFE, NOT_SAFE};
    
   int puzzle[][]={}; 
   MySet poss[][]; //Possible numbers for every position in sudoku
   int data[];  
   MySet all;
   
    SudokuSolver() //Default constructor
   {
       //System.out.println("Default called.");
        data = new int[9];//
        for(int i=0;i < 9;i++)
        {
            data[i]=i+1;
            //System.out.print(data[i]+" ");
        }
        System.out.println("");
      all = new MySet(data); //Set of All Numbers used to fill Sudoku(1,2,3,4,5,6,7,8,9)
   } 
   
   SudokuSolver(int p[][]) //parameterize constructor
{    
    this();
    //System.out.println("Parametrized called.");
    
    puzzle = new int[9][9]; //memory allocation
    puzzle = p;
 /* for(int i=0;i < 9;i++) // To see Data elements
    System.out.print(data[i]+" ");
      System.out.println("\n"); */
}
   
   MySet box(int x[][], int row, int col)
   {
       int set[] = new int[9];
       int c = 0;
       int base_row = 3 * (row / 3);// 3 * ( 6 / 3 ) = 3 * 2 = 6
       int base_col = 3 * (col / 3);// 3 * ( 4 / 3 ) = 3 * 1 = 3
       for(int i = 0; i < 3; ++i)
       {
           int row1 = base_row + i;
           for(int j = 0; j < 3; ++j)               
           {
               int col1 = base_col + j;
               if(x[row1][col1]!= 0)
               { 
                   set[c] = x[row1][col1];
                   c++;
               }
           }               
       }
    MySet ans = new MySet(set); 
       return ans;
   }

   void makeSet(int x[][])
   {
       MySet rows[] = new MySet[9];
       MySet cols[] = new MySet[9];
       MySet boxes[] = new MySet[9];
       
       for(int i = 0; i < 9; ++i)
        {
           rows[i] = new MySet(x[i]);                      
           for(int j = 0; j < 9; j++)
           {
               if(cols[j] == null)
                    cols[j] = new MySet();
               cols[j].add(x[i][j]);
           }
           
           int r1 = 3 * (i / 3); //3 *(1/3)= 0
           int c1 = 3 * (i % 3); //3 *(1%3)= 3
           boxes[i] = box(x,r1,c1);
        }
       
        poss = new MySet[9][];
       for(int i=0 ; i < 9; ++i)
       {
           poss[i] = new MySet[9];
           for(int j = 0; j < 9; ++j)
           {
               if(x[i][j]== 0)
               {
                   int pos = 3 * (i / 3) + (j / 3);

                   poss[i][j] = all.difference(rows[i]).difference(cols[j]).difference(boxes[pos]);
//                   System.out.println("x[" + i + "][" + j + "]");
//                   poss[i][j].print();
               }
           }
       }
       
   }
   
   
void solvePuzzle()
{
    int ans[][] = puzzle;
    int d[];
    int flag;
    int s = 0;
    
        do {
             flag = 0;
            
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (puzzle[i][j] == 0) {
                         s = poss[i][j].size();
                        //System.out.print(s + " ");
                        if (s == 1) {
                            flag = 1;
                            d = new int[0];
                            d = poss[i][j].getElements();
                            puzzle[i][j] = d[0];
                             makeSet(ans);
                        }
                      
                    }

                }
            }
            
        }
        
        while( flag == 1);
        
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
                       {8,4,0,   0,3,9,   1,0,0},
                       {3,6,0,   1,0,0,   0,0,8}, 
                       {1,0,9,   2,0,0,   0,0,0},
                       
                       {0,5,0,   8,0,0,   6,0,0},
                       {0,0,1,   9,0,5,   8,0,0},
                       {0,0,3,   0,0,4,   0,2,0},
                       
                       {0,0,0,   0,0,1,   7,0,2},
                       {2,0,0,   0,0,6,   0,8,1},
                       {0,0,6,   3,8,0,   0,5,4},
                      };
      //System.out.println("Making s1 ...");
      SudokuSolver s1= new SudokuSolver();
      //System.out.println("Making s ...");
      SudokuSolver s= new SudokuSolver(data);
      
      s.makeSet(data);
      s.solvePuzzle();
      s.printPuzzle();
     // Status ans = s.verify(data);
       // System.out.println("Status :"+ans);
}
}
