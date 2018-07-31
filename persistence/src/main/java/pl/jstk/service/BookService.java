package pl.jstk.service;

import pl.jstk.to.BookTo;

import java.util.List;

public interface BookService {

    List<BookTo> findAllBooks();

    List<BookTo> findBooksByTitle(String title);

    List<BookTo> findBooksByAuthor(String author);

    BookTo saveBook(BookTo book);

    void deleteBook(Long id);

    BookTo findBookById(Long bookId);

    List<BookTo> findBooksByParams(BookTo findBook);
}
