package gx.server.dbcopy;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/")
public class CopyDbCtrl {
	static final int STATUS_OK = 200; 
	static final int STATUS_NOT_AUTH = 401;
	static final int STATUS_NOT_FOUND = 404;
	static final int STATUS_SERVER_ERR = 500;
	@Context
	private HttpServletRequest httpRequest;

	public CopyDbCtrl() {
	}
	
    @GET
    @Path("/")
    public Response test() {
      return Response.status(STATUS_OK).entity("test page ok").build();
    }
  
    @POST
    @Path("/ping")
//    @Consumes("application/json")
    public Response ping() {
    	ResponseBuilder response = Response.ok();
//        response.header("X-Some-Server-Header", "value");

//        response.entity(new StreamingOutput() {
//          @Override
//          public void write(OutputStream outputStream) throws IOException, WebApplicationException {
//            Files.copy(Paths.get(fileName), outputStream);
//          }
//        });

        return response.build();
    }
    
    
}
