package ca.ulaval.glo4003.offenses.assemblers;

import ca.ulaval.glo4003.offenses.api.dto.OffenseTypeDto;
import ca.ulaval.glo4003.offenses.domain.OffenseType;
import java.util.List;
import java.util.stream.Collectors;

public class OffenseTypeAssembler {
  public OffenseTypeDto assemble(OffenseType offenseType) {
    OffenseTypeDto offenseTypeDto = new OffenseTypeDto();

    offenseTypeDto.description = offenseType.getDescription();
    offenseTypeDto.code = offenseType.getCode().toString();
    offenseTypeDto.amount = offenseType.getAmount().toDouble();

    return offenseTypeDto;
  }

  public List<OffenseTypeDto> assembleMany(List<OffenseType> offenseTypes) {
    return offenseTypes.stream().map(this::assemble).collect(Collectors.toList());
  }
}
