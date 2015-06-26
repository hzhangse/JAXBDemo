package cnblogs.jaxb_helloworld;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.ObjectFactory;
import model.Order;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	private String getProductDesc(Order.OrderItems.Produdct product) {
		return "productNo:" + product.getProductNo() + ",productName:"
				+ product.getProductName() + ",price:" + product.getPrice()
				+ ",amount:" + product.getAmount();

	}

	public void testXmlToObj() {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance("model");
			Unmarshaller u = jc.createUnmarshaller();
			String xmlFilePath = AppTest.class.getResource("/").getPath()
					+ "order.xml";
			System.out.println(xmlFilePath);
			Order order = (Order) u.unmarshal(new File(xmlFilePath));
			assertTrue(order.getOrderNo().equals("0000001"));
			assertTrue(order.getCustomerName().equals("jimmy"));
			for (int i = 0; i < order.getOrderItems().getProdudct().size(); i++) {
				System.out.println(getProductDesc(order.getOrderItems()
						.getProdudct().get(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testObjToXml() {
		try {
			ObjectFactory of = new ObjectFactory();
			Order order = of.createOrder();
			Order.OrderItems orderItems = of.createOrderOrderItems();
			
			Order.OrderItems.Produdct product1 = new Order.OrderItems.Produdct();
			product1.setProductNo("A-01");
			product1.setProductName("Car");
			product1.setPrice(1000000);
			product1.setAmount(1);
			
			orderItems.getProdudct().add(product1);
			
			Order.OrderItems.Produdct product2 = new Order.OrderItems.Produdct();
			product2.setProductNo("B-01");
			product2.setProductName("Book");
			product2.setPrice(200);
			product2.setAmount(2);
			
			orderItems.getProdudct().add(product2);
		
			order.setOrderItems(orderItems);

			JAXBContext jc = JAXBContext.newInstance("model");
			Marshaller ms = jc.createMarshaller();
			ms.setProperty("jaxb.encoding", "UTF-8");
			ms.setProperty("jaxb.formatted.output", true);
			String xmlFilePath = AppTest.class.getResource("/").getPath()
					+ "order_new.xml";
			ms.marshal(order, new File(xmlFilePath));
			
			System.out.println("testObjToXml");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
