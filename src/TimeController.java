import java.util.Timer;
import java.util.TimerTask;

public class TimeController {

    public static Timer timer;
    public static  TimerTask task;

    public TimeController() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {

                System.out.println("タイマーが動作しました");

            }
        };
    }

    public static void timeOver() {

        timer.schedule(task, 5000); // 5秒後にタスクを実行
    }

}