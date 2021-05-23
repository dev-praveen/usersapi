package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

    @GetMapping("/users")
    public ResponseEntity<CustomUserResponse> getUsersInfo() {

        CustomUserResponse userResponse = new CustomUserResponse();
        List<CustomUserResponseDTO> customUserResponseDTOS = new ArrayList<>();
        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", UserDTO[].class);

        boolean isError = response.getStatusCode().isError();

        if (!isError) {
            final UserDTO[] userDTOS = response.getBody();
            if (null != userDTOS && userDTOS.length > 0) {
                customUserResponseDTOS = Arrays.stream(userDTOS).map(userDTO -> apply(userDTO)).collect(Collectors.toList());
            }
        }
        userResponse.setCustomUserResponseDTOSList(customUserResponseDTOS);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/todos")
    public ResponseEntity<CustomTodosUserResponse> getUsersTodosInfo() {

        CustomTodosUserResponse customTodosUserResponse = new CustomTodosUserResponse();
        List<CustomUsersTodoDTO> usersTodoDTOList = new ArrayList<>();

        UserDTO[] userDTOS = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users", UserDTO[].class);
        UsersTodoDTO[] usersTodoDTOS = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos", UsersTodoDTO[].class);

        if (null != userDTOS && userDTOS.length > 0) {

            for (UserDTO userDTO : userDTOS) {

                Integer completedTasks = 0;
                Integer notCompletedTasks = 0;

                CustomUsersTodoDTO usersTodoDTO = new CustomUsersTodoDTO();
                usersTodoDTO.setUserName(userDTO.getUsername());
                usersTodoDTO.setCompanyName(userDTO.getCompany().getName());

                if (null != usersTodoDTOS && usersTodoDTOS.length > 0) {

                    for (UsersTodoDTO todoDTO : usersTodoDTOS) {

                        if (userDTO.getId() == todoDTO.getUserId()) {

                            Boolean isCompleted = todoDTO.getCompleted();
                            if (Boolean.TRUE.equals(isCompleted)) {
                                completedTasks++;
                            } else {
                                notCompletedTasks++;
                            }
                        }
                    }
                }

                usersTodoDTO.setCompletedTasks(completedTasks);
                usersTodoDTO.setNotCompletedTasks(notCompletedTasks);

                usersTodoDTOList.add(usersTodoDTO);
            }
        }

        Collections.sort(usersTodoDTOList);
        customTodosUserResponse.setCustomUsersTodoDTOS(usersTodoDTOList);
        return ResponseEntity.ok(customTodosUserResponse);
    }

    private CustomUserResponseDTO apply(@NonNull UserDTO user) {

        CustomUserResponseDTO userResponseDTO = new CustomUserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setUserName(user.getUsername());
        userResponseDTO.setCompanyName(user.getCompany().getName());
        userResponseDTO.setGeo(user.getAddress().getGeo());

        return userResponseDTO;
    }
}