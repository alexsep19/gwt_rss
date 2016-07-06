package gx.server.login;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginCtrl {

	@Context
	  private HttpServletRequest httpRequest;
	
  @GET
  @Path("/")
  public Response test() {
      return Response.status(200).entity("test page ok").build();
  }
  
  @POST
  @Path("/login")
  public Response login(@FormParam("username") String username, @FormParam("password") String password) {
   
      try {
//      	System.out.println("username: " + username + "; password: " + password);
//      	httpRequest.login("admin", "jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=");
//      	httpRequest.login("admin", "admin");
      	httpRequest.login(username, password);
      	httpRequest.getSession().setAttribute("login", username);
      	
//        System.out.println("Login Success for: " + username);
//          LOGGER.info("Login Success for: " + username);
          return Response.seeOther(new URI("../")).build();

      } catch (Exception e) {

          System.out.println("Login Exceptio for: " + e.getMessage());
          try{
//        	  return Response.seeOther(new URI("../loginerror.html")).build();
        	  return Response.temporaryRedirect(new URI("../loginerror.html")).build();
          }catch(Exception ee){
        	  System.out.println("return Response.seeOther(new URI(\"../loginerror\"))");  
          }
          System.out.println("Exception loginerror");
      }
      System.out.println("Exception null");
      return null;
  }
   
  @GET
  @Path("/logout")
  public Response logout() {
      try {
      	if (httpRequest.getSession(false) != null) {
      		httpRequest.getSession(false).invalidate();// remove session.
          }
      	httpRequest.logout();
      	return Response.seeOther(new URI("../login")).build();
      } catch (Exception e) {
      	System.out.println("Logout Exception: " + e.getMessage());
//          LOGGER.error("Logout Exception: " + e.getMessage());
      }
      return null;
  }

}
