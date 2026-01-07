# Cosmic Latte API

The Cosmic Latte API allows to manage Astronomical Bodies and users that create them. It uses the HTTP protocol and the JSON format.

The API is based on the CRUD pattern. It has the following operations :

_Users_

- Create a new user
- Get users
- Get a specific user
- Modify a user
- Delete yourself (in the sense of a CRUD operation, not literally)

_Astronomical Objects_

- Create an astronomical object
- Get astronomical objects (with many filtering options)
- Modify an astronomical object
- Delete an astronomical object

## User endpoints

### Create a new user

- `POST /user`

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

- `GET /user`

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

`GET /user/{username}`

Get a specific user with their username

#### Request

Path parameter:

- `username`: Username of the user

#### Response

JSON object with the following properties :

- `username`: The username of the user
- `email`: The email address of the user
- `age`: The age of the user

#### Status codes

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
- `400`: (Bad Request) -- Request body invalid
- `404`: (Not Found) -- The specified user does not exist

### Delete a user

- `DELETE /user/{username}`

Delete a user by its username

#### Request

The request path contains the user's unique username

#### Response

Empty response body

#### Status codes

- `204`: (No Content) -- User successfully deleted.
- `404`: (Not Found) -- User does not exist.

## Astronomical Objects endpoints

### Create an Astronomical Object

- `POST /object`

Create an astronomical object

#### Request

The request body must contain a JSON object with the following properties:

- `name`: Name of the object (Unique)
- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Response

- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Status codes

- `201`: (Created) -- The object was successfully created
- `400`: (Bad Request) -- Request body invalid
- `404`: (Not found) -- The specified username does not exist
- `409`: (Conflict) -- Object already exists

### Get the stored astronomical object with filters

`GET /object?{operation}={value}`

#### Request

The request query can contain the following parameters for the `operation` key :

- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km) (equals)
  - `diameter_ge` : diameter greater or equal than
  - `diameter_le` : diameter less or equal than
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass) (equals)
  - `mass_ge` : mass greater or equal than
  - `mass_le` : mass less or equal than
- `escape_velocity`: Minimum speed required to leave orbit (Km/s) (equals)
  - `escape_velocity_ge` : escape_velocity greater or equal than
  - `escape_velocity_le` : escape_velocity less or equal than
- `surface_temperature`: Mean temperature (K) (equals)
  - `surface_temperature_ge` : surface_temperature greater or equal than
  - `surface_temperature_le` : surface_temperature less or equal than
- `created_by`: The username of the user that created the object

#### Response

A JSON array with each JSON object having the following properties:

- `id` : The id of the object
- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Status codes

- `200`: (OK) -- Objects successfully retrieved

### Get a single object based on the id

`GET /object/{id}`

#### Request

The URL must contain the id of an existing object

- `id` : The id of the object

#### Response

A JSON file with the following properties:

- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Status codes

- `200`: (OK) -- Object successfully retrieved

### Update an Astronomical Object

`PUT /object/{name}`

#### Request

The request path must contain the name of the object

The name can not be modified as it is the unique identifier of the object

The request body contains a JSON object with the following properties:

- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Response

Body contains a JSON object with the following properties

- `name`: Name of the object
- `type`: The type of the object (star, planet, satellite, black_hole, ...)
- `diameter`: Diameter of the object (Km)
- `mass`: The mass of the object ($M_{\oplus}$ - Earth Mass)
- `escape_velocity`: Minimum speed required to leave orbit (Km/s)
- `surface_temperature`: Mean temperature (K)
- `created_by`: The username of the user that created the object

#### Status codes

- `200`: (OK) -- The object has been successfully updated.
- `400`: (Bad Request) -- The request body is invalid.
- `404`: (Not Found) -- The object does not exist.

### Delete Astronomical Objects

`DELETE /object/{name}`

Delete an astronomical object by its name

#### Request

The request path contains the object's name

#### Response

Empty response body

#### Status codes

- `204`: (No Content) -- The object has been successfully deleted
- `404`: (Not Found) -- The specified Astronomical Object does not exist
