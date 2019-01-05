import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DataBase {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rs = null;

    ObservableList< Book > books = FXCollections.observableArrayList();
    private int i = 0;

    //   creating connection
    public DataBase(){
        connect();
    }

    //  connecting to data base
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection =
                    DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/msienko1",
                            "msienko1","6H8YTDRpTUrwPYpT");
        }
        catch (SQLException e){
            reconnect(e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //  creating books list from whole data base

    public void createBooksList(){
        createBooksList("SELECT * FROM books");
    }

    //  creating books list from SQL query
    public void createBooksList( String query ){
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery( query );

            while(rs.next()){
                // adding to books list new book from data base
                books.add( new Book(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                ) );
            }
        }catch (SQLException ex){
            reconnect( ex );
            // handle any errors
        } finally {
            close();
        }
    }

    public ObservableList<Book> getBooksList(){
        return books;
    }

    //  adding new book to a data base
    public void addBook( String id, String title, String author, int year ){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO books VALUES ("+
                            "'"+id+"'"+","+
                            "'"+title+"'"+","+
                            "'"+author+"'"+","+
                            "'"+year+"'"+
                            ")"
            );
        } catch (SQLException ex){
            reconnect( ex );
            // handle any errors
        } finally {
            close();
        }
    }


    //  releasing last statement and resultSet
    private void close(){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlEx) { } // ignore
            statement = null;
        }
    }

    //  reconnecting to server in case of lost connection exception
    private void reconnect( SQLException e){
        if ( i < 3){
            ++i;
            connect();
        }
        else{
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

            Menu.createWindow("NO CONNECTION");
        }
    }
}
