package org.example.slicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.slicetest.coffee.RestDocConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();


}
