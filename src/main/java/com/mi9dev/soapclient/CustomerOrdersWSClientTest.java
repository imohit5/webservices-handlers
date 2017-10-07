package com.mi9dev.soapclient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.mi9dev.customerorders.GetOrdersRequest;
import com.mi9dev.customerorders.GetOrdersResponse;
import com.mi9dev.customerorders.Order;
import com.mi9dev.customerorders.Product;

@Component
public class CustomerOrdersWSClientTest {
	
	@Autowired
	private CustomerOrdersWSClient customerOrdersWSClient;

	public static void main(String[] args) throws MalformedURLException {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.mi9dev.soapclient");
		CustomerOrdersWSClientTest customerOrdersWSClientTest = ctx.getBean(CustomerOrdersWSClientTest.class);
		
		
		GetOrdersRequest getOrdersRequest = new GetOrdersRequest();
		getOrdersRequest.setCustomerId(BigInteger.valueOf(1));


		GetOrdersResponse ordersResponse = customerOrdersWSClientTest.customerOrdersWSClient.getOrdersByCustomerId(getOrdersRequest);

		List<Order> orders = ordersResponse.getOrder();

		for (Order order : orders) {
			System.out.println(order.getId());
			List<Product> products = order.getProduct();
			for (Product product : products) {
				System.out.println(product.getId());
				System.out.println(product.getDescription());
				System.out.println(product.getQuantity());
			}

		}

	}

}
