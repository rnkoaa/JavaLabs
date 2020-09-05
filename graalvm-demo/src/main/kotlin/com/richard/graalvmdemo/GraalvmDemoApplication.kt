package com.richard.graalvmdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(proxyBeanMethods = false)
class GraalvmDemoApplication

fun main(args: Array<String>) {
	runApplication<GraalvmDemoApplication>(*args)
}

data class Hello(val message: String)

@RestController
@RequestMapping("/api")
class HelloController {

	@GetMapping("/hello")
	fun sayHello(): Hello {
		return Hello("hello, world!")
	}
}
