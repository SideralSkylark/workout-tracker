# Workout Tracker Spring Boot App

This app is designed to help users track their progress in the gym. Currently, it allows you to devise a workout split and register the work completed during each session. This project stems from my personal need for a digital solution to track such metrics, as using pen and paper proved too cumbersome and impractical.

While I have yet to write unit tests for this project, I must set it aside for now due to the significant time already invested.

---

## Functional Requirements
- **Create a split:** Define workout splits according to your needs.
- **Define workouts for the split:** Assign exercises to each split.
- **Log your workouts:** Record completed sets and reps for each session.
- **Track your progress:** Monitor your performance over time.

---

## Bugs and Issues
- Users cannot log in if a token from a previous login is still in memory.
- The login method erroneously returns user credentials in the `splits` key of the JSON response.

---

## Instructions

### Prerequisites
Ensure you have the following installed:
- Docker
- Node.js
- Angular CLI

### Steps
1. Navigate to the `/frontend` directory and run:
    ```bash
    npm install
    ```

2. Globally install the Angular CLI:
    ```bash
    npm install -g @angular/cli
    ```

3. In the `/frontend` directory, build the Angular app by running:
    ```bash
    ng build
    ```
    This will compile the Angular app into the `/dist` directory.

4. Create a `.env` file at the root of the project with the following content:
    ```env
    DB_URL=jdbc:postgresql://postgres-db:5432/workout_db
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password
    ```

5. From the root directory, build the Docker containers by running:
    ```bash
    docker compose build
    ```

6. Start the app in detached mode:
    ```bash
    docker compose up -d
    ```

7. The app should now be available at:
    ```
    http://localhost
    ```

---

## Technologies Used
- **Java 23**
- **Spring Boot**
- **Angular**
- **Bootstrap**
- **PostgreSQL**
- **NGINX**
- **Docker**

