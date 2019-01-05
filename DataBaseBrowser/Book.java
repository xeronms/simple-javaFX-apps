public class Book {

    private String isbn;
    private String title;
    private String author;
    private int year;

    public Book(String id, String t, String a, int y){
        isbn = id;
        title = t;
        author = a;
        year = y;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}
