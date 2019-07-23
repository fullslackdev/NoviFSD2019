# JavaTest

**Contents of repository**
This repository contains all the applications and servlets created during classes or for testing purposes during the first NOVI Bootcamp for Full Stack Developers, which started in May 2019.

**License**
Everything in this repository can be used for education and testing purposes only. No code can be used in production, commercial or even graded assignments without contacting the author.

## Folder description

**_Please note_**
Unless explicitly mentioned all database connections are made with MariaDB or MySQL. Check *connection URL* and/or *pom.xml files* for details on connection and loaded dependencies.
All database connections are made non persistent, meaning they are closed after each SQL query.

### DatabaseServletTest
Java Servlet for connecting to a database. Contains a singleton implementation and also a persistent connection test.

### DatabaseTest
Local Java program to test a database connection.

### FileUploadTest
Java Servlet for testing a file upload. Upload folder is hardcoded into source. Also contains a drag and drop example using jQuery.

### GuessTheNumber
Made during Java Introduction class given by Robin Bakels. This is a command line game to guess a number, featuring feedback.

### HelloWorld
The basic Hello World example. Enhanced with several features and tests, some made during the Java Introduction class given by Robin Bakels. Also contains a local Java GUI example featuring a easter egg and a SHA3-512 hash example (including byte array to string conversion).

### HttpSessionServletTest
Originally started as a Java JSP and Servlet test for session management. Ended up containing a functioning database connection and fully featured username and password validation, including password hashing with random salting.

### JaveTest
Local Java program to test audio file conversion, using the [Jave2 library](https://github.com/a-schild/jave2).

### LiquorStoreApp
The first Java JSP and Servlet tutorial I followed. Enhanced with a filter to get a A+ (August 2019) rating on the [Security Headers](https://securityheaders.com/) website.

### Pyramid
*insert description*

### SpringBootDataQuickStart
*insert description*

### SpringBootQuickStart
*insert description*

### SpringDatabaseTest
*insert description*

### SpringSecurityTest
*insert description*

### Sudoku
*insert description*

### TicTacToe
*insert description*

### TwoFactorAuthTest
Local Java program for testing of two factor authentication (2FA) using Google Authenticator. Includes a QR Code generator using Google Chart API.

### database
MariaDB SQL and data export for the HttpSessionServletTest. Visual Paradigm class diagram for this database.

### demo
Made during Java OOP class given by Dennis Haverhals. This is a example of using Spring Boot to create a REST api and using JSON output to transfer data to a React app for parsing to a front-end (or something like this, I loathe React).

### mywebapp
*insert description*