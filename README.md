# Employees Application

This is an employees web application built with Spring Boot and Maven, using Java 17. The
application stores employee and project info, and checks if employees have worked on the same
project at the same time via a csv file uploaded via the browser,

## Requirements

To run this application, you need to have the following installed on your machine:

- Java 17
- Maven
- IDE of your choice

## Running the Application

To run the application, follow these steps:

1. Clone the repository to your local machine.
2. Run the application.
3. Once the application has started, navigate to `http://localhost:8080` in your web browser.
4. You should see the application running and ready to accept file uploads.

## File Upload Format

The application accepts CSV files with the following format:

`EmpID,ProjectID,DateFrom,DateTo`

- EmpID: The ID of the employee (.
- ProjectID: The ID of the project (integer).
- DateFrom: The start date of the project (yyyy-mm-dd).
- DateTo: The end date of the project (yyyy-mm-dd). This field can be left blank, in which case it
  will default to today's date.

## Using the Application

Once the application is running, you can upload a file by clicking on the "Upload File" button on
the home page. Select the CSV file that you want to upload, and click "Submit". The application will
then process the file and display the results on the same page.

## TODO

- Extensive tests
- Security configuration
