package com.hostmdy.expenese.model;

import java.time.LocalDateTime;

public class Expense {
	private Long id;
	private String name;
	private Integer qty;
	private Double price;
	private Double subTotal;
	private LocalDateTime issuedDate;
	private String description;
	private String image;
	
	public Expense() {}

	public Expense(String name, Integer qty, Double price,String description, String image) {
		super();
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.subTotal = qty * price;
		this.issuedDate = LocalDateTime.now();
		this.description = description;
		this.image = image;
	}
	
	public Expense(Long id,String name, Integer qty, Double price,String description, String image) {
		super();
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.subTotal = qty * price;
		this.issuedDate = LocalDateTime.now();
		this.description = description;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public LocalDateTime getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(LocalDateTime issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
