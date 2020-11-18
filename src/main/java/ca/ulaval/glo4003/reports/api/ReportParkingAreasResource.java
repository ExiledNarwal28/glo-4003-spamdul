package ca.ulaval.glo4003.reports.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reports/parkingAreas")
public interface ReportParkingAreasResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Response getParkingAreas(
      @DefaultValue("monthly") @QueryParam("reportType") String reportType,
      @DefaultValue("november") @QueryParam("month") String month); // TODO do not hardcode month
}
