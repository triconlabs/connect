package com.tricon.labs.join.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

import com.tricon.labs.join.config.views.JsonViewResolver;
import com.tricon.labs.join.config.views.MarshallingXmlViewResolver;

/**
 * 
 * @author Shailesh
 * 
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	protected ServletContext servletContext;

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry)
	 * { registry.addViewController("/").setViewName("index");
	 * //registry.addViewController("/home").setViewName("welcome");
	 * //registry.addViewController("/denied").setViewName("denied"); }
	 */

	@Bean(name = "messageSource")
	// Mandatory name
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
		msgSrc.setBasename("/WEB-INF/messages/global");
		return msgSrc;
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		// Simple strategy: only path extension is taken into account
		configurer.favorPathExtension(true).useJaf(true)
				.defaultContentType(MediaType.TEXT_HTML)
				.mediaType("html", MediaType.TEXT_HTML)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	/**
	 * Create the CNVR. Explicit setup - the view-resolvers are created y this
	 * method and the CNVR is initialised with both the resolvers to use and its
	 * {@link ContentNegotiationManager}.
	 * 
	 * @param manager
	 *            The content negotiation manager to use.
	 * @param servletContext
	 *            The Servlet Context - only required so the
	 *            {@link XmlViewResolver} can locate it's XML file of View beans
	 *            relative to document-root (it's in <tt>WEB-INF/spring</tt>).
	 * @return A CNVR instance.
	 */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager manager) {
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		InternalResourceViewResolver r2 = new InternalResourceViewResolver();
		r2.setPrefix("/WEB-INF/views/");
		r2.setSuffix(".jsp");
		resolvers.add(r2);
		resolvers.add(getJsonViewResolver());
		resolvers.add(getMarshallingXmlViewResolver());
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	private ViewResolver getJsonViewResolver() {
		return new JsonViewResolver();
	}

	private ViewResolver getMarshallingXmlViewResolver() {
		return new MarshallingXmlViewResolver();
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(500000000);
		return resolver;
	}

}
