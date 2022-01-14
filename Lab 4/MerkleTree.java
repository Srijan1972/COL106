import Includes.*;
import java.util.*;
import java.lang.Math;

public class MerkleTree{
	public TreeNode rootnode;
	public int numdocs;

	public String InsertDocument(String document){
		numdocs++;
		if(rootnode==null){
			rootnode=new TreeNode();
			rootnode.val=document;
			rootnode.isLeaf=true;
			rootnode.numberLeaves=0;
			rootnode.balanceFactor=0;
			rootnode.minleafval=document;
			rootnode.maxleafval=document;
			rootnode.numberLeaves=1;
			rootnode.height=0;
			return rootnode.val;
		}
		CRF obj=new CRF(64);
		TreeNode curr=rootnode;
		TreeNode l=new TreeNode();
		TreeNode r=new TreeNode();
		while(!curr.isLeaf){
			if(curr.maxleafval.compareTo(document)<0) curr=curr.right;
			else if(curr.minleafval.compareTo(document)>=0) curr=curr.left;
			else if(curr.right.minleafval.compareTo(document)>=0) curr=curr.left;
			else curr=curr.right;
		}
		l.parent=curr; r.parent=curr; l.balanceFactor=0; r.balanceFactor=0;
		l.isLeaf=true; r.isLeaf=true; l.numberLeaves=1; r.numberLeaves=1;
		l.height=0; r.height=0;
		if(document.compareTo(curr.val)>=0){
			l.val=curr.val; l.minleafval=curr.val; l.maxleafval=curr.val;
			r.val=document; r.minleafval=document; r.maxleafval=document;
		}
		else{
			l.val=document; l.minleafval=document; l.maxleafval=document;
			r.val=curr.val; r.minleafval=curr.val; r.maxleafval=curr.val;
		}
		curr.left=l; curr.right=r;
		TreeNode rot=curr;
		while(curr!=null){
			curr.val=obj.Fn(curr.left.val+"#"+curr.right.val);
			curr.isLeaf=false;
			curr.numberLeaves=curr.left.numberLeaves+curr.right.numberLeaves;
			curr.maxleafval=curr.right.maxleafval;
			curr.minleafval=curr.left.minleafval;
			curr.balanceFactor=curr.left.height-curr.right.height;
			curr.height=1+Math.max(curr.left.height,curr.right.height);
			if(curr.balanceFactor==2){
				if(curr.left.balanceFactor==1) curr=curr.SingleRightRotation();
				else curr=curr.DoubleLeftRightRotation();
			}
			else if(curr.balanceFactor==-2){
				if(curr.right.balanceFactor==-1) curr=curr.SingleLeftRotation();
				else curr=curr.DoubleRightLeftRotation();
			}
			if(curr.parent==null) rootnode=curr;
			curr=curr.parent;
		}
		return rootnode.val;
	}
	
	public String DeleteDocument(String document) throws IllegalStateException,IllegalArgumentException{
		if(numdocs==0) throw new IllegalStateException();
		TreeNode curr=rootnode;
		while(!curr.isLeaf){
			// System.out.println(curr.minleafval);
			// System.out.println(curr.maxleafval);
			if(curr.maxleafval.compareTo(document)<=0) curr=curr.right;
			else if(curr.minleafval.compareTo(document)>=0) curr=curr.left;
			else if(curr.right.minleafval.compareTo(document)>0) curr=curr.left;
			else curr=curr.right;
		}
		if(!curr.val.equals(document)) throw new IllegalArgumentException();
		numdocs--;
		if(numdocs==0){
			rootnode=null;
			return null;
		}
		CRF obj=new CRF(64);
		if(curr==curr.parent.left){
			curr.parent.val=curr.parent.right.val;
			curr.parent.isLeaf=true;
			curr.parent.numberLeaves=1;
			curr.parent.maxleafval=curr.parent.right.maxleafval;
			curr.parent.minleafval=curr.parent.right.minleafval;
			curr.parent.balanceFactor=0;
			curr.parent.height=0;
		}
		else{
			curr.parent.val=curr.parent.left.val;
			curr.parent.isLeaf=true;
			curr.parent.numberLeaves=1;
			curr.parent.maxleafval=curr.parent.left.maxleafval;
			curr.parent.minleafval=curr.parent.left.minleafval;
			curr.parent.balanceFactor=0;
			curr.parent.height=0;
		}
		curr.parent.left=null; curr.parent.right=null;
		curr=curr.parent.parent;
		while(curr!=null){
			curr.val=obj.Fn(curr.left.val+"#"+curr.right.val);
			curr.isLeaf=false;
			curr.numberLeaves=curr.left.numberLeaves+curr.right.numberLeaves;
			curr.maxleafval=curr.right.maxleafval;
			curr.minleafval=curr.left.minleafval;
			curr.balanceFactor=curr.left.height-curr.right.height;
			curr.height=1+Math.max(curr.left.height,curr.right.height);
			if(curr.balanceFactor==2){
				if(curr.left.balanceFactor==1) curr=curr.SingleRightRotation();
				else curr=curr.DoubleLeftRightRotation();
			}
			else if(curr.balanceFactor==-2){
				if(curr.right.balanceFactor==-1) curr=curr.SingleLeftRotation();
				else curr=curr.DoubleRightLeftRotation();
			}
			if(curr.parent==null) rootnode=curr;
			curr=curr.parent;
		}
		return rootnode.val;
	}
}


