package simpledb.tx.recovery;

import java.util.*;
import simpledb.file.Page;
import simpledb.log.LogMgr;
import simpledb.tx.Transaction;

public class NQCheckpoint implements LogRecord {
	private ArrayList<Integer> idList;

	public NQCheckpoint(Page p) {
		this.idList = new ArrayList<>();
		int tpos = Integer.BYTES;
	    int size = p.getInt(tpos);
	    for(int i = 0;i<size;i++) {
	    	idList.add(p.getInt((2*tpos)+(i*Integer.BYTES)));
	    }
	}

	@Override
	public int op() {
		return NQCKPT;
	}

	@Override
	public int txNumber() {
		return -1;
	}

	@Override
	public void undo(Transaction tx) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Integer> getActiveIdList(){
		return idList;
	}
	
	public String toString() {
		return "<NQCKPT " + idList.toString().replaceAll("[\\[\\],]", "") + ">";
	   }
	public static int writeToLog(LogMgr lm, List<Integer> idList) {
	      byte[] rec = new byte[Integer.BYTES*(2+idList.size())];
	      Page p = new Page(rec);
	      p.setInt(0, NQCKPT);
	      p.setInt(4, idList.size());
	      for(int i = 0;i<idList.size();i++) {
		    	p.setInt((i+2)*Integer.BYTES,idList.get(i));
		   }
	      return lm.append(rec);
	   }

}
