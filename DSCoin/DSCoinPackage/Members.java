package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void sortList(){
    for(int i=0;i<mycoins.size();i++){
      for(int j=i+1;j<mycoins.size();j++){
        if(mycoins.get(i).get_first().compareTo(mycoins.get(j).get_first())>0) Collections.swap(mycoins,i,j);
      }
    }
  }

  public void initiateCoinsend(String destUID, DSCoin_Honest DSobj) {
    Pair<String,TransactionBlock> coin=mycoins.get(0);
    mycoins.remove(0);
    Transaction t=new Transaction();
    t.coinID=coin.get_first();
    t.coinsrc_block=coin.get_second();
    t.Source=this;
    for(int i=0;i<DSobj.memberlist.length;i++){
      if(DSobj.memberlist[i].UID.equals(destUID)){
        t.Destination=DSobj.memberlist[i];
      }
    }
    int i=0;
    while(in_process_trans[i]!=null) i++;
    in_process_trans[i]=t;
    DSobj.pendingTransactions.AddTransactions(t);
  }

  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
    TransactionBlock curr=DSObj.bChain.lastBlock;
    int idx=0;
    boolean found=false;
    while(curr!=null && !found){
      for(int i=0;i<curr.trarray.length;i++){
        if(tobj==curr.trarray[i]){
          found=true;
          idx=i;
        }
      }
      if(!found) curr=curr.previous;
    }
    if(curr==null || !found) throw new MissingTransactionException();
    List<Pair<String,String>> rootpath=new ArrayList<Pair<String,String>>();
    TreeNode it=curr.Tree.rootnode;
    rootpath.add(new Pair<String,String>(it.val,null));
    int l=0,r=curr.Tree.numdocs-1;
    while(l<r){
      int m=l+(r-l)/2;
      rootpath.add(new Pair<String,String>(it.left.val,it.right.val));
      if(m>=idx){
        it=it.left; r=m;
      }
      else{
        it=it.right; l=m+1;
      }
    }
    Collections.reverse(rootpath);
    List<Pair<String,String>> blockpath=new ArrayList<Pair<String,String>>();
    TransactionBlock tB=DSObj.bChain.lastBlock;
    while(tB!=curr.previous){
      blockpath.add(new Pair<String,String>(tB.dgst,tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce));
      tB=tB.previous;
    }
    blockpath.add(new Pair<String,String>(curr.previous.dgst,null));
    Collections.reverse(blockpath);
    int i;
    for(i=0;i<in_process_trans.length;i++){
      if(tobj==in_process_trans[i]) in_process_trans[i]=null;
    }
    tobj.Destination.mycoins.add(new Pair<String,TransactionBlock>(tobj.coinID,DSObj.bChain.lastBlock));
    tobj.Destination.sortList();
    return new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(rootpath,blockpath);
  }

  public void MineCoin(DSCoin_Honest DSObj) {
    try{
      int i=0;
      Transaction[] arr=new Transaction[DSObj.bChain.tr_count];
      while(i+1<DSObj.bChain.tr_count){
        boolean valid=true;
        Transaction it=DSObj.pendingTransactions.RemoveTransaction();
        int j=0;
        boolean check0=true;
        while(arr[j]!=null){
          if(arr[j].coinID.equals(it.coinID)) check0=false;
          j++;
        }
        valid&=check0;
        boolean check1=it.coinsrc_block==null;
        if(it.coinsrc_block!=null){
          for(int k=0;k<it.coinsrc_block.trarray.length;k++){
            if(it.coinsrc_block.trarray[k].coinID.equals(it.coinID) && it.Source.UID.equals(it.coinsrc_block.trarray[k].Destination.UID)) check1=true;
          }
        }
        valid&=check1;
        boolean check2=true;
        TransactionBlock curr=DSObj.bChain.lastBlock;
        if(it.coinsrc_block!=null){
          while(curr!=it.coinsrc_block){
            for(int k=0;k<curr.trarray.length;k++){
              if(curr.trarray[k].coinID.equals(it.coinID)) check2=false;
            }
            curr=curr.previous;
          }
        }
        valid&=check2;
        if(valid){
          arr[i]=it; i++;
        }
      }
      Transaction minerRewardTransaction=new Transaction();
      int id=Integer.parseInt(DSObj.latestCoinID);
      id++;
      DSObj.latestCoinID=Integer.toString(id);
      minerRewardTransaction.coinID=DSObj.latestCoinID;
      minerRewardTransaction.Destination=this;
      arr[i]=minerRewardTransaction;
      TransactionBlock tB=new TransactionBlock(arr);
      DSObj.bChain.InsertBlock_Honest(tB);
      mycoins.add(new Pair<String,TransactionBlock>(DSObj.latestCoinID,tB));
      sortList();
    }
    catch(EmptyQueueException e){}
  }

  public void MineCoin(DSCoin_Malicious DSObj) {
    try{
      int i=0;
      Transaction[] arr=new Transaction[DSObj.bChain.tr_count];
      while(i+1<DSObj.bChain.tr_count){
        boolean valid=true;
        Transaction it=DSObj.pendingTransactions.RemoveTransaction();
        int j=0;
        boolean check0=true;
        while(arr[j]!=null){
          if(arr[j].coinID.equals(it.coinID)) check0=false;
          j++;
        }
        valid&=check0;
        boolean check1=it.coinsrc_block==null;
        if(it.coinsrc_block!=null){
          for(int k=0;k<it.coinsrc_block.trarray.length;k++){
            if(it.coinsrc_block.trarray[k].coinID.equals(it.coinID) && it.Source.UID.equals(it.coinsrc_block.trarray[k].Destination.UID)) check1=true;
          }
        }
        valid&=check1;
        boolean check2=true;
        TransactionBlock curr=DSObj.bChain.FindLongestValidChain();
        if(it.coinsrc_block!=null){
          while(curr!=it.coinsrc_block && BlockChain_Malicious.checkTransactionBlock(curr)){
            for(int k=0;k<curr.trarray.length;k++){
              if(curr.trarray[k].coinID.equals(it.coinID)) check2=false;
            }
            curr=curr.previous;
          }
        }
        valid&=check2;
        if(valid){
          arr[i]=it; i++;
        }
      }
      Transaction minerRewardTransaction=new Transaction();
      int id=Integer.parseInt(DSObj.latestCoinID);
      id++;
      DSObj.latestCoinID=Integer.toString(id);
      minerRewardTransaction.coinID=DSObj.latestCoinID;
      minerRewardTransaction.Destination=this;
      arr[i]=minerRewardTransaction;
      TransactionBlock tB=new TransactionBlock(arr);
      DSObj.bChain.InsertBlock_Malicious(tB);
      mycoins.add(new Pair<String,TransactionBlock>(DSObj.latestCoinID,tB));
      sortList();
    }
    catch(EmptyQueueException e){}
  }
  
 }