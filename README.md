# SpringBoot-Invoice-API
## Assessment for IOCO

## Clone the application

https://github.com/Arshadee/Invoice-API.git

## Build / Compile the application using maven
In command-Line / term navigate to the project folder  
and run : **mvn clean install**  

## Run the application  
In command-Line / term navigate to the project folder  
and run : **java -jar target/invoice-api-0.0.1-SNAPSHOT.jar**  

Alternatively, you can run the app without packaging it using  
**mvn spring-boot:run**  

The application will start running at    
http://localhost:8080

### Explore REST API

The Application defines the following
CRUD APIs:

Gets all the Invoices  
GET http://localhost:8080/invoices

Adds a new Invoice  
POST http://localhost:8080/invoices

Gets a specific invoice by invoice Id  
GET http://localhost:8080/invoices/{id}

Gets a specific invoice by client
GET http://localhost:8080/invoices/client/{client}"

Adds the line items for a specific Invoice,
search for by Invoice Id  
POST http://localhost:8080/invoices/{id}/lineItems

Gets the Subtotal of the line items for a specific Invoice
invoice search for by Invoice Id  
GET http://localhost:8080/invoices/{id}/getsubtotal

Gets the Total of the line items for a specific Invoice
invoice search for by Invoice Id  
GET http://localhost:8080/invoices/{id}/gettotal

Gets the Vat of the line items for a specific Invoice,
search for by Invoice Id  
GET http://localhost:8080/invoices/{id}/getvat

Gets the line items for a specific Invoice,
search for by Invoice Id  
GET http://localhost:8080/invoices/{id}/lineItems
