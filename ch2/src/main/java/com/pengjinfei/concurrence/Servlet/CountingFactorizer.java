package com.pengjinfei.concurrence.Servlet;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description: 用AtomicLong保证原子性操作
 */
@ThreadSafe
public class CountingFactorizer extends AbstractFactorizerServlet {

    private final AtomicLong count=new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        BigInteger[] factor = factor(bigInteger);
        count.incrementAndGet();
        encodeIntoResponse(res,factor);
    }
}
