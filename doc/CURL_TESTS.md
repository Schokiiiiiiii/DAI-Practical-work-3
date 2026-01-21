# Cosmic Latte API - Curl Test Commands

This document contains all the curl commands to test the Cosmic Latte API.

## Configuration

```bash
# Base URL (adjust according to your environment)
BASE_URL="http://localhost:8080"
```

---

## User Endpoints

### Create a new user

```bash
# Create a user successfully (201 Created)
curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "astronomer1",
    "email": "astronomer1@space.com",
    "date_of_birth": "1990-05-15",
    "diploma": "PhD in Astrophysics",
    "biography": "Passionate about stars and galaxies"
  }'

# Create another user
curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "stargazer",
    "email": "stargazer@cosmos.org",
    "date_of_birth": "1985-12-01",
    "diploma": "MSc in Astronomy",
    "biography": "Amateur astronomer since childhood"
  }'

# Create user with duplicate username (409 Conflict)
curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "astronomer1",
    "email": "different@email.com",
    "date_of_birth": "2000-01-01",
    "diploma": "BSc",
    "biography": "Test"
  }'

# Create user with invalid body (400 Bad Request)
curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{
    "invalid": "data"
  }'
```

### Get all users

```bash
# Get all users (200 OK)
curl -i -X GET "${BASE_URL}/user"

# Get users filtered by username
curl -i -X GET "${BASE_URL}/user?username=astronomer1"
```

### Get a specific user

```bash
# Get existing user (200 OK)
curl -i -X GET "${BASE_URL}/user/astronomer1"

# Get non-existing user (404 Not Found)
curl -i -X GET "${BASE_URL}/user/nonexistent"
```

### Update a user

```bash
# Update user successfully (200 OK)
curl -i -X PUT "${BASE_URL}/user/astronomer1" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "astronomer1",
    "email": "updated@space.com",
    "date_of_birth": "1990-05-15",
    "diploma": "PhD in Astrophysics - Updated",
    "biography": "Updated biography with new discoveries"
  }'

# Update non-existing user (404 Not Found)
curl -i -X PUT "${BASE_URL}/user/nonexistent" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nonexistent",
    "email": "test@test.com",
    "date_of_birth": "2000-01-01",
    "diploma": "Test",
    "biography": "Test"
  }'

# Update with invalid body (400 Bad Request)
curl -i -X PUT "${BASE_URL}/user/astronomer1" \
  -H "Content-Type: application/json" \
  -d '{
    "invalid": "data"
  }'
```

### Delete a user

```bash
# Delete existing user (204 No Content)
curl -i -X DELETE "${BASE_URL}/user/stargazer"

# Delete non-existing user (404 Not Found)
curl -i -X DELETE "${BASE_URL}/user/nonexistent"
```

---

## Astronomical Object Endpoints

### Create an astronomical object

```bash
# Create Earth (201 Created)
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Earth",
    "type": "planet",
    "diameter": 12742,
    "mass": 1.0,
    "escape_velocity": 11.2,
    "surface_temperature": 288,
    "created_by": "astronomer1"
  }'

# Create the Sun
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sun",
    "type": "star",
    "diameter": 1392700,
    "mass": 332946,
    "escape_velocity": 617.7,
    "surface_temperature": 5778,
    "created_by": "astronomer1"
  }'

# Create Mars
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mars",
    "type": "planet",
    "diameter": 6779,
    "mass": 0.107,
    "escape_velocity": 5.0,
    "surface_temperature": 210,
    "created_by": "astronomer1"
  }'

# Create the Moon
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Moon",
    "type": "satellite",
    "diameter": 3474,
    "mass": 0.0123,
    "escape_velocity": 2.4,
    "surface_temperature": 250,
    "created_by": "astronomer1"
  }'

# Create Sagittarius A* (black hole)
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sagittarius A*",
    "type": "black_hole",
    "diameter": 44000000,
    "mass": 1300000000000,
    "escape_velocity": 299792,
    "surface_temperature": 1,
    "created_by": "astronomer1"
  }'

# Create object with duplicate name (409 Conflict)
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Earth",
    "type": "planet",
    "diameter": 12742,
    "mass": 1.0,
    "escape_velocity": 11.2,
    "surface_temperature": 288,
    "created_by": "astronomer1"
  }'

# Create object with non-existing user (404 Not Found)
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jupiter",
    "type": "planet",
    "diameter": 139820,
    "mass": 317.8,
    "escape_velocity": 59.5,
    "surface_temperature": 165,
    "created_by": "nonexistent_user"
  }'

# Create object with invalid body (400 Bad Request)
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{
    "invalid": "data"
  }'
```

### Get all astronomical objects

