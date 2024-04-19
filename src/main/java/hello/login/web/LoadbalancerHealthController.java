package hello.login.web;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthy")
public class LoadbalancerHealthController {
    @GetMapping("")
    public int successHealthCheck() {
        return Response.SC_OK;
    }
}
