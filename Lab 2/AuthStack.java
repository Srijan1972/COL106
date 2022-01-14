import Includes.*;

public class AuthStack{
	// PLEASE USE YOUR ENTRY NUMBER AS THE START STRING
	private static final String start_string = "2020CS50444";
	private StackNode top;
	public static boolean CheckStack(AuthStack current, String proof) throws AuthenticationFailedException{
		CRF obj=new CRF(64);
		StackNode curr=current.top;
		boolean init=true;
		while(curr!=null){
			if(init){
				if(!proof.equals(curr.dgst)){
					throw new AuthenticationFailedException();
				}
				curr=curr.prev;
				init=false;
			}
			else if(curr.prev==null){
				String ch=obj.Fn(start_string+"#"+curr.data.value);
				if(!ch.equals(curr.dgst)){
					throw new AuthenticationFailedException();
				}
				curr=curr.prev;
			}
			else{
				String ch=obj.Fn(curr.prev.dgst+"#"+curr.data.value);
				if(!ch.equals(curr.dgst)){
					throw new AuthenticationFailedException();
				}
				curr=curr.prev;
			}
		}
		return true;
	}

	public String push(Data datainsert, String proof)throws AuthenticationFailedException{
		CheckStack(this,proof);
		CRF obj=new CRF(64);
		if(top==null){
			top=new StackNode(datainsert);
			top.dgst=obj.Fn(start_string+"#"+datainsert.value);
			return top.dgst;
		}
		else{
			StackNode node=new StackNode(datainsert);
			node.dgst=obj.Fn(top.dgst+"#"+datainsert.value);
			node.prev=top;
			top=node;
			return node.dgst;
		}
	}

	public String pop(String proof)throws AuthenticationFailedException, EmptyStackException{
		CheckStack(this,proof);
		if(top==null){
			throw new EmptyStackException();
		}
		else{
			top=top.prev;
			if(top==null) return null;
			return top.dgst;
		}
	}

	public StackNode GetTop(String proof)throws AuthenticationFailedException{
		CheckStack(this,proof);
		return top;
	}
}