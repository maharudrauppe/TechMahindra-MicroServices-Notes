package com.example.login;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@RibbonClient(name = "MicroService2")
@RestController
public class LoginController {



    @Autowired
    RestTemplate restTemplate;//=new RestTemplate();

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GET()
    @RequestMapping("/test1")
    public ResponseEntity test1()
    {
        System.out.println("request in Service 1 test1");
        return new ResponseEntity("welcome to service1",HttpStatus.OK);
    }

    @GET
    @RequestMapping("/test2")
    //@HystrixCommand(fallbackMethod = "error_calling_service2")
    public ResponseEntity test2()
    {

        System.out.println("calling  serive2");
        String url="http://MicroService2/SERVICE2/port";
        //String url="http://localhost:1237/SERVICE2/port";
        String l = restTemplate.getForObject(url, String.class);
        System.out.println(("response from service2  : "+l));
        return new ResponseEntity(l,HttpStatus.OK);
    }

    public ResponseEntity error_calling_service2()
    {
        return new ResponseEntity("error while calling service1 from service2",HttpStatus.OK);
    }

}
