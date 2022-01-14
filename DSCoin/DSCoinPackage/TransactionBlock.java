package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  public TransactionBlock(Transaction[] t) {
    trarray=t.clone();
    Tree=new MerkleTree();
    trsummary=Tree.Build(t);
    previous=null;
    dgst=null;
  }

  public boolean checkTransaction (Transaction t) {
    if(t.coinsrc_block==null) return true;
    TransactionBlock src=t.coinsrc_block;
    boolean c1=false;
    for(int i=0;i<src.trarray.length;i++){
      if(src.trarray[i].coinID.equals(t.coinID) && src.trarray[i].Destination.UID.equals(t.Source.UID)) c1=true;
    }
    boolean c2=true;
    TransactionBlock curr=previous;
    while(curr!=src){
      for(int i=0;i<curr.trarray.length;i++){
        if(t.coinID.equals(curr.trarray[i].coinID)) c2=false;
      }
      curr=curr.previous;
    }
    return c1 && c2;
  }

  
}
