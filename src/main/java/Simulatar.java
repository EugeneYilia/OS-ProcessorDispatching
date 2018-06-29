public class Simulatar {
    public static volatile boolean isStopped = false;
    public static int count = 0;
    public static Object sharedObject = new Object();
    public static Process process1;
    public static Process process2;
    public static Process process3;
    public static Process process4;
    public static Process process5;
    public static String fourBlank = "    ";

    public static Thread process1Thread;
    public static Thread process2Thread;
    public static Thread process3Thread;
    public static Thread process4Thread;
    public static Thread process5Thread;

    public static void simulatePD() {
        process1 = new Process("P1", 3, 0, 'R', sharedObject);
        process2 = new Process("P2", 6, 0, 'R', sharedObject);
        process3 = new Process("P3", 2, 0, 'R', sharedObject);
        process4 = new Process("P4", 1, 0, 'R', sharedObject);
        process5 = new Process("P5", 9, 0, 'R', sharedObject);

        process1.setNextProcess(process2);
        process2.setNextProcess(process3);
        process3.setNextProcess(process4);
        process4.setNextProcess(process5);
        process5.setNextProcess(process1);

        process1Thread = new Thread(process1);
        process2Thread = new Thread(process2);
        process3Thread = new Thread(process3);
        process4Thread = new Thread(process4);
        process5Thread = new Thread(process5);

        process1Thread.start();
        process2Thread.start();
        process3Thread.start();
        process4Thread.start();
        process5Thread.start();
    }

    public static boolean checkAll() {
        if (process1.getState() == 'E' && process2.getState() == 'E' && process3.getState() == 'E' && process4.getState() == 'E' && process5.getState() == 'E') {
            return true;
        } else {
            return false;
        }
    }

    public static void showAllProcessState() {
        count++;
        System.out.println("############第" + count + "次时间片轮转调度之后的结果开始###########");
        System.out.println("ProcessName     NextProcess    NeedTime   UsedTime    State");
        System.out.println(process1.getName() + getBlank(18) + process1.getNextProcess().getName() + getBlank(13) + process1.getNeedTime() + getBlank(10) + process1.getUsedTime() + getBlank(10) + process1.getState());
        System.out.println(process2.getName() + getBlank(18) + process2.getNextProcess().getName() + getBlank(13) + process2.getNeedTime() + getBlank(10) + process2.getUsedTime() + getBlank(10) + process2.getState());
        System.out.println(process3.getName() + getBlank(18) + process3.getNextProcess().getName() + getBlank(13) + process3.getNeedTime() + getBlank(10) + process3.getUsedTime() + getBlank(10) + process3.getState());
        System.out.println(process4.getName() + getBlank(18) + process4.getNextProcess().getName() + getBlank(13) + process4.getNeedTime() + getBlank(10) + process4.getUsedTime() + getBlank(10) + process4.getState());
        System.out.println(process5.getName() + getBlank(18) + process5.getNextProcess().getName() + getBlank(13) + process5.getNeedTime() + getBlank(10) + process5.getUsedTime() + getBlank(10) + process5.getState());
        System.out.println("############第" + count + "次时间片轮转调度之后的结果结束###########");
    }

    public static String getBlank(int number) {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < number; i++) {
            returnString.append(" ");
        }
        return returnString.toString();
    }

    public synchronized static void stopAll() {
        if (!Simulatar.isStopped) {
            Simulatar.isStopped = true;
            process1Thread.currentThread().interrupt();
            process2Thread.currentThread().interrupt();
            process3Thread.currentThread().interrupt();
            process4Thread.currentThread().interrupt();
            process5Thread.currentThread().interrupt();
            System.out.println("All process finish now.");
            System.exit(0);
        }
    }
}
