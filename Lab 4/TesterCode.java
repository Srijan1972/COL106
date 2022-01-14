import Includes.*;
import java.util.*;
import java.io.*;

public class TesterCode {
    public static void main(String args[]) {
        String summary = null;
        CRF obj = new CRF(64);
        MerkleTree tree = new MerkleTree();
        summary = tree.InsertDocument("1");
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 1:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("3");
        System.out.println(obj.Fn("1#3"));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 3:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("2");
        String s = obj.Fn("1#2");
        System.out.println(obj.Fn(s+"#3"));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 2:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("5");
        String ss = obj.Fn("3#5");
        System.out.println(obj.Fn(s+"#" + ss));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 5:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("4");
        String sss = obj.Fn(obj.Fn("3#4") + "#" + "5");
        System.out.println(obj.Fn(s + "#" + sss));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 4:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("8");
        String ss2 = obj.Fn(obj.Fn("3#4") + "#" + obj.Fn("5#8"));
        System.out.println(obj.Fn(obj.Fn("1#2")+"#"+ss2));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 8:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("6");
        String s1 = obj.Fn("1#2");
        String s2 = obj.Fn("3#4");
        String s3 = obj.Fn("5#6");
        String s4 = obj.Fn(s1+ "#" + s2);
        String s5 = obj.Fn(s3+ "#8");
        String s6 = obj.Fn(s4 + "#" + s5);
        String s7 = obj.Fn(s2 + "#" + s5);
        System.out.println(s6);// correct
        System.out.println(summary);// final result
        System.out.print("rootnode bf after inserting 6:- ");
        System.out.println(tree.rootnode.balanceFactor);

        summary = tree.InsertDocument("7");
        System.out.println(obj.Fn(obj.Fn(obj.Fn("1#2") + "#" + obj.Fn("3#4")) + "#" + obj.Fn(obj.Fn("5#6") + "#" + obj.Fn("7#8"))));
        System.out.println(summary);
        System.out.print("rootnode bf after inserting 7:- ");
        System.out.println(tree.rootnode.balanceFactor);
    }
}