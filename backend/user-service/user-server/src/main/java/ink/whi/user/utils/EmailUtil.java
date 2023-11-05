package ink.whi.user.utils;

import ink.whi.common.utils.SpringUtil;
import ink.whi.user.model.vo.EmailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author qing
 * @date 2023/11/5
 */
@Slf4j
public class EmailUtil {

    private static volatile String from;

    public static String getFrom() {
        if (from == null) {
            synchronized (EmailUtil.class) {
                if (from == null) {
                    from = SpringUtil.getConfig("spring.mail.from");
                }
            }
        }
        return from;
    }

    /**
     * 发送邮件封装
     *
     * @param email
     * @return
     */
    public static boolean sendMail(EmailVo email) {
        try {
            JavaMailSender javaMailSender = SpringUtil.getBean(JavaMailSender.class);
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(getFrom());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getTitle());
            // 支持html模板
            mimeMessageHelper.setText(email.getContent(), true);
            Thread.currentThread().setContextClassLoader(EmailUtil.class.getClassLoader());
            javaMailSender.send(mimeMailMessage);
            return true;
        } catch (Exception e) {
            log.warn("sendEmail error to {}, {}", email.getTo(), e);
            return false;
        }
    }
}
