import HelperClasses.Pair;
import HelperClasses.sha256;
import java.util.*;
import java.io.*;

public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/

    public Pair<String, String> FindCollDeterministic() {
        String base="Srijan";
        String m=Fn(base);
        HashMap<String,String> hm=new HashMap<String,String>();
        hm.put(base,Fn(base));
        String inp=m;
        String ans=Fn(m);
        while(true){
            if(hm.containsKey(ans)){
                return new Pair(hm.get(ans),inp);
            }
            hm.put(ans,inp);
            inp=ans;
            ans=Fn(inp);
        }
    }
    public String rand(){
        String s="ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
        StringBuilder sb=new StringBuilder(outputsize);
        for(int i=0;i<outputsize;i++){
            int idx= (int)(s.length()*Math.random());
            sb.append(s.charAt(idx));
        }
        return sb.toString();
    }
    public void FindCollRandomized() {
        try{
            FileOutputStream fa=new FileOutputStream("./FindCollRandomizedAttempts.txt",false);
            FileOutputStream fo=new FileOutputStream("./FindCollRandomizedOutcome.txt",false);
            PrintStream attempt=new PrintStream(fa);
            PrintStream outcome=new PrintStream(fo);
            String inp=rand();
            long p=(long)(Math.pow(4,outputsize));
			long n=p*1000L;
			int i=0; boolean done=false;
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put(Fn(inp),inp);
			while(i<n && !done){
				String s1=rand();
				String s2=Fn(s1);
				if(hm.containsKey(s2) && !s1.equals(hm.get(s2))){
					outcome.println("FOUND");
					outcome.println(hm.get(s2));
					outcome.println(s1);
					done=true;
					break;
				}
                hm.put(s2,s1);
			    i+=1L;
			}
            Iterator<String> l=hm.keySet().iterator();
		    while(l.hasNext()){
			    attempt.println(l.next());
		    }
            if(i==n || !done){
                outcome.println("NOT FOUND");
            }
		}
        catch(FileNotFoundException e){
            System.out.println("File not Found");
        }
    }
}
