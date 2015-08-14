package com.tricon.labs.join.config.views;

import java.util.Locale;

import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

/**
 * View resolver for returning XML in a view-based system.
 */
public class MarshallingXmlViewResolver implements ViewResolver {

    /**
     * Get the view to use.
     * 
     * @return Always returns an instance of {@link MappingJacksonJsonView}.
     */
    @Override
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {
        MarshallingView view = new MarshallingView();
        view.setMarshaller(new XStreamMarshaller());
        return view;
    }
}