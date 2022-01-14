import Includes.*;

public class StackNode{
	public Data data;
	public StackNode prev;
	public String dgst;
	public StackNode(Data ins){
		data=ins;
		prev=null;
		dgst=null;
	}
}