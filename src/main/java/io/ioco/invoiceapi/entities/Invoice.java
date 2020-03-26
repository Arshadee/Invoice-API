package io.ioco.invoiceapi.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Invoice {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String client;
	private Long vatRate;
	private Date invoiceDate;
	//@OneToMany
	//@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invoice", fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<LineItem> lineItems;
	
	public Invoice() {
		this.lineItems = new ArrayList<>();
	}
	
	public Invoice(Long id, String client, Long vatRate, Date invoiceDate){
		this.id = id;
		this.client = client;
		this.vatRate = vatRate;
		this.invoiceDate = invoiceDate;
		this.lineItems = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Long getVatRate() {
		return vatRate;
	}

	public void setVatRate(Long vatRate) {
		this.vatRate = vatRate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public BigDecimal getSubtotal() {
		List<BigDecimal> prices = lineItems.stream()
				.map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantity())))
				.collect(Collectors.toList());	
				
		BigDecimal result = prices.stream()
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return result;
	}
	
	public BigDecimal getVat() {
		return getSubtotal().multiply(new BigDecimal(vatRate))
				.divide(new BigDecimal(100));
	}
	
	public BigDecimal getTotal() {
		return getSubtotal().add(getVat());
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", client=" + client + ", vatRate=" + vatRate + ", invoiceDate=" + invoiceDate
				+ ", lineItems=" + lineItems + "]";
	}
	
	
}
