package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
    CRF obj=new CRF(64);
    if(lastBlock==null){
      long it=1000000001;
      newBlock.nonce=Long.toString(it);
      newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      while(!newBlock.dgst.substring(0,4).equals("0000")){
        it+=1;
        newBlock.nonce=Long.toString(it);
        newBlock.dgst=obj.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      }
    }
    else{
      newBlock.previous=lastBlock;
      long it=1000000001;
      newBlock.nonce=Long.toString(it);
      newBlock.dgst=obj.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      while(!newBlock.dgst.substring(0,4).equals("0000")){
        it+=1;
        newBlock.nonce=Long.toString(it);
        newBlock.dgst=obj.Fn(lastBlock.dgst+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      }
    }
    lastBlock=newBlock;
  }
}
