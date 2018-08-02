package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HomeControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/login");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testHomePage() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/"));
        //Then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("welcome"))
                     .andDo(print())
                     .andExpect(model().attribute(ModelConstants.MESSAGE, HomeController.WELCOME))
                     .andExpect(content().string(containsString("")));
    }

    @Test
    public void testLoginPage() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(get("/login"));
        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.LOGIN));
    }

}
