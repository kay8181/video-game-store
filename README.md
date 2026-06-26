Video Game Store — E‑Commerce Web Application
A full‑stack e‑commerce platform built with Spring Boot, Spring Security (JWT), Spring Data JPA, and a static HTML/CSS/JS frontend.
This repository focuses on a Video Game Store implementation.

Project Overview
The Video Game Store application allows users to:

Browse video games, accessories, and hardware

View product details and images

Register and log in using secure JWT authentication

Manage a shopping cart

Interact with a clean, responsive frontend UI

The backend exposes a REST API consumed by the static frontend located in src/main/resources/static.

Project Structure
Backend (Spring Boot)
Located under src/main/java/org/yearup:

controllers — REST endpoints

AuthenticationController

CategoriesController

ProductsController

ShoppingCartController

models — Entities & DTOs

Product, Category, User, Profile, ShoppingCart, etc.

Authentication DTOs: LoginDto, RegisterUserDto, LoginResponseDto

repository — Spring Data JPA repositories

ProductRepository, CategoryRepository, UserRepository, etc.

service — Business logic layer

ProductService, ShoppingCartService, etc.

security — JWT authentication & authorization

WebSecurityConfig

JWTFilter, TokenProvider

UserModelDetailsService

ECommerceApplication.java — Main Spring Boot entry point

Frontend (Static Web Client)
Located under src/main/resources/static:

index.html — Main entry page

templates/ — HTML views

home.html, product.html, cart.html, profile.html, etc.

js/ — Frontend logic

application.js, filter.js, template-builder.js

API service modules:

products-service.js

categories-service.js

shoppingcart-service.js

user-service.js

css/ — Styling (Bootstrap + custom CSS)

images/ — Store logo + product images

Database Scripts
Located in /database:

create_database_videogamestore.sql

Additional scripts for other store variants (clothing, grocery, etc.)

These scripts define:

Tables for products, categories, users, profiles, shopping carts

Seed data for testing

Foreign key relationships

Features
User Features
Log in with JWT authentication

Browse products by category

View product details

Add/remove items from the shopping cart



Admin Features

Manage products

Manage categories


Security
JWT‑based authentication

Role‑based authorization

Custom access denied & authentication handlers

API Endpoints
The API is defined in openapi.yaml (found in both root and /static).

Authentication
Code
POST /auth/login
POST /auth/register
Products
Code
GET /products
GET /products/{id}
Categories
Code
GET /categories
Shopping Cart
Code
GET /cart
POST /cart/products/{id}
PUT /cart/products/{id}
DELETE /cart/products/{id}

Tech Stack
Backend
Java 17+

Spring Boot

Spring Web

Spring Security (JWT)

Spring Data JPA / Hibernate

Maven

Frontend
HTML5, CSS3, JavaScript

Bootstrap

Axios

Mustache templates

Database
MySQL (default)

SQL scripts included in /database

Running the Application
1. Clone the repository
Code
git clone https://github.com/kay8181/video-game-store
cd video-game-store
2. Configure database
Edit src/main/resources/application.properties:

Code
spring.datasource.url=jdbc:mysql://localhost:3306/videogamestore
spring.datasource.username=youruser
spring.datasource.password=yourpass
spring.jpa.hibernate.ddl-auto=update
3. Build & run
Code
mvn clean install
mvn spring-boot:run
4. Access the app
Frontend:

Code
http://localhost:8080/
API:

Code
http://localhost:8080/api
Testing
Tests are located in:

Code
src/test/java/org/yearup/repository/ProductRepositoryTest.java
Test resources include:

test-data.sql

test-insert-data.sql

application.properties

Screenshots:
<img width="1910" height="940" alt="Screenshot 2026-06-26 013834" src="https://github.com/user-attachments/assets/cd090657-8e29-41f6-a4ca-2bee8b49bf7c" />

<img width="1913" height="922" alt="Screenshot 2026-06-26 013902" src="https://github.com/user-attachments/assets/82446f01-cc06-4c2e-a299-54f671b66b8a" />

<img width="1907" height="937" alt="Screenshot 2026-06-26 013920" src="https://github.com/user-attachments/assets/030bba04-623a-478f-a97e-a7e2898bbd5f" />

<img width="1907" height="937" alt="Screenshot 2026-06-26 013920" src="https://github.com/user-attachments/assets/4d051db1-e871-4dcf-9c3a-a4790576af25" />

<img width="1913" height="948" alt="Screenshot 2026-06-26 013938" src="https://github.com/user-attachments/assets/f4d8b04e-bf83-4037-bdcc-fb1f2a70b46a" />

<img width="1911" height="935" alt="Screenshot 2026-06-26 014611" src="https://github.com/user-attachments/assets/71c81e8d-3d8a-4741-8470-039c2adf13f6" />

<img width="1917" height="946" alt="Screenshot 2026-06-26 014621" src="https://github.com/user-attachments/assets/97f23943-b449-46fa-88c2-e68835845992" />

<img width="1907" height="946" alt="Screenshot 2026-06-26 014642" src="https://github.com/user-attachments/assets/bf8cd1f8-f233-45c1-b13e-23b9e57d6a1e" />

Interesting Piece of Code,
Logo:
<img width="1351" height="356" alt="Screenshot 2026-06-26 014932" src="https://github.com/user-attachments/assets/7f3e6566-f0ac-42ab-a09c-52670b1cd0a6" />

<img width="1841" height="660" alt="Screenshot 2026-06-26 014949" src="https://github.com/user-attachments/assets/ac7aeed5-5098-4f24-87d8-801dbcf2e6a3" />

<img width="1727" height="658" alt="Screenshot 2026-06-26 015002" src="https://github.com/user-attachments/assets/6f4cdf6c-9289-4f3e-bf8a-1c4d21a4306d" />




Code
/static/images/screenshots/
Contributing
Fork the repository

Create a feature branch

Commit your changes

Open a pull request

License
 MIT
