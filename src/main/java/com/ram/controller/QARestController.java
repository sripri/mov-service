package com.ram.controller;

import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;

import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping(value = "/", produces = { MediaType.TEXT_PLAIN_VALUE })
public class QARestController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	private final ExecutorService futureExecutorsPool1 = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

	private final ExecutorService futureExecutorsPool2 = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

	@RequestMapping(method = RequestMethod.GET)

	@Consumes(MediaType.ALL_VALUE)

	public ResponseEntity getAnswer(@RequestParam(name = "q") String q,
			@RequestParam(name = "puzzle", required = false) String puzzle, @RequestHeader HttpHeaders headers)
			throws InterruptedException, ExecutionException {

		log.debug("New GET request coming through : {} ", q, (puzzle != null ? puzzle : "NA"));

		try {

		} catch (Exception e) {
			log.error("Exception in service {}", e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred : " + e.getMessage());
		}
		log.debug("Returnning pong .........");

		return ResponseEntity.ok("OK");
	}

	@RequestMapping(method = RequestMethod.POST)

	@Consumes(MediaType.ALL_VALUE)

	public ResponseEntity postAnswerWithOthers(@RequestParam(name = "q") String q,
			@RequestParam(name = "puzzle", required = false) String puzzle, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws InterruptedException, ExecutionException {

		log.debug("New POST request with query params coming through : {} puzzle {} ", q,
				(puzzle != null ? puzzle : "NA"));
		log.debug("Headers --- {}", headers);
		log.debug("Request --- {}", request);
		String[] params = q.split("\\]\\[");
		String key= params[0].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("'", "");
		String value=params[1].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("'", "");
		
		log.debug("Input parameters ---- {} ---  {}", key, value);
		try {
			switch (value) {

			case "Name":
				return ResponseEntity.ok("Ram Natarajan");
			case "Email":
				return ResponseEntity.ok("test@gmail.com");
				
			case "Years":
				return ResponseEntity.ok("100");
				
			case "Position":
				return ResponseEntity.ok("Full Stack Engineer");
				
			case "Phone":
				return ResponseEntity.ok("111-222-1234");
				
			case "Referrer":
				return ResponseEntity.ok("dice.com");
				
			case "Status":
				return ResponseEntity.ok("Unknown");
				
			case "Puzzle":
				String puzzledecoded=new String(Base64.getDecoder().decode(puzzle));
				String eval=MVEL.eval(puzzledecoded)+"";
				log.debug("Eval expression {} --- {} ", puzzledecoded, eval);
				return ResponseEntity.ok(Math.round(Double.parseDouble(eval))+"");
				
			case "Source":
				return ResponseEntity.ok("https://github.com/rubiconvale/movology.git");
				
			case "Resume":
				return ResponseEntity.ok("https://drive.google.com/weiouwoueriwurw");
				
			default:
				return ResponseEntity.ok("OK");
			}
		} catch (Exception e) {
			log.error("Exception in service {}", e.getMessage());
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred : " + e.getMessage());
		}
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> printException(HttpServletRequest req, Exception e) {
		log.error("Exception occurred of type {}", e.getClass());
		log.error("Exception Message {}", e.getLocalizedMessage());
		log.error("Exception gen message {}", e.getMessage());

		return ResponseEntity.badRequest().body("Error");

	}
}
