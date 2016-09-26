package com.pengjinfei.concurrence.Servlet;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description: 并发性非常糟糕
 */
@ThreadSafe
public class SynchronizedFactorizer extends AbstractFactorizerServlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactos;

    @Override
    public synchronized void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        if (bigInteger.equals(lastNumber)) {
            encodeIntoResponse(res, lastFactos);
        } else {
            BigInteger[] factor = factor(bigInteger);
            lastNumber=bigInteger;
            lastFactos=factor;
            encodeIntoResponse(res,factor);
        }
    }
}
