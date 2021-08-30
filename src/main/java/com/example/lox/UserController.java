package com.example.lox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private LockRegistry lockRegistry;

    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name) throws InterruptedException {

        System.out.println("Inside greeting method..");
        Lock lock = lockRegistry.obtain(name);
        boolean acquired = lock.tryLock(2, TimeUnit.SECONDS);
        if(acquired){
            System.out.println("Lock acquired!");
            Thread.sleep(10000);
        }
        System.out.println("Releasing lock..");
        lock.unlock();

        return "Hello " + name;
    }

    @GetMapping("/")
    public String hello(){
        return "Hello";
    }
}
