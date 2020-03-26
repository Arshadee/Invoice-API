package io.ioco.invoiceapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ioco.invoiceapi.entities.Invoice;
import io.ioco.invoiceapi.entities.LineItem;
import io.ioco.invoiceapi.repositories.InvoiceRepository;


@Service
public class InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	LineItemService lineItemService;
	
	public List<Invoice> viewAllInvoices(){
		List<Invoice> invoices = new ArrayList<>();
		invoiceRepository.findAll()
		 .forEach(invoices::add);
		return invoices;
		
	}
	
	public Invoice viewInvoice(Long id) {
		return invoiceRepository.findById(id).orElse(null);
	}
	
	public void addInvoice(Invoice invoice) {
		invoiceRepository.save(invoice);
	}
	
	public void addLineItem(Long id, LineItem lineItem) {
		Invoice invoice = invoiceRepository.findById(id).orElse(null);
		if(invoice == null) return;
		//lineItem.setInvoice(invoice);
		//invoice.addLineItem(lineItem);
		invoice.addLineItem(lineItemService.addLineItem(lineItem));
		//invoiceRepository.save(invoice);
		System.out.println(invoiceRepository.save(invoice));
		System.out.println(invoice.getLineItems());
		
	}

}
