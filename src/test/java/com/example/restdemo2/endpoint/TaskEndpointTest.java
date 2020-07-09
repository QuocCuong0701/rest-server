package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import com.example.restdemo2.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskEndpoint.class)
class TaskEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    void testFunction_GetAllTaskByPersonId() throws Exception {
        List<Task> tasks = Arrays.asList(new Task(4L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L)),
                new Task(81L	,"buy me a cup of coffee",	"some sugar with a lot of ice",	4,	new Person(5L)));

        given(taskService.getAllTasks(5L)).willReturn(tasks);

        mockMvc.perform(get("/api_v1/task/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].title", is("đi chợ")))
                .andExpect(jsonPath("$[1].title", is("buy me a cup of coffee")));
    }

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testFunction_SaveTask() throws Exception {
        Task task = new Task(4L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L));

        given(taskService.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api_v1/task/save").content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title", is("đi chợ")))
        ;
    }
}