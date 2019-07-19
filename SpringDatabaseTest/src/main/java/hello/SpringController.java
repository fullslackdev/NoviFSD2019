package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpringController {

    @Autowired
    public SpringDAO dao;

    @RequestMapping("/getcustInfo")
    public List<Customer> customerInformation() {
        List<Customer> customers = dao.isData();
        return customers;
    }
}
