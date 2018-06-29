public class Process implements Runnable {
    private String name;
    private Process nextProcess;
    private volatile int needTime;
    private volatile int usedTime;
    private volatile char state;
    private Object sharedObject;

    public Process() {
    }

    public Process(String name, int needTime, int usedTime, char state, Object sharedObject) {
        this.name = name;
        this.needTime = needTime;
        this.usedTime = usedTime;
        this.state = state;
        this.sharedObject = sharedObject;
    }

    public Process(String name, Process nextProcess, int needTime, int usedTime, char state) {
        this.name = name;
        this.nextProcess = nextProcess;
        this.needTime = needTime;
        this.usedTime = usedTime;
        this.state = state;
    }

    public void run() {
        while (true) {
            synchronized (sharedObject) {
                if (!Simulatar.isStopped) {
                    if (state == 'R') {
                        this.usedTime++;
                        if (this.needTime == this.usedTime) {
                            this.state = 'E';
                        }
                    } else {//Optimize dispatching
                        optimizeDispatching();
                        continue;
                    }
                    try {
                        Simulatar.showAllProcessState();
                        if (Simulatar.checkAll()) {
                            Simulatar.stopAll();
                        } else {
                            Thread.sleep(100);
                            sharedObject.notify();
                            sharedObject.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private synchronized void optimizeDispatching() {

        if (Simulatar.checkAll()) {
            Simulatar.stopAll();
            Simulatar.isStopped = true;
        } else {
            sharedObject.notify();
            try {
                sharedObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public Process getNextProcess() {
        return nextProcess;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public char getState() {
        return state;
    }

    public Object getSharedObject() {
        return sharedObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNextProcess(Process nextProcess) {
        this.nextProcess = nextProcess;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public void setState(char state) {
        this.state = state;
    }

    public void setSharedObject(Object sharedObject) {
        this.sharedObject = sharedObject;
    }
}
