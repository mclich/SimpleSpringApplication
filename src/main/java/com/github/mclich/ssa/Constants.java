package com.github.mclich.ssa;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Constants
{
    public static final DateTimeFormatter DATE_FORMAT=DateTimeFormatter.ofPattern("MMMM, d", Locale.US);
    public static final DateTimeFormatter YEAR_FORMAT=new DateTimeFormatterBuilder().appendValueReduced(ChronoField.YEAR, 1, 1, 0).toFormatter();

    public static final String LOGIN_MSG="com.github.mclich.ssa.validation.annotation.Login.message";
    public static final String LOGIN_NOT_UNIQUE="com.github.mclich.ssa.validation.annotation.Login.notUnique";
    public static final String PASSWORD_MSG="com.github.mclich.ssa.validation.annotation.Password.message";
    public static final String PASSWORD_CONFIRM="password.confirm.error";
    public static final String NAME_MSG="com.github.mclich.ssa.validation.annotation.Name.message";
    public static final String EMAIL_MSG="javax.validation.constraints.Email.message";
    public static final String GENDER_MSG="javax.validation.constraints.NotNull.gender";
    public static final String TERMS_MSG="terms.not.agreed.error";

    public static final String LOGIN_REGEX="^[a-z\\d_-]{3,15}$";
    public static final String PASSWORD_REGEX="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final String NAME_REGEX="^[a-z ,.'-]{3,20}$";
    public static final String EMAIL_REGEX="^\\S+@\\S+\\.\\S+$";

    public static final String USERS_QUERY="select login, password, status from user where login=?";
    public static final String ROLES_QUERY="select user.login, ur.role from user inner join user_role ur on user.id=ur.user_id and user.login=?";

    public static final String[] ALL_MATCHERS=new String[]{"/**/*.png", "/", "/403", "/students", "/teachers", "/classes", "/about", "/login", "/{fn:\\p{Alpha}+}-{ln:\\p{Alpha}+}"};
    public static final String[] USER_MATHER=new String[]{"/profile/**", "/logout"};
    public static final String[] MODERATOR_MATCHER=new String[]{"/edit/**", "/logout"};
    public static final String[] ADMIN_MATCHER=new String[]{"/admin/**", "/logout"};

    public static String pathToTitle(String path)
    {
        if(path==null) return "No Title | You-Zitsu";
        String title=Stream.of(path.split("[/-]")).filter(s->!s.isBlank())/*.sorted(String::compareTo)*/.map(StringUtils::capitalize).collect(Collectors.joining(" "));
        if(title.equals("403")) title="Access Denied";
        return title.isBlank()?"You-Zitsu":title+" | You-Zitsu";
    }

    public static void popUp(HttpServletRequest request, HttpServletResponse response, String attribute, String value, String location) throws IOException
    {
        request.getSession().setAttribute(attribute, value);
        response.sendRedirect(location);
    }
}