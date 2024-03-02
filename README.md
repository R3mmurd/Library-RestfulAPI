# Library REST API

## Setup

- Clone this repository:
  ```bash
  git clone git@github.com:R3mmurd/Library-RestfulAPI.git
  ```

- Open a terminal, go to the project root folder and run the docker file to start-up PostgreSQL server and Mailhug:
  ```bash
  docker-compose -f docker-compose.yaml up
  ```
  
- Open the project with IntelliJ IDEA, go to settings, install the plugin Lombok and set it as the compile Annotation Processor for main and test

- Run the main application

- Open Postman and import `postman_collection.json` and `postman_environment.json` to test the endpoints

- You can see the documentation at `http://localhost:8085/api/docs-ui/swagger-ui/index.html`