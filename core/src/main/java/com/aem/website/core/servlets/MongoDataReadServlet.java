package com.aem.website.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.website.core.services.ReadDataService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service=Servlet.class,
        property={
                Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
                "sling.servlet.paths="+ "/bin/readFormData",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET

        })
public class MongoDataReadServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUid = 1L;

    @Reference
    private ReadDataService readdata;

    @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws ServletException, IOException {


        JSONObject finalResponse = new JSONObject();
        
        // Invoke the readDataFromMongoDB to return a JSON with all user documents
        finalResponse = readdata.readDataFromMongoDB();

        response.setContentType("application/json");
        response.getWriter().print(finalResponse);
    }
}
