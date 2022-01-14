package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class Moderator
 {

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
    int id=100000;
    int id_n=100000;
    int k=DSObj.memberlist.length;
    Members mod=new Members();
    mod.UID="Moderator";
    int i=0;
    while(i<coinCount){
      int j=0;
      Transaction[] tr=new Transaction[DSObj.bChain.tr_count];
      while(j<DSObj.bChain.tr_count){
        int ins=i%k;
        Transaction t=new Transaction();
        t.coinID=Integer.toString(id);
        t.Source=mod;
        t.Destination=DSObj.memberlist[ins];
        tr[j]=t;
        id++; j++; i++;
      }
      TransactionBlock tB=new TransactionBlock(tr);
      for(int l=0;l<DSObj.bChain.tr_count;l++){
        tB.trarray[l].Destination.mycoins.add(new Pair<String,TransactionBlock>(Integer.toString(id_n+l),tB));
      }
      id_n+=DSObj.bChain.tr_count;
      DSObj.bChain.InsertBlock_Honest(tB);
    }
    DSObj.latestCoinID=Integer.toString(id-1);
  }

  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
    int id=100000;
    int id_n=100000;
    int k=DSObj.memberlist.length;
    Members mod=new Members();
    mod.UID="Moderator";
    int i=0;
    while(i<coinCount){
      int j=0;
      Transaction[] tr=new Transaction[DSObj.bChain.tr_count];
      while(j<DSObj.bChain.tr_count){
        int ins=i%k;
        Transaction t=new Transaction();
        t.coinID=Integer.toString(id);
        t.Source=mod;
        t.Destination=DSObj.memberlist[ins];
        tr[j]=t;
        id++; j++; i++;
      }
      TransactionBlock tB=new TransactionBlock(tr);
      for(int l=0;l<DSObj.bChain.tr_count;l++){
        tB.trarray[l].Destination.mycoins.add(new Pair<String,TransactionBlock>(Integer.toString(id_n+l),tB));
      }
      id_n+=DSObj.bChain.tr_count;
      DSObj.bChain.InsertBlock_Malicious(tB);
    }
    DSObj.latestCoinID=Integer.toString(id-1);
  }
}
