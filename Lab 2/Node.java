import Includes.*;

public class Node{
	public Data data;
	public Node previous;
	public Node next;
	public String dgst;
	public Node(Data ins){
		data=ins;
		previous=null;
		next=null;
		dgst=null;
	}
}
