import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

statusListener(OnConsoleStatusListener)

appender("FILE", RollingFileAppender) {
    file = "/var/log/converter.log"

    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = "/var/log/converter.%d{yyyy-MM-dd}.%i.log"
        maxHistory = 30
        maxFileSize = "50MB"
        totalSizeCap = "5GB"
    }

    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %d{HH:mm:ss.SSS} [%thread] %X{MessageId}  %logger{100} - %msg%n"
    }
}

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %d{HH:mm:ss.SSS} [%thread] %X{MessageId}  %logger{100} - %msg%n"
    }
}

Appender fileAppender = appenderList.find { it -> it.name == "FILE" };

AsyncAppender asyncAppender = new AsyncAppender();
asyncAppender.queueSize = 10000
asyncAppender.name = "ASYNC"
asyncAppender.context = context
asyncAppender.addAppender(fileAppender)
appenderList.add(asyncAppender)
asyncAppender.start()

logger("org.mine.mmi", DEBUG)
logger("org.springframework.boot", DEBUG)

logger("org.springframework.web", DEBUG)

logger("org.springframework.util", INFO)
logger("org.springframework.jdbc.datasource", INFO)

root(INFO, ["ASYNC","CONSOLE"])

scan()
