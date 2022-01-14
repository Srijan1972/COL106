package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
    if(lastTransaction==null) firstTransaction=transaction;
    else lastTransaction.next=transaction;
    lastTransaction=transaction;
    numTransactions++;
  }
  
  public Transaction RemoveTransaction () throws EmptyQueueException {
    if(numTransactions==0) throw new EmptyQueueException();
    Transaction t=firstTransaction;
    firstTransaction=firstTransaction.next;
    if(firstTransaction==null) lastTransaction=null;
    numTransactions--;
    return t;
  }

  public int size() {
    return numTransactions;
  }
}
