package co.ebti.rc.wordstat;

import java.sql.*;
import java.util.ArrayList;

public class SQLite {
    private static String DBAdress = "org.sqlite.JDBC";
    private static String DBConnectionAdress = "jdbc:sqlite:test.sqlite";
    private static Connection c =null;
    private static Statement stmt =null;

    public static Connection connectCheck()
    {
        Connection c = null;
        try {
            Class.forName(DBAdress);
            c = DriverManager.getConnection(DBConnectionAdress);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
        return c;
    }

    public static void tableCreate(String tableName)
    {
        try {
            c = connectCheck();
            stmt = c.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS  "+tableName+" " +
                    "(ID Integer PRIMARY KEY AUTOINCREMENT," +
                    " PHName varchar(40) NOT NULL, " +
                    " Specification varchar(1000) NOT NULL) ";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Table '"+tableName+"' created successfully");
    }

    public static void insertRecord(String tableName, String phoneName, String phoneSpec)
    {
        try {
            c = connectCheck();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "INSERT INTO "+tableName+" (PHName,Specification) " +
                    "VALUES ('"+phoneName+"', '"+phoneSpec+"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void selectRecordFrom(String tableName, String PHName)
    {
        try {
            c = connectCheck();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM "+tableName+" where PHName="+PHName+";" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  eng = rs.getString("ENG");
                String  rus = rs.getString("RUS");
                //System.out.println( "ID = " + id );
                System.out.println( "ENG = " + eng );
                System.out.println( "RUS = " + rus );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static String checkRecord(String tableName, String phoneName)
    {
        String a = "";
        try {

            c = connectCheck();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PhonesSpecificaton WHERE PHName = '"+phoneName+"';");
            while (rs.next()){
                a = a+rs.getString("PHName");
            }

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return a;
    }

    public static String recievePhoneSpec(String tableName, String phoneName)
    {
        String a = "";
        try {

            c = connectCheck();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Specification FROM PhonesSpecificaton WHERE PHName=\""+phoneName+"\"");
            //ResultSet rs = stmt.executeQuery("SELECT DISTINCT Specification FROM PhonesSpecificaton PHName = ;");

            while (rs.next()){
                a = a+rs.getString("Specification");
            }

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return a;
    }

    public static void updateRecord(String tableName, String updatedColumn, int ID, String updatedValue)
    {
        try {
            c = connectCheck();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            //String sql = "UPDATE WORDS1 set RUS = 'eber' where ID=2;";
            String sql = "UPDATE "+tableName+" set "+updatedColumn+" = '"+updatedValue+"' where ID="+ID+";";
            stmt.executeUpdate(sql);

            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void deleteRecord(String tableName, int deletedWordIdNumber)
    {
        try {
            c = connectCheck();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "DELETE from "+tableName+" where ID='"+deletedWordIdNumber+"';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void viewTableList()
    {
        try {
            c = connectCheck();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;");

            StringBuilder builder = new StringBuilder();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < columnCount;) {
                    builder.append(rs.getString(i + 1));
                    if (++i < columnCount) builder.append(",");
                }
                builder.append("\r\n");
            }
            String resultSetAsString = builder.toString();
            System.out.println(resultSetAsString);

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static ArrayList<Integer> viewColumnList(String tableName, String columnName) throws SQLException, ClassNotFoundException {
        c = connectCheck();
        c.setAutoCommit(false);
        stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT "+columnName+" FROM "+tableName+";");

        ResultSetMetaData metadata = rs.getMetaData();
        int numberOfColumns = metadata.getColumnCount();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (rs.next()) {
            int i = 1;
            while(i <= numberOfColumns) {
                arrayList.add(rs.getInt(i++));
            }
        }

        rs.close();
        stmt.close();
        c.close();

        return arrayList;
    }

    public static ArrayList<String> viewAllFromTable(String tableName) throws SQLException, ClassNotFoundException {
        c = connectCheck();
        c.setAutoCommit(false);
        stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName+";");

        ResultSetMetaData metadata = rs.getMetaData();
        int numberOfColumns = metadata.getColumnCount();

        ArrayList<String> arrayList = new ArrayList<String>();
        while (rs.next()) {
            int i = 1;
            while(i <= numberOfColumns) {
                arrayList.add(String.valueOf(rs.getInt(i++)));
                arrayList.add(rs.getString(i++));
                arrayList.add(rs.getString(i++));
            }
        }

        rs.close();
        stmt.close();
        c.close();

        return arrayList;
    }

    public static String viewColumnListString(String tableName, String columnName) throws SQLException, ClassNotFoundException {
        c = connectCheck();
        c.setAutoCommit(false);
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT "+columnName+" FROM "+tableName+";");

        StringBuilder builder = new StringBuilder();
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 0; i < columnCount;) {
                builder.append(rs.getString(i + 1));
                if (++i < columnCount) builder.append(",");
            }
            builder.append("\r\n");
        }
        String resultSetAsString = builder.toString();

        rs.close();
        stmt.close();
        c.close();

        return resultSetAsString;
    }
}
