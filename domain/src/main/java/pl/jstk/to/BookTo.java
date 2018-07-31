package pl.jstk.to;

import pl.jstk.enumerations.BookStatus;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookTo {
    private Long id;
    @NotNull
    @Size(min = 4)
    private String title;
    @NotNull
    @Size(min = 4)
    private String authors;
    @NotNull
    private BookStatus status;
    
    public BookTo() {
    }

    public BookTo(Long id, String title, String authors, BookStatus status) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.setStatus(status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}
}
