import Includes.*;

public class AuthList{
	public static final String start_string = "2020CS50444";
	public Node firstNode;
	public Node lastNode;

	public static boolean CheckList(AuthList current, String proof) throws AuthenticationFailedException {
		CRF obj = new CRF(64);
		Node curr = current.firstNode;
		boolean initial = true;
		while(curr != null){
			if(initial){
				String hsh = obj.Fn(AuthList.start_string + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh)) {
					throw new AuthenticationFailedException();
				}
				initial = false;
				curr = curr.next;
			}else if(curr == current.lastNode){
				if(!curr.dgst.equals(proof)) {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}else{
				String hsh = obj.Fn(curr.previous.dgst + "#" + curr.data.value);
				if(!curr.dgst.equals(hsh))  {
					throw new AuthenticationFailedException();
				}
				curr = curr.next;
			}
		}
		return true;
	}


	public String InsertNode(Data datainsert, String proof) throws AuthenticationFailedException {
		CheckList(this,proof);
		CRF obj=new CRF(64);
		if(lastNode==null){
			lastNode=new Node(datainsert);
			lastNode.dgst=obj.Fn(start_string+"#"+datainsert.value);
			firstNode=lastNode;
			return lastNode.dgst;
		}
		else if(firstNode==lastNode){
			Node n=new Node(datainsert);
			n.previous=firstNode;
			firstNode.next=n;
			n.dgst=obj.Fn(firstNode.dgst+"#"+datainsert.value);
			lastNode.next=n;
			lastNode=n;
			return lastNode.dgst;
		}
		else{
			Node n=new Node(datainsert);
			n.previous=lastNode;
			n.dgst=obj.Fn(lastNode.dgst+"#"+datainsert.value);
			lastNode.next=n;
			lastNode=n;
			return lastNode.dgst;
		}
	}
		

	public String DeleteFirst(String proof) throws AuthenticationFailedException, EmptyListException {
		CheckList(this,proof);	
		if(firstNode==null) {
			throw new EmptyListException();
		}
		CheckList(this,proof);
		CRF obj=new CRF(64);
		if(firstNode==lastNode){
			firstNode=null;lastNode=null;
			return null;
		}
		Node curr=firstNode.next;
		firstNode=curr;
		firstNode.previous=null;
		firstNode.dgst=obj.Fn(start_string+"#"+firstNode.data.value);
		curr=firstNode.next;
		while(curr!=null){
			curr.dgst=obj.Fn(curr.previous.dgst+"#"+curr.data.value);
			curr=curr.next;
		}
		return lastNode.dgst;
	}


	public String DeleteLast(String proof) throws AuthenticationFailedException, EmptyListException {
			CheckList(this,proof);
			if(firstNode==null){
				throw new EmptyListException();
			}
			if(lastNode==firstNode){
				firstNode=null; lastNode=null;
				return null;
			}
			lastNode=lastNode.previous;
			return lastNode.dgst;
	}

	public static Node RetrieveNode(AuthList current, String proof, Data data) throws AuthenticationFailedException, DocumentNotFoundException{
		CheckList(current,proof);
		Node curr=current.firstNode;
		while(curr!=null){
			if(curr.data.value.equals(data.value)){
				return curr;
			}
		}
		throw new DocumentNotFoundException();
	}

	public static void AttackList(AuthList current, String new_data)throws EmptyListException{
	}

}
