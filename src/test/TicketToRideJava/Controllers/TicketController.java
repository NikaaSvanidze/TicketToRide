package Controllers;

import DataClasses.Route;
import Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/calculate-price")
    public ResponseEntity<Integer> calculatePrice(@RequestBody Route route) {
        int price = ticketService.calculateOptimalTravelPrice(route);
        return ResponseEntity.ok(price);
    }

    @PostMapping("/buy-ticket")
    public ResponseEntity<String> buyTicket(@RequestParam String username,
                                            @RequestParam String password,
                                            @RequestParam Long routeId) {
        boolean bought = ticketService.buyTicket(username, password, routeId);
        if (bought) {
            return ResponseEntity.ok("Ticket purchased successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to purchase ticket. Insufficient funds or invalid credentials.");
        }
    }
}