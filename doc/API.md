~~# Cosmic Latte API

The Cosmic Latte API allows to manage Astronomical Bodies and users that create them. It uses the HTTP protocol and the JSON format.

The API is based on the CRUD pattern. It has the following operations :

_Users_

- Create a new user
- Get users
- Get a specific user
- Modify a user
- Delete yourself (in the sense of a CRUD operation, not literally)

_Astronomical Objects_

- Create an astronomical objects
- Get astronomical objects (with many filtering options)
- Modify an astronomical object
- Delete an astronomical object

## User endpoints

### Create a new user

- `POST /users`

Create a new user.

#### Request

JSON objects with the following properties:

- `username`: The username of the user
- `email`: The email address of the user
- `age`: The age of the user

#### Response

JSON objects with the following properties:

- `username`: The username of the user (unique)
- `email`: The email address of the user (unique)
- `age`: The age of the user

#### Status codes

- `201`: (Created) -- The user was successfully created
- `400`: (Bad Request) -- Request body invalid
- `409`: (Conflict) -- User already exists

### Get all users

- `GET /users`

Get all the stored users

#### Request

Contains the following query parameter:

- `username`: Username of the user

#### Response

JSON array, each has the following properties :

- `username`: The username of the user
- `email`: The email address of the user
- `age`: The age of the user

#### Status codes

- `200`: (OK) -- All the users have been retrieved

### Get one user

Get a specific user with their username

#### Request

Query parameter:

- `username`: Username of the user

#### Response

JSON object with the following properties :

- `username`: The username of the user
- `email`: The email address of the user
- `age`: The age of the user

#### Status Codes

- `200`: (OK) -- User has been retrieved
- `404`: (Not Found) -- User does not exist

### Update user

`PUT /user/{username}`

Update a user by its username, username can't be modified

#### Request

Request path contains the username of the user

Request body contains a JSON object with the following properties

- `email`: The email address of the user
- `age`: The age of the user

#### Response

Response body contains a JSON object with the following properties:

- `username`: The username of the user
- `email`: The email address of the user
- `age`: The age of the user

#### Status codes

- `200`: (OK) -- User successfully updated
- `400`: (Bad Request) - Request body invalid
- `404`: (Not Found) -- The specified user does not exist

### Delete a user

- `DELETE /users/{username}`

Delete a user by its username

#### Request

The request path contains the user's unique username

#### Response

Empty response body

#### Status codes

- `204`: (No Content) -- User successfully deleted.
- `404`: (Not Found) -- User does not exist.

## Astronomical Objects endpoints

### Create an Astronomical Objects

- `POST /object`

Create an astronomical object

#### Request

The request body must contain a JSON object with the following properties:

- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)$
- `diameter`: Diameter of the object (Km)
- `Mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)

#### Response

- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)$
- `diameter`: Diameter of the object (Km)
- `Mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)

#### Status codes

- `201`: (Created) -- The object was successfully created
- `400`: (Bad Request) -- Request body invalid
- `409`: (Conflict) -- Object already exists

### Get Astronomical Objects

`GET /`

#### Request
#### Response
#### Status codes

### Astronomical Objects

#### Request
#### Response
#### Status codes

### Astronomical Objects

#### Request
#### Response
#### Status codes


