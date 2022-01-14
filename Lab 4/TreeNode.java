import Includes.*;
import java.util.*;
import java.lang.Math;

public class TreeNode{
	public TreeNode parent;
	public TreeNode left;
	public TreeNode right;
	public String val;
	public boolean isLeaf;
	public int numberLeaves;
	public String maxleafval;
	public String minleafval;
	public int balanceFactor;
	public int height;

	public TreeNode SingleLeftRotation(){
		CRF obj=new CRF(64);
		TreeNode n=new TreeNode();
		TreeNode l=new TreeNode();
		l.parent=n;
		l.left=left;
		l.left.parent=l;
		l.right=right.left;
		l.right.parent=l;
		l.val=obj.Fn(l.left.val+"#"+l.right.val);
		l.isLeaf=false;
		l.numberLeaves=l.left.numberLeaves+l.right.numberLeaves;
		l.maxleafval=l.right.maxleafval;
		l.minleafval=l.left.minleafval;
		l.balanceFactor=l.left.height-l.right.height;
		l.height=1+Math.max(l.left.height,l.right.height);
		if(parent!=null){
			if(parent.left==this){
				n.parent=parent; parent.left=n;
			}
			else{
				n.parent=parent; parent.right=n;
			}
		}
		n.left=l;
		n.right=right.right;
		n.right.parent=n;
		n.val=obj.Fn(n.left.val+"#"+n.right.val);
		n.isLeaf=false;
		n.numberLeaves=n.left.numberLeaves+n.right.numberLeaves;
		n.maxleafval=n.right.maxleafval;
		n.minleafval=n.left.minleafval;
		n.balanceFactor=n.left.height-n.right.height;
		n.height=1+Math.max(n.left.height,n.right.height);
		return n;
	}

	public TreeNode SingleRightRotation(){
		CRF obj=new CRF(64);
		TreeNode n=new TreeNode();
		TreeNode r=new TreeNode();
		r.parent=n;
		r.left=left.right;
		r.left.parent=r;
		r.right=right;
		r.right.parent=r;
		r.val=obj.Fn(r.left.val+"#"+r.right.val);
		r.isLeaf=false;
		r.numberLeaves=r.left.numberLeaves+r.right.numberLeaves;
		r.maxleafval=r.right.maxleafval;
		r.minleafval=r.left.minleafval;
		r.balanceFactor=r.left.height-r.right.height;
		r.height=1+Math.max(r.left.height,r.right.height);
		if(parent!=null){
			if(parent.left==this){
				n.parent=parent; parent.left=n;
			}
			else{
				n.parent=parent; parent.right=n;
			}
		}
		n.left=left.left;
		n.left.parent=n;
		n.right=r;
		n.val=obj.Fn(n.left.val+"#"+n.right.val);
		n.isLeaf=false;
		n.numberLeaves=n.left.numberLeaves+n.right.numberLeaves;
		n.maxleafval=n.right.maxleafval;
		n.minleafval=n.left.minleafval;
		n.balanceFactor=n.left.height-n.right.height;
		n.height=1+Math.max(n.left.height,n.right.height);
		return n;
	}

	public TreeNode DoubleLeftRightRotation(){
		CRF obj=new CRF(64);
		TreeNode n=new TreeNode();
		TreeNode l=new TreeNode();
		TreeNode r=new TreeNode();
		l.parent=n;
		l.left=left.left;
		l.left.parent=l;
		l.right=left.right.left;
		l.right.parent=l;
		l.val=obj.Fn(l.left.val+"#"+l.right.val);
		l.isLeaf=false;
		l.numberLeaves=l.left.numberLeaves+l.right.numberLeaves;
		l.maxleafval=l.right.maxleafval;
		l.minleafval=l.left.minleafval;
		l.balanceFactor=l.left.height-l.right.height;
		l.height=1+Math.max(l.left.height,l.right.height);
		r.parent=n;
		r.left=left.right.right;
		r.left.parent=r;
		r.right=right;
		r.right.parent=r;
		r.val=obj.Fn(r.left.val+"#"+r.right.val);
		r.isLeaf=false;
		r.numberLeaves=r.left.numberLeaves+r.right.numberLeaves;
		r.maxleafval=r.right.maxleafval;
		r.minleafval=r.left.minleafval;
		r.balanceFactor=r.left.height-r.right.height;
		r.height=1+Math.max(r.left.height,r.right.height);
		if(parent!=null){
			if(parent.left==this){
				n.parent=parent; parent.left=n;
			}
			else{
				n.parent=parent; parent.right=n;
			}
		}
		n.left=l;
		n.right=r;
		n.val=obj.Fn(n.left.val+"#"+n.right.val);
		n.isLeaf=false;
		n.numberLeaves=n.left.numberLeaves+n.right.numberLeaves;
		n.maxleafval=n.right.maxleafval;
		n.minleafval=n.left.minleafval;
		n.balanceFactor=n.left.height-n.right.height;
		n.height=1+Math.max(n.left.height,n.right.height);
		return n;
	}
	
	public TreeNode DoubleRightLeftRotation(){
		CRF obj=new CRF(64);
		TreeNode n=new TreeNode();
		TreeNode l=new TreeNode();
		TreeNode r=new TreeNode();
		l.parent=n;
		l.left=left;
		l.left.parent=l;
		l.right=right.left.left;
		l.right.parent=l;
		l.val=obj.Fn(l.left.val+"#"+l.right.val);
		l.isLeaf=false;
		l.numberLeaves=l.left.numberLeaves+l.right.numberLeaves;
		l.maxleafval=l.right.maxleafval;
		l.minleafval=l.left.minleafval;
		l.balanceFactor=l.left.height-l.right.height;
		l.height=1+Math.max(l.left.height,l.right.height);
		r.parent=n;
		r.left=right.left.right;
		r.left.parent=r;
		r.right=right.right;
		r.right.parent=r;
		r.val=obj.Fn(r.left.val+"#"+r.right.val);
		r.isLeaf=false;
		r.numberLeaves=r.left.numberLeaves+r.right.numberLeaves;
		r.maxleafval=r.right.maxleafval;
		r.minleafval=r.left.minleafval;
		r.balanceFactor=r.left.height-r.right.height;
		r.height=1+Math.max(r.left.height,r.right.height);
		if(parent!=null){
			if(parent.left==this){
				n.parent=parent; parent.left=n;
			}
			else{
				n.parent=parent; parent.right=n;
			}
		}
		n.left=l;
		n.right=r;
		n.val=obj.Fn(n.left.val+"#"+n.right.val);
		n.isLeaf=false;
		n.numberLeaves=n.left.numberLeaves+n.right.numberLeaves;
		n.maxleafval=n.right.maxleafval;
		n.minleafval=n.left.minleafval;
		n.balanceFactor=n.left.height-n.right.height;
		n.height=1+Math.max(n.left.height,n.right.height);
		return n;
	}
}

