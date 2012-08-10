/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.icts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 *
 * @author Ray
 */
public class SimpleDB {
    // connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/Charisse";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USERNAME = "tester";
    private static final String PASSWORD = "tester";
    private Connection conn;
    
    // logger variables
    private static Logger logger = Logger.getLogger(SimpleDB.class);
    private static SimpleLayout layout = new SimpleLayout();
    private static Appender appender = new ConsoleAppender(layout);
    private static Level logLevel = Level.INFO;
    
    // SQL
    private static PreparedStatement INSERT_PS;
    private static String INSERT_STMT = "INSERT INTO map (name, abbrev) VALUES (?, ?)";
    private static PreparedStatement INSERT_PS_TEMP;
    private static String INSERT_STMT_TEMP = "INSERT INTO temp VALUES (?)";
    private static PreparedStatement INSERT_PS_FINAL;
    private static String INSERT_STMT_FINAL = "INSERT INTO combinations (combo, size, count) VALUES (?, ?, ?)";
    
    public SimpleDB(){
        // setup logger
        logger.setLevel(logLevel);
        logger.addAppender(appender);
    }
    
    /**
     * Connect to the data source
     */
    public void connect() {
        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            INSERT_PS = conn.prepareCall(INSERT_STMT);
            INSERT_PS_TEMP = conn.prepareCall(INSERT_STMT_TEMP);
            INSERT_PS_FINAL = conn.prepareCall(INSERT_STMT_FINAL);
            System.out.println("connection successful");

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Commits and closes the connection
     * @throws SQLException 
     */
    public void close() throws SQLException{
        conn.commit();
        conn.close();
    }
    
    public void commit() throws SQLException{
        conn.commit();
    }
    
    /**
     * Writes the map to the database
     * @param map   the map to serialize
     * @throws SQLException 
     */
    public void writeToDB(HashMap<String, String> map) throws SQLException{
        if(conn == null) connect();
        Iterator<Entry<String, String> > it = map.entrySet().iterator();
        while(it.hasNext()){
            Entry<String, String> e = it.next();
            logger.debug(e.getKey() + ", " + e.getValue());
            INSERT_PS.setString(1, e.getKey());
            INSERT_PS.setString(2, e.getValue());
            INSERT_PS.execute();
        }
    }
    
    /**
     * Reads in the map from the database
     * @return  the map
     * @throws SQLException 
     */
    public HashMap<String, String> readFromDB() throws SQLException{
        HashMap<String, String> r = new HashMap<String, String>();
        if(conn == null) connect();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM map");
        while(rs.next()){
            r.put(rs.getString(1), rs.getString(2));
        }
        return r;
    }
    
    public void addToTemp(String combo) throws SQLException{
        if(conn == null) connect();
        INSERT_PS_TEMP.setString(1, combo);
        INSERT_PS_TEMP.execute();
    }
    
    public void combine() throws SQLException{
        if(conn == null) connect();
        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(1000000);
        ResultSet rs = stmt.executeQuery("SELECT * FROM temp ORDER BY combo");
        
        String prev = "";
        int size = -1;
        int count = 0;
        boolean first = true;
        
        while(rs.next()){
            String c = rs.getString(1);
            if(c.equals(prev)) {
                count++;
            } else {
                if(first){
                    first = false;
                } else {
                    INSERT_PS_FINAL.setString(1, prev);
                    INSERT_PS_FINAL.setInt(2, size);
                    INSERT_PS_FINAL.setInt(3, count);
                    INSERT_PS_FINAL.execute();
                }
                size = c.length()/3;
                prev = c;
                count = 1;
            }
        }
        // write the final entry
        INSERT_PS_FINAL.setString(1, prev);
        INSERT_PS_FINAL.setInt(2, size);
        INSERT_PS_FINAL.setInt(3, count);
        INSERT_PS_FINAL.execute();
        conn.commit();
    }
    
    
    private Statement stmt = null;
    private int batch = 0;
    public void prepareColTable() throws SQLException{
        if(conn == null) connect();
        stmt = conn.createStatement();
        batch = 0;
    }
    public void executeBatch() throws SQLException{
        logger.info(" executing batch...");
        stmt.executeBatch();
        stmt.clearBatch();
        batch = 0;
        conn.commit();
        logger.info(" fnished executing batch");
    }
    public void addToCols(List<String> list) throws SQLException{
        if(batch > 500000) this.executeBatch();
        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        for(int i=0; i<list.size(); i++){
            cols.append("col").append(i).append(",");
            vals.append("'").append(list.get(i).replace("'", "''")).append("'").append(",");
        }
        StringBuilder q = new StringBuilder();
        cols.setLength(cols.length()-1);
        vals.setLength(vals.length()-1);
        q.append("INSERT INTO cols (").append(cols).append(")").append(" VALUES (").append(vals).append(")");
        stmt.addBatch(q.toString());
        //System.out.println(q.toString());
        //stmt.execute(q.toString());
        batch++;
    }
}
