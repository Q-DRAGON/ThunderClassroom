package lzketh.model;

import java.util.ArrayList;

public class AnswerService {

	public static Answer add(Answer question) {
		ArrayList<Answer> all = load();
		all.add(question);
		save(all);
		return question;
	}

	public static ArrayList<Answer> load() {
		String className = Answer.class.getSimpleName();

		ArrayList<Answer> rs = ModelFactory.load(className, 3, modelData -> {
			String id = modelData.get(0);
			String studentId = modelData.get(1);
			String solution = modelData.get(2);

			Answer q = new Answer();
			q.questionId = id;
			q.studentId = Integer.parseInt(studentId);
			q.solution = solution;
			return q;
		});
		return rs;
	}

	public static void save(ArrayList<Answer> list) {
		String className = Answer.class.getSimpleName();

		ModelFactory.save(className, list, model -> {
			ArrayList<String> lines = new ArrayList<>();
			lines.add(model.questionId);
			lines.add(String.valueOf(model.studentId));
			lines.add(model.solution);
			return lines;
		});
	}

}
