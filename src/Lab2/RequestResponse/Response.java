package Lab2.RequestResponse;

import Lab2.Book;

public class Response
{
    private String command;
    private Book book;

    public Response(String command, Book book)
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
