package Lab2;

public class BookResponse
{
    private String command;
    private Book book;

    public BookResponse(String command, Book book)
    {
        this.command = command;
        this.book = book;
    }

    public String getCommand() {
        return command;
    }

    public Book getBook() {
        return book;
    }
}
