package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import javax.validation.Valid;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public String getAllBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/book")
    public String getBookById(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/delete")
    public String deleteBookById(@RequestParam("id") Long id, Model model) {
        bookService.deleteBook(id);
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/find")
    public String findBooks(Model model) {
        model.addAttribute("findBook", new BookTo());
        return ViewNames.FIND;
    }

    @PostMapping(value = "/search")
    public String findBooksByParams(@ModelAttribute("findBook") BookTo findBook, Model model) {
        model.addAttribute("bookList", bookService.findBooksByParams(findBook));
        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/add")
    public String addBook(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD;
    }

    @PostMapping(value = "/greeting")
    public String saveBook(@ModelAttribute("newBook") @Valid BookTo newBook, Model model) {
        bookService.saveBook(newBook);
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }
}
