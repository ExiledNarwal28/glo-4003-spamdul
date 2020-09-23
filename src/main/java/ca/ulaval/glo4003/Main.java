package ca.ulaval.glo4003;

import ca.ulaval.glo4003.api.car.CarResourceImpl;
import ca.ulaval.glo4003.api.contact.ContactResource;
import ca.ulaval.glo4003.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.domain.Account.AccountService;
import ca.ulaval.glo4003.domain.car.CarAssembler;
import ca.ulaval.glo4003.domain.car.CarService;
import ca.ulaval.glo4003.domain.car.CarValidator;
import ca.ulaval.glo4003.domain.car.InvalidCarExceptionMapper;
import ca.ulaval.glo4003.domain.contact.Contact;
import ca.ulaval.glo4003.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.domain.contact.ContactRepository;
import ca.ulaval.glo4003.domain.contact.ContactService;
import ca.ulaval.glo4003.http.CORSResponseFilter;
import ca.ulaval.glo4003.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.infrastructure.contact.ContactRepositoryInMemory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/** RESTApi setup without using DI or spring */
@SuppressWarnings("all")
public class Main {
  public static boolean isDev = true; // TODO : Would be a JVM argument or in a .property file

  public static void main(String[] args) throws Exception {
    ContactResource contactResource = createContactResource();
    // TODO : Not the real AccountService, this one is a stub
    AccountService accountService = createAccountService();
    CarResourceImpl carResource = createCarResource(accountService);
    InvalidCarExceptionMapper invalidCarExceptionMapper = new InvalidCarExceptionMapper();

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig =
        ResourceConfig.forApplication(
            new Application() {
              @Override
              public Set<Object> getSingletons() {
                HashSet<Object> resources = new HashSet<>();
                resources.add(contactResource);
                resources.add(carResource);
                resources.add(invalidCarExceptionMapper);
                return resources;
              }
            });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] {context});
    Server server = new Server(8080);
    server.setHandler(contexts);

    try {
      server.start();
      server.join();
    } finally {
      server.destroy();
    }
  }

  private static ContactResource createContactResource() {
    ContactRepository contactRepository = new ContactRepositoryInMemory();

    if (isDev) {
      ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
      List<Contact> contacts = contactDevDataFactory.createMockData();
      contacts.stream().forEach(contactRepository::save);
    }

    ContactAssembler contactAssembler = new ContactAssembler();
    ContactService contactService = new ContactService(contactRepository, contactAssembler);

    return new ContactResourceImpl(contactService);
  }

  private static AccountService createAccountService() {
    return new AccountService();
  }

  private static CarResourceImpl createCarResource(AccountService accountService) {
    CarValidator carValidator = new CarValidator();
    CarAssembler carAssembler = new CarAssembler(carValidator);
    CarService carService = new CarService(carAssembler, accountService);

    return new CarResourceImpl(carService);
  }
}