```bash
# Get all objects (200 OK)
curl -i -X GET "${BASE_URL}/object"

# Filter by type
curl -i -X GET "${BASE_URL}/object?type=planet"
curl -i -X GET "${BASE_URL}/object?type=star"
curl -i -X GET "${BASE_URL}/object?type=satellite"
curl -i -X GET "${BASE_URL}/object?type=black_hole"

# Filter by diameter range
curl -i -X GET "${BASE_URL}/object?diameter_ge=10000"
curl -i -X GET "${BASE_URL}/object?diameter_le=10000"
curl -i -X GET "${BASE_URL}/object?diameter_ge=5000&diameter_le=15000"

# Filter by mass range
curl -i -X GET "${BASE_URL}/object?mass_ge=1.0"
curl -i -X GET "${BASE_URL}/object?mass_le=1.0"
curl -i -X GET "${BASE_URL}/object?mass_ge=0.1&mass_le=10"

# Filter by escape velocity range
curl -i -X GET "${BASE_URL}/object?escape_velocity_ge=10"
curl -i -X GET "${BASE_URL}/object?escape_velocity_le=20"

# Filter by surface temperature range
curl -i -X GET "${BASE_URL}/object?surface_temperature_ge=200"
curl -i -X GET "${BASE_URL}/object?surface_temperature_le=300"

# Filter by creator
curl -i -X GET "${BASE_URL}/object?created_by=astronomer1"

# Combine multiple filters
curl -i -X GET "${BASE_URL}/object?type=planet&diameter_ge=5000&mass_le=2"
```

### Get a specific astronomical object

```bash
# Get object by ID (200 OK) - replace {id} with actual ID
curl -i -X GET "${BASE_URL}/object/1"

# Get non-existing object (404 Not Found)
curl -i -X GET "${BASE_URL}/object/99999"
```

### Update an astronomical object

```bash
# Update object successfully (200 OK) - replace {id} with actual ID
curl -i -X PUT "${BASE_URL}/object/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Earth",
    "type": "planet",
    "diameter": 12742,
    "mass": 1.0,
    "escape_velocity": 11.186,
    "surface_temperature": 287,
    "created_by": "astronomer1"
  }'

# Update non-existing object (404 Not Found)
curl -i -X PUT "${BASE_URL}/object/99999" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Unknown",
    "type": "planet",
    "diameter": 12742,
    "mass": 1.0,
    "escape_velocity": 11.2,
    "surface_temperature": 288,
    "created_by": "astronomer1"
  }'

# Update with invalid body (400 Bad Request)
curl -i -X PUT "${BASE_URL}/object/1" \
  -H "Content-Type: application/json" \
  -d '{
    "invalid": "data"
  }'
```

### Delete an astronomical object

```bash
# Delete existing object (204 No Content) - replace {id} with actual ID
curl -i -X DELETE "${BASE_URL}/object/1"

# Delete non-existing object (404 Not Found)
curl -i -X DELETE "${BASE_URL}/object/99999"
```

---

## Complete Test Scenario

Run these commands in order to test a complete workflow:

```bash
# 1. Create users
curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{"username": "alice", "email": "alice@example.com", "date_of_birth": "1995-03-20", "diploma": "PhD Astronomy", "biography": "Galaxy researcher"}'

curl -i -X POST "${BASE_URL}/user" \
  -H "Content-Type: application/json" \
  -d '{"username": "bob", "email": "bob@example.com", "date_of_birth": "1988-07-10", "diploma": "MSc Physics", "biography": "Planet hunter"}'

# 2. List all users
curl -i -X GET "${BASE_URL}/user"

# 3. Create astronomical objects
curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{"name": "Venus", "type": "planet", "diameter": 12104, "mass": 0.815, "escape_velocity": 10.4, "surface_temperature": 737, "created_by": "alice"}'

curl -i -X POST "${BASE_URL}/object" \
  -H "Content-Type: application/json" \
  -d '{"name": "Mercury", "type": "planet", "diameter": 4879, "mass": 0.055, "escape_velocity": 4.3, "surface_temperature": 440, "created_by": "bob"}'

# 4. List all objects
curl -i -X GET "${BASE_URL}/object"

# 5. Filter objects by creator
curl -i -X GET "${BASE_URL}/object?created_by=alice"

# 6. Update an object (use the ID returned from creation)
curl -i -X PUT "${BASE_URL}/object/1" \
  -H "Content-Type: application/json" \
  -d '{"name": "Venus", "type": "planet", "diameter": 12104, "mass": 0.815, "escape_velocity": 10.36, "surface_temperature": 737, "created_by": "alice"}'

# 7. Delete an object
curl -i -X DELETE "${BASE_URL}/object/2"

# 8. Verify deletion
curl -i -X GET "${BASE_URL}/object"

# 9. Delete a user
curl -i -X DELETE "${BASE_URL}/user/bob"

# 10. Verify user deletion
curl -i -X GET "${BASE_URL}/user"
```

---

## Print response header

Add `-i` flag to include response headers in output:

```bash
curl -i -X GET "${BASE_URL}/user"
```

## Pretty Print JSON

Pipe output through `jq` for formatted JSON:

```bash
curl -s -X GET "${BASE_URL}/user" | jq .
curl -s -X GET "${BASE_URL}/object" | jq .
```

_This file was written with the help of AI_