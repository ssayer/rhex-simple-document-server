package org.mitre.rhex.jwt;

import java.io.IOException;
import java.util.logging.Logger;

import org.mitre.rhex.InvalidAuthorizationException;
import org.mitre.rhex.oauth2.AuthorizationChecker;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("/documents")
public class DocumentEndpoint {
	
	@Autowired
	AmqpTemplate template;
	
	@Autowired
	AuthorizationChecker checker;
	
	private static final Logger logger = Logger.getAnonymousLogger();
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	public void upload(@RequestParam("client_id") String clientId, @RequestParam("document") String document, @RequestParam("token") String token) throws IOException {
		
		logger.info("Authorizing...");
		if (checker.checkAuthorization(clientId, token)) {
			logger.info("Putting document on queue...");
			template.send("", "documents", new Message(document.getBytes(), new MessageProperties()));
		} else {
			logger.info("Authoriation token was not valid");
			throw new InvalidAuthorizationException();
		}
	
	}

	public AmqpTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AmqpTemplate template) {
		this.template = template;
	}

	public AuthorizationChecker getChecker() {
		return checker;
	}

	public void setChecker(AuthorizationChecker checker) {
		this.checker = checker;
	}

	

}
