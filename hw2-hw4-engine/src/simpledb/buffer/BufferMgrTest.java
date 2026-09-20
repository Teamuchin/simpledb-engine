package simpledb.buffer;

import simpledb.server.SimpleDB;
import simpledb.file.*;

public class BufferMgrTest {
   public static void main(String[] args) throws Exception {
      SimpleDB db = new SimpleDB("buffermgrtest", 400, 4);
      BufferMgr bm = db.bufferMgr();

      Buffer[] buff = new Buffer[7];
      bm.printStatus();
      System.out.printf("\n");
      buff[0] = bm.pin(new BlockId("testfile", 0));
      buff[1] = bm.pin(new BlockId("testfile", 1));
      buff[2] = bm.pin(new BlockId("testfile", 2));
      buff[3] = bm.pin(new BlockId("testfile", 3));
      bm.printStatus();
      System.out.printf("\n");
      bm.unpin(buff[1]); 
      buff[1] = null;
      bm.printStatus();
      System.out.printf("\n");
      buff[4] = bm.pin(new BlockId("testfile", 4));
      bm.printStatus();
      System.out.printf("\n");
      bm.unpin(buff[3]); 
      buff[3] = null;
      bm.unpin(buff[0]); 
      buff[0] = null;
      bm.unpin(buff[2]); 
      buff[2] = null;
      bm.unpin(buff[4]); 
      buff[4] = null;
      bm.printStatus();
      System.out.printf("\n");
      buff[5] = bm.pin(new BlockId("testfile", 5));
      buff[6] = bm.pin(new BlockId("testfile", 6));
      System.out.println("Final Buffer Allocation:");
      bm.printStatus();
   }
}
