package io.ioco.invoiceapi.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.ioco.invoiceapi.entities.Invoice;
import io.ioco.invoiceapi.entities.LineItem;
import io.ioco.invoiceapi.services.InvoiceService;


@RestController
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@RequestMapping("/invoices")
	public List<Invoice> getAllInvoices() {
		return invoiceService.viewAllInvoices();
	}
	
	@RequestMapping("/invoices/{id}")
	public Invoice getInvoice(@PathVariable Long id) {
		return invoiceService.viewInvoice(id);
	}
	
//	@RequestMapping("/invoices/client/{client}")
//	public Invoice getInvoiceByClient(@PathVariable String client) {
//		return invoiceService.viewInvoice(client);
//	}
	
	@RequestMapping("/invoices/client/{client}")
	public ResponseEntity<Invoice> getInvoiceByClient(@PathVariable String client) {
		Invoice invoice = invoiceService.viewInvoice(client);
		if(invoice == null)
			return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(method=RequestMethod.POST, value="/invoices")
	public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
		HttpStatus status = HttpStatus.OK;
		try {
			invoiceService.addInvoice(invoice);
			//used to test error 500 internal error
			//throw new IOException("IOException test"); 
			/*note if data sent to be persisted is incorrect
			  http status code 400 Bad request is returned automatically
			  to test this I populated vatRate with a String value
			*/
		}catch(Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		finally{
			return new ResponseEntity<Invoice>(status);
		}
		 
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/invoices/{id}/addlineitem")
	public void addLineItem(@RequestBody LineItem lineItem, @PathVariable Long id) {
		System.out.println(lineItem);
		invoiceService.addLineItem(id, lineItem);
		
	}
	
	@RequestMapping("/invoices/{id}/getsubtotal")
	public BigDecimal getSubTotal(@PathVariable Long id) {
		Invoice invoice = invoiceService.viewInvoice(id);
		return invoice.getSubtotal();
	}
	
	@RequestMapping("/invoices/{id}/gettotal")
	public BigDecimal getTotal(@PathVariable Long id) {
		Invoice invoice = invoiceService.viewInvoice(id);
		return invoice.getTotal();
	}
	
	@RequestMapping("/invoices/{id}/getvat")
	public BigDecimal getVat(@PathVariable Long id) {
		Invoice invoice = invoiceService.viewInvoice(id);
		return invoice.getVat();
	}

}
