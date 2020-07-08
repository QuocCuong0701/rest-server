package com.example.restdemo2.endpoint;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import com.example.restdemo2.service.TaskService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskEndpoint.class)
class TaskEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    void testGetTaskByPersonId() throws Exception {
        List<Task> tasks = Arrays.asList(new Task(4L, "đi chợ", "Mua phải tiết kiệm", 5, new Person(5L)),
                new Task(81L	,"buy me a cup of coffee",	"some sugar with a lot of ice",	4,	new Person(5L)));

        BDDMockito.given(taskService.getAllTasks(5L)).willReturn(tasks);

        mockMvc.perform(get("/api_v1/task/5").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(4)))
        .andExpect(jsonPath("$[0].title", is("đi chợ")))
                .andExpect(jsonPath("$[1].title", is("buy me a cup of coffee")));
    }
}