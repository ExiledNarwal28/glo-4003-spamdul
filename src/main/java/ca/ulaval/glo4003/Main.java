package ca.ulaval.glo4003;

import ca.ulaval.glo4003.api.contact.ContactResource;
import ca.ulaval.glo4003.api.contact.ContactResourceImplementation;
import ca.ulaval.glo4003.api.parking.ParkingResource;
import ca.ulaval.glo4003.api.parking.ParkingResourceImplementation;
import ca.ulaval.glo4003.api.user.UserResource;
import ca.ulaval.glo4003.api.user.UserResourceImplementation;
import ca.ulaval.glo4003.domain.account.AccountFactory;
import ca.ulaval.glo4003.domain.account.AccountIdAssembler;
import ca.ulaval.glo4003.domain.account.AccountIdGenerator;
import ca.ulaval.glo4003.domain.account.AccountRepository;
import ca.ulaval.glo4003.domain.contact.Contact;
import ca.ulaval.glo4003.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.domain.contact.ContactRepository;
import ca.ulaval.glo4003.domain.contact.ContactService;
import ca.ulaval.glo4003.domain.parking.*;
import ca.ulaval.glo4003.domain.user.UserAssembler;
import ca.ulaval.glo4003.domain.user.UserService;
import ca.ulaval.glo4003.domain.user.exception.InvalidUserExceptionMapper;
import ca.ulaval.glo4003.http.CORSResponseFilter;
import ca.ulaval.glo4003.infrastructure.account.AccountRepositoryInMemory;
import ca.ulaval.glo4003.infrastructure.contact.ContactFakeFactory;
import ca.ulaval.glo4003.infrastructure.contact.ContactRepositoryInMemory;
import ca.ulaval.glo4003.infrastructure.parking.ParkingAreaFakeFactory;
import ca.ulaval.glo4003.infrastructure.parking.ParkingAreaRepositoryInMemory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Application;

import ca.ulaval.glo4003.serverConfiguration.ServerResourceConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

@SuppressWarnings("all")
public class Main {
  public static boolean isDev = true; // TODO : Would be a JVM argument or in a .property file

  public static void main(String[] args) throws Exception {
    // TODO : Move creation of resources elsewhere (custom injection)
    ContactResource contactResource = createContactResource(); // TODO : Remove demo Contact logic
    InvalidUserExceptionMapper invalidUserExceptionMapper = new InvalidUserExceptionMapper();
    // TODO : Add ParkingExceptionMapper

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");

    ServerResourceConfig serverResourceConfig = new ServerResourceConfig();
    ServletContainer servletContainer = new ServletContainer(serverResourceConfig);
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
      ContactFakeFactory contactFakeFactory = new ContactFakeFactory();
      List<Contact> contacts = contactFakeFactory.createMockData();
      contacts.stream().forEach(contactRepository::save);
    }

    ContactAssembler contactAssembler = new ContactAssembler();
    ContactService contactService = new ContactService(contactRepository, contactAssembler);

    return new ContactResourceImplementation(contactService);
  }
}
