package Practice.SudokuProject;

import java.io.*;

public class SudokuSolver 
{
    enum Status {VALID,INVALID};
    enum Row_Col{SAFE, NOT_SAFE};
    
  //int puzzle[][];
   //MySet poss[][]; //Possible numbers for every position in sudoku
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
        //System.out.println("");
      all = new MySet(data); //Set of All Numbers used to fill Sudoku(1,2,3,4,5,6,7,8,9)
   } 
   
   SudokuSolver(int p[][]) //parameterize constructor
{    
    this();
    //System.out.println("Parametrized called.");
    
    //puzzle = new int[9][9]; //memory allocation
    //puzzle = p;
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

   MySet[][] makeSet(int x[][])
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
       
        MySet poss[][] = new MySet[9][];
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
       return poss;       
   }
   
   MySet getNewPoss(int a[][], int row, int col)
   {      
       MySet r = new MySet();
       MySet c = new MySet();
       MySet b = new MySet();
       
       r = new MySet(a[row]); //getting row elements
      
      for(int j = 0; j < 9; j++)
      {
          c.add(a[j][col]); //getting column elements
      }
           int r1 = 3 * (row / 3); //3 * (1 / 3)= 0
           int c1 = 3 * (col / 3); //3 * (1 / 3)= 0
           b = box( a,r1,c1);
       
           /*
           System.out.println("box1::" + row + "," + col);
           System.out.println("box2::" + r1 + "," + c1);
           b.print();
                   */
        MySet set = all.difference(r).difference(c).difference(b); //new set of possibilities
      
       return set;
   }
   
   int[][] getSudokuSolved(int a[][],int row, int col)
   {
       //find first element in sudoku with zero element; start searching from (row, col)
      //if it is zero make new set of elements and put into that position and try next till all tried 
       //System.out.println("Assignment attempt: " + row + "," + col);
       //printPuzzle(a);
       //System.out.println("single position filling");
            int new_ans[][] = copy(a);
         new_ans = solvePuzzle(new_ans);
         if(new_ans == null)
         {
             //System.out.println("Backtracking...");
             return null;
         }
         
         //printPuzzle(new_ans);
         if (verify(new_ans) != Status.VALID)
         {                          
             System.out.println("INVALID");
             System.exit(1);
         }
         else
         {
             //System.out.println("VALID");
         }
         
       while(row < 9 && new_ans[row][col] != 0)
       {
           //compute next valid position
           col = (col + 1) % 9;
           row = row + (col + 1) / 9;
           //System.out.println("here:" + row + "," + col);
       }
       
       //System.out.println("(" + row + "," + col + ")");
       if(row >= 9)
       {
           //verify
           //System.out.println("Validation:");
           //printPuzzle(new_ans);
           if (verify(new_ans) == Status.VALID)
               return new_ans;
           else
           {
               //System.out.println("Validation failed: 1");
               return null;
           }
       }
       else
       {
           MySet set = getNewPoss(new_ans, row, col);
           int rem_elems[] = set.getElements();

           if (set.size() == 0) 
           {
              //System.out.println("set is null: "  + "(" + row + "," + col + ")");
              //System.out.println("Validation failed: 2");
              return null;
           } 
           else 
           {
               int new_col = (col + 1) % 9;
               int new_row = row + (col + 1) / 9;

               /*
               for (int i = 0; i < rem_elems.length; i++) {
                   System.out.print(rem_elems[i] + ",");
               }
               System.out.println("");
                       */
               
               for (int i = 0; i < rem_elems.length; i++) {
                   //System.out.println("Guess: (" + row + "," + col + ") <- " + rem_elems[i]);
                   new_ans[row][col] = rem_elems[i];

                   int ans[][] = getSudokuSolved(new_ans, new_row, new_col);
                   if (ans != null) {
                       //if (verify(ans) == Status.VALID) {
                           return ans;
                       //}
                   }
                   else
                   {
                       //System.out.println("Validation failed: retrying another possibility");                       
                   }
             }
           }
           
       }      
           
     return null;
   }
   
   
int[][] solvePuzzle(int puzz[][])
{
   int ans[][] = puzz;
   //MySet poss[][] = makeSet(puzz);
   
    int d[];
    int flag;
    int s ;
    
        do {
             flag = 0;
               s = 0;  
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (ans[i][j] == 0) {
                        MySet poss1 = getNewPoss(ans, i, j);
                         s = poss1.size();
                        //System.out.print(s + " ");
                         if(s == 0)
                         {
                             //System.out.println("No poss: (" + i + "," + j + ")");
                             //printPuzzle(ans);
                             return null;
                         }
                         else if (s == 1) {
                            flag = 1;                            
                            d = poss1.getElements();
                            ans[i][j] = d[0];
                            //System.out.println("Sure: (" + i + "," + j + ") <- " + d[0]);                             
                        }
                      
                    }

                }
            }
            
        }while( flag == 1);
        
                
       return ans; 
    }
  
   Row_Col isSafe(int x[][], int row, int col)//If value of x[row][col] is not existing in full row and col then SAFE
   {
       Row_Col ans = Row_Col.SAFE;
       
       int check = x[row][col];//row=6 ; col = 4
       if(check != 0)
       {
             for (int i = 0; i < 9 ; i++) //for column
                {
                    if (check == x[row][i] && i!=col) 
                    {
                        System.out.println("isSafe: row " + row + " (" + i  + "," + col + ")");
                        ans = Row_Col.NOT_SAFE;
                        break;
                    }
                }
             for (int i = 0; i < 9 ; i++) //for row
                {
                    if (check == x[i][col] && i!=row) 
                    {
                        System.out.println("isSafe: col " + col + " (" + i  + "," + row + ")");
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
                                //System.out.println("This : " + row + " " + col);
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


   void printPuzzle(int a[][])
   {
       System.out.println("+---+---+---+");
       for (int i = 0; i < 9; i++) 
        {
            for (int j = 0; j < 9; j++) 
            {
                    if(j==0)
                    System.out.print("|");
                    System.out.print( a[i][j]);
                    if(j==2 || j==5 || j==8)
                    System.out.print("|");
            }
            System.out.println(" ");
            if(i==2||i==5||i==8)
                System.out.println("+---+---+---+");
        }
       System.out.println("");
   }

   int[][] copy(int arr[][]) //to make a copy of original puzzle
   {
       int c[][] = new int[9][];
       for(int i = 0; i < 9; i++)
       {
           c[i] = new int[9];
           for(int j = 0; j < 9; j++)
           {
               c[i][j]= arr[i][j];
           }
       }
       return c;
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
      //System.out.println("Making s1 ...");
      SudokuSolver s= new SudokuSolver();
      //System.out.println("Making s ...");
      //SudokuSolver s1= new SudokuSolver(data);
      
      s.makeSet(data);
      //s.printPuzzle(data);
      int ans[][]= s.copy(data);
      s.getSudokuSolved(ans,0,0);
      //s.printPuzzle(data);
      s.printPuzzle(ans);
     // Status ans = s.verify(data);
       // System.out.println("Status :"+ans);
}
}
