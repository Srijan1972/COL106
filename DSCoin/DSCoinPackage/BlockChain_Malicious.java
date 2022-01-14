package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
    boolean check=true;
    CRF obj=new CRF(64);
    check&=tB.dgst.substring(0,4).equals("0000");
    if(tB.previous==null) check&=tB.dgst.equals(obj.Fn(start_string+"#"+tB.trsummary+"#"+tB.nonce));
    else check&=tB.dgst.equals(obj.Fn(tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce));
    List<String> treecheck=new ArrayList<String>();
    for(int i=0;i<tB.trarray.length;i++){
      treecheck.add(tB.Tree.get_str(tB.trarray[i]));
    }
    while(treecheck.size()>1){
      String l=treecheck.get(0);
      treecheck.remove(0);
      String r=treecheck.get(0);
      treecheck.remove(0);
      treecheck.add(obj.Fn(l+"#"+r));
    }
    check&=tB.trsummary.equals(treecheck.get(0));
    for(int i=0;i<tB.trarray.length;i++){
      check&=tB.checkTransaction(tB.trarray[i]);
    }
    return check;
  }

  public TransactionBlock FindLongestValidChain () {
    int len=0;
    TransactionBlock last=null;
    for(int i=0;i<lastBlocksList.length;i++){
      TransactionBlock curr=lastBlocksList[i];
      int chain=0;
      while(curr!=null){
        TransactionBlock set=curr;
        while(curr!=null && checkTransactionBlock(curr)){
          chain++; curr=curr.previous;
        }
        if(chain>len){
          len=chain; last=set;
        }
        chain=0;
        if(curr!=null) curr=curr.previous;
      }
    }
    return last;
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {
    CRF obj=new CRF(64);
    TransactionBlock last=FindLongestValidChain();
    long it=1000000001;
    if(last==null){
      newBlock.nonce=Long.toString(it);
      newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      while(!newBlock.dgst.substring(0,4).equals("0000")){
        it+=1;
        newBlock.nonce=Long.toString(it);
        newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      }
      lastBlocksList[0]=newBlock;
      return;
    }
    newBlock.nonce=Long.toString(it);
    newBlock.dgst=obj.Fn(last.dgst+"#"+newBlock.trsummary+"#"+newBlock.nonce);
    while(!newBlock.dgst.substring(0,4).equals("0000")){
      it+=1;
      newBlock.nonce=Long.toString(it);
      newBlock.dgst=obj.Fn(last.dgst+"#"+newBlock.trsummary+"#"+newBlock.nonce);
    }
    newBlock.previous=last;
    for(int i=0;i<lastBlocksList.length;i++){
      if(lastBlocksList[i]==last){
        lastBlocksList[i]=newBlock; return;
      }
    }
    for(int i=0;i<lastBlocksList.length;i++){
      if(lastBlocksList[i]==null){
        lastBlocksList[i]=newBlock; return;
      }
    }
  }
}
