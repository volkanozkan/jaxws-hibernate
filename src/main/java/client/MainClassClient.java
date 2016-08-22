package client;

import java.net.URL;
import java.text.SimpleDateFormat;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import ws.UserService;

public class MainClassClient {

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:8080/fourth/user-service?wsdl");
		// Service URI And Service Name.
		QName qname = new QName("http://ws/", "UserServiceImplService");

		UserService userService = Service.create(url, qname).getPort(UserService.class);

		// Call Create Method.
		System.out.println("Add");
		userService.createUser("volkan9", "volkan@gmail.com", "11111",
				new SimpleDateFormat("yyyyMMdd").parse("20160624"), (short) 1);

		// Call Update Method.
		System.out.println("Update");
		userService.updateUser("volkan8", "ozkanvolkan@outlook.com");

		// Call Read Method.
		System.out.println("Read");
		System.out.println(userService.read("volkan"));

		// Call Delete Method.
		System.out.println("Delete");
		userService.deleteUser("volkan5");
	}
}
