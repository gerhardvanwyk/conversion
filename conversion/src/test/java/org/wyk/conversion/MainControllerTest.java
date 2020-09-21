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

    @DisplayName("ktom kilometer to mile")
    @Test
    public void getKilometerToMile() throws Exception {
        String uri = "/conversions/ktom/842952.2558";
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("523786.24818559614", result.getResponse().getContentAsString());

        uri = "/conversions/ktom/3";
        result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("1.8641135767120018", result.getResponse().getContentAsString());
    }

    @DisplayName("mtok miles to kilometers")
    @Test
    public void getMilesToKilometers() throws Exception {
        String uri = "/conversions/mtok/36517";
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("58768.414848", result.getResponse().getContentAsString());

        uri = "/conversions/mtok/3";
        result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("4.828032", result.getResponse().getContentAsString());
    }

    @DisplayName("ktoc kelvin to celcuis")
    @Test
    public void getKtoc() throws Exception {
        String uri = "/conversions/ktoc/58";
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("-215.14999999999998", result.getResponse().getContentAsString());

        uri = "/conversions/ktoc/354.147";
        result = mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andReturn();
        assertEquals("80.99700000000001", result.getResponse().getContentAsString());
    }

    @DisplayName("Get  conversion list")
    @Test
    public void getList() throws Exception {
        String uri = "/conversions";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(mvcResult.getResponse().getStatus(), 200);

    }

    @DisplayName("Do conversion")
    @Test
    public void doConversions() throws Exception {
        MvcResult mvcResult = getMvcResult("/conversions/convert/meter-miles/52");

        assertEquals( 204, mvcResult.getResponse().getStatus());
        assertEquals("Conversion not supported", mvcResult.getResponse().getContentAsString());


        mvcResult = getMvcResult("/conversions/convert/celsius-fahrenheit/49");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("120.2", mvcResult.getResponse().getContentAsString());

        mvcResult = getMvcResult("/conversions/convert/inch-centimeter/10");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("25.4000508001016", mvcResult.getResponse().getContentAsString());

        mvcResult = getMvcResult("/conversions/convert/centimeter-inch/7");
        assertEquals( 200, mvcResult.getResponse().getStatus());
        assertEquals("2.7559", mvcResult.getResponse().getContentAsString());


    }

    private MvcResult getMvcResult(String s) throws Exception {
        String uri = s;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.TEXT_PLAIN))
                .andReturn();


        return mvcResult;
    }
}
