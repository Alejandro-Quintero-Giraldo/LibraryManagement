package co.com.library.management.controller;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.model.Resources;
import co.com.library.management.service.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceControllerTest {

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findById() throws  Exception{

        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("1");
        resourceDTO.setName("La evolución de la vida en la tierra");


        var resourceDTO1 = new ResourcesDTO();
        resourceDTO1.setId("1");
        resourceDTO1.setName("La evolución de la vida en la tierra");


        doReturn(resourceDTO).when(resourceService).findById("1");

        mockMvc.perform(get("/management/getResourceId/"+resourceDTO.getId())
                .content(resourceDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is("1")));

    }

    @Test
    void findAll() throws  Exception{

        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("1");
        resourceDTO.setName("La evolución de la vida en la tierra");


        var resourceDTO1 = new ResourcesDTO();
        resourceDTO1.setId("2");
        resourceDTO1.setName("La evolución del ser humano");

        var list = new ArrayList<ResourcesDTO>();
        list.add(resourceDTO);
        list.add(resourceDTO1);

        doReturn(list).when(resourceService).findAll();

        mockMvc.perform(get("/management/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id",is("1")))
                .andExpect(jsonPath("$[1].id",is("2")))
                .andReturn();

    }

    @Test
    void createResource() throws  Exception {

        var resourceDTO1 = new ResourcesDTO();
        resourceDTO1.setId("1");
        resourceDTO1.setName("La evolución de la vida en la tierra");

        doReturn(resourceDTO1).when(resourceService).createResource(any());

        mockMvc.perform(post("/management/createResource")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(resourceDTO1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Test
    void findByType() throws  Exception {
        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("1");
        resourceDTO.setType("Book");


        var resourceDTO1 = new ResourcesDTO();
        resourceDTO1.setId("2");
        resourceDTO1.setType("Book");

        var list = new ArrayList<ResourcesDTO>();
        list.add(resourceDTO);
        list.add(resourceDTO1);

        doReturn(list).when(resourceService).findByType("Book");

        mockMvc.perform(get("/management/getResourcesType/Book")
                        .content("Book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id",is("1")))
                .andExpect(jsonPath("$[0].type",is("Book")))
                .andExpect(jsonPath("$[1].id",is("2")))
                .andExpect(jsonPath("$[1].type",is("Book")))
                .andReturn();

    }


    @Test
    void findByTheme() throws Exception{
        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("1");
        resourceDTO.setTheme("Science");


        var resourceDTO1 = new ResourcesDTO();
        resourceDTO1.setId("2");
        resourceDTO1.setTheme("Science");

        var list = new ArrayList<ResourcesDTO>();
        list.add(resourceDTO);
        list.add(resourceDTO1);

        doReturn(list).when(resourceService).findByTheme("Science");

        mockMvc.perform(get("/management/getResourcesTheme/Science")
                        .content("Science"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id",is("1")))
                .andExpect(jsonPath("$[0].theme",is("Science")))
                .andExpect(jsonPath("$[1].id",is("2")))
                .andExpect(jsonPath("$[1].theme",is("Science")))
                .andReturn();

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}