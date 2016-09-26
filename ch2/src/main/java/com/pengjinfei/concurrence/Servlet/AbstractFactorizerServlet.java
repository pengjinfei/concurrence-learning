package com.pengjinfei.concurrence.Servlet;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description:
 */
public abstract class AbstractFactorizerServlet implements Servlet {

    protected BigInteger extractFromRequest(ServletRequest request) {
        String param = request.getParameter("param");
        return new BigInteger(param);
    }

    protected BigInteger[] factor(BigInteger bigInteger) {
        return new BigInteger[]{BigInteger.valueOf(2), BigInteger.valueOf(3)};
    }

    protected void encodeIntoResponse(ServletResponse response, BigInteger[] factors) throws IOException {
        String[] strings = new String[factors.length];
        for (int i=0;i<factors.length;i++) {
            strings[i]= String.valueOf(factors[i]);
        }
        response.getWriter().write(Arrays.toString(strings));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
