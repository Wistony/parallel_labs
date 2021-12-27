package Lab2.Actor;

import Lab2.Book;
import Lab2.RequestResponse.Request;
import Lab2.RequestResponse.Response;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

import static Lab2.Commands.*;
import static Lab2.Commands.RETURN_BOOK;

public class Reader extends AbstractActor
{
    private Map<String, Book> booksForLibrary = new HashMap();
    private Map<String, Book> booksForHome = new HashMap<>();
    private ActorRef library;

    public Reader(ActorRef library) {
        this.library = library;
    }

    public static Props props(ActorRef library) {
        return Props.create(Reader.class, library);
    }

    @Override
    public Receive createReceive()
    {
        return ReceiveBuilder.create()
                .match(Request.class, this::handleBookRequest)
                .match(Response.class, this::handleBookResponse)
                .build();
    }

    private void handleBookRequest(Request request)
    {
        String command = request.getCommand();
        System.out.println("Reader(" + self().path().name() + "): tell visitor to " + command);
        if (RETURN_BOOK.equals(command))
        {
            returnBook(request.getBookName());
            return;
        }
        requestBook(command, request.getBookName());
    }

    private void handleBookResponse(Response response)
    {
        switch (response.getCommand())
        {
            case APPROVE_GIVING_BOOK_FOR_LIBRARY:
            {
                booksForLibrary.put(response.getBook().getName(), response.getBook());
                System.out.println("Reader(" + self().path().name() + "): took book for library");
                break;
            }
            case APPROVE_GIVING_BOOK_FOR_HOME:
            {
                booksForHome.put(response.getBook().getName(), response.getBook());
                System.out.println("Reader(" + self().path().name() + "): took book for home");
                break;
            }
            case REJECT_GIVING_BOOK:
            {
                System.out.println("Reader(" + self().path().name() + "): unable to take a book");
                break;
            }
            default:
            {
                System.out.println("Reader(" + self().path().name() + "): unknown command for visitor handler");
            }
        }
    }

    public void requestBook(String command, String bookName)
    {
        library.tell(new Request(command, bookName), getSelf());
    }

    public void returnBook(String bookName)
    {
        if (booksForLibrary.containsKey(bookName))
        {
            library.tell(new Response(RETURN_BOOK, booksForLibrary.get(bookName)), ActorRef.noSender());
            booksForLibrary.remove(bookName);
            System.out.println("Reader(" + self().path().name() + "): book returned from library");
        }
        else if (booksForHome.containsKey(bookName))
        {
            library.tell(new Response(RETURN_BOOK, booksForLibrary.get(bookName)), ActorRef.noSender());
            booksForHome.remove(bookName);
            System.out.println("Reader(" + self().path().name() + "): book returned from home");
        }
        else
        {
            System.out.println("Reader(" + self().path().name() + "): unable to return book that you don't have");
        }
    }
}
