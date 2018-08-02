package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
public class BookControllerTest {

    private MockMvc mockMvc;

    private List<BookTo> booklist;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(bookService);
        Mockito.reset(bookService);

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        ReflectionTestUtils.setField(bookController, "bookService", bookService);

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();

        booklist = new ArrayList<>();
        BookTo bookTo = new BookTo();
        bookTo.setId(1L);
        bookTo.setTitle("Test");
        booklist.add(bookTo);
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnBooksPage() throws Exception {
        //When
        Mockito.when(bookService.findAllBooks()).thenReturn(booklist);
        ResultActions resultActions = mockMvc.perform(get("/books").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS));
        verify(bookService, times(1)).findAllBooks();
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnBookDetails() throws Exception {
        //When
        Mockito.when(bookService.findBookById(1L)).thenReturn(booklist.get(0));
        ResultActions resultActions = mockMvc.perform(get("/books/book?id=1").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOK));
        verify(bookService, times(1)).findBookById(Mockito.anyLong());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldDeleteBook() throws Exception {
        //When
        Mockito.when(bookService.findBookById(1L)).thenReturn(booklist.get(0));
        ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS));
        verify(bookService, times(1)).deleteBook(Mockito.anyLong());
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnFindBooksView() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/books/find").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.FIND));
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnBooksByParams() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(post("/search").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS));
        verify(bookService, times(1)).findBooksByParams(Mockito.any(BookTo.class));
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnAddBookView() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/books/add").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ADD));
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldSaveAddedBook() throws Exception {
        //When
        Mockito.when(bookService.findAllBooks()).thenReturn(booklist);

        ResultActions resultActions = mockMvc.perform(post("/books/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "test2")
                .param("authors", "test2")
                .param("status", "FREE")
                .with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS));
        verify(bookService, times(1)).saveBook(Mockito.any(BookTo.class));
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnAccessView() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/403").with(testSecurityContext()));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ACCESS));
    }
}