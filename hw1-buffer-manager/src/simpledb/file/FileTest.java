package simpledb.file;

import java.io.*;
import simpledb.server.SimpleDB;

public class FileTest {
   public static void main(String[] args) throws IOException {
      SimpleDB db = new SimpleDB("filetest", 400, 8);
      FileMgr fm = db.fileMgr();
      BlockId blk = new BlockId("testfile", 2);
      int pos1 = 360;

      Page p1 = new Page(fm.blockSize());
      p1.setString(pos1, "abcdefghijklm");
      p1.setString(pos1, "abcdef");
      //if code reads the short one it means that /0 is recognized
      int size = Page.maxLength("abcdefghijklm".length());
      int pos2 = pos1 + size;
      p1.setInt(pos2, 345);
      int pos3 =pos2+Integer.BYTES;
      byte[] byte1 = {1, 2, 3, 4};
      p1.setBytes(pos3, byte1);
      int pos4 =pos3+byte1.length*Byte.BYTES+Integer.BYTES;
      p1.setInt(pos4, 9);
      //out of range test
      fm.write(blk, p1);
      
      
      Page p2 = new Page(fm.blockSize());
      fm.read(blk, p2);
      System.out.println("offset " + pos4 + " contains " + p2.getInt(pos4));
      System.out.println("offset " + pos3 + " contains " + java.util.Arrays.toString(p2.getBytes(pos3)));
      System.out.println("offset " + pos2 + " contains " + p2.getInt(pos2));
      System.out.println("offset " + pos1 + " contains " + p2.getString(pos1));
   }
}