package simpledb.tx.concurrency;

import java.util.*;
import simpledb.file.BlockId;

/**
 * The lock table, which provides methods to lock and unlock blocks.
 * If a transaction requests a lock that causes a conflict with an
 * existing lock, then that transaction is placed on a wait list.
 * There is only one wait list for all blocks.
 * When the last lock on a block is unlocked, then all transactions
 * are removed from the wait list and rescheduled.
 * If one of those transactions discovers that the lock it is waiting for
 * is still locked, it will place itself back on the wait list.
 * @author Edward Sciore
 */
class LockTable {   
   private Map<BlockId,List<Integer>> locks = new HashMap<>();
   
   public synchronized void sLock(BlockId blk, int txnum) {
	    while (hasXlock(blk, txnum)) {
	        if (shouldAbort(blk, txnum)) {
	            throw new LockAbortException();
	        }
	        try {
	            wait();
	        } catch (InterruptedException e) {
	            throw new LockAbortException();
	        }
	    }
	    List<Integer> slockholders = locks.get(blk);
	    if (slockholders == null) {
	    	slockholders = new ArrayList<>();
	        locks.put(blk, slockholders);
	    }
	    if (!slockholders.contains(txnum) && !slockholders.contains(-txnum)) {
	    	slockholders.add(txnum);
	    }
	}
   
   synchronized void xLock(BlockId blk,int txnum) {
	    while (hasOtherSLocks(blk, txnum)||hasXlock(blk,txnum)) {
	        if (shouldAbort(blk, txnum)) {
	            throw new LockAbortException();
	        }
	        try {
	            wait();
	        } catch (InterruptedException e) {
	            throw new LockAbortException();
	        }
	    }
	    List<Integer> xlockholders = locks.get(blk);
	    if (xlockholders == null) {
	    	xlockholders = new ArrayList<>();
	        locks.put(blk, xlockholders);
	    }
	    xlockholders.clear();
	    xlockholders.add(-txnum);
	}
   
   public synchronized void unlock(BlockId blk, int txid) {
	    List<Integer> holders = locks.get(blk);
	    if (holders != null) {
	        holders.remove(new Integer(txid));
	        holders.remove(new Integer(-txid));

	        if (holders.isEmpty()) {
	            locks.remove(blk);
	        }
	        notifyAll();
	    }
	}
   
   private boolean hasXlock(BlockId blk, int txnum) {
	  List<Integer> xlockholders = locks.get(blk);
      if (xlockholders == null || xlockholders.isEmpty()) 
    	  return false;
      int holder = xlockholders.get(0);
      return holder < 0 && holder != -txnum;
   }
   
   private boolean hasOtherSLocks(BlockId blk,int txnum) {
	   List<Integer> slockholders = locks.get(blk);
	   if (slockholders == null || slockholders.isEmpty()) 
		   return false;
	   if (slockholders.size() > 1) 
	    	return true;
	   int holder = slockholders.get(0);
	   return holder != txnum && holder != -txnum;
   }
   
   private boolean shouldAbort(BlockId blk, int txid) {
	    List<Integer> lockholders = locks.get(blk);
	    if (lockholders == null) return false;

	    for (int h : lockholders) {
	        int holderID = Math.abs(h);
	        if (holderID == txid) 
	        	continue;
	        if (holderID < txid) {
	            return true;
	        }
	    }
	    return false;
	}

}
