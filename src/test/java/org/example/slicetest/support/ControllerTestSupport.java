package org.example.slicetest.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.slicetest.coffee.controller.CoffeeController;
import org.example.slicetest.coffee.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = {CoffeeController.class})
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();


    @MockitoBean
    protected CoffeeService coffeeService;


}
