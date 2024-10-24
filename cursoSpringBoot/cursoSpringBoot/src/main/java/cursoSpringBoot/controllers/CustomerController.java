package cursoSpringBoot.controllers;

import cursoSpringBoot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(123, "Gerardo López", "gerardol", "contrasena123"),
            new Customer(456, "Alejandra García", "alegarcia", "clave456"),
            new Customer(789, "Laura Sánchez", "lauras", "secreto789"),
            new Customer(234, "Carlos Martínez", "carlosm", "password234")
    ));

    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customers);
    }

    // @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @GetMapping("/{username}")
    public ResponseEntity<?> getClient(@PathVariable String username) {
        for (Customer c: customers) {
            if (c.getUsername().equals(username)){
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado por username: " + username);
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@RequestBody Customer customer) {
        customers.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente: " + customer.getUsername());
    }

    @PutMapping
    public ResponseEntity<?> putCliente(@RequestBody Customer customer){
        for  (Customer c : customers){
            if(c.getID() == customer.getID()) {
                c.setName(customer.getName());
                c.setUsername(customer.getUsername());
                c.setPassword(customer.getPassword());

                return ResponseEntity.ok("Cliente modificado satisfactoriamente: " + customer.getID());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + customer.getID());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id){
        for(Customer c : customers){
            if(c.getID() == id){
                customers.remove(c);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente eliminado satisfactoriamente: " + id);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado: " + id);
    }

    @PatchMapping
    public ResponseEntity<?> patchCliente(@RequestBody Customer customer){
        for(Customer c : customers){
            if(c.getID() == customer.getID()){
                if(customer.getName() != null){
                    c.setName(customer.getName());
                }
                if(customer.getUsername() != null){
                    c.setUsername(customer.getUsername());
                }
                if(customer.getPassword() != null){
                    c.setPassword(customer.getPassword());
                }
                return ResponseEntity.ok("Cliente modificado satisfactoriamente: " + customer.getID());
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con id: " +  customer.getID());
    }
}
