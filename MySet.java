package  Practice.SudokuProject;

public class MySet 
{
    final static int max_size = 100;
    int data[]={};
    
    MySet ()
    {
       data = new int[max_size]; 
    }
    
    MySet(int ls[])
    {
        this();
        if(ls != null)
           for(int item : ls)
               data[item]=1;
        
    }
    
    void add(int x)
    {
        data[x]=1;
    }
    void print()
    {
       for(int i=0;i < data.length;i++)
       {
           if(data[i]!= 0)
           System.out.print(i +" ");
       }
        System.out.println(" ");
    }
    
    MySet intersection(MySet set)
    {
        MySet ans = new MySet();
        
        for(int i = 0; i < data.length; i++)
        {
            if(data[i] !=0 && set.data[i] != 0 )
            {
                ans.data[i]= 1;
            }
        }         
       return ans;
    }
    
    MySet union(MySet set)
    {
        MySet ans = new MySet();
        
        for(int i = 0; i < data.length; i++)
        {
            if(data[i] !=0 || set.data[i] != 0 )
            {
                ans.data[i]= 1;
            }
        }         
       return ans;
    }
    
    MySet difference(MySet set)
    {
        MySet ans = new MySet();
        
        for(int i = 0; i < data.length; i++)
        {
            if(data[i] !=0 && set.data[i] == 0 )
            {
                ans.data[i]= 1;
            }
        }         
       return ans;
    }
    
    int size()
    {
        int count=0;
        for(int i=0;i<data.length;i++)
        {
            if(data[i]!=0)
                count++;
        }
        return count;
    }
    
    protected MySet copy()
    {
        MySet ans = new MySet(getElements());
        
        return ans;
    }
    
    int[] getElements()
    {
        int len = size();
        if(len > 0)
        {
            int ans[] = new int[len];
            int idx=0;
            for(int i=0;i<data.length;i++)
            {
                if(data[i]!= 0)
                {
                    ans[idx]= i;
                    idx++;
                }                
                     
            }
            return ans;
        }
        return null;
    }
    
    void delete(int idx)
    {
        data[idx]= 0;
    }
    
    public static void main(String[] args) 
    {
        
        int ls1[] = {1,5,3,5,6,7};
        int ls2[] = {5,2,1,1};
     
        MySet set1 = new MySet(ls1);
        set1.print();//1,3,5,6,7
        MySet set2 = new MySet(ls2);
        set2.print();//1,2,5
        
        MySet set3 = set1.intersection(set2);//common elements
        set3.print();//1,5
        MySet set4 = set1.union(set2);//all unique elements
        set4.print();//1,2,3,5,6,7
        MySet set5 = set1.difference(set2);//all unique elements of set1 which are not in set2
        set5.print();//3,6,7
    }
    
}
