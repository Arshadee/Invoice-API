package io.ioco.invoiceapi.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		BigDecimal result  = lineItems.stream()
				.map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}
	
	public BigDecimal getVat() {
		BigDecimal result = getSubtotal().multiply(new BigDecimal(vatRate))
				.divide(new BigDecimal(100));
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}
	
	public BigDecimal getTotal() {
		BigDecimal result = getSubtotal().add(getVat());
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((lineItems == null) ? 0 : lineItems.hashCode());
		result = prime * result + ((vatRate == null) ? 0 : vatRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (lineItems == null) {
			if (other.lineItems != null)
				return false;
		} else if (!lineItems.equals(other.lineItems))
			return false;
		if (vatRate == null) {
			if (other.vatRate != null)
				return false;
		} else if (!vatRate.equals(other.vatRate))
			return false;
		return true;
	}
	
	
}
