# customusersapi

https://jsonplaceholder.typicode.com/users -- > users api
https://jsonplaceholder.typicode.com/todos -- > todos api

Consume these two external apis in spring boot application and create single api with below requirement.

Each user is mapped to different tasks and we should get every user with completed tasks (if completed field is true) otherwise it is not completed task.

id in users api is same as userid in todos api.

Final api should be like below

        {
            "userName": "Delphine",
            "companyName": "Yost and Sons",
            "completedTasks": 8,
            "notCompletedTasks": 12
        }
