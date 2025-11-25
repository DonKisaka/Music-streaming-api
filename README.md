Music Streaming API
A RESTful API for music streaming built with Spring Boot, featuring user authentication, song management, album management, artist management, and playlist creation. The API supports JWT-based authentication and provides comprehensive endpoints for managing a music streaming service.

🚀 Features
User Authentication: Secure JWT-based authentication system with signup and login
Song Management: CRUD operations for songs with search capabilities
Album Management: Create and manage music albums
Artist Management: Manage artist information
Playlist Management: User-specific playlist creation and management
Search Functionality: Search songs by title, album, or artist
Pagination Support: Efficient data retrieval with Spring Data pagination
API Documentation: Auto-generated Swagger/OpenAPI documentation
Docker Support: Containerized deployment with Docker and Docker Compose
🛠️ Tech Stack
Java 25
Spring Boot 3.5.6
Spring Security - JWT authentication
Spring Data JPA - Database operations
PostgreSQL - Relational database
Lombok - Boilerplate code reduction
SpringDoc OpenAPI - API documentation
Docker - Containerization
📋 Prerequisites
Java 25 or higher
Maven 3.6+
PostgreSQL 16+ (or use Docker Compose)
Docker & Docker Compose (optional, for containerized deployment)
🔧 Installation & Setup
Option 1: Local Development
Clone the repository

git clone <repository-url>
cd musicstreaming/musicstreaming
Set up PostgreSQL Database

Create a PostgreSQL database:

CREATE DATABASE music_streaming;
CREATE USER musicuser WITH PASSWORD 'musicpass';
GRANT ALL PRIVILEGES ON DATABASE music_streaming TO musicuser;
Configure Application Properties

Update src/main/resources/application.properties if needed:

spring.datasource.url=jdbc:postgresql://localhost:5432/music_streaming
spring.datasource.username=musicuser
spring.datasource.password=musicpass
server.port=8084
Build the project

./mvnw clean install
Run the application

./mvnw spring-boot:run
The application will start on http://localhost:8084

Option 2: Docker Deployment
Navigate to the project directory

cd musicstreaming/musicstreaming
Start services with Docker Compose

docker-compose up -d
This will start:

PostgreSQL database on port 5432
Music Streaming application on port 8080
View logs

docker-compose logs -f
Stop services

docker-compose down
📚 API Documentation
Once the application is running, access the interactive API documentation:

Swagger UI: http://localhost:8084/swagger-ui.html
OpenAPI JSON: http://localhost:8084/v3/api-docs
🔐 Authentication
The API uses JWT (JSON Web Token) for authentication. Most endpoints require a valid JWT token in the Authorization header.

Sign Up
POST /api/v1/auth/signup
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123"
}
Login
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
Response:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "johndoe"
}
Using the Token
Include the token in subsequent requests:

Authorization: Bearer <your-token-here>
📖 API Endpoints
Authentication Endpoints
POST /api/v1/auth/signup - Register a new user
POST /api/v1/auth/login - Authenticate and get JWT token
Song Endpoints
GET /api/v1/songs - Get all songs (paginated)
GET /api/v1/songs/{id} - Get song by ID
POST /api/v1/songs - Create a new song (requires authentication)
PUT /api/v1/songs/{id} - Update a song (requires authentication)
DELETE /api/v1/songs/{id} - Delete a song (requires authentication)
GET /api/v1/songs/search/by-title?title={title} - Search songs by title
GET /api/v1/songs/search/by-album?albumId={albumId} - Get songs by album
GET /api/v1/songs/search/by-artist?artistId={artistId} - Get songs by artist
Album Endpoints
GET /api/v1/albums - Get all albums (paginated)
GET /api/v1/albums/{id} - Get album by ID
POST /api/v1/albums - Create a new album (requires authentication)
PUT /api/v1/albums/{id} - Update an album (requires authentication)
DELETE /api/v1/albums/{id} - Delete an album (requires authentication)
Artist Endpoints
GET /api/v1/artists - Get all artists (paginated)
GET /api/v1/artists/{id} - Get artist by ID
POST /api/v1/artists - Create a new artist (requires authentication)
PUT /api/v1/artists/{id} - Update an artist (requires authentication)
DELETE /api/v1/artists/{id} - Delete an artist (requires authentication)
Playlist Endpoints
GET /api/v1/playlists - Get all playlists (paginated, user-specific)
GET /api/v1/playlists/{id} - Get playlist by ID (requires authentication)
POST /api/v1/playlists - Create a new playlist (requires authentication)
PUT /api/v1/playlists/{id} - Update a playlist (requires authentication)
DELETE /api/v1/playlists/{id} - Delete a playlist (requires authentication)
🏗️ Project Structure
musicstreaming/
├── src/
│   ├── main/
│   │   ├── java/com/example/musicstreaming/
│   │   │   ├── config/          # Security and application configuration
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── Dto/            # Data Transfer Objects
│   │   │   ├── mapper/         # Entity to DTO mappers
│   │   │   ├── model/          # JPA entity models
│   │   │   ├── repository/     # Spring Data JPA repositories
│   │   │   ├── service/        # Business logic services
│   │   │   └── MusicstreamingApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                   # Test files
├── docker-compose.yml          # Docker Compose configuration
├── Dockerfile                  # Docker image definition
└── pom.xml                     # Maven dependencies
