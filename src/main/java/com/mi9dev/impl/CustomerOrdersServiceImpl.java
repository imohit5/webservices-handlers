package com.mi9dev.impl;

import java.util.List;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jws.HandlerChain;

import com.mi9dev.customerorders.CreateOrdersRequest;
import com.mi9dev.customerorders.CreateOrdersResponse;
import com.mi9dev.customerorders.CustomerOrders;
import com.mi9dev.customerorders.GetOrdersRequest;
import com.mi9dev.customerorders.GetOrdersResponse;
import com.mi9dev.customerorders.Order;
import com.mi9dev.customerorders.Product;

@HandlerChain(file="soap-handler.xml")
public class CustomerOrdersServiceImpl implements CustomerOrders {
	
	public CustomerOrdersServiceImpl() {
		init();
	}
	
	int customerId;
	Map<BigInteger, List<Order>> customers = new HashMap<>();

	public void init() {
		ArrayList<Order> orders = new ArrayList<>();
		
		Order order = new Order();
		order.setId(BigInteger.valueOf(12));
		
		Product product = new Product();
		product.setId("123");
		product.setDescription("MacBook");
		product.setQuantity(BigInteger.valueOf(2));
		
		order.getProduct().add(product);
		
		orders.add(order);
		
		customers.put(BigInteger.valueOf(++customerId), orders);
		
	}

	@Override
	public GetOrdersResponse getOrders(GetOrdersRequest parameters) {
		BigInteger customerId2 = parameters.getCustomerId();
		List<Order> orders = customers.get(customerId2);
		
		GetOrdersResponse getOrdersResponse = new GetOrdersResponse();
		List<Order> responseOrders = getOrdersResponse.getOrder();
		for (Order order : orders) {
			responseOrders.add(order);
		}
		
		return getOrdersResponse;
	}

	@Override
	public CreateOrdersResponse createOrders(CreateOrdersRequest parameters) {
		Order order = parameters.getOrder();
		
		List<Order> newOrders = customers.get(parameters.getCustomerId());
		newOrders.add(order);
		CreateOrdersResponse createOrdersResponse = new CreateOrdersResponse();
		createOrdersResponse.setResult(true);
		
		return createOrdersResponse;
	}

}
