package cz.omar.tennisclubreservationapp.customer;

import cz.omar.tennisclubreservationapp.customer.dto.CustomerDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable Long customerId) {
        System.out.println("got customer: " + customerId);
        return new CustomerDto(1, "user1", "+4511");
    }

    @DeleteMapping("/{customerId}")
    public CustomerDto deleteCustomer(@PathVariable Long customerId) {
        System.out.println("deleted: customer: " + customerId);
        return new CustomerDto(1, "user1", "+4511");
    }
}
