package msg;

import java.sql.SQLException;

import projClasses.DbDataObject;
import sqlTestingDec27.MySqlMachine;
import msg.MsgObject;

public class SqlMachine {
	public DbBundle dbBundle;
	public SqlMachine(DbBundle dbBundle){
		this.dbBundle = dbBundle;
	}
	// get data from database
	// ///////////////////////////////////////////////////////////////////////////////////

	synchronized public DbBundle getSql() {
		if(dbBundle.sqlQuery.length() < 5) return new DbBundle();
		DbDataObject dbData = null;
		try {
			 dbData = new MySqlMachine(dbBundle.sqlQuery).getDbData();
			} catch (SQLException e) {
			e.printStackTrace();
		}
		String field = dbData.colLabels[1];
		String row = dbData.getRowData();
		//System.out.println(row);
		
		loadSqlBundle(dbData);
		
		return dbBundle;
	}
	// load database bundle
	// ///////////////////////////////////////////////////////////////////////////////////

	synchronized void loadSqlBundle(DbDataObject dbData) {
		
		// MsgObject msgObj = new MsgObject();
		dbBundle.alRowList = dbData.alRowList;
		dbBundle.colLabels = dbData.colLabels;
		dbBundle.columns = dbData.columns;
		dbBundle.rowData = dbData.getRowData();
		dbBundle.RowList = dbData.RowList;
		dbBundle.rsMap = dbData.rsMap;
 
	}

}
