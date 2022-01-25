package org.altroyan.web.service.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql.scripts/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql.scripts/clean-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmployeeById() throws Exception {
        System.out.println("привет");
        this.mockMvc.perform(get("/api/v1/employee/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"Андрей\",\n" +
                        "    \"surname\": \"Кузнецов\",\n" +
                        "    \"phone\": \"+79995694585\",\n" +
                        "    \"department\": \"ACCOUNTING\"\n" +
                        "}"));
    }
    @Test
    void getEmployeeByIdWrongId() throws Exception {
        this.mockMvc.perform(get("/api/v1/employee/9"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("Сотрудник с таким id не найден")))
                .andExpect(content().string(containsString("404 NOT_FOUND")));
    }

    @Test
    void getAllEmployees() throws Exception {
        this.mockMvc.perform(get("/api/v1/employee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"Владимир\",\n" +
                        "        \"surname\": \"Костиков\",\n" +
                        "        \"phone\": \"+78005694585\",\n" +
                        "        \"department\": \"CUSTOMER\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"Андрей\",\n" +
                        "        \"surname\": \"Кузнецов\",\n" +
                        "        \"phone\": \"+79995694585\",\n" +
                        "        \"department\": \"ACCOUNTING\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 3,\n" +
                        "        \"name\": \"Елена\",\n" +
                        "        \"surname\": \"Ларина\",\n" +
                        "        \"phone\": \"+78082698956\",\n" +
                        "        \"department\": null\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 4,\n" +
                        "        \"name\": \"Ольга\",\n" +
                        "        \"surname\": \"Панфилова\",\n" +
                        "        \"phone\": \"+79996663322\",\n" +
                        "        \"department\": null\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 5,\n" +
                        "        \"name\": \"Олег\",\n" +
                        "        \"surname\": \"Титов\",\n" +
                        "        \"phone\": \"+75656665555\",\n" +
                        "        \"department\": \"CUSTOMER\"\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    void getAllEmployeesWithFilter() throws Exception {
        this.mockMvc.perform(get("/api/v1/employee").param("department", "CUSTOMER"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"Владимир\",\n" +
                        "        \"surname\": \"Костиков\",\n" +
                        "        \"phone\": \"+78005694585\",\n" +
                        "        \"department\": \"CUSTOMER\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 5,\n" +
                        "        \"name\": \"Олег\",\n" +
                        "        \"surname\": \"Титов\",\n" +
                        "        \"phone\": \"+75656665555\",\n" +
                        "        \"department\": \"CUSTOMER\"\n" +
                        "    }\n" +
                        "]"));
    }


    @Test
    void addNewEmployee() throws Exception {
        this.mockMvc.perform(post("/api/v1/employee").contentType("application/json").content("{\n" +
                        "  \"name\": \"Алексей\",\n" +
                        "  \"surname\": \"Богданов\",\n" +
                        "  \"phone\": \"+75555555555\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\n" +
                        "    \"id\": 6,\n" +
                        "    \"name\": \"Алексей\",\n" +
                        "    \"surname\": \"Богданов\",\n" +
                        "    \"phone\": \"+75555555555\",\n" +
                        "    \"department\": null\n" +
                        "}"));
    }

    @Test
    void updateEmployee() throws Exception {
        this.mockMvc.perform(put("/api/v1/employee").contentType("application/json").content("{\n" +
                        "  \"id\": \"3\",\n" +
                        "  \"phone\": \"+78888889988\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\n" +
                        "    \"id\": 3,\n" +
                        "    \"name\": \"Елена\",\n" +
                        "    \"surname\": \"Ларина\",\n" +
                        "    \"phone\": \"+78888889988\",\n" +
                        "    \"department\": null\n" +
                        "}"));
    }

    @Test
    void deleteEmployee() throws Exception {
        this.mockMvc.perform(delete("/api/v1/employee").contentType("application/json").content("{\n" +
                        "  \"id\": 3\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/v1/employee/3"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("Сотрудник с таким id не найден")))
                .andExpect(content().string(containsString("404 NOT_FOUND")));
    }

    @Test
    void assignToDepartment() throws Exception {
        this.mockMvc.perform(post("/api/v1/employee/department").contentType("application/json").content("{\n" +
                        "  \"employeeId\": 4,\n" +
                        "  \"department\": \"LEGAL\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\n" +
                        "    \"id\": 4,\n" +
                        "    \"name\": \"Ольга\",\n" +
                        "    \"surname\": \"Панфилова\",\n" +
                        "    \"phone\": \"+79996663322\",\n" +
                        "    \"department\": \"LEGAL\"\n" +
                        "}"));
    }

    @Test
    void removeFromDepartment() throws Exception {
        this.mockMvc.perform(delete("/api/v1/employee/department").contentType("application/json").content("{\n" +
                        "  \"id\": 1\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Владимир\",\n" +
                        "    \"surname\": \"Костиков\",\n" +
                        "    \"phone\": \"+78005694585\",\n" +
                        "    \"department\": null\n" +
                        "}"));
    }
}