package Services;

import DataClasses.Route;
import DataClasses.Ticket;
import DataClasses.Traveler;
import Repository.ITicketRepository;
import Repository.ITravelerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private ITicketRepository _ticketRepository;
    @Autowired
    private ITravelerRepository _travelerRepository;

    @Transactional
    public int calculateOptimalTravelPrice(Route route) {
        int numSegments = route.getSegments().size();
        if (numSegments == 1) {
            return 5;
        } else if (numSegments == 2) {
            return 7;
        } else {
            return 10;
        }
    }

    @Transactional
    public boolean buyTicket(String username, String password, Long routeId) {
        Traveler traveler = _travelerRepository.findByUsername(username);
        if (traveler != null && traveler.getPassword().equals(password)) {
            Route route = new Route();
            int ticketPrice = calculateOptimalTravelPrice(route);
            if (traveler.getMoney() >= ticketPrice) {
                Ticket ticket = new Ticket();
                ticket.setRoute(route);
                ticket.setPrice(ticketPrice);
                traveler.setMoney(traveler.getMoney() - ticketPrice);
                _ticketRepository.save(ticket);
                _travelerRepository.save(traveler);
                return true;
            }
        }
        return false;
    }
}



