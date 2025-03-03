import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

class PeriodicTimerTask extends TimerTask {
    public void run() {
        System.out.println("...");
        Toolkit.getDefaultToolkit().beep();
    }
}

class DelayedTask extends TimerTask {
    private  Timer target;

    DelayedTask(Timer targetTimer) {
        target = targetTimer;
    }

    public void run() {
        target.cancel();
        System.out.println("Periodic timer canceled");
        System.out.println("Delayed Task Timer finished");
    }
}

public class Activator {
    private PeriodicTimerTask ptt;
    private Timer pttTimer;
    private DelayedTask dt;
    private Timer dtTimer;

    Activator() {
        ptt = new PeriodicTimerTask();
        pttTimer = new Timer();
        dt = new DelayedTask(pttTimer);
        dtTimer = new Timer();
    }

    public void activateDelayedTimer(long delay) {
        dtTimer.schedule(dt, delay);
        System.out.println("Delay begins;");
    }

    public void activateClockTimer(long delay, long period) {
        System.out.println("Repeating with " + delay + " in " + period + " millis");
        pttTimer.scheduleAtFixedRate(ptt, delay, period);
    }

    public static void main (String[] args) {
        Activator activator = new Activator();
        activator.activateClockTimer(1000, 2000);
        activator.activateDelayedTimer(10000);
    }
}
