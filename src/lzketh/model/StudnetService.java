package lzketh.model;

import jdk.jshell.execution.Util;
import lzketh.Utils;

import java.util.ArrayList;

public class StudnetService {

	public static StudentModel add(StudentModel student) {
		ArrayList<StudentModel> all = load();
		all.add(student);
		save(all);
		return student;
	}

	public static ArrayList<StudentModel> load() {
		String className = StudentModel.class.getSimpleName();

		ArrayList<StudentModel> rs = ModelFactory.load(className, 3, modelData -> {
			String id = modelData.get(0);
			String username = modelData.get(1);
			String password = modelData.get(2);

			StudentModel q = new StudentModel();
			q.id = Integer.parseInt(id);
			q.username = username;
			q.password = password;
			return q;
		});
		return rs;
	}

	public static void save(ArrayList<StudentModel> list) {
		String className = StudentModel.class.getSimpleName();

		ModelFactory.save(className, list, model -> {
			ArrayList<String> lines = new ArrayList<>();
			lines.add(String.valueOf(model.id));
			lines.add(model.username);
			lines.add(model.password);
			return lines;
		});
	}

	public static StudentModel valid(String username, String password) {
		ArrayList<StudentModel> all = load();
		for (StudentModel s : all) {
			if (s.username.equals(username) && s.password.equals(password)) {
				return s;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Utils.log("%s", valid("jqhk", "jqhk"));
	}
}
