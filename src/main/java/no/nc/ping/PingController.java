package no.nc.ping;

import io.swagger.inflector.models.RequestContext;
import io.swagger.inflector.models.ResponseContext;
import no.nc.model.Pong;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.time.OffsetDateTime;

@Component
public class PingController {

    public ResponseContext getPing(RequestContext context, String message) {
        return new ResponseContext()
                .status(Response.Status.OK)
                .entity(new Pong()
                        .message(message)
                        .dateAndTime(OffsetDateTime.now()));
    }
}
