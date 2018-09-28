package br.com.jira.hello.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;
import java.io.IOException;
import java.util.*;

@Scanned
public class Hello extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(Hello.class);
    private static final String LIST_TEMPLATE = "/templates/feature/hello/show.vm";

    @ComponentImport
    private TemplateRenderer templateRenderer;

    @Inject
    public Hello(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
      ApplicationUser loggedInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

      Map<String, Object> context = new HashMap<>();
      context.put("currentUser", loggedInUser.getDisplayName());
      templateRenderer.render(LIST_TEMPLATE, context, resp.getWriter());
    }

}
