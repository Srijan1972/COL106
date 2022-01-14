import Includes.*;
import java.util.*;

public class MerkleTree{
	public TreeNode rootnode;
	public int numstudents;

	public String Build(List<Pair<String,Integer>> documents){
		numstudents=documents.size();
		ArrayList<TreeNode> arr=new ArrayList<TreeNode>();
		for(int i=0;i<numstudents;i++){
			String str=documents.get(i).get_first()+"_"+Integer.toString(documents.get(i).get_second());
			TreeNode t=new TreeNode();
			t.val=str;
			t.maxleafval=documents.get(i).get_second();
			t.numberLeaves=1;
			t.isLeaf=true;
			arr.add(t);
		}
		CRF obj=new CRF(64);
		while(arr.size()>1){
			ArrayList<TreeNode> al=new ArrayList<TreeNode>();
			int i=0;
			while(i<arr.size()){
				TreeNode t=new TreeNode();
				t.left=arr.get(i);
				t.right=arr.get(i+1);
				arr.get(i).parent=t;
				arr.get(i+1).parent=t;
				t.val=obj.Fn(t.left.val+"#"+t.right.val);
				t.maxleafval=Math.max(t.left.maxleafval,t.right.maxleafval);
				t.numberLeaves=t.left.numberLeaves+t.right.numberLeaves;
				t.isLeaf=false;
				al.add(t);
				i+=2;
			}
			arr=al;
		}
		rootnode=arr.get(0);
		return rootnode.val;
	}

	public String UpdateDocument(int student_id, int newScore){
		assert student_id>=1 && student_id<=numstudents;
		int l=0,r=numstudents-1;
		TreeNode curr=rootnode;
		while(l<r){
			int m=l+(r-l)/2;
			if(student_id>m){
				l=m+1; curr=curr.right;
			}
			else{
				r=m; curr=curr.left;
			}
		}
		String ou=Integer.toString(curr.maxleafval);
		String in=Integer.toString(newScore);
		String s=curr.val.substring(0,curr.val.length()-ou.length());
		curr.val=s+in;
		curr.maxleafval=newScore;
		CRF obj=new CRF(64);
		while(curr.parent!=null){
			if(curr==curr.parent.left){
				curr.parent.val=obj.Fn(curr.val+"#"+curr.parent.right.val);
			}
			else{
				curr.parent.val=obj.Fn(curr.parent.left.val+"#"+curr.val);
			}
			curr.parent.maxleafval=Math.max(curr.parent.left.maxleafval,curr.parent.right.maxleafval);
			curr=curr.parent;
		}
		return rootnode.val;
	}
}
