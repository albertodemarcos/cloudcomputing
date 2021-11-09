package es.uah.facturasvalidadorms.controllers;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MicroservicioController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Map<String, String> alwaysOk = Collections.singletonMap("status", "UP");

	@RequestMapping("/readinessProbe/isOk")
	@ResponseBody Map<String, String> getMicroservicioStatusOK(HttpServletResponse response) {
		logger.info("El microservicio esta listo para utilizarse");
		response.setStatus(HttpStatus.SC_OK);
		return alwaysOk;
	}
}
