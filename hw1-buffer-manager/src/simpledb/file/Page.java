package simpledb.file;

import java.nio.ByteBuffer;
import java.nio.charset.*;

public class Page {
   private ByteBuffer bb;
   public static Charset CHARSET = StandardCharsets.US_ASCII;

   // For creating data buffers
   public Page(int blocksize) {
      bb = ByteBuffer.allocateDirect(blocksize);
   }
   
   // For creating log pages
   public Page(byte[] b) {
      bb = ByteBuffer.wrap(b);
   }

   public int getInt(int offset) {
	   if(offset+Integer.BYTES>bb.capacity()|| offset<0) {
		   System.out.println("ERROR: Offset \""+offset+"\" out of range");
		   return 0;
	   }
      return bb.getInt(offset);
   }

   public void setInt(int offset, int n) {
	   if(offset+Integer.BYTES > (bb.capacity())) {
		   System.out.println("ERROR: The integer " + n + " does not fit at location " + offset + " of the page");
		   return;
	   }
      bb.putInt(offset, n);
   }

   public byte[] getBytes(int offset) {
	   if (offset < 0 || offset + Integer.BYTES > bb.capacity()) {
		   System.out.println("ERROR: Offset \""+offset+"\" out of range");
	        return new byte[0]; 
	    }
      bb.position(offset);
      int length = bb.getInt();
      if(offset+Byte.BYTES*length+Integer.BYTES>bb.capacity()|| offset<0) {
		   System.out.println("ERROR: Offset \""+offset+"\" out of range");
		   return new byte[0];
	   }
      byte[] b = new byte[length];
      bb.get(b);
      return b;
   }

   public void setBytes(int offset, byte[] b) {
	   if(offset+Byte.BYTES*b.length+Integer.BYTES > (bb.capacity())) {
		   System.out.println("ERROR: The Byte array does not fit at location " + offset + " of the page");
		   return;
	   }
      bb.position(offset);
      bb.putInt(b.length);
      bb.put(b);
   }
   
   public String getString(int offset) {
	   if(offset+Character.BYTES>bb.capacity()|| offset<0) {
		   System.out.println("ERROR: Offset \""+offset+"\" out of range");
		   return "";
	   }
	   int i= 0;
	   StringBuilder sb = new StringBuilder();
	   bb.position(offset+i);
	   char currentChar = bb.getChar();
	  while(currentChar !='\0') {
		  sb.append(currentChar);
		  i+=2;
		  if(offset+i+Character.BYTES>bb.capacity()) {
			   System.out.println("ERROR: Offset \""+offset+"\" out of range");
			   return "";
		   }
		  bb.position(offset+i);
		  currentChar = bb.getChar();
	  }
	  return sb.toString();
   }

   public void setString(int offset, String s) {
	   if(offset+(s.length()*2+2) > (bb.capacity())) {
		   System.out.println("ERROR: The string \"" + s + "\" does not fit at location " + offset + " of the page");
		   return;
	   }
      for(int i = 0;i<s.length()*2;i+=2) {
    	  bb.position(offset+i);
    	  bb.putChar(s.charAt(i/2));
      }
      bb.position(offset+s.length()*2);
      bb.putChar('\0');
   }

   public static int maxLength(int strlen) {
      float bytesPerChar = Character.BYTES;
      return ((strlen+1)* (int)bytesPerChar);
   }

   // a package private method, needed by FileMgr
   ByteBuffer contents() {
      bb.position(0);
      return bb;
   }
}
