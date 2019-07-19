package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SpringDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL = "SELECT * FROM customers";

    public List<Customer> isData() {

        List<Customer> customers = new ArrayList<Customer>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setCustNo((int)row.get("Cust_id"));
            customer.setCustName((String)row.get("Cust_name"));
            customer.setCountry((String)row.get("Country"));

            customers.add(customer);
        }

        return customers;
    }
}
