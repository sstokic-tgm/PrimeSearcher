package primes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by Stefan on 14.03.14.
 */
@WebServlet (urlPatterns = "/primes/searcher")
public class Searcher extends HttpServlet implements Runnable {

    private PrintWriter out;
    private long lastprime = 0;
    private Date lastprimeModified = new Date();
    private Date searchStarted = new Date();
    private Thread searcher;

    public void init() throws  ServletException {

        searcher = new Thread(this);
        searcher.start();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        try {
            out = response.getWriter();
        } catch (IOException e) {
        }

        out.println("<html>");
        out.println("<head></head>");
        out.println("<body style=\"text-align: center; margin-left: 10%; margin-right: 10%;\">");
        out.println("<h2>Prime Searcher</h2>");
        out.println("<div style=\"background-color: blue;\"><br/></div><br/>");
        out.println("Started at " + searchStarted + "<br/><br/>");

        if(lastprime == 1) {

            out.println("Still searching for the first prime...");
        }else {

            out.println("Last prime discovered was " + lastprime + " at " + lastprimeModified);
        }
        out.println("<br/><br/><div style=\"background-color: blue;\"><br/></div>");
        out.println("</body>");
        out.println("</html>");
    }

    public void run() {

        long numb = 1;

        while(true) {

            if(isPrime(numb)) {

                lastprime = numb;
                lastprimeModified = new Date();
            }
            numb += 2;

            try {

                searcher.sleep(300);
            }catch (InterruptedException ie) {}
        }
    }

    private boolean isPrime(long numb) {

        if(numb%2 == 0)
            return false;

        for(long i = 3; i*i <= numb; i += 2) {

            if(numb%i == 0)
                return false;
        }
        return true;
    }

    public void destroy() {
        searcher.interrupt();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }
}
