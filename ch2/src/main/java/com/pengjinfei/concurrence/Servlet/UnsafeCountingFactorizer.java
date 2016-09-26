package com.pengjinfei.concurrence.Servlet;

import com.pengjinfei.concurrence.annotation.NoThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description:
 */
@NoThreadSafe
public class UnsafeCountingFactorizer extends AbstractFactorizerServlet {

    private long count=0;

    public long getCount() {
        return count;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        BigInteger[] factor = factor(bigInteger);
        count++;
        encodeIntoResponse(res,factor);
    }
}
