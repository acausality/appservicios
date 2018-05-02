package api.grupo.appservicios.view.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final String INDEX = "index";

	private Log logger = LogFactory.getLog(IndexController.class);

	@RequestMapping("/index")
	public String index() {
		logger.info("Inside index");
		return INDEX;
	}

}