package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.jstk.service.BookService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@WithMockUser(username="admin", roles={"ADMIN"})
public class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookController()).build();
        MockitoAnnotations.initMocks(bookService);
        Mockito.reset(bookService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ReflectionTestUtils.setField(bookController, "bookService", bookService);
    }

    @Test
    public void testBooksPage() throws Exception {
        //When
        Mockito.when(bookService.findAllBooks()).thenReturn(new ArrayList<>());
        ResultActions resultActions = mockMvc.perform(get("/books"));
        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("books"));
    }
}
