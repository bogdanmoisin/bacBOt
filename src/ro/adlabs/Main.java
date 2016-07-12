package ro.adlabs;

import javazoom.jl.player.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.FileInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        new Main();
    }

    private Main() {
        start();
    }

    private void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        FutureRunnable runnable = new FutureRunnable() {

            public void run() {
                int year = 2016;
                String url = "http://static.bacalaureat.edu.ro/" + year + "/info/news.html";
                try {
                    String source = downloadSourceForURL(url).toLowerCase();
                    if(source.contains("rezultat")) {
                        System.out.println("AVEM REZULTATE");
                        playAlarm();

                        getFuture().cancel(false);
                    } else {
                        System.out.println("No results yet.. Waiting a little bit more..");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        Future<?> future = executor.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
        runnable.setFuture(future);
    }

    private String downloadSourceForURL(String URL) throws Exception {
        String UA = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        Document doc = Jsoup.connect(URL).userAgent(UA).get();

        return removeComments(doc).toString();
    }

    private static Node removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }

        return node;
    }

    private void playAlarm() throws Exception{
        FileInputStream fis = new FileInputStream("alarm/alarm.mp3");
        Player playMP3 = new Player(fis);

        playMP3.play();
    }
}
