package simpledb.query;

import simpledb.record.*;


public class RenameScan implements UpdateScan {
	private Scan s;
	private String oldname;
	private String newname;
	
	/**
	 * Create a select scan having the specified underlying
	 * scan and predicate.
	 * @param s the scan of the underlying query
	 * @param pred the selection predicate
	 */
	public RenameScan(Scan s, String oldname,String newname) {
		this.s = s;
		this.oldname = oldname;
		this.newname=newname;
	}

	// Scan methods

	public void beforeFirst() {
		s.beforeFirst();
	}

	public boolean next() {
		return s.next();
	}

	public int getInt(String fldname) {
		if(fldname.equals(newname)) {
			return s.getInt(oldname);
		}else {
			return s.getInt(fldname);
		}
	}

	public String getString(String fldname) {
		if(fldname.equals(newname)) {
			return s.getString(oldname);
		}else {
			return s.getString(fldname);
		}
	}

   public Constant getVal(String fldname) {
	   if(fldname.equals(newname)) {
			return s.getVal(oldname);
		}else {
			return s.getVal(fldname);
		}
   }

	public boolean hasField(String fldname) {
		if(fldname.equals(newname)) {
			return true;
		}else {
			return s.hasField(fldname);
		}
	}

   public void close() {
      s.close();
   }

	// UpdateScan methods

	public void setInt(String fldname, int val) {
		if(fldname.equals(newname)) {
			UpdateScan us = (UpdateScan) s;
			us.setInt(oldname, val);
		}else {
			UpdateScan us = (UpdateScan) s;
			us.setInt(fldname, val);
		}
		
	}

	public void setString(String fldname, String val) {
		if(fldname.equals(newname)) {
			UpdateScan us = (UpdateScan) s;
			us.setString(oldname, val);
		}else {
			UpdateScan us = (UpdateScan) s;
			us.setString(fldname, val);
		}
	}

   public void setVal(String fldname, Constant val) {
	   if(fldname.equals(newname)) {
			UpdateScan us = (UpdateScan) s;
			us.setVal(oldname, val);
	   }else {
			UpdateScan us = (UpdateScan) s;
			us.setVal(fldname, val);
      }
   }

	public void delete() {
		UpdateScan us = (UpdateScan) s;
		us.delete();
	}

	public void insert() {
		UpdateScan us = (UpdateScan) s;
		us.insert();
	}

	public RID getRid() {
		UpdateScan us = (UpdateScan) s;
		return us.getRid();
	}

	public void moveToRid(RID rid) {
		UpdateScan us = (UpdateScan) s;
		us.moveToRid(rid);
	}
}
