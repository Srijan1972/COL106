import Includes.*;
import java.util.*;

public class MerkleTree{
	public TreeNode rootnode;
	public int numdocs;

	public String Build(String[] documents){
		numdocs=documents.length;
		Vector<TreeNode> vec=new Vector<TreeNode>();
		for(String doc:documents){
			TreeNode t=new TreeNode();
			t.val=doc;
			vec.add(t);
		}
		CRF obj=new CRF(64);
		while(vec.size()>1){
			Vector<TreeNode> v=new Vector<TreeNode>();
			int i=0;
			while(i<vec.size()){
				TreeNode t=new TreeNode();
				t.left=vec.get(i);
				t.right=vec.get(i+1);
				vec.get(i).parent=t;
				vec.get(i+1).parent=t;
				t.val=obj.Fn(vec.get(i).val+"#"+vec.get(i+1).val);
				v.add(t);
				i+=2;
			}
			vec=v;
		}
		rootnode=vec.get(0);
		return rootnode.val;
	}
		
	public List<Pair<String,String>> QueryDocument(int doc_idx){
		assert doc_idx>=1 && doc_idx<=numdocs;
		ArrayList<Pair<String,String>> al=new ArrayList<Pair<String,String>>();
		int l=1,r=numdocs;
		al.add(new Pair<String,String>(rootnode.val,null));
		TreeNode curr=rootnode;
		while(l<r){
			int m=l+(r-l)/2;
			al.add(new Pair<String,String>(curr.left.val,curr.right.val));
			if(doc_idx>m){
				l=m+1;
				curr=curr.right;
			}
			else{
				r=m;
				curr=curr.left;
			}
		}
		Collections.reverse(al);
		return al;
	}

	public static boolean Authenticate(List<Pair<String,String>> path, String summary){
		int i=0;
		CRF obj=new CRF(64);
		while(i<path.size()-1){
			String check=obj.Fn(path.get(i).get_first()+"#"+path.get(i).get_second());
			if(!check.equals(path.get(i+1).get_first()) && !check.equals(path.get(i+1).get_second())) return false;
			i+=1;
		}
		return true;
	}

	public String UpdateDocument(int doc_idx, String new_document){
		assert doc_idx>=1 && doc_idx<=numdocs;
		int l=1,r=numdocs;
		TreeNode curr=rootnode;
		while(l<r){
			int m=l+(r-l)/2;
			if(doc_idx>m){
				l=m+1;
				curr=curr.right;
			}
			else{
				r=m;
				curr=curr.left;
			}
		}
		curr.val=new_document;
		CRF obj=new CRF(64);
		while(curr.parent!=null){
			if(curr.parent.left==curr){
				curr.parent.val=obj.Fn(curr.val+"#"+curr.parent.right.val);
			}
			else{
				curr.parent.val=obj.Fn(curr.parent.left.val+"#"+curr.val);
			}
			curr=curr.parent;
		}
		return rootnode.val;
	}
}