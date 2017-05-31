/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {

	public abstract String getName();

	public abstract String perform(HttpServletRequest request);

	private static Map<String, Action> map = new HashMap<String, Action>();

	public static void add(Action a) {
		synchronized (map) {
			map.put(a.getName(), a);
		}
	}

	public static String perform(String name, HttpServletRequest request) {
		Action action;
		synchronized (map) {
			action = map.get(name);
		}
		if (action == null) {
			return null;
		}
		return action.perform(request);
	}
}
