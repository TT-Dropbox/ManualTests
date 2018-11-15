import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;
import junit.framework.AssertionFailedError;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by reg on 26/09/2018.
 */
public class Tester {

    private static final int SSH_PORT = 22;
    private static final String USERNAME = "reg";

    private static final String VM1_IP = "172.16.184.133";
    private static final String VM2_IP = "172.16.184.132";
    private static final String VMOBS_IP = "172.16.184.130";

    private static final String VM1_PRK = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAvJ2Q3INpFaPbI8WBL+Pz22L52nCfYafkKE+HIs+KAIX+lz1O\n" +
            "GiWbLRnmi9pgNz/kYan/E5gKyX4OoKvTpA4OLOvC0HafosYjEN0QsC5CfwFurcEv\n" +
            "f6FdR8T7MxFTmEZGm1O4rMSFhc4uhlWbSGO20UAt3rcOqCogpfEkGE0g9d/fiSBR\n" +
            "q0FdlseDXotUTL108llL5U8J0s6x4ZBFAmkfAcAUaul0bSE83pu+dii3YbYX4QYm\n" +
            "mOfwNjOUQL5zr7WdnZ34/fXKUIvA8EdKNDCyitSYfHCzESwjWDlkTdNNm53gKV9D\n" +
            "HN8CWoNzv4nsv9uuSXi02qJhNdhWKVnqIxNs7QIDAQABAoIBABBLlJ5YhBWoiGA4\n" +
            "ygZ3LTRBAtEKddqcBKwu4r/xKj0NVod+dmbv5M4qtK5UeYeMb5C4fR8WPahWybC7\n" +
            "55b+196tp7EW7ofDknf8gVqXRItwdj1wuUfPix2OOR2aW5yGRcdZnlTezaJXw9E7\n" +
            "SCUi6EJ/xva83flNWnC7uqCW2UgknyM901wghrIhjtFD/pFO6OA8W/6TrbBWSjfr\n" +
            "apGGmfO/RUtyNDCkgb0bkMHAdPyHUm9weIBHQdBcYb5NDPale6iapC6NyNl12I34\n" +
            "es6kk/9x11YTZyugnVa0/Qn+MaXkGNc6oo31xZaCRG7eKEg+iobBZaw5XHHPJI/h\n" +
            "5P4ehcECgYEA8zP59UDhLFdYlScOjvrhPbOOWcOb1QRmZcSTwBX89Hj9OfgxYGLJ\n" +
            "Fx+amaUtcRqWiqX5N5ykWCFxAk3+fDLMIrVZ+bCkl7lpGn2XVLBD0m51hgvx3aUf\n" +
            "CxVyyN//IoFaPgZXNQrOzvsPdcjMHhJ0JaILoGjjirBSubnOl2KJd8kCgYEAxopH\n" +
            "EYs0wADBcqfaGXLjzaoqJ82eCMR2G4tTCURD2+/iLzyTYZa/IITM+Su3PV8Sk7T/\n" +
            "AqoquOxrqibj/XhHsRHCG6RmeZwq+AWVylSo2FVB60nCU5y1+DCVya74oHk5eO6y\n" +
            "nGBb0YSI7bnbamANQJ17h2UJ8/Kvu8SGfbRaZgUCgYAlI5LQORApzUzhug0nGHi9\n" +
            "C2Z5nr11Ui6w68wEUVdHnhJNf/FKXsuGlHTvcaH227CRi7b7HxiZvMGdMHvkS32Q\n" +
            "71DGKIt5//5k5Tju3dv9jpCz368Xwddzwdq9gjdb9ZTqU92NZBEg2oYJ3pgNH5RN\n" +
            "CunrnRjiXPrFEfLSe2GywQKBgH1+kBd9x1UJ5T0k7g4h+e5Y/hZ0uMzP6dvVQCJe\n" +
            "XQpbYbv77SjFUYArkSh6wnNvcd2djYdQqnqDLja6KGhiK+a309sHGqMJ3HszhJAE\n" +
            "3UkI8wbXc5bIOplrlHKMP6mnlnVjY9DGc6NGxAqH7TIbDqVmJdvaOOVS5FOqkFxK\n" +
            "sM1FAoGAQMY9ITHa1yaowTpWK9UAdkubeTTE4SbrU6RrpWT90akEDqS6fxo2udLP\n" +
            "lVsFI0E2y+A+sJmtbwXcEaaZuD/b+uCnP0z9Ttp42bKZlrTyGIAdZ51SY4m0CVIV\n" +
            "MUJsHN9byhViaaOK+i/1UmURvkx5zblF8FDrrBzjTsn/4Agtt28=\n" +
            "-----END RSA PRIVATE KEY-----\n";

