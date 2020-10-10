package ca.ulaval.glo4003.parkings.api;

import ca.ulaval.glo4003.parkings.api.dto.AccessStatusDto;
import ca.ulaval.glo4003.parkings.api.dto.ParkingStickerCodeDto;
import ca.ulaval.glo4003.parkings.api.dto.ParkingStickerDto;
import ca.ulaval.glo4003.parkings.domain.AccessStatus;
import ca.ulaval.glo4003.parkings.services.ParkingStickerService;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ParkingResourceImplementation implements ParkingResource {
  private final ParkingStickerService parkingStickerService;

  public ParkingResourceImplementation(ParkingStickerService parkingStickerService) {
    this.parkingStickerService = parkingStickerService;
  }

  @Override
  public Response addParkingSticker(ParkingStickerDto parkingStickerDto) {
    ParkingStickerCodeDto parkingStickerCodeDto =
        parkingStickerService.addParkingSticker(parkingStickerDto);
    return Response.status(Response.Status.CREATED)
        .entity(parkingStickerCodeDto)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @Override
  public Response validateParkingSticker(String parkingStickerCode) {
    AccessStatusDto accessStatusDto =
        parkingStickerService.validateParkingStickerCode(parkingStickerCode);

    Response.Status status;
    if (AccessStatus.ACCESS_REFUSED.toString().equals(accessStatusDto.accessStatus)) {
      status = Response.Status.FORBIDDEN;
    } else {
      status = Response.Status.ACCEPTED;
    }

    return Response.status(status).entity(accessStatusDto).type(MediaType.APPLICATION_JSON).build();
  }
}