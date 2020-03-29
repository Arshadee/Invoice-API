# SpringBoot-Invoice-API
## Assessment for IOCO

## Clone the application

https://github.com/Arshadee/Invoice-API.git

## Run the application

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
