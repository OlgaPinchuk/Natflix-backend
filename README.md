# Natflix backend

## About

The Natflix Streaming project is a web-based application designed to meet the increasing demand for streaming services during the pandemic. This project aims to create a secure, robust, and scalable platform for streaming movies, series, and documentaries. It consists of both frontend and backend components that work together to provide a seamless streaming experience to customers.

## Backend
The backend will be developed using Java with Spring Boot and Hibernate within IntelliJ IDEA, connected to a MySQL database.


## Running the Application

To get the application up and running, follow these steps:

1. **Clone this project**
2. **Create a Database:** In your MySQL instance, create a new database that the application will use.
3. **Configure Database Connection** 
<br>
   * Locate the code `application.properties` file in the `src/main/resources` folder of the directory.
   * Update this file with the following information:
     - **URL:** Set the URL to point to your MySQL database.<br>
     - **Username:** Provide the username to access your database.<br>
     - **Password:** Enter the corresponding password for the provided username.<br>
4. Build the project using Maven
5. Run the jar file
6. Backend should be available at http://localhost:8080.

## Project documentation
* [Project requirements and backlog](https://docs.google.com/spreadsheets/d/1Qa9AoqakL_MrFDY0LZyrrkatrKT2fmMaqEwvo6EWlH0/)
* [User stories](https://docs.google.com/document/d/10f8xBv4TqigeU2Fb_ecDXdsPaoNk9i4QfYB91z-7jcU/edit)
* [Class diagram](https://lucid.app/lucidchart/cae4b935-0ef9-4177-a66b-6ac0f66414d7/edit?page=0_0&invitationId=inv_80c86a6b-b492-4a40-8876-dd9d799a7854#)
* [Use Case Diagram](https://lucid.app/lucidchart/15902163-18bf-4103-9f6e-7ca5e8a53b58/edit?page=Hs68-Svgrdj8&invitationId=inv_2d576aba-6bd5-4c76-bc9d-97274d55902c#)

## Technology Stack

`Java` v.17.0.2

