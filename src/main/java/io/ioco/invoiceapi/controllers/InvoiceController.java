package io.ioco.invoiceapi.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping("/invoices/client/{client}")
	public Invoice getInvoiceByClient(@PathVariable String client) {
		return invoiceService.viewInvoice(client);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/invoices")
	public void addInvoice(@RequestBody Invoice invoice) {
		invoiceService.addInvoice(invoice);
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