    private static Shell shellVM1;
    private static Shell shellVM2;
    private static Shell shellVMOBS;

    /**
     * Create the shells used to execute commands on the VM's acting as dropbox clients.
     *
     * @throws UnknownHostException when a connection with a VM could not be made.
     */
    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        shellVM1 = new SSH(VM1_IP, SSH_PORT, USERNAME, VM1_PRK);
        shellVM2 = new SSH(VM2_IP, SSH_PORT, USERNAME, VM1_PRK);
        shellVMOBS = new SSH(VMOBS_IP, SSH_PORT, USERNAME, VM1_PRK);
    }

    @Test
    public void test1() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        plainObs.exec("touch Dropbox/test1.txt");

        Thread.sleep(30000);
        MyThread t1 = new MyThread(plain1, "echo -n 'a' > Dropbox/test1.txt");
        MyThread t2 = new MyThread(plain2, "echo -n 'b' > Dropbox/test1.txt");

        t1.start();
        t2.start();

        Thread.sleep(40000);
        String dbResult = plainObs.exec("cat Dropbox/test1.txt");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "test1\\ \\(ubuntu\\'s\\ conflicted\\ copy\\ " + LocalDate.now().format(formatter) + "\\).txt";

        String conflictRes = plainObs.exec("cat Dropbox/" + fileName);
        if (dbResult.equals("a")) {
            assertEquals("b", conflictRes);
        } else if (dbResult.equals("b")) {
            assertEquals("a", conflictRes);
        } else {
            throw new AssertionFailedError();
        }

        plainObs.exec("rm Dropbox/" + fileName);
        plainObs.exec("rm Dropbox/test1.txt");

        System.out.println("Test completed");
    }

    @Test
    public void test2() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        Thread.sleep(20000);
        MyThread t1 = new MyThread(plain1, "echo -n 'a' > Dropbox/test2.txt");
        MyThread t2 = new MyThread(plain2, "echo -n 'b' > Dropbox/test2.txt");

        t1.start();
        t2.start();

        Thread.sleep(40000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "test2\\ \\(ubuntu\\'s\\ conflicted\\ copy\\ " + LocalDate.now().format(formatter) + "\\).txt";

        String dbResult = plainObs.exec("cat Dropbox/test2.txt");

        String conflictRes = plainObs.exec("cat Dropbox/" + fileName);


        if (dbResult.equals("a")) {
            assertEquals("b", conflictRes);
        } else if (dbResult.equals("b")) {
            assertEquals("a", conflictRes);
        } else {
            throw new AssertionFailedError();
        }

        plainObs.exec("rm Dropbox/" + fileName);
        plainObs.exec("rm Dropbox/test2.txt");


        System.out.println("Test completed");
    }

    @Test
    public void test3() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        plainObs.exec("touch Dropbox/test3.txt");

        Thread.sleep(10000);


        MyThread t1 = new MyThread(plain1, "echo -n 'a' > Dropbox/test3.txt");
        MyThread t2 = new MyThread(plain2, "rm Dropbox/test3.txt");

        t1.start();
        t2.start();


        Thread.sleep(10000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "test3\\ \\(ubuntu\\'s\\ conflicted\\ copy\\ " + LocalDate.now().format(formatter) + "\\).txt";


        boolean txtFileExists = plainObs.exec("[ -f Dropbox/test3.txt ] && echo \"exists\"").equals("exists\n");
        boolean conflictFileExists = plainObs.exec("[ -f Dropbox/"+fileName+" ] && echo \"exists\"").equals("exists\n");
        String dbResult = plainObs.exec("cat Dropbox/test3.txt");
        String dbConflictResult = plainObs.exec("cat Dropbox/"+fileName);

        if (txtFileExists){
            assertEquals("a",dbResult);

        } else {
            assertTrue(conflictFileExists);
            assertEquals("a",dbResult);

        }

        plainObs.exec("rm Dropbox/test3.txt");
        plainObs.exec("rm Dropbox/"+fileName);

        System.out.println("Test completed");
    }

    @Test
    public void test4() throws IOException, InterruptedException { // might conflict
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);
        plainObs.exec("touch Dropbox/test4.txt");
        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1, "echo -n 'a' > Dropbox/test4.txt");
        MyThread t2 = new MyThread(plain2, "mv Dropbox/test4.txt Dropbox/test4.old");

        t1.start();
        t2.start();
        Thread.sleep(30000);
        String resTxt = plainObs.exec("cat Dropbox/test4.txt");
        String resOld = plainObs.exec("cat Dropbox/test4.old");
        boolean oldFileExists = plainObs.exec("[ -f Dropbox/test4.old ] && echo \"exists\"").equals("exists\n");
        boolean txtFileExists = plainObs.exec("[ -f Dropbox/test4.txt ] && echo \"exists\"").equals("exists\n");

        if(oldFileExists && txtFileExists){
            assertEquals("a",resTxt);
            assertEquals("", resOld);
        } else if (oldFileExists){
            assertEquals("a",resOld);
        } else {
            throw new AssertionFailedError();
        }
        plainObs.exec("rm Dropbox/test4.old");
        plainObs.exec("rm Dropbox/test4.txt");

    }

    @Test
    public void test5() throws IOException, InterruptedException { // might conflict
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);
        plainObs.exec("touch Dropbox/test5.txt");
        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1, "rm Dropbox/test5.txt", "touch Dropbox/test5.txt");
        MyThread t2 = new MyThread(plain2, "echo -n 'a' > Dropbox/test5.txt");

        t1.start();
        t2.start();
        Thread.sleep(10000);

        boolean txtFileExists = plainObs.exec("[ -f Dropbox/test5.txt ] && echo \"exists\"").equals("exists\n");
        String dbResult = plainObs.exec("cat Dropbox/test5.txt");
        assertEquals(true,txtFileExists);
        if (!(dbResult.equals("a")||dbResult.equals(""))){
            throw new AssertionFailedError();
        }
        plainObs.exec("rm Dropbox/test5.txt");
    }

    @Test
    public void test6() throws IOException, InterruptedException {
        Shell.Plain plain = new Shell.Plain(shellVM1);

        plain.exec("touch Dropbox/test6.txt");
        plain.exec("echo -n 'a' > Dropbox/test6.txt");
        plain.exec("echo -n '' > Dropbox/test6.txt");

        Thread.sleep(10000);

        String readRes = plain.exec("cat Dropbox/test6.txt");

        assertEquals("", readRes);

        plain.exec("rm Dropbox/test6.txt");

        System.out.println("Test 6 completed");
    }

    @Test
    public void test7() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        plainObs.exec("mkdir Dropbox/folder7");

        plainObs.exec("touch Dropbox/test7.txt");
        plainObs.exec("echo -n 'abc' > Dropbox/test7.txt");

        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1, "mv Dropbox/test7.txt Dropbox/folder7/test7.txt");
        MyThread t2 = new MyThread(plain2, "rm -r Dropbox/folder7");

        t1.start();
        t2.start();

        Thread.sleep(60000); // this one can take a really long time

        boolean folder1Exists = plainObs.exec("[ -d Dropbox/folder7/ ] && echo \"exists\"").equals("exists\n");
        boolean txtFileExists = plainObs.exec("[ -f Dropbox/test7.txt ] && echo \"exists\"").equals("exists\n");
        boolean newTxtFileExists = plainObs.exec("[ -f Dropbox/folder7/test7.txt ] && echo \"exists\"").equals("exists\n");
        if (folder1Exists) { // folder removed, then recreated
            System.out.println("fldr1 exists " + txtFileExists + " " + newTxtFileExists);
            String readRes = plain1.exec("cat Dropbox/folder7/test7.txt");
            assertEquals(false, txtFileExists);
            assertEquals(true, newTxtFileExists);
        } else { // file moved to folder, then folder removed
            System.out.println("fldr1 does not exist "+ txtFileExists + " " + newTxtFileExists);
        }
        plainObs.exec("rm -r Dropbox/folder7");
        plainObs.exec("rm Dropbox/test7.txt");

        System.out.println("Test 7 completed");
    }

    @Test
    public void test8() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        plainObs.exec("mkdir Dropbox/folder8");
        plainObs.exec("touch Dropbox/folder8/test8.txt");

        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1, "rm -r Dropbox/folder8");
        MyThread t2 = new MyThread(plain2, "echo -n 'a' > Dropbox/folder8/test8.txt");

        t1.start();
        t2.start();

        Thread.sleep(40000);

        boolean folder1Exists = plainObs.exec("[ -d Dropbox/folder8/ ] && echo \"exists\"").equals("exists\n");
        if (folder1Exists) {
            String readRes = plain1.exec("cat Dropbox/folder8/test8.txt");
            assertEquals("a", readRes);
            plainObs.exec("rm -r Dropbox/folder8");
        }


        System.out.println("Test 8 completed");
    }

    @Test
    public void test9() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        plainObs.exec("mkdir Dropbox/folder9");

        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1, "mv Dropbox/folder9 Dropbox/folder9\\ 2");
        MyThread t2 = new MyThread(plain2, "mv Dropbox/folder9 Dropbox/folder9\\ 3");

        t1.start();
        t2.start();

        Thread.sleep(10000);

        String lsRes = plain1.exec("ls Dropbox");
        assertTrue(lsRes.contains("folder9 2") || lsRes.contains("folder9 3"));


        plainObs.exec("rm -r Dropbox/folder9\\ 2");
        plainObs.exec("rm -r Dropbox/folder9\\ 3");

        System.out.println("Test 9 completed");
    }

    @Test
    public void test10() throws IOException, InterruptedException {
        Shell.Plain plain1 = new Shell.Plain(shellVM1);
        Shell.Plain plain2 = new Shell.Plain(shellVM2);
        Shell.Plain plainObs = new Shell.Plain(shellVMOBS);

        Thread.sleep(10000);

        MyThread t1 = new MyThread(plain1,
                "mkdir Dropbox/folder10",
                "touch Dropbox/folder10/test10.txt",
                "echo -n 'a' > Dropbox/folder10/test10.txt");

        MyThread t2 = new MyThread(plain2,
                "mkdir Dropbox/folder10",
                "touch Dropbox/folder10/test10.txt",
                "echo -n 'b' > Dropbox/folder10/test10.txt");

        t1.start();
        t2.start();

        Thread.sleep(30000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "test10\\ \\(ubuntu\\'s\\ conflicted\\ copy\\ " + LocalDate.now().format(formatter) + "\\).txt";

        String conflictRes = plainObs.exec("cat Dropbox/folder10/" + fileName);
        String readRes = plainObs.exec("cat Dropbox/folder10/test10.txt");
        //assertEquals("a", readRes);

        if (readRes.equals("a")) {
            assertEquals("b", conflictRes);
        } else if (readRes.equals("b")) {
            assertEquals("a", conflictRes);
        } else {
            throw new AssertionFailedError();
        }

        plainObs.exec("rm -r Dropbox/folder10");

        System.out.println("Test 10 completed");
    }

    class MyThread extends Thread {

        String[] commands;
        Shell.Plain plain;

        public MyThread(Shell.Plain plain, String... commands) {
            this.commands = commands;
            this.plain = plain;
        }

        @Override
        public void run() {
            try {
                for (String command : commands) {
                    plain.exec(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}