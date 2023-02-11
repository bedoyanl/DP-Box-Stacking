// Laura Bedoyan
// Project 2
// 12/4/2022
// COMP 610

//This project takes in a input.txt file with different dimensional boxes-- length width and height included.
//the problem here is to take each box and stack them on top of each other so that they can make the tallest stack
//Condition is that the length and width of the box below are both less than the box above and so forth.
//This is an extension of the longest incrreasing subsequence problem with a couple of additions: we have a multi dimensional array
//and have the condition that length and width are smaller for the box above. First we sort the boxes with increasing area.
//Then we perfrom the Longest increasing subsequence problem to find the subsequence and height of the tallest subsequence.
//*************************************************************************
//import necessary libraries
import java.util.*;
import java.io.*;
import java.lang.Math;
import java.util.Arrays;

//class project2
public class Project2 {
//create a static global class constant variable N which will hold the value N of input array.
   static int N = 0;
    
    //Main method:
   public static void main(String[] args) {
   //Get the data from input file
      int[][] theData = getInput();
      findArea(theData);
      int[][] newdata = sortbyArea(theData);
      stackBoxes(newdata);
    
    }

   // Reading from input.txt and filling theData-- adapted from Professor Noga's Project 0 file. returns the 
   //data in an array theData
   private static int[][] getInput() {
      Scanner sc = null;
      try {
         // Note the filename is input.txt without any mention of the path
         sc = new Scanner(new File("input.txt"));
      } 
      //if file is not found
      catch (FileNotFoundException e) 
      {
         System.out.println("Did you forget the input file?");
         System.exit(1);
      }
      //initialize size to be the first integer from input file
      int pos = 0, size = sc.nextInt(), counter=0;
      N =size;
     //create the new 2d array but with size four to put the areas in that column
      int[][] theData = new int[size][4];
      
      //add in the data
      while (sc.hasNextInt()) 
      {
         for (int row = 0; row < size; row++) 
         { 
            for (int col = 0; col < 3; col++)
            {
                 
                 theData[row][col] = sc.nextInt();  
            }
            counter ++;
         } 
      }
    
     return theData;
     }
     
     //find the area of each box and put in the last column. length * width which are the first and second column
      public static int[][] findArea(int[][]theData)
     
     {
          for (int row = 0; row < N; row++) 
               { 
            for (int col = 0; col < 4; col++)
            {
                 int length = theData[row][0]; 
                 int width = theData[row][1]; 
                 int area = length * width;
                 theData[row][3]=area;
               
            }
            
         } 
         
          
          
              return theData;
          
     
      }
      
      //sort the array based on the area.return the new array sorted
      
     
     
     public static int[][] sortbyArea(int[][]theData)
     
     {
      Arrays.sort(theData, (a,b) -> Integer.compare(a[3], b[3]));
      
          return theData;
      }
     
  //stack the boxes! Implement longest increasing subsequence dynamic programming algorithm
     public static void stackBoxes(int[][] array)
     
     {   
     //keep track of the longest stacked boxes with length
     int [] length = new int[N];
     //keep track of which box you used to create such a long length
     int [] subseq = new int[N];
     
     for (int i = 0; i < N; i++) 
     {
     //initialize arrays 
            length[i]=1;
            //initialise length to one becaause one box has one length
            subseq[i]=-1;
            
     }
     
     //go through box array and have two pointers. i which will go from the beginning to end and j which compares
     //values of i to it to see if it satisfies the stack box condiiton (i.e. length and width less)
     for (int i = 1; i < N; i++) 
     {
         //i starts at the beginning
         int newL = 1;
         
         for (int j = 0; j < i; j++) 
            {
            
            //j looks to check and see if it is less than box i 
            //if box j is less than box i then update the length and subseq arrays
               if (((array[j][0] < array[i][0]) && (array[j][1] < array[i][1])))
                  {
                        //increase length of box since it takes the one before. 
                        int newBox = 1 + length[j];
                        //pick thhe greater of using the new box or the already used boxes 
                        newL=Math.max(newBox, newL);
                        //insert in subseq aray whhich box you used
                        subseq[i]=j;
                                             
                  }
                 
            }
            //update the length to the current size with the most boxes
          length[i] = newL;
         
     }
     //figure out which boxes you used from subseq array
     int h = whichBoxes(subseq, array, length); 
     //print 
     System.out.println(h);
          
       
     }
   //use subseq array to figure out which boxes are in the longest subseq
     public static int whichBoxes(int[] subseq, int[][] theBoxes, int[] length)
     {
     
     //find the index of the max value in the length array
          int max = length[0];  
          int maxIdx = 0; 
          
         // int max = Arrays.stream(length).max().getAsInt();
         // int maxIdx = Arrays.asList(length).indexOf(max);
           
          for(int i = 1; i < length.length; i++) {  
               if(length[i] > max) {  
                     max = length[i];  
                     maxIdx = i;  
                    }  
                  }  
          
              
         //go to max index of subseq and return the value there
         int x = subseq[maxIdx];
         int height = theBoxes[maxIdx][2];
         //while subseqeunce array is not -1 find the other boxes and add it to height variable
         while (x != -1)
         {
               maxIdx = x;
               
               height = height + theBoxes[maxIdx][2];
              
               x = subseq[maxIdx];
               
               
         
         
         }
        
        
         return height;
        
         }
         
         
      
     }// end class
   

  