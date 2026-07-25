import java.util.concurrent.CountDownLatch;
class Foo {
    private CountDownLatch fd;
    private CountDownLatch sd;
    public Foo() {
        fd = new CountDownLatch(1);
        sd = new CountDownLatch(1);
    }
    public void first(Runnable printFirst) {
        printFirst.run();
        fd.countDown();
    }
    public void second(Runnable printSecond) throws InterruptedException {
        fd.await();
        printSecond.run();
        sd.countDown();
    }
    public void third(Runnable printThird) throws InterruptedException {
        sd.await();
        printThird.run();
    }
}
