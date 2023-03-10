package ron.cyberstar.resource;

import io.smallrye.mutiny.Uni;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ron.cyberstar.service.CyberStarService;

@Path("/cyberstar")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CyberStarResource {

  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  @Inject
  CyberStarService cyberStarService;

  @GET
  @Path("/{loginId}")
  public Uni<Response> getCyberstar(String loginId) {
    return cyberStarService.getCyberStar(loginId)
        .onItem().ifNotNull().transform(c -> Response.ok(c).build())
        .onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
  }

  @GET
  public Uni<Response> getCyberstar() {
    return cyberStarService.getAllCyberStar()
        .onItem().ifNotNull().transform(listC -> Response.ok(listC).build())
        .onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
  }
  @POST()
  @Path("/{loginId}/subscribe")
  @Consumes(MediaType.TEXT_PLAIN)
  @Transactional
  public Uni<Response> subscribe(@PathParam("loginId") String loginId,
      long currentUserId) {
    lock.writeLock().lock();
    try {
      return cyberStarService.subscribe(currentUserId, loginId)
          .onItem().ifNotNull().transform(c -> Response.ok(c).build());

    }finally {
      lock.writeLock().unlock();
    }
  }
}
