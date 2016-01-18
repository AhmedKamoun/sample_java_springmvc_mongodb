package tn.springmvc_mongodb.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import tn.springmvc_mongodb.web.config.root.AppSecurityConfig;
import tn.springmvc_mongodb.web.config.root.DevelopmentConfiguration;
import tn.springmvc_mongodb.web.config.root.RootContextConfig;
import tn.springmvc_mongodb.web.config.root.TestConfiguration;
import tn.springmvc_mongodb.web.config.servlet.ServletContextConfig;

/**
 *
 * Replacement for most of the content of web.xml, sets up the root and the
 * servlet context config.
 *
 */
//@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {

		// As we have AppSecurityConfig.java in same package as
		// RootContextConfig.java and
		// enabled ComponentScan to scan "tn.charity.v2" we
		// don't need to explicitely configure it.
		// otherwise we should add AppSecurityConfig.class ,
		// DevelopmentConfiguration.class to getRootConfigClasses():

		return new Class<?>[] { AppSecurityConfig.class, RootContextConfig.class, DevelopmentConfiguration.class,
				TestConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { ServletContextConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
