# Postman Collection Paths

Base URL: `http://localhost:8080/ws-rest-1.0-SNAPSHOT`

## Trains API

### 1. Get All Trains
- **Method:** GET
- **URL:** `{{Base URL}}/api/trains`
- **Description:** Returns a list of all trains.

### 2. Get Train by ID
- **Method:** GET
- **URL:** `{{Base URL}}/api/trains/{id}`
- **Description:** Returns a specific train by its ID.
- **Example:** `{{Base URL}}/api/trains/1`

### 3. Create a Train
- **Method:** POST
- **URL:** `{{Base URL}}/api/trains`
- **Body (JSON):**
  ```json
  {
    "nom": "Train D",
    "villeDepart": "Fes",
    "villeArrivee": "Oujda",
    "heureDepart": "09:30"
  }
  ```

### 4. Update a Train
- **Method:** PUT
- **URL:** `{{Base URL}}/api/trains/{id}`
- **Body (JSON):**
  ```json
  {
    "nom": "Train D Modified",
    "villeDepart": "Fes",
    "villeArrivee": "Oujda",
    "heureDepart": "10:00"
  }
  ```

### 5. Delete a Train
- **Method:** DELETE
- **URL:** `{{Base URL}}/api/trains/{id}`

## JSP Interface
- **Method:** GET
- **URL:** `{{Base URL}}/train-list`
- **Description:** Displays the list of trains using JSP.
