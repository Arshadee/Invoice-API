package io.ioco.invoiceapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ioco.invoiceapi.entities.LineItem;
import io.ioco.invoiceapi.repositories.LineItemRepository;


@Service
public class LineItemService {
	
	@Autowired
	private LineItemRepository lineItemRepository;
	
	 public List<LineItem> getAllLineItems(Long invoiceId){
		 List<LineItem> lineItems = new ArrayList<>();
		 lineItemRepository.findByInvoiceId(invoiceId)
		 .forEach(lineItems::add);
		 return lineItems;
	 }
	 
	 public List<LineItem> getAllLineItems(){
		 List<LineItem> lineItems = new ArrayList<>();
		 lineItemRepository.findAll()
		 .forEach(lineItems::add);
			return lineItems;
	 }
	 
	 public LineItem addLineItem(LineItem lineItem) {
		 return lineItemRepository.save(lineItem);
	 }

}
