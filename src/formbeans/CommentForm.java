package formbeans;
/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CommentForm {
	private String comment;

	public CommentForm(HttpServletRequest request) {
		String commentInput = request.getParameter("comment");
		String commentTest = null;

		if (commentInput != null) {
			commentTest = fixBadChars(commentInput);
			comment = commentTest.trim();
		}
			
	}

	public String getComment() {
		return comment;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (comment == null || comment.length() == 0)
			errors.add("Please enter your comment for the favorite URL.");

		return errors;
	}
	
	private String fixBadChars(String s) {
        if (s == null || s.length() == 0)
            return s;

        Pattern p = Pattern.compile("[<>\"&]");
        Matcher m = p.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null)
                b = new StringBuffer();
            switch (s.charAt(m.start())) {
            case '<':
                m.appendReplacement(b, "&lt;");
                break;
            case '>':
                m.appendReplacement(b, "&gt;");
                break;
            case '&':
                m.appendReplacement(b, "&amp;");
                break;
            case '"':
                m.appendReplacement(b, "&quot;");
                break;
            default:
                m.appendReplacement(b, "&#" + ((int) s.charAt(m.start())) + ';');
            }
        }

        if (b == null)
            return s;
        m.appendTail(b);
        return b.toString();
    }
}
