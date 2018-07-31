package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;

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
}
