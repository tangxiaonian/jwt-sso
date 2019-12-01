package com.tang.leyou.mail.listener;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @Classname EmailListener
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/11/26 20:28
 * @Created by ASUS
 */

@RabbitListener(bindings = {
        @QueueBinding(
                exchange = @Exchange(name = "${amqp.email.exchange}",type = "direct"),
                value = @Queue(name = "${amqp.email.emailQueue}",autoDelete = "false"),
                key = {"${amqp.email.emailKey}"}
        )
})
@Component
public class EmailListener {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @RabbitHandler
    public void sendEmail(Map<String,String> map) {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;

        try {

            helper = new MimeMessageHelper(message, true);

            helper.setFrom("2684270465@qq.com");
            helper.setTo(map.get("qq"));
            helper.setSubject(map.get("subject"));

            helper.setText(getTemplateString(map.get("verificationCode")),true);

            mailSender.send(message);

        } catch (MessagingException e) {

            e.printStackTrace();
        }

    }

    /**
     * 获取 html 模板 内容
     * @param verificationCode
     * @return
     */
    private String getTemplateString(String verificationCode) {

        // 发送邮件
        Context context = new Context();

        context.setVariable("number",verificationCode);

        return templateEngine.process("index", context);

    }

}