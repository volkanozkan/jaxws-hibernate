package ws;

import model.Users;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface UserService {

	@WebMethod(operationName = "read")
	public Users read(String username);

	@WebMethod(operationName = "add")
	public void createUser(String username, String email, String password, Date birthday, Short sex);

	@WebMethod(operationName = "update")
	public void updateUser(String username, String email);

	@WebMethod(operationName = "delete")
	public void deleteUser(String username);

}
