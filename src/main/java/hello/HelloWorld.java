package hello;

import javax.jws.WebService;

@WebService
public class HelloWorld {

public void sayHello() {
        System.out.println("Welcome to JAX-WS 2!");
    }
}