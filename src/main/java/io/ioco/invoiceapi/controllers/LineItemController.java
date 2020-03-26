package io.ioco.invoiceapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.ioco.invoiceapi.entities.Invoice;
import io.ioco.invoiceapi.entities.LineItem;
import io.ioco.invoiceapi.services.LineItemService;



@RestController
public class LineItemController {
	
	@Autowired
	private LineItemService lineItemService;
	
	@RequestMapping("/invoices/{id}/lineitems")
	public List<LineItem> getAllLineItems(@PathVariable Long id) {
		return lineItemService.getAllLineItems(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/invoices/{invoiceId}/lineitems")
	public void addLineItem(@RequestBody LineItem lineItem, @PathVariable Long invoiceId) {
		//lineItem.setInvoice(new Invoice(invoiceId,"",null,null));//tTopic(new Topic(topicId,"",""));
		lineItemService.addLineItem(lineItem);
	}
	
	@RequestMapping("/lineitems")
	public List<LineItem> getAllLineItems(){
		 return lineItemService.getAllLineItems();
	}

}
