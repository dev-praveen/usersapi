package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private RestTemplate restTemplate = new RestTemplate();

    private CustomUserResponseDTO apply(UserDTO user) {

        CustomUserResponseDTO userResponseDTO = new CustomUserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setUserName(user.getUsername());
        userResponseDTO.setCompanyName(user.getCompany().getName());
        userResponseDTO.setGeo(user.getAddress().getGeo());

        return userResponseDTO;
    }

    @GetMapping("/users")
    public ResponseEntity<CustomUserResponse> getUsersInfo() {

        CustomUserResponse userResponse = new CustomUserResponse();
        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", UserDTO[].class);
        final UserDTO[] userDTOS = response.getBody();
        final List<CustomUserResponseDTO> customUserResponseDTOS = Arrays.stream(userDTOS).map(userDTO -> apply(userDTO)).collect(Collectors.toList());
        userResponse.setCustomUserResponseDTOSList(customUserResponseDTOS);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/todos")
    public ResponseEntity<CustomTodosUserResponse> getUsersTodosInfo() {

        CustomTodosUserResponse customTodosUserResponse = new CustomTodosUserResponse();
        List<CustomUsersTodoDTO> usersTodoDTOList = new ArrayList<>();

        UserDTO[] userDTOS = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", UserDTO[].class);
        UsersTodoDTO[] usersTodoDTOS = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos", UsersTodoDTO[].class);

        for (UserDTO userDTO : userDTOS) {

            CustomUsersTodoDTO usersTodoDTO = new CustomUsersTodoDTO();
            usersTodoDTO.setUserName(userDTO.getUsername());
            usersTodoDTO.setCompanyName(userDTO.getCompany().getName());

            Integer completedTasks = 0;
            Integer notCompletedTasks = 0;

            for (UsersTodoDTO todoDTO : usersTodoDTOS) {

                if (userDTO.getId() == todoDTO.getUserId()) {

                    Boolean isCompleted = todoDTO.getCompleted();
                    if (Boolean.TRUE.equals(isCompleted)) {
                        completedTasks++;
                    }
                    if (Boolean.FALSE.equals(isCompleted)) {
                        notCompletedTasks++;
                    }
                    continue;
                }
            }

            usersTodoDTO.setCompletedTasks(completedTasks);
            usersTodoDTO.setNotCompletedTasks(notCompletedTasks);

            usersTodoDTOList.add(usersTodoDTO);
        }

        Collections.sort(usersTodoDTOList);
        customTodosUserResponse.setCustomUsersTodoDTOS(usersTodoDTOList);
        return ResponseEntity.ok(customTodosUserResponse);
    }
}