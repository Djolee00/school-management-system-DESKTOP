
# About the project

During one month work on project, I had the opportunity to learn a simplified software development process, from the identification of user requirements to the concrete implementation of the application.

I went through simplified **Larman's method of software development**, which consists of 5 stages:

 - Requirements definition stage
 - Analysis stage
 - Design stage
 - Implementation stage
 - Test stage


This project includes four of them.

# About the project

During one month work on project, I had the opportunity to learn a simplified software development process, from the identification of user requirements to the concrete implementation of the application.

I went through simplified **Larman's method of software development**, which consists of 5 stages:

 - Requirements definition stage
 - Analysis stage
 - Design stage
 - Implementation stage
 - Test stage


This project includes four of them.

## Requirements definition stage

In the first phase of development, I decided to develop a system that would simplify the management for schools of foreign languages. Two actors who interact with the system were identified, namely the **student** and the **administrator**.

For the purposes of modeling requirements, I used the *UML Use Case diagram*. I identified 10 Use Cases for those actors.

![Screenshot 2023-02-21 223748](https://user-images.githubusercontent.com/93478227/220464292-faf38479-f9cd-4770-99a2-32c0713b9e9d.jpg)


Each Use Case is described in detail and these descriptions are available in the documentation, which is currently available only in Serbian language :shushing_face: 


## Analysis stage


As a result of the **analysis phase**, a description of the dynamics of each Use Case and the structure of the software were obtained.

An *UML System Sequence Diagram* was used to describe the dynamics of every Use Case. The purpose of these diagrams is to show the interaction of actors with the system and the messages they exchange.
Below is the example of System Sequence diagram for Login Use Case:

![Screenshot 2023-02-21 225244](https://user-images.githubusercontent.com/93478227/220467072-229cc599-11ac-4e77-a16d-988799793615.jpg)

Conceptual model of the system is created using *UML Class Diagram*:

![Picture1](https://user-images.githubusercontent.com/93478227/220468871-8d6b730f-8317-49ac-b813-b7a4ea3ade23.jpg)

Here is the *Relational Model*:

```
User (id, username, password)
Administrator (user_id, employmentDate)
Student (user_id, firstName, lastName, birthDate, creationDate)
Course (id, name,  startDate, endDate, groupCapacity,language_id)
Language (id, name, level)
Tutor (id, firstName, lastName)
LanguageTutor (tutor_id, language_id)
CourseGroup (id,course_id, name, numberOfStudents)
TutorsAssignment (tutor_id, id,course_group_id)
GroupEnrollment (student_id, id,course_group_id)

```
## Design stage

At the beginning of this phase, I designed *user forms* for each use case. Here are some examples of forms:

**Courses form**

![Picture1](https://user-images.githubusercontent.com/93478227/220786151-e921c3f4-6b9e-484b-846a-7cea5fcdf022.jpg)


**Course groups form**

![Picture2](https://user-images.githubusercontent.com/93478227/220786153-7b67c2da-6a89-4df2-852a-16fb0517bcfe.jpg)



After designing the GUI, I worked on designing the *application logic*.
There is an controller of application logic that serves the requests that arrive on the server side of application, by using different services.

![Picture3](https://user-images.githubusercontent.com/93478227/220786684-21d2945d-e4df-4762-a3ac-7925fffded74.png)

In addition to controller of application logic, it also contains different services which provide implementation of defined system operations in cooperation with DAOs (Data Access Objects) that are responsible for communication with database.

Here is one example of communication betweeen controller, service and DAO:

![Picture4](https://user-images.githubusercontent.com/93478227/220787455-58892777-c38b-45b5-8c79-614b93176ff7.png)

In the picture below we can see the design of *Database Broker* which consists of different DAOs:

![Picture5](https://user-images.githubusercontent.com/93478227/220787659-b71935ce-d98b-46d8-bb40-bc9f7fab07b9.jpg)

And finally we can see the complete *architecture* of my software system:

![Picture6](https://user-images.githubusercontent.com/93478227/220787846-6b9599fa-087f-4394-bf49-3a47df30b173.png)
## Implementation stage

**Tech Stack:**
```
-Java SE 17
-MySQL
-JDBC API

```
**Tools:**
```
-Apache Netbeans 15
-SQLyog Community Version
```

**Short description:**

The entire application was made using Core Java, both client and server side. Things I would like to point out:

- **MVC pattern** is used on client side 
- I made Validation Library using **Builder pattern** which can be easily used in different situation. The idea for this library came from famous C# *FluentValidation library*
- Swing library is used for GUI
- The whole communication with database is implemented using JDBC (no frameworks like Hibernate)


##Validation Library

The validation library is made using **Builder pattern**. Here we can see the structure of library and example of library usage:

**Structure**

![Picture7](https://user-images.githubusercontent.com/93478227/220790843-0b90d2c9-f64c-4a2f-aa5f-49ca8112784f.jpg)

**Usage**

Defining validator for model class:
```
public class StudentValidatorBuilder extends AbstractValidator {
    
    public StudentValidatorBuilder(Student student) {
        ruleFor(student.getUsername())
                .notEmpty()
                .withMessage("You have to insert username")
                .minLength(5)
                .withMessage("Username must have at least 5 characters")
                .maxLength(15)
                .withMessage("Username can't have more than 15 characters");
        
        ruleFor(student.getPassword())
                .notEmpty()
                .withMessage("You have to insert password")
                .minLength(8)
                .withMessage("Password must have at least 8 characters")
                .maxLength(20)
                .withMessage("Password can't have more than 20 characters")
                .matchesRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])
                .withMessage("Password must have at least 1 lowercase, 1 uppercase and 1 special character");

 ```

Validation data:

```
    private void validateStudentData(Student student) throws ValidationException {
        ResultInfo result = new StudentValidatorBuilder(student).validate();
        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }
    }
```
## Run Locally

Clone the project

```bash
  git clone https://github.com/Djolee00/school-management-system-DESKTOP
```
Execute DDL SQL script in some MySQL client and make sure thah the MySQL server is running.

Download and add missing JARs in Apache Netbeans IDE.

