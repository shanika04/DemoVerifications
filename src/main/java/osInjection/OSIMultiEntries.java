package osInjection;

import org.owasp.esapi.codecs.WindowsCodec;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.ESAPI;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OSIMultiEntries {

    @PostMapping
    public void source1(String password) throws IOException, InterruptedException {
        String osCommand = "/bin/ls ";
        sink(encodeForOS(password), osCommand);
    }

    @PostMapping
    public void source2(String userName) throws IOException, InterruptedException {
        String osCommand = "c:\\WINDOWS\\SYSTEM32\\cmd.exe /c dir ";
        sink(encodeForOS(userName), osCommand);
    }

    public void source3(String surName) throws IOException, InterruptedException {
        String osCommand = "/bin/ls ";
        sink(surName, osCommand);
    }

    @PostMapping
    public void source4(String middleName) throws IOException, InterruptedException {
        middleName = "abc";
        String osCommand = "c:\\WINDOWS\\SYSTEM32\\cmd.exe /c dir ";
        sink(middleName, osCommand);
    }

    private void sink(String data, String osCommand) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(osCommand + data);
        process.waitFor();
    }

    private String encodeForOS(String param) {
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
            return ESAPI.encoder().encodeForOS(new WindowsCodec(), param);
        } else {
            return ESAPI.encoder().encodeForOS(new UnixCodec(), param);
        }
    }
}