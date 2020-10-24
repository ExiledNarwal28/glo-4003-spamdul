package ca.ulaval.glo4003.initiative.infrastructure;

import ca.ulaval.glo4003.initiative.domain.Initiative;
import ca.ulaval.glo4003.initiative.domain.InitiativeCode;
import ca.ulaval.glo4003.initiative.domain.InitiativeRepository;
import ca.ulaval.glo4003.initiative.exception.InitiativeNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitiativeRepositoryInMemory implements InitiativeRepository {
  private final Map<InitiativeCode, Initiative> initiatives = new HashMap<>();

  @Override
  public InitiativeCode save(Initiative initiative) {
    initiatives.put(initiative.getInitiativeCode(), initiative);
    return initiative.getInitiativeCode();
  }

  @Override
  public List<Initiative> getAllInitiatives() {
    return new ArrayList<>(initiatives.values());
  }

  @Override
  public Initiative get(InitiativeCode initiativeCode) {
    Initiative initiative = initiatives.get(initiativeCode);

    if (initiative == null) {
      throw new InitiativeNotFoundException();
    }

    return initiative;
  }

  @Override
  public void update(Initiative initiative) {
    get(initiative.getInitiativeCode());

    initiatives.put(initiative.getInitiativeCode(), initiative);
  }
}
