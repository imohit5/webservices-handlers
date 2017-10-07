package com.mi9dev.handlers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class WebsiteHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		Boolean responseOrNot = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!responseOrNot) {
			SOAPMessage message = context.getMessage();
			try {
				SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
				SOAPHeader header = envelope.getHeader();
				Iterator childElements = header.getChildElements();

				while (childElements.hasNext()) {
					Node node = (Node) childElements.next();

					String localName = node.getLocalName();

					if (localName != null && localName.equals("WebsiteName")) {
						System.out.println("name: " + node.getValue());
					}

				}

			} catch (SOAPException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("outgoing in process");
			SOAPMessage soapMsg = context.getMessage();
			SOAPEnvelope soapEnv;
			try {
				soapEnv = soapMsg.getSOAPPart().getEnvelope();
				
				SOAPBody body = soapEnv.getBody();
				org.w3c.dom.Node hasAttribute = body.getFirstChild();
				
				String localName = hasAttribute.getLocalName();
				System.out.println("getOrdersResponse: "+localName);
				
				
				if(localName.equalsIgnoreCase("getOrdersResponse")) {
				
					
				SOAPHeader soapHeader = soapEnv.getHeader();

				if (soapHeader == null) {
					soapHeader = soapEnv.addHeader();
				}

				QName qname = new QName("http://www.mi9dev.com/CustomerOrders/", "TestId");
				SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

				// soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
				soapHeaderElement.addTextNode("123-123");
				soapMsg.saveChanges();

				soapMsg.writeTo(System.out);
				
				}
				
			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("handleFault");
		return false;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("close");
	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("getHeaders");
		return null;
	}

}
