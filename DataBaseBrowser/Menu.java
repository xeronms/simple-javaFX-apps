import com.mysql.cj.util.StringUtils;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;


public class Menu {

    private ScrollPane root = new ScrollPane();
    private TableView< Book > table = new TableView<>( );
    DataBase db = new DataBase();

    ObservableList< Book > books;

    //  creating whole menu logic
    public Menu(){
        db.createBooksList();
        books = db.getBooksList();

        //  searching author or id
        TextField searchField = new TextField();
        searchField.setPromptText( "Search author" );
        searchField.setPrefSize(500, 20);
        searchField.setOnAction(e -> {
            String search = searchField.getText();
            table.getItems().clear();
            // SELECT *....
            if ( StringUtils.isStrictlyNumeric( search )){
                db.createBooksList("SELECT * FROM books WHERE isbn LIKE '% " + search + "%'");
        }
            else {
                db.createBooksList("SELECT * FROM books WHERE author LIKE '% " + search + "%'");
            }
            books = db.getBooksList();
        });

        //  adding new element to books db
        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            addNewElement();
        });

        //  composition
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        //  root
        root.setPadding(new Insets(20));
        createTable();
        vbox.getChildren().addAll( searchField, table, addButton);
        root.setContent( vbox );
}

    // creating table containing all data from books list
    private void createTable(){
        TableColumn< Book, String > idCol = new TableColumn<>("ID");
        TableColumn< Book, String > titleCol = new TableColumn<>("Title");
        TableColumn< Book, String > authorCol = new TableColumn<>("Author");
        TableColumn< Book, Integer > yearCol = new TableColumn<>("year");

        idCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        titleCol.setMinWidth(200);

        table.getColumns().addAll(idCol, titleCol, authorCol, yearCol);
        table.setItems( books );
    }

    //  adding new element to data base
    //  element is created by a user
    private void addNewElement(){
        String id = createWindow("Enter the new book ID");
        String title = createWindow("Enter the new book title");
        String author = createWindow("Enter the new book author");
        String year = createWindow("Enter the new book year");

        db.addBook(id, title, author, Integer.parseInt(year));
    }

    //  creating new windows and adding new element options
    static String createWindow( String message ){
        Stage window = new Stage();
        AtomicReference<String> out = new AtomicReference<>();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new book to the list");
        window.setMinWidth(300);

        Label label = new Label();
        label.setText( message );

        TextField text = new TextField();

        Button ok = new Button("OK");
        ok.setOnAction(e -> {
            out.set(text.getText());
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,text,ok);

        Scene scene = new Scene(layout);
        window.setScene( scene );
        window.showAndWait();

        return out.get();
    }

    public ScrollPane getMenu(){
        return root;
    }
}
