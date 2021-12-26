package Lab2;

public class LibraryBook extends Book
{

    private boolean available;
    private boolean availableForHome;
    private boolean availableForLibrary;

    public LibraryBook(String name, boolean available, boolean availableForHome, boolean availableForLibrary)
    {
        super(name);
        this.available = available;
        this.availableForHome = availableForHome;
        this.availableForLibrary = availableForLibrary;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailableForHome() {
        return availableForHome;
    }

    public boolean isAvailableForLibrary() {
        return availableForLibrary;
    }
}
