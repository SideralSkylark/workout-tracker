# Workout tracker spring boot app
This app is designed to help track progress in the gym, for the time beign it will simply allow you to devise a split and register the work done each session. This project comes from a need of mine to keep track of such metrics in a way of avoiding the use of pen and paper, for it becomes far too taxing to keep track of such data using it.

I have yet to write unit tests for this project, but i have to drop it for the time being, for i have spent too much time already in this one project.

## Functional requirements
- **Create a split**;
- **Define workouts for the split**;
- **Log your workouts**;
- **Track your progress**;

## Bugs and problems
- The user can't login if his token from a previous login is still in memory;
- The login method returns the user credentials as a response in the splits key of the json;

## Instructions
1. To run the app i recoment using docker, simply installing docker, node and angular cli should be enough.
2. run: 
    ```npm install
    ```
   on the /frontend directory.
3. run:
    ```
    npm install -g @angular/cli
    ```
4. on the /frontend directory run:
    ```
    ng build
    ```
    To build the angular app onto /dist.
5. create a .env file at the root of the project
    ```
    DB_URL=jdbc:postgresql://postgres-db:5432/workout_db
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password
    ```
6. run the compose file from the root directory with the command:
    ```
    docker compose build
    ```
7. finaly run:
    ```
    docker compose up -d
    ```

The app should be avaliable on:
```
http://localhost
```

## Tecnologies used
- Java 23
- spring boot
- angular
- bootstrap
- postgre db
- nginx
- docker