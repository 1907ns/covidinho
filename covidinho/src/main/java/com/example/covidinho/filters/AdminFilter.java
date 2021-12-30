package com.example.covidinho.filters;

import com.example.covidinho.beans.User;
import com.example.covidinho.servlets.LoginServlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(req.getSession().getAttribute("user") == null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("../Login.jsp");
            dispatcher.forward(request, response);
        } else{
            if(((User)req.getSession().getAttribute("user")).getAdmin()==1){
                chain.doFilter(req,resp);
            } else{
                resp.sendRedirect("../Login.jsp");
            }
        }
    }
}
