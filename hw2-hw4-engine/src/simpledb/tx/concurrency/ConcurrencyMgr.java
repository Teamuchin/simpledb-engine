package simpledb.tx.concurrency;

import java.util.*;
import simpledb.file.BlockId;

/**
 * The concurrency manager for the transaction.
 * Each transaction has its own concurrency manager. 
 * The concurrency manager keeps track of which locks the 
 * transaction currently has, and interacts with the
 * global lock table as needed. 
 * @author Edward Sciore
 */
public class ConcurrencyMgr {

   /**
    * The global lock table. This variable is static because 
    * all transactions share the same table.
    */
   private static LockTable locktbl = new LockTable();
   private Map<BlockId,String> locks  = new HashMap<BlockId,String>();


   public void sLock(BlockId blk, int txnum) {
	   if (!hasXLock(blk) && !hasSLock(blk)) {
		  locktbl.sLock(blk, txnum);
	      locks.put(blk, "S");
	   }
	}


   public void xLock(BlockId blk, int txnum) {
	   if (!hasXLock(blk)) {
	      locktbl.xLock(blk, txnum);
	      locks.put(blk, "X");
	   }
	}

   
   public void release(int txid) {
      for (BlockId blk : locks.keySet()) 
         locktbl.unlock(blk,txid);
      locks.clear();
   }

   private boolean hasXLock(BlockId blk) {
      String locktype = locks.get(blk);
      return locktype != null && locktype.equals("X");
   }
   private boolean hasSLock(BlockId blk) {
	   String locktype = locks.get(blk);
	   return locktype != null && locktype.equals("S");
	}
}
