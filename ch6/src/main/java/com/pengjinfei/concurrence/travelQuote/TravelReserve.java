package com.pengjinfei.concurrence.travelQuote;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description: 在预定时间内请求旅游报价
 */
public class TravelReserve {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    /*
    从一个公司获取报价
     */
    private class QuoteTask implements Callable<TravelQuote> {

        private final TravelCompany company;
        private final TravelInfo info;

        public QuoteTask(TravelCompany company, TravelInfo info) {
            this.company = company;
            this.info = info;
        }

        @Override
        public TravelQuote call() throws Exception {
            return company.solicitQuote(info);
        }

        public TravelQuote getFailureQuote(Throwable cause) {
            return null;
        }

        public TravelQuote getTimeoutQuote(CancellationException e) {
            return null;
        }
    }

    public List<TravelQuote> getRankedQuotes(TravelInfo travelInfo, Set<TravelCompany> companies, Comparator<TravelQuote> ranking, long time, TimeUnit timeUnit) throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }
        /*
        invokeAll按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中
         */
        List<Future<TravelQuote>> futures = executor.invokeAll(tasks, time, timeUnit);
        List<TravelQuote> quotes = new ArrayList<>();
        Iterator<QuoteTask> iterator = tasks.iterator();
        for (Future<TravelQuote> future : futures) {
            QuoteTask task = iterator.next();
            try {
                quotes.add(future.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }
        Collections.sort(quotes,ranking);
        return quotes;
    }
}
