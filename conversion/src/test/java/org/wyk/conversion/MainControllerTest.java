package org.wyk.conversion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class MainControllerTest {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public  void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Get  conversion list")
    @Test
    public void getList() throws Exception {
        String uri = "/conversions";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(mvcResult.getResponse().getStatus(), 200);

    }

    @DisplayName("Do conversion")
    @Test
    public void doConversions() throws Exception {
        MvcResult mvcResult = getMvcResult("/convert/meter-miles/52");
        assertEquals( 204, mvcResult.getResponse().getStatus());
        assertEquals("Conversion not supported", mvcResult.getResponse().getContentAsString());


        mvcResult = getMvcResult("/convert/celsius-fahrenheit/49");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("120.2", mvcResult.getResponse().getContentAsString());

        mvcResult = getMvcResult("/convert/inches-centimeters/10");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("25.4000508001016", mvcResult.getResponse().getContentAsString());

        mvcResult = getMvcResult("/convert/centimeters-inches/7");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("2.7559", mvcResult.getResponse().getContentAsString());


    }

    private MvcResult getMvcResult(String s) throws Exception {
        String uri = s;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();


        return mvcResult;
    }
}
