import Includes.*;
import java.util.*;

public class BlockChain{
	public static final String start_string = "LabModule5";
	public Block firstblock;
	public Block lastblock;

	public String InsertBlock(List<Pair<String,Integer>> Documents, int inputyear){
		Block b=new Block();
		b.mtree=new MerkleTree();
		String val=b.mtree.Build(Documents);
		b.year=inputyear;
		b.value=b.mtree.rootnode.val+"_"+Integer.toString(b.mtree.rootnode.maxleafval);
		CRF obj=new CRF(64);
		if(firstblock==null){
			b.dgst=obj.Fn(start_string+"#"+b.value);
			firstblock=b;
		}
		else{
			b.dgst=obj.Fn(lastblock.dgst+"#"+b.value);
			b.previous=lastblock;
			lastblock.next=b;
		}
		lastblock=b;
		return lastblock.dgst;
	}

	public Pair<List<Pair<String,String>>, List<Pair<String,String>>> ProofofScore(int student_id, int year){
		Block it=firstblock;
		while(it!=null){
			if(it.year==year) break;
			it=it.next;
		}
		if(it==null) return null;
		ArrayList<Pair<String,String>> al=new ArrayList<Pair<String,String>>();
		TreeNode curr=it.mtree.rootnode;
		al.add(new Pair<String,String>(curr.val,null));
		int l=0,r=it.mtree.numstudents-1;
		while(l<r){
			int m=l+(r-l)/2;
			al.add(new Pair<String,String>(curr.left.val,curr.right.val));
			if(student_id>m){
				l=m+1;
				curr=curr.right;
			}
			else{
				r=m; curr=curr.left;
			}
		}
		Collections.reverse(al);
		ArrayList<Pair<String,String>> dg=new ArrayList<Pair<String,String>>();
		while(it!=null){
			dg.add(new Pair<String,String>(it.value,it.dgst));
			it=it.next;
		}
		return new Pair<List<Pair<String,String>>, List<Pair<String,String>>>(al,dg);
	}
}
