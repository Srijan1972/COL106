import java.util.*;
import java.lang.Math;
import java.io.*;

class MergeSort{
    public static void merge_x(int[][] arr,int l,int m,int r){
        int n1=m-l+1,n2=r-m;
        int[][] L=new int[n1][2];
        int[][] R=new int[n2][2];
        for(int i=0;i<n1;i++){
            L[i][0]=arr[l+i][0];
            L[i][1]=arr[l+i][1];
        }
        for(int i=0;i<n2;i++){
            R[i][0]=arr[m+1+i][0];
            R[i][1]=arr[m+1+i][1];
        }
        int i=0,j=0,k=l;
        while(i<n1 && j<n2){
            if(L[i][0]<=R[j][0]){
                arr[k][0]=L[i][0];
                arr[k][1]=L[i][1];
                i++;
            }
            else{
                arr[k][0]=R[j][0];
                arr[k][1]=R[j][1];
                j++;
            }
            k++;
        }
        while(i<n1){
            arr[k][0]=L[i][0];
            arr[k][1]=L[i][1];
            i++; k++;
        }
        while(j<n2){
            arr[k][0]=R[j][0];
            arr[k][1]=R[j][1];
            j++; k++;
        }
    }

    public static void merge_y(int[][] arr,int l,int m,int r){
        int n1=m-l+1,n2=r-m;
        int[][] L=new int[n1][2];
        int[][] R=new int[n2][2];
        for(int i=0;i<n1;i++){
            L[i][0]=arr[l+i][0];
            L[i][1]=arr[l+i][1];
        }
        for(int i=0;i<n2;i++){
            R[i][0]=arr[m+1+i][0];
            R[i][1]=arr[m+1+i][1];
        }
        int i=0,j=0,k=l;
        while(i<n1 && j<n2){
            if(L[i][1]<=R[j][1]){
                arr[k][0]=L[i][0];
                arr[k][1]=L[i][1];
                i++;
            }
            else{
                arr[k][0]=R[j][0];
                arr[k][1]=R[j][1];
                j++;
            }
            k++;
        }
        while(i<n1){
            arr[k][0]=L[i][0];
            arr[k][1]=L[i][1];
            i++; k++;
        }
        while(j<n2){
            arr[k][0]=R[j][0];
            arr[k][1]=R[j][1];
            j++; k++;
        }
    }

    public static void sort_x(int[][] arr,int l,int r){
        if(l>=r) return;
        int m=l+(r-l)/2;
        sort_x(arr,l,m);
        sort_x(arr,m+1,r);
        merge_x(arr,l,m,r);
    }
    public static void sort_y(int[][] arr,int l,int r){
        if(l>=r) return;
        int m=l+(r-l)/2;
        sort_y(arr,l,m);
        sort_y(arr,m+1,r);
        merge_y(arr,l,m,r);
    }
}

class Node{
    public int[] loc=new int[2];
    public Node left;
    public Node right;
    public int leaves;
    public int xmin=Integer.MIN_VALUE;
    public int xmax=Integer.MAX_VALUE;
    public int ymin=Integer.MIN_VALUE;
    public int ymax=Integer.MAX_VALUE;
}

public class kdtree{

    public Node root;

    public Node build(int[][] arr,Node node,int l,int r,int d){
        node=new Node();
        if(l==r){
            node.loc=arr[l];
            node.leaves=1;
            return node;
        }
        else{
            if(d%2==0){
                MergeSort.sort_x(arr,l,r);
                int m=l+(r-l)/2;
                node.left=build(arr,node.left,l,m,d+1);
                node.left.xmin=node.xmin;
                node.left.xmax=arr[m][0];
                node.left.ymin=node.ymin;
                node.left.ymax=node.ymax;
                node.right=build(arr,node.right,m+1,r,d+1);
                node.right.xmin=arr[m+1][0];
                node.right.xmax=node.xmax;
                node.right.ymin=node.ymin;
                node.right.ymax=node.ymax;
            }
            else{
                MergeSort.sort_y(arr,l,r);
                int m=l+(r-l)/2;
                node.left=build(arr,node.left,l,m,d+1);
                node.left.xmin=node.xmin;
                node.left.xmax=node.xmax;
                node.left.ymin=node.ymin;
                node.left.ymax=arr[m][1];
                node.right=build(arr,node.right,m+1,r,d+1);
                node.right.xmin=node.xmin;
                node.right.xmax=node.xmax;
                node.right.ymin=arr[m+1][1];
                node.right.ymax=node.ymax;
            }
            node.leaves=node.left.leaves+node.right.leaves;
            return node;
        }
    }

    public int solve(int[] q,Node node){
        if(node.left==null && node.right==null){
            if(Math.abs(node.loc[0]-q[0])<=100 && Math.abs(node.loc[1]-q[1])<=100) return 1;
            else return 0;
        }
        else if(q[0]-100<=node.xmin && q[0]+100>=node.xmax && q[1]-100<=node.ymin && q[1]+100>=node.ymax) return node.leaves;
        else return solve(q,node.left)+solve(q,node.right);
    }
    
    public static void main(String[] args){
        try{
            File rest=new File("./restaurants.txt");
            File que=new File("./queries.txt");
            Scanner scr1=new Scanner(rest);
            Scanner scq1=new Scanner(que);
            int n1=0,n2=0;
            while(scr1.hasNextLine()){
                scr1.nextLine();
                n1++;
            }
            while(scq1.hasNextLine()){
                scq1.nextLine();
                n2++;
            }
            n1--; n2--;
            scr1.close();
            scq1.close();
            kdtree kd=new kdtree();
            int[][] r=new int[n1][2];
            int[][] q=new int[n2][2];
            Scanner scr2=new Scanner(rest);
            Scanner scq2=new Scanner(que);
            boolean b1=true,b2=true;
            int i1=0,i2=0;
            while(scr2.hasNextLine()){
                if(b1){
                    b1=false; scr2.nextLine();
                    continue;
                }
                String[] a=scr2.nextLine().split(",",2);
                int[] k={Integer.parseInt(a[0]),Integer.parseInt(a[1])};
                r[i1]=k; i1++;
            }
            while(scq2.hasNextLine()){
                if(b2){
                    b2=false; scq2.nextLine();
                    continue;
                }
                String[] a=scq2.nextLine().split(",",2);
                int[] k={Integer.parseInt(a[0]),Integer.parseInt(a[1])};
                q[i2]=k; i2++;
            }
            scr2.close();
            scq2.close();
            kd.root=kd.build(r,kd.root,0,n1-1,0);
            FileOutputStream ou=new FileOutputStream("./output.txt",false);
            PrintStream out=new PrintStream(ou);
            for(int i=0;i<n2;i++){
                out.println(kd.solve(q[i],kd.root));
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}